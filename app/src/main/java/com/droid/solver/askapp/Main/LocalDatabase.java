package com.droid.solver.askapp.Main;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import android.util.Log;

import com.droid.solver.askapp.ImagePoll.AskImagePollModel;
import com.droid.solver.askapp.Notification.ImagePollModel;
import com.droid.solver.askapp.Notification.QuestionModel;
import com.droid.solver.askapp.Notification.SurveyModel;
import com.droid.solver.askapp.Question.Community;
import com.droid.solver.askapp.Question.Follower;
import com.droid.solver.askapp.Question.Following;
import com.droid.solver.askapp.Question.RootQuestionModel;
import com.droid.solver.askapp.Question.UserQuestionModel;
import com.droid.solver.askapp.Survey.AskSurveyModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class LocalDatabase extends SQLiteOpenHelper {

    private static final String DATABASE_NAME="database";
    private static final int DATABASE_VERSION=8;

    private final String USER_INFO_TABLE="user_info_table";
    private final String USER_QUESTION_MODEL="question_asked_table";
    private final String ROOT_QUESTION_MODEL="root_question_model";
    private final String SURVEY_ASKED_TABLE="survey_asked_table";
    private final String IMAGE_POLL_TABLE="image_poll_table";
    private final String ANSWER_LIKE_TABLE="answer_like_table";
    private final String SURVEY_PARTICIPATED_TABLE="survey_participated_table";
    private final String IMAGE_POLL_LIKE_TABLE="image_poll_like_table";
    private final String FOLLOWER_TABLE="follower_table";
    private final String FOLLOWING_TABLE="following_table";
    private final String COMMUNITY_TABLE="community_table";
    private final String IMAGE_POLL_REPORT="image_poll_report";
    private final String SURVEY_REPORT="survey_report";
    private final String QUESTION_REPORT="question_report";

    private final String NOTIFICATION_QUESTION="notification_question";
    private final String NOTIFICATION_IMAGE_POLL="notification_image_poll";
    private final String NOTIFICATION_SURVEY="notification_survey";
    private final String NOTIFICATION="notifiction";


    //user main document
    private final String userId="userId";
    private final String userName="userName";
    private final String profilePicUrlLow="profilePicUrlLow";
    private final String profilePicUrlHigh="profilePicUrlHigh";
    private final String bio="about";
    private final String point="point";
    private final String country="country";
    private final String language="language";
    private final String interest="interest";
    private final String followerCount="followerCount";
    private final String followingCount="followingCount";
    private final String fontUsed="fontUsed";

    //question asked  document
    private final String askerId="askerId";
    private final String askerName="askerName";
    private final String anonymous="anonymous";
    private final String askerImageUrlLow="askerImageUrlLow";
    private final String askerImageUrlHigh="askerImageUrlHigh";
    private final String askerBio="askerBio";
    private final String questionId="questionId";//primary key not null
    private final String question="question";
    private final String questionType="questionType";
    private final String timeOfAsking="timeOfAsking";
    private final String recentAnswererId="recentAnswererId";
    private final String recentAnswererImageUrlLow="recentAnswererImageUrlLow";
    private final String recentAnswererName="recentAnswererName";
    private final String recentAnswererBio="recentAnswererBio";
    private final String recentAnsweId="recentAnswerId";
    private final String recentAnswer="recentAnswer";
    private final String recentAnswerImageAttached="recentAnswerImageAttached";
    private final String recentAnswerImageUrl="recentAnswerImageUrl";
    private final String answerCount="answerCount";
    private final String recentAnswerLikeCount="recentAnswerLikeCount";

    private final String userNotified="user_notified";
    private final String userImageUrlLow="userImageUrlLow";
    private final String userBio="userBio";

    //survey document
    private final String surveyId="surveyId";//primary key
    private final String timeOfSurvey="timeOfSurvey";
    private final String option1="option1";
    private final String option2="option2";
    private final String option3="option3";
    private final String option4="option4";
    private final String option1Value="option1Value";
    private final String option2Value="option2Value";
    private final String option3Value="option3Value";
    private final String option4Value="option4Value";
    private final String option1Count="option1Count";
    private final String option2Count="option2Count";
    private final String option3Count="option3Count";
    private final String option4Count="option4Count";
    private final String languageSelectedIndex="languageSelectedIndex";

    //image poll document
    private final String image1Url="image1Url";
    private final String image2Url="image2Url";
    private final String timeOfPolling="timeOfPolling";
    private final String image1LikeNo="image1LikeNo";
    private final String image2LikeNo="image2LikeNo";
    private final String imagePollId="imagePollId";//primary key

    //answer like document
    private final String answerId="questionLikeId";
    //survey participated document
    private final String surveyParticipatedId="surveyParticipatedId";
    private final String optionSelected="optionSelected";
    //imagepoll like document

    //Follower document
    private final  String followerId="followerId";
    private final String followerName="followerName";
    private final String followerImageUrl="followerImageUrl";
    private final String followerBio="followerBio";

    //Following document
    private final String  followingId="followingId";
    private final String followingName="followingName";
    private final String followingImageUrl="followingImageUrl";
    private final String followingBio="followingBio";

    //Community document
    private final String  communityId="communityId";
    private final String communityImageUrl="communityImageUrl";
    private final String communityName="communityName";
    private final String  communityMemberCount="communityMemberCount";

    //notification
    private final String likerId="likerId";
    private final String likerName="likerName";
    private final String likerImageUrl="likerImageUrl";
    private final String likerBio="likerBio";
    private final String type="type";
    private final String containVioloanceOrAdult="containVioloanceOrAdult";
    private final String reported="reported";
    private final String notifiedTime="notifiedTime";
    private final String TYPE_QUESTION="question";
    private final String TYPE_IMAGE_POLL="imagePoll";
    private final String TYPE_SURVEY="survey";
    private final String notificationId="notificationId";
    private final String notificationType="notificationType";

    //create table string
    private  final String CREATE_TABLE_USER_INFO_TABLE="CREATE TABLE "+USER_INFO_TABLE+"("+userId+" TEXT PRIMARY KEY ,"+
    userName+" TEXT ,"+profilePicUrlLow+" TEXT ,"+profilePicUrlHigh+" TEXT ,"+bio+" TEXT ,"+point +" INTEGER ,"+country+" TEXT ,"+
            language+" TEXT ,"+interest+" TEXT ,"+followerCount+" INTEGER ,"+followingCount+" INTEGER "+")";

    private  final String CREATE_TABLE_USER_QUESTION_MODEL="CREATE TABLE "+USER_QUESTION_MODEL+"("+questionId+" TEXT PRIMARY KEY,"+
            question+" TEXT ,"+questionType+" TEXT ,"+timeOfAsking+" INTEGER ,"+userId+" TEXT ,"+userName+" TEXT ,"+userImageUrlLow+" TEXT ,"+
            userBio+" TEXT ,"+answerCount+" INTEGER ,"+anonymous+" INTEGER ,"+userNotified+" INTEGER "+")";

    private  final String CREATE_TABLE_ROOT_QUESTION_MODEL="CREATE TABLE "+ROOT_QUESTION_MODEL+"("+askerId+" TEXT ,"+askerName+" TEXT ,"+
            askerImageUrlLow+" TEXT ,"+anonymous+" INTEGER ,"+askerBio+" TEXT ,"+questionId+" TEXT ,"+question+" TEXT ,"+
            questionType+" TEXT ,"+timeOfAsking+" INTEGER ,"+recentAnswererId+" TEXT ,"+recentAnswererImageUrlLow+" TEXT ,"+recentAnswererName+" TEXT ,"
            +recentAnswererBio+" TEXT ,"+recentAnsweId+" TEXT ,"+recentAnswer+" TEXT ,"+recentAnswerImageAttached+" INTEGER ,"+recentAnswerImageUrl+
            " TEXT ,"+answerCount+" INTEGER ,"+recentAnswerLikeCount+" INTEGER ,"+fontUsed +"INTEGER"+")";

    private  final String CREATE_TABLE_SURVEY_ASKED_TABLE="CREATE TABLE "+SURVEY_ASKED_TABLE+"("+askerId+" TEXT ,"+askerName+" TEXT ,"+
            askerImageUrlLow+" TEXT ,"+askerBio+" TEXT ,"+question+" TEXT ,"+timeOfSurvey+" INTEGER ,"+option1+" INTEGER ,"+option2+" INTEGER ,"+
            option3+" INTEGER ,"+option4+" INTEGER ,"+option1Value+" TEXT ,"+option2Value+" TEXT ,"+option3Value+" TEXT ,"+option4Value+" TEXT ,"+
            option1Count+" INTEGER ,"+option2Count+" INTEGER ,"+option3Count+" INTEGER ,"+option4Count+" INTEGER ,"+languageSelectedIndex+" INTEGER ,"+
            surveyId+" TEXT "+")";

    private  final String CREATE_TABLE_IMAGE_POLL_TABLE="CREATE TABLE "+IMAGE_POLL_TABLE+"("+askerId+" TEXT ,"+askerName+" TEXT ,"+
            askerImageUrlLow+" TEXT ,"+askerBio+" TEXT ,"+question+" TEXT ,"+image1Url+" TEXT ,"+image2Url+" TEXT ,"+timeOfPolling+" INTEGER ,"+
            image1LikeNo+" INTEGER ,"+image2LikeNo+" INTEGER ,"+imagePollId+" TEXT PRIMARY KEY"+")";

    private  final String CREATE_TABLE_ANSWER_LIKE_TABLE="CREATE TABLE "+ANSWER_LIKE_TABLE+"("+answerId+" TEXT PRIMARY KEY"+")";

    private  final String CREATE_TABLE_SURVEY_PARTICIPATED_TABLE="CREATE TABLE "+SURVEY_PARTICIPATED_TABLE+"("+surveyParticipatedId+
            " TEXT PRIMARY KEY,"+optionSelected+" INTEGER "+")";

    private  final String CREATE_TABLE_IMAGE_POLL_LIKE_TABLE="CREATE TABLE "+IMAGE_POLL_LIKE_TABLE+"("+imagePollId+" TEXT PRIMARY KEY,"+
            optionSelected +" INTEGER "+")";

    private final String CREATE_TABLE_FOLLOWER_TABLE="CREATE TABLE "+FOLLOWER_TABLE+"("+followerId+" TEXT PRIMARY KEY ,"+
            followerName+" TEXT ,"+followerImageUrl+" TEXT ,"+followerBio+" TEXT "+")";

    private final String CREATE_TABLE_FOLLOWING_TABLE="CREATE TABLE "+FOLLOWING_TABLE+"("+followingId+" TEXT PRIMARY KEY,"+
            followingName+" TEXT ,"+followingImageUrl+" TEXT ,"+followingBio+" TEXT "+")";

    private final String CREATE_TABLE_COMMUNITY_TABLE="CREATE TABLE "+COMMUNITY_TABLE+"("+communityId+"TEXT PRIMARY KEY, "+communityName+" TEXT ,"+
            communityImageUrl+" TEXT ,"+communityMemberCount+" INTEGER "+")";

    private  final String CREATE_TABLE_IMAGE_POLL_REPORT_TABLE="CREATE TABLE "+IMAGE_POLL_REPORT+"("+imagePollId+" TEXT PRIMARY KEY "+")";

    private  final String CREATE_TABLE_SURVEY_REPORT_TABLE="CREATE TABLE "+SURVEY_REPORT+"("+surveyId+" TEXT PRIMARY KEY "+")";

    private  final String CREATE_TABLE_QUESTION_REPORT_TABLE="CREATE TABLE "+QUESTION_REPORT+"("+questionId+" TEXT PRIMARY KEY"+")";

    private final String CREATE_TABLE_NOTIFICATION_QUESTION="CREATE TABLE "+NOTIFICATION_QUESTION+"("+questionId+" TEXT PRIMARY KEY ,"+
            askerId+" TEXT,"+likerId+" TEXT ,"+likerName+" TEXT ,"+likerImageUrl+" TEXT ,"+likerBio+" TEXT ,"+type +"TEXT "+")";

    private final String CREATE_TABLE_NOTIFICATION_IMAGE_POLL="CREATE TABLE "+NOTIFICATION_IMAGE_POLL+"("+imagePollId+" TEXT PRIMARY KEY,"+
            question+" TEXT ,"+askerId+" TEXT ,"+askerName+" TEXT ,"+askerBio+" TEXT ,"+askerImageUrlLow+" TEXT ,"+image1Url+" TEXT ,"+
            image2Url+" TEXT ,"+image1LikeNo+" INTEGER ,"+image2LikeNo+" INTEGER ,"+ containVioloanceOrAdult+" INTEGER ,"+reported+" INTEGER ,"+
            type +" TEXT ,"+notifiedTime+" INTEGER "+")";

    private final String CREATE_TABLE_NOTIFICATION_SURVEY="CREATE TABLE "+NOTIFICATION_SURVEY+"("+surveyId+" TEXT PRIMARY KEY ,"+
            askerId+" TEXT ,"+askerName+" TEXT ,"+askerBio+" TEXT ,"+askerImageUrlLow+" TEXT ,"+question+" TEXT ,"+timeOfSurvey+" INTEGER ,"+
            option1Value+" TEXT ,"+option2Value+" TEXT ,"+option3Value+" TEXT ,"+option4Value+" TEXT ,"+option1Count+" INTEGER ,"+
            option2Count+" INTEGER ,"+option3Count+" INTEGER ,"+option4Count+" INTEGER ,"+option1+" INTEGER ,"+option2+" INTEGER ,"+
            option3+" INTEGER ,"+option4+" INTEGER ,"+type+" TEXT ,"+notifiedTime+" INTEGER "+")";

    private final String CREATE_TABLE_NOTIFICATION="CREATE TABLE "+NOTIFICATION+"("+"Id INTEGER PRIMARY KEY AUTOINCREMENT, "
            +notificationId+" TEXT,"+ notificationType+" TEXT "+")";



    public LocalDatabase(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_USER_INFO_TABLE);
        sqLiteDatabase.execSQL(CREATE_TABLE_USER_QUESTION_MODEL);
        sqLiteDatabase.execSQL(CREATE_TABLE_ROOT_QUESTION_MODEL);
        sqLiteDatabase.execSQL(CREATE_TABLE_SURVEY_ASKED_TABLE);
        sqLiteDatabase.execSQL(CREATE_TABLE_IMAGE_POLL_TABLE);
        sqLiteDatabase.execSQL(CREATE_TABLE_ANSWER_LIKE_TABLE);
        sqLiteDatabase.execSQL(CREATE_TABLE_SURVEY_PARTICIPATED_TABLE);
        sqLiteDatabase.execSQL(CREATE_TABLE_IMAGE_POLL_LIKE_TABLE);
        sqLiteDatabase.execSQL(CREATE_TABLE_FOLLOWER_TABLE);
        sqLiteDatabase.execSQL(CREATE_TABLE_FOLLOWING_TABLE);
        sqLiteDatabase.execSQL(CREATE_TABLE_COMMUNITY_TABLE);
        sqLiteDatabase.execSQL(CREATE_TABLE_IMAGE_POLL_REPORT_TABLE);
        sqLiteDatabase.execSQL(CREATE_TABLE_SURVEY_REPORT_TABLE);
        sqLiteDatabase.execSQL(CREATE_TABLE_QUESTION_REPORT_TABLE);
        sqLiteDatabase.execSQL(CREATE_TABLE_NOTIFICATION_QUESTION);
        sqLiteDatabase.execSQL(CREATE_TABLE_NOTIFICATION_IMAGE_POLL);
        sqLiteDatabase.execSQL(CREATE_TABLE_NOTIFICATION_SURVEY);
        sqLiteDatabase.execSQL(CREATE_TABLE_NOTIFICATION);
        Log.i("TAG", "table created :");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+USER_INFO_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+USER_QUESTION_MODEL);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ROOT_QUESTION_MODEL);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+SURVEY_ASKED_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+IMAGE_POLL_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ANSWER_LIKE_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+SURVEY_PARTICIPATED_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+IMAGE_POLL_LIKE_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+FOLLOWER_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+FOLLOWING_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+COMMUNITY_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+IMAGE_POLL_REPORT);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+SURVEY_REPORT);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+QUESTION_REPORT);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+NOTIFICATION_QUESTION);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+NOTIFICATION_IMAGE_POLL);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+NOTIFICATION_SURVEY);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+NOTIFICATION);
        Log.i("TAG", "table upgrade ");
        onCreate(sqLiteDatabase);

    }

     public  void insertUserInfoModel(UserInfoModel model){

        SQLiteDatabase database=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        if(database.isOpen()) {
            values.put(userId, model.getUserId());
            values.put(userName, model.getUserName());
            values.put(askerImageUrlLow, model.getProfilePicUrlLow());
            values.put(profilePicUrlHigh, model.getProfilePicUrlHigh());
            values.put(bio, model.getBio());
            values.put(point, model.getPoint());
            values.put(country, model.getCountry());
            String mlanguage = model.getLanguage();
            values.put(language, mlanguage);

            ArrayList<String> minterest = model.getInterest();
            StringBuilder builder = new StringBuilder();

            if(minterest!=null) {
                for (int i = 0; i < minterest.size(); i++) {
                    builder.append(minterest.get(i));
                    builder.append("@");
                }
            }
            values.put(interest, builder.toString());
            values.put(followerCount, model.getFollowerCount());
            values.put(followingCount, model.getFollowingCount());

            database.insert(USER_INFO_TABLE, null, values);
            values.clear();
            database.close();
        }

    }
     public void clearUserInfoModel(){
        SQLiteDatabase database=this.getWritableDatabase();
        database.delete(USER_INFO_TABLE, null, null);
        if(database.isOpen())
        database.close();
    }
     public UserInfoModel getUserInfoModel(){
        SQLiteDatabase database=this.getWritableDatabase();
        String query="SELECT * FROM "+USER_INFO_TABLE+" ORDER BY "+userId+" DESC  LIMIT 1";
        Cursor cursor=database.rawQuery(query, null);
        if(cursor!=null)
            cursor.moveToFirst();

        String muserId=cursor.getString(cursor.getColumnIndex(userId));
        String muserName=cursor.getString(cursor.getColumnIndex(userName));
        String mprofilePicUrlLow=cursor.getString(cursor.getColumnIndex(profilePicUrlLow));
        String mprofilePicUrlHigh=cursor.getString(cursor.getColumnIndex(profilePicUrlHigh));
        String mbio=cursor.getString(cursor.getColumnIndex(bio));
        int mpoint=cursor.getInt(cursor.getColumnIndex(point));
        String mlanguage=cursor.getString(cursor.getColumnIndex(language));
        String minterest=cursor.getString(cursor.getColumnIndex(interest));
        int mfollowerCount=cursor.getInt(cursor.getColumnIndex(followerCount));
        int mfollowingCount=cursor.getInt(cursor.getColumnIndex(followingCount));

        String [] interestArr=minterest.split("@");
        ArrayList<String> interestList=new ArrayList<>();
        interestList=(ArrayList<String>)Arrays.asList(interestArr);

        UserInfoModel infoModel=new UserInfoModel(
        muserId,muserName ,mprofilePicUrlLow , mprofilePicUrlHigh, mbio,
                mpoint,country,mlanguage,interestList,mfollowerCount,mfollowingCount);

        if(!cursor.isClosed())
            cursor.close();
        database.close();
        return infoModel;

    }

     public void insertUserQuestionModel(ArrayList<UserQuestionModel> list){
        SQLiteDatabase database=this.getWritableDatabase();
        if(database.isOpen()) {
            if (list != null)
                for (int i = 0; i < list.size(); i++) {
                    UserQuestionModel model = list.get(i);
                    ContentValues values = new ContentValues();
                    values.put(questionId, model.getQuestionId());
                    values.put(question, model.getQuestion());

                    StringBuilder builder = new StringBuilder();
                    if (model.getQuestionType() != null)
                        for (int j = 0; j < model.getQuestionType().size(); j++) {
                            builder.append(model.getQuestionType().get(i));
                            builder.append("@");
                        }

                    values.put(timeOfAsking, model.getTimeOfAsking());
                    values.put(userId, model.getUserId());
                    values.put(userName, model.getUserName());
                    values.put(userImageUrlLow, model.getUserImageUrlLow());
                    values.put(userBio, model.getUserBio());
                    values.put(answerCount, model.getAnswerCount());
                    int manonymous = model.isAnonymous() ? 1 : 0;
                    values.put(anonymous, manonymous);
                    int muserNotified = model.isUserNotified() ? 1 : 0;
                    values.put(userNotified, muserNotified);
                    database.insert(USER_QUESTION_MODEL, null, values);
                    values.clear();
                }


            if (database.isOpen())
                database.close();

        }
     }
     public int  clearUserQuestionModel(){
         SQLiteDatabase database=this.getWritableDatabase();
         int numberOfRowsDeleted=database.delete(USER_QUESTION_MODEL, null, null);
         database.close();
         return numberOfRowsDeleted;

     }
     public ArrayList<UserQuestionModel> getUserQuestionModelList(){

        ArrayList<UserQuestionModel> modelList=new ArrayList<>();
        SQLiteDatabase database=this.getWritableDatabase();
        String query="SELECT * FROM "+USER_QUESTION_MODEL;
        Cursor cursor=database.rawQuery(query, null);

        if(cursor.moveToFirst()){
            do{
                String mquestionId=cursor.getString(cursor.getColumnIndex(questionId));
                String mquestion=cursor.getString(cursor.getColumnIndex(question));
                String tempQuestionType=cursor.getString(cursor.getColumnIndex(questionType));
                String [] tempQuestionTypeArr=tempQuestionType.split("@");
                List<String> mquestionType= Arrays.asList(tempQuestionTypeArr);
                long mtimeOfAsking=cursor.getLong(cursor.getColumnIndex(timeOfAsking));
                String muserId=cursor.getString(cursor.getColumnIndex(userId));
                String muserName=cursor.getString(cursor.getColumnIndex(userName));
                String muserImageUrlLow=cursor.getString(cursor.getColumnIndex(userImageUrlLow));
                String muserBio=cursor.getString(cursor.getColumnIndex(userBio));
                int manswerCount=cursor.getInt(cursor.getColumnIndex(answerCount));
                int tempAnomymous=cursor.getInt(cursor.getColumnIndex(anonymous));
                boolean manonymous=tempAnomymous==1;
                int tempUserNotified=cursor.getInt(cursor.getColumnIndex(userNotified));
                boolean muserNotified=tempUserNotified==1;

                UserQuestionModel questionModel=new UserQuestionModel(muserId, muserName,
                        muserImageUrlLow, muserBio, mtimeOfAsking, mquestionId,
                        mquestion, mquestionType, manswerCount, manonymous, muserNotified);

                modelList.add(questionModel);
            }while (cursor.moveToNext());

            if(!cursor.isClosed())
                cursor.close();
            database.close();
            return modelList;
        }
        return null;

     }
     public void insertRootQuestionModel(ArrayList<RootQuestionModel> list){
        SQLiteDatabase database=this.getWritableDatabase();
        if(database.isOpen()){
            if(list!=null)
                for(int i=0;i<list.size();i++){
                    ContentValues values = new ContentValues();
                    RootQuestionModel rootQuestionModel=list.get(i);
                    values.put(askerId,rootQuestionModel.getAskerId());
                    values.put(askerName, rootQuestionModel.getAskerName());
                    values.put(askerImageUrlLow, rootQuestionModel.getAskerImageUrlLow());
                    int  manonymous=rootQuestionModel.isAnonymous()?1:0;
                    values.put(anonymous, manonymous);
                    values.put(askerBio, rootQuestionModel.getAskerBio());
                    values.put(questionId, rootQuestionModel.getQuestionId());
                    values.put(question, rootQuestionModel.getQuestion());

                    StringBuilder builder = new StringBuilder();
                    if(rootQuestionModel.getQuestionType()!=null)
                    for(int j=0;j<rootQuestionModel.getQuestionType().size();j++){
                        builder.append(rootQuestionModel.getQuestionType().get(j));
                        builder.append("@");
                    }
                    values.put(questionType, builder.toString());
                    values.put(timeOfAsking,rootQuestionModel.getTimeOfAsking());
                    values.put(recentAnswererId, rootQuestionModel.getRecentAnswererId());
                    values.put(recentAnswererImageUrlLow, rootQuestionModel.getRecentAnswererImageUrlLow());
                    values.put(recentAnswererName, rootQuestionModel.getRecentAnswererName());
                    values.put(recentAnswererBio, rootQuestionModel.getRecentAnswererBio());
                    values.put(recentAnswererId, rootQuestionModel.getRecentAnswerId());
                    values.put(recentAnswer, rootQuestionModel.getRecentAnswer());
                    boolean temprecentAnswerImageAttached=rootQuestionModel.isRecentAnswerImageAttached();
                    int mrecentAnswerImageAttached=temprecentAnswerImageAttached?1:0;
                    values.put(recentAnswerImageAttached, mrecentAnswerImageAttached);
                    values.put(recentAnswerImageUrl, rootQuestionModel.getRecentAnswerImageUrl());
                    values.put(answerCount, rootQuestionModel.getAnswerCount());
                    values.put(recentAnswerLikeCount, rootQuestionModel.getRecentAnswerLikeCount());
                    values.put(fontUsed, rootQuestionModel.getFontUsed());

                    database.insert(ROOT_QUESTION_MODEL, null, values);

                    values.clear();
                }
            if(database.isOpen())
                database.close();
        }

     }
     public int clearRootQuestionModel(){
        SQLiteDatabase database=this.getWritableDatabase();

        int numberOfRowDeleted=database.delete(ROOT_QUESTION_MODEL, null, null);
        if(database.isOpen())
            database.close();
        return numberOfRowDeleted;
     }
     public ArrayList<RootQuestionModel> getRootQuestionModelList(){
        ArrayList<RootQuestionModel> list=new ArrayList<>();
        SQLiteDatabase database =this.getWritableDatabase();
        String query="SELECT * FROM "+ROOT_QUESTION_MODEL;
        Cursor cursor=database.rawQuery(query, null);
        if(cursor.moveToFirst()){
            do{
                String maskerId=cursor.getString(cursor.getColumnIndex(askerId));
                String maskerName=cursor.getString(cursor.getColumnIndex(askerName));
                String maskerImageUrlLow=cursor.getString(cursor.getColumnIndex(askerImageUrlLow));
                int tempAnonymous=cursor.getInt(cursor.getColumnIndex(anonymous));
                boolean manonymous=tempAnonymous==1;
                String maskerBio=cursor.getString(cursor.getColumnIndex(askerBio));
                String mquestionId=cursor.getString(cursor.getColumnIndex(questionId));
                String mquestion=cursor.getString(cursor.getColumnIndex(question));
                String tempQuestionType=cursor.getString(cursor.getColumnIndex(questionType));
                String [] tempArray=tempQuestionType.split("@");
                List<String> mquestionType=Arrays.asList(tempArray);
                long mtimeOfAsking=cursor.getLong(cursor.getColumnIndex(timeOfAsking));
                String mrecentAnswererId=cursor.getString(cursor.getColumnIndex(recentAnswererId));
                String mrecentAnswererImageUrlLow=cursor.getString(cursor.getColumnIndex(recentAnswererImageUrlLow));
                String mrecentAnswererName=cursor.getString(cursor.getColumnIndex(recentAnswererName));
                String mrecentAnswererBio=cursor.getString(cursor.getColumnIndex(recentAnswererBio));
                String mrecentAnswerId=cursor.getString(cursor.getColumnIndex(recentAnswererId));
                String mrecentAnswer=cursor.getString(cursor.getColumnIndex(recentAnswer));
                int tempRecentAnswerImageAttached=cursor.getInt(cursor.getColumnIndex(recentAnswerImageAttached));
                boolean mrecentAnswerImageAtached=tempRecentAnswerImageAttached==1;
                String mrecentAnswerImageUrl=cursor.getString(cursor.getColumnIndex(recentAnswerImageUrl));
                int manswerCount=cursor.getInt(cursor.getColumnIndex(answerCount));
                int mrecentAnswerLikeCount=cursor.getInt(cursor.getColumnIndex(recentAnswerLikeCount));
                int mfontUsed=cursor.getInt(cursor.getColumnIndex(fontUsed));

                RootQuestionModel rootQuestionModel=new RootQuestionModel(maskerId, maskerName, maskerImageUrlLow,
                        manonymous, maskerBio, mquestionId, mquestion, mquestionType,
                        mtimeOfAsking, mrecentAnswererId, mrecentAnswererImageUrlLow,
                        mrecentAnswererName, mrecentAnswererBio, mrecentAnswerId,
                        mrecentAnswer, mrecentAnswerImageAtached, mrecentAnswerImageUrl,
                        manswerCount, mrecentAnswerLikeCount,mfontUsed);
                list.add(rootQuestionModel);


            }while (cursor.moveToNext());
            if(database.isOpen())
                database.close();
            if(!cursor.isClosed())
                cursor.close();
            return list;
        }
        return null;
     }

     public void insertSurveyAskedModel(ArrayList<AskSurveyModel> list){
        SQLiteDatabase database=this.getWritableDatabase();
        if(database.isOpen()){
            if(list!=null){
                for(int i=0;i<list.size();i++){
                    AskSurveyModel model=list.get(i);
                    ContentValues values=new ContentValues();
                    values.put(askerId, model.getAskerId());
                    values.put(askerName, model.getAskerName());
                    values.put(askerImageUrlLow, model.getAskerImageUrlLow());
                    values.put(askerBio, model.getAskerBio());
                    values.put(question, model.getQuestion());
                    values.put(timeOfSurvey, model.getTimeOfSurvey());

                    boolean temp=model.isOption1();
                    int mtemp=temp?1:0;
                    values.put(option1, mtemp);

                    temp=model.isOption2();
                    mtemp=temp?1:0;
                    values.put(option2, mtemp);

                    temp=model.isOption3();
                    mtemp=temp?1:0;
                    values.put(option3, mtemp);

                    temp=model.isOption4();
                    mtemp=temp?1:0;
                    values.put(option4, mtemp);

                    values.put(option1Value, model.getOption1Value());
                    values.put(option2Value, model.getOption2Value());
                    values.put(option3Value, model.getOption3Value());
                    values.put(option4Value, model.getOption4Value());
                    values.put(option1Count, model.getOption1Count());
                    values.put(option2Count , model.getOption2Count());
                    values.put(option3Count, model.getOption3Count());
                    values.put(option4Count, model.getOption4Count());
                    values.put(languageSelectedIndex, model.getLanguageSelectedIndex());
                    values.put(surveyId, model.getSurveyId());

                    database.insert(SURVEY_ASKED_TABLE, null, values);
                    values.clear();
                }
                if(database.isOpen())
                    database.close();
            }

        }

     }
     public void clearSurveyAskedModel(){
        SQLiteDatabase database=this.getWritableDatabase();
        database.delete(SURVEY_ASKED_TABLE, null, null);
        if(database.isOpen())
            database.close();
        database.close();

     }
     public ArrayList<AskSurveyModel> getSurveyAskedModel(){
        ArrayList<AskSurveyModel> list=new ArrayList<>();
        SQLiteDatabase database=this.getWritableDatabase();
        String query="SELECT * FROM "+SURVEY_ASKED_TABLE;
        Cursor cursor=database.rawQuery(query, null);
        if(cursor.moveToFirst()){
            do{
                String maskerUid=cursor.getString(cursor.getColumnIndex(askerId));
                String maskerName=cursor.getString(cursor.getColumnIndex(askerName));
                String maskerImageUrl=cursor.getString(cursor.getColumnIndex(askerImageUrlLow));
                String maskerBio=cursor.getString(cursor.getColumnIndex(askerBio));
                String mquestion=cursor.getString(cursor.getColumnIndex(question));
                long mtimeOfSurvey=cursor.getLong(cursor.getColumnIndex(timeOfSurvey));
                boolean moption1=cursor.getInt(cursor.getColumnIndex(option1))==1;
                boolean moption2=cursor.getInt(cursor.getColumnIndex(option2))==1;
                boolean moption3=cursor.getInt(cursor.getColumnIndex(option3))==1;
                boolean moption4=cursor.getInt(cursor.getColumnIndex(option4))==1;
                String moption1Value=cursor.getString(cursor.getColumnIndex(option1Value));
                String moption2Value=cursor.getString(cursor.getColumnIndex(option2Value));
                String moption3Value=cursor.getString(cursor.getColumnIndex(option3Value));
                String moption4Value=cursor.getString(cursor.getColumnIndex(option4Value));
                int moption1Count=cursor.getInt(cursor.getColumnIndex(option1Count));
                int moption2Count=cursor.getInt(cursor.getColumnIndex(option2Count));
                int moption3Count=cursor.getInt(cursor.getColumnIndex(option3Count));
                int moption4Count=cursor.getInt(cursor.getColumnIndex(option4Count));
                int mlanguageSelectedIndex=cursor.getInt(cursor.getColumnIndex(languageSelectedIndex));
                String msurveyId=cursor.getString(cursor.getColumnIndex(surveyId));

                AskSurveyModel model = new AskSurveyModel(maskerUid, maskerName, maskerImageUrl,maskerBio,
                        mquestion, mtimeOfSurvey, moption1,
                        moption2, moption3, moption4, moption1Value, moption2Value,
                        moption3Value, moption4Value, moption1Count, moption2Count,
                        moption3Count, moption4Count, mlanguageSelectedIndex, msurveyId);
                list.add(model);

            }while (cursor.moveToNext());
            if(!cursor.isClosed())
                cursor.close();
            database.close();

            return list;
        }
        return null;
     }

     public void insertImagePollModel(ArrayList<AskImagePollModel> list){
        SQLiteDatabase database=this.getWritableDatabase();
        if(database.isOpen()){
            if(list!=null){
                for(int i=0;i<list.size();i++){
                    AskImagePollModel pollModel=list.get(i);
                    ContentValues values = new ContentValues();
                    values.put(askerId, pollModel.getAskerId());
                    values.put(askerName, pollModel.getAskerName());
                    values.put(askerImageUrlLow, pollModel.getAskerImageUrlLow());
                    values.put(askerBio, pollModel.getAskerBio());
                    values.put(question, pollModel.getQuestion());
                    values.put(image1Url,pollModel.getImage1Url());
                    values.put(image2Url, pollModel.getImage2Url());
                    values.put(timeOfPolling,pollModel.getTimeOfPolling());
                    values.put(image1LikeNo, pollModel.getImage1LikeNo());
                    values.put(image2LikeNo, pollModel.getImage2LikeNo());
                    values.put(imagePollId, pollModel.getImagePollId());

                    database.insert(IMAGE_POLL_TABLE, null, values);
                    values.clear();
                }

            }
            if(database.isOpen())
                database.close();
        }

     }
     public void clearImagePollModel(){
        SQLiteDatabase database = this.getWritableDatabase();
        database.delete(IMAGE_POLL_TABLE, null, null);
        if(database.isOpen())
            database.close();
     }
     public ArrayList<AskImagePollModel> getImagePollModel(){

        ArrayList<AskImagePollModel> list=new ArrayList<>();
        SQLiteDatabase database = this.getWritableDatabase();
        String query="SELECT * FROM "+IMAGE_POLL_TABLE;
        Cursor cursor=database.rawQuery(query, null);
        if(cursor.moveToNext()){
            do {
                String maskerId=cursor.getString(cursor.getColumnIndex(askerId));
                String maskerName=cursor.getString(cursor.getColumnIndex(askerName));
                String maskerImageUrlLow=cursor.getString(cursor.getColumnIndex(askerImageUrlLow));
                String maskerBio=cursor.getString(cursor.getColumnIndex(askerBio));
                String mquestion=cursor.getString(cursor.getColumnIndex(question));
                String mimage1Url=cursor.getString(cursor.getColumnIndex(image1Url));
                String mimage2Url=cursor.getString(cursor.getColumnIndex(image2Url));
                long mtimeOfPolling=cursor.getLong(cursor.getColumnIndex(timeOfPolling));
                int mimage1LikeNo=cursor.getInt(cursor.getColumnIndex(image1LikeNo));
                int mimage2LikeNo=cursor.getInt(cursor.getColumnIndex(image2LikeNo));
                String mimagePollId=cursor.getString(cursor.getColumnIndex(imagePollId));

                AskImagePollModel model = new AskImagePollModel(
                maskerId,maskerName ,maskerImageUrlLow ,maskerBio , mquestion,
                        mimage1Url, mimage2Url, mtimeOfPolling, mimage1LikeNo, mimage2LikeNo,mimagePollId);
                list.add(model);

            }while (cursor.moveToNext());
            if(database.isOpen())
                database.close();
            if(!cursor.isClosed())
                cursor.close();
            return list;
        }
        return null;
     }

     public void insertAnswerLikeModel(ArrayList<String> list){
        SQLiteDatabase database=this.getWritableDatabase();
        if(database.isOpen()){
            ContentValues values=new ContentValues();
            if(list!=null)
            for(int i=0;i<list.size();i++){
                values.put(answerId, list.get(i));
                database.insert(ANSWER_LIKE_TABLE, null, values);
                values.clear();
            }
            if(database.isOpen())
                database.close();

            }

        }
        public void insertSingleAnswerLikeModel(String manswerId){
        SQLiteDatabase database=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(answerId, manswerId);
        database.insert(ANSWER_LIKE_TABLE, null, values);
        values.clear();
        if(database.isOpen())
            database.close();
        }
        public void clearAnswerLikeModel(){
            SQLiteDatabase database=this.getWritableDatabase();
            database.delete(ANSWER_LIKE_TABLE, null, null);
            if(database.isOpen())
                database.close();
        }
        public ArrayList<String> getAnswerLikeModel(){
        SQLiteDatabase database=this.getWritableDatabase();
        String query="SELECT * FROM "+ANSWER_LIKE_TABLE;
        ArrayList<String> list = new ArrayList<>();
        Cursor cursor=database.rawQuery(query, null);
        if(cursor.moveToFirst()){
            do{
                String manswerId=cursor.getString(cursor.getColumnIndex(answerId));
                list.add(manswerId);

            }while (cursor.moveToNext());
            if(database.isOpen())
                database.close();
            if(!cursor.isClosed())
                cursor.close();
            return list;
        }
        return null;
    }
    public void removeAnswerLikeModel(String manswerId){
        SQLiteDatabase database=this.getWritableDatabase();
        database.delete(ANSWER_LIKE_TABLE, answerId+"=?", new String[]{manswerId});
        if(database.isOpen())
            database.close();
    }

    public void insertSurveyParticipatedModel(HashMap<String,Integer> participatedMap){
        SQLiteDatabase database=this.getWritableDatabase();
        if(database.isOpen()){
            ContentValues values=new ContentValues();
            Set<String> participatedId=participatedMap.keySet();
            for(String key:participatedId){
                values.put(surveyParticipatedId, key);
                values.put(optionSelected, participatedMap.get(key));
                database.insert(SURVEY_PARTICIPATED_TABLE, null, values);
                values.clear();

            }
            if(database.isOpen())
                database.close();
        }
    }
    public void clearSurveyParticipatedModel(){
        SQLiteDatabase database=this.getWritableDatabase();
        database.delete(SURVEY_PARTICIPATED_TABLE, null, null);
        if(database.isOpen())
            database.close();
    }
    public HashMap<String,Integer> getSurveyParticipatedModel(){
        SQLiteDatabase database=this.getWritableDatabase();
        HashMap<String ,Integer> participatedMap=new HashMap<>();
        String query="SELECT * FROM "+SURVEY_PARTICIPATED_TABLE;
        Cursor cursor=database.rawQuery(query, null);
        if(cursor.moveToFirst()){
            do{
                String msurveyParticipatedId=cursor.getString(cursor.getColumnIndex(surveyParticipatedId));
                int moptionSelected=cursor.getInt(cursor.getColumnIndex(optionSelected));
                participatedMap.put(msurveyParticipatedId, moptionSelected);

            }while (cursor.moveToNext());
            if(database.isOpen())
                database.close();
            if(!cursor.isClosed())
                cursor.close();
            return participatedMap;
        }
        return null;

    }
    public void removeSurveyParticipatedModel(String msurveyParticipatedId){
        SQLiteDatabase database=this.getWritableDatabase();
        database.delete(SURVEY_PARTICIPATED_TABLE, surveyParticipatedId+"+=?", new String[]{msurveyParticipatedId});
//        database.execSQL("DELETE FROM "+SURVEY_PARTICIPATED_TABLE+" WHERE "+surveyParticipatedId+"=="+msurveyParticipatedId);
        if(database.isOpen())
            database.close();
    }

    public void insertImagePollLikeModel(HashMap<String,Integer> imageLikeMap){
        SQLiteDatabase database=this.getWritableDatabase();
        if(database.isOpen()){
            ContentValues values=new ContentValues();
            Set<String> imagePollIdSet=imageLikeMap.keySet();
            for(String key: imagePollIdSet){
                values.put(imagePollId, key);
                values.put(optionSelected, imageLikeMap.get(key));
                database.insert(IMAGE_POLL_LIKE_TABLE, null, values);
                values.clear();
            }
            if(database.isOpen())
                database.close();
        }
    }
    public void clearImagePollLikeModel(){
        SQLiteDatabase database=this.getWritableDatabase();
        database.delete(IMAGE_POLL_LIKE_TABLE, null, null);
        if(database.isOpen())
            database.close();
    }
    public HashMap<String,Integer> getImagePollLikeModel(){
        SQLiteDatabase database=this.getWritableDatabase();
        String query="SELECT * FROM "+IMAGE_POLL_LIKE_TABLE;
        HashMap<String,Integer> imageLikeMap=new HashMap<>();
        Cursor cursor=database.rawQuery(query, null);
        if(cursor.moveToFirst()){
            do{
                String mimagePollId=cursor.getString(cursor.getColumnIndex(imagePollId));
                int moptionSelected=cursor.getInt(cursor.getColumnIndex(optionSelected));
                imageLikeMap.put(mimagePollId, moptionSelected);

            }while (cursor.moveToNext());
            if(!cursor.isClosed())
                cursor.close();
            if(database.isOpen())
                database.close();
            return imageLikeMap;

        }
        return  null;
    }
    public void removeImagePollLikeModel(String mimagePollId){
        SQLiteDatabase database = this.getWritableDatabase();
        database.delete(IMAGE_POLL_LIKE_TABLE, imagePollId+"=?", new String[]{mimagePollId});
        if(database.isOpen())
            database.close();
    }

    public  void insertFollowerModel(ArrayList<Follower> list){
        SQLiteDatabase database=this.getWritableDatabase();
        if(list!=null){
            ContentValues values=new ContentValues();
            for(int i=0;i<list.size();i++){
                Follower model=list.get(i);
                values.put(followerId, model.getFollowerId());
                values.put(followerName, model.getFollowerName());
                values.put(followerImageUrl, model.getFollowerImageUrl());
                values.put(followerBio, model.getFollowerBio());
                database.insert(FOLLOWER_TABLE, null, values);
                values.clear();

            }
            if(database.isOpen())
                database.close();
        }
    }
    public void clearFollowerModel(){
        SQLiteDatabase database=this.getWritableDatabase();
        database.delete(FOLLOWER_TABLE, null, null);
        if(database.isOpen())
            database.close();

    }
    public ArrayList<Follower> getFollowerModelList(){
        ArrayList<Follower> list;
        SQLiteDatabase database=this.getWritableDatabase();
        String query="SELECT * FROM "+FOLLOWER_TABLE;
        Cursor cursor=database.rawQuery(query, null);
        if(cursor.moveToFirst()){
            list=new ArrayList<>();
            do {
                String mfollowerId=cursor.getString(cursor.getColumnIndex(followerId));
                String mfollowerName=cursor.getString(cursor.getColumnIndex(followerName));
                String mfollowerImageUrl=cursor.getString(cursor.getColumnIndex(followerImageUrl));
                String mfollowerBio=cursor.getString(cursor.getColumnIndex(followerBio));
                Follower model=new Follower(mfollowerId, mfollowerName, mfollowerImageUrl, mfollowerBio);
                list.add(model);
            }while (cursor.moveToNext());
            if(database.isOpen())
                database.close();
            if(!cursor.isClosed())
                cursor.close();
            return list;
        }
        return null;
    }
    public ArrayList<String> getFollowerIdList(){
        ArrayList<String> list;
        SQLiteDatabase database=this.getWritableDatabase();
        String query="SELECT * FROM "+FOLLOWER_TABLE;
        Cursor cursor=database.rawQuery(query, null);
        if(cursor.moveToFirst()){
            list=new ArrayList<>();
            do {
                String mfollowerId=cursor.getString(cursor.getColumnIndex(followerId));
                list.add(mfollowerId);
            }while (cursor.moveToNext());
            if(database.isOpen())
                database.close();
            if(!cursor.isClosed())
                cursor.close();
            return list;
        }
        return null;
    }
    public void removeFollowerModel(String mfollowerId){
        SQLiteDatabase database = this.getWritableDatabase();
        database.delete(FOLLOWER_TABLE, followerId+"=?", new String[]{mfollowerId});
        if(database.isOpen())
            database.close();
    }

    public  void insertFollowingModel(ArrayList<Following> list){
        SQLiteDatabase database=this.getWritableDatabase();
        if(list!=null){
            ContentValues values=new ContentValues();
            for(int i=0;i<list.size();i++){
                Following model=list.get(i);
                values.put(followingId, model.getFollowingId());
                values.put(followingName, model.getFollowingName());
                values.put(followingImageUrl, model.getFollowingImageUrl());
                values.put(followingBio, model.getFollowingBio());
                database.insert(FOLLOWING_TABLE, null, values);
                values.clear();

            }
            if(database.isOpen())
                database.close();
        }
    }
    public void clearFollowingModel(){
        SQLiteDatabase database=this.getWritableDatabase();
        database.delete(FOLLOWING_TABLE, null, null);
        if(database.isOpen())
            database.close();

    }
    public ArrayList<Following> getFollowingModelList(){
        ArrayList<Following> list;
        SQLiteDatabase database=this.getWritableDatabase();
        String query="SELECT * FROM "+FOLLOWING_TABLE;
        Cursor cursor=database.rawQuery(query, null);
        if(cursor.moveToFirst()){
            list=new ArrayList<>();
            do {
                String mfollowingId=cursor.getString(cursor.getColumnIndex(followingId));
                String mfollowingName=cursor.getString(cursor.getColumnIndex(followingName));
                String mfollowingImageUrl=cursor.getString(cursor.getColumnIndex(followingImageUrl));
                String mfollowingBio=cursor.getString(cursor.getColumnIndex(followingBio));
                Following model=new Following(mfollowingId, mfollowingName,
                        mfollowingImageUrl, mfollowingBio);
                list.add(model);
            }while (cursor.moveToNext());
            if(database.isOpen())
                database.close();
            if(!cursor.isClosed())
                cursor.close();
            return list;
        }
        return null;
    }
    public ArrayList<String> getFollowingIdList(){
        ArrayList<String> list;
        SQLiteDatabase database=this.getWritableDatabase();
        String query="SELECT * FROM "+FOLLOWING_TABLE;
        Cursor cursor=database.rawQuery(query, null);
        if(cursor.moveToFirst()){
            list=new ArrayList<>();
            do {
                String mfollowingId=cursor.getString(cursor.getColumnIndex(followingId));
                list.add(mfollowingId);
            }while (cursor.moveToNext());
            if(database.isOpen())
                database.close();
            if(!cursor.isClosed())
                cursor.close();
            return list;
        }
        return null;
    }
    public void removeFollowingModel(String mfollowingId){
        SQLiteDatabase database=this.getWritableDatabase();
        database.delete(FOLLOWING_TABLE, followingId+"=?", new String[]{mfollowingId});
        if(database.isOpen())
            database.close();
    }


    public  void insertCommunityModel(ArrayList<Community> list){

        SQLiteDatabase database=this.getWritableDatabase();
        if(list!=null){
            ContentValues values=new ContentValues();
            for(int i=0;i<list.size();i++){
                Community model=list.get(i);
                values.put(communityId, model.getCommunityId());
                values.put(communityName, model.getCommunityName());
                values.put(communityImageUrl, model.getCommunityImageUrl());
                values.put(communityMemberCount, model.getCommunityMemberCount());
                database.insert(COMMUNITY_TABLE, null, values);
                values.clear();
            }
            if(database.isOpen())
                database.close();
        }
    }
    public void clearCommunityModel(){
        SQLiteDatabase database=this.getWritableDatabase();
        database.delete(COMMUNITY_TABLE, null, null);
        if(database.isOpen())
            database.close();

    }
    public ArrayList<Community> getCommunityModelList(){
        ArrayList<Community> list;
        SQLiteDatabase database=this.getWritableDatabase();
        String query="SELECT * FROM "+COMMUNITY_TABLE;
        Cursor cursor=database.rawQuery(query, null);
        if(cursor.moveToFirst()){
            list=new ArrayList<>();
            do {
                String mcommunityId=cursor.getString(cursor.getColumnIndex(communityId));
                String mcommunityName=cursor.getString(cursor.getColumnIndex(communityName));
                String mcommunityImageUrl=cursor.getString(cursor.getColumnIndex(communityImageUrl));
                int mcommunityMemberCount=cursor.getInt(cursor.getColumnIndex(communityMemberCount));
                Community model=new Community(mcommunityId,mcommunityName, mcommunityImageUrl, mcommunityMemberCount);
                list.add(model);
            }while (cursor.moveToNext());
            if(database.isOpen())
                database.close();
            if(!cursor.isClosed())
                cursor.close();
            return list;
        }
        return null;
    }
    public void removeCommunityModel(String mcommunityId){
        SQLiteDatabase database=this.getWritableDatabase();
        database.delete(COMMUNITY_TABLE, communityId+"=?", new String[]{mcommunityId});
        database.execSQL("DELETE FROM "+COMMUNITY_TABLE+" WHERE "+communityId+"=="+mcommunityId);
        if(database.isOpen())
            database.close();
    }

    public void insertReportedImagePoll(String mimagePollId){
        SQLiteDatabase database=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(imagePollId, mimagePollId);
        if(database.isOpen()){
            database.insert(IMAGE_POLL_REPORT, null, values);
            values.clear();
            database.close();
        }
    }
    public void clearReportedImagePoll(){
        SQLiteDatabase database=this.getWritableDatabase();
        database.delete(IMAGE_POLL_REPORT, null, null);
        if(database.isOpen())
            database.close();
    }
    public ArrayList<String> getImagePollReport(){
        ArrayList<String> reportedImagePollList;
        SQLiteDatabase database=this.getWritableDatabase();
        String query="SELECT * FROM "+IMAGE_POLL_REPORT;
        Cursor cursor=database.rawQuery(query, null);
        if(cursor.moveToFirst()){
            reportedImagePollList=new ArrayList<>();
            do{
                String mimagePollId=cursor.getString(cursor.getColumnIndex(imagePollId));
                reportedImagePollList.add(mimagePollId);
            }while (cursor.moveToNext());

            if(!cursor.isClosed()){
                cursor.close();
            }
            if(database.isOpen())
                database.close();
            return reportedImagePollList;
        }
        return null;
    }

    public void insertReportedSurvey(String msurveyid){
    SQLiteDatabase database=this.getWritableDatabase();
    ContentValues values=new ContentValues();
    values.put(surveyId, msurveyid);
    if(database.isOpen()){
        database.insert(SURVEY_REPORT, null, values);
        values.clear();
        database.close();
    }
}
    public void clearReportedSurvey(){
        SQLiteDatabase database=this.getWritableDatabase();
        database.delete(SURVEY_REPORT, null, null);
        if(database.isOpen())
            database.close();
    }
    public ArrayList<String> getReportedSurvey(){
        ArrayList<String> reportedSurveyList;
        SQLiteDatabase database=this.getWritableDatabase();
        String query="SELECT * FROM "+SURVEY_REPORT;
        Cursor cursor=database.rawQuery(query, null);
        if(cursor.moveToFirst()){
            reportedSurveyList=new ArrayList<>();
            do{
                String mimagePollId=cursor.getString(cursor.getColumnIndex(surveyId));
                reportedSurveyList.add(mimagePollId);
            }while (cursor.moveToNext());

            if(!cursor.isClosed()){
                cursor.close();
            }
            if(database.isOpen())
                database.close();
            return reportedSurveyList;
        }
        return null;
    }

    public void insertReportedQuestion(String mquestionId){
        SQLiteDatabase database=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(questionId, mquestionId);
        if(database.isOpen()){
            database.insert(QUESTION_REPORT, null, values);
            values.clear();
            database.close();
        }
    }
    public void clearReportedQuestion(){
        SQLiteDatabase database=this.getWritableDatabase();
        database.delete(QUESTION_REPORT, null, null);
        if(database.isOpen())
            database.close();
    }
    public ArrayList<String> getReportedQuestion(){
        ArrayList<String> reportedQuestionList;
        SQLiteDatabase database=this.getWritableDatabase();
        String query="SELECT * FROM "+QUESTION_REPORT;
        Cursor cursor=database.rawQuery(query, null);
        if(cursor.moveToFirst()){
            reportedQuestionList=new ArrayList<>();
            do{
                String mimagePollId=cursor.getString(cursor.getColumnIndex(questionId));
                reportedQuestionList.add(mimagePollId);
            }while (cursor.moveToNext());

            if(!cursor.isClosed()){
                cursor.close();
            }
            if(database.isOpen())
                database.close();
            return reportedQuestionList;
        }
        return null;
    }

    public void insertNotificationQuestion(QuestionModel model){
        SQLiteDatabase database=this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(questionId, model.getQuestionId());
        values.put(askerId, model.getAskerId());
        values.put(likerId,model.getLikerId());
        values.put(likerName, model.getLikerName());
        values.put(likerImageUrl, model.getLikerImageUrl());
        values.put(likerBio, model.getLikerBio());
        values.put(type, model.getType());
        values.put(notifiedTime, model.getNotifiedTime());
        if(database.isOpen()){
            database.insert(NOTIFICATION_QUESTION, null, values);
            values.clear();
            database.close();
        }

    }
    public QuestionModel getNotificationQuestion(String questionId){
        SQLiteDatabase database=this.getWritableDatabase();
        Cursor cursor=database.query(NOTIFICATION_QUESTION, null,
                this.questionId+"=?", new String[]{questionId},null,null,null,"1");
        if(cursor.moveToNext()){
            do {
                String mquestionId=cursor.getString(cursor.getColumnIndex(this.questionId));
                String maskerId=cursor.getString(cursor.getColumnIndex(askerId));
                String mlikerId=cursor.getString(cursor.getColumnIndex(likerId));
                String mlikerName=cursor.getString(cursor.getColumnIndex(this.likerName));
                String mlikerImageUrl=cursor.getString(cursor.getColumnIndex(this.likerImageUrl));
                String mlikerBio=cursor.getString(cursor.getColumnIndex(this.likerBio));
                String mtype=cursor.getString(cursor.getColumnIndex(this.type));
                long mnotifiedTime=cursor.getLong(cursor.getColumnIndex(this.notifiedTime));
                if(!cursor.isClosed()){
                    cursor.close();
                }
                if(database.isOpen())
                    database.close();
                return new QuestionModel(mlikerId, mlikerName,mlikerImageUrl,mlikerBio, maskerId, mquestionId, mtype,mnotifiedTime);
            }while (cursor.moveToNext());
        }
        if(database.isOpen()){
            database.close();
        }
        if(!cursor.isClosed()){
            cursor.close();
        }
        return null;
    }

    public void insertNotificationImagePoll(ImagePollModel model){
        SQLiteDatabase database=this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(imagePollId, model.getImagePollId());
        values.put(question, model.getQuestion());
        values.put(askerId, model.getAskerId());
        values.put(askerName, model.getAskerName());
        values.put(askerBio, model.getAskerBio());
        values.put(askerImageUrlLow, model.getAskerImageUrlLow());
        values.put(image1Url, model.getImage1Url());
        values.put(image2Url, model.getImage2Url());
        values.put(image1LikeNo, model.getImage1LikeNo());
        values.put(image2LikeNo, model.getImage2LikeNo());
        values.put(containVioloanceOrAdult, (model.isContainVioloanceOrAdult()?1:0));
        values.put(reported, (model.isReported()?1:0));
        values.put(type, model.getType());
        values.put(notifiedTime, model.getNotifiedTime());

        if(database.isOpen()){
            database.insert(NOTIFICATION_IMAGE_POLL, null, values);
            values.clear();
            database.close();
        }
    }
    public ImagePollModel getNotificationImagePoll(String imagePollId){
        SQLiteDatabase database=this.getWritableDatabase();
        Cursor cursor=database.query(NOTIFICATION_IMAGE_POLL, null,
                this.imagePollId+"=?", new String[]{imagePollId},null,null,null,"1");
        if(cursor.moveToNext()){
            do {
                String mimagePollId=cursor.getString(cursor.getColumnIndex(this.imagePollId));
                String mquestion=cursor.getString(cursor.getColumnIndex(this.question));
                String maskerId=cursor.getString(cursor.getColumnIndex(this.askerId));
                String maskerBio=cursor.getString(cursor.getColumnIndex(this.askerBio));
                String maskerName=cursor.getString(cursor.getColumnIndex(this.askerName));
                String maskerImageUrlLow=cursor.getString(cursor.getColumnIndex(this.askerImageUrlLow));
                String mimage1Url=cursor.getString(cursor.getColumnIndex(this.image1Url));
                String mimage2Url=cursor.getString(cursor.getColumnIndex(this.image2Url));
                int mimage1LikeNo=cursor.getInt(cursor.getColumnIndex(this.image1LikeNo));
                int mimage2LikeNo=cursor.getInt(cursor.getColumnIndex(this.image2LikeNo));
                boolean mcontainViolanceOrAdult=cursor.getInt(cursor.getColumnIndex(this.containVioloanceOrAdult))==1;
                boolean mreported=cursor.getInt(cursor.getColumnIndex(this.reported))==1;
                String mtype=cursor.getString(cursor.getColumnIndex(this.type));
                long mnotifiedTime=cursor.getLong(cursor.getColumnIndex(this.notifiedTime));

                if(!cursor.isClosed()){
                    cursor.close();
                }
                if(database.isOpen())
                    database.close();

                return new ImagePollModel(mimagePollId, mquestion, maskerId, maskerName,
                        maskerBio, maskerImageUrlLow, mimage1Url, mimage2Url,
                        mimage1LikeNo, mimage2LikeNo, mcontainViolanceOrAdult,
                        mreported,mtype, mnotifiedTime);
            }
            while (cursor.moveToNext());
        }
        if(database.isOpen()){
            database.close();
        }
        if(!cursor.isClosed()){
            cursor.close();
        }
        return null;
    }

    public void insertNotificationSurvey(SurveyModel model){
        SQLiteDatabase database=this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(surveyId, model.getSurveyId());
        values.put(askerId, model.getAskerId());
        values.put(askerName, model.getAskerName());
        values.put(askerBio, model.getAskerBio());
        values.put(askerImageUrlLow, model.getAskerImageUrlLow());
        values.put(question, model.getQuestion());
        values.put(timeOfSurvey, model.getTimeOfSurvey());
        values.put(option1Value, model.getOption1Value());
        values.put(option2Value, model.getOption2Value());
        values.put(option3Value, model.getOption3Value());
        values.put(option4Value, model.getOption4Value());
        values.put(option1Count, model.getOption1Count());
        values.put(option2Count, model.getOption2Count());
        values.put(option3Count, model.getOption3Count());
        values.put(option4Count, model.getOption4Count());
        values.put(option1, (model.isOption1()?1:0));
        values.put(option2, (model.isOption2()?1:0));
        values.put(option3, (model.isOption3()?1:0));
        values.put(option4, (model.isOption4()?1:0));
        values.put(type, model.getType());
        values.put(notifiedTime, model.getNotifiedTime());
        if (database.isOpen()) {
            database.insert(NOTIFICATION_SURVEY, null, values);
            values.clear();
            database.close();
        }
    }
    public SurveyModel getNotificationSurvey(String surveyId){
        SQLiteDatabase database=this.getWritableDatabase();
        Cursor cursor=database.query(NOTIFICATION_SURVEY, null,
                this.surveyId+"=?", new String[]{surveyId},null,null,null,"1");
        if(cursor.moveToNext()){
            do {
                String msurveyId=cursor.getString(cursor.getColumnIndex(this.surveyId));
                String maskerId=cursor.getString(cursor.getColumnIndex(this.askerId));
                String maskerName=cursor.getString(cursor.getColumnIndex(this.askerName));
                String maskerBio=cursor.getString(cursor.getColumnIndex(this.askerBio));
                String maskerImageUrlLow=cursor.getString(cursor.getColumnIndex(this.askerImageUrlLow));
                String mquestion=cursor.getString(cursor.getColumnIndex(this.question));
                long mtimeOfSurvey=cursor.getLong(cursor.getColumnIndex(this.timeOfSurvey));
                String moption1Value=cursor.getString(cursor.getColumnIndex(this.option1Value));
                String moption2Value=cursor.getString(cursor.getColumnIndex(this.option2Value));
                String moption3Value=cursor.getString(cursor.getColumnIndex(this.option3Value));
                String moption4Value=cursor.getString(cursor.getColumnIndex(this.option4Value));
                int moption1Count=cursor.getInt(cursor.getColumnIndex(this.option1Count));
                int moption2Count=cursor.getInt(cursor.getColumnIndex(this.option2Count));
                int moption3Count=cursor.getInt(cursor.getColumnIndex(this.option3Count));
                int moption4Count=cursor.getInt(cursor.getColumnIndex(this.option4Count));
                boolean moption1=cursor.getInt(cursor.getColumnIndex(this.option1))==1;
                boolean moption2=cursor.getInt(cursor.getColumnIndex(this.option2))==1;
                boolean moption3=cursor.getInt(cursor.getColumnIndex(this.option3))==1;
                boolean moption4=cursor.getInt(cursor.getColumnIndex(this.option4))==1;
                String mtype=cursor.getString(cursor.getColumnIndex(this.type));
                long mnotifiedTime=cursor.getLong(cursor.getColumnIndex(this.notifiedTime));
                if(!cursor.isClosed()){
                    cursor.close();
                }
                if(database.isOpen())
                    database.close();

                return new SurveyModel(maskerBio, maskerId, maskerImageUrlLow,
                        maskerName,mquestion, msurveyId, mtimeOfSurvey,
                        moption1Value, moption2Value, moption3Value, moption4Value,
                        moption1Count, moption2Count, moption3Count, moption4Count,
                        moption1, moption2, moption3, moption4, mtype, mnotifiedTime);
            }
            while (cursor.moveToNext());
        }
        if(database.isOpen()){
            database.close();
        }
        if(!cursor.isClosed()){
            cursor.close();
        }
        return null;
    }

    public void insertNotification(String mnotificationId,String mnotificationType){
        ContentValues values=new ContentValues();
        values.put(notificationId, mnotificationId);
        values.put(notificationType, mnotificationType);
        clearNotification(mnotificationId, mnotificationType);
        SQLiteDatabase database=this.getWritableDatabase();
        if(database.isOpen()){
            database.insert(NOTIFICATION, null, values);
            values.clear();
            database.close();
        }

    }

    public void clearNotification(String notificationId,String type){

        SQLiteDatabase database=this.getWritableDatabase();
        if(type.equals(TYPE_QUESTION)){
            database.delete(NOTIFICATION_QUESTION, questionId+"=?",new String[]{notificationId});
        }else if(type.equals(TYPE_IMAGE_POLL)){
            database.delete(NOTIFICATION_IMAGE_POLL, imagePollId+"=?",new String[]{notificationId});

        }else if(type.equals(TYPE_SURVEY)){
            database.delete(NOTIFICATION_SURVEY, surveyId+"=?",new String[]{notificationId});
        }
        database.delete(NOTIFICATION, "notificationId =?", new String[]{notificationId});

        if(database.isOpen()){
            database.close();
        }
    }

    public Object getNotification(){
        SQLiteDatabase database=this.getWritableDatabase();
        Cursor cursor=database.query(NOTIFICATION, new String[]{"ID",notificationId,notificationType}
        ,null,null,null,null,"ID DESC","20");
        if(cursor.moveToFirst()){
            ArrayList<Object> list=new ArrayList<>();

            do{
                String mnotificationId=cursor.getString(cursor.getColumnIndex(notificationId));
                String mnotificationType=cursor.getString(cursor.getColumnIndex(notificationType));

                if(mnotificationType.equals(TYPE_QUESTION)){
                    Object o=getNotificationQuestion(mnotificationId);
                    if(o!=null)
                        list.add(o);
                }else if(mnotificationType.equals(TYPE_IMAGE_POLL)){
                    Object o=getNotificationImagePoll(mnotificationId);
                    if(o!=null)
                        list.add(o);

                }else if(mnotificationType.equals(TYPE_SURVEY)){
                    Object o=getNotificationSurvey(mnotificationId);
                    if(o!=null)
                        list.add(o);
                }
            }while (cursor.moveToNext());

            if(!cursor.isClosed())
                cursor.close();
            if(database.isOpen())
                database.close();
            return list;
        }
        return null;
    }

    public void clearAllTable(){

        SQLiteDatabase database=this.getWritableDatabase();
        database.delete(USER_INFO_TABLE, null, null);
        database.delete(USER_QUESTION_MODEL, null, null);
        database.delete(ROOT_QUESTION_MODEL, null, null);
        database.delete(SURVEY_ASKED_TABLE, null, null);
        database.delete(IMAGE_POLL_TABLE, null, null);
        database.delete(ANSWER_LIKE_TABLE, null, null);
        database.delete(SURVEY_PARTICIPATED_TABLE, null, null);
        database.delete(IMAGE_POLL_LIKE_TABLE, null, null);
        database.delete(FOLLOWER_TABLE, null, null);
        database.delete(FOLLOWING_TABLE, null, null);
        database.delete(COMMUNITY_TABLE, null, null);
        database.delete(NOTIFICATION_QUESTION, null, null);
        database.delete(NOTIFICATION_IMAGE_POLL, null, null);
        database.delete(NOTIFICATION_SURVEY, null, null);
        database.delete(NOTIFICATION, null, null);
        if(database.isOpen())
            database.close();
    }



}
