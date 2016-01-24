package io.github.demonstrablybad.translate.ocr;

import android.os.Environment;

import com.googlecode.tesseract.android.TessBaseAPI;

import java.io.File;

/**
 * Created by James on 1/24/16.
 */
public class OCR {

    public final static String EXTERNAL_APP_DIRECTORY = "DemonstrablyBad";
    public final static String OCR_DATA_DIRECTORY = EXTERNAL_APP_DIRECTORY + "/" + "tessdata";
    public final static String IMAGE_DIRECTORY = EXTERNAL_APP_DIRECTORY + "/" + "images";

    private TessBaseAPI tessAPI;

    public static String getTessDir() {
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

    public void initTessAPI(String lang) {
        tessAPI = new TessBaseAPI();
        tessAPI.init(getTessDir(), lang);
    }

}
