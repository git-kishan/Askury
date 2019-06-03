package com.droid.solver.askapp.Main;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;
import android.util.Log;
import com.droid.solver.askapp.Question.RootQuestionModel;
import com.droid.solver.askapp.Survey.AskSurveyModel;

import java.util.ArrayList;

public class LocalDatabase extends SQLiteOpenHelper {

    private static final String DATABASE_NAME="database";
    private static final int DATABASE_VERSION=2;
    private static final String USER_INFO_TABLE="user_info_table";
    private static final String QUESTION_ASKED_TABLE="question_asked_table";
    private static final String SURVEY_ASKED_TABLE="survey_asked_table";
    private static final String IMAGE_POLL_TABLE="image_poll_table";
    private static final String QUESTION_LIKE_TABLE="question_like_table";
    private static final String SURVEY_PARTICIPATED_TABLE="survey_participated_table";
    private static final String IMAGE_POLL_LIKE_TABLE="image_poll_like_table";
    private static final String FOLLOWER_TABLE="follower_table";
    private static final String FOLLOWING_TABLE="following_table";
    private static final String COMMUNITY_TABLE="community_table";

    //user main document
    private  static final String userId="userId";
    private static final String userName="userName";
    private static final String profilePicUrlLow="profilePicUrlLow";
    private static final String profilePicUrlHigh="profilePicUrlHigh";
    private static final String about="about";
    private static final String point="point";
    private static final String language="language";
    private static final String interest="interest";
    private static final String followerCount="followerCount";
    private static final String followingCount="followingCount";


    //question asked  document
    private static final String askerName="askerName";
    private static final String askerUid="askerUid";
    private static final String questionId="questionId";
    private static final String question="question";
    private static final String questionType="questionType";
    private static final String timeOfAsking="timeOfAsking";
    private static final String userImageUrl="userImageUrl";
    private static final String likeCount="likeCount";
    private static final String answerCount="answerCount";
    private static final String imageAttached="imageAttached";
    private static final String questionImageUrl="questionImageUrl";
    private static final String anonymous="anonymous";
    private static final String satisfiedByUserAnswerCount="satisfiedByUserAnswerCount";
    private static final String recentUserAnswer="recentUserAnswer";
    private static final String recentUserUid="recentUserUid";
    private static final String recentUserImageUrl="recentUserImageUrl";
    private static final String recentUserName="recentUserName";
    private static final String recentUserAbout="recentUserAbout";
    private static final String recentUserAnswerImageUrl="recentUserAnswerImageUrl";
    private static final String recentUserAnswerImageAttached="recentUserAnswerImageAttached";


    //survey document
    private static final String surveyId="surveyId";
    private static final String askerImageUrl="askerImageUrl";
    private static final String timeOfSurvey="timeOfSurvey";
    private static final String maximumTimeOfSurvey="maximumTimeOfSurvey";
    private static final String option1="option1";
    private static final String option2="option2";
    private static final String option3="option3";
    private static final String option4="option4";
    private static final String option1Value="option1Value";
    private static final String option2Value="option2Value";
    private static final String option3Value="option3Value";
    private static final String option4Value="option4Value";
    private static final String option1Count="option1Count";
    private static final String option2Count="option2Count";
    private static final String option3Count="option3Count";
    private static final String option4Count="option4Count";
    private static final String languageSelectedIndex="languageSelectedIndex";

    //image poll document
    private static final String image1Url="image1Url";
    private static final String image2Url="image2Url";
    private static final String timeOfPolling="timeOfPolling";
    private static final String image1LikeNo="image1LikeNo";
    private static final String image2LikeNo="image2LikeNo";
    private static final String imagePollId="imagePollId";

    //question like document
    private static final String questionLikeId="questionLikeId";
    //survey participated document
    private static final String surveyParticipatedId="surveyParticipatedId";
    //imagepoll like document
    private static final String imagePollLikeId="imagePollLikeId";
    //Follower document
    private static final  String followerId="followerId";
    private static final String followerName="followerName";
    private static final String followerImageUrl="followerImageUrl";
    private static final String followerAbout="followerAbout";

    //Following document
    private static final String  followingId="followingId";
    private static final String followingName="followingName";
    private static final String followingImageUrl="followingImageUrl";
    private static final String followingAbout="followingAbout";

    //Community document
    private static final String  communityId="communityId";
    private static final String communityImageUrl="communityImageUrl";
    private static final String communityName="communityName";
    private static final String  communityMemberCount="communityMemberCount";


    //create table string

