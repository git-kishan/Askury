package com.droid.solver.askapp.Home;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.droid.solver.askapp.ImagePoll.AskImagePollModel;
import com.droid.solver.askapp.Question.RootQuestionModel;
import com.droid.solver.askapp.R;
import com.droid.solver.askapp.Survey.AskSurveyModel;

import java.util.ArrayList;

public class VerticalRecyclerViewAdapter extends RecyclerView.Adapter {
    private Context context;
    private ArrayList<Object> list;
    private LayoutInflater inflater;
    private static final int QUESTION_ANSWER=0;
    private static final int QUESTION_ANSWER_WITH_IMAGE=1;
    private static final int SURVEY=2;
    private static final int IMAGE_POLL=3;
    private static final int LOADING=4;

    VerticalRecyclerViewAdapter (Context context,ArrayList<Object> list){
        this.context=context;
        this.list=list;
        inflater=LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view;
        switch (viewType){
            case QUESTION_ANSWER:
                view=inflater.inflate(R.layout.home_question_answer_item,viewGroup,false);
                return new QuestionAnswerViewHolder(view);

            case QUESTION_ANSWER_WITH_IMAGE:
                view=inflater.inflate(R.layout.home_question_answer_image_item, viewGroup,false);
                return new QuestionAnswerWithImageViewHolder(view);
            case SURVEY:
                view=inflater.inflate(R.layout.home_survey, viewGroup,false);
                return new SurveyViewHolder(view);

            case IMAGE_POLL:
                view=inflater.inflate(R.layout.home_image_poll, viewGroup,false);
                return new ImagePollViewHolder(view);
            case LOADING:
                view=inflater.inflate(R.layout.loading, viewGroup,false);
                break;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int i) {



    }

    @Override
    public int getItemCount() {
        return list==null?0:list.size();
    }

    @Override
    public int getItemViewType(int position) {
        if((list.get(position) instanceof RootQuestionModel)&&((RootQuestionModel) list.get(position)).isRecentAnswerImageAttached()){
            return QUESTION_ANSWER_WITH_IMAGE;
        }else if((list.get(position) instanceof RootQuestionModel)&&!((RootQuestionModel) list.get(position)).isRecentAnswerImageAttached()){
            return QUESTION_ANSWER;
        }else if(list.get(position) instanceof AskImagePollModel){
            return IMAGE_POLL;
        }else if(list.get(position) instanceof AskSurveyModel){
            return SURVEY;
        }
        return -1;
    }
}
