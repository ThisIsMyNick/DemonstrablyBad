package translate.test;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import translate.web.TranslateClient;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class TestTranslateClient
{
    TranslateClient client;
    String result;
    Map<String, String> parameters;

    @Before
    public void setup()
    {
        result = "";
        parameters = new HashMap<>();
        client = new TranslateClient();
    }

    @Test
    public void testUrlEncode()
    {
        try
        {
            result = TranslateClient.urlEncode("test");
            assertEquals(result, "test");
            result = TranslateClient.urlEncode("this and that!");
            assertEquals(result, "this+and+that%21");
            result = TranslateClient.urlEncode("?&&||&&?");
            assertEquals(result, "%3F%26%26%7C%7C%26%26%3F");
        } catch (UnsupportedEncodingException e)
        {
            fail(e.toString());
        }
    }

    @Test
    public void testParameterize()
    {
        try
        {
            result = client.parameterize(parameters);
            assertEquals(result, "");

            parameters.put("test", "params");

            result = client.parameterize(parameters);
            assertEquals(result, "test=params");

            parameters.put("u", "{} ");
            result = client.parameterize(parameters);
            assertEquals(result, "test=params&u=%7B%7D+");
        } catch (UnsupportedEncodingException e)
        {
            fail(e.toString());
        }
    }

    @Test
    public void testMakeRequest()
    {
        try
        {
            String response = client.makeRequest("https://icanhazip.com", "POST", new HashMap<String, String>());
            assertFalse(response=="");
        } catch (Exception e)
        {
            fail(e.toString());
        }
    }
}
