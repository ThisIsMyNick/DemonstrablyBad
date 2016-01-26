package io.github.demonstrablybad.translate.fragment;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.renderscript.ScriptGroup;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

import io.github.demonstrablybad.translate.activity.MainActivity;
import io.github.demonstrablybad.translate.R;
import io.github.demonstrablybad.translate.ocr.OCR;

public class TranslatePictureFragment extends Fragment {

    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 0;
    private static final int UPLOAD_IMAGE_ACTIVITY_REQUEST_CODE = 1;
    private Button takePictureButton;
    private Button uploadPictureButton;

    public TranslatePictureFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_translate_picture,
                container, false);

        takePictureButton = (Button) rootView.findViewById(R.id.take_picture);
        takePictureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
            }
        });

        uploadPictureButton = (Button) rootView.findViewById(R.id.choose_picture);
        uploadPictureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, UPLOAD_IMAGE_ACTIVITY_REQUEST_CODE);
            }
        });

        // Inflate the layout for this fragment
        return rootView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        OCR ocr = new OCR();
        ocr.initTessAPI("eng");
        Bitmap imageBitmap = null;
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == MainActivity.RESULT_OK) {
            Bundle extras = data.getExtras();
            imageBitmap = (Bitmap) extras.get("data");
        } else if (requestCode == UPLOAD_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == MainActivity.RESULT_OK) {

            // Retrieve image from gallery intent
            InputStream stream;
            try {
                stream = getActivity().getContentResolver().openInputStream(data.getData());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return;
            }

            imageBitmap = BitmapFactory.decodeStream(stream);
        } else {
            return;
        }
        Fragment fragment;
        try {
            fragment = (Fragment) OCRChooseLanguageFragment.newInstance(imageBitmap);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragments, fragment).commit();

    }
}