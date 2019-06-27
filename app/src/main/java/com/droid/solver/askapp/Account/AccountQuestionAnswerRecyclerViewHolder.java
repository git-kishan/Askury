package com.droid.solver.askapp.Account;

import android.support.text.emoji.widget.EmojiTextView;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.droid.solver.askapp.R;
import de.hdodenhof.circleimageview.CircleImageView;

public class AccountQuestionAnswerRecyclerViewHolder extends RecyclerView.ViewHolder {

    CircleImageView askerImageView,answererImageView;
    EmojiTextView askerName,askerBio,questionTextView,answerTextView;
    CardView rootCardView;
    public AccountQuestionAnswerRecyclerViewHolder(View itemView){
        super(itemView);
        askerImageView=itemView.findViewById(R.id.profile_image);
        answererImageView=itemView.findViewById(R.id.circleImageView);
        askerName=itemView.findViewById(R.id.asker_name);
        askerBio=itemView.findViewById(R.id.about_textview);
        questionTextView=itemView.findViewById(R.id.question_textview);
        answerTextView=itemView.findViewById(R.id.answer_text_view);
        rootCardView=itemView.findViewById(R.id.root_card_view);

    }
}
