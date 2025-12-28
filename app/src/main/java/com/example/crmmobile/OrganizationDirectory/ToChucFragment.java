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

        // 1. Ánh xạ nút Back
        ImageButton btnBack = view.findViewById(R.id.btn_organization_back);
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> requireActivity().getSupportFragmentManager().popBackStack());
        }

        // 2. Ánh xạ nút Thêm mới (FAB)
        view.findViewById(R.id.fab_add).setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), TaoCongTyActivity.class);
            intent.putExtra("EXTRA_TITLE", "Tạo công ty");
            startActivity(intent);
        });

        // 3. Cấu hình RecyclerView
        recyclerView = view.findViewById(R.id.recycler_view_organization);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // 4. XỬ LÝ TÌM KIẾM
        android.widget.EditText etSearch = view.findViewById(R.id.et_organization_search);
        if (etSearch != null) {
            etSearch.addTextChangedListener(new android.text.TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    // Gọi hàm lọc dữ liệu mỗi khi người dùng nhập chữ
                    filterList(s.toString());
                }

                @Override
                public void afterTextChanged(android.text.Editable s) {}
            });
        }

        loadData(); // Load toàn bộ danh sách lần đầu

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
        Intent intent = new Intent(getContext(), ChiTietToChucActivity.class);
        intent.putExtra("COMPANY_ID", toChuc.getId());
        startActivity(intent);
    }

    // Thêm hàm lọc dữ liệu
    private void filterList(String keyword) {
        toChucList = companyRepository.searchCompany(keyword);
        adapter = new ToChucAdapter(getContext(), toChucList, this, this);
        recyclerView.setAdapter(adapter);
    }

    private void onEditClicked(int position) {
        if(position == -1 || selectedToChuc == null) return;

        Log.d(TAG, "onEditClicked: " + selectedToChuc.getCompanyName() + " ID: " + selectedToChuc.getId());

        Intent intent = new Intent(getContext(), TaoCongTyActivity.class);
        intent.putExtra("EXTRA_TITLE", "Chỉnh sửa công ty");
        intent.putExtra("COMPANY_ID", selectedToChuc.getId());
        startActivity(intent);
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

    @Override
    public void onActionXemTongQuan() {
        if (selectedToChuc != null) {
            Intent intent = new Intent(getContext(), TongQuanCongTyActivity.class);
            intent.putExtra("COMPANY_ID", selectedToChuc.getId());
            startActivity(intent);
        }
    }

    @Override
    public void onActionChinhSua() {
        // Gọi đúng hàm xử lý chỉnh sửa
        onEditClicked(selectedPosition);
    }
}
