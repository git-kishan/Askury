package com.droid.solver.askapp.Account;


import android.graphics.Path;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.text.emoji.widget.EmojiTextView;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.droid.solver.askapp.Question.UserQuestionModel;
import com.droid.solver.askapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class AccountQuestionFragment extends Fragment {


    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Object> list;
    private AccountQuestionRecyclerAdapter adapter;
    private DocumentSnapshot lastVisibleQuestionItem;
    private static final int QUESTION_LIMIT=8;
    private boolean isLoading=false;

    public AccountQuestionFragment() {
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_account_question, container, false);
        recyclerView=view.findViewById(R.id.recycler_view);
        recyclerView.setNestedScrollingEnabled(true);
        initRecyclerView();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }
    private void initRecyclerView(){
        layoutManager=new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        list=new ArrayList<>();
        adapter=new AccountQuestionRecyclerAdapter(getActivity(), list);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(scrollListener);
        loadQuestionFromRemoteDatabase();

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
            if(!isLoading) {
                if (manager != null && manager.findLastVisibleItemPosition() == list.size() - 1) {
                    list.add(list.size(), null);
                    adapter.notifyItemInserted(list.size());
                    isLoading = true;
                    Log.i("TAG", "inside onLoading");
                    loadMoreQuestionFromRemoteDatabase();

                }
            }
        }
    };
    private void loadQuestionFromRemoteDatabase(){
        FirebaseFirestore rootRef=FirebaseFirestore.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user!=null) {
            String uid = user.getUid();
            Query query=rootRef.collection("user").document(uid)
                    .collection("question")
                    .orderBy("timeOfAsking")
                    .limit(QUESTION_LIMIT);
            query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if(task.isSuccessful()){
                        if(task.getResult()!=null)
                        for(QueryDocumentSnapshot snapshot:task.getResult()){
                            UserQuestionModel model = snapshot.toObject(UserQuestionModel.class);
                            list.add(model);
                        }
                        adapter.notifyDataSetChanged();

                        if(task.getResult()!=null&&task.getResult().getDocuments().size()>0)
                        lastVisibleQuestionItem=task.getResult().getDocuments().get(task.getResult().size()-1);

                        if(task.getResult()!=null&&task.getResult().getDocuments().size()==0){
                            recyclerView.removeOnScrollListener(scrollListener);
                        }
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                    //error occurs ..
                }
            });

        }


    }
    private void loadMoreQuestionFromRemoteDatabase(){

        FirebaseFirestore rootRef=FirebaseFirestore.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user!=null&&lastVisibleQuestionItem!=null) {
            String uid = user.getUid();
            Query query=rootRef.collection("user").document(uid)
                    .collection("question")
                    .orderBy("timeOfAsking")
                    .startAfter(lastVisibleQuestionItem)
                    .limit(QUESTION_LIMIT);

            query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if(task.isSuccessful()){
                        int scrollPosition=list.size()-1;
                        list.remove(scrollPosition);
                        adapter.notifyItemRemoved(scrollPosition);
                        isLoading=false;
                        if(task.getResult()!=null)
                            for(QueryDocumentSnapshot snapshot:task.getResult()){
                                UserQuestionModel model = snapshot.toObject(UserQuestionModel.class);
                                list.add(model);
                            }
                        adapter.notifyDataSetChanged();
                        if(task.getResult()!=null&&task.getResult().getDocuments().size()>0)
                            lastVisibleQuestionItem=task.getResult().getDocuments().get(task.getResult().size()-1);

                        if(task.getResult()!=null&&task.getResult().getDocuments().size()==0){
                            recyclerView.removeOnScrollListener(scrollListener);
                        }
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                    //error occurs ...
                }
            });

        }
    }
}
