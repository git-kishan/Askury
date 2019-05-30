package com.droid.solver.askapp.Main;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ImageUrlToByteConvertor  extends AsyncTask<String, Void, Bitmap> {
    private Context context;
    private ProfilePicSaver picSaver;
    private boolean lowProfilePic,highProfilePic;
     ImageUrlToByteConvertor(Context context,boolean lowProfilePic,boolean highProfilePic){
         this.context=context;
         picSaver= (ProfilePicSaver) context;
         this.lowProfilePic=lowProfilePic;
         this.highProfilePic=highProfilePic;
     }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }


    @Override
    protected Bitmap doInBackground(String... strings) {
        URL profilePicUrl=null;
        HttpURLConnection httpURLConnection=null;
        InputStream inputStream=null;
        BufferedInputStream bufferedInputStream=null;
        try {
            profilePicUrl=new URL(strings[0]);
            httpURLConnection= (HttpURLConnection) profilePicUrl.openConnection();
            httpURLConnection.connect();
            inputStream=httpURLConnection.getInputStream();
            bufferedInputStream=new BufferedInputStream(inputStream);
            Bitmap bitmap= BitmapFactory.decodeStream(bufferedInputStream);
            return bitmap;
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            httpURLConnection.disconnect();
            try {
                inputStream.close();
                bufferedInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
         picSaver.onResult(bitmap,lowProfilePic, highProfilePic);
        super.onPostExecute(bitmap);
    }
}
interface ProfilePicSaver{
    void onResult(Bitmap bitmap,boolean lowProfilePic,boolean highProfilePic);
}
