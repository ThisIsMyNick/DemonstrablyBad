package io.github.demonstrablybad.translate.fragment;

import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import io.github.demonstrablybad.translate.R;
import io.github.demonstrablybad.translate.ocr.OCR;

public class OCRChooseLanguageFragment extends Fragment {

    private Spinner chooseLanguageSpinner;
    private ImageView preview;
    private EditText ocrResult;
    private Button translateButton;

    public OCRChooseLanguageFragment() {
        // Required empty public constructor
    }

    public static OCRChooseLanguageFragment newInstance(Bitmap bitmap) {
        OCRChooseLanguageFragment fragment = new OCRChooseLanguageFragment();

        Bundle args = new Bundle();

        // Compress bitmap into byte array so we can use it later on in onCreateView()
        ByteArrayOutputStream bs = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 50, bs);
        args.putByteArray("bitmap", bs.toByteArray());

        // Set the arguments so we can later call it with getArguments()
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_ocrchoose_language, container, false);

        byte[] compressed = getArguments().getByteArray("bitmap");

        final Bitmap bitmap = BitmapFactory.decodeByteArray(compressed, 0, compressed.length);
        preview = (ImageView) rootView.findViewById(R.id.choose_language_imageview);
        preview.setImageBitmap(bitmap);

        ocrResult = (EditText) rootView.findViewById(R.id.ocr_result);

        chooseLanguageSpinner = (Spinner) rootView.findViewById(R.id.choose_language_spinner);
        ArrayAdapter<String> chooseLanguageSpinnerAdapter = new ArrayAdapter<String>(getActivity().getBaseContext(), android.R.layout.simple_spinner_item, OCR.getInstance().getLanguages());
        chooseLanguageSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        chooseLanguageSpinner.setAdapter(chooseLanguageSpinnerAdapter);

        chooseLanguageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String language = parent.getItemAtPosition(position).toString();
                String languageCode = OCR.getInstance().getLanguageCodeFromLanguage(language);
                OCR.getInstance().initTessAPI(languageCode);
                String result = OCR.getInstance().getText(bitmap);
                ocrResult.setText(result);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        translateButton = (Button) rootView.findViewById(R.id.translate_button);
        translateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = ocrResult.getText().toString();
                Fragment fragment;
                try {
                    fragment = (Fragment) TranslateTextFragment.newInstance(input);
                } catch (Exception e) {
                    e.printStackTrace();
                    return;
                }

                // Insert the fragment by replacing any existing fragment
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.fragments, fragment).commit();
            }
        });

        return rootView;
    }
}
