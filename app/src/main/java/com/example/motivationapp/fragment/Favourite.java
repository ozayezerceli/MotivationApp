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

import java.util.ArrayList;

public class Favourite extends Fragment implements FavouriteQuotesAdapter.MyListener {
    private RecyclerView recyclerView;
    public static ArrayList<MotivationalQuote> favQuotes;
    private FavouriteQuotesAdapter favouriteQuotesAdapter;

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

        if(isAdded()){
            recyclerView.setAdapter(favouriteQuotesAdapter);
        }
        getFavQuotes();
        favouriteQuotesAdapter.notifyDataSetChanged();
        return rootView;
    }


    public ArrayList<MotivationalQuote> getFavouriteList(ArrayList<MotivationalQuote> favQuotes){



        /*   DatabaseReference newReference = firebaseDatabase.getReference("favQuotes");
        newReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    HashMap<String,String> hashMap = (HashMap<String, String>) ds.getValue();
                    String getFavouriteFromDB = hashMap.get("isFavourite");
                    boolean isFavourite = Boolean.valueOf(getFavouriteFromDB);
                    if(!hashMap.isEmpty() || ds.child(getFavouriteFromDB).equals("true")){
                        favQuotes.add(new MotivationalQuote(hashMap.get("quoteId"),
                                hashMap.get("quoteImage"),
                                hashMap.get("quoteDescription"),
                                isFavourite));
                        favouriteQuotesAdapter.notifyDataSetChanged();
                    }else{

                    }



                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        }); */
        return favQuotes;
    }

    public ArrayList<MotivationalQuote> getFavQuotes() {
        return favQuotes;
    }

    @Override
    public void MyListener(MotivationalQuote motivationalQuote) {

    }
}
