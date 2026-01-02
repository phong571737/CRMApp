package com.example.crmmobile.OrderDirectory;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crmmobile.R;
import com.example.crmmobile.OrderDirectory.TaiLieu;
import com.example.crmmobile.OrderDirectory.TaiLieuAdapter;

import java.util.ArrayList;
import java.util.List;

public class TongQuanFragment extends Fragment {

    private RecyclerView recyclerTaiLieu;
    private LinearLayout btnChonTaiLieu, btnThemTaiLieu;
    private TaiLieuAdapter adapter;
    private List<TaiLieu> danhSachTaiLieu;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_tong_quan, container, false);
        initViews(view);

        recyclerTaiLieu.setLayoutManager(new LinearLayoutManager(getContext()));

        // Dữ liệu mẫu
        danhSachTaiLieu = new ArrayList<>();
        danhSachTaiLieu.add(new TaiLieu("Báo giá sản phẩm.pdf", "17/05/2024", "10:24", "Nguyễn Minh Hà"));
        danhSachTaiLieu.add(new TaiLieu("Bảng giá sản phẩm.xlsx", "13/05/2024", "12:40", "Nguyễn Thị Tú Vân"));

        // Gán Adapter
        adapter = new TaiLieuAdapter(getContext(), danhSachTaiLieu);
        recyclerTaiLieu.setAdapter(adapter);

        btnChonTaiLieu.setOnClickListener(v ->
                Toast.makeText(requireContext(), "Chọn tài liệu", Toast.LENGTH_SHORT).show()
        );

        btnThemTaiLieu.setOnClickListener(v ->
                Toast.makeText(requireContext(), "Thêm tài liệu", Toast.LENGTH_SHORT).show()
        );

        return view;
    }

    private void initViews(View view) {
        recyclerTaiLieu = view.findViewById(R.id.recyclerTaiLieu);
        btnChonTaiLieu = view.findViewById(R.id.btnChonTaiLieu);
        btnThemTaiLieu = view.findViewById(R.id.btnThemTaiLieu);
    }
}

