package io.github.demonstrablybad.translate.install;

import android.util.Log;

import java.io.File;

import io.github.demonstrablybad.translate.ocr.OCR;
import io.github.demonstrablybad.translate.utils.WebUtils;

public class Install {

    // Check if files are installed
    public static boolean isInstalled() {
        File tessDir = new File(OCR.getOCRDataDirectory());
        if (tessDir.exists()) {
            return true;
        }
        return false;
    }

    // To be run when the user runs the app for the first time
    public static void install() {
        new File(OCR.getTessDirectory()).mkdirs();
        new File(OCR.getOCRDataDirectory()).mkdirs();
        new File(OCR.getImageDirectory()).mkdirs();
        OCR.downloadLanguage("eng");
        OCR.downloadLanguage("spa");
    }
}
