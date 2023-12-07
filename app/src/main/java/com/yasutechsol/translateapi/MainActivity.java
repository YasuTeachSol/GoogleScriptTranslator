package com.yasutechsol.translateapi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.util.Log;

import com.yasutechsol.translator.GoogleScriptTranslator;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static final String SCRIPT_ID = "SCRIPT_ID";
    private static final String SOURCE_LANG = "en"; // Source language code
    private static final String TARGET_LANG = "am"; // Target language code

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String textToTranslate = "Hello, how are you?";

        GoogleScriptTranslator.translate(SCRIPT_ID, SOURCE_LANG, TARGET_LANG, textToTranslate, new GoogleScriptTranslator.TranslationCallback() {
            @Override
            public void onTranslationComplete(String result) {
                // Handle the translated text
                Log.d(TAG, "Translation Result: " + result);
            }

            @Override
            public void onTranslationError(String error) {
                // Handle the error
                Log.e(TAG, "Translation Error: " + error);
            }
        });
    }
}