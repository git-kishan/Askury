package com.droid.solver.askapp.Home;

import android.content.Context;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.support.annotation.NonNull;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.droid.solver.askapp.Account.ProfileImageActivity;
import com.droid.solver.askapp.GlideApp;
import com.droid.solver.askapp.ImagePoll.AskImagePollModel;
import com.droid.solver.askapp.Main.Constants;
import com.droid.solver.askapp.Main.LocalDatabase;
import com.droid.solver.askapp.Main.MainActivity;
import com.droid.solver.askapp.Question.RootQuestionModel;
import com.droid.solver.askapp.R;
import com.droid.solver.askapp.Survey.AskSurveyModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.like.LikeButton;
import com.like.OnLikeListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

public class HomeRecyclerViewAdapter extends RecyclerView.Adapter {
    private Context context;
    private ArrayList<Object> list;
    private LayoutInflater inflater;
    private static final int QUESTION_ANSWER=0;
    private static final int QUESTION_ANSWER_WITH_IMAGE=1;
    private static final int SURVEY=2;
    private static final int IMAGE_POLL=3;
    private static final int LOADING=4;
    private ArrayList<String> answerLikeListFromLocalDatabase;
    private HashMap<String,Integer> imagePollLikeMapFromLocalDatabase;
    private HashMap<String,Integer> surveyParticipatedMapFromLocalDatabase;
    private  LocalDatabase localDatabase ;
    public  int [] fontId=new int[]{R.font.open_sans,R.font.abril_fatface,R.font.aclonica,R.font.bubbler_one,R.font.bitter,R.font.geo};


