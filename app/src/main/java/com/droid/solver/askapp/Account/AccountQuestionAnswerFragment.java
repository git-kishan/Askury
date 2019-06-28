package com.droid.solver.askapp.Account;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
import android.widget.FrameLayout;

import com.droid.solver.askapp.Answer.UserAnswerModel;
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

public class AccountQuestionAnswerFragment extends Fragment {

    private FrameLayout frameLayout;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager linearLayoutManager;
    private AccountQuestionAnswerRecyclerAdapter adapter;
    private ArrayList<Object> list;
    private boolean isLoading=false;
    private DocumentSnapshot lastVisibleSnapshot=null;
    private String uid=null;

    public AccountQuestionAnswerFragment() {
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_account_question_answer, container, false);
        recyclerView=view.findViewById(R.id.recycler_view);
        linearLayoutManager=new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        frameLayout=view.findViewById(R.id.frameLayout);
        frameLayout.setVisibility(View.GONE);
        list=new ArrayList<>();
        if(FirebaseAuth.getInstance().getCurrentUser()!=null){
            uid=FirebaseAuth.getInstance().getCurrentUser().getUid();
        }
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(isNetworkAvailable()&&uid!=null){
            adapter=new AccountQuestionAnswerRecyclerAdapter(getActivity(), list);
            recyclerView.setAdapter(adapter);
            loadAnswerFromRemoteDatabase();
            recyclerView.addOnScrollListener(scrollListener);
        }else {
            frameLayout.setVisibility(View.VISIBLE);
        }


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
                if(manager!=null&&manager.findLastVisibleItemPosition()==list.size()-1){
                    isLoading=true;
                    list.add(list.size(),null);
                    adapter.notifyItemInserted(list.size());
                    loadMoreAnswerFromRemoteDatabase();

                }
            }
        }
    };

    private void loadAnswerFromRemoteDatabase(){
        if(uid!=null) {
            Query query = FirebaseFirestore.getInstance()
                    .collection("user").document(uid)
                    .collection("answer")
                    .orderBy("timeOfAnswering", Query.Direction.DESCENDING)
                    .limit(5);
            query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if(task.isSuccessful()){
                        if(task.getResult()!=null)
                        for(QueryDocumentSnapshot snapshot:task.getResult()){
                            UserAnswerModel model = snapshot.toObject(UserAnswerModel.class);
                            list.add(model);
                        }
                        if(task.getResult()!=null&&task.getResult().getDocuments().size()>0){
                            lastVisibleSnapshot=task.getResult().getDocuments().get(task.getResult().getDocuments().size()-1);
                        }
                        if(task.getResult()!=null&&task.getResult().getDocuments().size()>0){
                            recyclerView.removeOnScrollListener(scrollListener);
                        }
                        adapter.notifyDataSetChanged();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d("TAG", "failed to fetch answer in account fragment ");
                }
            });
        }

    }
    private void loadMoreAnswerFromRemoteDatabase(){

        if(uid!=null&&lastVisibleSnapshot!=null) {
            Query query = FirebaseFirestore.getInstance()
                    .collection("user").document(uid)
                    .collection("answer")
                    .orderBy("timeOfAnswering", Query.Direction.DESCENDING)
                    .startAfter(lastVisibleSnapshot)
                    .limit(5);
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
                                UserAnswerModel model = snapshot.toObject(UserAnswerModel.class);
                                list.add(model);
                            }
                        if(task.getResult()!=null&&task.getResult().getDocuments().size()>0){
                            lastVisibleSnapshot=task.getResult().getDocuments().get(task.getResult().getDocuments().size()-1);
                        }if(task.getResult()!=null&&task.getResult().getDocuments().size()==0){
                            recyclerView.removeOnScrollListener(scrollListener);
                        }
                        adapter.notifyDataSetChanged();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d("TAG", "failed to fetch answer in account fragment ");
                }
            });
        }
    }
    private boolean isNetworkAvailable(){
        if(getActivity()!=null) {
            ConnectivityManager cmm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
            if(cmm!=null) {
                NetworkInfo activeNetworkInfo = cmm.getActiveNetworkInfo();
                return activeNetworkInfo != null && activeNetworkInfo.isConnected();
            }
        }
        return false;
    }
}
