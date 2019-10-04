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
import android.widget.Toast;

import com.azoft.carousellayoutmanager.CarouselLayoutManager;
import com.azoft.carousellayoutmanager.CarouselZoomPostLayoutListener;
import com.azoft.carousellayoutmanager.CenterScrollListener;
import com.example.motivationapp.MotivationalQuote;
import com.example.motivationapp.R;
import com.example.motivationapp.recyclerview.MotivationalQuotesAdapter;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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
    private FloatingActionMenu fab;
    private FloatingActionButton refresh,qDownload, goUp;
    private FirebaseAuth mAuth;
    private FirebaseUser user;


    public static AllMotivationalSentences newInstance() {
        return new AllMotivationalSentences();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_all_motivational_sentences, container, false);
        motivationalQuotesAdapter = new MotivationalQuotesAdapter((AppCompatActivity) getActivity(), this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        myRef = firebaseDatabase.getReference();
        mAuth = FirebaseAuth.getInstance();
        motivationalQuotes = motivationalQuotesAdapter.getMotivationalQuotes();
        final CarouselLayoutManager layoutManager = new CarouselLayoutManager(CarouselLayoutManager.VERTICAL);
        layoutManager.setPostLayoutListener(new CarouselZoomPostLayoutListener());
        recyclerView = rootView.findViewById(R.id.fragment_all_motivational_sentences_recyclerView);
        fab = rootView.findViewById(R.id.fab);
        refresh = rootView.findViewById(R.id.fab_refresh);
        qDownload = rootView.findViewById(R.id.fab_download);
        goUp = rootView.findViewById(R.id.fab_goUp);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.addOnScrollListener(new CenterScrollListener());
        if (isAdded()) {
            recyclerView.setAdapter(motivationalQuotesAdapter);
        }

        goUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerView.getLayoutManager().smoothScrollToPosition(recyclerView, null, 0);
            }
        });
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refreshPage();
            }
        });
        qDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Söz Galeriye Kaydedildi.", Toast.LENGTH_SHORT).show();
            }
        });
        motivationalQuotes.clear();
        getMotivationalQuotes(motivationalQuotes);
        motivationalQuotesAdapter.notifyDataSetChanged();
        return rootView;
    }


    public ArrayList<MotivationalQuote> getQuotes() {
        return motivationalQuotes;
    }

    public ArrayList<MotivationalQuote> getMotivationalQuotes(final ArrayList<MotivationalQuote> motivationalQuotes) {
        final DatabaseReference newReference = firebaseDatabase.getReference("user");
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
                                        motivationalQuotes.add(new MotivationalQuote(qHashMap.get("qId"),
                                                qHashMap.get("imgUrl"),
                                                "",
                                                isFav));
                                        motivationalQuotesAdapter.notifyDataSetChanged();
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
        return motivationalQuotes;
    }


    @Override
    public void MyListener(MotivationalQuote motivationalQuote) {

    }

    public void refreshPage(){
        motivationalQuotes.clear();
        getMotivationalQuotes(motivationalQuotes);
    }
}