    private static final String CREATE_TABLE_USER_INFO="CREATE TABLE "+USER_INFO_TABLE+"("+userId+" TEXT PRIMARY KEY  ,"+userName+" TEXT ,"
            +profilePicUrlLow+" TEXT ,"+profilePicUrlHigh+" TEXT ,"+about+" TEXT ,"+point+" INTEGER ,"+language+" TEXT ,"+interest+" TEXT ,"+
            followerCount+" INTEGER ,"+followingCount+" INTEGER "+")";

    private static final String CREATE_TABLE_QUESTION_ASKED_TABLE="CREATE TABLE "+QUESTION_ASKED_TABLE+"("+askerName+" TEXT ,"+
            askerUid+" TEXT ,"+ questionId+" TEXT  PRIMARY KEY ,"+timeOfAsking+" INTEGER ,"+question+" TEXT ,"+userImageUrl+" TEXT ,"+
            imageAttached+" INTEGER ,"+ questionImageUrl+" TEXT ,"+anonymous+" INTEGER ,"+likeCount+" INTEGER ,"+answerCount+
            " INTEGER ,"+satisfiedByUserAnswerCount+" INTEGER ,"+
            recentUserAnswer+" TEXT ,"+recentUserUid+" TEXT ,"+recentUserImageUrl+" TEXT ,"+recentUserName+" TEXT ,"+recentUserAbout+" TEXT ,"+
            recentUserAnswerImageUrl+" TEXT ,"+recentUserAnswerImageAttached+" INTEGER" +")";

    private static final String CREATE_TABLE_SURVEY_ASKED_TABLE="CREATE TABLE "+SURVEY_ASKED_TABLE+"("+askerUid+" TEXT  ,"+
            askerName+" TEXT ,"+askerImageUrl+" TEXT ,"+question+" TEXT ,"+timeOfSurvey+" INTEGER ,"+maximumTimeOfSurvey+" INTEGER ,"+
            option1+" INTEGER ,"+option2+" INTEGER ,"+option3+" INTEGER ,"+option4+" INTEGER ,"+option1Value+" TEXT ,"+option2Value+" TEXT ,"+
            option3Value+" TEXT ,"+option4Value+" TEXT ,"+option1Count+" INTEGER ,"+option2Count+" INTEGER ,"+option3Count+" INTEGER ,"+
            option4Count+" INTEGER ,"+languageSelectedIndex+" INTEGER ,"+surveyId+" TEXT PRIMARY KEY"+")";

    private static final String CREATE_TABLE_IMAGE_POLL_TABLE="CREATE TABLE "+IMAGE_POLL_TABLE+"("+askerUid+" TEXT ,"+
            askerName+" TEXT ,"+askerImageUrl+" TEXT ,"+question+" TEXT ,"+image1Url+" TEXT ,"+image2Url+" TEXT ,"+
            timeOfPolling+" INTEGER ,"+image1LikeNo+" INTEGER ,"+image2LikeNo+" INTEGER ,"+imagePollId+" TEXT PRIMARY KEY"+")";

    private static final String CREATE_TABLE_QUESTION_LIKE_TABLE="CREATE TABLE "+QUESTION_LIKE_TABLE+"("+questionLikeId+" TEXT PRIMARY KEY"+")";

    private static final String CREATE_TABLE_SURVEY_PARTICIPATED_TABLE="CREATE TABLE "+SURVEY_PARTICIPATED_TABLE+"("+surveyParticipatedId+
            " TEXT PRIMARY KEY"+")";

    private static final String CREATE_TABLE_IMAGE_POLL_LIKE_TABLE="CREATE TABLE "+IMAGE_POLL_LIKE_TABLE+"("+imagePollLikeId+" TEXT PRIMARY KEY"+")";

    private static final String CREATE_TABLE_FOLLOWER_TABLE="CREATE TABLE "+FOLLOWER_TABLE+"("+followerId+" TEXT PRIMARY KEY ,"+
            followerName+" TEXT ,"+followerImageUrl+" TEXT ,"+followerAbout+" TEXT "+")";

    private static final String CREATE_TABLE_FOLLOWING_TABLE="CREATE TABLE "+FOLLOWING_TABLE+"("+followingId+" TEXT PRIMARY KEY,"+
            followingName+" TEXT ,"+followingImageUrl+" TEXT ,"+followingAbout+" TEXT "+")";

    private static final String CREATE_TABLE_COMMUNITY_TABLE="CREATE TABLE "+COMMUNITY_TABLE+"("+communityId+"TEXT PRIMARY KEY, "+communityName+" TEXT ,"+
            communityImageUrl+" TEXT ,"+communityMemberCount+" INTEGER "+")";

