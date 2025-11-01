package com.example.crmmobile;

import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

public class QuoteOverView extends Fragment {
    private RecyclerView rv_document;
    private TaiLieuAdapter adapter;
    private List<TaiLieu> list_document;
    private ImageView iv_document;
    private ConstraintLayout cl_document;

    public QuoteOverView() {
        // Required empty public constructor
    }

    public static QuoteOverView newInstance(String param1, String param2) {
        QuoteOverView fragment = new QuoteOverView();
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
        View view =  inflater.inflate(R.layout.fragment_quote_over_view, container, false);
        rv_document = view.findViewById(R.id.rv_document);
        iv_document = view.findViewById(R.id.iv_document);
        cl_document = view.findViewById(R.id.cl_reference);

        rv_document.setLayoutManager(new LinearLayoutManager(getContext()));
        list_document = new ArrayList<>();

        list_document.add(new TaiLieu("Báo giá sản phẩm.pdf", "17/05/2024", "10:24", "Nguyễn Minh Hà"));
        list_document.add(new TaiLieu("Bảng giá sản phẩm.xlsx", "13/05/2024", "12:40", "Nguyễn Thị Tú Vân"));

        adapter = new TaiLieuAdapter(getContext(), list_document);
        rv_document.setAdapter(adapter);

        iv_document.setOnClickListener(v -> {
            if(cl_document.getVisibility() == View.VISIBLE){
                cl_document.setVisibility(View.GONE);
                iv_document.setImageResource(R.drawable.ic_arrow_down);
            }
            else {
                cl_document.setVisibility(View.VISIBLE);
                iv_document.setImageResource(R.drawable.ic_arrow_up);
            }
        });

        return view;
    }
}