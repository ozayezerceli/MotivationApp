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
import com.example.motivationapp.recyclerview.MotivationalQuotesAdapter;

import java.util.ArrayList;

public class AllMotivationalSentences extends Fragment implements MotivationalQuotesAdapter.MyListener {

    private MotivationalQuotesAdapter motivationalQuotesAdapter;
    private RecyclerView recyclerView;
    private ArrayList<MotivationalQuote> motivationalQuotes;

    public static AllMotivationalSentences newInstance(){
        return new AllMotivationalSentences();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_all_motivational_sentences,container,false);
        motivationalQuotesAdapter = new MotivationalQuotesAdapter((AppCompatActivity) getActivity(),this);

        motivationalQuotes = motivationalQuotesAdapter.getMotivationalQuotes();

        recyclerView = rootView.findViewById(R.id.fragment_all_motivational_sentences_recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));

        if(isAdded()){
            recyclerView.setAdapter(motivationalQuotesAdapter);
        }

        getMotivationalQuotes(motivationalQuotes);


        return rootView;
    }

    public ArrayList<MotivationalQuote> getMotivationalQuotes(ArrayList<MotivationalQuote> motivationalQuotes){
        for (int i =0 ; i<16; i++){
            motivationalQuotes.add(new MotivationalQuote("1"+i,
                    "https://firebasestorage.googleapis.com/v0/b/motivationapp-6b622.appspot.com/o/MotivationalQuotes%2Fquote1.jpg?alt=media&token=64921386-fa3a-44fd-ae6a-7466d72461f0",
                    "Description",
                    false));
        }
        //database verileri buradan çekilecek
        return motivationalQuotes;

    }
    @Override
    public void MyListener(MotivationalQuote motivationalQuote) {

    }
}
