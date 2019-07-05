package com.droid.solver.askapp.Notification;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.droid.solver.askapp.R;

import java.util.ArrayList;
import java.util.List;

public class NotificationFragment extends Fragment {


    private RecyclerView recyclerView ;
    private LinearLayoutManager layoutManager;
    private NotificationRecyclerAdapter adapter;
    private List<Object> list;


    public NotificationFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_notification, container, false);
        recyclerView = view.findViewById(R.id.recycler_view);
        layoutManager=new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        list=new ArrayList<>();
        return view;
    }
    private void checkNotification(){

    }


}
