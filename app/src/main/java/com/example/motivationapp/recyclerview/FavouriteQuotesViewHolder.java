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

public class FavouriteQuotesViewHolder extends RecyclerView.ViewHolder {
    ImageView imageView;
    ProgressBar progressBar;

    public FavouriteQuotesViewHolder(@NonNull View itemView) {
        super(itemView);

        imageView = itemView.findViewById(R.id.recyclerView_favourite_pictures_list_imageView);
        progressBar = itemView.findViewById(R.id.recyclerView_favourite_pictures_list_progressbar);
    }

    public void getQuotes(final Context context, final MotivationalQuote motivationalQuote){
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
