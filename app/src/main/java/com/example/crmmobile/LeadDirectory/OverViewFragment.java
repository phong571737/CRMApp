package com.example.crmmobile.LeadDirectory;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.crmmobile.Adapter.AdapterHoatDong;
import com.example.crmmobile.BottomSheet.BottomHoatDongFragment;
import com.example.crmmobile.DataBase.HoatDongRepository;
import com.example.crmmobile.HoatDongDirectory.HoatDong;
import com.example.crmmobile.HoatDongDirectory.ViewModelHoatDong;
import com.example.crmmobile.R;

import java.util.ArrayList;
import java.util.List;

public class OverViewFragment extends Fragment {
    private LinearLayout ll_add_activity;
    private TextView fillslcuocgoi, fillscuochop;
    private RecyclerView recycler_activity;
    private HoatDongRepository hoatDongRepository;
    private ArrayList<HoatDong> hoatDongList;
    private AdapterHoatDong adapter;
    private ViewModelLead viewModelLead;
    private ViewModelHoatDong viewModelHoatDong;

    public OverViewFragment() {
    }
    public static OverViewFragment newInstance(String param1, String param2) {
        OverViewFragment fragment = new OverViewFragment();
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
        return inflater.inflate(R.layout.fragment_over_view, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);

        hoatDongRepository = new HoatDongRepository(requireContext());
        viewModelLead = new ViewModelProvider(requireActivity()).get(ViewModelLead.class);
        viewModelHoatDong = new ViewModelProvider(requireActivity()).get(ViewModelHoatDong.class);
        viewModelHoatDong.init(requireContext());

        hoatDongList = new ArrayList<>();
        adapter = new AdapterHoatDong(requireContext(), hoatDongList);
        recycler_activity.setLayoutManager(new LinearLayoutManager(requireContext()));
        recycler_activity.setAdapter(adapter);

        observeData();

        //add activity
        ll_add_activity.setOnClickListener(v -> {
            showBottomSheetHoatDong();
        });

    }

    private void observeData() {
        viewModelLead.leadId.observe(getViewLifecycleOwner(), leadId->{
            if (leadId != null) {
                viewModelHoatDong.loadHoatDongByID(leadId);
            }
        });

        viewModelHoatDong.getHoatDongLiveData()
                .observe(getViewLifecycleOwner(), list->{
            hoatDongList.clear();
            if (list != null){
                hoatDongList.addAll(list);
            }
            adapter.notifyDataSetChanged();
        });

        //refresh
        getParentFragmentManager().setFragmentResultListener(
            "REFRESH_HOATDONG",
            getViewLifecycleOwner(),
            (requestKey, bundle) -> {
                boolean refresh = bundle.getBoolean("REFRESH", false);
                if (refresh) {
                    Integer leadId = viewModelLead.leadId.getValue();
                    if (leadId != null){
                        viewModelHoatDong.loadHoatDongByID(leadId);
                    }
                }
        });
    }

    private void initViews(View view) {
        ll_add_activity = view.findViewById(R.id.ll_add_activity);
        fillslcuocgoi = view.findViewById(R.id.fillslcuocgoi);
        fillscuochop= view.findViewById(R.id.fillscuochop);
        recycler_activity = view.findViewById(R.id.recycler_activity);
    }

    private void showBottomSheetHoatDong() {
        BottomHoatDongFragment bottom = new BottomHoatDongFragment();
        bottom.show(getParentFragmentManager(), "hoatdong");
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}