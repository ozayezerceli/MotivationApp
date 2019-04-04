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

public class FavouriteQuotesAdapter extends RecyclerView.Adapter<FavouriteQuotesViewHolder> implements View.OnClickListener {
    private ArrayList<MotivationalQuote> favQuotes;
    private LayoutInflater layoutInflater;
    private AppCompatActivity appCompatActivity;
    private MyListener myListener;

    public FavouriteQuotesAdapter(AppCompatActivity appCompatActivity, MyListener myListener) {
        this.appCompatActivity = appCompatActivity;
        this.myListener = myListener;
        layoutInflater = appCompatActivity.getLayoutInflater();
        favQuotes = new ArrayList<>();
    }

    public ArrayList<MotivationalQuote> getFavQuotes() {
        return favQuotes;
    }

    @NonNull
    @Override
    public FavouriteQuotesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View rootView = layoutInflater.inflate(R.layout.recyclerview_favourite_pictures_list,viewGroup,false);
        rootView.setOnClickListener(this);
        return new FavouriteQuotesViewHolder(rootView);

    }

    @Override
    public void onBindViewHolder(@NonNull FavouriteQuotesViewHolder favouriteQuotesViewHolder, int i) {
        favouriteQuotesViewHolder.getQuotes(appCompatActivity,favQuotes.get(i));
    }

    @Override
    public int getItemCount() {
        return favQuotes.size();
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
