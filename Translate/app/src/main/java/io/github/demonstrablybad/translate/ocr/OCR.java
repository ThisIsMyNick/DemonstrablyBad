package io.github.demonstrablybad.translate.ocr;

import android.os.Environment;

import com.googlecode.tesseract.android.TessBaseAPI;

import java.io.File;
import java.io.IOException;
import java.util.Formatter;

import io.github.demonstrablybad.translate.utils.DownloadFileAsyncTask;
import io.github.demonstrablybad.translate.utils.WebUtils;

/**
 * Created by James on 1/24/16.
 */
public class OCR {

    public final static String EXTERNAL_APP_DIRECTORY = "DemonstrablyBad";
    public final static String OCR_DATA_DIRECTORY = EXTERNAL_APP_DIRECTORY + "/" + "tessdata";
    public final static String IMAGE_DIRECTORY = EXTERNAL_APP_DIRECTORY + "/" + "images";
    public final static String DOWNLOAD_TRAINED_URL = "https://github.com/tesseract-ocr/tessdata/raw/master/%s.traineddata";


    private TessBaseAPI tessAPI;

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

}
