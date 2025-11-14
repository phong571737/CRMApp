package com.example.crmmobile;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Arrays;
import java.util.List;


public class main_screen extends Fragment {

    private RecyclerView rl_module;
    private Module_Adapter adapter;
    private List<item_module> itemModules;

    private onModuleItemSelectedListener itemModuleSelectedListener;

    public interface onModuleItemSelectedListener{
        void onModuleSelectedListener(String moduleNames);
    }

    @Override
    public void onAttach(@NonNull Context context){
        super.onAttach(context);
        if(context instanceof onModuleItemSelectedListener){
            itemModuleSelectedListener = (onModuleItemSelectedListener) context;
        }
    }

    public main_screen() {
    }
    public static main_screen newInstance(String param1, String param2) {
        main_screen fragment = new main_screen();
        Bundle args = new Bundle();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_main_screen, container, false);

        rl_module = view.findViewById(R.id.rl_module);
        //shrink icon see more
        TextView tvSeemore = view.findViewById(R.id.tv_seemore);
        Drawable seemoreicon = AppCompatResources.getDrawable(getContext(), R.drawable.ic_arrow_down);
        if(seemoreicon != null){
            int size = (int)(16 * getResources().getDisplayMetrics().density); //16dp
            seemoreicon.setBounds(0, 0, size, size);
        }
        tvSeemore.setCompoundDrawablesRelative(null, null, seemoreicon, null);

        itemModules = Arrays.asList(
                    new item_module("Tổ chức", R.drawable.office_building),
                    new item_module("Liên hệ", R.drawable.ic_person),
                    new item_module("Báo giá", R.drawable.quote_request),
                    new item_module("Hóa đơn", R.drawable.bill),
                    new item_module("Hợp đồng", R.drawable.contract),
                    new item_module("Báo cáo", R.drawable.bar_chart),
                    new item_module("Cơ hội", R.drawable.ic_target),
                    new item_module("CSKH", R.drawable.customer_care)
        );

        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 4);
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return 1;
            }
        });
        //assign layoutmanager
        rl_module.setLayoutManager(layoutManager);

        //Init adapter
        adapter = new Module_Adapter(itemModules, position->{
            String selected = itemModules.get(position).getName().trim();
            if(itemModuleSelectedListener != null){
                itemModuleSelectedListener.onModuleSelectedListener(selected);
            }
        });

        //Assign adapter
        rl_module.setAdapter(adapter);

        return view;
    }
}