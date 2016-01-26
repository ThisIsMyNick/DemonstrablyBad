package translate;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.net.UnknownHostException;
import translate.tesseract.TranslateTesseract;
import translate.web.TranslateClient;

public class Driver
{
    static String source;
    static String target;
    static boolean transcribe;
    static boolean shellmode;

    public static void help()
    {
        System.out.println("Usage: ./run.sh [--img=PATH] [--shell] [--from=SOURCE] [--to=TARGET] [--tr]");
        System.out.println("Arguments:");
        System.out.println("\timg\tImage to be translated");
        System.out.println("\tshell\tUse stdin for text instead of image");
        System.out.println("\tfrom\tLanguage to be translated from (defaults to automatic language detection).");
        System.out.println("\tto\tLanguage to translate image to");
        System.out.println("\ttr\tSpecify if you only want a transcription, not translation.");
    }

    public static void parseArgs(String[] args)
    {
        if (args.length < 5)
        {
            // Only the to and from arguments have been passed
            System.out.println("Please use run.sh");
            help();
            System.exit(1);
        }

        // Parse the --img argument
        String[] img = args[0].split("=");
        if (img.length > 1)
        {
            File f = new File(img[1]);
            if (!f.exists() || f.isDirectory())
            {
                System.out.println("File does not exist!");
                help();
                System.exit(1);
            }
        }

        // Parse the --from argument
        String[] from = args[1].split("=");

        if (from.length > 1)
        {
            // Source language has been specified
            source = from[1];
        } else
        {
            // Default to automatic language detection
            source = "auto";
        }

        // Parse the --to argument
        String[] to = args[2].split("=");

        if (to.length > 1)
        {
            // Target language has been specified
            target = to[1];
        } else
        {
            // Default to english
            target = "en";
        }

        // Parse the --transcribe argument
        transcribe = args[3].equals("--transcribe=true");

        // Parse the --shell argument
        shellmode = args[4].equals("--shell=true");
    }

    private static String getLang(String text)
        throws IOException
    {
        TranslateClient t = new TranslateClient();

        Map<String, String> params = new HashMap<>();
        params.put("sl", "auto");
        params.put("tl", "en");
        params.put("client", "p");
        params.put("text", text);

        String response = t.makeRequest(t.GOOGLE_URL, "GET", params);
        return t.getSourceLanguage(response);
    }

    private static String translate(String source, String target, String text)
        throws IOException
    {
        TranslateClient t = new TranslateClient();

        Map<String, String> params = new HashMap<>();
        params.put("sl", source);
        params.put("tl", target);
        params.put("client", "p");
        params.put("text", text);

        String response = t.makeRequest(t.GOOGLE_URL, "GET", params);
        return t.getSentences(response);
    }

    private static void interactive()
    {
        String s;
        InputStreamReader in = new InputStreamReader(System.in);
        BufferedReader input = new BufferedReader(in);
        for (;;)
        {
            System.out.print(">> ");
            try
            {
                s = input.readLine();
            } catch (IOException o)
            {
                o.printStackTrace(System.out);
                return;
            }
            if (s == null) break;
            try
            {
                System.out.println(translate(source, target, s));
            } catch (Exception o)
            {
                o.printStackTrace(System.out);
                if (o instanceof UnknownHostException)
                {
                    System.out.println("Perhaps you're not connected to the internet.");
                }
            }
        }
    }

    private static String isoToTess(String src)
    {
        if (src.equals("en")) return "eng";
        if (src.equals("es")) return "spa";
        return "en";
    }

    public static void main(String[] args)
    {
        parseArgs(args);
        if (shellmode)
        {
            interactive();
            return;
        }

        if (!source.equals("auto"))
        {
            try
            {
                String tess_code = isoToTess(source);
                String s = TranslateTesseract.getText(args[0].substring(6), tess_code);
                if (transcribe)
                {
                    // transcribe only
                    System.out.println(s);
                    return;
                }
                System.out.println(translate(source, target, s));
            } catch (Exception o)
            {
                o.printStackTrace(System.out);
                if (o instanceof UnknownHostException)
                {
                    System.out.println("Perhaps you're not connected to the internet.");
                }
            }
        }

        try
        {
            String s = TranslateTesseract.getText(args[0].substring(6), "eng");
            String src = getLang(s);
            String tess_code = isoToTess(src);
            s = TranslateTesseract.getText(args[0].substring(6), tess_code);
            if (transcribe)
            {
                // transcribe only
                System.out.println(s);
                return;
            }
            System.out.println(translate(source, target, s));
        } catch (Exception o)
        {
            o.printStackTrace(System.out);
            if (o instanceof UnknownHostException)
            {
                System.out.println("Perhaps you're not connected to the internet.");
            }
        }
    }
}
