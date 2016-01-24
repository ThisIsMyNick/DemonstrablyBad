package io.github.demonstrablybad.translate.install;

import java.io.File;

import io.github.demonstrablybad.translate.ocr.OCR;
import io.github.demonstrablybad.translate.utils.WebUtils;

public class Install {

    public static boolean isInstalled() {
        File tessDir = new File(OCR.getOCRDataDirectory());
        if (tessDir.exists()) {
            return true;
        }
        return false;
    }

    public static void install() {
        new File(OCR.getOCRDataDirectory()).mkdirs();
        new File(OCR.getImageDirectory()).mkdirs();
        new File(OCR.getTessDirectory()).mkdirs();
        OCR.downloadLanguage("eng");
    }
}
