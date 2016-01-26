package io.github.demonstrablybad.translate.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;

import io.github.demonstrablybad.translate.R;
import io.github.demonstrablybad.translate.ocr.OCR;
import io.github.demonstrablybad.translate.utils.WebUtils;

public class TranslateTextFragment extends Fragment {

    private Spinner sourceSpinner;
    private Spinner targetSpinner;
    private Button swap;
    private CardView inputCard;
    private CardView outputCard;
    private TextView translated;
    private EditText inputText;

    public TranslateTextFragment() {
        // Required empty public constructor
    }

    public static TranslateTextFragment newInstance(String input) {
        TranslateTextFragment fragment = new TranslateTextFragment();

        Bundle args = new Bundle();
        args.putString("input", input);

        fragment.setArguments(args);

        return fragment;
    }

    public void updateTranslation(View rootView) {

        String text = inputText.getText().toString();

        if (text.equals("")) {
            outputCard.setVisibility(View.GONE);
        } else {
            int sourcePosition = sourceSpinner.getSelectedItemPosition();
            int targetPosition = targetSpinner.getSelectedItemPosition();
            String source = OCR.SOURCE_LANGUAGES[sourcePosition];
            String target = OCR.TARGET_LANGUAGES[targetPosition];
            String trans = WebUtils.translate(text, source, target);
            translated.setText(trans);
            outputCard.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Allow for sending requests
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);

        final String[] sourceArray = getResources().getStringArray(R.array.source_array);

        final View rootView = inflater.inflate(R.layout.fragment_translate_text, container, false);

        inputCard = (CardView) rootView.findViewById(R.id.input_translation_card);
        outputCard = (CardView) rootView.findViewById(R.id.output_translation_card);
        translated = (TextView) rootView.findViewById(R.id.translated_text);
        inputText = (EditText) rootView.findViewById(R.id.input_text);

        if (getArguments() != null && getArguments().containsKey("input")) {
            inputText.setText(getArguments().getString("input"));
        }

        inputText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                updateTranslation(rootView);
            }
        });


        if (translated.getText() == "") {
            outputCard.setVisibility(View.GONE);
        }

        // Set the dropdown options for source and target languages
        sourceSpinner = (Spinner) rootView.findViewById(R.id.source_spinner);
        ArrayAdapter<CharSequence> sourceAdapter = ArrayAdapter.createFromResource(getActivity().getBaseContext(),
                R.array.source_array, android.R.layout.simple_spinner_item);
        sourceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sourceSpinner.setAdapter(sourceAdapter);

        targetSpinner = (Spinner) rootView.findViewById(R.id.target_spinner);
        ArrayAdapter<CharSequence> targetAdapter = ArrayAdapter.createFromResource(getActivity().getBaseContext(),
                R.array.target_array, android.R.layout.simple_spinner_item);
        targetAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        targetSpinner.setAdapter(targetAdapter);

        swap = (Button) rootView.findViewById(R.id.swap_button);
        swap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!swap.isEnabled()) {
                    return;
                }

                // Correct offsets due to the first item in source languages being "Detect Language"
                int sourcePosition = sourceSpinner.getSelectedItemPosition() - 1;
                int targetPosition = targetSpinner.getSelectedItemPosition() + 1;

                sourceSpinner.setSelection(targetPosition);
                targetSpinner.setSelection(sourcePosition);
                updateTranslation(rootView);
            }
        });

        sourceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (sourceArray[position].equals("Detect Language")) {
                    swap.setEnabled(false);
                    swap.setClickable(false);
                } else {
                    if (!swap.isEnabled()) {
                        swap.setEnabled(true);
                        swap.setClickable(true);
                    }
                }
                updateTranslation(rootView);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // Inflate the layout for this fragment
        return rootView;
    }
}
