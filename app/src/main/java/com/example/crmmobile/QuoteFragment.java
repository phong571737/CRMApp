package com.example.crmmobile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class QuoteFragment extends Fragment {
    RecyclerView recyclerView;
    AdapterQuote adapterQuote;
    List<Quote> listquote;

    FloatingActionButton btnaddQuote;

    public QuoteFragment() {

    }

    public static QuoteFragment newInstance(String param1, String param2) {
        QuoteFragment fragment = new QuoteFragment();
        Bundle args = new Bundle();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_quote, container, false);

        recyclerView = view.findViewById(R.id.QuoteRecycler);
        btnaddQuote = view.findViewById(R.id.btn_add_quote);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        listquote = new ArrayList<>();
        listquote.add(new Quote("ABC1234", "Công ty X", "19/10/2025"));
        listquote.add(new Quote("ABC1234", "Công ty Y", "19/10/2025"));
        listquote.add(new Quote("ABC1234", "Công ty Z", "19/10/2025"));
        listquote.add(new Quote("ABC1234", "Công ty Z", "19/10/2025"));
        listquote.add(new Quote("ABC1234", "Công ty Z", "19/10/2025"));

        adapterQuote = new AdapterQuote(listquote,(item, position) ->{
            BottomSheetActionQuote.ShowBottomSheetQuote(getContext(), item, position);
        }, quote->{
            Intent intent = new Intent(getContext(), QuoteDetailActivity.class);
            startActivity(intent);
        });
        recyclerView.setAdapter(adapterQuote);

        //tạo mới báo giá
        btnaddQuote.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), TaoBaoGiaActivity.class);
            startActivity(intent);
        });

        ImageView iv_back = view.findViewById(R.id.iv_back);
        iv_back.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager().popBackStack();
        });

        return view;
    }
}