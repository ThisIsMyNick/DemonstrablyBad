package translate;

import translate.web.TranslateClient;
import translate.tesseract.TranslateTesseract;

import java.io.BufferedReader;
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

public class Driver
{
    public static void main(String[] args)
    {
        if (args.length < 1) {
            System.out.println("Please provide an image!");
            System.exit(1);
        }
        String s = TranslateTesseract.getText(args[0]);
        TranslateClient t = new TranslateClient();

        Map<String, String> params = new HashMap<>();
        params.put("sl", "es");
        params.put("tl", "en");
        params.put("client", "p");
        params.put("text", s);
        try
        {
            System.out.println(t.makeRequest(t.GOOGLE_URL, "GET", params));
        } catch (IOException o)
        {
            System.out.println(o);
        }
    }
}
