package com.example.crmmobile.IndividualDirectory;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crmmobile.Adapter.AdapterCaNhan;
import com.example.crmmobile.Adapter.AdapterHoatDong;
import com.example.crmmobile.IndividualDirectory.CaNhan;
import com.example.crmmobile.DataBase.HoatDongRepository;
import com.example.crmmobile.HoatDongDirectory.HoatDong;
import com.example.crmmobile.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class TongQuanFragment extends Fragment {
    private RecyclerView rvHoatDong;

    private AdapterHoatDong adapter;
    private ArrayList<HoatDong> hoatDongList;
    //private FloatingActionButton btnAdd;
    private HoatDongRepository db;
    private CaNhan caNhan;

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
        // 🔥 DÒNG BẠN BỊ THIẾU
        return inflater.inflate(R.layout.fragment_tong_quan, container, false);
    }

    @Nullable
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Lấy CaNhan từ Bundle
        if (getArguments() != null && getArguments().containsKey("CANHAN_DATA")) {
            caNhan = (CaNhan) getArguments().getSerializable("CANHAN_DATA");
        }

        rvHoatDong = view.findViewById(R.id.rvHoatDong);

        db = new HoatDongRepository(requireContext());

        loadHoatDong();

        adapter = new AdapterHoatDong(requireContext(), hoatDongList);

        rvHoatDong.setLayoutManager(new LinearLayoutManager(requireContext()));
        rvHoatDong.setAdapter(adapter);
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
        // Refresh danh sách khi fragment được resume (sau khi thêm hoạt động thành công)
        if (db != null && caNhan != null && caNhan.getId() > 0) {
            List<HoatDong> listFromDB = db.getHoatDongByNguoiLienHe(caNhan.getId());
            hoatDongList.clear();
            hoatDongList.addAll(listFromDB);
            if (adapter != null) {
                adapter.notifyDataSetChanged();
            }
        }
    }


}