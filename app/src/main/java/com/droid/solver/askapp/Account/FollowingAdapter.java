package com.droid.solver.askapp.Account;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.droid.solver.askapp.GlideApp;
import com.droid.solver.askapp.Home.LoadingViewHolderVertically;
import com.droid.solver.askapp.Main.Constants;
import com.droid.solver.askapp.Question.Following;
import com.droid.solver.askapp.R;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
 class FollowingAdapter extends RecyclerView.Adapter{

    private Context context;
    private LayoutInflater inflater;
    private ArrayList<Object> list;
    private final int FOLLOWING=1;
    private final int LOADING=0;
     FollowingAdapter(Context context, ArrayList<Object> list){
        this.context=context;
        if(context!=null){
            this.list=list;
            inflater = LayoutInflater.from(context);
        }

    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        RecyclerView.ViewHolder viewHolder;
        switch (viewType){
            case FOLLOWING:
                view=inflater.inflate(R.layout.follower_item, parent,false);
                viewHolder=new FollowingViewHolder(view);
                break;
            case LOADING:
                view=inflater.inflate(R.layout.loading,parent,false);
                viewHolder=new LoadingViewHolderVertically(view);
                break;
            default:
                view=inflater.inflate(R.layout.loading,parent,false);
                viewHolder=new LoadingViewHolderVertically(view);
                break;
        }
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(list.get(position) instanceof Following && holder instanceof FollowingViewHolder){

            Following model= (Following) list.get(position);
            String bio=model.getFollowingBio();
            String uid=model.getFollowingId();
            String name=model.getFollowingName();

            name=name==null?"Someone":name;
            bio=bio==null?"":bio;

            ((FollowingViewHolder) holder).profileName.setText(name);
            ((FollowingViewHolder) holder).bio.setText(bio);

            StorageReference reference = FirebaseStorage.getInstance().getReference();
            String imageUrl= Constants.PROFILE_PICTURE+"/"+uid+Constants.SMALL_THUMBNAIL;

            GlideApp.with(context)
                    .load(reference.child(imageUrl))
                    .error(android.R.color.holo_blue_dark)
                    .placeholder(android.R.color.holo_blue_dark)
                    .into(((FollowingViewHolder) holder).profileImage);

        }
    }
    @Override
    public int getItemCount() {
        return list==null?0:list.size();
    }
    @Override
    public int getItemViewType(int position) {
        if(list.get(position) instanceof Following){
            return FOLLOWING;
        }else if(list.get(position) ==null){
            return LOADING;
        }
        else return LOADING;
    }
}
