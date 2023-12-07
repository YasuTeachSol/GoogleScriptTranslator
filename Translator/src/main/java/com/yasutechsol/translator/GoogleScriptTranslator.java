package com.yasutechsol.translator;


import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GoogleScriptTranslator {

    public interface TranslationCallback {
        void onTranslationComplete(String result);

        void onTranslationError(String error);
    }

    private static final Executor executor = Executors.newSingleThreadExecutor();

    public static void translate(String scriptId, String sourceLang, String targetLang, String textToTranslate, TranslationCallback callback) {
        new TranslationTask(scriptId, sourceLang, targetLang, textToTranslate, callback).executeOnExecutor(executor);
    }

    private static class TranslationTask extends AsyncTask<Void, Void, String> {

        private final String scriptId;
        private final String sourceLang;
        private final String targetLang;
        private final String textToTranslate;
        private final TranslationCallback callback;

        public TranslationTask(String scriptId, String sourceLang, String targetLang, String textToTranslate, TranslationCallback callback) {
            this.scriptId = scriptId;
            this.sourceLang = sourceLang;
            this.targetLang = targetLang;
            this.textToTranslate = textToTranslate;
            this.callback = callback;
        }

        @Override
        protected String doInBackground(Void... voids) {
            OkHttpClient client = new OkHttpClient();

            HttpUrl.Builder urlBuilder = HttpUrl.parse("https://script.google.com/macros/s/" + scriptId + "/exec").newBuilder();
            urlBuilder.addQueryParameter("langFrom", sourceLang);
            urlBuilder.addQueryParameter("langTo", targetLang);
            urlBuilder.addQueryParameter("text", textToTranslate);
            String finalUrl = urlBuilder.build().toString();

            Request request = new Request.Builder()
                    .url(finalUrl)
                    .build();

            try {
                Response response = client.newCall(request).execute();
                if (response.isSuccessful()) {
                    return response.body().string();
                } else {
                    return "Error: " + response.code() + " - " + response.message();
                }
            } catch (IOException e) {
                return "Error: " + e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String result) {
            // Check if the callback is not null before invoking methods
            if (callback != null) {
                try {
                    Log.d("Translation Response", result); // Log the raw response for debugging

                    // Check if the result is a valid JSON format
                    if (result.startsWith("{") && result.endsWith("}")) {
                        JSONObject jsonResult = new JSONObject(result);
                        String translatedText = jsonResult.optString("translatedText");

                        if (!TextUtils.isEmpty(translatedText)) {
                            callback.onTranslationComplete(translatedText);
                        } else {
                            callback.onTranslationError("Error: Translated text is empty or null");
                        }
                    } else {
                        // If not a valid JSON, treat the response as a plain string
                        callback.onTranslationComplete(result);
                    }
                } catch (JSONException e) {
                    callback.onTranslationError("Error: JSON parsing error - " + e.getMessage());
                } catch (Exception e) {
                    callback.onTranslationError("Error: " + e.getMessage());
                }
            }
        }
    }
}
