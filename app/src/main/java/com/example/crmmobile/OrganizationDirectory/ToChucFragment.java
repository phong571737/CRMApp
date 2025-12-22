package com.example.crmmobile.OrganizationDirectory;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crmmobile.Adapter.ToChucAdapter;
import com.example.crmmobile.DataBase.CompanyRepository;
import com.example.crmmobile.R;
import com.example.crmmobile.BottomSheet.ToChucLuaChonHanhDongSheet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ToChucFragment extends Fragment
        implements ToChucAdapter.OnMoreOptionsClickListener,
        ToChucLuaChonHanhDongSheet.ItemClickListener, ToChucAdapter.OnItemClickListener {

    private static final String TAG = "SWIPE_DEBUG";

    private RecyclerView recyclerView;
    private ToChucAdapter adapter;
    private List<ToChuc> toChucList;
    private CompanyRepository companyRepository;

    private int selectedPosition = -1;
    private ToChuc selectedToChuc = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_tochuc, container, false);

        companyRepository = new CompanyRepository(getContext());

        ImageButton btnBack = view.findViewById(R.id.btn_organization_back);
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> requireActivity().getSupportFragmentManager().popBackStack());
        }

        view.findViewById(R.id.fab_add).setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), TaoCongTyActivity.class);
            intent.putExtra("EXTRA_TITLE", "Tạo công ty");
            startActivity(intent);
        });

        recyclerView = view.findViewById(R.id.recycler_view_organization);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        loadData(); // Load lần đầu

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadData(); // Refresh khi quay lại
    }

    private void loadData() {
        toChucList = companyRepository.getAllCompany();
        if (toChucList == null) toChucList = new ArrayList<>();

        adapter = new ToChucAdapter(getContext(), toChucList, this, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClicked(int position, ToChuc toChuc) {
        // Click vào item -> Mở màn hình Sửa (hoặc Chi tiết)
        Intent intent = new Intent(getContext(), TaoCongTyActivity.class);
        intent.putExtra("COMPANY_ID", toChuc.getId()); // Truyền ID để biết là Edit
        startActivity(intent);
    }


    private void onEditClicked(int position) {
        if(position == -1 || selectedToChuc == null) return; // Kiểm tra kỹ

        Log.d(TAG, "onEditClicked: " + selectedToChuc.getCompanyName() + " ID: " + selectedToChuc.getId());

        Intent intent = new Intent(getContext(), TaoCongTyActivity.class);
        intent.putExtra("EXTRA_TITLE", "Chỉnh sửa công ty");

        // === QUAN TRỌNG: TRUYỀN ID CÔNG TY ===
        intent.putExtra("COMPANY_ID", selectedToChuc.getId());
        // =====================================

        startActivity(intent);
        // Không cần notifyItemChanged ở đây vì khi quay lại onResume sẽ load lại list
    }
    @Override
    public void onMoreOptionsClicked(int position, ToChuc toChuc) {
        this.selectedPosition = position;
        this.selectedToChuc = toChuc;
        ToChucLuaChonHanhDongSheet bottomSheet = new ToChucLuaChonHanhDongSheet();
        bottomSheet.show(getChildFragmentManager(), "LuaChonHanhDongSheet");
    }

    @Override
    public void onActionXoa() {
        if(selectedToChuc == null) return;
        companyRepository.deleteCompany(selectedToChuc.getId());
        Toast.makeText(getContext(), "Đã xóa", Toast.LENGTH_SHORT).show();
        loadData(); // Refresh list
    }

    // Các hàm khác giữ nguyên hoặc để trống nếu chưa dùng
    @Override public void onActionGhim() {}
    @Override public void onActionXemTongQuan() {}
    @Override public void onActionThemHoatDong() {}
    @Override public void onActionChinhSua() {
        onItemClicked(selectedPosition, selectedToChuc);
    }
}
