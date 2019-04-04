package com.example.motivationapp.recyclerview;

import android.content.Context;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.motivationapp.MotivationalQuote;
import com.example.motivationapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;


public class MotivationalQuotesViewHolder extends RecyclerView.ViewHolder {

    ImageView imageView;
    ProgressBar progressBar;
    private int counter = 0;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference myRef;


    public MotivationalQuotesViewHolder(@NonNull View itemView) {
        super(itemView);

        imageView = itemView.findViewById(R.id.recyclerview_all_quotes_pictures_list_imageview);
        progressBar = itemView.findViewById(R.id.recyclerview_all_quotes_pictures_list_progressbar);
        firebaseDatabase = FirebaseDatabase.getInstance();
        myRef = firebaseDatabase.getReference();
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
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter++;
                Handler handler = new Handler();
                Runnable run = new Runnable() {
                    @Override
                    public void run() {
                        counter =0 ;
                    }
                };
                if(counter == 1){
                    Toast.makeText(context, "Single Click", Toast.LENGTH_SHORT).show();
                    handler.postDelayed(run,400);
                }else if(counter == 2){
                    Toast.makeText(context, "Second click", Toast.LENGTH_SHORT).show();
                    saveAsFavourite(motivationalQuote,context);
                    counter = 0;
                }
            }
        });


    }

    public void saveAsFavourite(final MotivationalQuote motivationalQuote,final Context context){
        DatabaseReference newReference = firebaseDatabase.getReference("favQuotes");
        newReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try {
                        if(dataSnapshot.child("favQuotes").child("quoteId").equals(motivationalQuote.getQuoteId())){
                        }else{
                            myRef.child("favQuotes").child("quote"+motivationalQuote.getQuoteId()).child("isFavourite").setValue("true");
                            myRef.child("favQuotes").child("quote"+motivationalQuote.getQuoteId()).child("quoteDescription").setValue(motivationalQuote.getQuoteDescription());
                            myRef.child("favQuotes").child("quote"+motivationalQuote.getQuoteId()).child("quoteId").setValue(motivationalQuote.getQuoteId());
                            myRef.child("favQuotes").child("quote"+motivationalQuote.getQuoteId()).child("quoteImage").setValue(motivationalQuote.getQuoteImage());
                        }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


}
