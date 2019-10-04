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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.like.LikeButton;
import com.like.OnLikeListener;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.HashMap;


public class MotivationalQuotesViewHolder extends RecyclerView.ViewHolder {
    ImageView imageView;
    ProgressBar progressBar;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference myRef;
    private LikeButton likeButton;
    private FirebaseAuth mAuth;
    private FirebaseUser user;


    public MotivationalQuotesViewHolder(@NonNull View itemView) {
        super(itemView);

        imageView = itemView.findViewById(R.id.recyclerview_all_quotes_pictures_list_imageview);
        progressBar = itemView.findViewById(R.id.recyclerview_all_quotes_pictures_list_progressbar);
        likeButton = itemView.findViewById(R.id.all_motivational_sentences_fav_button);
        firebaseDatabase = FirebaseDatabase.getInstance();
        myRef = firebaseDatabase.getReference();
        mAuth = FirebaseAuth.getInstance();


    }

    public void getQuotes(final Context context,final MotivationalQuote motivationalQuote){
        itemView.setTag(motivationalQuote);
        Picasso.get().load(motivationalQuote.getQuoteImage()).fit().centerCrop().into(imageView, new Callback() {
            @Override
            public void onSuccess() {
                progressBar.setVisibility(View.INVISIBLE);
                likeButton.setVisibility(View.VISIBLE);
                if(motivationalQuote.isFavourite()){
                    likeButton.setLiked(true);
                }
            }
            @Override
            public void onError(Exception e) {

            }

        });

        likeButton.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
                if(!motivationalQuote.isFavourite()){
                    motivationalQuote.setFavourite(true);
                    likeButton.setLiked(true);
                    changeFavState(motivationalQuote);
                    Toast.makeText(context, "Added to Favourites!", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(context, "It's already Favourite!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void unLiked(LikeButton likeButton) {
                if(motivationalQuote.isFavourite()){
                    motivationalQuote.setFavourite(false);
                    likeButton.setLiked(false);
                    changeFavState(motivationalQuote);
                    Toast.makeText(context, "Deleted from Favourites!", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(context, "It's not already Favourite!", Toast.LENGTH_SHORT).show();
                }

            }
        });
       }

    public void changeFavState(final MotivationalQuote mq){
        final DatabaseReference newReference = myRef.child("user");
        user = mAuth.getCurrentUser();
        newReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    HashMap<String, String> hashMap = (HashMap<String, String>) ds.getValue();
                    String email = hashMap.get("email");
                    final String userID = hashMap.get("userId");
                    String userEmail = user.getEmail();
                    if (email.matches(userEmail)) {
                        DatabaseReference quoteRef = firebaseDatabase.getReference("user").child(userID).child("quotes");
                        quoteRef.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for (DataSnapshot qds : dataSnapshot.getChildren()) {
                                    HashMap<String, String> qHashMap = (HashMap<String, String>) qds.getValue();
                                    String qId = qHashMap.get("qId");
                                    if (!qHashMap.isEmpty() && mq.getQuoteId().matches(qId)) {
                                        DatabaseReference dbRef = firebaseDatabase.getReference("user").child(userID).child("quotes").child(qId);
                                        if(mq.isFavourite()){
                                            dbRef.child("isFav").setValue("true");
                                        }else{
                                            dbRef.child("isFav").setValue("false");
                                        }
                                    }
                                }
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

       /* String quoteId = "quote"+motivationalQuote.getQuoteId();
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
        */
    }
