package io.github.demonstrablybad.translate.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.io.File;
import java.util.ArrayList;

import io.github.demonstrablybad.translate.R;
import io.github.demonstrablybad.translate.ocr.OCR;

public class OCRChooseLanguageFragment extends Fragment {

    Spinner ocrLanguageSpinner;

    public OCRChooseLanguageFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_ocrchoose_language, container, false);
        ocrLanguageSpinner = (Spinner) rootView.findViewById(R.id.ocr_choose_language);
        //ArrayAdapter<String> spinnerAdapter= new ArrayAdapter<String>(getActivity().getBaseContext(),
        //        android.R.layout.simple_list_item_1, OCR.getInstance().getLanguages());
        //spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //ocrLanguageSpinner.setAdapter(spinnerAdapter);
        return rootView;
    }
}
