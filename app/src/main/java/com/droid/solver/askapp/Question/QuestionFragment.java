package com.droid.solver.askapp.Question;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.core.content.res.ResourcesCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.droid.solver.askapp.Answer.AnswerActivity;
import com.droid.solver.askapp.ImagePoll.ImagePollActivity;
import com.droid.solver.askapp.R;
import com.droid.solver.askapp.SignInActivity;
import com.droid.solver.askapp.Survey.QuestionTakerActivity;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.leinardi.android.speeddial.SpeedDialActionItem;
import com.leinardi.android.speeddial.SpeedDialView;
import java.util.ArrayList;


public class QuestionFragment extends Fragment {


    private Animation scaleInAnimation;
    SpeedDialView speedDialView;
    private RecyclerView recyclerView;
    ShimmerFrameLayout shimmerFrameLayout;
    private FirebaseAuth auth;
    private FirebaseUser user;
    private FirebaseFirestore root;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ArrayList<Object> list;
    private LinearLayoutManager layoutManager;
    private AskQuestionRecyclerAdapter adapter;
    private Context context;
    private static final int QUESTION_LIMIT=8;
    private boolean isLoading=false;
    private DocumentSnapshot lastVisibleSnapshot=null;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_question, container, false);

        speedDialView=view.findViewById(R.id.speedDial);
        scaleInAnimation= AnimationUtils.loadAnimation(getActivity(), R.anim.fab_scale_in);
        speedDialView.startAnimation(scaleInAnimation);
        shimmerFrameLayout=view.findViewById(R.id.shimmer);
        shimmerFrameLayout.startShimmer();
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView=view.findViewById(R.id.recycler_view);
        swipeRefreshLayout=view.findViewById(R.id.swipe_refresh);
        auth=FirebaseAuth.getInstance();
        user=auth.getCurrentUser();
        if(user==null){
            startActivity(new Intent(getActivity(), SignInActivity.class));
            if(getActivity()!=null)
            getActivity().finish();
        }
        root=FirebaseFirestore.getInstance();
        addFabItem();
        initScrollListener();
        return view;
    }

    private void initScrollListener(){
        list=new ArrayList<>();
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addOnScrollListener(scrollListener);
        adapter =new AskQuestionRecyclerAdapter(getActivity(), list);
        recyclerView.setAdapter(adapter);
        loadDataFromRemoteDatabase();
        swipeRefreshLayout.setEnabled(true);
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
                        startActivity(new Intent(getActivity(), QuestionTakerActivity.class));
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

    RecyclerView.OnScrollListener scrollListener=new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
            if(!isLoading){
                if(manager!=null&&list!=null&&
                        manager.findLastVisibleItemPosition()==list.size()-1){
                    isLoading=true;
                    list.add(list.size(), null);
                    adapter.notifyItemInserted(list.size());
                    loadMoreDataFromRemoteDatabase();
                }
            }

        }
    };

    private void loadDataFromRemoteDatabase() {

        root.collection("question")
                .whereEqualTo("answerCount",0).
                orderBy("timeOfAsking", Query.Direction.DESCENDING).
                limit(QUESTION_LIMIT).
                get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            if(task.getResult()!=null)
                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                                RootQuestionModel model = documentSnapshot.toObject(RootQuestionModel.class);
                                if(model.getAskerId()!=null&&model.getQuestionId()!=null){
                                    list.add(model);
                                }
                            }
                            if(task.getResult()!=null&&task.getResult().size()>0){
                                lastVisibleSnapshot=task.getResult().getDocuments().get(task.getResult().getDocuments().size()-1);
                            }
                            adapter.notifyDataSetChanged();
                            swipeRefreshLayout.setRefreshing(false);
                            swipeRefreshLayout.setEnabled(false);
                            shimmerFrameLayout.setVisibility(View.GONE);
                        }
                        else {
                            Log.i("TAG", "task exception :- "+task.getException());
                            swipeRefreshLayout.setRefreshing(false);
                            swipeRefreshLayout.setEnabled(false);
                            Log.i("TAG", "exception occurs in getting documents :- " + task.getException());
                            shimmerFrameLayout.setVisibility(View.GONE);

                            Toast.makeText(getActivity(), "Network response is not good", Toast.LENGTH_LONG).show();
                        }
                    }

                });
    }

    private void loadMoreDataFromRemoteDatabase() {

        if(lastVisibleSnapshot!=null) {
            root.collection("question").
                    whereEqualTo("answerCount", 0).
                    orderBy("timeOfAsking", Query.Direction.DESCENDING).
                    startAfter(lastVisibleSnapshot)
                    .limit(QUESTION_LIMIT).
                    get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        int scrollPosition=list.size()-1;
                        list.remove(scrollPosition);
                        adapter.notifyItemRemoved(list.size());
                        isLoading=false;
                        if (task.getResult() != null)
                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                                RootQuestionModel model = documentSnapshot.toObject(RootQuestionModel.class);
                                if (model.getAskerId() != null && model.getQuestionId() != null) {
                                    list.add(model);
                                }
                            }
                        if(task.getResult()!=null&&task.getResult().size()>0){
                            lastVisibleSnapshot=task.getResult().getDocuments().get(task.getResult().getDocuments().size()-1);
                        }else if(task.getResult()!=null&&task.getResult().size()==0){
                            recyclerView.removeOnScrollListener(scrollListener);
                        }
                        adapter.notifyDataSetChanged();
                        swipeRefreshLayout.setRefreshing(false);
                        swipeRefreshLayout.setEnabled(false);
                        shimmerFrameLayout.setVisibility(View.GONE);
                    } else {
                        swipeRefreshLayout.setRefreshing(false);
                        swipeRefreshLayout.setEnabled(false);
                        Log.i("TAG", "exception occurs in getting documents :- " + task.getException());
                        shimmerFrameLayout.setVisibility(View.GONE);

                        Toast.makeText(getActivity(), "Network response is not good", Toast.LENGTH_LONG).show();
                    }
                }

            });
        }
    }

    @Override
    public void onAttach(Context context) {
        this.context=context;
        super.onAttach(context);
    }
    private void goToAnswerActivity(){
        startActivity(new Intent(context,AnswerActivity.class));

    }

}
