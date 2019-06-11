package com.droid.solver.askapp.Home;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class HomeFragment extends Fragment  {


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
        recyclerView.setNestedScrollingEnabled(true);
        list=new ArrayList<>();
        user=auth.getCurrentUser();
        if(user==null){
            startActivity(new Intent(getActivity(), SignInActivity.class));
            getActivity().finish();
        }
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        recyclerView.setLayoutManager(layoutManager);
        adapter=new HomeRecyclerViewAdapter(getActivity(), list);
        recyclerView.setAdapter(adapter);
        loadDataFromRemoteDataabase();

    }
    private void loadDataFromRemoteDataabase(){
        rootRef=FirebaseFirestore.getInstance();
        CollectionReference imagePollRef=rootRef.collection("imagePoll");
        CollectionReference surveyRef=rootRef.collection("survey");
        CollectionReference questionRef=rootRef.collection("question");

        questionRef.whereGreaterThanOrEqualTo("answerCount", 1).get().addOnCompleteListener(getActivity(), new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot snapshot:task.getResult()){
                        RootQuestionModel questionModel=snapshot.toObject(RootQuestionModel.class);
//                        Log.i("TAG", "name :- "+questionModel.getAskerName());
//                        Log.i("TAG", "asker id :- "+questionModel.getAskerId());
//                        Log.i("TAG", "asker image url :- "+questionModel.getAskerImageUrlLow());
                        list.add(questionModel);


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

        imagePollRef.get().addOnCompleteListener(getActivity(), new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot snapshot :task.getResult()){
                        AskImagePollModel askImagePollModel=snapshot.toObject(AskImagePollModel.class);
                        list.add(askImagePollModel);
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
        surveyRef.get().addOnCompleteListener(getActivity(), new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot snapshot :task.getResult()){
                        AskSurveyModel askSurveyModel=snapshot.toObject(AskSurveyModel.class);
                        list.add(askSurveyModel);
                        adapter.notifyDataSetChanged();
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
