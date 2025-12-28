package com.example.crmmobile.QuoteDirectory;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.crmmobile.BottomSheet.BottomHoatDongFragment;
import com.example.crmmobile.R;
import com.example.crmmobile.OrderDirectory.TaiLieu;
import com.example.crmmobile.Adapter.TaiLieuAdapter;

import java.util.ArrayList;
import java.util.List;

public class QuoteOverView extends Fragment {
    private ImageView display_action;
    private TextView fillslcuocgoi, fillscuochop;
    private LinearLayout ll_add_activity;
    private RecyclerView recycler_activity;
    private CreateQuoteViewModel viewModel;

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
        initViews(view);


        display_action.setOnClickListener(v -> {
            if(recycler_activity.getVisibility() == View.VISIBLE){
                recycler_activity.setVisibility(View.GONE);
                display_action.setImageResource(R.drawable.ic_arrow_down);
            }
            else {
                recycler_activity.setVisibility(View.VISIBLE);
                display_action.setImageResource(R.drawable.ic_arrow_up);
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //add activity
        ll_add_activity.setOnClickListener(v -> {
            showBottomSheetHoatDong();
        });
    }

    private void showBottomSheetHoatDong() {
        BottomHoatDongFragment bottom = new BottomHoatDongFragment();
        Integer currentID = viewModel.QuoteID.getValue();
        if (currentID != null){
            bottom.setLead(currentID);
        }
        bottom.show(getParentFragmentManager(), "hoatdong");
    }

    private void initViews(View view) {
        display_action = view.findViewById(R.id.display_action);
        recycler_activity = view.findViewById(R.id.recycler_activity);
        ll_add_activity = view.findViewById(R.id.ll_add_activity);
        fillslcuocgoi = view.findViewById(R.id.fillslcuocgoi);
        fillscuochop= view.findViewById(R.id.fillscuochop);
    }
}