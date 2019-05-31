package com.droid.solver.askapp.Question;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.BounceInterpolator;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.droid.solver.askapp.ImagePoll.ImagePollActivity;
import com.droid.solver.askapp.R;
import com.droid.solver.askapp.Survey.LanguageSelectionActivity;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.leinardi.android.speeddial.SpeedDialActionItem;
import com.leinardi.android.speeddial.SpeedDialView;

import org.w3c.dom.Document;

import java.util.ArrayList;


public class QuestionFragment extends Fragment {


    private Animation scaleInAnimation;
    SpeedDialView speedDialView;
    private RecyclerView recyclerView;
    ShimmerFrameLayout shimmerFrameLayout;
    private FirebaseAuth auth;
    private FirebaseUser user;
    private FirebaseFirestore root;
    private ArrayList<AskQuestionModel> questionList;
    private LinearLayoutManager layoutManager;
    private AskQuestionRecyclerAdapter adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_question, container, false);
        speedDialView=view.findViewById(R.id.speedDial);
        scaleInAnimation= AnimationUtils.loadAnimation(getActivity(), R.anim.fab_scale_in);
        speedDialView.startAnimation(scaleInAnimation);
        recyclerView=view.findViewById(R.id.recycler_view);
        shimmerFrameLayout=view.findViewById(R.id.shimmer);
        shimmerFrameLayout.startShimmer();
        auth=FirebaseAuth.getInstance();
        user=auth.getCurrentUser();
        root=FirebaseFirestore.getInstance();
        addFabItem();
        loadDataFromRemoteDatabase();
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        questionList=new ArrayList<>();
        adapter=new AskQuestionRecyclerAdapter(getActivity(), questionList);
        recyclerView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        speedDialView.setOnActionSelectedListener(new SpeedDialView.OnActionSelectedListener() {
            @Override
            public boolean onActionSelected(SpeedDialActionItem speedDialActionItem) {
                switch (speedDialActionItem.getId()) {
                    case R.id.fab_action1:
                        startActivity(new Intent(getActivity(), ImagePollActivity.class));
                        return false;
                    case R.id.fab_action2:
                        startActivity(new Intent(getActivity(), LanguageSelectionActivity.class));
                        return false;
                    case R.id.fab_action3:
                        startActivity(new Intent(getActivity(),QuestionActivity.class));
                        return false; // true to keep the floating button Speed Dial open

                    default:
                        return false;
                }
            }
        });
    }

    private void addFabItem(){

        speedDialView.addActionItem(
                new SpeedDialActionItem.Builder(R.id.fab_action1, R.drawable.ic_imagepoll)
                        .setFabBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.google_color,null))
                        .setLabel("Image poll")
                        .setLabelColor(ResourcesCompat.getColor(getResources(), android.R.color.black, null))
                        .setLabelBackgroundColor(ResourcesCompat.getColor(getResources(), android.R.color.white,null))
                        .create());

        speedDialView.addActionItem(
                new SpeedDialActionItem.Builder(R.id.fab_action2, R.drawable.ic_survey)
                        .setFabBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.colorPrimary,null))
                        .setLabel("Survey")
                        .setLabelColor(ResourcesCompat.getColor(getResources(), android.R.color.black, null))
                        .setLabelBackgroundColor(ResourcesCompat.getColor(getResources(), android.R.color.white,null))
                        .create());

        speedDialView.addActionItem(
                new SpeedDialActionItem.Builder(R.id.fab_action3, R.drawable.ic_ask)
                        .setFabBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.yellow,null))
                        .setLabel("Ask")
                        .setLabelColor(ResourcesCompat.getColor(getResources(), android.R.color.black, null))
                        .setLabelBackgroundColor(ResourcesCompat.getColor(getResources(), android.R.color.white,null))
                        .create());

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    private void loadDataFromRemoteDatabase(){

        root.collection("question").get().
                addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot documentSnapshot:task.getResult()){
                                AskQuestionModel model=documentSnapshot.toObject(AskQuestionModel.class);
                                questionList.add(model);
//                                String askerName=documentSnapshot.getString("askerName");
//                                String askerUid=documentSnapshot.getString("askerUid");
//                                Long timeOfAsking=documentSnapshot.getLong("timeOfAsking");
//                                String question=documentSnapshot.getString("question");
//                                String userImageUrl=documentSnapshot.getString("userImageUrl");
//                                Boolean isImageAttached=documentSnapshot.getBoolean("isImageAttached");
//                                String imageEncodedString=documentSnapshot.getString("imageEncodedString");
//                                Boolean isAnonymous=documentSnapshot.getBoolean("isAnonymous");
//                                questionList.add(model);
//
//                                Log.i("TAG", "asker name :- "+askerName);
//                                Log.i("TAG", "asker uid :- "+askerUid);
//                                Log.i("TAG", "time of asking  :- "+timeOfAsking);
//                                Log.i("TAG", "question :- "+question);
//                                Log.i("TAG", "userImgeUrl :- "+userImageUrl);
//                                Log.i("TAG", "is Image Attached :- "+isImageAttached);
//                                Log.i("TAG", "is anonymous :- "+isAnonymous);
//                                Log.i("TAG", "BLANK :- ");
//                                Log.i("TAG", "BLANK :- ");


//                                Log.i("TAG", "asker name :- "+model.getAskerName());
//                                Log.i("TAG", "asker uid :- "+model.getAskerUid());
//                                Log.i("TAG", "time of asking  :- "+model.getTimeOfAsking());
//                                Log.i("TAG", "question :- "+model.getQuestion());
//                                Log.i("TAG", "userImgeUrl :- "+model.getUserImageUrl());
//                                Log.i("TAG", "is Image Attached :- "+model.isImageAttached());
//                                Log.i("TAG", "is anonymous :- "+model.isImageAttached());
//                                Log.i("TAG", "BLANK :- ");
//                                Log.i("TAG", "BLANK :- ");
                            }
                            adapter.notifyDataSetChanged();

                            shimmerFrameLayout.setVisibility(View.GONE);
                        }else {
                            Log.i("TAG", "exception occurs in getting documents :- "+task.getException());
                            shimmerFrameLayout.setVisibility(View.GONE);
                            //show error getting in documents
                            //task.getException()
                            Toast.makeText(getActivity(),"Network response is not good" , Toast.LENGTH_LONG).show();
                        }
                    }
                }) ;
    }

}
