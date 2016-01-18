package translate.web;

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
import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

/*
 * Handles interaction with the Google api
 */
public class TranslateClient
{

    private final String USER_AGENT = "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2227.0 Safari/537.36";
    //private static final String GOOGLE_URL = "http://translate.google.com/translate_a/t";
    public final String GOOGLE_URL = "http://translate.google.com/translate_a/t";

    public static String urlEncode(String s) throws UnsupportedEncodingException
    {
        try
        {
            return URLEncoder.encode(s, "UTF-8");
        } catch (UnsupportedEncodingException e)
        {
            throw new UnsupportedEncodingException(e.toString());
        }
    }

    public String parameterize(Map<String, String> parameters) throws UnsupportedEncodingException
    {
        if (parameters.size() == 0) {
            return "";
        }

        String params = "";
        for (Map.Entry<String, String> parameter : parameters.entrySet())
        {
            params += String.format("%s=%s",
                    urlEncode(parameter.getKey().toString()),
                    urlEncode(parameter.getValue().toString()));
            params += "&";
        }
        return params.substring(0, params.length()-1);
    }

    public String makeRequest(String url, String method, Map<String, String> parameters) throws IOException
    {
        URL u = new URL(url);
        HttpURLConnection conn = (HttpURLConnection) u.openConnection();

        conn.setRequestProperty("User-Agent", USER_AGENT);
        conn.setDoOutput(true);
        conn.setRequestMethod(method);
        if (parameters != null)
        {
            conn.getOutputStream().write(parameterize(parameters).toString().getBytes("UTF-8"));
        }
        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
        String response = "";
        String input;
        while ((input = br.readLine()) != null)
        {
            response += input;
        }
        return response;
    }

    public String getSentences(String json) {
        JsonObject object = Json.parse(json).asObject();
        JsonArray sentences = object.get("sentences").asArray();
        JsonValue sentence = sentences.get(0);

        String translated = sentence.asObject().get("trans").asString();

        // Replace multiple new lines with just one
        translated = translated.replace("\n\n", "\n");
        return translated;
    }
}
