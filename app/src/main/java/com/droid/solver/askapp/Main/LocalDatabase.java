package com.droid.solver.askapp.Main;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

import android.os.strictmode.SqliteObjectLeakedViolation;
import android.util.Log;
import com.droid.solver.askapp.Answer.UserAnswerModel;
import com.droid.solver.askapp.ImagePoll.AskImagePollModel;
import com.droid.solver.askapp.Notification.AnswerModel;
import com.droid.solver.askapp.Notification.FollowerModel;
import com.droid.solver.askapp.Notification.ImagePollModel;
import com.droid.solver.askapp.Notification.QuestionModel;
import com.droid.solver.askapp.Notification.SurveyModel;
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
    private static final int DATABASE_VERSION=27;

    private final String USER_QUESTION_MODEL="question_asked_table";
    private final String USER_ANSWER_MODEL="user_answer_table";
    private final String ROOT_QUESTION_MODEL="root_question_model";
    private final String QUESTION_ROOT_QUESTION_MODEL="question_root_question_model";
    private final String SURVEY_ASKED_TABLE="survey_asked_table";
    private final String IMAGE_POLL_TABLE="image_poll_table";
    private final String ANSWER_LIKE_TABLE="answer_like_table";
    private final String SURVEY_PARTICIPATED_TABLE="survey_participated_table";
    private final String IMAGE_POLL_LIKE_TABLE="image_poll_like_table";
    private final String FOLLOWER_TABLE="follower_table";
    private final String FOLLOWING_TABLE="following_table";
    private final String IMAGE_POLL_REPORT="image_poll_report";
    private final String SURVEY_REPORT="survey_report";
    private final String QUESTION_REPORT="question_report";
    private final String HOME_OBJECT="home_object";

    private final String NOTIFICATION_QUESTION="notification_question";
    private final String NOTIFICATION_ANSWER="notification_answer";
    private final String NOTIFICATION_IMAGE_POLL="notification_image_poll";
    private final String NOTIFICATION_SURVEY="notification_survey";
    private final String NOTIFICATION_FOLLOWER="notification_follower";
    private final String NOTIFICATION="notifiction";



    //user main document
    private final String userId="userId";
    private final String userName="userName";
    private final String fontUsed="fontUsed";

    //question asked  document
    private final String askerId="askerId";
    private final String askerName="askerName";
    private final String anonymous="anonymous";
    private final String askerImageUrlLow="askerImageUrlLow";
    private final String askerBio="askerBio";
    private final String questionId="questionId";//primary key not null
    private final String question="question";
    private final String answer="answer";
    private final String answerLikeCount="answerLikeCount";
    private final String questionType="questionType";
    private final String timeOfAsking="timeOfAsking";
    private final String answererName="answererName";
    private final String answererBio="answererBio";
    private final String timeOfAnswering="timeOfAnswering";
    private final String imageAttached="imageAttached";
    private final String imageAttachedUrl="imageAttachedUrl";
    private final String recentAnswererId="recentAnswererId";
    private final String recentAnswererImageUrlLow="recentAnswererImageUrlLow";
    private final String recentAnswererName="recentAnswererName";
    private final String recentAnswererBio="recentAnswererBio";
    private final String recentAnswerId="recentAnswerId";
    private final String recentAnswer="recentAnswer";
    private final String recentAnswerImageAttached="recentAnswerImageAttached";
    private final String recentAnswerImageUrl="recentAnswerImageUrl";
    private final String answerCount="answerCount";
    private final String recentAnswerLikeCount="recentAnswerLikeCount";
    private final String likedByMe="likedByMe";
    private final String helpful="helpful";

