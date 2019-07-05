package com.droid.solver.askapp.Account;


import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.emoji.widget.EmojiTextView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.droid.solver.askapp.Main.UidPasserListener;
import com.droid.solver.askapp.Question.UserQuestionModel;
import com.droid.solver.askapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class AccountQuestionFragment extends Fragment {


    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Object> list;
    private AccountQuestionRecyclerAdapter adapter;
    private DocumentSnapshot lastVisibleQuestionItem;
    private static final int QUESTION_LIMIT=8;
    private boolean isLoading=false;
    private String uid=null;
    private UidPasserListener uidPasserListener;
    public AccountQuestionFragment() {
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_account_question, container, false);
        uidPasserListener= (UidPasserListener) getActivity();
        if(uidPasserListener!=null)
            uid=uidPasserListener.passUid();
        recyclerView=view.findViewById(R.id.recycler_view);
        recyclerView.setNestedScrollingEnabled(true);
        if(uid==null){
            if(FirebaseAuth.getInstance().getCurrentUser()!=null)
                uid=FirebaseAuth.getInstance().getCurrentUser().getUid();
        }
        initRecyclerView();
        return view;
    }
    private void checkFollowerList(){
        if(FirebaseAuth.getInstance().getCurrentUser()!=null&&uid!=null) {
            String selfUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
            if(selfUid.equals(uid)){

            }

        }
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

        if(uid!=null) {

            FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
            Query query = rootRef.collection("user").document(uid)
                    .collection("question")
                    .orderBy("timeOfAsking")
                    .limit(QUESTION_LIMIT);
            query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        if (task.getResult() != null)
                            for (QueryDocumentSnapshot snapshot : task.getResult()) {
                                UserQuestionModel model = snapshot.toObject(UserQuestionModel.class);
                                list.add(model);
                            }
                        adapter.notifyDataSetChanged();

                        if (task.getResult() != null && task.getResult().getDocuments().size() > 0)
                            lastVisibleQuestionItem = task.getResult().getDocuments().get(task.getResult().size() - 1);

                        if (task.getResult() != null && task.getResult().getDocuments().size() == 0) {
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
        }else {
            //uid null
        }




    }
    private void loadMoreQuestionFromRemoteDatabase(){

        FirebaseFirestore rootRef=FirebaseFirestore.getInstance();
        if(uid !=null&&lastVisibleQuestionItem!=null) {

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
