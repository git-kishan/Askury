package com.droid.solver.askapp.ImagePoll;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class BitmapCompression extends AsyncTask<Void,Void,Void> {

    private Bitmap bitmap;
    private Context context;
    private int showingImageHeight,showingImageWidth;
    private int uploderImageHeight,uploaderImageWidth;
    private String encodedString=null;
    private Uri imageUri;
    private BitmapCompressor compressor;
    BitmapCompression(Context context,Uri imageUri,int showingImageWidth,int showingImageHeight){
        this.context=context;
        this.showingImageHeight=showingImageHeight;
        this.showingImageWidth=showingImageWidth;
        this.imageUri=imageUri;
        compressor= (BitmapCompressor) context;

    }
    @Override
    protected Void doInBackground(Void... voids) {
        bitmap=decodeSelectedImageUri(imageUri, showingImageWidth, showingImageHeight);
        if(bitmap!=null){
            Bitmap compressedBitmap = Bitmap.createScaledBitmap(bitmap, showingImageWidth, showingImageHeight, false);
            ByteArrayOutputStream showImageOutputStream = new ByteArrayOutputStream();
            compressedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, showImageOutputStream);

            Bitmap uploaderBitmap=Bitmap.createScaledBitmap(bitmap, 500, 500,false );
            ByteArrayOutputStream uploaderBitmapStream=new ByteArrayOutputStream();
            uploaderBitmap.compress(Bitmap.CompressFormat.JPEG, 100, uploaderBitmapStream);
            byte [] uploaderByteArray=uploaderBitmapStream.toByteArray();
            encodedString= Base64.encodeToString(uploaderByteArray, Base64.DEFAULT);
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        compressor.onBitmapCompressed(bitmap, encodedString);
    }

    private  Bitmap decodeSelectedImageUri(Uri uri, int requiredWidth, int requiredHeight){
        try {
            final BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            final InputStream imageStream = context.getContentResolver().openInputStream(uri);
            BitmapFactory.decodeStream(imageStream, null, options);
            int selectedImageHeight = options.outHeight;
            int selectedImageWidth = options.outWidth;
            String imageType = options.outMimeType;
            Log.i("TAG", "bitmap height :- " + selectedImageHeight);
            Log.i("TAG", "bitmap width :- " + selectedImageWidth);
            Log.i("TAG", "bitmap mime type   :- " + imageType);

            options.inSampleSize=calculateInSampleSize(options, requiredWidth, requiredHeight);
            options.inJustDecodeBounds=false;
            return BitmapFactory.decodeStream(context.getContentResolver().openInputStream(uri), null, options);
        }catch (FileNotFoundException e){
            return null;
        }

    }
    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

}
interface BitmapCompressor{
    void onBitmapCompressed(Bitmap bitmap,String encodedString);
}
