package com.example.motivationapp.recyclerview;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.motivationapp.MotivationalQuote;
import com.example.motivationapp.R;

import java.util.ArrayList;

public class MotivationalQuotesAdapter extends RecyclerView.Adapter<MotivationalQuotesViewHolder> implements View.OnClickListener {
   private ArrayList<MotivationalQuote> motivationalQuotes;
   private LayoutInflater layoutInflater;
   private AppCompatActivity appCompatActivity;
   private MyListener myListener;

    public MotivationalQuotesAdapter(AppCompatActivity appCompatActivity, MyListener myListener) {
        this.appCompatActivity = appCompatActivity;
        this.myListener = myListener;
        layoutInflater = appCompatActivity.getLayoutInflater();
        motivationalQuotes = new ArrayList<>();
    }

    public ArrayList<MotivationalQuote> getMotivationalQuotes() {
        return motivationalQuotes;
    }

    @NonNull
    @Override
    public MotivationalQuotesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View rootView = layoutInflater.inflate(R.layout.recyclerview_all_motivational_sentences_pictures_list,viewGroup,false);
        rootView.setOnClickListener(this);
        return new MotivationalQuotesViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull MotivationalQuotesViewHolder motivationalQuotesViewHolder, int i) {
        motivationalQuotesViewHolder.getQuotes(appCompatActivity,motivationalQuotes.get(i));
    }

    @Override
    public int getItemCount() {
        return motivationalQuotes.size();
    }

    @Override
    public void onClick(View v) {
        if(v.getTag() instanceof MotivationalQuote){
            MotivationalQuote motivationalQuote = (MotivationalQuote) v.getTag();
            myListener.MyListener(motivationalQuote);
        }

    }

    public interface MyListener {
        void MyListener(MotivationalQuote motivationalQuote);
    }
}
