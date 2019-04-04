package com.example.motivationapp.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.motivationapp.MotivationalQuote;
import com.example.motivationapp.R;
import com.example.motivationapp.recyclerview.FavouriteQuotesAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class Favourite extends Fragment implements FavouriteQuotesAdapter.MyListener {
    private RecyclerView recyclerView;
    private ArrayList<MotivationalQuote> favQuotes;
    private FavouriteQuotesAdapter favouriteQuotesAdapter;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference myRef;

    public static Favourite newInstance(){
        return new Favourite();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_favourite,container,false);
        favouriteQuotesAdapter = new FavouriteQuotesAdapter((AppCompatActivity) getActivity(),this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        myRef = firebaseDatabase.getReference();
        favQuotes = favouriteQuotesAdapter.getFavQuotes();

        recyclerView = rootView.findViewById(R.id.fragment_favourite_recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),1));

        if(isAdded()){
            recyclerView.setAdapter(favouriteQuotesAdapter);
        }
        getFavouriteList(favQuotes);
        return rootView;
    }

    public ArrayList<MotivationalQuote> getFavouriteList(final ArrayList<MotivationalQuote> favQuotes){
        DatabaseReference newReference = firebaseDatabase.getReference("favQuotes");
        newReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    HashMap<String,String> hashMap = (HashMap<String, String>) ds.getValue();
                    String getFavouriteFromDB = hashMap.get("isFavourite");
                    boolean isFavourite = Boolean.valueOf(getFavouriteFromDB);
                    if(!hashMap.isEmpty()){
                        favQuotes.add(new MotivationalQuote(hashMap.get("quoteId"),
                                hashMap.get("quoteImage"),
                                hashMap.get("quoteDescription"),
                                isFavourite));
                        favouriteQuotesAdapter.notifyDataSetChanged();
                    }


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return favQuotes;
    }

    public ArrayList<MotivationalQuote> getFavQuotes() {
        return favQuotes;
    }

    @Override
    public void MyListener(MotivationalQuote motivationalQuote) {

    }
}
