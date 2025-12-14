package com.example.crmmobile.IndividualDirectory;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crmmobile.BottomSheet.BottomActionFragment;
import com.example.crmmobile.BottomSheet.BottomHoatDongFragment;
import com.example.crmmobile.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.example.crmmobile.Adapter.AdapterCaNhan;
import com.example.crmmobile.DataBase.CaNhanRepository;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class DanhSachCaNhanFragment extends Fragment {

    private ImageView icBack;
    private RecyclerView rvCaNhan;
    private AdapterCaNhan adapter;
    private ArrayList<CaNhan> caNhanList;
    private FloatingActionButton btnAdd;
    private CaNhanRepository db;

    // ActivityResultLauncher cho ADD request
    private ActivityResultLauncher<Intent> addCaNhanLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == android.app.Activity.RESULT_OK && result.getData() != null) {
                    handleAddResult(result.getData());
                }
            }
    );

    // ActivityResultLauncher cho EDIT request
    private ActivityResultLauncher<Intent> editCaNhanLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == android.app.Activity.RESULT_OK && result.getData() != null) {
                    handleEditResult(result.getData());
                }
            }
    );

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_danhsachcanhan, container, false);

        rvCaNhan = view.findViewById(R.id.rvCaNhan);
        btnAdd = view.findViewById(R.id.btn_add_contact);
        icBack = view.findViewById(R.id.ic_back);

        // --- Khởi tạo DB ---
        db = new CaNhanRepository(requireContext());

        // --- Load dữ liệu từ database ---
        loadCaNhan();

        // --- Adapter + RecyclerView ---
        adapter = new AdapterCaNhan(requireContext(), caNhanList);
        rvCaNhan.setLayoutManager(new LinearLayoutManager(requireContext()));
        rvCaNhan.setAdapter(adapter);

        // --- Nút thêm mới ---
        btnAdd.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), ThongTinLienHeActivity.class);
            addCaNhanLauncher.launch(intent);
        });

        // --- Nút back ---
        icBack.setOnClickListener(v -> {
            if (getActivity() != null) {
                getActivity().onBackPressed();
            }
        });

        // --- Xử lý sự kiện click item ---
        adapter.setOnItemClickListener(new AdapterCaNhan.OnItemClickListener() {
            @Override
            public void onMoreClick(CaNhan cn) {
                // Tạo BottomActionFragment và truyền item + callback
                BottomActionFragment bottomSheet = new BottomActionFragment();
                bottomSheet.setCaNhan(cn, new BottomActionFragment.OnActionListener() {
                    @Override
                    public void onDelete(CaNhan deletedCn) {
                        // Xóa khỏi database
                        db.delete(deletedCn.getId());

                        // Xóa khỏi danh sách local và cập nhật RecyclerView
                        int index = -1;
                        for (int i = 0; i < caNhanList.size(); i++) {
                            if (caNhanList.get(i).getId() == deletedCn.getId()) {
                                index = i;
                                break;
                            }
                        }
                        if (index != -1) {
                            caNhanList.remove(index);
                            adapter.notifyItemRemoved(index);
                        }
                    }

                    @Override
                    public void onEdit(CaNhan editCn) {
                        // Mở activity ThongTinLienHeActivity ở chế độ edit, truyền dữ liệu
                        Intent intent = new Intent(requireContext(), ThongTinLienHeActivity.class);
                        intent.putExtra("mode", "edit");
                        intent.putExtra("id", editCn.getId());
                        intent.putExtra("danhXung", editCn.getDanhXung());
                        intent.putExtra("hoTen", editCn.getHoVaTen());
                        intent.putExtra("ten", editCn.getTen());
                        intent.putExtra("congTy", editCn.getCongTy());
                        intent.putExtra("gioiTinh", editCn.getGioiTinh());
                        intent.putExtra("diDong", editCn.getDiDong());
                        intent.putExtra("email", editCn.getEmail());
                        intent.putExtra("ngaySinh", editCn.getNgaySinh());
                        intent.putExtra("diaChi", editCn.getDiaChi());
                        intent.putExtra("quanHuyen", editCn.getQuanHuyen());
                        intent.putExtra("tinhTP", editCn.getTinhTP());
                        intent.putExtra("quocGia", editCn.getQuocGia());
                        intent.putExtra("moTa", editCn.getMoTa());
                        intent.putExtra("ghiChu", editCn.getGhiChu());
                        intent.putExtra("giaoCho", editCn.getGiaoCho());
                        intent.putExtra("soCuocGoi", editCn.getSoCuocGoi());
                        intent.putExtra("soCuocHop", editCn.getSoCuocHop());

                        editCaNhanLauncher.launch(intent);
                    }

                    @Override
                    public void onAddHoatDong(CaNhan cn) {
                        BottomHoatDongFragment bottom = new BottomHoatDongFragment();
                        bottom.setCaNhan(cn);   // <<< gửi dữ liệu CaNhan
                        bottom.show(getParentFragmentManager(), "hoatdong");
                    }
                });
                bottomSheet.show(getParentFragmentManager(), "BottomAction");
            }

            @Override
            public void onItemClick(CaNhan cn) {
                Intent intent = new Intent(requireContext(), TabActivity.class);
                intent.putExtra("CANHAN_OBJECT", cn);
                startActivity(intent);
            }
        });

        return view;
    }

    private void handleAddResult(Intent data) {
        CaNhan cn = new CaNhan();

        // --- Lấy dữ liệu từ form ---
        cn.setHoVaTen(data.getStringExtra("hoTen"));
        cn.setTen(data.getStringExtra("ten"));
        cn.setCongTy(data.getStringExtra("congTy"));
        cn.setDanhXung(data.getStringExtra("danhXung"));
        cn.setGioiTinh(data.getStringExtra("gioiTinh"));
        cn.setDiDong(data.getStringExtra("diDong"));
        cn.setEmail(data.getStringExtra("email"));
        cn.setNgaySinh(data.getStringExtra("ngaySinh"));
        cn.setDiaChi(data.getStringExtra("diaChi"));
        cn.setQuanHuyen(data.getStringExtra("quanHuyen"));
        cn.setTinhTP(data.getStringExtra("tinhTP"));
        cn.setQuocGia(data.getStringExtra("quocGia"));
        cn.setMoTa(data.getStringExtra("moTa"));
        cn.setGhiChu(data.getStringExtra("ghiChu"));
        cn.setGiaoCho(data.getStringExtra("giaoCho"));

        // --- Ngày tạo mặc định hôm nay (nếu chưa set) ---
        if (data.getStringExtra("ngayTao") != null) {
            cn.setNgayTao(data.getStringExtra("ngayTao"));
        } else {
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
            cn.setNgayTao(sdf.format(calendar.getTime()));
        }

        // --- Số cuộc gọi & meeting mặc định từ result nếu có ---
        cn.setSoCuocGoi(data.getIntExtra("soCuocGoi", 2));
        cn.setSoCuocHop(data.getIntExtra("soCuocHop", 2));

        // --- Lưu vào database (nhận id trả về) ---
        long newId = db.add(cn);
        cn.setId((int)newId);

        // --- Cập nhật RecyclerView ---
        caNhanList.add(cn);
        adapter.notifyItemInserted(caNhanList.size() - 1);
        rvCaNhan.scrollToPosition(caNhanList.size() - 1);
    }

    private void handleEditResult(Intent data) {
        int id = data.getIntExtra("id", -1);
        if (id != -1) {
            // Tìm model trong list theo id
            int index = -1;
            for (int i = 0; i < caNhanList.size(); i++) {
                if (caNhanList.get(i).getId() == id) {
                    index = i;
                    break;
                }
            }
            if (index != -1) {
                CaNhan cn = caNhanList.get(index);

                // cập nhật từ result
                cn.setDanhXung(data.getStringExtra("danhXung"));
                cn.setHoVaTen(data.getStringExtra("hoTen"));
                cn.setTen(data.getStringExtra("ten"));
                cn.setCongTy(data.getStringExtra("congTy"));
                cn.setGioiTinh(data.getStringExtra("gioiTinh"));
                cn.setDiDong(data.getStringExtra("diDong"));
                cn.setEmail(data.getStringExtra("email"));
                cn.setNgaySinh(data.getStringExtra("ngaySinh"));
                cn.setDiaChi(data.getStringExtra("diaChi"));
                cn.setQuanHuyen(data.getStringExtra("quanHuyen"));
                cn.setTinhTP(data.getStringExtra("tinhTP"));
                cn.setQuocGia(data.getStringExtra("quocGia"));
                cn.setMoTa(data.getStringExtra("moTa"));
                cn.setGhiChu(data.getStringExtra("ghiChu"));
                cn.setGiaoCho(data.getStringExtra("giaoCho"));
                cn.setSoCuocGoi(data.getIntExtra("soCuocGoi", cn.getSoCuocGoi()));
                cn.setSoCuocHop(data.getIntExtra("soCuocHop", cn.getSoCuocHop()));

                // Cập nhật ngày sửa
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
                String ngaySuaHienTai = sdf.format(calendar.getTime());
                cn.setNgaySua(ngaySuaHienTai);

                // Cập nhật DB
                db.update(cn);

                // Cập nhật UI
                adapter.notifyItemChanged(index);
            }
        }
    }

    private void loadCaNhan() {
        List<CaNhan> listFromDB = db.getAllCaNhan();
        if (listFromDB.isEmpty()) {
            // Nếu DB trống, khởi tạo danh sách mặc định
            caNhanList = new ArrayList<>();
            caNhanList.add(new CaNhan("Anh","Nguyễn Văn", "A", "Công ty X", "01/01/2025", 2, 2));
            caNhanList.add(new CaNhan("Chị", "Trần Thị", "B", "Công ty Y", "02/01/2025", 2, 2));
            caNhanList.add(new CaNhan("Anh", "Lê Văn", "C", "Công ty Z", "03/01/2025", 2, 2));

            // Lưu 3 item mặc định vào DB
            for (CaNhan cn : caNhanList) {
                long id = db.add(cn);
                cn.setId((int) id);
            }
        } else {
            caNhanList = new ArrayList<>(listFromDB);
        }
    }
}
