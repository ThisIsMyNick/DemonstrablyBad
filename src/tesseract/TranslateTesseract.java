package translate.tesseract;

import java.io.File;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

public class TranslateTesseract
{

    public static String getText(String path, String lang)
    {
        File image = new File(path);
        Tesseract instance = Tesseract.getInstance();

        String text = "";
        try
        {
            instance.setLanguage(lang);
            text = instance.doOCR(image);
            return text;
        } catch (TesseractException e)
        {
            return null;
        }
    }

}
