package com.droid.solver.askapp.Home;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private FirebaseAuth auth;
    private FirebaseUser user;
    private FirebaseFirestore rootRef;
    ArrayList<Object> list;
    private HomeRecyclerViewAdapter adapter;

    public HomeFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView=view.findViewById(R.id.recycler_view);
        layoutManager=new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
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
        recyclerView.setLayoutManager(layoutManager);
        adapter=new HomeRecyclerViewAdapter(getActivity(), list);
        recyclerView.setAdapter(adapter);
        loadDataFromRemoteDatabase();

    }
    private void loadDataFromRemoteDatabase(){
        loadImagePollFromRemoteDatabase();
        loadSurveyFromRemoteDatabase();
        loadQuestionFromRemoteDatabase();
    }

    private void loadImagePollFromRemoteDatabase(){
        rootRef=FirebaseFirestore.getInstance();
        CollectionReference imagePollRef=rootRef.collection("imagePoll");

        Query query=imagePollRef.orderBy("timeOfPolling", Query.Direction.DESCENDING)
                .whereEqualTo("containVioloanceOrAdult", false).limit(MAXIMUM_NO_OF_IMAGEMPOLL_LOADED_AT_A_TIME);

        query.get().addOnCompleteListener(getActivity(), new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if(task.isSuccessful()){
                    if(task.getResult()!=null)
                    for(QueryDocumentSnapshot snapshot :task.getResult()){
                        Log.i("TAG", "image poll retrive document size :- "+task.getResult().getDocuments().size());
                        AskImagePollModel askImagePollModel=snapshot.toObject(AskImagePollModel.class);
                        imagePollModelStack.add(askImagePollModel);
                    }
                    if(task.getResult()!=null&&task.getResult().getDocuments().size()>0) {
                        lastImagePollSnapshot = task.getResult().getDocuments().get(task.getResult().size() - 1);
                    }
                    Log.i("TAG", "last image poll snapshot :- "+lastImagePollSnapshot.getId());
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.i("TAG", "query failed in retriving data from remotedatabase");
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
                        Log.i("TAG", "survey poll retrive document size :- "+task.getResult().getDocuments().size());
                        AskSurveyModel askSurveyModel=snapshot.toObject(AskSurveyModel.class);
                        surveyModelStack.add(askSurveyModel);
                    }
                    if(task.getResult()!=null&&task.getResult().getDocuments().size()>0){
                        lastSurveySnapshot=task.getResult().getDocuments().get(task.getResult().size()-1);
                        Log.i("TAG", "last survey documents :- "+lastSurveySnapshot.getId());
                    }
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }

    private void loadQuestionFromRemoteDatabase(){

        rootRef=FirebaseFirestore.getInstance();
        CollectionReference questionRef=rootRef.collection("question");
        Query query=questionRef.whereGreaterThanOrEqualTo("answerCount", 1)
                .limit(MAXIMUM_NO_OF_QUESTION_LOADED_AT_A_TIME);

        query.get().addOnCompleteListener(getActivity(), new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot snapshot:task.getResult()){
                        RootQuestionModel questionModel=snapshot.toObject(RootQuestionModel.class);
                        questionModelStack.add(questionModel);
                    }
                    handleOrderingOfList(QUESTION_ORDER);
                    if(task.getResult()!=null&&task.getResult().getDocuments().size()>0){
                        lastQuestionSnapshot=task.getResult().getDocuments().get(task.getResult().size()-1);
                        Log.i("TAG", "last question snapshot :- "+lastQuestionSnapshot.getId());
                    }


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

        for(int i=0;i<s1Size;i++){
            if(!s1.isEmpty()){
                tempList.add(s1.pop());

            }
        }

        for(int i=0;i<(s2Size+s3Size);i++){
            int randomNumber=generateRandomNumber(s1Size++);
            if(!s1.isEmpty()){
                tempList.add(randomNumber,s1.pop());
            }else if(!s2.isEmpty()){
                tempList.add(randomNumber,s2.pop());
            }
        }
        s1.empty();
        s2.empty();
        s3.empty();
        Log.i("TAG", "temp list size :- "+tempList.size());
        return tempList;
    }


    private int generateRandomNumber(int lastRange){
        Random random=new Random();
        return random.nextInt(lastRange);

    }

//    private void onLoadingLoadImagePollFromRemoteDatabase(){
//        rootRef=FirebaseFirestore.getInstance();
//        CollectionReference imagePollRef=rootRef.collection("imagePoll");
//
//        Query query=imagePollRef.orderBy("timeOfPolling", Query.Direction.DESCENDING)
//                .whereEqualTo("containVioloanceOrAdult", false).limit(MAXIMUM_NO_OF_IMAGEMPOLL_LOADED_AT_A_TIME);
//
//        query.get().addOnCompleteListener(getActivity(), new OnCompleteListener<QuerySnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//
//                if(task.isSuccessful()){
//                    for(QueryDocumentSnapshot snapshot :task.getResult()){
//                        AskImagePollModel askImagePollModel=snapshot.toObject(AskImagePollModel.class);
//                        imagePollModelStack.add(askImagePollModel);
//                        list=handleOrderingOfList(IMAGEPOLL_ORDER);
//                        if(list!=null&&list.size()>0){
//                            adapter.notifyDataSetChanged();
//                        }
//                    }
//                }
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                Log.i("TAG", "query failed in retriving data from remotedatabase");
//            }
//        });
//    }
//
//    private void onLoadingLoadSurveyFromRemoteDatabase(){
//        rootRef=FirebaseFirestore.getInstance();
//        CollectionReference surveyRef=rootRef.collection("survey");
//
//        Query query=surveyRef.orderBy("timeOfSurvey", Query.Direction.DESCENDING).limit(MAXIMUM_NO_OF_SURVEY_LOADED_AT_A_TIME);
//
//        query.get().addOnCompleteListener(getActivity(), new OnCompleteListener<QuerySnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                if(task.isSuccessful()){
//                    for(QueryDocumentSnapshot snapshot :task.getResult()){
//                        AskSurveyModel askSurveyModel=snapshot.toObject(AskSurveyModel.class);
//                        surveyModelStack.add(askSurveyModel);
//                        handleOrderingOfList(SURVEY_ORDER);
//                        if(list!=null&&list.size()>0){
//                            adapter.notifyDataSetChanged();
//                        }
//                    }
//                }
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//
//            }
//        });
//    }
//
//    private void onLoadingLoadQuestionFromRemoteDatabase(){
//
//        rootRef=FirebaseFirestore.getInstance();
//        CollectionReference questionRef=rootRef.collection("question");
//        Query query=questionRef.whereGreaterThanOrEqualTo("answerCount", 1)
//                .limit(MAXIMUM_NO_OF_QUESTION_LOADED_AT_A_TIME);
//
//        query.get().addOnCompleteListener(getActivity(), new OnCompleteListener<QuerySnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                if(task.isSuccessful()){
//
//                    for(QueryDocumentSnapshot snapshot:task.getResult()){
//                        RootQuestionModel questionModel=snapshot.toObject(RootQuestionModel.class);
//                        questionModelStack.add(questionModel);
//                        list= handleOrderingOfList(QUESTION_ORDER);
//                        if(list!=null&&list.size()>0){
//                            adapter.notifyDataSetChanged();
//                        }
//
//                    }
//
//                }else {
//
//                    Toast.makeText(getActivity(), "Error in getting documents", Toast.LENGTH_SHORT).show();
//                }
//            }
//        }) .addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//
//            }
//        });
//    }
}