    public LocalDatabase(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_USER_INFO);
        sqLiteDatabase.execSQL(CREATE_TABLE_QUESTION_ASKED_TABLE);
        sqLiteDatabase.execSQL(CREATE_TABLE_SURVEY_ASKED_TABLE);
        sqLiteDatabase.execSQL(CREATE_TABLE_IMAGE_POLL_TABLE);
        sqLiteDatabase.execSQL(CREATE_TABLE_QUESTION_LIKE_TABLE);
        sqLiteDatabase.execSQL(CREATE_TABLE_SURVEY_PARTICIPATED_TABLE);
        sqLiteDatabase.execSQL(CREATE_TABLE_IMAGE_POLL_LIKE_TABLE);
        sqLiteDatabase.execSQL(CREATE_TABLE_FOLLOWER_TABLE);
        sqLiteDatabase.execSQL(CREATE_TABLE_FOLLOWING_TABLE);
        sqLiteDatabase.execSQL(CREATE_TABLE_COMMUNITY_TABLE);
        Log.i("TAG", "table created :");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+USER_INFO_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+QUESTION_ASKED_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+SURVEY_ASKED_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+IMAGE_POLL_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+QUESTION_LIKE_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+SURVEY_PARTICIPATED_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+IMAGE_POLL_LIKE_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+FOLLOWER_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+FOLLOWING_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+COMMUNITY_TABLE);
        Log.i("TAG", "table upgrade ");
        onCreate(sqLiteDatabase);

    }

     public  void insertUserInfo(UserInfoModel model){

        SQLiteDatabase database=this.getWritableDatabase();
        ContentValues values=new ContentValues();

        values.put(userId,model.getUserId());
        values.put(userName, model.getUserName());
        values.put(profilePicUrlLow, model.getProfilePicUrlLow());
        values.put(profilePicUrlHigh, model.getProfilePicUrlHigh());
        values.put(about, model.getBio());
        values.put(point, model.getPoint());
        String [] mlanguage=model.getLanguage();
        StringBuilder builder=new StringBuilder();
        for(int i=0;i<mlanguage.length;i++){
            builder.append(mlanguage[i]);
            builder.append("@");
        }
        values.put(language,builder.toString());

         String [] minterest=model.getInterest();
         builder=new StringBuilder();

        for(int i=0;i<minterest.length;i++){
            builder.append(minterest[i]);
            builder.append("@");
        }
        values.put(interest, builder.toString());
        values.put(followerCount, model.getFollowerCount());
        values.put(followingCount, model.getFollowingCount());

        database.insert(USER_INFO_TABLE,null,values);
        values.clear();
        database.close();

    }
     public void clearUserInfo(){
        SQLiteDatabase database=this.getWritableDatabase();
        database.execSQL("DELETE FROM "+USER_INFO_TABLE);
        database.close();
    }
//     public UserInfoModel getUserInfo(){
//        SQLiteDatabase database=this.getWritableDatabase();
//        String query="SELECT * FROM "+USER_INFO_TABLE+" ORDER BY "+userId+" DESC  LIMIT 1";
//        Cursor cursor=database.rawQuery(query, null);
//        if(cursor!=null)
//            cursor.moveToFirst();
//
//        String muserId=cursor.getString(cursor.getColumnIndex(userId));
//        String muserName=cursor.getString(cursor.getColumnIndex(userName));
//        String mprofilePicUrlLow=cursor.getString(cursor.getColumnIndex(profilePicUrlLow));
//        String mprofilePicUrlHigh=cursor.getString(cursor.getColumnIndex(profilePicUrlHigh));
//        String mabout=cursor.getString(cursor.getColumnIndex(about));
//        int mpoint=cursor.getInt(cursor.getColumnIndex(point));
//        String mlanguage=cursor.getString(cursor.getColumnIndex(language));
//        String minterest=cursor.getString(cursor.getColumnIndex(interest));
//        int mfollowerCount=cursor.getInt(cursor.getColumnIndex(followerCount));
//        int mfollowingCount=cursor.getInt(cursor.getColumnIndex(followingCount));
//
//        String [] languageArr=mlanguage.split("@");
//        String [] interestArr=minterest.split("@");
//
//        UserInfoModel infoModel=new UserInfoModel(
//        muserId,muserName ,mprofilePicUrlLow , mprofilePicUrlHigh, mabout,
//                mpoint, )
//
//        if(!cursor.isClosed())
//            cursor.close();
//        database.close();
//        return infoModel;
//
//    }

