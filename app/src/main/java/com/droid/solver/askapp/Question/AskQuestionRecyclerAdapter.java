package com.droid.solver.askapp.Question;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.droid.solver.askapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AskQuestionRecyclerAdapter extends RecyclerView.Adapter {

    private Context context;
    private ArrayList<AskQuestionModel> questionModelArrayList;
    private LayoutInflater inflater;
    public static final int QUESTION_WITHOUT_IMAGE=0;
    public static final int QUESTION_WITH_IMAGE=1;
    public AskQuestionRecyclerAdapter(Context context, ArrayList<AskQuestionModel> questionModelArrayList){
     this.context=context;
     this.questionModelArrayList=questionModelArrayList;
     inflater=LayoutInflater.from(context);

    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        View view=null;
        switch (viewType){
            case QUESTION_WITH_IMAGE:
                 view=inflater.inflate(R.layout.question_question_item_with_image, viewGroup,false);
                return new  AskQuestionViewHolderWithImage(view);
            case QUESTION_WITHOUT_IMAGE:
                 view=inflater.inflate(R.layout.question_question_item, viewGroup,false);
                 return new AskQuestionViewHolderWithoutImage(view);
        }
        return  null;

    }
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int i) {

        if(holder instanceof AskQuestionViewHolderWithImage){
            boolean isAnonymous=questionModelArrayList.get(i).isAnonymous();
            String profileNameAsked="";
            if(isAnonymous){
                profileNameAsked=context.getString(R.string.someoneasked);
            }else {
                Picasso.get().load(questionModelArrayList.get(i).getUserImageUrl()).
                        placeholder(R.drawable.ic_placeholder).into(((AskQuestionViewHolderWithImage) holder).profilePicture);
                profileNameAsked=String.format(context.getString(R.string.user_name_asked), questionModelArrayList.get(i).getAskerName());
            }

            Picasso.get().load(questionModelArrayList.get(i).getQuestionImageUrl()).
                    into(((AskQuestionViewHolderWithImage) holder).questionImage);
            ((AskQuestionViewHolderWithImage) holder).questionImage.setScaleType(ImageView.ScaleType.CENTER_CROP);
            ((AskQuestionViewHolderWithImage) holder).profileName.setText(profileNameAsked);

            // later about section add
            ((AskQuestionViewHolderWithImage) holder).question.setText(questionModelArrayList.get(i).getQuestion());
            long oldTime=questionModelArrayList.get(i).getTimeOfAsking();
            long currentTime=System.currentTimeMillis();
            String timeAgo=getTime(oldTime, currentTime);
            if(timeAgo==null)
                ((AskQuestionViewHolderWithImage) holder).timeAgo.setText("");
            else
                ((AskQuestionViewHolderWithImage) holder).timeAgo.setText(timeAgo);

        }
        else if(holder instanceof AskQuestionViewHolderWithoutImage){
            boolean isAnonymous=questionModelArrayList.get(i).isAnonymous();
            String profileNameAsked="";
            if(isAnonymous){
                profileNameAsked=context.getString(R.string.someoneasked);
            }else {
                profileNameAsked=String.format(context.getString(R.string.user_name_asked), questionModelArrayList.get(i).getAskerName());
                Picasso.get().load(questionModelArrayList.get(i).getUserImageUrl()).placeholder(R.drawable.ic_placeholder).
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

        }
    }

    @Override
    public int getItemViewType(int position) {
        boolean isImageAttached=questionModelArrayList.get(position).isImageAttached();
        if(isImageAttached){
            return QUESTION_WITH_IMAGE;
        }
        else {
            return QUESTION_WITHOUT_IMAGE;
        }
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
