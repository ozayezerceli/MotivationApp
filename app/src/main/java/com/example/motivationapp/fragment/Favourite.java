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
import com.example.motivationapp.recyclerview.FavouriteQuotesAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class Favourite extends Fragment implements FavouriteQuotesAdapter.MyListener {
    private RecyclerView recyclerView;
    public static ArrayList<MotivationalQuote> favQuotes;
    private FavouriteQuotesAdapter favouriteQuotesAdapter;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private DatabaseReference myRef;

    public static Favourite newInstance(){
        return new Favourite();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_favourite,container,false);
        favouriteQuotesAdapter = new FavouriteQuotesAdapter((AppCompatActivity) getActivity(),this);
        favQuotes = favouriteQuotesAdapter.getFavQuotes();
        final CarouselLayoutManager layoutManager = new CarouselLayoutManager(CarouselLayoutManager.VERTICAL);
        layoutManager.setPostLayoutListener(new CarouselZoomPostLayoutListener());
        recyclerView = rootView.findViewById(R.id.fragment_favourite_recyclerView);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.addOnScrollListener(new CenterScrollListener());
        firebaseDatabase = FirebaseDatabase.getInstance();
        myRef = firebaseDatabase.getReference();
        mAuth = FirebaseAuth.getInstance();
        getFavouriteList(favQuotes);
        if(isAdded()){
            recyclerView.setAdapter(favouriteQuotesAdapter);
        }
        getFavQuotes();
        favouriteQuotesAdapter.notifyDataSetChanged();
        return rootView;
    }


    public ArrayList<MotivationalQuote> getFavouriteList(final ArrayList<MotivationalQuote> favQuotes){
        DatabaseReference newReference = firebaseDatabase.getReference("user");
        user = mAuth.getCurrentUser();
        newReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    HashMap<String, String> hashMap = (HashMap<String, String>) ds.getValue();
                    String email = hashMap.get("email");
                    String userID = hashMap.get("userId");
                    String userEmail = user.getEmail();
                    if (email.matches(userEmail)) {
                        DatabaseReference quoteRef = firebaseDatabase.getReference("user").child(userID).child("quotes");
                        quoteRef.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for (DataSnapshot qds : dataSnapshot.getChildren()) {
                                    HashMap<String, String> qHashMap = (HashMap<String, String>) qds.getValue();
                                    if (!qHashMap.isEmpty()) {
                                        String getFavouriteFromDB = qHashMap.get("isFav");
                                        boolean isFav = Boolean.valueOf(getFavouriteFromDB);
                                        if(isFav){
                                            favQuotes.add(new MotivationalQuote(qHashMap.get("qId"),
                                                    qHashMap.get("imgUrl"),
                                                    "",
                                                    isFav));
                                        }
                                        favouriteQuotesAdapter.notifyDataSetChanged();
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
        return favQuotes;
    }

    public ArrayList<MotivationalQuote> getFavQuotes() {
        return favQuotes;
    }

    @Override
    public void MyListener(MotivationalQuote motivationalQuote) {

    }
}
