package translate;

import translate.web.TranslateClient;

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
        TranslateClient t = new TranslateClient();
        Map<String, String> params = new HashMap<>();
        params.put("sl", "es");
        params.put("tl", "en");
        params.put("client", "p");
        params.put("text", "hola, como estas?");
        try
        {
            System.out.println(t.makeRequest(t.GOOGLE_URL, "GET", params));
        } catch (IOException o)
        {
            System.out.println(o);
        }
    }
}
