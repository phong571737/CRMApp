package com.example.crmmobile.OpportunityDirectory;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crmmobile.Adapter.AdapterHoatDong;
import com.example.crmmobile.BottomSheet.BottomSheetChiTietHoatDongFragment;
import com.example.crmmobile.DataBase.HoatDongRepository;
import com.example.crmmobile.HoatDongDirectory.HoatDong;
import com.example.crmmobile.OpportunityDirectory.Opportunity;
import com.example.crmmobile.R;

import java.util.ArrayList;

public class OpportunityDetailTabOverviewFragment extends Fragment {

    private int opportunityId;
    private OpportunityDetailViewModel detailVM;

    private TextView tvTitle, tvPrice, tvDate, tvStatus, tvCallCount, tvMessageCount, tvExchange;
    private RecyclerView rvHoatDong;
    private LinearLayout layoutEmptyActivities;
    private LinearLayout layoutContentActivities;
    private HoatDongRepository hoatDongRepository;
    private AdapterHoatDong adapterHoatDong;
    private ArrayList<HoatDong> hoatDongList;

    public static OpportunityDetailTabOverviewFragment newInstance(int opportunityId) {
        OpportunityDetailTabOverviewFragment fragment = new OpportunityDetailTabOverviewFragment();
        Bundle args = new Bundle();
        args.putInt("opportunity_id", opportunityId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            opportunityId = getArguments().getInt("opportunity_id");
        }

        //LẤY ViewModel CHUNG VỚI ACTIVITY
        detailVM = new ViewModelProvider(requireActivity())
                .get(OpportunityDetailViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_opportunity_detail_tab_overview, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize views
        rvHoatDong = view.findViewById(R.id.rvHoatDong);
        layoutEmptyActivities = view.findViewById(R.id.layout_empty_activities);
        hoatDongRepository = new HoatDongRepository(requireContext());
        hoatDongList = new ArrayList<>();
        
        // Setup RecyclerView
        adapterHoatDong = new AdapterHoatDong(requireContext(), hoatDongList);
        rvHoatDong.setLayoutManager(new LinearLayoutManager(requireContext()));
        rvHoatDong.setAdapter(adapterHoatDong);
        
        // Set click listener để hiển thị bottom sheet chi tiết
        adapterHoatDong.setOnItemClickListener(new AdapterHoatDong.OnItemClickListener() {
            @Override
            public void onMoreClick(HoatDong hd) {
                // Có thể xử lý menu more nếu cần
            }

            @Override
            public void onItemClick(HoatDong hd) {
                // Hiển thị bottom sheet chi tiết
                BottomSheetChiTietHoatDongFragment bottomSheet = BottomSheetChiTietHoatDongFragment.newInstance(hd);
                bottomSheet.show(getParentFragmentManager(), "chi_tiet_hoat_dong");
            }
        });

        // 🔹 Hoạt động đã lên lịch
        ImageView ivOpportunity = view.findViewById(R.id.iv_scheduled_activities_toggle);
        layoutContentActivities = view.findViewById(R.id.layout_content_activities);
        setupToggle(ivOpportunity, layoutContentActivities);

        // Load hoạt động
        loadHoatDong();

        // 🔹 Comment
//        ImageView ivOpportunity2 = view.findViewById(R.id.iv_comment_toggle);
//        LinearLayout layoutOpportunity2 = view.findViewById(R.id.layout_comment_content);
//        setupToggle(ivOpportunity2, layoutOpportunity2);

        detailVM.getOpportunity().observe(
                getViewLifecycleOwner(),
                opportunity -> {
                    if (opportunity != null) {
                        bindData(view, opportunity);
                    }
                }
        );

    }
    
    private void loadHoatDong() {
        if (opportunityId <= 0) {
            return;
        }
        
        hoatDongList.clear();
        ArrayList<HoatDong> list = hoatDongRepository.getHoatDongByCoHoi(opportunityId);
        hoatDongList.addAll(list);
        adapterHoatDong.notifyDataSetChanged();
        
        // Hiển thị RecyclerView hoặc empty state
        if (hoatDongList.isEmpty()) {
            layoutEmptyActivities.setVisibility(View.VISIBLE);
            rvHoatDong.setVisibility(View.GONE);
        } else {
            layoutEmptyActivities.setVisibility(View.GONE);
            rvHoatDong.setVisibility(View.VISIBLE);
        }
    }

    private void setupToggle(ImageView toggleIcon, LinearLayout contentLayout) {
        if (toggleIcon == null || contentLayout == null) return;

        // Ban đầu hiển thị
        contentLayout.setVisibility(View.VISIBLE);
        toggleIcon.setImageResource(R.drawable.ic_arrow_up);

        toggleIcon.setOnClickListener(v -> {
            boolean isVisible = contentLayout.getVisibility() == View.VISIBLE;
            contentLayout.setVisibility(isVisible ? View.GONE : View.VISIBLE);
            toggleIcon.setImageResource(isVisible ? R.drawable.ic_arrow_down : R.drawable.ic_arrow_up);
        });
    }



    private void bindData(View view, Opportunity o) {
//        TextView tvTitle = view.findViewById(R.id.tv_title);
//        TextView tvPrice = view.findViewById(R.id.tv_price);
//        TextView tvStatus = view.findViewById(R.id.tv_status);
//
//        tvTitle.setText(o.getName());
//        tvPrice.setText(String.valueOf(o.getAmount()));
//        tvStatus.setText(o.getStatus());
    }


}
