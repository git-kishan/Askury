package com.droid.solver.askapp.homeAnswer;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.droid.solver.askapp.GlideApp;
import com.droid.solver.askapp.Home.LoadingViewHolderVertically;
import com.droid.solver.askapp.Main.Constants;
import com.droid.solver.askapp.R;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class QuestionAnswerRecyclerAdapter extends RecyclerView.Adapter {

    private Context context;
    private ArrayList<Object> list;
    private LayoutInflater inflater;
    private final int QUESTION=1;
    private final int ANSWER=2;
    private final int LOADING=0;
    private StorageReference reference;
    private int [] fontId=new int[]{R.font.open_sans,R.font.abril_fatface,R.font.aclonica,R.font.bubbler_one,R.font.bitter,R.font.geo};
    QuestionAnswerRecyclerAdapter(Context context, ArrayList<Object> list){
        this.context=context;
        this.list=list;
        reference=FirebaseStorage.getInstance().getReference();
        if(context!=null)
            inflater=LayoutInflater.from(context);
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        RecyclerView.ViewHolder viewHolder;
        View view;
        switch (i){
            case QUESTION:
                view=inflater.inflate(R.layout.home_answer_question_item,viewGroup,false);
                viewHolder=new QuestionViewHolder(view);
                break;
            case ANSWER:
                view=inflater.inflate(R.layout.home_answer_answer_item, viewGroup,false);
                viewHolder=new AnswerViewHolder(view,context);
                break;
            case LOADING:
                view=inflater.inflate(R.layout.loading, viewGroup,false);
                viewHolder=new LoadingViewHolderVertically(view);
                break;
                default:
                    view=inflater.inflate(R.layout.loading, viewGroup,false);
                    viewHolder=new LoadingViewHolderVertically(view);
                    break;

        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int i) {

        if(holder.getItemViewType()==QUESTION&&holder instanceof QuestionViewHolder){
           QuestionModel model = (QuestionModel) list.get(i);
           String askerName=model.getAskerName();
           String bio=model.getAskerBio();
           String question=model.getQuestion();
           boolean anonymous=model.isAnonymous();
           long timeOfAsking=model.getTimeOfAsking();
           askerName=askerName==null?"Someone":askerName;
           bio=bio==null?"":bio;
           if(question==null){
               list.remove(i);
               return;
           }
           timeOfAsking=timeOfAsking==0?System.currentTimeMillis():timeOfAsking;
           String timeAgoText=getTime(timeOfAsking, System.currentTimeMillis());
           if(anonymous){
               ((QuestionViewHolder) holder).askerName.setText(context.getString(R.string.unknown));
               ((QuestionViewHolder) holder).askerBio.setText("");
               GlideApp.with(context).load(R.drawable.ic_placeholder).into(((QuestionViewHolder) holder).profileImage);

           }else {
               ((QuestionViewHolder) holder).askerName.setText(askerName);
               ((QuestionViewHolder) holder).askerBio.setText(bio);

               String url= Constants.PROFILE_PICTURE+"/"+((QuestionModel) list.get(i)).getAskerId()+Constants.SMALL_THUMBNAIL;

               GlideApp.with(context).load(reference.child(url)).error(R.drawable.ic_placeholder).placeholder(R.drawable.ic_placeholder)
                       .into(((QuestionViewHolder) holder).profileImage);
           }

            ((QuestionViewHolder) holder).timeAgo.setText(timeAgoText);
            ((QuestionViewHolder) holder).question.setText(question);
            questionClickListener(context, (QuestionViewHolder) holder, model);
        }
        else if(holder.getItemViewType()==ANSWER&&holder instanceof AnswerViewHolder){
            AnswerModel model = (AnswerModel) list.get(i);
            String answererName=model.getAnswererName();
            String answererBio=model.getAnswererBio();
            String answer=model.getAnswer();
            int likeCount=model.getAnswerLikeCount();
            long timeAgo=model.getTimeOfAnswering();
            String answerImageUrl=model.getImageAttachedUrl();
            int fontUsed=model.getFontUsed();

            fontUsed=fontUsed<=fontId.length?fontUsed:0;
            answererName=answererName==null?"Someone":answererName;
            answererBio=answererBio==null?"":answererBio;
            if(answer==null){
                list.remove(i);
                return;
            }
            ((AnswerViewHolder) holder).answerImage.setVisibility(View.GONE);
            if(answerImageUrl!=null){
                ((AnswerViewHolder) holder).answerImage.setVisibility(View.VISIBLE);
                GlideApp.with(context).load(answerImageUrl).into(((AnswerViewHolder) holder).answerImage);
            }
            String timeAgoText=getTime(timeAgo, System.currentTimeMillis());
            String url=Constants.PROFILE_PICTURE+"/"+((AnswerModel) list.get(i)).getAnswererId()+Constants.SMALL_THUMBNAIL;
            GlideApp.with(context).load(reference.child(url)).error(R.drawable.ic_placeholder).placeholder(R.drawable.ic_placeholder)
                    .into(((AnswerViewHolder) holder).profileImage);

            ((AnswerViewHolder) holder).answererName.setText(answererName);
            ((AnswerViewHolder) holder).answererBio.setText(answererBio);
            ((AnswerViewHolder) holder).answer.setText(answer);
            ((AnswerViewHolder) holder).answer.setTypeface(ResourcesCompat.getFont(context, fontId[fontUsed]));
            ((AnswerViewHolder) holder).likeCount.setText(String.valueOf(likeCount));
            ((AnswerViewHolder) holder).timeAgo.setText(timeAgoText);

        }
    }

    @Override
    public int getItemCount() {
        return list==null?0:list.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(list.get(position) instanceof AnswerModel){
            return ANSWER;
        }else if(list.get(position) instanceof QuestionModel){
            return QUESTION;
        }
        else if(list.get(position) ==null)
             return LOADING;
        return LOADING;
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
        return "";
    }

    private String getTime(long oldDate,long newDate){
        long diff=newDate-oldDate;//777600
        if (diff<0){
            return null;
        }
        return getTimeDifferenceInWords(diff);
    }

    private void questionClickListener(final Context context, final QuestionViewHolder holder, final QuestionModel  model){

        holder.wantToAnswerImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.onWantToAnswerClicked(context,model);
            }
        });
    }

}