//home
    private final String TYPE_ROOT_QUESTION_MODEL="root_question_model";
    private final String TYPE_IMAGE_POLL_MODEL="image_poll_model";
    private final String TYPE_SURVEY_POLL_MODEL="survey_poll_model";
    private final String objectId="object_id";

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
    private final String optionSelectedByMe="optionSelectedByMe";

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

    //notification
    private final String likerId="likerId";
    private final String likerName="likerName";
    private final String likerImageUrl="likerImageUrl";
    private final String likerBio="likerBio";
    private final String type="type";
    private final String containVioloanceOrAdult="containVioloanceOrAdult";
    private final String reported="reported";
    private final String notifiedTime="notifiedTime";
    private final String selfId="selfId";
    private final String TYPE_QUESTION="question";
    private final String TYPE_IMAGE_POLL="imagePoll";
    private final String TYPE_SURVEY="survey";
    private final String TYPE_ANSWER="answer";
    private final String TYPE_FOLLOWER="follower";
    private final String notificationId="notificationId";
    private final String notificationType="notificationType";
    private final String answererId="answererId";
    private final String isStoredLocally="isStoredLocally";

    //create table string

    public LocalDatabase(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

          final String CREATE_TABLE_USER_QUESTION_MODEL="CREATE TABLE "+USER_QUESTION_MODEL+"("+questionId+" TEXT PRIMARY KEY,"+
                question+" TEXT ,"+questionType+" TEXT ,"+timeOfAsking+" INTEGER ,"+userId+" TEXT ,"+userName+" TEXT ,"+userImageUrlLow+" TEXT ,"+
                userBio+" TEXT ,"+answerCount+" INTEGER ,"+anonymous+" INTEGER ,"+userNotified+" INTEGER "+")";

          final String CREATE_TABLE_USER_ANSWER_MODEL="CREATE TABLE "+USER_ANSWER_MODEL+"("+askerId+" TEXT ,"+askerName+" TEXT ,"+
                  askerImageUrlLow+" TEXT ,"+askerBio+" TEXT ,"+questionId+" TEXT PRIMARY KEY ,"+question+" TEXT ,"+questionType+" TEXT ,"+
                  timeOfAsking+" INTEGER ,"+timeOfAnswering+" TEXT ,"+answerId+" TEXT ,"+answererId+" TEXT ,"+answererName+" TEXT ,"+
                  helpful+" INTEGER ,"+answer+" TEXT ,"+imageAttached+" INTEGER ,"+imageAttachedUrl+" TEXT ,"+fontUsed+" INTEGER ,"+
                  answerLikeCount+" INTEGER ,"+anonymous+" INTEGER "+")";

          final String CREATE_TABLE_ROOT_QUESTION_MODEL="CREATE TABLE "+ROOT_QUESTION_MODEL+"("+askerId+" TEXT ,"+askerName+" TEXT ,"+
                askerImageUrlLow+" TEXT ,"+anonymous+" INTEGER ,"+askerBio+" TEXT ,"+questionId+" TEXT PRIMARY KEY ,"+question+" TEXT ,"+
                questionType+" TEXT ,"+timeOfAsking+" INTEGER ,"+recentAnswererId+" TEXT ,"+recentAnswererImageUrlLow+" TEXT ,"+recentAnswererName+" TEXT ,"
                +recentAnswererBio+" TEXT ,"+recentAnswerId+" TEXT ,"+recentAnswer+" TEXT ,"+recentAnswerImageAttached+" INTEGER ,"+recentAnswerImageUrl+
                " TEXT ,"+answerCount+" INTEGER ,"+recentAnswerLikeCount+" INTEGER ,"+fontUsed +" INTEGER ,"+likedByMe+" INTEGER "+")";

          final String CREATE_TABLE_QUESTION_ROOT_QUESTION_MODEL="CREATE TABLE "+QUESTION_ROOT_QUESTION_MODEL+"("+askerId+" TEXT ,"+askerName+" TEXT ,"+
                askerImageUrlLow+" TEXT ,"+anonymous+" INTEGER ,"+askerBio+" TEXT ,"+questionId+" TEXT PRIMARY KEY,"+question+" TEXT ,"+
                questionType+" TEXT ,"+timeOfAsking+" INTEGER ,"+recentAnswererId+" TEXT ,"+recentAnswererImageUrlLow+" TEXT ,"+recentAnswererName+" TEXT ,"
                +recentAnswererBio+" TEXT ,"+recentAnswerId+" TEXT ,"+recentAnswer+" TEXT ,"+recentAnswerImageAttached+" INTEGER ,"+recentAnswerImageUrl+
                " TEXT ,"+answerCount+" INTEGER ,"+recentAnswerLikeCount+" INTEGER ,"+fontUsed +" INTEGER"+")";

          final String CREATE_TABLE_SURVEY_ASKED_TABLE="CREATE TABLE "+SURVEY_ASKED_TABLE+"("+askerId+" TEXT ,"+askerName+" TEXT ,"+
                askerImageUrlLow+" TEXT ,"+askerBio+" TEXT ,"+question+" TEXT ,"+timeOfSurvey+" INTEGER ,"+option1+" INTEGER ,"+option2+" INTEGER ,"+
                option3+" INTEGER ,"+option4+" INTEGER ,"+option1Value+" TEXT ,"+option2Value+" TEXT ,"+option3Value+" TEXT ,"+option4Value+" TEXT ,"+
                option1Count+" INTEGER ,"+option2Count+" INTEGER ,"+option3Count+" INTEGER ,"+option4Count+" INTEGER ,"+languageSelectedIndex+" INTEGER ,"+
                surveyId+" TEXT PRIMARY KEY ,"+optionSelectedByMe+" INTEGER "+")";

          final String CREATE_TABLE_IMAGE_POLL_TABLE="CREATE TABLE "+IMAGE_POLL_TABLE+"("+askerId+" TEXT ,"+askerName+" TEXT ,"+
                askerImageUrlLow+" TEXT ,"+askerBio+" TEXT ,"+question+" TEXT ,"+image1Url+" TEXT ,"+image2Url+" TEXT ,"+timeOfPolling+" INTEGER ,"+
                image1LikeNo+" INTEGER ,"+image2LikeNo+" INTEGER ,"+imagePollId+" TEXT PRIMARY KEY,"+optionSelectedByMe+" INTEGER "+")";

          final String CREATE_TABLE_ANSWER_LIKE_TABLE="CREATE TABLE "+ANSWER_LIKE_TABLE+"("+answerId+" TEXT PRIMARY KEY"+")";

          final String CREATE_TABLE_SURVEY_PARTICIPATED_TABLE="CREATE TABLE "+SURVEY_PARTICIPATED_TABLE+"("+surveyParticipatedId+
                " TEXT PRIMARY KEY,"+optionSelected+" INTEGER "+")";

          final String CREATE_TABLE_IMAGE_POLL_LIKE_TABLE="CREATE TABLE "+IMAGE_POLL_LIKE_TABLE+"("+imagePollId+" TEXT PRIMARY KEY,"+
                optionSelected +" INTEGER "+")";

          final String CREATE_TABLE_FOLLOWER_TABLE="CREATE TABLE "+FOLLOWER_TABLE+"("+followerId+" TEXT PRIMARY KEY ,"+
                followerName+" TEXT ,"+followerImageUrl+" TEXT ,"+followerBio+" TEXT "+")";

          final String CREATE_TABLE_FOLLOWING_TABLE="CREATE TABLE "+FOLLOWING_TABLE+"("+followingId+" TEXT PRIMARY KEY,"+
                followingName+" TEXT ,"+followingImageUrl+" TEXT ,"+followingBio+" TEXT "+")";

          final String CREATE_TABLE_IMAGE_POLL_REPORT_TABLE="CREATE TABLE "+IMAGE_POLL_REPORT+"("+imagePollId+" TEXT PRIMARY KEY "+")";

          final String CREATE_TABLE_SURVEY_REPORT_TABLE="CREATE TABLE "+SURVEY_REPORT+"("+surveyId+" TEXT PRIMARY KEY "+")";

          final String CREATE_TABLE_QUESTION_REPORT_TABLE="CREATE TABLE "+QUESTION_REPORT+"("+questionId+" TEXT PRIMARY KEY"+")";

          final String CREATE_TABLE_NOTIFICATION_QUESTION="CREATE TABLE "+NOTIFICATION_QUESTION+"("+questionId+" TEXT PRIMARY KEY ,"+
                askerId+" TEXT,"+likerId+" TEXT ,"+likerName+" TEXT ,"+likerImageUrl+" TEXT ,"+likerBio+" TEXT ,"+type +" TEXT ,"+
                answerId+" TEXT ,"+answererId+" TEXT ,"+notifiedTime+" INTEGER ,"+ isStoredLocally+" INTEGER "+")";

          final String CREATE_TABLE_NOTIFICATION_IMAGE_POLL="CREATE TABLE "+NOTIFICATION_IMAGE_POLL+"("+imagePollId+" TEXT PRIMARY KEY,"+
                question+" TEXT ,"+askerId+" TEXT ,"+askerName+" TEXT ,"+askerBio+" TEXT ,"+askerImageUrlLow+" TEXT ,"+image1Url+" TEXT ,"+
                image2Url+" TEXT ,"+image1LikeNo+" INTEGER ,"+image2LikeNo+" INTEGER ,"+ containVioloanceOrAdult+" INTEGER ,"+reported+" INTEGER ,"+
                type +" TEXT ,"+notifiedTime+" INTEGER ,"+isStoredLocally+" INTEGER "+")";

         final String CREATE_TABLE_NOTIFICATION_SURVEY="CREATE TABLE "+NOTIFICATION_SURVEY+"("+surveyId+" TEXT PRIMARY KEY ,"+
                askerId+" TEXT ,"+askerName+" TEXT ,"+askerBio+" TEXT ,"+askerImageUrlLow+" TEXT ,"+question+" TEXT ,"+timeOfSurvey+" INTEGER ,"+
                option1Value+" TEXT ,"+option2Value+" TEXT ,"+option3Value+" TEXT ,"+option4Value+" TEXT ,"+option1Count+" INTEGER ,"+
                option2Count+" INTEGER ,"+option3Count+" INTEGER ,"+option4Count+" INTEGER ,"+option1+" INTEGER ,"+option2+" INTEGER ,"+
                option3+" INTEGER ,"+option4+" INTEGER ,"+type+" TEXT ,"+notifiedTime+" INTEGER ,"+isStoredLocally+" INTEGER "+")";

         final String CREATE_TABLE_NOTIFICATION_ANSWER="CREATE TABLE "+NOTIFICATION_ANSWER+"("+answerId+" TEXT PRIMARY KEY ,"+askerId+" TEXT ,"+
                 askerName+" TEXT ,"+askerBio+" TEXT ,"+questionId+" TEXT ,"+question+" TEXT ,"+timeOfAsking+" INTEGER ,"+timeOfAnswering+" INTEGER ,"+
                 answererId+" TEXT ,"+answererName+" TEXT ,"+answererBio+" TEXT ,"+answer+" TEXT ,"+imageAttached+" INTEGER ,"+imageAttachedUrl+" TEXT ,"+
                 fontUsed+" INTEGER ,"+anonymous+" INTEGER ,"+notifiedTime+" INTEGER ,"+type+" TEXT ,"+answerLikeCount+" INTEGER, "
                 +isStoredLocally+" INTEGER "+")";

         final String CREATE_TABLE_NOTIFICATION_FOLLOWER="CREATE TABLE "+NOTIFICATION_FOLLOWER+"("+followerId+" TEXT PRIMARY KEY ,"+
                 followerName+" TEXT ,"+followerBio+" TEXT ,"+followerImageUrl+" TEXT ,"+selfId+" TEXT ,"+isStoredLocally+" INTEGER ,"+
                 notifiedTime+" INTEGER ,"+type+" TEXT "+")";

         final String CREATE_TABLE_NOTIFICATION="CREATE TABLE "+NOTIFICATION+"("+"ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                +notificationId+" TEXT,"+ notificationType+" TEXT "+")";

         final String CREATE_TABLE_HOME_OBJECT="CREATE TABLE "+HOME_OBJECT+"("+objectId +" TEXT PRIMARY KEY ,"+type +" TEXT "+")";

         try {
             sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+USER_QUESTION_MODEL);
             sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+USER_ANSWER_MODEL);
             sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ROOT_QUESTION_MODEL);
             sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+QUESTION_ROOT_QUESTION_MODEL);
             sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+SURVEY_ASKED_TABLE);
             sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+IMAGE_POLL_TABLE);
             sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ANSWER_LIKE_TABLE);
             sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+SURVEY_PARTICIPATED_TABLE);
             sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+IMAGE_POLL_LIKE_TABLE);
             sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+FOLLOWER_TABLE);
             sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+FOLLOWING_TABLE);
             sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+IMAGE_POLL_REPORT);
             sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+SURVEY_REPORT);
             sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+QUESTION_REPORT);
             sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+NOTIFICATION_QUESTION);
             sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+NOTIFICATION_ANSWER);
             sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+NOTIFICATION_IMAGE_POLL);
             sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+NOTIFICATION_SURVEY);
             sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+NOTIFICATION_FOLLOWER);
             sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+NOTIFICATION);
             sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+HOME_OBJECT);

             sqLiteDatabase.execSQL(CREATE_TABLE_USER_QUESTION_MODEL);
             sqLiteDatabase.execSQL(CREATE_TABLE_USER_ANSWER_MODEL);
             sqLiteDatabase.execSQL(CREATE_TABLE_ROOT_QUESTION_MODEL);
             sqLiteDatabase.execSQL(CREATE_TABLE_QUESTION_ROOT_QUESTION_MODEL);
             sqLiteDatabase.execSQL(CREATE_TABLE_SURVEY_ASKED_TABLE);
             sqLiteDatabase.execSQL(CREATE_TABLE_IMAGE_POLL_TABLE);
             sqLiteDatabase.execSQL(CREATE_TABLE_ANSWER_LIKE_TABLE);
             sqLiteDatabase.execSQL(CREATE_TABLE_SURVEY_PARTICIPATED_TABLE);
             sqLiteDatabase.execSQL(CREATE_TABLE_IMAGE_POLL_LIKE_TABLE);
             sqLiteDatabase.execSQL(CREATE_TABLE_FOLLOWER_TABLE);
             sqLiteDatabase.execSQL(CREATE_TABLE_FOLLOWING_TABLE);
             sqLiteDatabase.execSQL(CREATE_TABLE_IMAGE_POLL_REPORT_TABLE);
             sqLiteDatabase.execSQL(CREATE_TABLE_SURVEY_REPORT_TABLE);
             sqLiteDatabase.execSQL(CREATE_TABLE_QUESTION_REPORT_TABLE);
             sqLiteDatabase.execSQL(CREATE_TABLE_NOTIFICATION_QUESTION);
             sqLiteDatabase.execSQL(CREATE_TABLE_NOTIFICATION_ANSWER);
             sqLiteDatabase.execSQL(CREATE_TABLE_NOTIFICATION_IMAGE_POLL);
             sqLiteDatabase.execSQL(CREATE_TABLE_NOTIFICATION_SURVEY);
             sqLiteDatabase.execSQL(CREATE_TABLE_NOTIFICATION_FOLLOWER);
             sqLiteDatabase.execSQL(CREATE_TABLE_NOTIFICATION);
             sqLiteDatabase.execSQL(CREATE_TABLE_HOME_OBJECT);
             Log.i("TAG", "table created :");
         }
         catch(SQLiteException e){
             Log.i("TAG", "Sqlite exception occurs ,"+e.getMessage());
         }

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+USER_QUESTION_MODEL);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+USER_ANSWER_MODEL);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ROOT_QUESTION_MODEL);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+QUESTION_ROOT_QUESTION_MODEL);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+SURVEY_ASKED_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+IMAGE_POLL_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ANSWER_LIKE_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+SURVEY_PARTICIPATED_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+IMAGE_POLL_LIKE_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+FOLLOWER_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+FOLLOWING_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+IMAGE_POLL_REPORT);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+SURVEY_REPORT);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+QUESTION_REPORT);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+NOTIFICATION_QUESTION);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+NOTIFICATION_ANSWER);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+NOTIFICATION_IMAGE_POLL);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+NOTIFICATION_SURVEY);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+NOTIFICATION_FOLLOWER);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+NOTIFICATION);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+HOME_OBJECT);
        Log.i("TAG", "table upgrade ");
        onCreate(sqLiteDatabase);

    }
     public void insertUserAnswerModel(ArrayList<Object> list){
        clearUserAnswerModel();
         SQLiteDatabase database=this.getWritableDatabase();
         if(database.isOpen()) {
             if (list != null)
                 for (int i = 0; i < list.size(); i++) {
                     if (list.get(i) instanceof UserAnswerModel) {
                         UserAnswerModel model =(UserAnswerModel)list.get(i);
                         ContentValues values = new ContentValues();
                         values.put(askerId, model.getAskerId());
                         values.put(askerName, model.getAskerName());
                         values.put(askerImageUrlLow, model.getAskerImageUrl());
                         values.put(askerBio, model.getAskerBio());
                         values.put(questionId, model.getQuestionId());
                         values.put(question, model.getQuestion());

                         StringBuilder builder = new StringBuilder("");
                         try {
                             if (model.getQuestionType() != null)
                                 for (int j = 0; j < model.getQuestionType().size(); j++) {
                                     builder.append(model.getQuestionType().get(i));
                                     builder.append("@");
                                 }
                         }catch (ArrayIndexOutOfBoundsException e){
                             Log.i("TAG", "Exception occurs  in database userAnswermodeInsertion :- "+e.getMessage());
                         }
                         values.put(questionType, builder.toString());
                         values.put(timeOfAsking, model.getTimeOfAsking());
                         values.put(timeOfAnswering, model.getTimeOfAnswering());
                         values.put(answerId, model.getAnswerId());
                         values.put(answererId, model.getAnswererId());
                         values.put(answererName, model.getAnswererName());
                         values.put(helpful, model.isHelpful()?1:0);
                         values.put(answer, model.getAnswer());
                         values.put(imageAttached, model.isImageAttached());
                         values.put(imageAttachedUrl, model.getImageAttachedUrl());
                         values.put(fontUsed, model.getFontUsed());
                         values.put(answerLikeCount, model.getAnswerLikeCount());
                         values.put(anonymous, model.isAnonymous()?1:0);

                         database.insert(USER_ANSWER_MODEL, null, values);
                         values.clear();
                     }
                 }
             if (database.isOpen())
                 database.close();
         }
     }
     private void clearUserAnswerModel(){
        SQLiteDatabase database=this.getWritableDatabase();
        if(database.isOpen()){
            database.delete(USER_ANSWER_MODEL, null, null);
            database.close();
        }
     }
     public ArrayList<UserAnswerModel> getUserAnswerModelList(){

         ArrayList<UserAnswerModel> modelList=new ArrayList<>();
         SQLiteDatabase database=this.getWritableDatabase();
         String query="SELECT * FROM "+USER_ANSWER_MODEL;
         Cursor cursor=database.rawQuery(query, null);
         if(cursor.moveToFirst()){
             do{
                 String maskerId=cursor.getString(cursor.getColumnIndex(askerId));
                 String maskerName=cursor.getString(cursor.getColumnIndex(askerName));
                 String maskerImageUrl=cursor.getString(cursor.getColumnIndex(this.askerImageUrlLow));
                 String maskerBio=cursor.getString(cursor.getColumnIndex(this.askerBio));
                 String mquestionId=cursor.getString(cursor.getColumnIndex(questionId));
                 String mquestion=cursor.getString(cursor.getColumnIndex(question));
                 String tempQuestionType=cursor.getString(cursor.getColumnIndex(questionType));
                 String [] tempQuestionTypeArr=tempQuestionType.split("@");
                 List<String> mquestionType= Arrays.asList(tempQuestionTypeArr);
                 long mtimeOfAsking=cursor.getLong(cursor.getColumnIndex(timeOfAsking));
                 long mtimeOfAnswering=cursor.getLong(cursor.getColumnIndex(timeOfAnswering));
                 String manswerId=cursor.getString(cursor.getColumnIndex(answerId));
                 String manswererId=cursor.getString(cursor.getColumnIndex(answererId));
                 String manswererName=cursor.getString(cursor.getColumnIndex(answererName));
                 boolean mhelpful=cursor.getInt(cursor.getColumnIndex(helpful))==1;
                 String manswer=cursor.getString(cursor.getColumnIndex(answer));
                 boolean mimageAttached=cursor.getInt(cursor.getColumnIndex(imageAttached))==1;
                 String mimageAttachedUrl=cursor.getString(cursor.getColumnIndex(imageAttachedUrl));
                 int mfontUsed=cursor.getInt(cursor.getColumnIndex(fontUsed));
                 int manswerLikeCount=cursor.getInt(cursor.getColumnIndex(answerLikeCount));
                 boolean manonymous=cursor.getInt(cursor.getColumnIndex(anonymous))==1;


                 UserAnswerModel model = new UserAnswerModel(maskerId, maskerName, maskerImageUrl,
                         maskerBio, mquestionId, mquestion, mquestionType, mtimeOfAsking,
                         mtimeOfAnswering, manswerId, manswererId, manswererName, mhelpful,
                         manswer, mimageAttached, mimageAttachedUrl, mfontUsed, manswerLikeCount,
                         manonymous);

                 modelList.add(model);
             }while (cursor.moveToNext());

             if(!cursor.isClosed())
                 cursor.close();
             if(database.isOpen())
                 database.close();
             return modelList;
         }
         return null;
     }

     public void insertUserQuestionModel(ArrayList<Object> list){
         clearUserQuestionModel();
         try {
             SQLiteDatabase database = this.getWritableDatabase();
             if (database.isOpen()) {
                 if (list != null)
                     for (int i = 0; i < list.size(); i++) {
                         if (list.get(i) instanceof UserQuestionModel) {
                             UserQuestionModel model = (UserQuestionModel) list.get(i);
                             ContentValues values = new ContentValues();
                             values.put(questionId, model.getQuestionId());
                             values.put(question, model.getQuestion());

                             StringBuilder builder = new StringBuilder();
                             if (model.getQuestionType() != null)
                                 for (int j = 0; j < model.getQuestionType().size(); j++) {
                                     builder.append(model.getQuestionType().get(i));
                                     builder.append("@");
                                 }
                             values.put(questionType, builder.toString());
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
                             database.insertOrThrow(USER_QUESTION_MODEL, null, values);
                             values.clear();
                         }
                     }
                 if (database.isOpen())
                     database.close();
             }
         }catch (SQLiteException e){
             Log.i("TAG", "SQLiteException occurs in inserting user question model ,"+e.getMessage());

         }catch (NullPointerException e){
             Log.i("TAG", "NullPointerException occurs in inserting user question model ,"+e.getMessage());
         }
     }
     private void  clearUserQuestionModel(){
        try {
            SQLiteDatabase database = this.getWritableDatabase();
            if (database.isOpen()) {
                database.delete(USER_QUESTION_MODEL, null, null);
            }
        }catch (SQLiteException e){
            Log.i("TAG", "SqliteException occurs in clearing user question model ,"+e.getMessage());
        }
     }
     public void removeUserQuestionModel(final String questionId){
        try {
            SQLiteDatabase database = this.getWritableDatabase();
            if (database.isOpen()) {
                database.delete(USER_QUESTION_MODEL, this.questionId + "=?", new String[]{questionId});
                database.close();
            }
        }catch(SQLiteException e ){
            Log.i("TAG","SqiteException occurs in removing user question mdoel ,"+e.getMessage());
        }
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
            if(database.isOpen())
                database.close();
            return modelList;
        }
        return null;

     }

     public void insertQuestionRootQuestionModel(ArrayList<Object> list){
        try {
            SQLiteDatabase database = this.getWritableDatabase();
            if (database.isOpen()) {
                if (list != null)
                    for (int i = 0; i < list.size(); i++) {
                        if (list.get(i) instanceof RootQuestionModel) {
                            ContentValues values = new ContentValues();
                            RootQuestionModel rootQuestionModel = (RootQuestionModel) list.get(i);
                            values.put(askerId, rootQuestionModel.getAskerId());
                            values.put(askerName, rootQuestionModel.getAskerName());
                            values.put(askerImageUrlLow, rootQuestionModel.getAskerImageUrlLow());
                            int manonymous = rootQuestionModel.isAnonymous() ? 1 : 0;
                            values.put(anonymous, manonymous);
                            values.put(askerBio, rootQuestionModel.getAskerBio());
                            values.put(questionId, rootQuestionModel.getQuestionId());
                            values.put(question, rootQuestionModel.getQuestion());

                            StringBuilder builder = new StringBuilder();
                            if (rootQuestionModel.getQuestionType() != null)
                                for (int j = 0; j < rootQuestionModel.getQuestionType().size(); j++) {
                                    builder.append(rootQuestionModel.getQuestionType().get(j));
                                    builder.append("@");
                                }
                            values.put(questionType, builder.toString());
                            values.put(timeOfAsking, rootQuestionModel.getTimeOfAsking());
                            values.put(recentAnswererId, rootQuestionModel.getRecentAnswererId());
                            values.put(recentAnswererImageUrlLow, rootQuestionModel.getRecentAnswererImageUrlLow());
                            values.put(recentAnswererName, rootQuestionModel.getRecentAnswererName());
                            values.put(recentAnswererBio, rootQuestionModel.getRecentAnswererBio());
                            values.put(recentAnswererId, rootQuestionModel.getRecentAnswerId());
                            values.put(recentAnswer, rootQuestionModel.getRecentAnswer());
                            boolean temprecentAnswerImageAttached = rootQuestionModel.isRecentAnswerImageAttached();
                            int mrecentAnswerImageAttached = temprecentAnswerImageAttached ? 1 : 0;
                            values.put(recentAnswerImageAttached, mrecentAnswerImageAttached);
                            values.put(recentAnswerImageUrl, rootQuestionModel.getRecentAnswerImageUrl());
                            values.put(answerCount, rootQuestionModel.getAnswerCount());
                            values.put(recentAnswerLikeCount, rootQuestionModel.getRecentAnswerLikeCount());
                            values.put(fontUsed, rootQuestionModel.getFontUsed());

                            database.insertOrThrow(QUESTION_ROOT_QUESTION_MODEL, null, values);

                            values.clear();
                        }
                    }
                if (database.isOpen())
                    database.close();
            }
        }catch (SQLiteException e){
            Log.i("TAG", "SqliteException occurs in inserting question root question model ,"+e.getMessage());
        }catch (NullPointerException e ){
            Log.i("TAG", "NullPointerException occurs in inserting question root question model ,"+e.getMessage());

        }

    }
     public void clearQuestionRootQuestionModel(){
        try {
            SQLiteDatabase database = this.getWritableDatabase();
            if (database.isOpen()) {
                database.delete(QUESTION_ROOT_QUESTION_MODEL, null, null);
                database.close();
            }
        }catch (SQLiteException e){
            Log.i("TAG", "SqliteException occurs in clearing question root question model ,"+e.getMessage());
        }
    }
     public ArrayList<Object> getQuestionRootQuestionModelList(){
        ArrayList<Object> list=new ArrayList<>();
        SQLiteDatabase database =this.getWritableDatabase();
        String query="SELECT * FROM "+QUESTION_ROOT_QUESTION_MODEL;
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

     private void insertRootQuestionModel(RootQuestionModel model){
        try {
            if (model != null) {
                SQLiteDatabase database = this.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put(askerId, model.getAskerId());
                values.put(askerName, model.getAskerName());
                values.put(askerImageUrlLow, model.getAskerImageUrlLow());
                int manonymous = model.isAnonymous() ? 1 : 0;
                values.put(anonymous, manonymous);
                values.put(askerBio, model.getAskerBio());
                values.put(questionId, model.getQuestionId());
                values.put(question, model.getQuestion());

                StringBuilder builder = new StringBuilder();
                if (model.getQuestionType() != null)
                    for (int j = 0; j < model.getQuestionType().size(); j++) {
                        builder.append(model.getQuestionType().get(j));
                        builder.append("@");
                    }
                values.put(questionType, builder.toString());
                values.put(timeOfAsking, model.getTimeOfAsking());
                values.put(recentAnswererId, model.getRecentAnswererId());
                values.put(recentAnswererImageUrlLow, model.getRecentAnswererImageUrlLow());
                values.put(recentAnswererName, model.getRecentAnswererName());
                values.put(recentAnswererBio, model.getRecentAnswererBio());
                values.put(recentAnswererId, model.getRecentAnswerId());
                values.put(recentAnswer, model.getRecentAnswer());
                boolean temprecentAnswerImageAttached = model.isRecentAnswerImageAttached();
                int mrecentAnswerImageAttached = temprecentAnswerImageAttached ? 1 : 0;
                values.put(recentAnswerImageAttached, mrecentAnswerImageAttached);
                values.put(recentAnswerImageUrl, model.getRecentAnswerImageUrl());
                values.put(answerCount, model.getAnswerCount());
                values.put(recentAnswerLikeCount, model.getRecentAnswerLikeCount());
                values.put(fontUsed, model.getFontUsed());
                values.put(likedByMe, model.isLikedByMe() ? 1 : 0);
                if (database.isOpen()) {
                    database.insertOrThrow(ROOT_QUESTION_MODEL, null, values);
                    values.clear();
                    database.close();

                }
            }
        }catch (SQLiteException e){
            Log.i("TAG", "SqliteException occurs in inserting root question model ,"+e.getMessage());
        }catch (NullPointerException e ){
            Log.i("TAG", "NullPointerException occurs in inserting root question model ,"+e.getMessage());
        }


     }
     private void clearRootQuestionModel(){
        try {
            SQLiteDatabase database = this.getWritableDatabase();
            database.delete(ROOT_QUESTION_MODEL, null, null);
            if (database.isOpen())
                database.close();
        }catch (SQLiteException e){
            Log.i("TAG","SqliteException occurs in clearing root question model ,"+e.getMessage());
        }
     }
     private RootQuestionModel getRootQuestionModel(String questionId){
        SQLiteDatabase database =this.getWritableDatabase();
        Cursor cursor=database.query(ROOT_QUESTION_MODEL, null, this.questionId+"=?",
                new String[]{questionId}, null, null, null,"1");

        if(cursor.moveToFirst()){

                String maskerId=cursor.getString(cursor.getColumnIndex(askerId));
                String maskerName=cursor.getString(cursor.getColumnIndex(askerName));
                String maskerImageUrlLow=cursor.getString(cursor.getColumnIndex(askerImageUrlLow));
                int tempAnonymous=cursor.getInt(cursor.getColumnIndex(anonymous));
                boolean manonymous=tempAnonymous==1;
                String maskerBio=cursor.getString(cursor.getColumnIndex(askerBio));
                String mquestionId=cursor.getString(cursor.getColumnIndex(this.questionId));
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
                boolean mlikedByMe=cursor.getInt(cursor.getColumnIndex(likedByMe))==1;

                RootQuestionModel model=new RootQuestionModel(maskerId, maskerName, maskerImageUrlLow,
                        manonymous, maskerBio, mquestionId, mquestion, mquestionType,
                        mtimeOfAsking, mrecentAnswererId, mrecentAnswererImageUrlLow,
                        mrecentAnswererName, mrecentAnswererBio, mrecentAnswerId,
                        mrecentAnswer, mrecentAnswerImageAtached, mrecentAnswerImageUrl,
                        manswerCount, mrecentAnswerLikeCount,mfontUsed);
                model.setLikedByMe(mlikedByMe);

            if(database.isOpen())
                database.close();
            if(!cursor.isClosed())
                cursor.close();
            return model;
        }
        return null;
     }
     public void updateRootQuestionModel(RootQuestionModel model){
        try {
            if (model != null) {
                SQLiteDatabase database = this.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put(askerId, model.getAskerId());
                values.put(askerName, model.getAskerName());
                values.put(askerImageUrlLow, model.getAskerImageUrlLow());
                int manonymous = model.isAnonymous() ? 1 : 0;
                values.put(anonymous, manonymous);
                values.put(askerBio, model.getAskerBio());
                values.put(questionId, model.getQuestionId());
                values.put(question, model.getQuestion());

                StringBuilder builder = new StringBuilder();
                if (model.getQuestionType() != null)
                    for (int j = 0; j < model.getQuestionType().size(); j++) {
                        builder.append(model.getQuestionType().get(j));
                        builder.append("@");
                    }
                values.put(questionType, builder.toString());
                values.put(timeOfAsking, model.getTimeOfAsking());
                values.put(recentAnswererId, model.getRecentAnswererId());
                values.put(recentAnswererImageUrlLow, model.getRecentAnswererImageUrlLow());
                values.put(recentAnswererName, model.getRecentAnswererName());
                values.put(recentAnswererBio, model.getRecentAnswererBio());
                values.put(recentAnswererId, model.getRecentAnswerId());
                values.put(recentAnswer, model.getRecentAnswer());
                boolean temprecentAnswerImageAttached = model.isRecentAnswerImageAttached();
                int mrecentAnswerImageAttached = temprecentAnswerImageAttached ? 1 : 0;
                values.put(recentAnswerImageAttached, mrecentAnswerImageAttached);
                values.put(recentAnswerImageUrl, model.getRecentAnswerImageUrl());
                values.put(answerCount, model.getAnswerCount());
                values.put(recentAnswerLikeCount, model.getRecentAnswerLikeCount());
                values.put(fontUsed, model.getFontUsed());
                if (database.isOpen()) {
                    database.update(ROOT_QUESTION_MODEL, values, questionId + "=?", new String[]{model.getQuestionId()});
                    values.clear();
                    database.close();

                }
            }
        }catch (SQLiteException e ){
            Log.i("TAG","SqliteException occurs on updating root question model ,"+e.getMessage());
        }catch (NullPointerException e){
            Log.i("TAG","NullPointerException occurs on updating root question model ,"+e.getMessage());
        }
     }

     private void insertSurveyAskedModel(AskSurveyModel model){
        try {
            if (model != null) {
                SQLiteDatabase database = this.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put(askerId, model.getAskerId());
                values.put(askerName, model.getAskerName());
                values.put(askerImageUrlLow, model.getAskerImageUrlLow());
                values.put(askerBio, model.getAskerBio());
                values.put(question, model.getQuestion());
                values.put(timeOfSurvey, model.getTimeOfSurvey());

                boolean temp = model.isOption1();
                int mtemp = temp ? 1 : 0;
                values.put(option1, mtemp);

                temp = model.isOption2();
                mtemp = temp ? 1 : 0;
                values.put(option2, mtemp);

                temp = model.isOption3();
                mtemp = temp ? 1 : 0;
                values.put(option3, mtemp);

                temp = model.isOption4();
                mtemp = temp ? 1 : 0;
                values.put(option4, mtemp);

                values.put(option1Value, model.getOption1Value());
                values.put(option2Value, model.getOption2Value());
                values.put(option3Value, model.getOption3Value());
                values.put(option4Value, model.getOption4Value());
                values.put(option1Count, model.getOption1Count());
                values.put(option2Count, model.getOption2Count());
                values.put(option3Count, model.getOption3Count());
                values.put(option4Count, model.getOption4Count());
                values.put(languageSelectedIndex, model.getLanguageSelectedIndex());
                values.put(surveyId, model.getSurveyId());
                values.put(optionSelectedByMe, model.getOptionSelectedByMe());
                if (database.isOpen()) {
                    database.insertOrThrow(SURVEY_ASKED_TABLE, null, values);
                    values.clear();
                    database.close();
                }
            }
        }catch(SQLiteException e){
            Log.i("TAG","SqliteException occurs in inserting survey asked model ,"+e.getMessage());
        }catch(NullPointerException e){
            Log.i("TAG","NullPointerException occurs in inserting survey asked model ,"+e.getMessage());

        }

     }
     public void updateSurveyAskedModel(AskSurveyModel model){
        try {
            if (model != null) {
                SQLiteDatabase database = this.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put(askerId, model.getAskerId());
                values.put(askerName, model.getAskerName());
                values.put(askerImageUrlLow, model.getAskerImageUrlLow());
                values.put(askerBio, model.getAskerBio());
                values.put(question, model.getQuestion());
                values.put(timeOfSurvey, model.getTimeOfSurvey());

                boolean temp = model.isOption1();
                int mtemp = temp ? 1 : 0;
                values.put(option1, mtemp);

                temp = model.isOption2();
                mtemp = temp ? 1 : 0;
                values.put(option2, mtemp);

                temp = model.isOption3();
                mtemp = temp ? 1 : 0;
                values.put(option3, mtemp);

                temp = model.isOption4();
                mtemp = temp ? 1 : 0;
                values.put(option4, mtemp);

                values.put(option1Value, model.getOption1Value());
                values.put(option2Value, model.getOption2Value());
                values.put(option3Value, model.getOption3Value());
                values.put(option4Value, model.getOption4Value());
                values.put(option1Count, model.getOption1Count());
                values.put(option2Count, model.getOption2Count());
                values.put(option3Count, model.getOption3Count());
                values.put(option4Count, model.getOption4Count());
                values.put(languageSelectedIndex, model.getLanguageSelectedIndex());
                values.put(surveyId, model.getSurveyId());
                values.put(optionSelectedByMe, model.getOptionSelectedByMe());
                if (database.isOpen()) {
                    database.update(SURVEY_ASKED_TABLE, values, surveyId + "=?", new String[]{model.getSurveyId()});
                    values.clear();
                    database.close();
                }
            }
        }catch (SQLiteException e){
            Log.i("TAG", "SqliteException occurs on updating survey asked model ,"+e.getMessage());
        }catch (NullPointerException e){
            Log.i("TAG", "NullPointerException occurs on updating survey asked model ,"+e.getMessage());

        }
     }
     private void clearSurveyAskedModel(){
        try {
            SQLiteDatabase database = this.getWritableDatabase();
            database.delete(SURVEY_ASKED_TABLE, null, null);
            if (database.isOpen())
                database.close();
        }catch(SQLiteException e){
            Log.i("TAG", "SqliteException occurs in clearing asked survey model ,"+e.getMessage());
        }

     }
     private AskSurveyModel getSurveyAskedModel(String surveyId){
        SQLiteDatabase database=this.getWritableDatabase();
        Cursor cursor=database.query(SURVEY_ASKED_TABLE, null, this.surveyId+"=?",
                new String[]{surveyId}, null, null, null,"1");

        if(cursor.moveToFirst()){

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
                String msurveyId=cursor.getString(cursor.getColumnIndex(this.surveyId));
                int moptionSelectedByMe=cursor.getInt(cursor.getColumnIndex(this.optionSelectedByMe));

                AskSurveyModel model = new AskSurveyModel(maskerUid, maskerName, maskerImageUrl,maskerBio,
                        mquestion, mtimeOfSurvey, moption1,
                        moption2, moption3, moption4, moption1Value, moption2Value,
                        moption3Value, moption4Value, moption1Count, moption2Count,
                        moption3Count, moption4Count, mlanguageSelectedIndex, msurveyId);
                model.setOptionSelectedByMe(moptionSelectedByMe);

            if(!cursor.isClosed())
                cursor.close();
            if(database.isOpen())
                database.close();
            return model;
        }
        return null;
     }

     private void insertImagePollModel(AskImagePollModel model){
        try {
            if (model != null) {
                SQLiteDatabase database = this.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put(askerId, model.getAskerId());
                values.put(askerName, model.getAskerName());
                values.put(askerImageUrlLow, model.getAskerImageUrlLow());
                values.put(askerBio, model.getAskerBio());
                values.put(question, model.getQuestion());
                values.put(image1Url, model.getImage1Url());
                values.put(image2Url, model.getImage2Url());
                values.put(timeOfPolling, model.getTimeOfPolling());
                values.put(image1LikeNo, model.getImage1LikeNo());
                values.put(image2LikeNo, model.getImage2LikeNo());
                values.put(imagePollId, model.getImagePollId());
                values.put(optionSelectedByMe, model.getOptionSelectedByMe());
                if (database.isOpen()) {
                    database.insertOrThrow(IMAGE_POLL_TABLE, null, values);
                    values.clear();
                    database.close();
                }
            }
        }catch(SQLiteException e){
            Log.i("TAG", "SQLiteException occurs in inserting image poll model ,"+e.getMessage());

        }catch (NullPointerException e){
            Log.i("TAG", "NullPointerException occurs in inserting image poll model ,"+e.getMessage());
        }
     }
     public void updateImagePollModel(AskImagePollModel model){
        try {
            if (model != null) {
                SQLiteDatabase database = this.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put(askerId, model.getAskerId());
                values.put(askerName, model.getAskerName());
                values.put(askerImageUrlLow, model.getAskerImageUrlLow());
                values.put(askerBio, model.getAskerBio());
                values.put(question, model.getQuestion());
                values.put(image1Url, model.getImage1Url());
                values.put(image2Url, model.getImage2Url());
                values.put(timeOfPolling, model.getTimeOfPolling());
                values.put(image1LikeNo, model.getImage1LikeNo());
                values.put(image2LikeNo, model.getImage2LikeNo());
                values.put(imagePollId, model.getImagePollId());
                values.put(optionSelectedByMe, model.getOptionSelectedByMe());
                if (database.isOpen()) {
                    database.update(IMAGE_POLL_TABLE, values, imagePollId + "=?", new String[]{model.getImagePollId()});
                    values.clear();
                    database.close();
                }
            }
        }catch (SQLiteException e){
            Log.i("TAG", "SQLiteException occurs in updating image poll model ,"+e.getMessage());
        }catch(NullPointerException e){
            Log.i("TAG", "NullPointerException occurs in updating image poll model ,"+e.getMessage());

        }
     }
     private void clearImagePollModel(){
        try {
            SQLiteDatabase database = this.getWritableDatabase();
            database.delete(IMAGE_POLL_TABLE, null, null);
            if (database.isOpen())
                database.close();
        }catch (SQLiteException e){
            Log.i("TAG", "SQLiteException occurs in clearing image poll model ,"+e.getMessage());
        }
     }
     private AskImagePollModel getImagePollModel(String imagePollId){
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor=database.query(IMAGE_POLL_TABLE, null, this.imagePollId+"=?",
                new String[]{imagePollId},null,null,null,"1");
        if(cursor.moveToFirst()){
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
                String mimagePollId=cursor.getString(cursor.getColumnIndex(this.imagePollId));
                int moptionSelectedByMe=cursor.getInt(cursor.getColumnIndex(this.optionSelectedByMe));

                AskImagePollModel model = new AskImagePollModel(
                maskerId,maskerName ,maskerImageUrlLow ,maskerBio , mquestion,
                        mimage1Url, mimage2Url, mtimeOfPolling, mimage1LikeNo, mimage2LikeNo,mimagePollId,
                        moptionSelectedByMe);

            if(database.isOpen())
                database.close();
            if(!cursor.isClosed())
                cursor.close();
            return model;
        }
        return null;
     }

     private void insertObject(String objectId,String type){
        try {
            SQLiteDatabase database=this.getWritableDatabase();
            if (database.isOpen()) {
                ContentValues values = new ContentValues();
                values.put(this.objectId, objectId);
                values.put(this.type, type);
                database.insertOrThrow(HOME_OBJECT, null, values);
                database.close();
            }
        }catch (SQLiteException e){
            Log.i("TAG", "SQLiteException occurs in inserting object,"+e.getMessage());

        }catch (NullPointerException e){
            Log.i("TAG", "NullPointerException occurs in inserting object,"+e.getMessage());

        }
     }

     public void insertHomeObject(ArrayList<Object> list){
        clearRootQuestionModel();
        clearImagePollModel();
        clearSurveyAskedModel();
        clearObject();
        try {
            int k = 0;
            if (list != null) {
                for (Object o : list) {
                    if (k >= 8) {
                        break;
                    }
                    if (o instanceof RootQuestionModel) {
                        insertRootQuestionModel((RootQuestionModel) o);
                        insertObject(((RootQuestionModel) o).getQuestionId(), TYPE_ROOT_QUESTION_MODEL);
                        k++;
                    } else if (o instanceof AskImagePollModel) {
                        insertImagePollModel((AskImagePollModel) o);
                        insertObject(((AskImagePollModel) o).getImagePollId(), TYPE_IMAGE_POLL_MODEL);
                        k++;
                    } else if (o instanceof AskSurveyModel) {
                        insertSurveyAskedModel((AskSurveyModel) o);
                        insertObject(((AskSurveyModel) o).getSurveyId(), TYPE_SURVEY_POLL_MODEL);
                        k++;
                    }
                }
            }
        }catch (SQLiteException e){
            Log.i("TAG", "SqlitException occurs in inserting home object,"+e.getMessage());

        }catch (NullPointerException e){
            Log.i("TAG", "NullPointerException occurs in inserting home object,"+e.getMessage());

        }
     }
     private void clearObject(){
        try {
            SQLiteDatabase database = this.getWritableDatabase();
            if (database.isOpen()) {
                database.delete(HOME_OBJECT, null, null);
                database.close();

            }
        }catch (SQLiteException e){
            Log.i("TAG", "SqliteException occurs in clearing home object ,"+e.getMessage());
        }
     }
     public ArrayList<Object> getHomeObject(){

         SQLiteDatabase database=this.getWritableDatabase();
         Cursor cursor=database.query(HOME_OBJECT, new String[]{this.objectId,this.type}
                 ,null,null,null,null,this.objectId,"8");
         if(cursor.moveToFirst()){
             ArrayList<Object > list = new ArrayList<>();
             do{
                 String objectId=cursor.getString(cursor.getColumnIndex(this.objectId));
                 String type=cursor.getString(cursor.getColumnIndex(this.type));
                 Object o;
                 switch (type){
                     case TYPE_ROOT_QUESTION_MODEL:
                         o=getRootQuestionModel(objectId);
                         if(o!=null)
                             list.add(o);
                         break;
                     case TYPE_IMAGE_POLL_MODEL:
                         o = getImagePollModel(objectId);
                         if(o!=null)
                             list.add(o);
                         break;
                     case TYPE_SURVEY_POLL_MODEL:
                         o=getSurveyAskedModel(objectId);
                         if(o!=null)
                             list.add(o);
                         break;
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

        void insertAnswerLikeModel(ArrayList<String> list){

        try {
            SQLiteDatabase database = this.getWritableDatabase();
            if (database.isOpen()) {
                ContentValues values = new ContentValues();
                if (list != null)
                    for (int i = 0; i < list.size(); i++) {
                        values.put(answerId, list.get(i));
                        database.insertOrThrow(ANSWER_LIKE_TABLE, null, values);
                        values.clear();
                    }
                if (database.isOpen())
                    database.close();

            }
        }catch (SQLiteException e){
            Log.i("TAG", "SqliteException occurs in inserting answer like model ,"+e.getMessage());
        }catch (NullPointerException e){
            Log.i("TAG", "NullPointerException occurs in inserting answer like model ,"+e.getMessage());
        }

        }
        public void insertSingleAnswerLikeModel(String manswerId){
        try {
            assert manswerId != null;
            SQLiteDatabase database = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(answerId, manswerId);
            database.insertOrThrow(ANSWER_LIKE_TABLE, null, values);
            values.clear();
            if (database.isOpen())
                database.close();
        }catch (AssertionError e){
            Log.i("TAG", "Assertion error occurs in inserting answer like model ,"+e.getMessage());
        }catch(SQLiteException e){
            Log.i("TAG", "SqliteException error occurs in inserting answer like model ,"+e.getMessage());

        }catch (NullPointerException e){
            Log.i("TAG", "NullPointer exception occurs in inserting answer like model ,"+e.getMessage());

        }

        }
        void clearAnswerLikeModel(){
        try {
            SQLiteDatabase database = this.getWritableDatabase();
            database.delete(ANSWER_LIKE_TABLE, null, null);
            if (database.isOpen())
                database.close();
        }catch(SQLiteException e){
            Log.i("TAG", "SqliteException occurs in clearing answer like model ,"+e.getMessage());
        }
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
        try {
            SQLiteDatabase database = this.getWritableDatabase();
            database.delete(ANSWER_LIKE_TABLE, answerId + "=?", new String[]{manswerId});
            if (database.isOpen())
                database.close();
        }catch(SQLiteException e){
            Log.i("TAG", "SqliteException occurs in removing answer like model ,"+e.getMessage());
        }
    }

    public void insertSurveyParticipatedModel(HashMap<String,Integer> participatedMap){

        try {
            assert participatedMap != null;
            SQLiteDatabase database = this.getWritableDatabase();
            if (database.isOpen()) {
                ContentValues values = new ContentValues();
                Set<String> participatedId = participatedMap.keySet();
                for (String key : participatedId) {
                    values.put(surveyParticipatedId, key);
                    values.put(optionSelected, participatedMap.get(key));
                    database.insert(SURVEY_PARTICIPATED_TABLE, null, values);
                    values.clear();

                }
                database.close();
            }
        }catch (AssertionError e){
            Log.i("TAG", "Assertion error  occurs in inserting survey participated model ,"+e.getMessage());
        }catch (SQLiteException e){
            Log.i("TAG", "SqliteException occurs in inserting survey participated model ,"+e.getMessage());
        }catch (NullPointerException e){
            Log.i("TAG", "NullPointerException occurs in inserting survey participated model ,"+e.getMessage());

        }
    }
    void clearSurveyParticipatedModel(){
        try {
            SQLiteDatabase database = this.getWritableDatabase();
            database.delete(SURVEY_PARTICIPATED_TABLE, null, null);
            if (database.isOpen())
                database.close();
        }catch (SQLiteException e){
            Log.i("TAG", "SqliteException occurs in clearing survey participated model ,"+e.getMessage());
        }
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
        try {
            assert msurveyParticipatedId != null;
            SQLiteDatabase database = this.getWritableDatabase();
            database.delete(SURVEY_PARTICIPATED_TABLE, surveyParticipatedId + "+=?", new String[]{msurveyParticipatedId});
            if (database.isOpen())
                database.close();
        }catch(AssertionError e){
            Log.i("TAG", "Assertion error occurs in removing survey participated model ,"+e.getMessage());
        }catch(SQLiteException e){
            Log.i("TAG", "SqliteException occurs in removing survey participated model ,"+e.getMessage());
        }
    }

    public void insertImagePollLikeModel(HashMap<String,Integer> imageLikeMap){
        try {
            SQLiteDatabase database = this.getWritableDatabase();
            if (database.isOpen()) {
                ContentValues values = new ContentValues();
                Set<String> imagePollIdSet = imageLikeMap.keySet();
                for (String key : imagePollIdSet) {
                    values.put(imagePollId, key);
                    values.put(optionSelected, imageLikeMap.get(key));
                    database.insert(IMAGE_POLL_LIKE_TABLE, null, values);
                    values.clear();
                }
                if (database.isOpen())
                    database.close();
            }
        }catch (SQLiteException e){
            Log.i("TAG", "SqliteException occurs in inserting image poll like model ,"+e.getMessage());
        }catch (NullPointerException e){
            Log.i("TAG", "NullPointerException occurs in inserting image poll like model ,"+e.getMessage());
        }
    }
     void clearImagePollLikeModel(){
        try {
            SQLiteDatabase database = this.getWritableDatabase();
            database.delete(IMAGE_POLL_LIKE_TABLE, null, null);
            if (database.isOpen())
                database.close();
        }catch (SQLiteException e ){
            Log.i("TAG", "SqliteException occurs in clearing image poll like model ,"+e.getMessage());
        }
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
        try {
            assert mimagePollId != null;
            SQLiteDatabase database = this.getWritableDatabase();
            if (database.isOpen()) {
                database.delete(IMAGE_POLL_LIKE_TABLE, imagePollId + "=?", new String[]{mimagePollId});
                database.close();
            }
        }catch (AssertionError e){
            Log.i("TAG", "Assertion error occurs in removing image poll like model, imagePollLikeModelId is null ,"+e.getMessage());
        }catch (SQLiteException e){
            Log.i("TAG","SqliteException occurs in removing image poll like model ,"+e.getMessage());
        }
    }

    public  void insertFollowerModel(ArrayList<Follower> list){
        try {
            SQLiteDatabase database = this.getWritableDatabase();
            if (list != null) {
                ContentValues values = new ContentValues();
                for (int i = 0; i < list.size(); i++) {
                    Follower model = list.get(i);
                    values.put(followerId, model.getFollowerId());
                    values.put(followerName, model.getFollowerName());
                    values.put(followerImageUrl, model.getFollowerImageUrl());
                    values.put(followerBio, model.getFollowerBio());
                    database.insertOrThrow(FOLLOWER_TABLE, null, values);
                    values.clear();

                }
                if (database.isOpen())
                    database.close();
            }
        }catch (SQLiteException e){
            Log.i("TAG", "SqliteException occurs in inserting follower model ,"+e.getMessage());
        }catch (NullPointerException e){
            Log.i("TAG", "NullPointerExcetion occurs in inserting follower model ,"+e.getMessage());
        }
    }
    public void clearFollowerModel(){
        try {
            SQLiteDatabase database = this.getWritableDatabase();
            if (database.isOpen()) {
                database.delete(FOLLOWER_TABLE, null, null);
                database.close();
            }
        }catch (SQLiteException e){
            Log.i("TAG", "SqliteException occurs in clearing follower model ,"+e.getMessage());
        }


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
        try {
            assert mfollowerId != null;
            SQLiteDatabase database = this.getWritableDatabase();
            database.delete(FOLLOWER_TABLE, followerId + "=?", new String[]{mfollowerId});
            if (database.isOpen())
                database.close();
        }catch (AssertionError e){
            Log.i("TAG", "Assertion error occurs in removing follower model,mfollower id is null ,"+e.getMessage());
        }catch (SQLiteException e){
            Log.i("TAG", "NullPointerException occurs in remove follower model ,"+e.getMessage());

        }
    }

    public  void insertFollowingModel(ArrayList<Following> list){
        clearFollowingModel();
        try {
            SQLiteDatabase database = this.getWritableDatabase();
            if (list != null) {
                ContentValues values = new ContentValues();
                for (int i = 0; i < list.size(); i++) {
                    Following model = list.get(i);
                    values.put(followingId, model.getFollowingId());
                    values.put(followingName, model.getFollowingName());
                    values.put(followingImageUrl, model.getFollowingImageUrl());
                    values.put(followingBio, model.getFollowingBio());
                    database.insertOrThrow(FOLLOWING_TABLE, null, values);
                    values.clear();

                }
                if (database.isOpen())
                    database.close();
            }
        }catch (SQLiteException e){
            Log.i("TAG", "SqliteException occurs in inserting following model ,"+e.getMessage());
        }catch (NullPointerException e){
            Log.i("TAG", "NullPointerException occurs in inserting following model ,"+e.getMessage());

        }
    }
    private void clearFollowingModel(){
        try {
            SQLiteDatabase database = this.getWritableDatabase();
            database.delete(FOLLOWING_TABLE, null, null);
            if (database.isOpen())
                database.close();
        }catch (SQLiteException e){
            Log.i("TAG", "SqliteException occurs in clearing following model ,"+e.getMessage());
        }

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
        try {
            assert mfollowingId!=null;
            SQLiteDatabase database = this.getWritableDatabase();
            if (database.isOpen()) {
                database.delete(FOLLOWING_TABLE, followingId + "=?", new String[]{mfollowingId});
                database.close();
            }
        }catch (AssertionError e){
            Log.i("TAG","Error occured in removing following model,following id is null"+e.getMessage());
        }catch (SQLiteException e){
            Log.i("TAG", "SqliteException occurs in removing following model ,"+e.getMessage());
        }

    }

    public void insertReportedImagePoll(String mimagePollId){
        try {
            SQLiteDatabase database = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(imagePollId, mimagePollId);
            if (database.isOpen()) {
                database.insertOrThrow(IMAGE_POLL_REPORT, null, values);
                values.clear();
                database.close();
            }
        }catch (SQLiteException e){
            Log.i("TAG","SqliteException occurs in inserting reported image poll,"+e.getMessage());
        }
    }
    private void clearReportedImagePoll(){
        try {
            SQLiteDatabase database = this.getWritableDatabase();
            if (database.isOpen()) {
                database.delete(IMAGE_POLL_REPORT, null, null);
                database.close();
            }
        }catch (SQLiteException e){
            Log.i("TAG", "SqliteException occurs in clearing reported image poll");
        }

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

      try {
          SQLiteDatabase database = this.getWritableDatabase();
          ContentValues values = new ContentValues();
          values.put(surveyId, msurveyid);
          if (database.isOpen()) {
              database.insertOrThrow(SURVEY_REPORT, null, values);
              values.clear();
              database.close();
          }
      }catch (SQLiteException e){
          Log.i("TAG", "SqliteException occurs in inserting reported Survey ,"+e.getMessage());
      }
}
    private void clearReportedSurvey(){
        try {
            SQLiteDatabase database = this.getWritableDatabase();
            database.delete(SURVEY_REPORT, null, null);
            if (database.isOpen())
                database.close();
        }catch (SQLiteException e){
            Log.i("TAG", "SqliteException occurs in clearing reported Survey ,"+e.getMessage());
        }
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
        try {
            SQLiteDatabase database = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(questionId, mquestionId);
            if (database.isOpen()) {
                database.insert(QUESTION_REPORT, null, values);
                values.clear();
                database.close();
            }
        }catch(SQLiteException e){
            Log.i("TAG", "Assertion error occurs in inserting reportedQuestion ,"+e.getMessage());
        }catch (NullPointerException e ){
            Log.i("TAG", "NullPointerException occurs in inserting reportedQuestion ,"+e.getMessage());

        }
    }
    private void clearReportedQuestion(){
        try {
            SQLiteDatabase database = this.getWritableDatabase();
            if (database.isOpen()) {
                database.delete(QUESTION_REPORT, null, null);
                database.close();
            }
        }catch (SQLiteException e){
            Log.i("TAG", "SqliteException occurs in clearing reported question ,"+e.getMessage());
        }
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
        try {
            SQLiteDatabase database = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(questionId, model.getQuestionId());
            values.put(askerId, model.getAskerId());
            values.put(likerId, model.getLikerId());
            values.put(likerName, model.getLikerName());
            values.put(likerImageUrl, model.getLikerImageUrl());
            values.put(likerBio, model.getLikerBio());
            values.put(type, model.getType());
            values.put(answerId, model.getAnswerId());
            values.put(answererId, model.getAnswererId());
            values.put(notifiedTime, model.getNotifiedTime());
            values.put(isStoredLocally, model.isStoredLocally() ? 1 : 0);
            if (database.isOpen()) {
                database.insert(NOTIFICATION_QUESTION, null, values);
                values.clear();
                database.close();
            }
        }catch (SQLiteException  e){
                Log.i("TAG", "SqliteExceptioni occrus in inserting notification question ,"+e.getMessage());
         }catch (NullPointerException e ){
            Log.i("TAG", "NullpointerException  occurs in inserting notification question ,"+e.getMessage());
        }

    }
    private QuestionModel getNotificationQuestion(String answerId){
        SQLiteDatabase database=this.getWritableDatabase();
        Cursor cursor=database.query(NOTIFICATION_QUESTION, null,
                this.answerId+"=?", new String[]{answerId},null,null,null,"1");
        if(cursor.moveToNext()){

                String mquestionId=cursor.getString(cursor.getColumnIndex(this.questionId));
                String maskerId=cursor.getString(cursor.getColumnIndex(askerId));
                String mlikerId=cursor.getString(cursor.getColumnIndex(likerId));
                String mlikerName=cursor.getString(cursor.getColumnIndex(this.likerName));
                String mlikerImageUrl=cursor.getString(cursor.getColumnIndex(this.likerImageUrl));
                String mlikerBio=cursor.getString(cursor.getColumnIndex(this.likerBio));
                String mtype=cursor.getString(cursor.getColumnIndex(this.type));
                long mnotifiedTime=cursor.getLong(cursor.getColumnIndex(this.notifiedTime));
                String manswerId=cursor.getString(cursor.getColumnIndex(this.answerId));
                String manswererId=cursor.getString(cursor.getColumnIndex(this.answererId));
                boolean  misStoredLocally=cursor.getInt(cursor.getColumnIndex(isStoredLocally))==1;
                if(!cursor.isClosed()){
                    cursor.close();
                }
                if(database.isOpen())
                    database.close();
                return new QuestionModel(mlikerId, mlikerName,mlikerImageUrl,mlikerBio, maskerId,manswerId,manswererId,
                        mquestionId, mtype,mnotifiedTime,misStoredLocally);

        }
        if(database.isOpen()){
            database.close();
        }
        if(!cursor.isClosed()){
            cursor.close();
        }
        return null;
    }

    public void insertNotificationAnswer(AnswerModel  model){
        try {
            SQLiteDatabase database = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(askerId, model.getAskerId());
            values.put(askerName, model.getAskerName());
            values.put(askerBio, model.getAskerBio());
            values.put(questionId, model.getQuestionId());
            values.put(question, model.getQuestion());
            values.put(timeOfAsking, model.getTimeOfAsking());
            values.put(timeOfAnswering, model.getTimeOfAnswering());
            values.put(answererId, model.getAnswererId());
            values.put(answererName, model.getAnswererName());
            values.put(answererBio, model.getAnswererBio());
            values.put(answerId, model.getAnswerId());
            values.put(answer, model.getAnswer());
            values.put(imageAttached, model.isImageAttached() ? 1 : 0);
            values.put(imageAttachedUrl, model.getImageAttachedUrl());
            values.put(fontUsed, model.getFontUsed());
            values.put(anonymous, model.isAnonymous() ? 1 : 0);
            values.put(notifiedTime, model.getNotifiedTime());
            values.put(answerLikeCount, model.getAnswerLikeCount());
            values.put(type, model.getType());
            values.put(isStoredLocally, model.isStoredLocally() ? 1 : 0);

            if (database.isOpen()){
                database.insertOrThrow(NOTIFICATION_ANSWER, null, values);
                values.clear();
                database.close();
            }
        }catch (SQLiteException e){
            Log.i("TAG", "SqliteException occrus in inserting notification answer ,"+e.getMessage());
        }catch (NullPointerException e){
            Log.i("TAG","NullPointerExceptoin occurs in inserting notification answer ,"+e.getMessage());
        }

    }
    private AnswerModel getNotificationAnswer(String manswerId){
        if(manswerId!=null) {
            SQLiteDatabase database = this.getWritableDatabase();
            Cursor cursor = database.query(NOTIFICATION_ANSWER, null,
                    this.answerId + "=?", new String[]{manswerId}, null, null, null, "1");
            if (cursor.moveToNext()) {

                String askerId = cursor.getString(cursor.getColumnIndex(this.askerId));
                String askerName = cursor.getString(cursor.getColumnIndex(this.askerName));
                String askerBio = cursor.getString(cursor.getColumnIndex(this.askerBio));
                String questionId = cursor.getString(cursor.getColumnIndex(this.questionId));
                String question = cursor.getString(cursor.getColumnIndex(this.question));
                long timeOfAsking = cursor.getLong(cursor.getColumnIndex(this.timeOfAsking));
                long timeOfAnswering = cursor.getLong(cursor.getColumnIndex(this.timeOfAnswering));
                String answererId = cursor.getString(cursor.getColumnIndex(this.answererId));
                String answererName = cursor.getString(cursor.getColumnIndex(this.answererName));
                String answererBio = cursor.getString(cursor.getColumnIndex(this.answererBio));
                String answerId = cursor.getString(cursor.getColumnIndex(this.answerId));
                String answer = cursor.getString(cursor.getColumnIndex(this.answer));
                boolean imageAttached = cursor.getInt(cursor.getColumnIndex(this.imageAttached)) == 1;
                String imageAttachedUrl = cursor.getString(cursor.getColumnIndex(this.imageAttachedUrl));
                int fontUsed = cursor.getInt(cursor.getColumnIndex(this.fontUsed));
                boolean anonymous = cursor.getInt(cursor.getColumnIndex(this.anonymous)) == 1;
                long notifiedTime = cursor.getLong(cursor.getColumnIndex(this.notifiedTime));
                int answerLikeCount = cursor.getInt(cursor.getColumnIndex(this.answerLikeCount));
                String type = cursor.getString(cursor.getColumnIndex(this.type));
                boolean isStoredLocally = cursor.getInt(cursor.getColumnIndex(this.isStoredLocally)) == 1;

                AnswerModel model = new AnswerModel(askerId, askerName, askerBio,
                        questionId, question, timeOfAsking, timeOfAnswering,
                        answererId, answererName, answererBio, answerId, answer,
                        imageAttached, imageAttachedUrl, fontUsed, anonymous,
                        notifiedTime, answerLikeCount, type, isStoredLocally);

                if (!cursor.isClosed()) {
                    cursor.close();
                }
                if (database.isOpen())
                    database.close();
                return model;

            }
            if (database.isOpen()) {
                database.close();
            }
            if (!cursor.isClosed()) {
                cursor.close();
            }
            return null;
        }return null;
    }

    public void insertNotificationImagePoll(ImagePollModel model){

        try {
            SQLiteDatabase database = this.getWritableDatabase();
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
            values.put(containVioloanceOrAdult, (model.isContainVioloanceOrAdult() ? 1 : 0));
            values.put(reported, (model.isReported() ? 1 : 0));
            values.put(type, model.getType());
            values.put(notifiedTime, model.getNotifiedTime());
            values.put(isStoredLocally, model.isStoredLocally() ? 1 : 0);

            if (database.isOpen()) {
                database.insert(NOTIFICATION_IMAGE_POLL, null, values);
                values.clear();
                database.close();
            }
        }catch (SQLiteException e){
            Log.i("TAG", "SqliteException occurs in inserting notification image poll ,"+e.getMessage());

        }catch (NullPointerException e){
            Log.i("TAG", "NullPointerException occurs in inserting notification image poll ,"+e.getMessage());

        }
    }
    private ImagePollModel getNotificationImagePoll(String imagePollId){
        SQLiteDatabase database=this.getWritableDatabase();
        Cursor cursor=database.query(NOTIFICATION_IMAGE_POLL, null,
                this.imagePollId+"=?", new String[]{imagePollId},null,null,null,"1");
        if(cursor.moveToNext()){

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
                boolean misStoredLocally=cursor.getInt(cursor.getColumnIndex(this.isStoredLocally))==1;

                if(!cursor.isClosed()){
                    cursor.close();
                }
                if(database.isOpen())
                    database.close();

                return new ImagePollModel(mimagePollId, mquestion, maskerId, maskerName,
                        maskerBio, maskerImageUrlLow, mimage1Url, mimage2Url,
                        mimage1LikeNo, mimage2LikeNo, mcontainViolanceOrAdult,
                        mreported,mtype, mnotifiedTime,misStoredLocally);

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
        try {
            SQLiteDatabase database = this.getWritableDatabase();
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
            values.put(option1, (model.isOption1() ? 1 : 0));
            values.put(option2, (model.isOption2() ? 1 : 0));
            values.put(option3, (model.isOption3() ? 1 : 0));
            values.put(option4, (model.isOption4() ? 1 : 0));
            values.put(type, model.getType());
            values.put(notifiedTime, model.getNotifiedTime());
            values.put(isStoredLocally, model.isStoredLocally() ? 1 : 0);
            if (database.isOpen()) {
                database.insert(NOTIFICATION_SURVEY, null, values);
                values.clear();
                database.close();
            }
        }catch (SQLiteException e){
            Log.i("TAG", "SqliteException occurs in inserting notification survey ,"+e.getMessage());
        }catch(NullPointerException e){
            Log.i("TAG", "NullPointerException occurs in inserting notification survey ,"+e.getMessage());

        }
    }
    private SurveyModel getNotificationSurvey(String surveyId){
        SQLiteDatabase database=this.getWritableDatabase();
        Cursor cursor=database.query(NOTIFICATION_SURVEY, null,
                this.surveyId+"=?", new String[]{surveyId},null,null,null,"1");
        if(cursor.moveToNext()){

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
                boolean misStoredLocally=cursor.getInt(cursor.getColumnIndex(this.isStoredLocally))==1;
                if(!cursor.isClosed()){
                    cursor.close();
                }
                if(database.isOpen())
                    database.close();

                return new SurveyModel(maskerBio, maskerId, maskerImageUrlLow,
                        maskerName,mquestion, msurveyId, mtimeOfSurvey,
                        moption1Value, moption2Value, moption3Value, moption4Value,
                        moption1Count, moption2Count, moption3Count, moption4Count,
                        moption1, moption2, moption3, moption4, mtype, mnotifiedTime,misStoredLocally);
        }
        if(database.isOpen()){
            database.close();
        }
        if(!cursor.isClosed()){
            cursor.close();
        }
        return null;
    }

    public void insertNotificationFollower(FollowerModel model){

        try {
            SQLiteDatabase database = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(followerId, model.getFollowerId());
            values.put(followerName, model.getFollowerName());
            values.put(followerBio, model.getFollowerBio());
            values.put(followerImageUrl, model.getFollowerImageUrl());
            values.put(selfId, model.getSelfId());
            values.put(isStoredLocally, model.isStoredLocally() ? 1 : 0);
            values.put(notifiedTime, model.getNotifiedTime());
            values.put(type, model.getType());

            if (database.isOpen()) {
                database.insert(NOTIFICATION_FOLLOWER, null, values);
                database.close();
            }
        }catch (SQLiteException e){
            Log.i("TAG","SqliteException occurs in inserting notification follower ,"+e.getMessage());
        }catch (NullPointerException e){
            Log.i("TAG", "NullPointerException occurs in inserting notification follower ,"+e.getMessage());
        }

    }
    private FollowerModel getNotificationFollower(String followerId){
        SQLiteDatabase database=this.getWritableDatabase();
        Cursor cursor=database.query(NOTIFICATION_FOLLOWER, null,
                this.followerId+"=?", new String[]{followerId},null,null,null,"1");
        if(cursor.moveToFirst()){
                String mfollowerId=cursor.getString(cursor.getColumnIndex(this.followerId));
                String mfollowerName=cursor.getString(cursor.getColumnIndex(this.followerName));
                String mfollowerBio=cursor.getString(cursor.getColumnIndex(this.followerBio));
                String mfollowerImageUrl=cursor.getString(cursor.getColumnIndex(this.followerImageUrl));
                String mselfId=cursor.getString(cursor.getColumnIndex(this.selfId));
                boolean misStoredLocally=cursor.getInt(cursor.getColumnIndex(this.isStoredLocally))==1;
                long mnotifiedTime=cursor.getLong(cursor.getColumnIndex(this.notifiedTime));
                String mtype=cursor.getString(cursor.getColumnIndex(this.type));

                FollowerModel model=new FollowerModel(mfollowerId, mfollowerName,
                        mfollowerBio, mfollowerImageUrl, mselfId, misStoredLocally,
                        mnotifiedTime, mtype);

                if(!cursor.isClosed()){
                    cursor.close();
                }
                if(database.isOpen()){
                    database.close();
                }
                return model;
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
    private void clearNotification(String notificationId,String type){
try {
    SQLiteDatabase database = this.getWritableDatabase();
    switch (type) {
        case TYPE_QUESTION:
            database.delete(NOTIFICATION_QUESTION, answerId + "=?", new String[]{notificationId});
            break;
        case TYPE_IMAGE_POLL:
            database.delete(NOTIFICATION_IMAGE_POLL, imagePollId + "=?", new String[]{notificationId});
            break;
        case TYPE_SURVEY:
            database.delete(NOTIFICATION_SURVEY, surveyId + "=?", new String[]{notificationId});
            break;
        case TYPE_ANSWER:
            database.delete(NOTIFICATION_ANSWER, answerId + "=?", new String[]{notificationId});
            break;
        case TYPE_FOLLOWER:
            database.delete(NOTIFICATION_FOLLOWER, followerId + "=?", new String[]{notificationId});
            break;
    }
    database.delete(NOTIFICATION, "notificationId =?", new String[]{notificationId});

    if (database.isOpen()) {
        database.close();
    }
}catch (SQLiteException e){
    Log.i("TAG", "SqliteException occurs in clear notification."+e.getMessage());
}
    }
    public ArrayList<Object> getNotification(){
        SQLiteDatabase database=this.getWritableDatabase();
        Cursor cursor=database.query(NOTIFICATION, new String[]{"ID",notificationId,notificationType}
        ,null,null,null,null,"ID DESC","20");
        if(cursor.moveToFirst()){
            ArrayList<Object> list=new ArrayList<>();

            do{
                String mnotificationId=cursor.getString(cursor.getColumnIndex(notificationId));
                String mnotificationType=cursor.getString(cursor.getColumnIndex(notificationType));
                Object o;
                switch(mnotificationType){

                    case TYPE_QUESTION:
                         o=getNotificationQuestion(mnotificationId);
                        if(o!=null)
                            list.add(o);
                        break;
                    case TYPE_ANSWER:
                        o=getNotificationAnswer(mnotificationId);
                        if(o!=null)
                            list.add(o);
                        break;
                    case TYPE_IMAGE_POLL:
                        o=getNotificationImagePoll(mnotificationId);
                        if(o!=null)
                            list.add(o);
                        break;
                    case TYPE_FOLLOWER:
                        o=getNotificationFollower(mnotificationId);
                        if(o!=null)
                            list.add(o);
                        break;
                    case TYPE_SURVEY:
                        o=getNotificationSurvey(mnotificationId);
                        if(o!=null)
                            list.add(o);
                        break;
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

      void clearAllTable() {
          try {
              SQLiteDatabase database = this.getWritableDatabase();
              if (database.isOpen()) {
                  database.delete(USER_QUESTION_MODEL, null, null);
                  database.delete(ROOT_QUESTION_MODEL, null, null);
                  database.delete(SURVEY_ASKED_TABLE, null, null);
                  database.delete(IMAGE_POLL_TABLE, null, null);
                  database.delete(ANSWER_LIKE_TABLE, null, null);
                  database.delete(SURVEY_PARTICIPATED_TABLE, null, null);
                  database.delete(IMAGE_POLL_LIKE_TABLE, null, null);
                  database.delete(FOLLOWER_TABLE, null, null);
                  database.delete(FOLLOWING_TABLE, null, null);
                  database.delete(NOTIFICATION_QUESTION, null, null);
                  database.delete(NOTIFICATION_ANSWER, null, null);
                  database.delete(NOTIFICATION_IMAGE_POLL, null, null);
                  database.delete(NOTIFICATION_SURVEY, null, null);
                  database.delete(NOTIFICATION_FOLLOWER, null, null);
                  database.delete(NOTIFICATION, null, null);
                  database.delete(HOME_OBJECT, null, null);
                  clearReportedQuestion();
                  clearReportedImagePoll();
                  clearReportedSurvey();
                  database.close();
              }
          }catch (SQLiteException e){
              Log.i("TAG", "SqliteException occurs in deleting table."+e.getLocalizedMessage());
          }
      }



}
