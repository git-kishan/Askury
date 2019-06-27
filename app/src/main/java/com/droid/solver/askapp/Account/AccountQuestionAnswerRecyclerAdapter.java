package com.droid.solver.askapp.Account;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.droid.solver.askapp.Answer.UserAnswerModel;
import com.droid.solver.askapp.GlideApp;
import com.droid.solver.askapp.Home.LoadingViewHolderVertically;
import com.droid.solver.askapp.Main.Constants;
import com.droid.solver.askapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import java.util.ArrayList;

public class AccountQuestionAnswerRecyclerAdapter extends RecyclerView.Adapter {

    private Context context;
    private ArrayList<Object> list;
    private LayoutInflater inflater;
    private static final int LOADING=1;
    private static final int ANSWER=2;

    public AccountQuestionAnswerRecyclerAdapter(final Context context, ArrayList<Object> list){

        if(context!=null){
            this.context=context;
            this.list=list;
            inflater=LayoutInflater.from(context);
        }
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        RecyclerView.ViewHolder viewHolder;
        View view;
        switch(i){
            case ANSWER:
                view=inflater.inflate(R.layout.account_question_answer_item, viewGroup,false);
                viewHolder=new AccountQuestionAnswerRecyclerViewHolder(view);
                break;
            case LOADING:
                view=inflater.inflate(R.layout.loading,viewGroup,false);
                viewHolder=new LoadingViewHolderVertically(view);
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

        if(holder instanceof AccountQuestionAnswerRecyclerViewHolder && list.get(i) instanceof UserAnswerModel){
            UserAnswerModel model = (UserAnswerModel) list.get(i);
            String askserId=model.getAskerId();
            String askerName=model.getAskerName();
            String askerBio=model.getAskerBio();
            String question=model.getQuestion();
            String answer=model.getAnswer();

            askerName=askerName==null?"Someone":askerName;
            askerBio=askerBio==null?"":askerBio;
            question=question==null?"Question not available":question;
            answer=answer==null?"Answer not available":answer;

            String answererId=null;
            if(FirebaseAuth.getInstance().getCurrentUser()!=null) {
                answererId = FirebaseAuth.getInstance().getCurrentUser().getUid();
            }
            String askerUrl= Constants.PROFILE_PICTURE+"/"+askserId+Constants.SMALL_THUMBNAIL;
            String answererUrl=Constants.PROFILE_PICTURE+"/"+answererId+Constants.SMALL_THUMBNAIL;
            StorageReference reference= FirebaseStorage.getInstance().getReference();
            GlideApp.with(context)
                    .load(reference.child(askerUrl))
                    .error(R.drawable.ic_placeholder)
                    .placeholder(R.drawable.ic_placeholder)
                    .into(((AccountQuestionAnswerRecyclerViewHolder) holder).askerImageView);
            GlideApp.with(context)
                    .load(reference.child(answererUrl))
                    .error(R.drawable.ic_placeholder)
                    .placeholder(R.drawable.ic_placeholder)
                    .into(((AccountQuestionAnswerRecyclerViewHolder) holder).answererImageView);
            ((AccountQuestionAnswerRecyclerViewHolder) holder).askerName.setText(askerName);
            ((AccountQuestionAnswerRecyclerViewHolder) holder).askerBio.setText(askerBio);
            ((AccountQuestionAnswerRecyclerViewHolder) holder).questionTextView.setText(question);
            ((AccountQuestionAnswerRecyclerViewHolder) holder).answerTextView.setText(answer);

        }
    }

    @Override
    public int getItemCount() {
        return list==null?0:list.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(list.get(position) instanceof UserAnswerModel){
            return ANSWER;
        }else if(list.get(position) ==null){
            return LOADING;
        }
        else return LOADING;
    }
}
