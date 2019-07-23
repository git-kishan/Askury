package com.droid.solver.askapp.homeAnswer;

import android.app.Activity;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.res.ResourcesCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import com.droid.solver.askapp.Main.LocalDatabase;
import com.droid.solver.askapp.Main.MainActivity;
import com.droid.solver.askapp.R;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;

public class AnswerActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView recyclerView;
    private String questionId,askerId;
    private FirebaseFirestore firestoreRootRef;
    private FirebaseUser user;
    private ArrayList<Object> objectArrayList;
    private QuestionAnswerRecyclerAdapter adapter;
    private DocumentSnapshot lastSnapshot;
    private boolean isLoading=false;
    private ShimmerFrameLayout shimmerFrameLayout;
    private ArrayList<String> answerLikeListFromLocalDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer2);
        Intent intent=getIntent();
        String askerImageUrl=intent.getStringExtra("askerImageUrl");
        long timeOfAsking=intent.getLongExtra("timeOfAsking",0);
        String askerName=intent.getStringExtra("askerName");
        String askerBio=intent.getStringExtra("askerBio");
        questionId=intent.getStringExtra("questionId");
        String question=intent.getStringExtra("question");
        askerId=intent.getStringExtra("askerId");
        boolean anonymous=intent.getBooleanExtra("anonymous", false);
        Toolbar toolbar=findViewById(R.id.toolbar);
        changeToolbarFont(toolbar, this);
        shimmerFrameLayout=findViewById(R.id.shimmer);
        shimmerFrameLayout.setVisibility(View.VISIBLE);
        recyclerView=findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
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
        loadAnswerLikeListFromLocalDatabase();

    }
    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_out_down, R.anim.slide_out_up);

    }

    private void loadAnswerLikeListFromLocalDatabase(){
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                LocalDatabase database = new LocalDatabase(getApplicationContext());
                answerLikeListFromLocalDatabase=database.getAnswerLikeModel();
            }
        });
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
    private void showSnackbar(){
        CoordinatorLayout rootLayout=findViewById(R.id.root);
        Snackbar.make(rootLayout, "Error occured in getting answers of question", Snackbar.LENGTH_LONG).show();
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
                if(linearLayoutManager!=null&&linearLayoutManager.findLastVisibleItemPosition()==objectArrayList.size()-1){
                    objectArrayList.add(objectArrayList.size(),null);
                    adapter.notifyItemInserted(objectArrayList.size());
                    fetchMoreAnswerFromRemoteDatabase();
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
                            model.setLikedByMe(false);
                            if(MainActivity.answerLikeList!=null&&MainActivity.answerLikeList.contains(model.getAnswerId())){
                                model.setLikedByMe(true);
                            }else if(answerLikeListFromLocalDatabase!=null&&answerLikeListFromLocalDatabase.contains(model.getAnswerId())){
                                model.setLikedByMe(true);
                            }
                            objectArrayList.add(model);
                        }
                        if(task.getResult()!=null) {
                            if (task.getResult().getDocuments().size() > 0)
                                lastSnapshot = task.getResult().getDocuments().get(task.getResult().size() - 1);
                        }

                        adapter.notifyDataSetChanged();
                        shimmerFrameLayout.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                        recyclerView.addOnScrollListener(scrollListener);
                        try {
                            if (objectArrayList != null && objectArrayList.size() > 0) {
                                Object o = objectArrayList.get(objectArrayList.size() - 1);
                                if (o == null) {
                                    objectArrayList.remove(objectArrayList.size() - 1);
                                    adapter.notifyItemRemoved(objectArrayList.size() - 1);
                                    isLoading = false;
                                    recyclerView.removeOnScrollListener(scrollListener);
                                }
                            }
                        }catch (ArrayIndexOutOfBoundsException e){
                            Log.i("TAG", "ArrayIndexOutOfBoundsException occurs in retrieving answer in AnsweActivity "+e.getMessage());
                        }catch (NullPointerException e){
                            Log.i("TAG", "NullPointerException occurs in retrieving answer in AnsweActivity "+e.getMessage());

                        }
                    }else {
                        Log.i("TAG", "fetching answer documents fail");
                        showSnackbar();
                        try {
                            if (objectArrayList != null && objectArrayList.size() > 0) {
                                Object o = objectArrayList.get(objectArrayList.size() - 1);
                                if (o == null) {
                                    objectArrayList.remove(objectArrayList.size() - 1);
                                    adapter.notifyItemRemoved(objectArrayList.size() - 1);
                                    isLoading = false;
                                }
                            }

                            Log.i("TAG", "fetching answer documents fail");
                        }catch (ArrayIndexOutOfBoundsException e){
                            Log.i("TAG", "ArrayIndexOutOfBoundsException occurs in retrieving answer in AnsweActivity "+e.getMessage());
                        }catch (NullPointerException e){
                            Log.i("TAG", "NullPointerException occurs in retrieving answer in AnsweActivity "+e.getMessage());

                        }

                    }
                }
            })  ;
        }
    }

    private void fetchMoreAnswerFromRemoteDatabase(){

        if(lastSnapshot!=null&&questionId!=null&&askerId!=null) {
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
                                model.setLikedByMe(false);
                                if(MainActivity.answerLikeList!=null&&MainActivity.answerLikeList.contains(model.getAnswerId())){
                                    model.setLikedByMe(true);
                                }else if(answerLikeListFromLocalDatabase!=null&&answerLikeListFromLocalDatabase.contains(model.getAnswerId())){
                                    model.setLikedByMe(true);
                                }
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
                        showSnackbar();
                        try {
                            if (objectArrayList != null && objectArrayList.size() > 0) {
                                Object o = objectArrayList.get(objectArrayList.size() - 1);
                                if (o == null) {
                                    objectArrayList.remove(objectArrayList.size() - 1);
                                    adapter.notifyItemRemoved(objectArrayList.size() - 1);
                                    isLoading = false;
                                }
                            }

                            Log.i("TAG", "fetching answer documents fail");
                        }catch (ArrayIndexOutOfBoundsException e){
                            Log.i("TAG", "ArrayIndexOutOfBoundsException occurs in retrieving answer in AnsweActivity "+e.getMessage());
                        }catch (NullPointerException e){
                            Log.i("TAG", "NullPointerException occurs in retrieving answer in AnsweActivity "+e.getMessage());

                        }
                    }
                }
            });

        }
    }
}
