package com.droid.solver.askapp.Question;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.droid.solver.askapp.Account.ProfileImageActivity;
import com.droid.solver.askapp.GlideApp;
import com.droid.solver.askapp.Main.Constants;
import com.droid.solver.askapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class AskQuestionRecyclerAdapter extends RecyclerView.Adapter {

    private Context context;
    private ArrayList<RootQuestionModel> questionModelArrayList;
    private LayoutInflater inflater;
    public AskQuestionRecyclerAdapter(Context context, ArrayList<RootQuestionModel> questionModelArrayList){
     this.context=context;
     this.questionModelArrayList=questionModelArrayList;
     if(context!=null)
     inflater=LayoutInflater.from(context);

    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view=inflater.inflate(R.layout.question_question_item, viewGroup,false);
        return new AskQuestionViewHolderWithoutImage(view,context);


    }
    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final  int i) {


            boolean isAnonymous=questionModelArrayList.get(i).isAnonymous();
            String profileNameAsked="";
            if(isAnonymous){
                profileNameAsked=context.getString(R.string.someoneasked);
            }else {
                profileNameAsked=String.format(context.getString(R.string.user_name_asked), questionModelArrayList.get(i).getAskerName());

                String url= Constants.PROFILE_PICTURE+"/"+questionModelArrayList.get(i).getAskerId()+ProfileImageActivity.SMALL_THUMBNAIL;
                StorageReference reference= FirebaseStorage.getInstance().getReference().child(url);

                GlideApp.with(context).load(reference)
                        .placeholder(R.drawable.ic_placeholder).
                        into(((AskQuestionViewHolderWithoutImage) holder).profilePicture);
            }

            ((AskQuestionViewHolderWithoutImage) holder).profileName.setText(profileNameAsked);
            ((AskQuestionViewHolderWithoutImage) holder).question.setText(questionModelArrayList.get(i).getQuestion());
            long oldTime=questionModelArrayList.get(i).getTimeOfAsking();
            long currentTime=System.currentTimeMillis();
            String timeAgo=getTime(oldTime, currentTime);
            if(timeAgo==null)
                ((AskQuestionViewHolderWithoutImage) holder).timeAgo.setText("");
            else
                ((AskQuestionViewHolderWithoutImage) holder).timeAgo.setText(timeAgo);

            if(i==questionModelArrayList.size()-1){
                ((AskQuestionViewHolderWithoutImage) holder).view.setVisibility(View.GONE);
            }

        ((AskQuestionViewHolderWithoutImage) holder).cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String askerUid=questionModelArrayList.get(i).getAskerId();
                String questionId=questionModelArrayList.get(i).getQuestionId();
                String question=questionModelArrayList.get(i).getQuestion();
                long timeOfAsking=questionModelArrayList.get(i).getTimeOfAsking();
                String askerName=questionModelArrayList.get(i).getAskerName();
                String askerImageUrl=questionModelArrayList.get(i).getAskerImageUrlLow();
                String askerBio=questionModelArrayList.get(i).getAskerBio();
                ArrayList<String> questionType= (ArrayList<String>) questionModelArrayList.get(i).getQuestionType();

                ((AskQuestionViewHolderWithoutImage) holder).onCardClicked(context,i,
                        askerUid,questionId,question,timeOfAsking,askerName,askerImageUrl,askerBio,questionType);
            }
        });

    }
    @Override
    public int getItemCount() {
        return questionModelArrayList==null?0:questionModelArrayList.size();
    }

    private String getTimeDifferenceInWords(long diff){
        long yearInMillis=365*24*60*60*1000L;
        long dayInMillis=24*60*60*1000L;//86400000
        long hourInMillis=60*60*1000L;//3600000
        long minuteInMillis=60*1000L;//60000
        long secondInMillis=1000L;//1000
        if(diff/yearInMillis>0){
            long years=diff/yearInMillis;
            return ""+years+" years ago";
        }
        if(diff/dayInMillis>0){
            long day=diff/dayInMillis;
            return ""+day+" days ago";

        }else if(diff/hourInMillis>0){
            long hour =diff/hourInMillis;
            return ""+hour+" hrs ago";

        }else if(diff/minuteInMillis>0){

            long minutes=diff/minuteInMillis;
            return ""+minutes+" min ago";

        } else if(diff/secondInMillis>0){
            long seconds=diff/secondInMillis;
            return ""+seconds+" sec ago";
        }
        return "Not known";
    }
    public String getTime(long oldDate,long newDate){
        long diff=newDate-oldDate;//777600
        if (diff<0){
            return null;
        }
        return getTimeDifferenceInWords(diff);
    }

}
