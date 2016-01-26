package io.github.demonstrablybad.translate.ocr;

import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;

import com.googlecode.tesseract.android.TessBaseAPI;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.github.demonstrablybad.translate.utils.DownloadFileAsyncTask;
import io.github.demonstrablybad.translate.utils.WebUtils;

/**
 * Created by James on 1/24/16.
 */
public class OCR {

    private static OCR ocr;

    public final static String EXTERNAL_APP_DIRECTORY = "DemonstrablyBad";
    public final static String OCR_DATA_DIRECTORY = EXTERNAL_APP_DIRECTORY + "/" + "tessdata";
    public final static String IMAGE_DIRECTORY = EXTERNAL_APP_DIRECTORY + "/" + "images";
    public final static String DOWNLOAD_TRAINED_URL = "https://github.com/tesseract-ocr/tessdata/raw/master/%s.traineddata";

    public Map<String, String> languageCodeMap = new HashMap<String, String>();
    public static String[] SOURCE_LANGUAGES = {
            "auto",
            "en",
            "es"
    };

    public static String[] TARGET_LANGUAGES = {
            "en",
            "es"
    };

    private TessBaseAPI tessAPI;

    public static OCR getInstance() {
        if (ocr == null) {
            ocr = new OCR();
            ocr.initLanguageCodeMap();
        }
        return ocr;
    }

    public static String getTessDirectory() {
        String path = new File(Environment.getExternalStorageDirectory(), EXTERNAL_APP_DIRECTORY).getPath() + "/";
        return path;
    }

    public static String getOCRDataDirectory() {
        String path = new File(Environment.getExternalStorageDirectory(), OCR_DATA_DIRECTORY).getPath() + "/";
        return path;
    }

    public static String getImageDirectory() {
        String path = new File(Environment.getExternalStorageDirectory(), IMAGE_DIRECTORY).getPath() + "/";
        return path;
    }

    public static void downloadLanguage(String lang) {
        Formatter formatter = new Formatter();
        new DownloadFileAsyncTask().execute(formatter.format(DOWNLOAD_TRAINED_URL, lang).toString(), getOCRDataDirectory());
    }

    public void initTessAPI(String lang) {
        tessAPI = new TessBaseAPI();
        tessAPI.init(getTessDirectory(), lang);
    }

    public String getText(Bitmap bitmap) {
        tessAPI.setImage(bitmap);
        return tessAPI.getUTF8Text();
    }

    public void initLanguageCodeMap() {
        languageCodeMap.put("eng", "English");
        languageCodeMap.put("spa", "Spanish");
    }

    public String getLanguageCodeFromLanguage(String language) {
        for (Map.Entry<String, String> entry : languageCodeMap.entrySet()) {
            if (entry.getValue().equals(language)) {
                return entry.getKey();
            }
        }
        return "eng";
    }

    public List<String> getLanguages() {
        List<String> languages = new ArrayList<String>();
        File tessDataDir = new File(OCR.getOCRDataDirectory());
        for (File language : tessDataDir.listFiles()) {
            String fileName = language.getName();

            // Format is xxx(_xxx)?.traineddata where xxx is the language code
            String languageCode = fileName.substring(0, fileName.indexOf("."));
            languages.add(languageCodeMap.get(languageCode));
        }
        return languages;
    }
}
