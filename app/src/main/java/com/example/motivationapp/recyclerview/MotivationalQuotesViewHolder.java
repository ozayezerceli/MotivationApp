package com.example.motivationapp.recyclerview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.motivationapp.MotivationalQuote;
import com.example.motivationapp.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.like.LikeButton;
import com.like.OnLikeListener;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;


public class MotivationalQuotesViewHolder extends RecyclerView.ViewHolder {

    ImageView imageView;
    ProgressBar progressBar;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference myRef;
    private LikeButton likeButton;


    public MotivationalQuotesViewHolder(@NonNull View itemView) {
        super(itemView);

        imageView = itemView.findViewById(R.id.recyclerview_all_quotes_pictures_list_imageview);
        progressBar = itemView.findViewById(R.id.recyclerview_all_quotes_pictures_list_progressbar);
        likeButton = itemView.findViewById(R.id.all_motivational_sentences_fav_button);
        firebaseDatabase = FirebaseDatabase.getInstance();
        myRef = firebaseDatabase.getReference();
    }

    public void getQuotes(final Context context, final MotivationalQuote motivationalQuote){
        itemView.setTag(motivationalQuote);
        Picasso.get().load(motivationalQuote.getQuoteImage()).fit().centerCrop().into(imageView, new Callback() {
            @Override
            public void onSuccess() {
                progressBar.setVisibility(View.INVISIBLE);
                likeButton.setVisibility(View.VISIBLE);
                if(motivationalQuote.isFavourite() && !likeButton.isLiked()){
                    likeButton.setLiked(true);
                    likeButton.setOnLikeListener(new OnLikeListener() {
                        @Override
                        public void liked(LikeButton likeButton) {
                            Toast.makeText(context, "Already Favourite!", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void unLiked(LikeButton likeButton) {
                            likeButton.setLiked(false);
                            deleteFromFavourite(motivationalQuote,context);


                        }
                    });

                }else{
                    likeButton.setLiked(false);
                    likeButton.setOnLikeListener(new OnLikeListener() {
                        @Override
                        public void liked(LikeButton likeButton) {
                            likeButton.setLiked(true);
                            saveAsFavourite(motivationalQuote,context);


                        }

                        @Override
                        public void unLiked(LikeButton likeButton) {
                            Toast.makeText(context, "Not in Favourite!", Toast.LENGTH_SHORT).show();

                        }
                    });
                }
            }

            @Override
            public void onError(Exception e) {

            }
        });
       }

    public void deleteFromFavourite(final MotivationalQuote motivationalQuote,final Context context){
        String quoteId = "quote"+motivationalQuote.getQuoteId();
        DatabaseReference newReference = firebaseDatabase.getInstance().getReference("favQuotes").child(quoteId);
        newReference.removeValue();
        DatabaseReference updateData = firebaseDatabase.getInstance().getReference("motivationalQuotes").child(quoteId).child("isFavourite");
        updateData.setValue("false");
        motivationalQuote.setFavourite(false);
        Toast.makeText(context, "Deleted from Favourites", Toast.LENGTH_SHORT).show();


    }

    public void saveAsFavourite(final MotivationalQuote motivationalQuote,final Context context){
        String quoteId = "quote"+motivationalQuote.getQuoteId();
        DatabaseReference newReference = firebaseDatabase.getInstance().getReference("favQuotes");
        if(newReference.child(quoteId).equals(motivationalQuote.getQuoteId())){
            Toast.makeText(context, "Already in Favourite", Toast.LENGTH_SHORT).show();
        }else{
            DatabaseReference updateData = firebaseDatabase.getInstance().getReference("motivationalQuotes");
            updateData.child(quoteId).child("isFavourite").setValue("true");
            motivationalQuote.setFavourite(true);
            myRef.child("favQuotes").child(quoteId).child("isFavourite").setValue("true");
            myRef.child("favQuotes").child(quoteId).child("quoteDescription").setValue(motivationalQuote.getQuoteDescription());
            myRef.child("favQuotes").child(quoteId).child("quoteId").setValue(motivationalQuote.getQuoteId());
            myRef.child("favQuotes").child(quoteId).child("quoteImage").setValue(motivationalQuote.getQuoteImage());
            Toast.makeText(context, "Added to Favourites", Toast.LENGTH_SHORT).show();
        }
        }

    }