//     public boolean insertQuestionAsked(ArrayList<RootQuestionModel> list){
//
//        SQLiteDatabase database=this.getWritableDatabase();
//        if(database.isOpen()){
//        if(list!=null)
//            for(int i=0;i<list.size();i++) {
//
//                RootQuestionModel model = list.get(i);
//                ContentValues values = new ContentValues();
//
//                values.put(askerName, model.getAskerName());
//                values.put(askerUid, model.getAskerUid());
//                values.put(questionId, model.getQuestionId());
//                values.put(timeOfAsking, model.getTimeOfAsking());
//                values.put(question, model.getQuestion());
//                values.put(userImageUrl, model.getUserImageUrl());
//                int tempIsImageAttached = model.isImageAttached() ? 1 : 0;
//                values.put(imageAttached, tempIsImageAttached);
//                values.put(questionImageUrl, model.getQuestionImageUrl());
//                int tempIsAnonymous = model.isAnonymous() ? 1 : 0;
//                values.put(anonymous, tempIsAnonymous);
//                values.put(likeCount, model.getLikeCount());
//                values.put(answerCount, model.getAnswerCount());
//                values.put(satisfiedByUserAnswerCount, model.getSatisfiedByUserAnswerCount());
//                values.put(recentUserAnswer, model.getRecentUserAnswer());
//                values.put(recentUserUid, model.getRecentUserUid());
//                values.put(recentUserImageUrl, model.getRecentUserImageUrl());
//                values.put(recentUserName, model.getRecentUserName());
//                values.put(recentUserAbout, model.getRecentUserAbout());
//                values.put(recentUserAnswerImageUrl, model.getRecentUserAnswerImageUrl());
//                int tempRecentUserAnswerImageAttached = model.isRecentUserAnswerImageAttached() ? 1 : 0;
//                values.put(recentUserAnswerImageAttached, tempRecentUserAnswerImageAttached);
//
//                long isOk = database.insert(QUESTION_ASKED_TABLE, null, values);
//                if (isOk == -1) {
//                    return false;
//                }
//                values.clear();
//            }
//
//            }else return false;
//
//        if(database.isOpen())
//           database.close();
//            return true;
//     }

     public int  clearQuestionAsked(){
         SQLiteDatabase database=this.getWritableDatabase();
         int numberOfRowsDeleted=database.delete(QUESTION_ASKED_TABLE, null, null);
         database.close();
         return numberOfRowsDeleted;

     }
