package com.droid.solver.askapp.Home;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.droid.solver.askapp.Community.CommunityModel;
import com.droid.solver.askapp.ImagePoll.AskImagePollModel;
import com.droid.solver.askapp.Question.RootQuestionModel;
import com.droid.solver.askapp.Question.UserQuestionModel;
import com.droid.solver.askapp.R;
import com.droid.solver.askapp.Survey.AskSurveyModel;

import java.util.ArrayList;

public class HomeRecyclerViewAdapter extends RecyclerView.Adapter {
    private ArrayList<Object> list;
    private Context context;
    private LayoutInflater inflater;
    private static final int VERTICAL = 0;
    private static final int HORIZONTAL = 1;
    private static final int LOADING = 2;

    HomeRecyclerViewAdapter(Context context, ArrayList<Object> list) {
        this.context = context;
        this.list = list;
        if (context != null)
            inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        switch (i) {
            case VERTICAL:
                view = inflater.inflate(R.layout.vertical_recycler_view, viewGroup, false);
                return new VerticalRecyclerViewHolder(view, context);
            case HORIZONTAL:
                view = inflater.inflate(R.layout.horizontal_recycler_view, viewGroup, false);
                return new HorizontalRecyclerViewHolder(view, context);
            case LOADING:
                view = inflater.inflate(R.layout.loading, viewGroup, false);
                return new LoadingViewHolderVertically(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int i) {
        if (holder.getItemViewType() == VERTICAL) {
            if (holder instanceof VerticalRecyclerViewHolder) {
                ((VerticalRecyclerViewHolder) holder).recyclerView.setLayoutManager(new LinearLayoutManager(context,
                        LinearLayoutManager.VERTICAL,false));
                ((VerticalRecyclerViewHolder) holder).recyclerView.setNestedScrollingEnabled(false);
                if(list.get(i) instanceof VerticalModel){
                    VerticalRecyclerViewAdapter adapter=new VerticalRecyclerViewAdapter(context, ((VerticalModel) list.get(i)).getList());
                    ((VerticalRecyclerViewHolder) holder).recyclerView.setAdapter(adapter);
                }
            }

        } else if (holder.getItemViewType() == HORIZONTAL) {

            if(holder instanceof HorizontalRecyclerViewHolder){
                ((HorizontalRecyclerViewHolder) holder).recyclerView.setLayoutManager(new LinearLayoutManager(context,
                        LinearLayoutManager.HORIZONTAL,false));
                ((HorizontalRecyclerViewHolder) holder).recyclerView.setNestedScrollingEnabled(false);
                if(list.get(i) instanceof HorizontalModel){
                    HorizontalRecyclerViewAdapter adapter=new HorizontalRecyclerViewAdapter(context, ((HorizontalModel) list.get(i)).getList());
                    ((HorizontalRecyclerViewHolder) holder).recyclerView.setAdapter(adapter);
                }
            }


        } else if (holder.getItemViewType() == LOADING) {


        }
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(list.get(position) instanceof VerticalModel){
            return VERTICAL;
        }else if(list.get(position) instanceof HorizontalModel){
            return HORIZONTAL;
        }
        return -1;
    }
}
