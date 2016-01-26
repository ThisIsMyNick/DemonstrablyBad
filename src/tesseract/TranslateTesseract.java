package translate.tesseract;

import java.io.File;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

public class TranslateTesseract
{

    // Extract text from an image given the path, and language
    public static String getText(String path, String lang)
    {
        File image = new File(path);

        // Tesseract is a singleton
        Tesseract instance = Tesseract.getInstance();

        String text = "";
        try
        {
            // Set the language of the image and attempt to retrieve text
            instance.setLanguage(lang);
            text = instance.doOCR(image);
            return text;
        } catch (TesseractException e)
        {
            return null;
        }
    }

}
