package io.github.demonstrablybad.translate.utils;


import android.os.AsyncTask;
import android.util.Log;

public class DownloadFileAsyncTask extends AsyncTask<String, String, String> {

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... url) {
        try {
            WebUtils.downloadFile(url[0], url[1]);
        } catch (Exception e) {
            Log.e("Error: ", e.getMessage());
        }

        return null;
    }

    @Override
    protected void onPostExecute(String url) {
    }

}
