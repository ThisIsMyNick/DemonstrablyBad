package translate.test;

import java.io.UnsupportedEncodingException;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import translate.web.TranslateClient;

public class TestTranslateClient {

    @Test
    public void testUrlEncode() {
        String result;
        try {
            result = TranslateClient.urlEncode("test");
            assertEquals(result, "test");
        } catch (UnsupportedEncodingException e) {
        }
    }
}
