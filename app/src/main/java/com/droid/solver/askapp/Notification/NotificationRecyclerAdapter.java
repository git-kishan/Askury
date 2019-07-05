package com.droid.solver.askapp.Notification;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.droid.solver.askapp.Home.LoadingViewHolderVertically;
import com.droid.solver.askapp.R;
import java.util.List;

public class NotificationRecyclerAdapter  extends RecyclerView .Adapter{

    private List<Object> list;
    private LayoutInflater inflater;
    private Context context;
    private final int QUESTION=1;
    private final int IMAGE_POLL=2;
    private final int SURVEY=3;
    private final int LOADING=0;

    NotificationRecyclerAdapter(Context context,  List<Object> list){
        this.context=context;
        if(context!=null){
            inflater=LayoutInflater.from(context);
            this.list=list;
        }

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=null;
        RecyclerView.ViewHolder holder=null;
        switch (i){
            case QUESTION:
                view=inflater.inflate(R.layout.notification_question_item, viewGroup,false);
                holder=new QuestionViewHolder(view);
                break;
            case IMAGE_POLL:
                view=inflater.inflate(R.layout.notification_imagepoll_item, viewGroup,false);
                holder=new ImagePollViewHolder(view);
                break;
            case SURVEY:
                view=inflater.inflate(R.layout.notification_survey_item,viewGroup,false);
                holder=new SurveyViewHolder(view);
                break;
            case LOADING:
                view=inflater.inflate(R.layout.loading,viewGroup,false);
                holder=new LoadingViewHolderVertically(view);
                break;
                default:
                    view=inflater.inflate(R.layout.loading,viewGroup,false);
                    holder=new LoadingViewHolderVertically(view);
                    break;
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int i) {

        if(holder instanceof QuestionViewHolder &&list.get(i) instanceof  QuestionModel){

        }else if(holder instanceof ImagePollViewHolder && list.get(i) instanceof ImagePollModel){

        }else if(holder instanceof SurveyViewHolder && list.get(i) instanceof  SurveyViewHolder){

        }
    }

    @Override
    public int getItemCount() {
        return list==null?0:list.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(list.get(position) instanceof QuestionModel){
            return QUESTION;
        }else if(list.get(position) instanceof ImagePollModel ){
            return IMAGE_POLL;
        }else if(list.get(position) instanceof  SurveyModel){
            return SURVEY;
        }else if(list.get(position)==null){
            return LOADING;
        }else {
            return LOADING;
        }
    }
}
