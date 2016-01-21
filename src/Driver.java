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

    public static void help()
    {
        System.out.println("Usage: make run img=PATH [from=SOURCE] [to=TARGET]");
        System.out.println("Arguments:");
        System.out.println("\timg\tImage to be translated");
        System.out.println("\tfrom\tLanguage to be translated from (defaults to automatic language detection).");
        System.out.println("\tto\tLanguage to translate image to");
    }

    public static void parseArgs(String[] args)
    {
        if (args.length < 3)
        {
            // Only the to and from arguments have been passed
            System.out.println("Please provide an image.");
            help();
            System.exit(1);
        }

        File f = new File(args[0]);
        if (!f.exists() || f.isDirectory())
        {
            System.out.println("File does not exist!");
            help();
            System.exit(1);
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
    }

    public static void main(String[] args)
    {
        parseArgs(args);
        String s = TranslateTesseract.getText(args[0]);
        TranslateClient t = new TranslateClient();

        Map<String, String> params = new HashMap<>();
        params.put("sl", source);
        params.put("tl", target);
        params.put("client", "p");
        params.put("text", s);
        try
        {
            String response = t.makeRequest(t.GOOGLE_URL, "GET", params);
            System.out.println(t.getSentences(response));
        } catch (Exception o)
        {
            System.out.println(o);
            if (o instanceof UnknownHostException)
            {
                System.out.println("Perhaps you're not connected to the internet.");
            }
        }
    }
}
