package com.droid.solver.askapp.Account;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.droid.solver.askapp.Answer.UserAnswerModel;
import com.droid.solver.askapp.Main.UidPasserListener;
import com.droid.solver.askapp.R;
import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.NativeAd;
import com.facebook.ads.NativeAdListener;
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
import java.util.Random;

public class AccountQuestionAnswerFragment extends Fragment implements NativeAdListener {

    private FrameLayout frameLayout;
    private RecyclerView recyclerView;
    private AccountQuestionAnswerRecyclerAdapter adapter;
    private ArrayList<Object> list;
    private boolean isLoading=false;
    private DocumentSnapshot lastVisibleSnapshot=null;
    private String uid=null;
    private ImageView qaImage;
    private TextView qaText;
    private NativeAd nativeAd;
    private CardView progressCard;
    public AccountQuestionAnswerFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_account_question_answer, container, false);
        UidPasserListener uidPasserListener= (UidPasserListener) getActivity();
        if(uidPasserListener!=null)
            uid=uidPasserListener.passUid();
        if(getActivity()!=null){
            nativeAd=new NativeAd(getActivity(), getActivity().getString(R.string.placement_id));
            nativeAd.setAdListener(this);
            nativeAd.loadAd(NativeAd.MediaCacheFlag.ALL);
        }
        recyclerView=view.findViewById(R.id.recycler_view);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        frameLayout=view.findViewById(R.id.frameLayout);
        frameLayout.setVisibility(View.GONE);
        qaImage=view.findViewById(R.id.imageView24);
        qaText=view.findViewById(R.id.textView46);
        list=new ArrayList<>();
        if(uid==null) {
            if (FirebaseAuth.getInstance().getCurrentUser() != null)
                uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        }
        progressCard=view.findViewById(R.id.progress_card);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(isNetworkAvailable()){
            if(uid!=null){
                progressCard.setVisibility(View.VISIBLE);
                adapter=new AccountQuestionAnswerRecyclerAdapter(getActivity(), list,uid);
                recyclerView.setAdapter(adapter);
                adapter=new AccountQuestionAnswerRecyclerAdapter(getActivity(), list,uid);
                recyclerView.setAdapter(adapter);
                loadAnswerFromRemoteDatabase();
                recyclerView.addOnScrollListener(scrollListener);

            }
        }else {
            progressCard.setVisibility(View.GONE);
            frameLayout.setVisibility(View.VISIBLE);
        }
    }
    private RecyclerView.OnScrollListener scrollListener=new RecyclerView.OnScrollListener() {
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
                        if(list!=null&&list.size()==0){
                            qaImage.setVisibility(View.VISIBLE);
                            qaText.setVisibility(View.VISIBLE);
                            String status=getString(R.string.some_one_donot_answer_question);
                            qaText.setText(status);
                            recyclerView.setVisibility(View.GONE);
                            progressCard.setVisibility(View.GONE);
                        }else {
                            progressCard.setVisibility(View.GONE);
                            adapter.notifyDataSetChanged();
                            if(task.getResult()!=null&&task.getResult().getDocuments().size()>0){
                                lastVisibleSnapshot=task.getResult().getDocuments().get(task.getResult().getDocuments().size()-1);
                            }
                            if(task.getResult()!=null&&task.getResult().getDocuments().size()>0){
                                recyclerView.removeOnScrollListener(scrollListener);
                            }
                        }
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressCard.setVisibility(View.GONE);
                    Log.d("TAG", "failed to fetch answer in account fragment ");
                }
            });
        }else {
            //uid is null
        }

    }

    private void loadMoreAnswerFromRemoteDatabase(){
        progressCard.setVisibility(View.VISIBLE);
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
                        progressCard.setVisibility(View.GONE);
                        adapter.notifyDataSetChanged();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressCard.setVisibility(View.GONE);
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

    @Override
    public void onMediaDownloaded(Ad ad) {
        if(list!=null) {
            int listSize = list.size();
            Random random = new Random();
            if (!ad.isAdInvalidated()&&listSize>0){
                list.add(random.nextInt(listSize),ad);
                adapter.notifyDataSetChanged();
                Log.i("TAG", "Medial load downloaded ");
            }

        }
    }

    @Override
    public void onError(Ad ad, AdError adError) {

    }

    @Override
    public void onAdLoaded(Ad ad) {
        if(list!=null) {
            int listSize = list.size();
            Random random = new Random();
            if (!ad.isAdInvalidated()&&listSize>0){
                list.add(random.nextInt(listSize),ad);
                adapter.notifyDataSetChanged();
                Log.i("TAG","Ad loaded from facebook ");
            }

        }
    }

    @Override
    public void onAdClicked(Ad ad) {

    }

    @Override
    public void onLoggingImpression(Ad ad) {

    }
}
