package com.droid.solver.askapp.Home;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.droid.solver.askapp.ImagePoll.AskImagePollModel;
import com.droid.solver.askapp.Main.LocalDatabase;
import com.droid.solver.askapp.Main.MainActivity;
import com.droid.solver.askapp.Question.RootQuestionModel;
import com.droid.solver.askapp.R;
import com.droid.solver.askapp.SignInActivity;
import com.droid.solver.askapp.Survey.AskSurveyModel;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Stack;

public class HomeFragment extends Fragment  {


    private static final int MAXIMUM_NO_OF_QUESTION_LOADED_AT_A_TIME=5;
    private static final int MAXIMUM_NO_OF_IMAGEMPOLL_LOADED_AT_A_TIME=1;
    private static final int MAXIMUM_NO_OF_SURVEY_LOADED_AT_A_TIME=1;
    private Stack<RootQuestionModel> questionModelStack;
    private Stack<AskImagePollModel> imagePollModelStack;
    private Stack<AskSurveyModel> surveyModelStack;
    private DocumentSnapshot lastQuestionSnapshot;
    private DocumentSnapshot lastImagePollSnapshot;
    private DocumentSnapshot lastSurveySnapshot;
    private RecyclerView recyclerView;
    private ShimmerFrameLayout shimmerFrameLayout;
    private FirebaseFirestore rootRef;
    private ArrayList<Object> list;
    private HomeRecyclerViewAdapter adapter;
    private boolean isLoading=false;
    private ArrayList<String> reportedImageListFromLocalDatabase;
    private ArrayList<String> reportedSurveyListFromLocalDatabase;
    private ArrayList<String> reportedQuestionFromLocalDatabase;
    private HashMap<String, Integer> imagePollLikeMapFromLocalDatabase;
    private HashMap<String, Integer> surveyParticipatedMapFromLocalDatabase;
    private ArrayList<String> answerLikeListFromLocalDatabase;
    private Handler handler;

