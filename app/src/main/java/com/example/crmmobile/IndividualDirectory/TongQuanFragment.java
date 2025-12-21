package com.example.crmmobile.IndividualDirectory;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crmmobile.Adapter.AdapterCaNhan;
import com.example.crmmobile.Adapter.AdapterHoatDong;
import com.example.crmmobile.BottomSheet.BottomActionFragment;
import com.example.crmmobile.BottomSheet.BottomHoatDongFragment;
import com.example.crmmobile.IndividualDirectory.CaNhan;
import com.example.crmmobile.DataBase.HoatDongRepository;
import com.example.crmmobile.HoatDongDirectory.HoatDong;
import com.example.crmmobile.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class TongQuanFragment extends Fragment {
    private RecyclerView rvHoatDong;
    private BottomActionFragment.OnActionListener listener;
    private AdapterHoatDong adapter;
    private LinearLayout groupThemHoatDong;
    private TextView btnThemHoatDong;
    private ArrayList<HoatDong> hoatDongList;
    //private FloatingActionButton btnAdd;
    private HoatDongRepository db;
    private CaNhan caNhan;

    private TextView tvCallCount, tvMeetingCount;


    private static final int REQ_ADD = 100;
    private static final int REQ_EDIT = 101;


    public TongQuanFragment() {
        // Constructor rỗng là bắt buộc
    }


    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState
    ) {
        //DÒNG BẠN BỊ THIẾU
        return inflater.inflate(R.layout.fragment_tong_quan, container, false);
    }
    //    public interface OnActionListener {
//        void onAddHoatDong(CaNhan cn);
//    }
    @Nullable
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Lấy CaNhan từ Bundle
        if (getArguments() != null && getArguments().containsKey("CANHAN_DATA")) {
            caNhan = (CaNhan) getArguments().getSerializable("CANHAN_DATA");
        }

        rvHoatDong = view.findViewById(R.id.rvHoatDong);
        groupThemHoatDong = view.findViewById(R.id.group_themhoatdong);
        btnThemHoatDong = view.findViewById(R.id.btnthemhoatdong);
        tvCallCount = view.findViewById(R.id.fillslcuocgoi);
        tvMeetingCount = view.findViewById(R.id.fillslcuochop);

        db = new HoatDongRepository(requireContext());

        loadHoatDong();

        adapter = new AdapterHoatDong(requireContext(), hoatDongList);

        rvHoatDong.setLayoutManager(new LinearLayoutManager(requireContext()));
        rvHoatDong.setAdapter(adapter);

        // Xử lý click nút "Thêm hoạt động"
        if (groupThemHoatDong != null) {
            groupThemHoatDong.setOnClickListener(v -> showBottomSheetHoatDong());
        }
        if (btnThemHoatDong != null) {
            btnThemHoatDong.setOnClickListener(v -> showBottomSheetHoatDong());
        }

        // Lắng nghe signal refresh từ BottomSheet
        getParentFragmentManager().setFragmentResultListener("REFRESH_HOATDONG", this, (requestKey, bundle) -> {
            boolean refresh = bundle.getBoolean("REFRESH", false);
            if (refresh) {
                refreshHoatDongList();
            }
        });
    }

    private void updateActivityStats() {
        if (caNhan == null || caNhan.getId() <= 0) {
            if (tvCallCount != null) tvCallCount.setText("0");
            if (tvMeetingCount != null) tvMeetingCount.setText("0");
            return;
        }

        if (db == null) {
            db = new HoatDongRepository(requireContext());
        }

        int callCount = 0;
        int meetingCount = 0;

        List<HoatDong> listFromDB = db.getHoatDongByNguoiLienHe(caNhan.getId());
        for (HoatDong hd : listFromDB) {
            String type = hd.getType();
            if ("call".equalsIgnoreCase(type)) {
                callCount++;
            } else if ("meeting".equalsIgnoreCase(type)) {
                meetingCount++;
            }
        }

        if (tvCallCount != null) {
            tvCallCount.setText("" + callCount);
        }
        if (tvMeetingCount != null) {
            tvMeetingCount.setText("" + meetingCount);
        }
    }
    private void refreshHoatDongList() {
        if (db != null && caNhan != null && caNhan.getId() > 0) {
            List<HoatDong> listFromDB = db.getHoatDongByNguoiLienHe(caNhan.getId());
            hoatDongList.clear();
            hoatDongList.addAll(listFromDB);
            if (adapter != null) {
                adapter.notifyDataSetChanged();
            }

            // Cập nhật số cuộc gọi/cuộc họp trong fragment
            updateActivityStats();

        }
    }


    private void showBottomSheetHoatDong() {
        if (caNhan == null || caNhan.getId() <= 0) {
            // Nếu không có CaNhan, không thể thêm hoạt động
            return;
        }

        BottomHoatDongFragment bottom = new BottomHoatDongFragment();
        bottom.setCaNhan(caNhan);
        bottom.show(getParentFragmentManager(), "hoatdong");
    }


    private void loadHoatDong() {
        if (caNhan == null || caNhan.getId() <= 0) {
            // Nếu không có CaNhan hoặc ID không hợp lệ, hiển thị danh sách rỗng
            hoatDongList = new ArrayList<>();
            return;
        }

        // Lấy hoạt động theo NGUOILIENHE
        List<HoatDong> listFromDB = db.getHoatDongByNguoiLienHe(caNhan.getId());
        hoatDongList = new ArrayList<>(listFromDB);
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshHoatDongList();
        updateActivityStats();
    }




}