package com.droid.solver.askapp.Home;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.droid.solver.askapp.ImagePoll.AskImagePollModel;
import com.droid.solver.askapp.Question.RootQuestionModel;
import com.droid.solver.askapp.R;
import com.droid.solver.askapp.Survey.AskSurveyModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class HomeRecyclerViewAdapter extends RecyclerView.Adapter {
    private Context context;
    private ArrayList<Object> list;
    private LayoutInflater inflater;
    private static final int QUESTION_ANSWER=0;
    private static final int QUESTION_ANSWER_WITH_IMAGE=1;
    private static final int SURVEY=2;
    private static final int IMAGE_POLL=3;
    private static final int LOADING=4;

    HomeRecyclerViewAdapter(Context context, ArrayList<Object> list){
        this.context=context;
        this.list=list;
        if(context!=null)
        inflater=LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view;
        RecyclerView.ViewHolder viewHolder;
        switch (viewType){
            case QUESTION_ANSWER:
                view=inflater.inflate(R.layout.home_question_answer_item,viewGroup,false);
                viewHolder= new QuestionAnswerViewHolder(view);
                break;

            case QUESTION_ANSWER_WITH_IMAGE:
                view=inflater.inflate(R.layout.home_question_answer_image_item, viewGroup,false);
                viewHolder= new QuestionAnswerWithImageViewHolder(view);
                break;
            case SURVEY:
                view=inflater.inflate(R.layout.home_survey, viewGroup,false);
                viewHolder= new SurveyViewHolder(view);
                break;
            case IMAGE_POLL:
                view=inflater.inflate(R.layout.home_image_poll, viewGroup,false);
                viewHolder= new ImagePollViewHolder(view);
                break;
            default:
                view=inflater.inflate(R.layout.loading, viewGroup,false);
                viewHolder=new LoadingViewHolderVertically(view);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int i) {
        if(holder.getItemViewType()==QUESTION_ANSWER&&holder instanceof QuestionAnswerViewHolder){
            if(list.get(i) instanceof RootQuestionModel){

                String question=((RootQuestionModel) list.get(i)).getQuestion();
                boolean anonymous=((RootQuestionModel) list.get(i)).isAnonymous();
                String answer=((RootQuestionModel) list.get(i)).getRecentAnswer();
                String askerImageUrl=((RootQuestionModel) list.get(i)).getAskerImageUrlLow();
                String recentAnswererImageUrl=((RootQuestionModel) list.get(i)).getRecentAnswererImageUrlLow();
                String askerBio=((RootQuestionModel) list.get(i)).getAskerBio();
                String askerName=((RootQuestionModel) list.get(i)).getAskerName();
                String recentAnswererName=((RootQuestionModel) list.get(i)).getRecentAnswererName();
                int likeCount=((RootQuestionModel) list.get(i)).getRecentAnswerLikeCount();
                int answerCount=((RootQuestionModel) list.get(i)).getAnswerCount();
                long timeOfAsking=((RootQuestionModel) list.get(i)).getTimeOfAsking();
                if(timeOfAsking==0)
                    timeOfAsking=System.currentTimeMillis();
                String timeAgo=getTime(timeOfAsking, System.currentTimeMillis());


                if(anonymous){
                    ((QuestionAnswerViewHolder) holder).askerImageView.setImageDrawable(context.getDrawable(R.drawable.ic_placeholder));
                    ((QuestionAnswerViewHolder) holder).askerName.setText("Someone wants to know");
                    ((QuestionAnswerViewHolder) holder).askerBio.setText("");
                }else {
                    if(askerImageUrl!=null)
                    Picasso.get().load(askerImageUrl).placeholder(R.drawable.ic_placeholder).into(((QuestionAnswerViewHolder) holder).askerImageView);
                    else   ((QuestionAnswerViewHolder) holder).askerImageView.setImageDrawable(context.getDrawable(R.drawable.ic_placeholder));



                    if(askerName!=null)
                         ((QuestionAnswerViewHolder) holder).askerName.setText(askerName);
                    else
                        ((QuestionAnswerViewHolder) holder).askerName.setText("Unknown");

                    if(askerBio!=null)
                       ((QuestionAnswerViewHolder) holder).askerBio.setText(askerBio);
                    else ((QuestionAnswerViewHolder) holder).askerBio.setText("");
                }
                ((QuestionAnswerViewHolder) holder).question.setText(question);

                if(recentAnswererImageUrl!=null)
                    Picasso.get().load(recentAnswererImageUrl).placeholder(R.drawable.ic_placeholder).
                            into(((QuestionAnswerViewHolder) holder).answererImageView);
                else   ((QuestionAnswerViewHolder) holder).answererImageView.setImageDrawable(context.getDrawable(R.drawable.ic_placeholder));


                ((QuestionAnswerViewHolder) holder).timeAgo.setText(timeAgo);

                if(recentAnswererName!=null)
                    ((QuestionAnswerViewHolder) holder).answererName.setText(recentAnswererName);
                else ((QuestionAnswerViewHolder) holder).answererName.setText("Unknown");

                if(answer==null)
                    ((QuestionAnswerViewHolder) holder).answer.setText("");
                else
                    ((QuestionAnswerViewHolder) holder).answer.setText(answer);

                ((QuestionAnswerViewHolder) holder).answerCount.setText(String.valueOf(answerCount));
                ((QuestionAnswerViewHolder) holder).likeCount.setText(String.valueOf(likeCount));

            }

        }else if(holder.getItemViewType()==QUESTION_ANSWER_WITH_IMAGE&&holder instanceof QuestionAnswerWithImageViewHolder){

            if(list.get(i) instanceof RootQuestionModel) {

                String question = ((RootQuestionModel) list.get(i)).getQuestion();
                boolean anonymous = ((RootQuestionModel) list.get(i)).isAnonymous();
                String answer = ((RootQuestionModel) list.get(i)).getRecentAnswer();
                String answerImageUrl = ((RootQuestionModel) list.get(i)).getRecentAnswerImageUrl();
                String askerImageUrl = ((RootQuestionModel) list.get(i)).getAskerImageUrlLow();
                String recentAnswererImageUrl = ((RootQuestionModel) list.get(i)).getRecentAnswererImageUrlLow();
                String askerBio = ((RootQuestionModel) list.get(i)).getAskerBio();
                String askerName = ((RootQuestionModel) list.get(i)).getAskerName();
                String recentAnswererName = ((RootQuestionModel) list.get(i)).getRecentAnswererName();
                int likeCount = ((RootQuestionModel) list.get(i)).getRecentAnswerLikeCount();
                int answerCount = ((RootQuestionModel) list.get(i)).getAnswerCount();
                long timeOfAsking = ((RootQuestionModel) list.get(i)).getTimeOfAsking();
                if (timeOfAsking == 0)
                    timeOfAsking = System.currentTimeMillis();
                String timeAgo = getTime(timeOfAsking, System.currentTimeMillis());

                if (answerImageUrl != null)
                    Picasso.get().load(answerImageUrl)
                            .into(((QuestionAnswerWithImageViewHolder) holder).answerImageView);
                if (anonymous) {
                    ((QuestionAnswerWithImageViewHolder) holder).askerImageView.setImageDrawable(context.getDrawable(R.drawable.ic_placeholder));
                    ((QuestionAnswerWithImageViewHolder) holder).askerName.setText("Someone wants to know");
                    ((QuestionAnswerWithImageViewHolder) holder).askerBio.setText("");
                } else {
                    if (askerImageUrl != null)
                        Picasso.get().load(askerImageUrl).placeholder(R.drawable.ic_placeholder).into(((QuestionAnswerWithImageViewHolder)
                                holder).askerImageView);
                    else
                        ((QuestionAnswerWithImageViewHolder) holder).askerImageView.setImageDrawable(context.getDrawable(R.drawable.ic_placeholder));


                    if (askerName != null)
                        ((QuestionAnswerWithImageViewHolder) holder).askerName.setText(askerName);
                    else
                        ((QuestionAnswerWithImageViewHolder) holder).askerName.setText("Unknown");

                    if (askerBio != null)
                        ((QuestionAnswerWithImageViewHolder) holder).askerBio.setText(askerBio);
                    else ((QuestionAnswerWithImageViewHolder) holder).askerBio.setText("");
                }
                ((QuestionAnswerWithImageViewHolder) holder).question.setText(question);
                if (recentAnswererImageUrl != null)
                    Picasso.get().load(recentAnswererImageUrl).placeholder(R.drawable.ic_placeholder).
                            into(((QuestionAnswerWithImageViewHolder) holder).answererImageView);
                else
                    ((QuestionAnswerWithImageViewHolder) holder).answererImageView.setImageDrawable(context.getDrawable(R.drawable.ic_placeholder));


                ((QuestionAnswerWithImageViewHolder) holder).timeAgo.setText(timeAgo);

                if (recentAnswererName != null)
                    ((QuestionAnswerWithImageViewHolder) holder).answererName.setText(recentAnswererName);
                else ((QuestionAnswerWithImageViewHolder) holder).answererName.setText("Unknown");

                if (answer == null)
                    ((QuestionAnswerWithImageViewHolder) holder).answer.setText("");
                else
                    ((QuestionAnswerWithImageViewHolder) holder).answer.setText(answer);
                ((QuestionAnswerWithImageViewHolder) holder).answerCount.setText(String.valueOf(answerCount));
                ((QuestionAnswerWithImageViewHolder) holder).likeCount.setText(String.valueOf(likeCount));

            }

        }


        else if(holder.getItemViewType()==IMAGE_POLL&&holder instanceof ImagePollViewHolder){

            if(list.get(i) instanceof AskImagePollModel){
                String askerId=((AskImagePollModel) list.get(i)).getAskerId();
                String askerName=((AskImagePollModel) list.get(i)).getAskerName();
                String askerImageUrlLow=((AskImagePollModel) list.get(i)).getAskerImageUrlLow();
                String askerBio=((AskImagePollModel) list.get(i)).getAskerBio();
                String askerQuestion=((AskImagePollModel) list.get(i)).getQuestion();
                final  String image1Url=((AskImagePollModel) list.get(i)).getImage1Url();
                final String image2Url=((AskImagePollModel) list.get(i)).getImage2Url();
                long timeOfPolling=((AskImagePollModel) list.get(i)).getTimeOfPolling();
                int image1LikeNo=((AskImagePollModel) list.get(i)).getImage1LikeNo();
                int image2LikeNo=((AskImagePollModel) list.get(i)).getImage2LikeNo();
                String imagePollId=((AskImagePollModel) list.get(i)).getImagePollId();

                askerName=askerName==null?"Unknown":askerName;
                askerBio=askerBio==null?"":askerBio;
                askerQuestion=askerQuestion==null?"":askerQuestion;
//                image1Url=image1Url==null?"":image1Url;
//                image2Url=image2Url==null?"":image2Url;
                timeOfPolling=timeOfPolling==0?System.currentTimeMillis():timeOfPolling;
                String timeAgo=getTime(timeOfPolling, System.currentTimeMillis());

                Picasso.get().load(image1Url).
                        into(((ImagePollViewHolder) holder).image1);
                Picasso.get().load(image2Url).
                        into(((ImagePollViewHolder) holder).image2);
                ((ImagePollViewHolder) holder).image1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent=new Intent(context,ImagePollOpenActivity.class);
                        intent.putExtra("imageUrl", image1Url);
                        context.startActivity(intent);
                    }
                });
                ((ImagePollViewHolder) holder).image2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent=new Intent(context,ImagePollOpenActivity.class);
                        intent.putExtra("imageUrl", image2Url);
                        context.startActivity(intent);
                    }
                });
                ((ImagePollViewHolder) holder).image1.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        return true;
                    }
                });
                ((ImagePollViewHolder) holder).image2.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        return true;
                    }
                });
                String userNameCreatedPoll=String.format(context.getString(R.string.username_created_a_poll), askerName);
                ((ImagePollViewHolder) holder).profileName.setText(userNameCreatedPoll);
                ((ImagePollViewHolder) holder).bio.setText(askerBio);
                ((ImagePollViewHolder) holder).question.setText(askerQuestion);
                if(askerQuestion.equals(""))
                    ((ImagePollViewHolder) holder).question.setVisibility(View.GONE);

                Picasso.get().load(askerImageUrlLow).placeholder(R.drawable.ic_placeholder).into(((ImagePollViewHolder) holder).profileImageView);
                ((ImagePollViewHolder) holder).timeAgo.setText(timeAgo);

            }

        }
        else if(holder.getItemViewType()==SURVEY&&holder instanceof SurveyViewHolder){
            if(list.get(i) instanceof AskSurveyModel){

            }
        }

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

}
