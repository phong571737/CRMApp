package com.example.crmmobile.MainDirectory;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.crmmobile.Adapter.AdapterModule;
import com.example.crmmobile.Adapter.AdapterRecent;
import com.example.crmmobile.AppConstant;
import com.example.crmmobile.DataBase.LeadRepository;
import com.example.crmmobile.DataBase.RecentRepository;
import com.example.crmmobile.LeadDirectory.DetailLeadActivity;
import com.example.crmmobile.LeadDirectory.Lead;
import com.example.crmmobile.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


public class main_screen extends Fragment {

    private RecyclerView rl_module, recycler_recent;
    private AdapterModule adapter;
    private AdapterRecent adapter_recent;
    private RecentViewModel recentViewModel;
    private List<Module> itemModules;
    private onModuleItemSelectedListener itemModuleSelectedListener;
    private RecentRepository recentRepository;
    private Handler timeHandler;

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
        recentViewModel = new ViewModelProvider(requireActivity()).get(RecentViewModel.class);
        recentRepository = new RecentRepository(requireContext());
        rl_module = view.findViewById(R.id.rl_module);
        recycler_recent = view.findViewById(R.id.recycler_recent);
        timeHandler = new Handler(Looper.getMainLooper());

        itemModules = Arrays.asList(
                    new Module("Công ty", R.drawable.ic_company),
                    new Module("Cá nhân", R.drawable.ic_individual),
                    new Module("Báo giá", R.drawable.ic_quote),
                    new Module("Hóa đơn", R.drawable.ic_bill),
                    new Module("Hợp đồng", R.drawable.ic_contract),
                    new Module("Báo cáo", R.drawable.ic_chart),
                    new Module("Cơ hội", R.drawable.ic_target),
                    new Module("CSKH", R.drawable.customer_care)
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
        adapter = new AdapterModule(itemModules, position->{
            String selected = itemModules.get(position).getName().trim();
            if(itemModuleSelectedListener != null){
                itemModuleSelectedListener.onModuleSelectedListener(selected);
            }
        });

        //Assign adapter
        rl_module.setAdapter(adapter);

        //recent
        GridLayoutManager layout = new GridLayoutManager(getContext(), 2);
        recycler_recent.addItemDecoration(new VerticalSpaceItemDecoration(
                (int) getResources().getDisplayMetrics().density * 30
        ));
        recycler_recent.setLayoutManager(layout);
        adapter_recent = new AdapterRecent(new ArrayList<>(), new AdapterRecent.OnItemClickListener(){

            @Override
            public void onItemClick(Recent recent) {
                if ("LEAD".equals(recent.getObjectType())){
                    OpenLeadDetail(recent);
                }
            }
        });
        recycler_recent.setAdapter(adapter_recent);

        recentViewModel.getRecentList()
                .observe(getViewLifecycleOwner(), recents -> {
                    adapter_recent.updateData(recents);
                });
        return view;
    }

    private void OpenLeadDetail(Recent recent) {
        Integer leadId = recent.getObjectID();
        if (leadId == null) return;
        Intent intent = new Intent(getContext(), DetailLeadActivity.class);
        intent.putExtra(AppConstant.LEAD_MODE, AppConstant.CURRENT_MODE);
        intent.putExtra("id", leadId);
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (recentViewModel != null){
            recentViewModel.loadRecent();
        }

        //tự động cập nhật thời gian
        if (timeHandler != null){
            timeHandler.post(timeRunnable);
        }
    }

    private Runnable timeRunnable = new Runnable() {
        @Override
        public void run() {
            if (adapter_recent != null){
                adapter_recent.notifyDataSetChanged();
            }
            timeHandler.postDelayed(this, 60000);
        }
    };

    @Override
    public void onPause() {
        super.onPause();
        timeHandler.removeCallbacks(timeRunnable);
    }
}