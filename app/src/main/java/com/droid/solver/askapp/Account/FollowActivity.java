package com.droid.solver.askapp.Account;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.droid.solver.askapp.Question.Follower;
import com.droid.solver.askapp.Question.Following;
import com.droid.solver.askapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
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

public class FollowActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String FOLLOWERS="followers";
    private static final String FOLLOWINGS="followings";
    private static  String MODE=null;
    private RecyclerView recyclerView;
    private FollowerAdapter followerAdapter;
    private FollowingAdapter followingAdapter;
    private ArrayList<Object> list;
    private DocumentSnapshot lastVisibleSnapshot;
    private boolean isLoading=false;
    private CoordinatorLayout rootLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follow);
        Intent intent=getIntent();
        String title=intent.getStringExtra("title");
        recyclerView=findViewById(R.id.recycler_view);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        Toolbar toolbar=findViewById(R.id.toobar);
        rootLayout =findViewById(R.id.root_layout);
        changeToolbarFont(toolbar);
        list=new ArrayList<>();
        toolbar.setNavigationOnClickListener(this);
        recyclerView.addOnScrollListener(onScrollListener);
        if(title!=null) {
            if (title.equals(FOLLOWERS)) {
                MODE=FOLLOWERS;
                followerAdapter=new FollowerAdapter(this, list);
                recyclerView.setAdapter(followerAdapter);
                loadFollowersFromRemoteDatabase();
            }else if(title.equals(FOLLOWINGS)){
                MODE=FOLLOWINGS;
                followingAdapter=new FollowingAdapter(this, list);
                recyclerView.setAdapter(followingAdapter);
                loadFollowingFromRemoteDatabase();

            }
        }

    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_out_down, R.anim.slide_out_up);

    }
    public  void changeToolbarFont(Toolbar toolbar) {
        for (int i = 0; i < toolbar.getChildCount(); i++) {
            View view = toolbar.getChildAt(i);
            if (view instanceof TextView) {
                TextView tv = (TextView) view;
                if (tv.getText().equals(toolbar.getTitle())) {
                    applyFont(tv);
                    break;
                }
            }
        }
    }

    private void applyFont(TextView view){
        Typeface typeface= ResourcesCompat.getFont(this,R.font.aclonica);
        view.setTypeface(typeface);
    }

    @Override
    public void onClick(View view) {

        onBackPressed();
    }

    RecyclerView.OnScrollListener onScrollListener=new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
            if(!isLoading) {
                if (manager != null && list != null && manager.findLastVisibleItemPosition() == list.size() - 1) {
                    isLoading = true;
                    list.add(list.size(), null);
                    if (MODE != null && MODE.equals(FOLLOWERS)) {
                        followerAdapter.notifyItemInserted(list.size());
                        loadMoreFollowersFromRemoteDatabase();
                    } else if (MODE != null && MODE.equals(FOLLOWINGS)) {
                        followingAdapter.notifyItemInserted(list.size());
                        loadMoreFollowingFromRemoteDatabase();
                    }
                }
            }

        }
    };

    private void loadFollowersFromRemoteDatabase(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid;
        if(user!=null){
            uid=user.getUid();
                Query query = FirebaseFirestore.getInstance().collection("user")
                        .document(uid).collection("follower").limit(10);
                query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            if(task.getResult()!=null)
                            for(QueryDocumentSnapshot snapshot:task.getResult()){
                                Follower follower=snapshot.toObject(Follower.class);
                                list.add(follower);
                            }
                            if(task.getResult()!=null&&task.getResult().size()>0){
                                lastVisibleSnapshot=task.getResult().getDocuments().get(task.getResult().size()-1);

                            }
                            if(task.getResult()!=null&&task.getResult().size()==0){
                                recyclerView.removeOnScrollListener(onScrollListener);
                            }
                            followerAdapter.notifyDataSetChanged();
                        }else {

                            Snackbar.make(rootLayout, "Can't proceed at this time", Snackbar.LENGTH_LONG).show();

                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Snackbar.make(rootLayout, "Error occured in loading followers", Snackbar.LENGTH_LONG).show();

                    }
                });

        }else {

            Snackbar.make(rootLayout, "Can't proceed at this time", Snackbar.LENGTH_LONG).show();

            //uid is null
        }

    }

    private void loadFollowingFromRemoteDatabase(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid;
        if(user!=null){
            uid=user.getUid();
            Query query = FirebaseFirestore.getInstance().collection("user")
                    .document(uid).collection("following").limit(10);
            query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if(task.isSuccessful()){
                        if(task.getResult()!=null)
                            for(QueryDocumentSnapshot snapshot:task.getResult()){
                                Following follower=snapshot.toObject(Following.class);
                                list.add(follower);
                            }
                        if(task.getResult()!=null&&task.getResult().size()>0){
                            lastVisibleSnapshot=task.getResult().getDocuments().get(task.getResult().size()-1);

                        }
                        if(task.getResult()!=null&&task.getResult().size()==0){
                            recyclerView.removeOnScrollListener(onScrollListener);
                        }

                        followingAdapter.notifyDataSetChanged();
                    }else {
                        Snackbar.make(rootLayout, "Can't proceed at this time", Snackbar.LENGTH_LONG).show();

                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Snackbar.make(rootLayout, "Error occured in loading following", Snackbar.LENGTH_LONG).show();

                }
            });

        }else {
            Snackbar.make(rootLayout, "Can't proceed at this time", Snackbar.LENGTH_LONG).show();
            //uid is null
        }

    }

    private void loadMoreFollowersFromRemoteDatabase(){
        if(lastVisibleSnapshot!=null) {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            String uid ;
            if (user != null) {
                uid = user.getUid();
                Query query = FirebaseFirestore.getInstance().collection("user")
                        .document(uid).collection("follower").startAfter(lastVisibleSnapshot).limit(10);
                query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            int scrollPosition=list.size()-1;
                            list.remove(scrollPosition);
                            followerAdapter.notifyItemRemoved(list.size());
                            isLoading=false;
                            if (task.getResult() != null)
                                for (QueryDocumentSnapshot snapshot : task.getResult()) {
                                    Follower follower = snapshot.toObject(Follower.class);
                                    list.add(follower);
                                }
                            if (task.getResult() != null && task.getResult().size() > 0) {
                                lastVisibleSnapshot = task.getResult().getDocuments().get(task.getResult().size() - 1);

                            }
                            if (task.getResult() != null && task.getResult().size() == 0) {
                                recyclerView.removeOnScrollListener(onScrollListener);
                            }
                            followerAdapter.notifyDataSetChanged();
                        } else {
                            Snackbar.make(rootLayout, "Can't proceed at this time", Snackbar.LENGTH_LONG).show();

                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Snackbar.make(rootLayout, "Error occured in loading followers", Snackbar.LENGTH_LONG).show();

                    }
                });

            } else {
                Snackbar.make(rootLayout, "Can't proceed at this time", Snackbar.LENGTH_LONG).show();
            }
        }

    }

    private void loadMoreFollowingFromRemoteDatabase() {
        if (lastVisibleSnapshot != null) {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            String uid ;
            if (user != null) {
                uid = user.getUid();
                Query query = FirebaseFirestore.getInstance().collection("user")
                        .document(uid).collection("following").startAfter(lastVisibleSnapshot).limit(10);
                query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            int scrollPosition=list.size()-1;
                            list.remove(scrollPosition);
                            followingAdapter.notifyItemRemoved(list.size());
                            isLoading=false;
                            if (task.getResult() != null)
                                for (QueryDocumentSnapshot snapshot : task.getResult()) {
                                    Following follower = snapshot.toObject(Following.class);
                                    list.add(follower);
                                }
                            if (task.getResult() != null && task.getResult().size() > 0) {
                                lastVisibleSnapshot = task.getResult().getDocuments().get(task.getResult().size() - 1);

                            }
                            if (task.getResult() != null && task.getResult().size() == 0) {
                                recyclerView.removeOnScrollListener(onScrollListener);
                            }
                            followingAdapter.notifyDataSetChanged();
                        } else {

                            Snackbar.make(rootLayout, "Can't proceed at this time", Snackbar.LENGTH_LONG).show();

                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        Snackbar.make(rootLayout, "Error occured in loading followings", Snackbar.LENGTH_LONG).show();

                    }
                });

            } else {

                Snackbar.make(rootLayout, "Can't proceed at this time", Snackbar.LENGTH_LONG).show();

            }

        }
    }


}