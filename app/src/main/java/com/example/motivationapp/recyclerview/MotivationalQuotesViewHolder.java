package com.example.motivationapp.recyclerview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.motivationapp.MotivationalQuote;
import com.example.motivationapp.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class MotivationalQuotesViewHolder extends RecyclerView.ViewHolder {

    ImageView imageView;
    ProgressBar progressBar;


    public MotivationalQuotesViewHolder(@NonNull View itemView) {
        super(itemView);

        imageView = itemView.findViewById(R.id.recyclerview_all_quotes_pictures_list_imageview);
        progressBar = itemView.findViewById(R.id.recyclerview_all_quotes_pictures_list_progressbar);
    }

    public void getQuotes(Context context, MotivationalQuote motivationalQuote){
        itemView.setTag(motivationalQuote);
        Picasso.get().load(motivationalQuote.getQuoteImage()).fit().centerCrop().into(imageView, new Callback() {
            @Override
            public void onSuccess() {
                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onError(Exception e) {

            }
        });
    }
}
