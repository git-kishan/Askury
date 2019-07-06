package com.droid.solver.askapp.Notification;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.droid.solver.askapp.GlideApp;
import com.droid.solver.askapp.Home.LoadingViewHolderVertically;
import com.droid.solver.askapp.Main.Constants;
import com.droid.solver.askapp.R;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
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
            QuestionModel model = (QuestionModel) list.get(i);
            String likerName=model.getLikerName();
            String likerUid=model.getLikerId();
            long notifiedTime=model.getNotifiedTime();
            String timeAgo=getTime(notifiedTime, System.currentTimeMillis());

            likerName=likerName==null?"Someone":likerName;
            String url= Constants.PROFILE_PICTURE+"/"+likerUid+Constants.SMALL_THUMBNAIL;
            StorageReference reference= FirebaseStorage.getInstance().getReference();

            GlideApp.with(context)
                    .load(reference.child(url))
                    .placeholder(android.R.color.holo_blue_dark)
                    .error(R.drawable.ic_placeholder)
                    .into(((QuestionViewHolder) holder).likerImage);

            String likes=String.format(context.getString(R.string.someone_likes_your_answer), likerName);
            ((QuestionViewHolder) holder).statusTextView.setText(likes);
            ((QuestionViewHolder) holder).timeAgo.setText(timeAgo);

            handleClickListenerOfQuestion((QuestionViewHolder) holder, model);

        }else if(holder instanceof ImagePollViewHolder && list.get(i) instanceof ImagePollModel){

            ImagePollModel model = (ImagePollModel) list.get(i);
            String question=model.getQuestion();
            String image1Url=model.getImage1Url();
            String image2Url=model.getImage2Url();
            int image1LikeNo=model.getImage1LikeNo();
            int image2LikeNo=model.getImage2LikeNo();
            long notifiedTime=model.getNotifiedTime();

            String timeAgo=getTime(notifiedTime, System.currentTimeMillis());
            question=question!=null?question:"Image poll result";
            image1Url=image1Url==null?"image1Url":image1Url;
            image2Url=image2Url==null?"image2Url":image2Url;
            image1LikeNo=image1LikeNo<0?0:image1LikeNo;
            image2LikeNo=image2LikeNo<0?0:image2LikeNo;
            int total=image1LikeNo+image2LikeNo;

            if(total==0){
                String percentA=String.format(context.getString(R.string.a_percent), 0)+"%";
                String percentB=String.format(context.getString(R.string.b_percent),0)+"%";
                ((ImagePollViewHolder) holder).percent1.setText(percentA);
                ((ImagePollViewHolder) holder).percent2.setText(percentB);
            }else {
                if(image1LikeNo==0){
                    String percentA=String.format(context.getString(R.string.a_percent), 0)+"%";
                    String percentB=String.format(context.getString(R.string.b_percent),100)+"%";
                    ((ImagePollViewHolder) holder).percent1.setText(percentA);
                    ((ImagePollViewHolder) holder).percent2.setText(percentB);
                }else if(image2LikeNo==0){
                    String percentA=String.format(context.getString(R.string.a_percent), 100)+"%";
                    String percentB=String.format(context.getString(R.string.b_percent),0)+"%";
                    ((ImagePollViewHolder) holder).percent1.setText(percentA);
                    ((ImagePollViewHolder) holder).percent2.setText(percentB);
                }else {
                    int perA=0,perB=0;
                    perA=(image1LikeNo)/total;
                    perB=100-perA;
                    String percentA=String.format(context.getString(R.string.a_percent), perA)+"%";
                    String percentB=String.format(context.getString(R.string.b_percent),perB)+"%";
                    ((ImagePollViewHolder) holder).percent1.setText(percentA);
                    ((ImagePollViewHolder) holder).percent2.setText(percentB);
                }

            }
            ((ImagePollViewHolder) holder).statusTextView.setText(question);
            ((ImagePollViewHolder) holder).timeAgo.setText(timeAgo);


            GlideApp.with(context)
                    .load(image1Url)
                    .placeholder(android.R.color.darker_gray)
                    .error(android.R.color.darker_gray)
                    .into(((ImagePollViewHolder) holder).image1);
            GlideApp.with(context)
                    .load(image2Url)
                    .placeholder(android.R.color.holo_blue_dark)
                    .error(android.R.color.holo_blue_dark)
                    .into(((ImagePollViewHolder) holder).image2);

            handleClickListenerOfImagePoll((ImagePollViewHolder) holder, model);

        }else if(holder instanceof SurveyViewHolder && list.get(i) instanceof  SurveyModel){

            SurveyModel model =(SurveyModel) list.get(i);
            String question=model.getQuestion();

            long notifiedTime=model.getNotifiedTime();
            String timeAgo=getTime(notifiedTime, System.currentTimeMillis());
            question=question==null?"Survey result":question;
            ((SurveyViewHolder) holder).timeAgo.setText(timeAgo);
            ((SurveyViewHolder) holder).statusTextView.setText(question);

            handleClickListenerOfSurvey((SurveyViewHolder) holder, model);

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

    private void handleClickListenerOfQuestion(final QuestionViewHolder viewHolder,final QuestionModel model){
        viewHolder.rootCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewHolder.onCardClicked(context, model);
            }
        });
    }
    private void handleClickListenerOfImagePoll(final ImagePollViewHolder viewHolder, final ImagePollModel model){
        viewHolder.rootCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewHolder.onCardClicked(context, model);
            }
        });
    }
    private void handleClickListenerOfSurvey(final SurveyViewHolder viewHolder,final  SurveyModel model){
        viewHolder.rootCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewHolder.onCardClicked(context, model);
            }
        });
    }
}

