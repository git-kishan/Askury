package com.droid.solver.askapp.Account;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.droid.solver.askapp.Home.LoadingViewHolderVertically;
import com.droid.solver.askapp.Question.UserQuestionModel;
import com.droid.solver.askapp.R;
import java.util.ArrayList;

public class AccountQuestionRecyclerAdapter extends RecyclerView.Adapter {

    private Context context;
    private ArrayList<Object> list;
    private LayoutInflater inflater;
    private static final int LOADING=0;
    private static final int QUESTION=1;
    AccountQuestionRecyclerAdapter(Context context, ArrayList<Object> list){
        if(context!=null){
            this.context=context;
            this.list=list;
            inflater=LayoutInflater.from(context);
        }

    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        RecyclerView.ViewHolder  viewHolder;
        switch (i){
            case QUESTION:
                view =inflater.inflate(R.layout.account_question_item,viewGroup,false);
                viewHolder=new AccountQuestionRecyclerViewHolder(view,context);
                break;

            case LOADING:
                view=inflater.inflate(R.layout.loading,viewGroup,false);
                viewHolder =new LoadingViewHolderVertically(view);
                break;

                default:
                    view=inflater.inflate(R.layout.loading,viewGroup,false);
                    viewHolder=new LoadingViewHolderVertically(view);
                    break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int i) {
        if(list.get(i) instanceof UserQuestionModel && holder instanceof AccountQuestionRecyclerViewHolder){
            UserQuestionModel model= (UserQuestionModel) list.get(i);
            String question=model.getQuestion();
            long time=model.getTimeOfAsking();
            int answerCount=model.getAnswerCount();
            String timeAgo=getTime(time, System.currentTimeMillis());
            answerCount=answerCount>=0?answerCount:0;
            String countAnswer=String.format(context.getString(R.string.count_answer), answerCount);
            ((AccountQuestionRecyclerViewHolder) holder).timeAgoTextView.setText(timeAgo);
            ((AccountQuestionRecyclerViewHolder) holder).questionTextView.setText(question);
            ((AccountQuestionRecyclerViewHolder) holder).answerCount.setText(countAnswer);
            onQuestionClicked((AccountQuestionRecyclerViewHolder) holder, model);

        }

    }

    @Override
    public int getItemCount() {
        return list==null?0:list.size();
    }

    @Override
    public int getItemViewType(int position) {

        if(list.get(position) instanceof UserQuestionModel){
            return QUESTION;
        }
        else if(list.get(position) ==null){
            return LOADING;
        }
       return 0;
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
    public String getTime(long oldDate,long newDate){
        long diff=newDate-oldDate;//777600
        if (diff<0){
            return null;
        }
        return getTimeDifferenceInWords(diff);
    }

    private void onQuestionClicked(final AccountQuestionRecyclerViewHolder holder,final UserQuestionModel model){
        holder.onCardClicked(model);
    }

}

