package com.droid.solver.askapp.Notification;

import android.os.AsyncTask;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.droid.solver.askapp.Main.LocalDatabase;
import com.droid.solver.askapp.R;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.Collections;

public class NotificationFragment extends Fragment {

    private NotificationRecyclerAdapter adapter;
    private ArrayList<Object> list;
    private Query query;
    private ShimmerFrameLayout shimmer;
    private ImageView noNotificationImage;
    private TextView noNotificationText;
    private Handler handler;

    public NotificationFragment() {

    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_notification, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager=new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        shimmer=view.findViewById(R.id.shimmer);
        shimmer.setVisibility(View.VISIBLE);
        noNotificationImage=view.findViewById(R.id.imageView22);
        noNotificationText=view.findViewById(R.id.textView43);
        noNotificationText.setVisibility(View.GONE);
        noNotificationImage.setVisibility(View.GONE);
        list=new ArrayList<>();
        handler=new Handler();
        adapter =new NotificationRecyclerAdapter(getActivity(), list);
        recyclerView.setAdapter(adapter);
        checkNotification();
        return view;
    }

    private void checkNotification(){
        DatabaseReference db= FirebaseDatabase.getInstance().getReference();
        if(FirebaseAuth.getInstance().getCurrentUser()!=null) {
            String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
            query=db.child("user").child(uid).child("notification")
                    .orderByChild("notifiedTime")
                    .limitToFirst(20);
            query.addValueEventListener(listener);
        }else{
            Log.i("TAG", "user is null in notification fragment");

        }
    }

    private ValueEventListener  listener=new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {

            AsyncTask.execute(new Runnable() {
                @Override
                public void run() {

                    for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                        if(snapshot.hasChild("type")) {

                            String type = (String) snapshot.child("type").getValue();

                            if(type!=null&&type.equals("question")){
                                QuestionModel model = snapshot.getValue(QuestionModel.class);
                                list.add(model);
                            }else if(type!=null&&type.equals("imagePoll")){

                                ImagePollModel model = snapshot.getValue(ImagePollModel.class);
                                list.add(model);
                            }else if(type!=null&&type.equals("survey")){
                                SurveyModel model = snapshot.getValue(SurveyModel.class);
                                list.add(model);
                            }else if(type!=null&&type.equals("answer")){
                                AnswerModel model = snapshot.getValue(AnswerModel.class);
                                list.add(model);
                            }else if(type!=null&&type.equals("follower")){
                                FollowerModel model=snapshot.getValue(FollowerModel.class);
                                list.add(model);
                            }
                        }
                    }
                    Collections.reverse(list);
                    if(getActivity()!=null) {
                        LocalDatabase db = new LocalDatabase(getActivity().getApplicationContext());
                        if(db.getNotification()!=null) {
                            list.addAll(db.getNotification());
                        }
                    }
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            if(list!=null&&list.size()==0){
                                noNotificationImage.setVisibility(View.VISIBLE);
                                noNotificationText.setVisibility(View.VISIBLE);
                            }

                            adapter.notifyDataSetChanged();
                            shimmer.setVisibility(View.GONE);
                            query.removeEventListener(listener);
                        }
                    });

                }
            });


        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            noNotificationImage.setVisibility(View.VISIBLE);
            noNotificationText.setVisibility(View.VISIBLE);
            shimmer.setVisibility(View.GONE);
            query.removeEventListener(listener);
            Log.i("TAG", "database error :- "+databaseError);
        }
    };

//    private  ChildEventListener childEventListener=new ChildEventListener() {
//        @Override
//        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//            Log.i("TAG", "Datasnapshot :- child added in childEventListener "+dataSnapshot);
//        }
//
//        @Override
//        public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//
//        }
//
//        @Override
//        public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
//
//        }
//
//        @Override
//        public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//
//        }
//
//        @Override
//        public void onCancelled(@NonNull DatabaseError databaseError) {
//            Log.i("TAG", "database error :- "+databaseError.getDetails());
//        }
//    };

}
