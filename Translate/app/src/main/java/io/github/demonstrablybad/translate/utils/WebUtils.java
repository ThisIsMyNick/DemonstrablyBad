package io.github.demonstrablybad.translate.utils;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

public class WebUtils {

    public static final String USER_AGENT = "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2227.0 Safari/537.36";
    public static final String GOOGLE_TRANSLATE = "http://translate.google.com/translate_a/t";

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

    public static String parameterize(Map<String, String> parameters) throws UnsupportedEncodingException
    {
        if (parameters.size() == 0)
        {
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

    public static String makeRequest(String url, String method, Map<String, String> parameters) throws IOException
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

    public static String getSentences(String json) {
        JSONObject object;
        JSONArray array;
        try {
            object = new JSONObject(json);
            array = object.getJSONArray("sentences");
        } catch (JSONException e) {
            return null;
        }


        String translated = "";
        for (int i = 0; i < array.length(); i++) {
            try {
                translated += array.getJSONObject(i).getString("trans").replace("\n\n", "\n");
            } catch (JSONException e) {
                continue;
            }
        }
        return translated;
    }

    public static boolean downloadFile(String fileURL, String saveDir)
            throws IOException {
        URL url = new URL(fileURL);
        HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
        int responseCode = httpConn.getResponseCode();

        if (responseCode == HttpURLConnection.HTTP_OK) {
            String fileName = "";
            String disposition = httpConn.getHeaderField("Content-Disposition");
            String contentType = httpConn.getContentType();
            int contentLength = httpConn.getContentLength();

            if (disposition != null) {
                int index = disposition.indexOf("filename=");
                if (index > 0) {
                    fileName = disposition.substring(index + 10,
                            disposition.length() - 1);
                }
            } else {
                fileName = fileURL.substring(fileURL.lastIndexOf("/") + 1,
                        fileURL.length());
            }

            InputStream inputStream = httpConn.getInputStream();
            String saveFilePath = saveDir + "/" + fileName;

            // opens an output stream to save into file
            FileOutputStream outputStream = new FileOutputStream(saveFilePath);

            int bytesRead = -1;
            byte[] buffer = new byte[1024]; // one kb
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }

            outputStream.flush();
            outputStream.close();
            inputStream.close();
            return true;
        } else {
            return false;
        }
    }

    public static String translate(String text, String source, String target) {
        Map<String, String> params = new HashMap<>();
        params.put("sl", source);
        params.put("tl", target);
        params.put("client", "p");
        params.put("text", text);

        String response;
        try {
            response = makeRequest(GOOGLE_TRANSLATE, "GET", params);
        } catch (Exception e) {
            if (e instanceof UnknownHostException) {
                Log.d("ERROR", "No Internet");
            } else {
                Log.d("ERROR", "SOMETHING GOOFED", e);
            }
            return "";
        }
        return getSentences(response);
    }
}