//     public ArrayList<RootQuestionModel> getQuestionAsked(){
//
//        ArrayList<RootQuestionModel> modelList=new ArrayList<>();
//        SQLiteDatabase database=this.getWritableDatabase();
//        String query="SELECT * FROM "+QUESTION_ASKED_TABLE;
//        Cursor cursor=database.rawQuery(query, null);
//
//        if(cursor.moveToFirst()){
//            do{
//                String maskerName=cursor.getString(cursor.getColumnIndex(askerName));
//                String maskerUid=cursor.getString(cursor.getColumnIndex(askerUid));
//                String mquestionId=cursor.getString(cursor.getColumnIndex(questionId));
//                long mtimeOfAsking=cursor.getLong(cursor.getColumnIndex(timeOfAsking));
//                String mquestion=cursor.getString(cursor.getColumnIndex(question));
//                String muserImageUrl=cursor.getString(cursor.getColumnIndex(userImageUrl));
//                boolean mimageAttached=cursor.getInt(cursor.getColumnIndex(imageAttached))!=0;
//                String mquestionImageUrl=cursor.getString(cursor.getColumnIndex(questionImageUrl));
//                boolean manonymous=cursor.getInt(cursor.getColumnIndex(anonymous))!=0;
//                int mlikeCount=cursor.getInt(cursor.getColumnIndex(likeCount));
//                int manswerCount=cursor.getInt(cursor.getColumnIndex(answerCount));
//                int msatisfiedByUserAnswerCount=cursor.getInt(cursor.getColumnIndex(satisfiedByUserAnswerCount));
//                String mrecentUserAnswer=cursor.getString(cursor.getColumnIndex(recentUserAnswer));
//                String mrecentUserUid=cursor.getString(cursor.getColumnIndex(recentUserUid));
//                String mrecentUserImageUrl=cursor.getString(cursor.getColumnIndex(recentUserImageUrl));
//                String mrecentUserName=cursor.getString(cursor.getColumnIndex(recentUserName));
//                String mrecentUserAbout=cursor.getString(cursor.getColumnIndex(recentUserAbout));
//                String mrecentUserAnswerImageUrl=cursor.getString(cursor.getColumnIndex(recentUserAnswerImageUrl));
//                boolean mrecentUserAnswerImageAttached=cursor.getInt(cursor.getColumnIndex(recentUserAnswerImageAttached))!=0;
//
//                RootQuestionModel model=new RootQuestionModel(maskerName, maskerUid, mquestionId,
//                        mtimeOfAsking, mquestion, muserImageUrl, mimageAttached,
//                        mquestionImageUrl, manonymous, mlikeCount, manswerCount,
//                        msatisfiedByUserAnswerCount, mrecentUserAnswer, mrecentUserUid,
//                        mrecentUserImageUrl, mrecentUserName, mrecentUserAbout,
//                        mrecentUserAnswerImageUrl, mrecentUserAnswerImageAttached);
//                modelList.add(model);
//            }while (cursor.moveToNext());
//
//            if(!cursor.isClosed())
//                cursor.close();
//            database.close();
//            return modelList;
//        }
//        return null;
//
//     }

     public void insertSurveyModelAsked(AskSurveyModel model){

        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values=new ContentValues();

        values.put(askerUid, model.getAskerUid());
        values.put(askerName, model.getAskerName());
        values.put(askerImageUrl, model.getAskerImageUrl());
        values.put(question, model.getQuestion());
        values.put(timeOfSurvey, model.getTimeOfSurvey());
        values.put(maximumTimeOfSurvey, model.getMaximumTimeOfSurvey());
        values.put(option1, model.isOption1());
        values.put(option2, model.isOption2());
        values.put(option3, model.isOption3());
        values.put(option4, model.isOption4());
        values.put(option1Value, model.getOption1Value());
        values.put(option2Value, model.getOption2Value());
        values.put(option3Value, model.getOption3Value());
        values.put(option4Value,model.getOption4Value());
        values.put(option1Count, model.getOption1Count());
        values.put(option2Count, model.getOption2Count());
        values.put(option3Count,model.getOption3Count());
        values.put(option4Count,model.getOption4Count());
        values.put(languageSelectedIndex, model.getLanguageSelectedIndex());
        values.put(surveyId, model.getSurveyId());

        database.insert(SURVEY_ASKED_TABLE, null,values);
        values.clear();
        database.close();

     }
     public void clearSurveyModelAsked(){
        SQLiteDatabase database=this.getWritableDatabase();
        database.execSQL("DELETE FROM "+SURVEY_ASKED_TABLE);
        database.close();

     }
     public ArrayList<AskSurveyModel> getSurveyAsked(){
        ArrayList<AskSurveyModel> modelList=new ArrayList<>();
        SQLiteDatabase database=this.getWritableDatabase();
        String query="SELECT * FROM "+SURVEY_ASKED_TABLE;
        Cursor cursor=database.rawQuery(query, null);
        if(cursor.moveToFirst()){
            do{
                String maskerUid=cursor.getString(cursor.getColumnIndex(askerUid));
                String maskerName=cursor.getString(cursor.getColumnIndex(askerName));
                String maskerImageUrl=cursor.getString(cursor.getColumnIndex(askerImageUrl));
                String mquestion=cursor.getString(cursor.getColumnIndex(question));
                long mtimeOfSurvey=cursor.getLong(cursor.getColumnIndex(timeOfSurvey));
                int mmaximumTimeOfSurvey=cursor.getInt(cursor.getColumnIndex(maximumTimeOfSurvey));
                boolean moption1=cursor.getInt(cursor.getColumnIndex(option1))!=0;
                boolean moption2=cursor.getInt(cursor.getColumnIndex(option2))!=0;
                boolean moption3=cursor.getInt(cursor.getColumnIndex(option3))!=0;
                boolean moption4=cursor.getInt(cursor.getColumnIndex(option4))!=0;
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

                AskSurveyModel model = new AskSurveyModel(maskerUid, maskerName, maskerImageUrl,
                        mquestion, mtimeOfSurvey, mmaximumTimeOfSurvey, moption1,
                        moption2, moption3, moption4, moption1Value, moption2Value,
                        moption3Value, moption4Value, moption1Count, moption2Count,
                        moption3Count, moption4Count, mlanguageSelectedIndex, msurveyId);
                modelList.add(model);

            }while (cursor.moveToNext());
            if(!cursor.isClosed())
                cursor.close();
            database.close();

            return modelList;
        }
        return null;
     }




}
