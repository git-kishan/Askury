package com.droid.solver.askapp.Answer;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.emoji.widget.EmojiEditText;
import androidx.emoji.widget.EmojiTextView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.core.content.res.ResourcesCompat;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.cardview.widget.CardView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.droid.solver.askapp.Main.Constants;
import com.droid.solver.askapp.R;
import com.droid.solver.askapp.SignInActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.firestore.WriteBatch;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import steelkiwi.com.library.DotsLoaderView;


public class AnswerActivity extends AppCompatActivity implements View.OnClickListener, TextWatcher {

    private static final int PERMISSIONS_REQUEST_READ_STORAGE =1705 ;
    private static final int PHOTO_PICKER_REQUEST_CODE =601 ;
    private static final int CAMERE_REQUEST_CODE=1565;
    private TextView fontTextView;
    public  int [] fontId=new int[]{R.font.open_sans,R.font.abril_fatface,R.font.aclonica,R.font.bubbler_one,R.font.bitter,R.font.geo};
    int textViewFontSelectedPosition=0;
    int fontSelected=0;
    int editTextFontSelectedPosition;
    private EmojiEditText answerEditText;
    private ImageView nextButtonImage,crossButtonImage,attachImage;
    private byte [] compressedByteArray=null;
    private String currentPhotoPath;
    private DotsLoaderView dotsLoaderView;
    private FrameLayout overlayLayout;
    private FirebaseAuth auth;
    private FirebaseUser user;
    private FirebaseFirestore firestoreRef;
    private StorageReference rootStorageRef;
    private String askerUid,questionId,question,askerName,askerImageUrl,askerBio;
    private long timeOfAsking;
    private ArrayList<String> questionType;
    private boolean anonymous;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);
        Intent intent=getIntent();
        askerUid=intent.getStringExtra("askerUid");
        questionId=intent.getStringExtra("questionId");
        question=intent.getStringExtra("question");
        timeOfAsking=intent.getLongExtra("timeOfAsking", System.currentTimeMillis());
        askerName=intent.getStringExtra("askerName");
        askerImageUrl=intent.getStringExtra("askerImageUrl");
        askerBio=intent.getStringExtra("askerBio");
        anonymous=intent.getBooleanExtra("anonymous", false);
        questionType=intent.getStringArrayListExtra("questionType");
        fontTextView = findViewById(R.id.font_text);
        CardView fontCardView=findViewById(R.id.cardView10);
        answerEditText=findViewById(R.id.answer_edit_text);
        nextButtonImage=findViewById(R.id.imageView13);
        EmojiTextView questionTextView=findViewById(R.id.textView14);
        answerEditText.addTextChangedListener(this);
        crossButtonImage=findViewById(R.id.imageView10);
        attachImage=findViewById(R.id.imageView14);
        attachImage.setVisibility(View.GONE);
        crossButtonImage.setVisibility(View.GONE);
        CardView galleryCardView=findViewById(R.id.cardView12);
        CardView cameraCardView=findViewById(R.id.cardView13);
        overlayLayout=findViewById(R.id.overlay_frame_layout);
        dotsLoaderView=findViewById(R.id.dotsLoaderView);
        dotsLoaderView.setVisibility(View.GONE);
        overlayLayout.setVisibility(View.GONE);
        Typeface typeface=ResourcesCompat.getFont(this, fontId[1]);
        fontTextView.setTypeface(typeface);
        galleryCardView.setOnClickListener(this);
        cameraCardView.setOnClickListener(this);
        fontCardView.setOnClickListener(this);
        nextButtonImage.setOnClickListener(this);
        crossButtonImage.setOnClickListener(this);
         questionTextView.setText(question);
        auth=FirebaseAuth.getInstance();
        user=auth.getCurrentUser();
        if(user==null){
            startActivity(new Intent(this, SignInActivity.class));
            finish();
        }
        firestoreRef=FirebaseFirestore.getInstance();
        rootStorageRef= FirebaseStorage.getInstance().getReference();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.cardView10:
                textViewFontSelectedPosition++;
                editTextFontSelectedPosition=textViewFontSelectedPosition-1;
               if(textViewFontSelectedPosition>fontId.length-1)
                   textViewFontSelectedPosition=0;
               if(editTextFontSelectedPosition>fontId.length-1)
                   editTextFontSelectedPosition=0;
                swipefont();
                break;
            case R.id.imageView10:
                attachImage.animate().translationX(2*attachImage.getWidth()).setDuration(500).start();
                crossButtonImage.animate().alpha(0).setDuration(300).start();
                compressedByteArray=null;
                break;
            case R.id.cardView12:
                attachImage.animate().translationX(2*attachImage.getWidth()).setDuration(500).start();
                checkPermission(true,false);
                break;
            case R.id.cardView13:
                checkPermission(false, true);
                break;
            case R.id.imageView13:
                if(checkValidation()){
                    if(isNetworkAvailable()){
                        String uid=auth.getUid();
                        if(askerUid!=null&&uid!=null)
                        if (uid.equals(askerUid)) {
                            overlayLayout.setVisibility(View.GONE);
                            dotsLoaderView.setVisibility(View.GONE);
                            showDialogMessage("You can't answer to your own question");
                            return;
                        }
                        overlayLayout.setVisibility(View.VISIBLE);
                        dotsLoaderView.setVisibility(View.VISIBLE);
                        dotsLoaderView.show();
                        uploadImageToStorage();
                    }else {
                        Toast.makeText(this, "No Internet connection", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }
    }

    private void swipefont(){
        Typeface answerFont=ResourcesCompat.getFont(this, fontId[editTextFontSelectedPosition]);
        Typeface textViewFont=ResourcesCompat.getFont(this, fontId[textViewFontSelectedPosition]);
        answerEditText.setTypeface(answerFont);
        fontSelected=editTextFontSelectedPosition;
        Log.i("TAG","font selected position :- "+fontSelected);
        fontTextView.setTypeface(textViewFont);

    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        int  length=answerEditText.getText().toString().trim().length();
        if(length==0)
            nextButtonImage.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_next_darker_fader, null));
        else if(length>=0)
            nextButtonImage.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_right_arrow_dark, null));

    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    private void checkPermission(boolean openGallery,boolean openCamera){

        if(Build.VERSION.SDK_INT>Build.VERSION_CODES.LOLLIPOP){

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED||
           ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)!=PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {

                //show permission rationale
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.CAMERA},
                        PERMISSIONS_REQUEST_READ_STORAGE);

            }
        } else {
            if (openGallery)
                selectImageFromGallery();
            if (openCamera)
                onCameraClicked();
        }
        }
        else {
            if (openGallery)
                selectImageFromGallery();
            if (openCamera)
                onCameraClicked();
        }
    }

    private void selectImageFromGallery(){
        Intent photoPickerIntent=new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        if(photoPickerIntent.resolveActivity(getPackageManager())!=null){
            startActivityForResult(photoPickerIntent, PHOTO_PICKER_REQUEST_CODE);
        }else {
            Toast.makeText(this, "No camera app present", Toast.LENGTH_SHORT).show();
        }
    }
    private void onCameraClicked(){

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            }
            catch (IOException ex) {
                //exception occurs in making new file for image
            }
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, CAMERE_REQUEST_CODE);

            }
        }

    }
    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir
        );
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==PHOTO_PICKER_REQUEST_CODE&&resultCode==RESULT_OK){
            resizeImageSelectedFromGallery(data);
        }
        else if(requestCode==CAMERE_REQUEST_CODE&&resultCode==RESULT_OK){
            addPicInGallery();
            resizeImageCapturedFromCamera();
        }
    }

    private void resizeImageCapturedFromCamera(){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(currentPhotoPath, options);
        int capturedImageWidth = options.outWidth;
        int capturedImageHeight = options.outHeight;
        int scaleFactor = Math.min(capturedImageWidth/250, capturedImageHeight/250);
        options.inJustDecodeBounds = false;
        options.inSampleSize = scaleFactor;
        Bitmap bitmap = BitmapFactory.decodeFile(currentPhotoPath, options);
        ByteArrayOutputStream stream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        compressedByteArray=stream.toByteArray();
        Log.i("TAG", "compressed byte array size :- "+compressedByteArray.length);
        final Bitmap mbitmap=BitmapFactory.decodeByteArray(compressedByteArray, 0, compressedByteArray.length);
        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                attachImage.setVisibility(View.VISIBLE);
                attachImage.animate().translationX(0).setDuration(500).start();
                attachImage.setImageBitmap(mbitmap);
                crossButtonImage.animate().alpha(1).setDuration(500).start();
                crossButtonImage.setVisibility(View.VISIBLE);


            }
        }, 100);


    }

    private void resizeImageSelectedFromGallery(@Nullable Intent data){
        if(data!= null) {
            final Uri imageUri = data.getData();
            Bitmap compressedBitmap = decodeSelectedImageUri(imageUri);
            if (compressedBitmap != null) {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                compressedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                compressedByteArray = byteArrayOutputStream.toByteArray();
                Log.i("TAG", "compressed byte array size :- " + compressedByteArray.length);
                final Bitmap bitmap = BitmapFactory.decodeByteArray(compressedByteArray, 0, compressedByteArray.length);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        attachImage.setVisibility(View.VISIBLE);
                        attachImage.animate().translationX(0).setDuration(500).start();
                        attachImage.setImageBitmap(bitmap);
                        crossButtonImage.animate().alpha(1).setDuration(500).start();
                        crossButtonImage.setVisibility(View.VISIBLE);


                    }
                }, 100);

            }
        }
    }

    private  Bitmap decodeSelectedImageUri(Uri uri){
        try {
            final BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            final InputStream imageStream = getContentResolver().openInputStream(uri);
            BitmapFactory.decodeStream(imageStream, null, options);
            options.inSampleSize=calculateInSampleSize(options, 250, 250);
            options.inJustDecodeBounds=false;
            return BitmapFactory.decodeStream(getContentResolver().openInputStream(uri), null, options);
        }catch (FileNotFoundException e){
            Toast.makeText(this, "Unknown error", Toast.LENGTH_SHORT).show();

        }
        return null;
    }

    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
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
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==PERMISSIONS_REQUEST_READ_STORAGE){
            if(grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(this, "Permission denied", Toast.LENGTH_LONG).show();
                //permission denied
            }
        }
    }

    private void addPicInGallery() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(currentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
    }

    private boolean checkValidation(){
        int length=answerEditText.getText().toString().trim().length();
        if(length==0){
            Toast.makeText(this, "Write your answer", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
    private boolean isNetworkAvailable(){
        ConnectivityManager cmm= (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        if(cmm!=null) {
            NetworkInfo activeNetworkInfo = cmm.getActiveNetworkInfo();
            return activeNetworkInfo != null && activeNetworkInfo.isConnected();
        }return false;

    }
    private void showDialogMessage(String message){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setMessage(message)
                .setCancelable(true)
                .setPositiveButton("got it", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Handler handler=new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                onBackPressed();
                            }
                        }, 300);
                    }
                });

        AlertDialog dialog=builder.create();
        dialog.show();

    }

    private void uploadImageToStorage(){
        if(compressedByteArray!=null) {
            final String uid = user.getUid();
            String currentTime = String.valueOf(System.currentTimeMillis());
            String imageName = uid + "@" + currentTime;
            final StorageReference answerImageRef = rootStorageRef.child("question").child(imageName);
            UploadTask uploadTask = answerImageRef.putBytes(compressedByteArray);
            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    answerImageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
//                            uploadAnswerToRemoteDatabase(uri.toString());
                            checkIfAnswerAlreadyExist(uri.toString());
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    overlayLayout.setVisibility(View.GONE);
                    dotsLoaderView.setVisibility(View.GONE);
                    dotsLoaderView.hide();
                    //show some error message
                    Log.i("TAG", "exception occur in uploading images");
                }
            });
        }else {
//            uploadAnswerToRemoteDatabase(null);
            checkIfAnswerAlreadyExist(null);
        }

    }

    private void checkIfAnswerAlreadyExist(final String imageUrl){
        user=auth.getCurrentUser();
        Log.i("TAG", "inside check function");

        Query query=firestoreRef.collection("user").document(askerUid).collection("question").
                document(questionId).collection("answer").whereEqualTo("answererId", user.getUid());
        query.get().addOnCompleteListener(this, new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    Log.i("TAG", "before loop :- ");
                    int counter=0;
                    String answerId=null;
                    if(task.getResult()!=null)
                    for (QueryDocumentSnapshot snapshot : task.getResult()) {
                        answerId=snapshot.getId();
                        counter++;
                    }
                    if(counter==0){
                        uploadAnswerToRemoteDatabase(imageUrl, null);

                    }else if(counter>0){
                        uploadAnswerToRemoteDatabase(imageUrl, answerId);
                    }
                }else {
                    Log.i("TAG", "task failed:- ");

                }
            }
        });

    }

    private void uploadAnswerToRemoteDatabase(@Nullable String answerImageUrl, String previousAnswerId){
        String uid=auth.getUid();
        SharedPreferences preferences=getSharedPreferences(Constants.PREFERENCE_NAME, MODE_PRIVATE);
        String answererName=preferences.getString(Constants.userName, null);
        String answererImageUrl=preferences.getString(Constants.LOW_IMAGE_URL, null);
        String answer=answerEditText.getText().toString();
        String answererBio=preferences.getString(Constants.bio, null);
        final boolean imageAttached=compressedByteArray!=null;
        String questionId=this.questionId;
        DocumentReference userAnswerRef;
        DocumentReference questionAnswer;
        DocumentReference rootQuestionRef;
        DocumentReference userQuestionRef;

        if(previousAnswerId==null&&uid!=null){

            String answerId=firestoreRef.collection("user").document(uid).collection("answer").document().getId();

            UserAnswerModel userAnswerModel=new UserAnswerModel(
                    askerUid, askerName, askerImageUrl, askerBio,
                    questionId, question, questionType, timeOfAsking,
                    System.currentTimeMillis(), answerId, uid,false, answer,
                    imageAttached, answerImageUrl, fontSelected, 0,anonymous);

            QuestionAnswerModel questionAnswerModel=new QuestionAnswerModel(
                    askerUid, askerName, askerBio, askerImageUrl, questionType,
                    questionId, question, timeOfAsking, System.currentTimeMillis(),
                    uid, answererName, answererImageUrl, answererBio,
                    false, answerId, answer, imageAttached, answerImageUrl,
                    true, fontSelected, false, 0,anonymous);

            Map<String, Object> map = new HashMap<>();
            map.put("recentAnswererId", uid);
            assert answererImageUrl != null;
            map.put("recentAnswererImageUrlLow",answererImageUrl);
            assert answererName != null;
            map.put("recentAnswererName", answererName);
            assert answererBio != null;
            map.put("recentAnswererBio", answererBio);
            map.put("recentAnswerId", answerId);
            map.put("recentAnswer", answer);
            map.put("recentAnswerImageAttached", imageAttached);
            assert answerImageUrl != null;
            map.put("recentAnswerImageUrl", answerImageUrl);
            map.put("answerCount", FieldValue.increment(1));
            map.put("recentAnswerLikeCount", 0);
            map.put("timeOfAnswering",System.currentTimeMillis());
            map.put("fontUsed", fontSelected);

            userAnswerRef=firestoreRef.collection("user").
                    document(uid).collection("answer").document(answerId);

            questionAnswer = firestoreRef.collection("user").document(askerUid)
                    .collection("question").document(questionId).collection("answer").document(answerId);

             rootQuestionRef=firestoreRef.collection("question").document(questionId);

             userQuestionRef=firestoreRef.collection("user").document(askerUid)
                    .collection("question").document(questionId);

            Map<String,Object > tempMap=new HashMap<>();
            tempMap.put("answerCount", FieldValue.increment(1));

            WriteBatch writeBatch = firestoreRef.batch();

            writeBatch.set(userAnswerRef, userAnswerModel);
            writeBatch.set(questionAnswer, questionAnswerModel);
            writeBatch.set(rootQuestionRef, map, SetOptions.merge());
            writeBatch.update(userQuestionRef, tempMap);
            writeBatch.commit().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    overlayLayout.setVisibility(View.GONE);
                    dotsLoaderView.hide();
                    dotsLoaderView.setVisibility(View.GONE);
                    answerEditText.setText("");
                    attachImage.setImageBitmap(null);
                    Log.i("TAG","successfully commited batch" );
                    showDialogMessage("Answered successfully \n\nThank you ");
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    overlayLayout.setVisibility(View.GONE);
                    dotsLoaderView.hide();
                    dotsLoaderView.setVisibility(View.GONE);
                    //show some error message
                    Log.i("TAG", "unsuccessful write");
                }
            });

        }
        else {

            UserAnswerModel userAnswerModel=new UserAnswerModel(
                    askerUid, askerName, askerImageUrl, askerBio,
                    questionId, question, questionType, timeOfAsking,
                    System.currentTimeMillis(), previousAnswerId, uid,false, answer,
                    imageAttached, answerImageUrl, fontSelected, 0,anonymous);

            QuestionAnswerModel questionAnswerModel=new QuestionAnswerModel(
                    askerUid, askerName, askerBio, askerImageUrl, questionType,
                    questionId, question, timeOfAsking, System.currentTimeMillis(),
                    uid, answererName, answererImageUrl, answererBio,
                    false, previousAnswerId, answer, imageAttached,answerImageUrl ,
                    true,
                    fontSelected, false, 0,anonymous);

            Map<String, Object> map = new HashMap<>();
            assert uid != null;
            map.put("recentAnswererId", uid);
            assert answererImageUrl != null;
            map.put("recentAnswererImageUrlLow",answererImageUrl);
            assert answererName != null;
            map.put("recentAnswererName", answererName);
            assert answererBio != null;
            map.put("recentAnswererBio", answererBio);
            map.put("recentAnswerId", previousAnswerId);
            map.put("recentAnswer", answer);
            map.put("recentAnswerImageAttached", imageAttached);
            assert answerImageUrl != null;
            map.put("recentAnswerImageUrl", answerImageUrl);
            map.put("recentAnswerLikeCount", 0);
            map.put("fontUsed", fontSelected);


            userAnswerRef=firestoreRef.collection("user").
                    document(uid).collection("answer").document(previousAnswerId);

            questionAnswer = firestoreRef.collection("user").document(askerUid)
                    .collection("question").document(questionId).collection("answer").document(previousAnswerId);

            rootQuestionRef=firestoreRef.collection("question").document(questionId);

            WriteBatch writeBatch = firestoreRef.batch();

            writeBatch.set(userAnswerRef, userAnswerModel);
            writeBatch.set(questionAnswer, questionAnswerModel);
            writeBatch.set(rootQuestionRef, map, SetOptions.merge());

            writeBatch.commit().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    overlayLayout.setVisibility(View.GONE);
                    dotsLoaderView.hide();
                    dotsLoaderView.setVisibility(View.GONE);
                    answerEditText.setText("");
                    attachImage.setImageBitmap(null);
                    Log.i("TAG","successfully commited batch" );
                    showDialogMessage("Answered successfully \n\nThank you ");
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    overlayLayout.setVisibility(View.GONE);
                    dotsLoaderView.hide();
                    dotsLoaderView.setVisibility(View.GONE);
                    //show some error message
                    Log.i("TAG", "unsuccessful write");
                }
            });
        }
        }

}
