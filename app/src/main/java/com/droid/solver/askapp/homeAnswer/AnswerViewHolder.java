package com.droid.solver.askapp.homeAnswer;

import androidx.annotation.NonNull;
import androidx.emoji.widget.EmojiTextView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.droid.solver.askapp.R;
import com.like.LikeButton;
import de.hdodenhof.circleimageview.CircleImageView;

class AnswerViewHolder extends RecyclerView.ViewHolder {

    CircleImageView profileImage;
    EmojiTextView answererName,answererBio,answer;
    TextView likeCount,timeAgo;
    LikeButton likeButton;
    ImageView answerImage;
     AnswerViewHolder(@NonNull View itemView) {
        super(itemView);
        profileImage=itemView.findViewById(R.id.circleImageView2);
        answererName=itemView.findViewById(R.id.textView15);
        answererBio=itemView.findViewById(R.id.textView16);
        answer=itemView.findViewById(R.id.textView1);
        likeCount=itemView.findViewById(R.id.textView25);
        timeAgo=itemView.findViewById(R.id.textView27);
        likeButton=itemView.findViewById(R.id.likeButton);
        answerImage=itemView.findViewById(R.id.imageView19);

    }
//    void onLikeClicked(final Context context,final AnswerModel model){
//
//        FirebaseFirestore firestoreRootRef=FirebaseFirestore.getInstance();
//
//        SharedPreferences preferences=context.getSharedPreferences(Constants.PREFERENCE_NAME, Context.MODE_PRIVATE);
//        final String likerId=preferences.getString(Constants.userId, null);
//        String likerName=preferences.getString(Constants.userName, null);
//        String likerImageUrl=preferences.getString(Constants.profilePicLowUrl, null);
//        String likerBio=preferences.getString(Constants.bio, null);
//        String questionId=model.getQuestionId();
//        String askerId=model.getAskerId();
//        String answerId=model.getAnswerId();
//        String answererId=model.getAnswererId();
//        AnswerLikeModel answerLikeModel=new AnswerLikeModel(likerId, likerName, likerImageUrl, likerBio,questionId,askerId,answerId,answererId);
//
//        if(likerId!=null&&model.getAskerId()!=null&&model.getAnswerId()!=null&&model.getQuestionId()!=null) {
//
//            Map<String, Object> likeMap = new HashMap<>();
//            likeMap.put("answerLikeCount", FieldValue.increment(1));
//
//            Map<String, Object> rootLikeMap = new HashMap<>();
//            rootLikeMap.put("recentAnswerLikeCount", FieldValue.increment(1));
//
//
//            DocumentReference likerReference = firestoreRootRef.collection("user").document(model.getAskerId())
//                    .collection("question").document(model.getQuestionId()).collection("answer")
//                    .document(model.getAnswerId()).collection("like").document(likerId);
//
//            DocumentReference questionAnswerModelRef = firestoreRootRef.collection("user").document(model.getAskerId())
//                    .collection("question").document(model.getQuestionId()).collection("answer")
//                    .document(model.getAnswerId());
//
//            DocumentReference userAnswerModelRef = firestoreRootRef.collection("user").document(model.getAnswerId())
//                    .collection("answer").document(model.getAnswerId());
//
//            DocumentReference rootQuestionModelRef = firestoreRootRef.collection("question").document(model.getQuestionId());
//
//            DocumentReference userAnswerLikeRef = firestoreRootRef.collection("user").document(likerId).
//                    collection("answerLike").document("like");
//
//            Map<String, Object> userAnswerLikeMap = new HashMap<>();
//            userAnswerLikeMap.put("answerLikeId", FieldValue.arrayUnion(model.getAnswerId()));
//
//            WriteBatch writeBatch = firestoreRootRef.batch();
//            writeBatch.set(likerReference, answerLikeModel);
//            writeBatch.update(questionAnswerModelRef, likeMap);
//            writeBatch.update(userAnswerModelRef, likeMap);
//            writeBatch.update(rootQuestionModelRef, rootLikeMap);
//            writeBatch.update(userAnswerLikeRef, userAnswerLikeMap);
//
//            writeBatch.commit().addOnCompleteListener(new OnCompleteListener<Void>() {
//                @Override
//                public void onComplete(@NonNull Task<Void> task) {
//
//                    if (likerId.equals(model.getAskerId())) {
//
//                    }
//                    Log.i("TAG", "successfully like");
//                }
//            });
//            int count = Integer.parseInt(likeCount.getText().toString()) + 1;
//            likeCount.setText(String.valueOf(count));
//
//            if (MainActivity.answerLikeList != null) {
//                MainActivity.answerLikeList.add(model.getAnswerId());
//            }
//            AsyncTask.execute(new Runnable() {
//                @Override
//                public void run() {
//                    LocalDatabase database = new LocalDatabase(context.getApplicationContext());
//                    database.insertSingleAnswerLikeModel(model.getAnswerId());
//                }
//            });
//
//        }
//
//
//    }
//    void onDislikedClicked(final Context context,final AnswerModel model){
//
//         FirebaseFirestore firestoreRootRef=FirebaseFirestore.getInstance();
//         SharedPreferences preferences=context.getSharedPreferences(Constants.PREFERENCE_NAME,Context.MODE_PRIVATE);
//         String likerId=preferences.getString(Constants.userId, null);
//
//         if(likerId!=null&&model.getAnswerId()!=null&&model.getQuestionId()!=null&&model.getAskerId()!=null) {
//             Map<String, Object> likeMap = new HashMap<>();
//             likeMap.put("answerLikeCount", FieldValue.increment(-1));
//
//             Map<String, Object> rootLikeMap = new HashMap<>();
//             rootLikeMap.put("recentAnswerLikeCount", FieldValue.increment(-1));
//
//             DocumentReference likerReference = firestoreRootRef.collection("user").document(model.getAskerId())
//                     .collection("question").document(model.getQuestionId()).collection("answer")
//                     .document(model.getAnswerId()).collection("like").document(likerId);
//
//             DocumentReference questionAnswerModelRef = firestoreRootRef.collection("user").document(model.getAskerId())
//                     .collection("question").document(model.getQuestionId()).collection("answer")
//                     .document(model.getAnswerId());
//
//             DocumentReference userAnswerModelRef = firestoreRootRef.collection("user").document(model.getAnswerId())
//                     .collection("answer").document(model.getAnswerId());
//
//             DocumentReference rootQuestionModelRef = firestoreRootRef.collection("question").document(model.getQuestionId());
//
//             DocumentReference userAnswerLikeRef = firestoreRootRef.collection("user").document(likerId).
//                     collection("answerLike").document("like");
//
//             Map<String, Object> userAnswerLikeMap = new HashMap<>();
//             userAnswerLikeMap.put("answerLikeId", FieldValue.arrayRemove(model.getAnswerId()));
//
//             WriteBatch writeBatch = firestoreRootRef.batch();
//             writeBatch.delete(likerReference);
//             writeBatch.update(questionAnswerModelRef, likeMap);
//             writeBatch.update(userAnswerModelRef, likeMap);
//             writeBatch.update(rootQuestionModelRef, rootLikeMap);
//             writeBatch.update(userAnswerLikeRef, userAnswerLikeMap);
//
//             writeBatch.commit().addOnCompleteListener(new OnCompleteListener<Void>() {
//                 @Override
//                 public void onComplete(@NonNull Task<Void> task) {
//                     Log.i("TAG", "successfully dislike ");
//                 }
//             });
//             int count = Integer.parseInt(likeCount.getText().toString()) - 1;
//             likeCount.setText(String.valueOf(count));
//
//             final LocalDatabase database = new LocalDatabase(context.getApplicationContext());
//             database.removeAnswerLikeModel(model.getAnswerId());
//
//             if (MainActivity.answerLikeList != null && MainActivity.answerLikeList.size() > 0) {
//                 MainActivity.answerLikeList.remove(model.getAnswererId());
//
//             }
//         }
//    }
}
