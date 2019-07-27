package com.droid.solver.askapp.Account;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.droid.solver.askapp.Answer.UserAnswerModel;
import com.droid.solver.askapp.GlideApp;
import com.droid.solver.askapp.Home.LoadingViewHolderVertically;
import com.droid.solver.askapp.Main.Constants;
import com.droid.solver.askapp.R;
import com.facebook.ads.NativeAd;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import java.util.ArrayList;
import java.util.List;

public class AccountQuestionAnswerRecyclerAdapter extends RecyclerView.Adapter {

    private Context context;
    private ArrayList<Object> list;
    private LayoutInflater inflater;
    private String answererId;
    private final int LOADING=1;
    private final int ANSWER=2;
    private final int NATIVE_AD=3;

    AccountQuestionAnswerRecyclerAdapter(final Context context, ArrayList<Object> list,String answererId){

        if(context!=null){
            this.context=context;
            this.list=list;
            inflater=LayoutInflater.from(context);
            this.answererId=answererId;
        }
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        RecyclerView.ViewHolder viewHolder;
        View view;
        switch(i){
            case ANSWER:
                view=inflater.inflate(R.layout.account_question_answer_item, viewGroup,false);
                viewHolder=new AccountQuestionAnswerRecyclerViewHolder(view);
                break;
            case LOADING:
                view=inflater.inflate(R.layout.loading,viewGroup,false);
                viewHolder=new LoadingViewHolderVertically(view);
                break;
            case NATIVE_AD:
                view=inflater.inflate(R.layout.fb_native_ad_item,viewGroup,false);
                viewHolder=new FbNativeAdViewHolder(view,context);
                break;
                 default:
                      view=inflater.inflate(R.layout.loading,viewGroup,false);
                      viewHolder=new LoadingViewHolderVertically(view);
                      break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int i) {

        if(holder instanceof AccountQuestionAnswerRecyclerViewHolder && list.get(i) instanceof UserAnswerModel){
            UserAnswerModel model = (UserAnswerModel) list.get(i);
            String askserId=model.getAskerId();
            String askerName=model.getAskerName();
            String askerBio=model.getAskerBio();
            String question=model.getQuestion();
            String answer=model.getAnswer();
            int likeCount=model.getAnswerLikeCount();
            question=question==null?"Question not available":question.trim();

            askerName=askerName==null?"Someone":askerName;
            askerBio=askerBio==null?"":askerBio;
            answer=answer==null?"Answer not available":answer.trim();

            String askerUrl= Constants.PROFILE_PICTURE+"/"+askserId+Constants.SMALL_THUMBNAIL;
            String answererUrl=Constants.PROFILE_PICTURE+"/"+answererId+Constants.SMALL_THUMBNAIL;
            StorageReference reference= FirebaseStorage.getInstance().getReference();
            GlideApp.with(context)
                    .load(reference.child(askerUrl))
                    .error(R.drawable.ic_placeholder)
                    .placeholder(R.drawable.ic_placeholder)
                    .into(((AccountQuestionAnswerRecyclerViewHolder) holder).askerImageView);
            GlideApp.with(context)
                    .load(reference.child(answererUrl))
                    .error(R.drawable.ic_placeholder)
                    .placeholder(R.drawable.ic_placeholder)
                    .into(((AccountQuestionAnswerRecyclerViewHolder) holder).answererImageView);
            ((AccountQuestionAnswerRecyclerViewHolder) holder).askerName.setText(askerName);
            ((AccountQuestionAnswerRecyclerViewHolder) holder).askerBio.setText(askerBio);
            ((AccountQuestionAnswerRecyclerViewHolder) holder).questionTextView.setText(question);
            ((AccountQuestionAnswerRecyclerViewHolder) holder).answerTextView.setText(answer);
            ((AccountQuestionAnswerRecyclerViewHolder) holder).likeButton.setLiked(true);
            ((AccountQuestionAnswerRecyclerViewHolder) holder).likeButton.setEnabled(false);
            ((AccountQuestionAnswerRecyclerViewHolder) holder).likeButton.setClickable(false);
            likeCount=likeCount<0?0:likeCount;
            ((AccountQuestionAnswerRecyclerViewHolder) holder).likeCount.setText(String.valueOf(likeCount));

            handleClickListener(context, (AccountQuestionAnswerRecyclerViewHolder) holder, model);
        }else if(holder instanceof FbNativeAdViewHolder){
            NativeAd nativeAd=(NativeAd) list.get(i);
            nativeAd.unregisterView();
            ((FbNativeAdViewHolder) holder).nativeAdTitle.setText(nativeAd.getAdvertiserName());
            ((FbNativeAdViewHolder) holder).nativeAdBody.setText(nativeAd.getAdBodyText());
            ((FbNativeAdViewHolder) holder).nativeAdSocialContext.setText(nativeAd.getAdSocialContext());
            ((FbNativeAdViewHolder) holder).nativeAdCallToAction.setVisibility(nativeAd.hasCallToAction() ? View.VISIBLE : View.INVISIBLE);
            ((FbNativeAdViewHolder) holder).nativeAdCallToAction.setText(nativeAd.getAdCallToAction());
            ((FbNativeAdViewHolder) holder).sponsoredLabel.setText(nativeAd.getSponsoredTranslation());

            List<View> clickableViews = new ArrayList<>();
            clickableViews.add(((FbNativeAdViewHolder) holder).nativeAdTitle);
            clickableViews.add(((FbNativeAdViewHolder) holder).nativeAdCallToAction);

            nativeAd.registerViewForInteraction(((FbNativeAdViewHolder) holder).adView, ((FbNativeAdViewHolder) holder).nativeAdMedia,
                    ((FbNativeAdViewHolder) holder).nativeAdIcon, clickableViews);
        }
    }

    @Override
    public int getItemCount() {
        return list==null?0:list.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(list.get(position) instanceof UserAnswerModel){
            return ANSWER;
        }else if(list.get(position) instanceof NativeAd){
            return NATIVE_AD;
        } else if(list.get(position) ==null){
            return LOADING;
        }
        return LOADING;
    }
    private void handleClickListener(final Context context, final AccountQuestionAnswerRecyclerViewHolder holder,final UserAnswerModel model){
        holder.questionTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.onQuestionClicked(context,model);
            }
        });
        holder.answerTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.onAnswerClicked(context,model);
            }
        });
        holder.askerImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.onAskerImageClicked(context,model);
            }
        });
        holder.answererImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.onAnswererImageClicked(context,model);
            }
        });
        holder.askerName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.onAskerNameClicked(context,model);
            }
        });
//        holder.likeButton.setOnLikeListener(new OnLikeListener() {
//
//            @Override
//            public void liked(LikeButton likeButton) {
//                holder.onLikedClicked(context, model);
//            }
//
//            @Override
//            public void unLiked(LikeButton likeButton) {
//                holder.onDislikedClicked(context, model);
//            }
//        });

    }

}
