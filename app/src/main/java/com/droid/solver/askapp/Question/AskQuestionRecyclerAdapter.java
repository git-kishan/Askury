package com.droid.solver.askapp.Question;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.droid.solver.askapp.GlideApp;
import com.droid.solver.askapp.Home.LoadingViewHolderVertically;
import com.droid.solver.askapp.Main.Constants;
import com.droid.solver.askapp.R;
import com.facebook.ads.NativeBannerAd;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import java.util.ArrayList;
import java.util.List;

public class AskQuestionRecyclerAdapter extends RecyclerView.Adapter {

    private Context context;
    private ArrayList<Object> questionModelArrayList;
    private LayoutInflater inflater;
    private final int LOADING=1;
    private final int QUESTION=2;
    private final int NATIVE_AD=3;
    AskQuestionRecyclerAdapter(Context context, ArrayList<Object> questionModelArrayList){
     this.context=context;
     this.questionModelArrayList=questionModelArrayList;
     if(context!=null) {
         inflater = LayoutInflater.from(context);
     }

    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        View view;
        RecyclerView.ViewHolder viewHolder;
        switch (viewType){
            case QUESTION:
                view=inflater.inflate(R.layout.question_question_item,viewGroup,false );
                viewHolder=new AskQuestionViewHolderWithoutImage(view);
                break;
            case LOADING:
                view=inflater.inflate(R.layout.loading, viewGroup,false);
                viewHolder=new LoadingViewHolderVertically(view);
                break;
            case NATIVE_AD:
                view=inflater.inflate(R.layout.question_fragment_ad, viewGroup,false);
                viewHolder=new QuestionAdViewHolder(view,context);
                break;
                default:
                    view=inflater.inflate(R.layout.loading,viewGroup,false);
                    viewHolder=new LoadingViewHolderVertically(view);
                    break;
        }
        return viewHolder;

    }
    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int i) {

        if(holder instanceof AskQuestionViewHolderWithoutImage && questionModelArrayList.get(i) instanceof RootQuestionModel) {

            final RootQuestionModel model = (RootQuestionModel) questionModelArrayList.get(i);
            boolean isAnonymous = model.isAnonymous();
            String profileNameAsked =model.getAskerName();
            String bio=model.getAskerBio();
            String mquestion =model.getQuestion();
            long mtime =model.getTimeOfAsking();



            if (isAnonymous) {
                profileNameAsked = context.getString(R.string.someoneasked);
                GlideApp.with(context)
                        .load("")
                        .error(R.drawable.ic_placeholder)
                        .placeholder(R.drawable.ic_placeholder)
                        .into(((AskQuestionViewHolderWithoutImage) holder).profilePicture);
                bio="";
            } else {
                profileNameAsked = profileNameAsked == null ? "Someone" : profileNameAsked;
                profileNameAsked = String.format(context.getString(R.string.user_name_asked), profileNameAsked);
                bio=bio==null?"":bio;


                String url = Constants.PROFILE_PICTURE + "/" + model.getAskerId() + Constants.SMALL_THUMBNAIL;
                StorageReference reference = FirebaseStorage.getInstance().getReference().child(url);

                GlideApp.with(context).load(reference)
                        .placeholder(R.drawable.ic_placeholder).
                        into(((AskQuestionViewHolderWithoutImage) holder).profilePicture);
            }
            ((AskQuestionViewHolderWithoutImage) holder).profileName.setText(profileNameAsked);
            mquestion = mquestion == null ? "Question not available" : mquestion.trim();
            ((AskQuestionViewHolderWithoutImage) holder).question.setText(mquestion);
            ((AskQuestionViewHolderWithoutImage) holder).about.setText(bio);
            mtime = mtime == 0 ? System.currentTimeMillis() : mtime;
            long oldTime = mtime;
            long currentTime = System.currentTimeMillis();
            String timeAgo = getTime(oldTime, currentTime);
            if (timeAgo == null)
                ((AskQuestionViewHolderWithoutImage) holder).timeAgo.setText("");
            else
                ((AskQuestionViewHolderWithoutImage) holder).timeAgo.setText(timeAgo);

            if (i == questionModelArrayList.size() - 1) {
                ((AskQuestionViewHolderWithoutImage) holder).view.setVisibility(View.GONE);
            }
            handleClickListener((AskQuestionViewHolderWithoutImage) holder, model);
        }

        else if(holder instanceof QuestionAdViewHolder){

            NativeBannerAd nativeBannerAd= (NativeBannerAd) questionModelArrayList.get(i);
            nativeBannerAd.unregisterView();
            ((QuestionAdViewHolder) holder).nativeAdCallToAction.setText(nativeBannerAd.getAdCallToAction());
            ((QuestionAdViewHolder) holder).nativeAdCallToAction.setVisibility(
                    nativeBannerAd.hasCallToAction() ? View.VISIBLE : View.INVISIBLE);
            ((QuestionAdViewHolder) holder).nativeAdTitle.setText(nativeBannerAd.getAdvertiserName());
            ((QuestionAdViewHolder) holder).nativeAdSocialContext.setText(nativeBannerAd.getAdSocialContext());
            ((QuestionAdViewHolder) holder).sponsoredLabel.setText(nativeBannerAd.getSponsoredTranslation());
            List<View> clickableViews = new ArrayList<>();
            clickableViews.add(((QuestionAdViewHolder) holder).nativeAdTitle);
            clickableViews.add(((QuestionAdViewHolder) holder).nativeAdCallToAction);
            nativeBannerAd.registerViewForInteraction(((QuestionAdViewHolder) holder).adView,
                    ((QuestionAdViewHolder) holder).nativeAdIconView, clickableViews);
        }

    }
    @Override
    public int getItemCount() {
        return questionModelArrayList==null?0:questionModelArrayList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(questionModelArrayList.get(position) instanceof RootQuestionModel){
            return QUESTION;
        }
        else if(questionModelArrayList .get(position) instanceof NativeBannerAd){
            return NATIVE_AD;
        }
        else if(questionModelArrayList.get(position)==null){
            return LOADING;
        }
        return 0;
    }

    private String getTimeDifferenceInWords(long diff){
        long yearInMillis=365*24*60*60*1000L;
        long dayInMillis=24*60*60*1000L;//86400000
        long hourInMillis=60*60*1000L;//3600000
        long minuteInMillis=60*1000L;//60000
        long secondInMillis=1000L;//1000
        if(diff/yearInMillis>0){
            long years=diff/yearInMillis;
            return ""+years+" years ago";
        }
        if(diff/dayInMillis>0){
            long day=diff/dayInMillis;
            return ""+day+" days ago";

        }else if(diff/hourInMillis>0){
            long hour =diff/hourInMillis;
            return ""+hour+" hrs ago";

        }else if(diff/minuteInMillis>0){

            long minutes=diff/minuteInMillis;
            return ""+minutes+" min ago";

        } else if(diff/secondInMillis>0){
            long seconds=diff/secondInMillis;
            return ""+seconds+" sec ago";
        }
        return "Not known";
    }
    private String getTime(long oldDate,long newDate){
        long diff=newDate-oldDate;//777600
        if (diff<0){
            return null;
        }
        return getTimeDifferenceInWords(diff);
    }

    private void handleClickListener(final AskQuestionViewHolderWithoutImage holder,final RootQuestionModel model){
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 holder.onCardClicked(context,model);
            }
        });
        holder.profilePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.onProfilePictureClicked(context, model);
            }
        });
        holder.profileName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.onProfileNameClicked(context,model);
            }
        });
    }


}
