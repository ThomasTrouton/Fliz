package com.example.thoma.fliz;

/**
 * Created by thoma on 21/11/2017.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


public class QuestionCardAdapter extends ArrayAdapter<Question> {

    public QuestionCardAdapter(Context context) {

        super(context, 0);

    }


    @Override

    public View getView(int position, View contentView, ViewGroup parent) {

        ViewHolder holder;



        if (contentView == null) {

            LayoutInflater inflater = LayoutInflater.from(getContext());

            contentView = inflater.inflate(R.layout.item_question_card, parent, false);

            holder = new ViewHolder(contentView);

            contentView.setTag(holder);

        } else {

            holder = (ViewHolder) contentView.getTag();

        }



        Question spot = getItem(position);



        holder.title.setText(spot.title);

        holder.content.setText(spot.content);



        return contentView;

    }



    private static class ViewHolder {

        public TextView title;

        public TextView content;


        public ViewHolder(View view) {

            this.title = (TextView) view.findViewById(R.id.item_question_card_title);

            this.content = (TextView) view.findViewById(R.id.item_question_card_content);

        }

    }


    public boolean getIsTrue(int position) {
        Question spot = getItem(position);
        return spot.isTrue;

    }


}
