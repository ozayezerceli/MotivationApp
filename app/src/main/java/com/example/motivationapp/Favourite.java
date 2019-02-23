package com.example.motivationapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class Favourite extends Fragment {


    public static Favourite newInstance(){
        return new Favourite();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_favourite,container,false);
        ArrayList<String> listFavourite = getFavouriteList();
        ListView listViewFavourite = rootView.findViewById(R.id.listView_favourite);
        listViewFavourite.setAdapter(new ArrayAdapter(getContext(),android.R.layout.simple_list_item_1,listFavourite));

        listViewFavourite.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intentToPop = new Intent(getContext(),PopActivity.class);
                intentToPop.putExtra("position",position);
                startActivity(intentToPop);
            }
        });
        return rootView;
    }

    public static ArrayList<String> getFavouriteList(){
        ArrayList<String> arrayList = new ArrayList<String>();
        arrayList.add("Only I can change my life.");
        arrayList.add("Good, better, best.");
        arrayList.add("Life is 10% what happens to you and 90% how you react to it.");
        arrayList.add("Change your life today.");
        arrayList.add("Optimism is the faith that leads to achievement.");

        return  arrayList;
    }
}
