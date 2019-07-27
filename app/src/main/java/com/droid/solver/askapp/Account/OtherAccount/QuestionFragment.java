package com.droid.solver.askapp.Account.OtherAccount;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.droid.solver.askapp.Main.UidPasserListener;
import com.droid.solver.askapp.Question.UserQuestionModel;
import com.droid.solver.askapp.R;
import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.NativeAdListener;
import com.facebook.ads.NativeBannerAd;
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

public class QuestionFragment extends Fragment implements NativeAdListener {

    private RecyclerView recyclerView;
    private ArrayList<Object> list;
    private QuestionRecyclerAdapter adapter;
    private DocumentSnapshot lastVisibleQuestionItem;
    private static final int QUESTION_LIMIT=8;
    private boolean isLoading=false;
    private String uid=null;
    private ImageView questionImage;
    private TextView questionText;
    private NativeBannerAd nativeBannerAd;
    private CardView progressCard;
    public QuestionFragment() {
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_account_question, container, false);
        if(getActivity()!=null) {
            nativeBannerAd = new NativeBannerAd(getActivity(), getString(R.string.placement_id));
            nativeBannerAd.loadAd();
        }
        nativeBannerAd.setAdListener(this);
        UidPasserListener uidPasserListener= (UidPasserListener) getActivity();
        if(uidPasserListener!=null)
            uid=uidPasserListener.passUid();
        recyclerView=view.findViewById(R.id.recycler_view);
        recyclerView.setNestedScrollingEnabled(true);
        questionImage=view.findViewById(R.id.imageView23);
        questionText=view.findViewById(R.id.textView45);
        if(uid==null){
            if(FirebaseAuth.getInstance().getCurrentUser()!=null)
                uid=FirebaseAuth.getInstance().getCurrentUser().getUid();
        }
        progressCard=view.findViewById(R.id.progress_card);
        progressCard.setVisibility(View.GONE);
        initRecyclerView();
        return view;
    }
//    private void checkFollowerList(){
//        if(FirebaseAuth.getInstance().getCurrentUser()!=null&&uid!=null) {
//            String selfUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
//            if(selfUid.equals(uid)){
//
//            }
//
//        }
//    }
    private void initRecyclerView(){
        LinearLayoutManager layoutManager=new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        list=new ArrayList<>();
        adapter=new QuestionRecyclerAdapter(getActivity(), list);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(scrollListener);
        loadQuestionFromRemoteDatabase();

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
            if(!isLoading) {
                if (manager != null && manager.findLastVisibleItemPosition() == list.size() - 1) {
                    list.add(list.size(), null);
                    adapter.notifyItemInserted(list.size());
                    isLoading = true;
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
                        if(list!=null&&list.size()==0){
                            questionImage.setVisibility(View.VISIBLE);
                            questionText.setVisibility(View.VISIBLE);
                            progressCard.setVisibility(View.GONE);
                            String status=getString(R.string.user_not_asked_question);
                            questionText.setText(status);
                        }else {
                            progressCard.setVisibility(View.GONE);
                            adapter.notifyDataSetChanged();
                            if (task.getResult() != null && task.getResult().getDocuments().size() > 0) {
                                lastVisibleQuestionItem = task.getResult().getDocuments().get(task.getResult().size() - 1);
                            }

                            if (task.getResult() != null && task.getResult().getDocuments().size() == 0) {
                                recyclerView.removeOnScrollListener(scrollListener);
                            }
                        }
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressCard.setVisibility(View.GONE);
                    //error occurs ..
                }
            });
        }else {
            //uid null
            progressCard.setVisibility(View.GONE);
            Log.i("TAG", "uid is null in accountQuestionFragment");
        }




    }
    private void loadMoreQuestionFromRemoteDatabase(){
        progressCard.setVisibility(View.VISIBLE);
        FirebaseFirestore rootRef=FirebaseFirestore.getInstance();
        if(lastVisibleQuestionItem==null){
            recyclerView.removeOnScrollListener(scrollListener);
            progressCard.setVisibility(View.GONE);
            return;
        }
        if(uid !=null) {

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
                        progressCard.setVisibility(View.GONE);
                        adapter.notifyDataSetChanged();
                        if(task.getResult()!=null&&task.getResult().getDocuments().size()>0)
                            lastVisibleQuestionItem=task.getResult().getDocuments().get(task.getResult().size()-1);

                        if(task.getResult()!=null&&task.getResult().getDocuments().size()==0){
                            recyclerView.removeOnScrollListener(scrollListener);
                        }
                        progressCard.setVisibility(View.GONE);
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressCard.setVisibility(View.GONE);
                    //error occurs ...
                }
            });

        }else {
            progressCard.setVisibility(View.GONE);
        }
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
