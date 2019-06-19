package com.droid.solver.askapp.Home;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Toast;

import com.droid.solver.askapp.ImagePoll.AskImagePollModel;
import com.droid.solver.askapp.Main.ImagePollClickListener;
import com.droid.solver.askapp.Main.LocalDatabase;
import com.droid.solver.askapp.Question.Follower;
import com.droid.solver.askapp.Question.RootQuestionModel;
import com.droid.solver.askapp.R;
import com.droid.solver.askapp.SignInActivity;
import com.droid.solver.askapp.Survey.AskSurveyModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.common.collect.ArrayTable;
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
    private static final int QUESTION_ORDER=0;
    private static final int IMAGEPOLL_ORDER=1;
    private static final int SURVEY_ORDER=2;
    private Stack<RootQuestionModel> questionModelStack;
    private Stack<AskImagePollModel> imagePollModelStack;
    private Stack<AskSurveyModel> surveyModelStack;
    private DocumentSnapshot lastQuestionSnapshot;
    private DocumentSnapshot lastImagePollSnapshot;
    private DocumentSnapshot lastSurveySnapshot;
    private DocumentSnapshot firstQuestionSnapshot;
    private DocumentSnapshot firstImagePollSnapshot;
    private DocumentSnapshot firstSurveySnapshot;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private FirebaseAuth auth;
    private FirebaseUser user;
    private FirebaseFirestore rootRef;
    ArrayList<Object> list;
    private HomeRecyclerViewAdapter adapter;
    private boolean isLoading=false;
    private CoordinatorLayout rootLayout;

    public HomeFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView=view.findViewById(R.id.recycler_view);

        layoutManager=new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        auth=FirebaseAuth.getInstance();
        list=new ArrayList<>();
        user=auth.getCurrentUser();
        if(user==null){
            startActivity(new Intent(getActivity(), SignInActivity.class));
            getActivity().finish();
        }
        questionModelStack=new Stack<>();
        imagePollModelStack=new Stack<>();
        surveyModelStack=new Stack<>();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        loadDataFromRemoteDatabase();
        initRecyclerView();

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

        query.get().addOnCompleteListener(getActivity(), new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if(task.isSuccessful()){
                    if(task.getResult()!=null)
                    for(QueryDocumentSnapshot snapshot :task.getResult()){
                        AskImagePollModel askImagePollModel=snapshot.toObject(AskImagePollModel.class);
                        imagePollModelStack.add(askImagePollModel);

                    }
                    if(task.getResult()!=null&&task.getResult().getDocuments().size()>0) {
                        firstImagePollSnapshot=task.getResult().getDocuments().get(0);
                        lastImagePollSnapshot = task.getResult().getDocuments().get(task.getResult().size() - 1);
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

        query.get().addOnCompleteListener(getActivity(), new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    if(task.getResult()!=null)
                    for(QueryDocumentSnapshot snapshot :task.getResult()){
                        AskSurveyModel askSurveyModel=snapshot.toObject(AskSurveyModel.class);
                        surveyModelStack.add(askSurveyModel);
                    }
                    if(task.getResult()!=null&&task.getResult().getDocuments().size()>0){
                        firstSurveySnapshot=task.getResult().getDocuments().get(0);
                        lastSurveySnapshot=task.getResult().getDocuments().get(task.getResult().size()-1);
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
        Query query=questionRef
                .whereGreaterThanOrEqualTo("answerCount", 1)
                .orderBy("answerCount", Query.Direction.DESCENDING)
                .orderBy("timeOfAnswering", Query.Direction.DESCENDING)
                .limit(MAXIMUM_NO_OF_QUESTION_LOADED_AT_A_TIME);

        query.get().addOnCompleteListener(getActivity(), new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    if(task.getResult()!=null)
                    for(QueryDocumentSnapshot snapshot:task.getResult()){
                        RootQuestionModel questionModel=snapshot.toObject(RootQuestionModel.class);
                        questionModelStack.add(questionModel);
                    }
                    handleOrderingOfList(QUESTION_ORDER);
                    if(task.getResult()!=null&&task.getResult().getDocuments().size()>0){
                        firstQuestionSnapshot=task.getResult().getDocuments().get(0);
                        lastQuestionSnapshot=task.getResult().getDocuments().get(task.getResult().size()-1);
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

    private void handleOrderingOfList(int order){
        if(order==QUESTION_ORDER){
            list.addAll(makeAHomogenousList(questionModelStack, imagePollModelStack, surveyModelStack));
            adapter.notifyDataSetChanged();
        }

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
        Log.i("TAG", "s1Size :- "+s1Size);
        Log.i("TAG", "s2Size :- "+s2Size);
        Log.i("TAG", "s3Size :- "+s3Size);

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
                ArrayList<String> answerLikeListFromLocalDatabase=new ArrayList<>();
                HashMap<String,Integer> imagePollLikeMapFromLocalDatabase=new HashMap<>();
                HashMap<String,Integer> surveyParticipatedMapFromLocalDatabase=new HashMap<>();
                ArrayList<String> followingIdListFromLocalDatabase=new ArrayList<>();
                adapter=new HomeRecyclerViewAdapter(getActivity(), list,answerLikeListFromLocalDatabase,imagePollLikeMapFromLocalDatabase,
                        surveyParticipatedMapFromLocalDatabase,followingIdListFromLocalDatabase);
                recyclerView.setAdapter(adapter);
            }
        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
        });

    }

    private void loadMoreItemFromRemoteDatabase(){
        onLoadingLoadImagePollFromRemoteDatabase();
        onLoadingLoadSurveyFromRemoteDatabase();
        onLoadingLoadQuestionFromRemoteDatabase();

    }

    private void onLoadingLoadImagePollFromRemoteDatabase(){
        rootRef=FirebaseFirestore.getInstance();
        CollectionReference imagePollRef=rootRef.collection("imagePoll");

        Query query=imagePollRef.orderBy("timeOfPolling", Query.Direction.DESCENDING)
                .whereEqualTo("containVioloanceOrAdult", false)
                .startAfter(lastImagePollSnapshot)
                .limit(MAXIMUM_NO_OF_IMAGEMPOLL_LOADED_AT_A_TIME);

        query.get().addOnCompleteListener(getActivity(), new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot snapshot :task.getResult()){
                        AskImagePollModel askImagePollModel=snapshot.toObject(AskImagePollModel.class);
                        imagePollModelStack.add(askImagePollModel);
                    }
                    if(task.getResult()!=null&&task.getResult().getDocuments().size()>0) {
                        lastImagePollSnapshot = task.getResult().getDocuments().get(task.getResult().size() - 1);
                    }else if(task.getResult()!=null&&task.getResult().getDocuments().size()==0){
                        lastImagePollSnapshot=firstImagePollSnapshot;
                    }
//                    Log.i("TAG", "last image poll snapshot :- "+lastImagePollSnapshot.getId());

                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.i("TAG", "query failed in retriving data from remotedatabase");
            }
        });
    }

    private void onLoadingLoadSurveyFromRemoteDatabase(){
        rootRef=FirebaseFirestore.getInstance();
        CollectionReference surveyRef=rootRef.collection("survey");

        Query query=surveyRef.orderBy("timeOfSurvey", Query.Direction.DESCENDING)
                .startAfter(lastSurveySnapshot)
                .limit(MAXIMUM_NO_OF_SURVEY_LOADED_AT_A_TIME);

        query.get().addOnCompleteListener(getActivity(), new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot snapshot :task.getResult()){
                        AskSurveyModel askSurveyModel=snapshot.toObject(AskSurveyModel.class);
                        surveyModelStack.add(askSurveyModel);
                    }
                    if(task.getResult()!=null&&task.getResult().getDocuments().size()>0){
                        lastSurveySnapshot=task.getResult().getDocuments().get(task.getResult().size()-1);
//                        Log.i("TAG", "last survey documents :- "+lastSurveySnapshot.getId());
                    }else if(task.getResult()!=null&&task.getResult().getDocuments().size()==0){
                        lastSurveySnapshot=firstSurveySnapshot;
                    }
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }

    private void onLoadingLoadQuestionFromRemoteDatabase(){

        rootRef=FirebaseFirestore.getInstance();
        CollectionReference questionRef=rootRef.collection("question");
        Query query=questionRef
                .whereGreaterThanOrEqualTo("answerCount", 1)
                .orderBy("answerCount", Query.Direction.DESCENDING)
                .orderBy("timeOfAnswering", Query.Direction.DESCENDING)
                .startAfter(lastQuestionSnapshot)
                .limit(MAXIMUM_NO_OF_QUESTION_LOADED_AT_A_TIME);

        query.get().addOnCompleteListener(getActivity(), new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    if(task.getResult()!=null)
                    for(QueryDocumentSnapshot snapshot:task.getResult()){
                        RootQuestionModel questionModel=snapshot.toObject(RootQuestionModel.class);
                        questionModelStack.add(questionModel);
                    }
                    if(task.getResult()!=null&&task.getResult().getDocuments().size()>0){
                        lastQuestionSnapshot=task.getResult().getDocuments().get(task.getResult().size()-1);
                    }else if(task.getResult()!=null&&task.getResult().getDocuments().size()==0){
                        lastQuestionSnapshot=firstQuestionSnapshot;
                    }
//                    Log.i("TAG", "last question snapshot :- "+lastQuestionSnapshot.getId());

                    int scrollPosition=list.size()-1;
                    list.remove(scrollPosition);
                    adapter.notifyItemRemoved(scrollPosition);
                    isLoading=false;
                    handleOrderingOfList(QUESTION_ORDER);


                }else {

                    Toast.makeText(getActivity(), "Error in getting documents", Toast.LENGTH_SHORT).show();
                }
            }
        }) .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }

}