    HomeRecyclerViewAdapter(Context context, ArrayList<Object> list){
        this.context=context;
        this.list=list;
        if(context!=null) {
            inflater = LayoutInflater.from(context);
            localDatabase = new LocalDatabase(context.getApplicationContext());
            localDatabase=new LocalDatabase(context.getApplicationContext());
            answerLikeListFromLocalDatabase=localDatabase.getAnswerLikeModel();
            imagePollLikeMapFromLocalDatabase=localDatabase.getImagePollLikeModel();
            surveyParticipatedMapFromLocalDatabase=localDatabase.getSurveyParticipatedModel();
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
                viewHolder= new QuestionAnswerViewHolder(view);
                break;

            case QUESTION_ANSWER_WITH_IMAGE:
                view=inflater.inflate(R.layout.home_question_answer_image_item, viewGroup,false);
                viewHolder= new QuestionAnswerWithImageViewHolder(view);
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
                String recentAnswerId=((RootQuestionModel) list.get(i)).getRecentAnswerId();
                int likeCount=((RootQuestionModel) list.get(i)).getRecentAnswerLikeCount();
                int answerCount=((RootQuestionModel) list.get(i)).getAnswerCount();
                int fontUsed=((RootQuestionModel) list.get(i)).getFontUsed();

                if(MainActivity.answerLikeList!=null&&MainActivity.answerLikeList.size()>0){
                    if(MainActivity.answerLikeList.contains(recentAnswerId)){
                        ((QuestionAnswerViewHolder) holder).likeButton.setLiked(true);
                    }else {
                        ((QuestionAnswerViewHolder) holder).likeButton.setLiked(false);
                    }
                }else if(answerLikeListFromLocalDatabase!=null&&answerLikeListFromLocalDatabase.size()>0) {
                    if(answerLikeListFromLocalDatabase.contains(recentAnswerId)){
                        ((QuestionAnswerViewHolder) holder).likeButton.setLiked(true);
                    }else {
                        ((QuestionAnswerViewHolder) holder).likeButton.setLiked(false);
                    }
                }


                long timeOfAsking=((RootQuestionModel) list.get(i)).getTimeOfAsking();
                timeOfAsking=timeOfAsking==0?System.currentTimeMillis():timeOfAsking;
                String timeAgo=getTime(timeOfAsking, System.currentTimeMillis());

                if(anonymous){
                    ((QuestionAnswerViewHolder) holder).askerImageView.setImageDrawable(context.getDrawable(R.drawable.ic_placeholder));
                    ((QuestionAnswerViewHolder) holder).askerName.setText("Someone wants to know");
                    ((QuestionAnswerViewHolder) holder).askerBio.setText("");
                }

                else {
                    askerImageUrl=askerImageUrl!=null?askerImageUrl:"";
                    String url = Constants.PROFILE_PICTURE + "/" + ((RootQuestionModel) list.get(i)).getAskerId()+Constants.SMALL_THUMBNAIL;
                    StorageReference reference= FirebaseStorage.getInstance().getReference().child(url);
                    GlideApp.with(context).load(reference).placeholder(R.drawable.ic_placeholder).error(R.drawable.ic_placeholder)
                            .into(((QuestionAnswerViewHolder) holder).askerImageView);

                    askerName=askerName!=null?askerName:"Unknown";
                    ((QuestionAnswerViewHolder) holder).askerName.setText(askerName);

                    askerBio=askerBio!=null?askerBio:"";
                    ((QuestionAnswerViewHolder) holder).askerBio.setText(askerBio);
                }
                ((QuestionAnswerViewHolder) holder).question.setText(question);

                recentAnswererImageUrl=recentAnswererName!=null?recentAnswererImageUrl:"";

                String url = Constants.PROFILE_PICTURE + "/" + ((RootQuestionModel) list.get(i)).getRecentAnswererId()+Constants.SMALL_THUMBNAIL;
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
                String recentAnswerId=((RootQuestionModel) list.get(i)).getRecentAnswerId();
                String recentAnswererName = ((RootQuestionModel) list.get(i)).getRecentAnswererName();
                int likeCount = ((RootQuestionModel) list.get(i)).getRecentAnswerLikeCount();
                int answerCount = ((RootQuestionModel) list.get(i)).getAnswerCount();
                long timeOfAsking = ((RootQuestionModel) list.get(i)).getTimeOfAsking();
                int fontUsed=((RootQuestionModel) list.get(i)).getFontUsed();

                timeOfAsking=timeOfAsking==0?System.currentTimeMillis():timeOfAsking;
                String timeAgo = getTime(timeOfAsking, System.currentTimeMillis());


                if(MainActivity.answerLikeList!=null&&MainActivity.answerLikeList.size()>0){
                    if(MainActivity.answerLikeList.contains(recentAnswerId)){
                        ((QuestionAnswerWithImageViewHolder) holder).likeButton.setLiked(true);
                    }else {
                        ((QuestionAnswerWithImageViewHolder) holder).likeButton.setLiked(false);
                    }
                }else if(answerLikeListFromLocalDatabase!=null&&answerLikeListFromLocalDatabase.size()>0){
                    if(answerLikeListFromLocalDatabase.contains(recentAnswerId)){
                        ((QuestionAnswerWithImageViewHolder) holder).likeButton.setLiked(true);
                    }else {
                        ((QuestionAnswerWithImageViewHolder) holder).likeButton.setLiked(false);
                    }
                }

                if (answerImageUrl != null)
                    GlideApp.with(context).load(answerImageUrl)
                    .into(((QuestionAnswerWithImageViewHolder) holder).answerImageView);


                if (anonymous) {
                    ((QuestionAnswerWithImageViewHolder) holder).askerImageView.setImageDrawable(context.getDrawable(R.drawable.ic_placeholder));
                    ((QuestionAnswerWithImageViewHolder) holder).askerName.setText("Someone wants to know");
                    ((QuestionAnswerWithImageViewHolder) holder).askerBio.setText("");
                } else {

                    askerImageUrl=askerImageUrl!=null?askerImageUrl:"";
                    String url = Constants.PROFILE_PICTURE + "/" + ((RootQuestionModel) list.get(i)).getAskerId()+Constants.SMALL_THUMBNAIL;
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
                recentAnswererImageUrl=recentAnswererImageUrl!=null?recentAnswererImageUrl:"";


                String url = Constants.PROFILE_PICTURE + "/" + ((RootQuestionModel) list.get(i)).getRecentAnswererId()+Constants.SMALL_THUMBNAIL;
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

                handleClickListenerOfQuestionAnswerWithImage((QuestionAnswerWithImageViewHolder)holder,(RootQuestionModel)list.get(i));
            }

        }

        else if(holder.getItemViewType()==IMAGE_POLL&&holder instanceof ImagePollViewHolder){

            if(list.get(i) instanceof AskImagePollModel){

                String askerId=((AskImagePollModel) list.get(i)).getAskerId();
                String askerName=((AskImagePollModel) list.get(i)).getAskerName();
                String askerImageUrlLow=((AskImagePollModel) list.get(i)).getAskerImageUrlLow();
                String askerBio=((AskImagePollModel) list.get(i)).getAskerBio();
                String askerQuestion=((AskImagePollModel) list.get(i)).getQuestion();
                String image1Url=((AskImagePollModel) list.get(i)).getImage1Url();
                String image2Url=((AskImagePollModel) list.get(i)).getImage2Url();
                long timeOfPolling=((AskImagePollModel) list.get(i)).getTimeOfPolling();
                int image1LikeNo=((AskImagePollModel) list.get(i)).getImage1LikeNo();
                int image2LikeNo=((AskImagePollModel) list.get(i)).getImage2LikeNo();
                String imagePollId=((AskImagePollModel) list.get(i)).getImagePollId();
                ((ImagePollViewHolder) holder).image1.setTransitionName("image1"+i);
                ((ImagePollViewHolder) holder).image2.setTransitionName("image2"+i);

                if(imagePollId==null){
                    list.remove(i);
                    return;
                }

                    if(MainActivity.imagePollLikeMap!=null&&MainActivity.imagePollLikeMap.size()>0)
                    {
                        if (MainActivity.imagePollLikeMap.containsKey(imagePollId)) {
                            try {
                                ((ImagePollViewHolder) holder).showLike(context, image1LikeNo, image2LikeNo,
                                        MainActivity.imagePollLikeMap.get(imagePollId));
                            }catch (NullPointerException e){
                                ((ImagePollViewHolder) holder).showLike(context, image1LikeNo,
                                        image2LikeNo, 0);
                            }
                        }

                    }else if(imagePollLikeMapFromLocalDatabase!=null&&imagePollLikeMapFromLocalDatabase.size()>0){
                        if(imagePollLikeMapFromLocalDatabase.containsKey(imagePollId)){
                            try {
                                ((ImagePollViewHolder) holder).showLike(context, image1LikeNo, image2LikeNo,
                                        imagePollLikeMapFromLocalDatabase.get(imagePollId));
                            }catch (NullPointerException e){
                                ((ImagePollViewHolder) holder).showLike(context, image1LikeNo,
                                        image2LikeNo, 0);
                            }
                        }
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

                handleClickListenerOfImagePoll((ImagePollViewHolder) holder, (AskImagePollModel) list.get(i));

            }

        }
        else if(holder.getItemViewType()==SURVEY&&holder instanceof SurveyViewHolder){
            if(list.get(i) instanceof AskSurveyModel){
                handleClickListenerOfSurvey((SurveyViewHolder)holder, (AskSurveyModel) list.get(i));

                String askerId=((AskSurveyModel) list.get(i)).getAskerId();
                String askerName=((AskSurveyModel) list.get(i)).getAskerName();
                String askerImageUrlLow=((AskSurveyModel) list.get(i)).getAskerImageUrlLow();
                String askerBio=((AskSurveyModel) list.get(i)).getAskerBio();
                String question=((AskSurveyModel) list.get(i)).getQuestion();
                long timeOfSurvey=((AskSurveyModel) list.get(i)).getTimeOfSurvey();
                boolean option1=((AskSurveyModel) list.get(i)).isOption1();
                boolean option2=((AskSurveyModel) list.get(i)).isOption2();
                boolean option3=((AskSurveyModel) list.get(i)).isOption3();
                boolean option4=((AskSurveyModel) list.get(i)).isOption4();
                String option1Value=((AskSurveyModel) list.get(i)).getOption1Value();
                String option2Value=((AskSurveyModel) list.get(i)).getOption2Value();
                String option3Value=((AskSurveyModel) list.get(i)).getOption3Value();
                String option4Value=((AskSurveyModel) list.get(i)).getOption4Value();
                int option1Count=((AskSurveyModel) list.get(i)).getOption1Count();
                int option2Count=((AskSurveyModel) list.get(i)).getOption2Count();
                int option3Count=((AskSurveyModel) list.get(i)).getOption3Count();
                int optin4Count=((AskSurveyModel) list.get(i)).getOption4Count();
                int languageSelectedIndex=((AskSurveyModel) list.get(i)).getLanguageSelectedIndex();
                String surveyId=((AskSurveyModel) list.get(i)).getSurveyId();

                if(MainActivity.surveyParticipatedMap!=null&&MainActivity.surveyParticipatedMap.size()>0){
                    try {
                        if (MainActivity.surveyParticipatedMap.containsKey(surveyId)) {
                            ((SurveyViewHolder) holder).showSelection(MainActivity.surveyParticipatedMap.get(surveyId),
                                    (AskSurveyModel) list.get(i));
                        }
                    }catch (NullPointerException e){
                        ((SurveyViewHolder) holder).showSelection(0, (AskSurveyModel)list.get(i));

                    }
                }else if(surveyParticipatedMapFromLocalDatabase!=null&&surveyParticipatedMapFromLocalDatabase.size()>0){
                    try {
                        if (surveyParticipatedMapFromLocalDatabase.containsKey(surveyId)) {
                            ((SurveyViewHolder) holder).showSelection(surveyParticipatedMapFromLocalDatabase.get(surveyId),
                                    (AskSurveyModel) list.get(i));
                        }
                    }catch (NullPointerException e){
                        ((SurveyViewHolder) holder).showSelection(0, (AskSurveyModel)list.get(i));
                    }
                }else

                askerName=askerName==null?"Someone":askerName;
                askerImageUrlLow=askerImageUrlLow==null?"":askerImageUrlLow;
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
    public String getTime(long oldDate,long newDate){
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
                holder.onThreeDotClicked(context);
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
                holder.onThreeDotClicked(context);
            }
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
                holder.onThreeDotClicked(context);
            }
        });
        holder.wantToAnswerImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.onWantToAnswer(context,rootQuestionModel);
            }
        });
        holder.numberOfAnswerImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.onAnswersClicked(context, rootQuestionModel);
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
                holder.onThreeDotClicked(context);
            }
        });
        holder.wantToAnswerImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.onWantToAnswer(context, rootQuestionModel);
            }
        });
        holder.numberOfAnswerImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.onAnswersClicked(context, rootQuestionModel);
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
