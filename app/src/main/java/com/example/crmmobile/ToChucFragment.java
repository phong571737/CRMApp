package com.example.crmmobile;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
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

    private int selectedPosition = -1;
    private ToChuc selectedToChuc = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.layout_tochuc, container, false);
        Log.d(TAG, "Fragment onCreateView CALLED");

        // === NÚT FAB ===
        com.google.android.material.floatingactionbutton.FloatingActionButton fab_add =
                view.findViewById(R.id.fab_add);
        fab_add.setOnClickListener(v -> {
            Log.d(TAG, "FAB Clicked!");
            Intent intent = new Intent(getContext(), TaoCongTyActivity.class);
            intent.putExtra("EXTRA_TITLE", "Tạo công ty");
            startActivity(intent);
        });

        // === CẬP NHẬT KHỞI TẠO ADAPTER ===
        recyclerView = view.findViewById(R.id.recycler_view_organization);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        loadSampleData();
        // Pass "this" (chính Fragment này) làm listener
        adapter = new ToChucAdapter(getContext(), toChucList, this, this);
        recyclerView.setAdapter(adapter);

        return view;
    }

    // === HÀM TỪ OnItemClickListener ===
    @Override
    public void onItemClicked(int position, ToChuc toChuc) {
        Log.d(TAG, "Item clicked: " + toChuc.getCompanyName());
        // Mở Activity chi tiết
        Intent intent = new Intent(getContext(), ChiTietToChucActivity.class);
        // (Sau này có thể gửi ID công ty qua đây)
        // intent.putExtra("COMPANY_ID", toChuc.getId());
        startActivity(intent);
    }

    // === HÀM TỪ ToChucAdapter.OnMoreOptionsClickListener ===
    @Override
    public void onMoreOptionsClicked(int position, ToChuc toChuc) {
        // Lưu lại công ty vừa được chọn
        this.selectedPosition = position;
        this.selectedToChuc = toChuc;

        // Mở BottomSheet
        ToChucLuaChonHanhDongSheet bottomSheet = new ToChucLuaChonHanhDongSheet();
        // Dùng getChildFragmentManager() khi mở dialog/sheet từ bên trong Fragment
        bottomSheet.show(getChildFragmentManager(), "LuaChonHanhDongSheet");
    }

    // === CÁC HÀM TỪ LuaChonHanhDongSheet.ItemClickListener ===
    @Override
    public void onActionGhim() {
        if(selectedToChuc == null) return;
        Toast.makeText(getContext(), "Đã ghim: " + selectedToChuc.getCompanyName(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onActionXemTongQuan() {
        onOverviewClicked(selectedPosition);
    }

    @Override
    public void onActionThemHoatDong() {
        if(selectedToChuc == null) return;
        Toast.makeText(getContext(), "Thêm hoạt động cho: " + selectedToChuc.getCompanyName(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onActionChinhSua() {
        onEditClicked(selectedPosition);
    }

    @Override
    public void onActionXoa() {
        if(selectedToChuc == null) return;
        Toast.makeText(getContext(), "Xóa: " + selectedToChuc.getCompanyName(), Toast.LENGTH_SHORT).show();
    }


    // === CÁC HÀM XỬ LÝ CLICK ===
    private void onEditClicked(int position) {
        if(position == -1) return; // Kiểm tra an toàn
        Log.d(TAG, "onEditClicked CALLED for position " + position);
        Intent intent = new Intent(getContext(), TaoCongTyActivity.class);
        intent.putExtra("EXTRA_TITLE", "Chỉnh sửa công ty");
        startActivity(intent);
        adapter.notifyItemChanged(position);
    }

    private void onOverviewClicked(int position) {
        if(position == -1) return; // Kiểm tra an toàn
        Log.d(TAG, "onOverviewClicked CALLED for position " + position);
        Intent intent = new Intent(getContext(), TongQuanCongTyActivity.class);
        startActivity(intent);
        adapter.notifyItemChanged(position);
    }

    // === Hàm loadSampleData ===
    private void loadSampleData() {
        toChucList = new ArrayList<>();
        // 1. CloudGO
        toChucList.add(new ToChuc(
                "Công ty TNHH CloudGO", "Công nghệ", "09/07/2025",
                true,
                ToChuc.TrangThai.KHONG_QUAN_TAM, "2", "2", false,
                Arrays.asList(R.drawable.avt4jpg, R.drawable.avatar_man, R.drawable.avatar_girl)
        ));
        // 2. Hasaki
        toChucList.add(new ToChuc(
                "Công ty TNHH Hasaki Beauty & Spa", "Mỹ phẩm", "12/10/2025",
                true,
                ToChuc.TrangThai.CO_CO_HOI, "2", "1", true,
                Arrays.asList(R.drawable.avt4jpg, R.drawable.avatar_man)
        ));
        // 3. Chiaki
        toChucList.add(new ToChuc(
                "Công ty Cổ phần Thương mại Chiaki", "Mua sắm", "18/09/2025",
                true,
                ToChuc.TrangThai.NONE, "2", "1", true,
                Arrays.asList(R.drawable.avt4jpg, R.drawable.avatar_man)
        ));
        // 4. IMAP
        toChucList.add(new ToChuc(
                "Công ty Cổ phần Giáo dục & Đào tạo IMAP VietNam", "Giáo dục", "12/08/2025",
                true,
                ToChuc.TrangThai.CAN_QUAN_TAM, "2", "1", false,
                Arrays.asList(R.drawable.avatar_girl)
        ));
    }
}
