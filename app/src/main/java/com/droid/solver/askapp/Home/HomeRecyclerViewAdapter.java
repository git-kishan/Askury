package com.droid.solver.askapp.Home;

import android.content.Context;
import android.graphics.Typeface;
import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.droid.solver.askapp.GlideApp;
import com.droid.solver.askapp.ImagePoll.AskImagePollModel;
import com.droid.solver.askapp.Main.Constants;
import com.droid.solver.askapp.Question.RootQuestionModel;
import com.droid.solver.askapp.R;
import com.droid.solver.askapp.Survey.AskSurveyModel;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.like.LikeButton;
import com.like.OnLikeListener;
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
    static final String FOLLOW="Follow";
    static final String UNFOLLOW="Unfollow";
    private ArrayList<String> followingIdListFromLocalDatabase;
    private  int [] fontId=new int[]{R.font.open_sans,R.font.abril_fatface,R.font.aclonica,R.font.bubbler_one,R.font.bitter,R.font.geo};

    HomeRecyclerViewAdapter(Context context, ArrayList<Object> list,
                            ArrayList<String> followingId){
        this.context=context;
        this.list=list;
        if(context!=null) {
            inflater = LayoutInflater.from(context);
            this.followingIdListFromLocalDatabase=followingId;
        }
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view;
        RecyclerView.ViewHolder viewHolder;
        switch (viewType){
            case QUESTION_ANSWER:
                view=inflater.inflate(R.layout.home_question_answer_item,viewGroup,false);
                viewHolder= new QuestionAnswerViewHolder(view,context);
                break;
            case QUESTION_ANSWER_WITH_IMAGE:
                view=inflater.inflate(R.layout.home_question_answer_image_item, viewGroup,false);
                viewHolder= new QuestionAnswerWithImageViewHolder(view,context);
                break;
            case SURVEY:
                view=inflater.inflate(R.layout.home_survey, viewGroup,false);
                viewHolder= new SurveyViewHolder(view,context);
                break;
            case IMAGE_POLL:
                view=inflater.inflate(R.layout.home_image_poll, viewGroup,false);
                viewHolder= new ImagePollViewHolder(view,context);
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
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int i) {
        if(holder.getItemViewType()==QUESTION_ANSWER&&holder instanceof QuestionAnswerViewHolder && list.get(i) instanceof RootQuestionModel){

                RootQuestionModel model=(RootQuestionModel) list.get(i);

                String question=model.getQuestion();
                boolean anonymous=model.isAnonymous();
                String answer=model.getRecentAnswer();
                String askerBio=model.getAskerBio();
                String askerName=model.getAskerName();
                String recentAnswererName=model.getRecentAnswererName();
                int likeCount=model.getRecentAnswerLikeCount();
                int answerCount=model.getAnswerCount();
                int fontUsed=model.getFontUsed();
                long timeOfAsking=model.getTimeOfAsking();

                if(model.isLikedByMe())
                    ((QuestionAnswerViewHolder) holder).likeButton.setLiked(true);
                else
                    ((QuestionAnswerViewHolder) holder).likeButton.setLiked(false);

                timeOfAsking=timeOfAsking==0?System.currentTimeMillis():timeOfAsking;
                String timeAgo=getTime(timeOfAsking, System.currentTimeMillis());

                if(anonymous){
                    ((QuestionAnswerViewHolder) holder).askerImageView.setImageDrawable(context.getDrawable(R.drawable.ic_placeholder));
                    ((QuestionAnswerViewHolder) holder).askerName.setText(context.getString(R.string.some_one_wants_to_know));
                    ((QuestionAnswerViewHolder) holder).askerBio.setText("");
                }
                else {
                    String url = Constants.PROFILE_PICTURE + "/" + model.getAskerId()+Constants.SMALL_THUMBNAIL;
                    StorageReference reference= FirebaseStorage.getInstance().getReference().child(url);
                    GlideApp.with(context).load(reference).placeholder(R.drawable.ic_placeholder).error(R.drawable.ic_placeholder)
                            .into(((QuestionAnswerViewHolder) holder).askerImageView);

                    askerName=askerName!=null?askerName:"Unknown";
                    ((QuestionAnswerViewHolder) holder).askerName.setText(askerName);

                    askerBio=askerBio!=null?askerBio:"";
                    ((QuestionAnswerViewHolder) holder).askerBio.setText(askerBio);
                }
                ((QuestionAnswerViewHolder) holder).question.setText(question);

                String url = Constants.PROFILE_PICTURE + "/" +model.getRecentAnswererId()+Constants.SMALL_THUMBNAIL;
                StorageReference reference= FirebaseStorage.getInstance().getReference().child(url);
                GlideApp.with(context).load(reference).placeholder(R.drawable.ic_placeholder).error(R.drawable.ic_placeholder)
                        .into(((QuestionAnswerViewHolder) holder).answererImageView);

                ((QuestionAnswerViewHolder) holder).timeAgo.setText(timeAgo);
                recentAnswererName=recentAnswererName!=null?recentAnswererName:"Unknown";
                ((QuestionAnswerViewHolder) holder).answererName.setText(recentAnswererName);

                answer=answer!=null?answer : "";
                ((QuestionAnswerViewHolder) holder).answer.setText(answer);

                Typeface typeface= ResourcesCompat.getFont(context, fontId[fontUsed]);
                ((QuestionAnswerViewHolder) holder).answer.setTypeface(typeface);

                ((QuestionAnswerViewHolder) holder).answerCount.setText(String.valueOf(answerCount));
                ((QuestionAnswerViewHolder) holder).likeCount.setText(String.valueOf(likeCount));
                if(likeCount==0)
                    ((QuestionAnswerViewHolder) holder).likeButton.setLiked(false);

                handleClickListenerOfQuestionAnswer((QuestionAnswerViewHolder) holder, (RootQuestionModel)list.get(i));



        }else if(holder.getItemViewType()==QUESTION_ANSWER_WITH_IMAGE&&holder instanceof QuestionAnswerWithImageViewHolder
                 && list.get(i) instanceof RootQuestionModel){

                RootQuestionModel model = (RootQuestionModel) list.get(i);

                String question = model.getQuestion();
                boolean anonymous = model.isAnonymous();
                String answer = model.getRecentAnswer();
                String answerImageUrl =model.getRecentAnswerImageUrl();
                String askerBio = model.getAskerBio();
                String askerName =model.getAskerName();
                String recentAnswererName =model.getRecentAnswererName();
                int likeCount = model.getRecentAnswerLikeCount();
                int answerCount = model.getAnswerCount();
                long timeOfAsking =model.getTimeOfAsking();
                int fontUsed=model.getFontUsed();

                timeOfAsking=timeOfAsking==0?System.currentTimeMillis():timeOfAsking;
                String timeAgo = getTime(timeOfAsking, System.currentTimeMillis());

                if(model.isLikedByMe())
                    ((QuestionAnswerWithImageViewHolder) holder).likeButton.setLiked(true);
                else
                    ((QuestionAnswerWithImageViewHolder) holder).likeButton.setLiked(false);

                if (answerImageUrl != null)
                    GlideApp.with(context).load(answerImageUrl)
                    .into(((QuestionAnswerWithImageViewHolder) holder).answerImageView);

                if (anonymous) {
                    ((QuestionAnswerWithImageViewHolder) holder).askerImageView.setImageDrawable(context.getDrawable(R.drawable.ic_placeholder));
                    ((QuestionAnswerWithImageViewHolder) holder).askerName.setText(context.getString(R.string.some_one_wants_to_know));
                    ((QuestionAnswerWithImageViewHolder) holder).askerBio.setText("");
                } else {

                    String url = Constants.PROFILE_PICTURE + "/" + model.getAskerId()+Constants.SMALL_THUMBNAIL;
                    StorageReference reference= FirebaseStorage.getInstance().getReference().child(url);
                    GlideApp.with(context).load(reference).placeholder(R.drawable.ic_placeholder).error(R.drawable.ic_placeholder).
                            into(((QuestionAnswerWithImageViewHolder)
                            holder).askerImageView);

                    askerName=askerName!=null?askerName:"Unknown";
                    ((QuestionAnswerWithImageViewHolder) holder).askerName.setText(askerName);
                    askerBio=askerBio!=null?askerBio:"";
                    ((QuestionAnswerWithImageViewHolder) holder).askerBio.setText(askerBio);
                }

                ((QuestionAnswerWithImageViewHolder) holder).question.setText(question);

                String url = Constants.PROFILE_PICTURE+"/"+model.getRecentAnswererId()+Constants.SMALL_THUMBNAIL;
                StorageReference reference= FirebaseStorage.getInstance().getReference().child(url);
                GlideApp.with(context).load(reference).placeholder(R.drawable.ic_placeholder).error(R.drawable.ic_placeholder).
                        into(((QuestionAnswerWithImageViewHolder) holder).answererImageView);

                ((QuestionAnswerWithImageViewHolder) holder).timeAgo.setText(timeAgo);

                recentAnswererName=recentAnswererName!=null?recentAnswererName:"Unknown";
                ((QuestionAnswerWithImageViewHolder) holder).answererName.setText(recentAnswererName);

                answer=answer!=null?answer:"";
                ((QuestionAnswerWithImageViewHolder) holder).answer.setText(answer);

                Typeface typeface= ResourcesCompat.getFont(context, fontId[fontUsed]);
                ((QuestionAnswerWithImageViewHolder) holder).answer.setTypeface(typeface);

                ((QuestionAnswerWithImageViewHolder) holder).answerCount.setText(String.valueOf(answerCount));
                ((QuestionAnswerWithImageViewHolder) holder).likeCount.setText(String.valueOf(likeCount));

                if(likeCount==0)
                    ((QuestionAnswerWithImageViewHolder) holder).likeButton.setLiked(false);

                handleClickListenerOfQuestionAnswerWithImage((QuestionAnswerWithImageViewHolder)holder,model);


        }

        else if(holder.getItemViewType()==IMAGE_POLL&&holder instanceof ImagePollViewHolder){

            if(list.get(i) instanceof AskImagePollModel){

                final AskImagePollModel model=(AskImagePollModel) list.get(i);
                String askerId=model.getAskerId();
                String askerName=model.getAskerName();
                String askerBio=model.getAskerBio();
                String askerQuestion=model.getQuestion();
                String image1Url=model.getImage1Url();
                String image2Url=model.getImage2Url();
                long timeOfPolling=model.getTimeOfPolling();
                final int image1LikeNo=model.getImage1LikeNo();
                final int image2LikeNo=model.getImage2LikeNo();
                ((ImagePollViewHolder) holder).image1.setTransitionName("image1"+i);
                ((ImagePollViewHolder) holder).image2.setTransitionName("image2"+i);
                if(model.getOptionSelectedByMe()!=0){
                    ((ImagePollViewHolder) holder).showLike(context, image1LikeNo,
                            image2LikeNo, model.getOptionSelectedByMe());
                }
                askerName=askerName==null?"Unknown":askerName;
                askerBio=askerBio==null?"":askerBio;
                askerQuestion=askerQuestion==null?"":askerQuestion;
                image1Url=image1Url==null?"":image1Url;
                image2Url=image2Url==null?"":image2Url;
                timeOfPolling=timeOfPolling==0?System.currentTimeMillis():timeOfPolling;
                String timeAgo=getTime(timeOfPolling, System.currentTimeMillis());

                Picasso.get().load(image1Url).
                        into(((ImagePollViewHolder) holder).image1);
                Picasso.get().load(image2Url).
                        into(((ImagePollViewHolder) holder).image2);

                String userNameCreatedPoll=String.format(context.getString(R.string.username_created_a_poll), askerName);
                ((ImagePollViewHolder) holder).profileName.setText(userNameCreatedPoll);
                ((ImagePollViewHolder) holder).bio.setText(askerBio);
                ((ImagePollViewHolder) holder).question.setText(askerQuestion);
                if(askerQuestion.equals(""))
                    ((ImagePollViewHolder) holder).question.setVisibility(View.GONE);

                String url = Constants.PROFILE_PICTURE + "/" + askerId+Constants.SMALL_THUMBNAIL;
                StorageReference reference= FirebaseStorage.getInstance().getReference().child(url);
                GlideApp.with(context).load(reference).placeholder(R.drawable.ic_placeholder).into(((ImagePollViewHolder) holder).profileImageView);
                ((ImagePollViewHolder) holder).timeAgo.setText(timeAgo);

                handleClickListenerOfImagePoll((ImagePollViewHolder) holder,model);

            }

        }

        else if(holder.getItemViewType()==SURVEY&&holder instanceof SurveyViewHolder){
            if(list.get(i) instanceof AskSurveyModel){
                final AskSurveyModel model =(AskSurveyModel)list.get(i);
                handleClickListenerOfSurvey((SurveyViewHolder)holder, model);
                String askerId=model.getAskerId();
                String askerName=model.getAskerName();
                String askerBio=model.getAskerBio();
                String question=model.getQuestion();
                final boolean option1=model.isOption1();
                final boolean option2=model.isOption2();
                final boolean option3=model.isOption3();
                final boolean option4=model.isOption4();
                final String option1Value=model.getOption1Value();
                final String option2Value=model.getOption2Value();
                final String option3Value=model.getOption3Value();
                final String option4Value=model.getOption4Value();

                if(model.getOptionSelectedByMe()!=0){
                    ((SurveyViewHolder) holder).showSelection(model.getOptionSelectedByMe(), model);

                }
                askerName=askerName==null?"Someone":askerName;
                askerBio=askerBio==null?"":askerBio;
                question=question==null?"":question;

                String url = Constants.PROFILE_PICTURE + "/" +askerId+Constants.SMALL_THUMBNAIL;
                StorageReference reference= FirebaseStorage.getInstance().getReference().child(url);
                GlideApp.with(context).load(reference).placeholder(R.drawable.ic_placeholder)
                        .into(((SurveyViewHolder) holder).profileImage);

                ((SurveyViewHolder) holder).question.setText(question);
                ((SurveyViewHolder) holder).bio.setText(askerBio);
                String someOneIsDoingASurvey=String.format(context.getString(R.string.some_one_is_doing_a_survey), askerName);
                ((SurveyViewHolder) holder).profileName.setText(someOneIsDoingASurvey);

                if(option1){
                    ((SurveyViewHolder) holder).option1TextView.setText(option1Value);
                    ((SurveyViewHolder) holder).container1.setVisibility(View.VISIBLE);
                }if(option2){
                    ((SurveyViewHolder) holder).option2TextView.setText(option2Value);
                    ((SurveyViewHolder) holder).container2.setVisibility(View.VISIBLE);
                }if(option3){
                    ((SurveyViewHolder) holder).option3TextView.setText(option3Value);
                    ((SurveyViewHolder) holder).container3.setVisibility(View.VISIBLE);
                }
                if(option4){
                    ((SurveyViewHolder) holder).option4TextView.setText(option4Value);
                    ((SurveyViewHolder) holder).container4.setVisibility(View.VISIBLE);
                }
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
        } else if(list.get(position) == null){
            return LOADING;
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

    private String getTime(long oldDate,long newDate){
        long diff=newDate-oldDate;//777600
        if (diff<0){
            return null;
        }
        return getTimeDifferenceInWords(diff);
    }

    private void handleClickListenerOfImagePoll(final ImagePollViewHolder holder, final AskImagePollModel imagePollModel){

        holder.image1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.onImage1SingleClicked(context,imagePollModel.getImage1Url());

            }
        });
        holder.image2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.onImage2SingleClicked(context,imagePollModel.getImage2Url());
            }
        });

        holder.image1.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                holder.onImage1LongClicked(context, imagePollModel);
                return true;
            }
        });
        holder.image2.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                holder.onImage2LongClicked(context,imagePollModel);
                return true;
            }
        });
        holder.threeDot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    if(followingIdListFromLocalDatabase.contains(imagePollModel.getAskerId())){
                        holder.onThreeDotClicked(context, imagePollModel,list,HomeRecyclerViewAdapter.this,UNFOLLOW,
                                followingIdListFromLocalDatabase);
                    }else
                        holder.onThreeDotClicked(context, imagePollModel,list,HomeRecyclerViewAdapter.this,FOLLOW,
                                followingIdListFromLocalDatabase);

            }
        });
        holder.profileImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.onProfileImageClicked(context, imagePollModel);

            }
        });


    }

    private void handleClickListenerOfSurvey(final SurveyViewHolder holder,final AskSurveyModel surveyModel){

        holder.container1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.onContainer1Clicked(surveyModel);
            }
        });
        holder.container2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.onContainer2Clicked(surveyModel);
            }
        });
        holder.container3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.onContainer3Clicked(surveyModel);
            }
        });
        holder.container4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.onContainer4Clicked(surveyModel);
            }
        });
        holder.threeDot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(followingIdListFromLocalDatabase.contains(surveyModel.getAskerId())){
                    holder.onThreeDotClicked(context, surveyModel,list,HomeRecyclerViewAdapter.this,UNFOLLOW,
                            followingIdListFromLocalDatabase);
                }else
                    holder.onThreeDotClicked(context, surveyModel,list,HomeRecyclerViewAdapter.this,FOLLOW,
                            followingIdListFromLocalDatabase);            }
        });
        holder.profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.onProfileImageClicked(context, surveyModel);

            }
        });
    }

    private void handleClickListenerOfQuestionAnswer(final QuestionAnswerViewHolder holder,
                                                     final RootQuestionModel rootQuestionModel){
        holder.threeDot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(followingIdListFromLocalDatabase.contains(rootQuestionModel.getAskerId())){

                    holder.onThreeDotClicked(context, rootQuestionModel,list,HomeRecyclerViewAdapter.this,UNFOLLOW,
                            followingIdListFromLocalDatabase);
                }else
                    holder.onThreeDotClicked(context, rootQuestionModel,list,HomeRecyclerViewAdapter.this,FOLLOW,
                            followingIdListFromLocalDatabase);
            }
        });

        holder.wantToAnswerImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.onWantToAnswer(context,rootQuestionModel);
            }
        });
        holder.answer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.onAnswerClicked(context, rootQuestionModel);
            }
        });
        holder.question.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.onAnswerClicked(context, rootQuestionModel);
            }
        });
        holder.likeButton.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
                holder.onLiked(context, rootQuestionModel);
            }

            @Override
            public void unLiked(LikeButton likeButton) {
                holder.onDisliked(context, rootQuestionModel);
            }
        });

        holder.numberOfAnswerImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.onNumberOfAnswerClicked(context, rootQuestionModel);
            }
        });
        holder.askerImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.onAskerImageViewClicked(context, rootQuestionModel);
            }
        });
        holder.answererImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.onAnswererImageViewClicked(context, rootQuestionModel);
            }
        });


    }

    private void handleClickListenerOfQuestionAnswerWithImage(final QuestionAnswerWithImageViewHolder holder,
                                                              final RootQuestionModel rootQuestionModel){

        holder.threeDot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(followingIdListFromLocalDatabase.contains(rootQuestionModel.getAskerId())){

                    holder.onThreeDotClicked(context, rootQuestionModel,list,HomeRecyclerViewAdapter.this,UNFOLLOW,
                            followingIdListFromLocalDatabase);
                }else
                    holder.onThreeDotClicked(context, rootQuestionModel,list,HomeRecyclerViewAdapter.this,FOLLOW,
                            followingIdListFromLocalDatabase);

            }
        });
        holder.wantToAnswerImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.onWantToAnswer(context, rootQuestionModel);
            }
        });
        holder.answer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.onAnswerClicked(context, rootQuestionModel);
            }
        });
        holder.question.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.onAnswerClicked(context, rootQuestionModel);
            }
        });
        holder.likeButton.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
                holder.onLiked(context, rootQuestionModel);
            }

            @Override
            public void unLiked(LikeButton likeButton) {
                holder.onDisliked(context, rootQuestionModel);
            }
        });
        holder.numberOfAnswerImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.onNumberOfAnswerClicked(context, rootQuestionModel);
            }
        });
        holder.askerImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.onAskerImageViewClicked(context, rootQuestionModel);
            }
        });
        holder.answererImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.onAnswererImageViewClicked(context, rootQuestionModel);
            }
        });

    }

}
