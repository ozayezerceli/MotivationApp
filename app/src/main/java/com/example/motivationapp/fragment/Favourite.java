package com.example.motivationapp.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.motivationapp.R;

import java.util.ArrayList;

public class Favourite extends Fragment {


    public static Favourite newInstance(){
        return new Favourite();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_favourite,container,false);

        return rootView;
    }

    public static ArrayList<String> getFavouriteList(){

        return  null;
    }
}
