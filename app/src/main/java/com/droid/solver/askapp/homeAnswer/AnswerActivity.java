package com.droid.solver.askapp.homeAnswer;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.droid.solver.askapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class AnswerActivity extends AppCompatActivity implements View.OnClickListener {

    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private String askerImageUrl,askerName,askerBio,questionId,question,askerId;
    private long timeOfAsking;
    private boolean anonymous;
    private FirebaseFirestore firestoreRootRef;
    private FirebaseUser user;
    private ArrayList<Object> objectArrayList;
    private QuestionAnswerRecyclerAdapter adapter;
    private DocumentSnapshot lastSnapshot;
    private boolean isLoading=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer2);
        Intent intent=getIntent();

        askerImageUrl=intent.getStringExtra("askerImageUrl");
        timeOfAsking=intent.getLongExtra("timeOfAsking",0);
        askerName=intent.getStringExtra("askerName");
        askerBio=intent.getStringExtra("askerBio");
        questionId=intent.getStringExtra("questionId");
        question=intent.getStringExtra("question");
        askerId=intent.getStringExtra("askerId");
        anonymous=intent.getBooleanExtra("anonymous", false);
        toolbar=findViewById(R.id.toolbar);
        changeToolbarFont(toolbar, this);
        recyclerView=findViewById(R.id.recycler_view);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        toolbar.setNavigationIcon(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_back_black, null));
        toolbar.setTitleMarginStart(0);
        toolbar.setNavigationOnClickListener(this);
        user= FirebaseAuth.getInstance().getCurrentUser();
        firestoreRootRef=FirebaseFirestore.getInstance();
        objectArrayList=new ArrayList<>();
        adapter=new QuestionAnswerRecyclerAdapter(this, objectArrayList);
        recyclerView.setAdapter(adapter);
        QuestionModel questionModel=new QuestionModel(
        askerId,askerName , askerImageUrl,
                askerBio, timeOfAsking, anonymous, questionId, question);
        objectArrayList.add(questionModel);
        adapter.notifyDataSetChanged();
        fetchAnswerFromRemoteDatabase();


    }


    public  void changeToolbarFont(Toolbar toolbar, Activity context) {
        for (int i = 0; i < toolbar.getChildCount(); i++) {
            View view = toolbar.getChildAt(i);
            if (view instanceof TextView) {
                TextView tv = (TextView) view;
                if (tv.getText().equals(toolbar.getTitle())) {
                    applyFont(tv, context);
                    break;
                }
            }
        }
    }

    public  void applyFont(TextView textView, Activity context) {
        textView.setTypeface(ResourcesCompat.getFont(context, R.font.aclonica));
    }

    @Override
    public void onClick(View view) {
        onBackPressed();
    }

    RecyclerView.OnScrollListener scrollListener=new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            LinearLayoutManager linearLayoutManager= (LinearLayoutManager) recyclerView.getLayoutManager();
            if(!isLoading){
                Log.i("TAG", "is Loading :- "+isLoading);
                if(linearLayoutManager!=null)
                Log.i("TAG", "position :- "+linearLayoutManager.findLastVisibleItemPosition());
                if(linearLayoutManager!=null&&linearLayoutManager.findLastVisibleItemPosition()==objectArrayList.size()-1){
                    objectArrayList.add(objectArrayList.size(),null);
                    adapter.notifyItemInserted(objectArrayList.size());
                    fetchMoreAnswerFromRemoteDatabase();
                    Log.i("TAG", "is Loading :- "+isLoading);
                    isLoading=true;
                }
            }

        }
    };
    private void fetchAnswerFromRemoteDatabase(){

        if(user!=null)
        {
            Query query=firestoreRootRef.collection("user").document(askerId).collection("question")
                    .document(questionId).collection("answer");
            query.orderBy("timeOfAnswering", Query.Direction.DESCENDING)
                    .limit(5)
                    .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {

                    if(task.isSuccessful()){
                        if(task.getResult()!=null)
                        for(QueryDocumentSnapshot snapshot:task.getResult()){
                            AnswerModel model=snapshot.toObject(AnswerModel.class);
                            objectArrayList.add(model);
                        }
                        if(task.getResult()!=null) {
                            if (task.getResult().getDocuments().size() > 0)
                                lastSnapshot = task.getResult().getDocuments().get(task.getResult().size() - 1);
                        }

                        adapter.notifyDataSetChanged();
                        recyclerView.addOnScrollListener(scrollListener);
                    }else {
                        Log.i("TAG", "fetching answer documents fail");
                    }
                }
            })  ;
        }
    }
    private void fetchMoreAnswerFromRemoteDatabase(){

        if(lastSnapshot!=null) {
            Query query = firestoreRootRef.collection("user").document(askerId).collection("question")
                    .document(questionId).collection("answer");
            query.orderBy("timeOfAnswering", Query.Direction.DESCENDING)
                    .startAfter(lastSnapshot)
                    .limit(5)
                    .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {

                    objectArrayList.remove(objectArrayList.size() - 1);
                    adapter.notifyItemRemoved(objectArrayList.size() - 1);
                    isLoading = false;

                    if (task.isSuccessful()) {
                        if (task.getResult() != null)
                            for (QueryDocumentSnapshot snapshot : task.getResult()) {
                                AnswerModel model = snapshot.toObject(AnswerModel.class);
                                objectArrayList.add(model);
                            }
                        adapter.notifyDataSetChanged();

                        if (task.getResult() != null) {
                            if (task.getResult().getDocuments().size() > 0)
                                lastSnapshot = task.getResult().getDocuments().get(task.getResult().size() - 1);
                            if (task.getResult().getDocuments().size() == 0) {
                                recyclerView.removeOnScrollListener(scrollListener);
                            }
                        }

                    } else {
                        Log.i("TAG", "fetching answer documents fail");
                    }
                }
            });

        }
    }
}
