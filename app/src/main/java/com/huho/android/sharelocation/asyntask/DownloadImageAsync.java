package com.huho.android.sharelocation.asyntask;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import com.huho.android.sharelocation.ShareLocationApplication;
import com.huho.android.sharelocation.interfaces.IAsynTaskDelegate;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * Created by sev_user on 4/25/2016.
 */
public class DownloadImageAsync extends AsyncTask<String,Void,String> {
    private IAsynTaskDelegate interfa;
    public DownloadImageAsync(IAsynTaskDelegate anInterface) {
        this.interfa = anInterface;
    }


    @Override
    protected String doInBackground(String... params) {
        String url = params[0];
        String path = null;
        final DefaultHttpClient client = new DefaultHttpClient();
        // forming a HttoGet request
        final HttpGet getRequest = new HttpGet(url);
        try {

            HttpResponse response = client.execute(getRequest);
            final int statusCode = response.getStatusLine().getStatusCode();

            if (statusCode != HttpStatus.SC_OK) {
                Log.w("ImageDownloader", "Error " + statusCode
                        + " while retrieving bitmap from " + url);
                return null;

            }

            final HttpEntity entity = response.getEntity();
            if (entity != null) {
                InputStream inputStream = null;
                try {
                    // getting contents from the stream
                    inputStream = entity.getContent();
                    final Bitmap bitmap = BitmapFactory
                            .decodeStream(inputStream);
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 75, stream);
                    ContextWrapper cw = new ContextWrapper(ShareLocationApplication.getInstance());
                    // path to /data/data/yourapp/app_data/imageDir
                    File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
                    File file = new File(directory,System.currentTimeMillis()+".jpg");
                    file.createNewFile();
                    path = file.getPath();
                    FileOutputStream fos = new FileOutputStream(file);
                    fos.write(stream.toByteArray());
                    fos.close();

                } finally {
                    if (inputStream != null) {
                        inputStream.close();
                    }
                    entity.consumeContent();
                }
            }
        } catch (Exception e) {
            // You Could provide a more explicit error message for
            // IOException
            getRequest.abort();
            return null;
        }

        return path;
    }
    @Override
    protected void onPostExecute(String result) {
        // TODO Auto-generated method stub
        if (result != null) {
            this.interfa.didSuccessWithMessage(result);
        }
    }
    @Override
    protected void onPreExecute() {
        // TODO Auto-generated method stub
        super.onPreExecute();
    }
}
