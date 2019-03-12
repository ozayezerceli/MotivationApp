package com.example.motivationapp.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.azoft.carousellayoutmanager.CarouselLayoutManager;
import com.azoft.carousellayoutmanager.CarouselZoomPostLayoutListener;
import com.azoft.carousellayoutmanager.CenterScrollListener;
import com.example.motivationapp.MotivationalQuote;
import com.example.motivationapp.R;
import com.example.motivationapp.recyclerview.MotivationalQuotesAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class AllMotivationalSentences extends Fragment implements MotivationalQuotesAdapter.MyListener {

    private MotivationalQuotesAdapter motivationalQuotesAdapter;
    private RecyclerView recyclerView;
    private ArrayList<MotivationalQuote> motivationalQuotes;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference myRef;





    public static AllMotivationalSentences newInstance(){
        return new AllMotivationalSentences();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_all_motivational_sentences,container,false);
        motivationalQuotesAdapter = new MotivationalQuotesAdapter((AppCompatActivity) getActivity(),this);

        firebaseDatabase = FirebaseDatabase.getInstance();
        myRef = firebaseDatabase.getReference();
        motivationalQuotes = motivationalQuotesAdapter.getMotivationalQuotes();

        final CarouselLayoutManager layoutManager = new CarouselLayoutManager(CarouselLayoutManager.VERTICAL);
        layoutManager.setPostLayoutListener(new CarouselZoomPostLayoutListener());
        recyclerView = rootView.findViewById(R.id.fragment_all_motivational_sentences_recyclerView);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.addOnScrollListener(new CenterScrollListener());

        //recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));


        if(isAdded()){
            recyclerView.setAdapter(motivationalQuotesAdapter);
        }
        getMotivationalQuotes(motivationalQuotes);


        return rootView;
    }

    public ArrayList<MotivationalQuote> getMotivationalQuotes(final ArrayList<MotivationalQuote> motivationalQuotes){
        DatabaseReference newReference = firebaseDatabase.getReference("motivationalQuotes");
        newReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    HashMap<String,String> hashMap =(HashMap<String, String>) ds.getValue();

                    String getFavouriteFromDB = hashMap.get("isFavourite");
                    boolean isFavourite = Boolean.valueOf(getFavouriteFromDB);
                    if(!hashMap.isEmpty()){
                        motivationalQuotes.add(new MotivationalQuote(hashMap.get("quoteId"),
                                hashMap.get("quoteImage"),
                                hashMap.get("quoteDescription"),
                                isFavourite));
                        motivationalQuotesAdapter.notifyDataSetChanged();
                    }


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return motivationalQuotes;

    }
    @Override
    public void MyListener(MotivationalQuote motivationalQuote) {

    }



    }