    public HomeFragment() {
    }


    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView=view.findViewById(R.id.recycler_view);
        shimmerFrameLayout=view.findViewById(R.id.shimmer);
        shimmerFrameLayout.setVisibility(View.VISIBLE);
        LinearLayoutManager layoutManager=new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        FirebaseAuth auth=FirebaseAuth.getInstance();
        list=new ArrayList<>();
        FirebaseUser user=auth.getCurrentUser();
        if(user==null){
            startActivity(new Intent(getActivity(), SignInActivity.class));
            if(getActivity()!=null)
            getActivity().finish();
        }
        handler=new Handler();
        questionModelStack=new Stack<>();
        imagePollModelStack=new Stack<>();
        surveyModelStack=new Stack<>();
        reportedImageListFromLocalDatabase=new ArrayList<>();
        reportedQuestionFromLocalDatabase=new ArrayList<>();
        reportedSurveyListFromLocalDatabase=new ArrayList<>();
        imagePollLikeMapFromLocalDatabase=new HashMap<>();
        surveyParticipatedMapFromLocalDatabase=new HashMap<>();
        answerLikeListFromLocalDatabase=new ArrayList<>();
        Handler handler=new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                if(getActivity()!=null) {
                    LocalDatabase localDatabase = new LocalDatabase(getActivity().getApplicationContext());
                    if(localDatabase.getImagePollReport()!=null)
                    reportedImageListFromLocalDatabase.addAll(localDatabase.getImagePollReport());
                    if(localDatabase.getReportedSurvey()!=null)
                    reportedSurveyListFromLocalDatabase.addAll(localDatabase.getReportedSurvey());
                    if(localDatabase.getReportedQuestion()!=null)
                    reportedQuestionFromLocalDatabase.addAll(localDatabase.getReportedQuestion());
                }
            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initRecyclerView();
        if(MainActivity.isFirstTimeHomeLoaded){
            loadDataFromRemoteDatabase();
        }else {
            lastQuestionSnapshot=MainActivity.homeLastQuestionDocumentSnapshot;
            lastImagePollSnapshot=MainActivity.homeLastImagePollDocumentSnapshot;
            lastSurveySnapshot=MainActivity.homeLastSurveyDocumentSnapshot;
            if(lastQuestionSnapshot!=null||lastSurveySnapshot!=null||lastImagePollSnapshot!=null){
                loadDataFromLocalDatabase();
            }
        }
    }

    private void loadDataFromLocalDatabase(){
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                if(getActivity()!=null) {
                    LocalDatabase database = new LocalDatabase(getActivity().getApplicationContext());
                    list =database.getHomeObject();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            adapter.notifyDataSetChanged();
                            shimmerFrameLayout.setVisibility(View.GONE);
                        }
                    });
                }
            }
        });
    }

    private void loadDataFromRemoteDatabase(){
        loadImagePollFromRemoteDatabase();
        loadSurveyFromRemoteDatabase();
        loadQuestionFromRemoteDatabase();
    }

    private void loadImagePollFromRemoteDatabase(){
        rootRef=FirebaseFirestore.getInstance();
        CollectionReference imagePollRef=rootRef.collection("imagePoll");

        Query query=imagePollRef.whereEqualTo("containVioloanceOrAdult", false)
                .orderBy("timeOfPolling", Query.Direction.DESCENDING)
                .limit(MAXIMUM_NO_OF_IMAGEMPOLL_LOADED_AT_A_TIME);

        if(getActivity()!=null)
        query.get().addOnCompleteListener(getActivity(), new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if(task.isSuccessful()){
                    if(task.getResult()!=null)
                    for(QueryDocumentSnapshot snapshot :task.getResult()){
                        AskImagePollModel askImagePollModel=snapshot.toObject(AskImagePollModel.class);
                        if(askImagePollModel.getImagePollId()!=null&&
                                !reportedImageListFromLocalDatabase.contains(askImagePollModel.getImagePollId())) {
                            int i2 = 0;

                            if(MainActivity.imagePollLikeMap!=null&&MainActivity.imagePollLikeMap.containsKey(askImagePollModel.getImagePollId())) {
                                Integer i1 = MainActivity.imagePollLikeMap.get(askImagePollModel.getImagePollId());
                                String s;
                                if (i1 != null) {
                                    s = i1.toString();
                                    i2 = Integer.parseInt(s);
                                }
                            }
                            else if(imagePollLikeMapFromLocalDatabase!=null&&imagePollLikeMapFromLocalDatabase.
                                    containsKey(askImagePollModel.getImagePollId())){
                                Integer i1 = MainActivity.imagePollLikeMap.get(askImagePollModel.getImagePollId());
                                String s;
                                if (i1 != null) {
                                    s = i1.toString();
                                    i2 = Integer.parseInt(s);
                                }
                            }
                            askImagePollModel.setOptionSelectedByMe(i2);
                            imagePollModelStack.add(askImagePollModel);
                        }
                    }
                    if(task.getResult()!=null&&task.getResult().getDocuments().size()>0) {
                        lastImagePollSnapshot = task.getResult().getDocuments().get(task.getResult().size() - 1);
                        MainActivity.homeLastImagePollDocumentSnapshot=lastImagePollSnapshot;
                    }
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.i("TAG", "query failed in retriving image poll from remotedatabase");
                Log.i("TAG", "query exception :- "+e.getMessage());
                e.printStackTrace();
            }
        });
    }

    private void loadSurveyFromRemoteDatabase(){
        rootRef=FirebaseFirestore.getInstance();
        CollectionReference surveyRef=rootRef.collection("survey");

        Query query=surveyRef.orderBy("timeOfSurvey", Query.Direction.DESCENDING).limit(MAXIMUM_NO_OF_SURVEY_LOADED_AT_A_TIME);

        if(getActivity()!=null)
        query.get().addOnCompleteListener(getActivity(), new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    if(task.getResult()!=null)
                    for(QueryDocumentSnapshot snapshot :task.getResult()){
                        AskSurveyModel askSurveyModel=snapshot.toObject(AskSurveyModel.class);
                        if(askSurveyModel.getSurveyId()!=null&&
                                !reportedSurveyListFromLocalDatabase.contains(askSurveyModel.getSurveyId())){
                            int i2=0;
                            if(MainActivity.surveyParticipatedMap!=null&&MainActivity.surveyParticipatedMap.
                                    containsKey(askSurveyModel.getSurveyId())){
                                Integer i1=MainActivity.surveyParticipatedMap.get(askSurveyModel.getSurveyId());
                                String s;
                                if(i1!=null){
                                    s=i1.toString();
                                    i2=Integer.parseInt(s);
                                }
                            }else if(surveyParticipatedMapFromLocalDatabase!=null&&surveyParticipatedMapFromLocalDatabase.
                                    containsKey(askSurveyModel.getSurveyId())){
                                Integer i1=MainActivity.surveyParticipatedMap.get(askSurveyModel.getSurveyId());
                                String s;
                                if(i1!=null){
                                    s=i1.toString();
                                    i2=Integer.parseInt(s);
                                }
                            }
                            askSurveyModel.setOptionSelectedByMe(i2);
                            surveyModelStack.add(askSurveyModel);
                        }
                    }
                    if(task.getResult()!=null&&task.getResult().getDocuments().size()>0){
                        lastSurveySnapshot=task.getResult().getDocuments().get(task.getResult().size()-1);
                        MainActivity.homeLastSurveyDocumentSnapshot=lastSurveySnapshot;
                    }
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Log.i("TAG", "query failed in retriving survey poll from remotedatabase");

            }
        });
    }

    private void loadQuestionFromRemoteDatabase(){

        rootRef=FirebaseFirestore.getInstance();
        CollectionReference questionRef=rootRef.collection("question");
        final Query query=questionRef
                .whereGreaterThanOrEqualTo("answerCount", 1)
                .orderBy("answerCount", Query.Direction.DESCENDING)
                .orderBy("timeOfAnswering", Query.Direction.DESCENDING)
                .limit(MAXIMUM_NO_OF_QUESTION_LOADED_AT_A_TIME);

        if(getActivity()!=null)
        query.get().addOnCompleteListener(getActivity(), new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    if(task.getResult()!=null)
                    for(QueryDocumentSnapshot snapshot:task.getResult()){
                        RootQuestionModel questionModel=snapshot.toObject(RootQuestionModel.class);
                        if(questionModel.getQuestionId()!=null&&
                                !reportedQuestionFromLocalDatabase.contains(questionModel.getQuestionId())){
                            if(MainActivity.answerLikeList!=null&&MainActivity.answerLikeList.
                                    contains(questionModel.getRecentAnswerId())){
                                questionModel.setLikedByMe(true);
                            }else if(answerLikeListFromLocalDatabase!=null&&answerLikeListFromLocalDatabase.
                                    contains(questionModel.getRecentAnswerId())){
                                questionModel.setLikedByMe(true);
                            }
                            questionModelStack.add(questionModel);
                        }
                    }
                    handleOrderingOfList();
                    if(task.getResult()!=null&&task.getResult().getDocuments().size()>0){
                        lastQuestionSnapshot=task.getResult().getDocuments().get(task.getResult().size()-1);
                        MainActivity.homeLastQuestionDocumentSnapshot=lastQuestionSnapshot;
                    }
                }else {
                    Log.i("TAG", "error occured in getting question documents from remote database");
                }
            }
        }) .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Log.i("TAG", "exception in loading question from remote database :- "+e.getMessage());
                e.printStackTrace();
            }
        });
    }

    private void handleOrderingOfList( ){
        list.addAll(makeAHomogenousList(questionModelStack, imagePollModelStack, surveyModelStack));
        adapter.notifyDataSetChanged();
        shimmerFrameLayout.setVisibility(View.GONE);
        MainActivity.isFirstTimeHomeLoaded=false;
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                if(getActivity()!=null) {
                    LocalDatabase database = new LocalDatabase(getActivity().getApplicationContext());
                    database.insertHomeObject(list);
                }
            }
        });

    }

    private void handleOnLoadingOrderingOfList(){
        list.addAll(makeAHomogenousList(questionModelStack, imagePollModelStack, surveyModelStack));
        adapter.notifyDataSetChanged();
        shimmerFrameLayout.setVisibility(View.GONE);
    }

    //call this function after s1 retrived from remoteDatabase
    private ArrayList<Object> makeAHomogenousList(Stack<RootQuestionModel> s1,Stack<AskImagePollModel> s2,Stack<AskSurveyModel> s3){
        ArrayList<Object> tempList=new ArrayList<>();
        int s1Size=s1.size();
        int s2Size=s2.size();
        int s3Size=s3.size();

        s1Size=s1Size==0?1:s1Size;
        while (!s1.isEmpty()){
            tempList.add(s1.pop());
        }

        for(int i=0;i<(s2Size+s3Size);i++){
            int randomNumber=generateRandomNumber(s1Size++);
            if(!s2.isEmpty()){
                tempList.add(randomNumber,s2.pop());
            }else if(!s3.isEmpty()){
                tempList.add(randomNumber,s3.pop());
            }
        }
        if(s1.isEmpty()) s1.clear();
        if(s2.isEmpty()) s2.clear();
        if(s3.isEmpty()) s3.clear();
        return tempList;
    }

    private int generateRandomNumber(int highRange){
        Random random=new Random();
        return random.nextInt(highRange);

    }

    private void initRecyclerView(){

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                if(getActivity()!=null) {
                    LocalDatabase database = new LocalDatabase(getActivity().getApplicationContext());
                    if(database.getImagePollLikeModel()!=null)
                        imagePollLikeMapFromLocalDatabase=database.getImagePollLikeModel();
                    if(database.getSurveyParticipatedModel()!=null)
                        surveyParticipatedMapFromLocalDatabase=database.getSurveyParticipatedModel();
                    if(database.getAnswerLikeModel()!=null)
                        answerLikeListFromLocalDatabase=database.getAnswerLikeModel();
                }
            }
        });

        Handler handler=new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                ArrayList<String> followingIdListFromLocalDatabase = new ArrayList<>();
                adapter = new HomeRecyclerViewAdapter(getActivity(), list, followingIdListFromLocalDatabase);
                recyclerView.setAdapter(adapter);
                if(getActivity()!=null) {
                    LocalDatabase localDatabase = new LocalDatabase(getActivity().getApplicationContext());
                    if (localDatabase.getFollowingIdList()!=null) {
                        followingIdListFromLocalDatabase.addAll(localDatabase.getFollowingIdList());
                    }
                }
                adapter.notifyDataSetChanged();
            }
        });
        recyclerView.addOnScrollListener(onScrollListener);
    }

    private RecyclerView.OnScrollListener onScrollListener=new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            LinearLayoutManager manager= (LinearLayoutManager) recyclerView.getLayoutManager();
            if(!isLoading){
                if(manager!=null&&manager.findLastVisibleItemPosition()==list.size()-1){
                    list.add(list.size(),null);
                    adapter.notifyItemInserted(list.size());
                    isLoading=true;
                    loadMoreItemFromRemoteDatabase();
                }
            }

        }
    };

    private void loadMoreItemFromRemoteDatabase(){
        onLoadingLoadQuestionFromRemoteDatabase();
        onLoadingLoadImagePollFromRemoteDatabase();
        onLoadingLoadSurveyFromRemoteDatabase();

    }

    private void onLoadingLoadImagePollFromRemoteDatabase() {
        rootRef = FirebaseFirestore.getInstance();
        final CollectionReference imagePollRef = rootRef.collection("imagePoll");

        if (lastImagePollSnapshot != null) {
            Query query = imagePollRef.orderBy("timeOfPolling", Query.Direction.DESCENDING)
                    .whereEqualTo("containVioloanceOrAdult", false)
                    .startAfter(lastImagePollSnapshot)
                    .limit(MAXIMUM_NO_OF_IMAGEMPOLL_LOADED_AT_A_TIME);

            if (getActivity() != null)
                query.get().addOnCompleteListener(getActivity(), new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if (task.isSuccessful()) {
                            if (task.getResult() != null)
                                for (QueryDocumentSnapshot snapshot : task.getResult()) {
                                    AskImagePollModel askImagePollModel = snapshot.toObject(AskImagePollModel.class);
                                    if (askImagePollModel.getImagePollId() != null &&
                                            !reportedImageListFromLocalDatabase.contains(askImagePollModel.getImagePollId())) {
                                        int i2 = 0;
                                        if(MainActivity.imagePollLikeMap!=null&&MainActivity.imagePollLikeMap.
                                                containsKey(askImagePollModel.getImagePollId())) {
                                            Integer i1 = MainActivity.imagePollLikeMap.get(askImagePollModel.getImagePollId());
                                            String s;
                                            if (i1 != null) {
                                                s = i1.toString();
                                                i2 = Integer.parseInt(s);
                                            }
                                        }else if(imagePollLikeMapFromLocalDatabase!=null&&imagePollLikeMapFromLocalDatabase.
                                                containsKey(askImagePollModel.getImagePollId())){
                                            Integer i1 = MainActivity.imagePollLikeMap.get(askImagePollModel.getImagePollId());
                                            String s;
                                            if (i1 != null) {
                                                s = i1.toString();
                                                i2 = Integer.parseInt(s);
                                            }
                                        }
                                        askImagePollModel.setOptionSelectedByMe(i2);
                                        imagePollModelStack.add(askImagePollModel);
                                    }
                                }
                            if (task.getResult() != null && task.getResult().getDocuments().size() > 0) {
                                lastImagePollSnapshot = task.getResult().getDocuments().get(task.getResult().size() - 1);
                            }
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.i("TAG", "query failed in retriving data from remotedatabase");
                    }
                });
        }
    }

    private void onLoadingLoadSurveyFromRemoteDatabase(){
        rootRef=FirebaseFirestore.getInstance();
        final CollectionReference surveyRef=rootRef.collection("survey");

        if (lastSurveySnapshot != null) {
            Query query = surveyRef.orderBy("timeOfSurvey", Query.Direction.DESCENDING)
                    .startAfter(lastSurveySnapshot)
                    .limit(MAXIMUM_NO_OF_SURVEY_LOADED_AT_A_TIME);

            if (getActivity() != null)
                query.get().addOnCompleteListener(getActivity(), new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            if (task.getResult() != null)
                                for (QueryDocumentSnapshot snapshot : task.getResult()) {
                                    AskSurveyModel askSurveyModel = snapshot.toObject(AskSurveyModel.class);
                                    if (askSurveyModel.getSurveyId() != null &&
                                            !reportedSurveyListFromLocalDatabase.contains(askSurveyModel.getSurveyId())) {
                                        int i2=0;
                                        if(MainActivity.surveyParticipatedMap!=null&&MainActivity.surveyParticipatedMap.
                                                containsKey(askSurveyModel.getSurveyId())){
                                            Integer i1=MainActivity.surveyParticipatedMap.get(askSurveyModel.getSurveyId());
                                            String s;
                                            if(i1!=null){
                                                s=i1.toString();
                                                i2=Integer.parseInt(s);
                                            }
                                        }else {
                                            if(surveyParticipatedMapFromLocalDatabase!=null&&surveyParticipatedMapFromLocalDatabase.
                                                    containsKey(askSurveyModel.getSurveyId())){
                                                Integer i1=MainActivity.surveyParticipatedMap.get(askSurveyModel.getSurveyId());
                                                String s;
                                                if(i1!=null){
                                                    s=i1.toString();
                                                    i2=Integer.parseInt(s);
                                                }
                                            }
                                        }
                                        askSurveyModel.setOptionSelectedByMe(i2);
                                        surveyModelStack.add(askSurveyModel);
                                    }
                                }
                            if (task.getResult() != null && task.getResult().getDocuments().size() > 0) {
                                lastSurveySnapshot = task.getResult().getDocuments().get(task.getResult().size() - 1);
                            }
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
        }
    }

    private void onLoadingLoadQuestionFromRemoteDatabase(){

        rootRef=FirebaseFirestore.getInstance();
        CollectionReference questionRef=rootRef.collection("question");

        if(lastQuestionSnapshot!=null) {
            final Query query = questionRef
                    .whereGreaterThanOrEqualTo("answerCount", 1)
                    .orderBy("answerCount", Query.Direction.DESCENDING)
                    .orderBy("timeOfAnswering", Query.Direction.DESCENDING)
                    .startAfter(lastQuestionSnapshot)
                    .limit(MAXIMUM_NO_OF_QUESTION_LOADED_AT_A_TIME);

            if (getActivity() != null)
                query.get().addOnCompleteListener(getActivity(), new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            if (task.getResult() != null)
                                for (QueryDocumentSnapshot snapshot : task.getResult()) {
                                    RootQuestionModel questionModel = snapshot.toObject(RootQuestionModel.class);
                                    if (questionModel.getQuestionId() != null &&
                                            !reportedQuestionFromLocalDatabase.contains(questionModel.getRecentAnswerId())) {
                                        questionModelStack.add(questionModel);
                                    }else {
                                        if(answerLikeListFromLocalDatabase!=null&&answerLikeListFromLocalDatabase.
                                                contains(questionModel.getRecentAnswerId())){
                                            questionModelStack.add(questionModel);
                                        }
                                    }
                                }
                            if (task.getResult() != null && task.getResult().getDocuments().size() > 0) {
                                lastQuestionSnapshot = task.getResult().getDocuments().get(task.getResult().size() - 1);
                            } else if (task.getResult() != null && task.getResult().getDocuments().size() == 0) {
                                recyclerView.removeOnScrollListener(onScrollListener);
                            }
                            int scrollPosition = list.size() - 1;
                            list.remove(scrollPosition);
                            adapter.notifyItemRemoved(scrollPosition);
                            isLoading = false;
                            handleOnLoadingOrderingOfList();

                        } else {

                            Toast.makeText(getActivity(), "Error in getting documents", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
        }
    }

}
