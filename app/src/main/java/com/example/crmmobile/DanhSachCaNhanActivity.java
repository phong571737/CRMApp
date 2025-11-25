package com.example.crmmobile;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import android.widget.ImageView;


public class DanhSachCaNhanActivity extends AppCompatActivity {
    private ImageView icBack;

    private RecyclerView rvCaNhan;
    private CaNhanAdapter adapter;
    private ArrayList<CaNhan> caNhanList;
    private FloatingActionButton btnAdd;

    private DBCRMHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danhsachcanhan);

        rvCaNhan = findViewById(R.id.rvCaNhan);
        btnAdd = findViewById(R.id.btn_add_contact);

        // --- Khởi tạo DB ---
        db = new DBCRMHandler(this);

        // --- Load dữ liệu từ database ---
        loadCaNhan();

        // --- Adapter + RecyclerView ---
        adapter = new CaNhanAdapter(this, caNhanList);
        rvCaNhan.setLayoutManager(new LinearLayoutManager(this));
        rvCaNhan.setAdapter(adapter);

        // --- Nút thêm mới ---
        btnAdd.setOnClickListener(v -> {
            Intent intent = new Intent(DanhSachCaNhanActivity.this, ThongTinLienHeActivity.class);
            startActivityForResult(intent, 100);
        });

        icBack = findViewById(R.id.ic_back); // phải bind view trước
        icBack.setOnClickListener(v -> {
            onBackPressed(); // quay lại Activity trước
        });


        adapter.setOnItemClickListener(new CaNhanAdapter.OnItemClickListener() {
            @Override
            public void onMoreClick(CaNhan cn) {
                BottomActionFragment bottomSheet = new BottomActionFragment();
                bottomSheet.show(getSupportFragmentManager(), "BottomAction"); // dùng getSupportFragmentManager() của Activity
            }

            @Override
            public void onItemClick(CaNhan cn) {
                Intent intent = new Intent(DanhSachCaNhanActivity.this, TabActivity.class); // dùng Activity context
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100 && resultCode == RESULT_OK && data != null) {
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

            // --- Ngày tạo mặc định hôm nay ---
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            cn.setNgayTao(sdf.format(calendar.getTime()));

            // --- Số cuộc gọi & meeting mặc định ---
            cn.setSoCuocGoi(2);
            cn.setSoCuocHop(2);

            // --- Lưu vào database ---
            db.add(cn);

            // --- Cập nhật RecyclerView ---
            caNhanList.add(cn);
            adapter.notifyItemInserted(caNhanList.size() - 1);
            rvCaNhan.scrollToPosition(caNhanList.size() - 1);
        }
    }

    private void loadCaNhan() {
        List<CaNhan> listFromDB = db.getAllCaNhan();
        if (listFromDB.isEmpty()) {
            // Nếu DB trống, khởi tạo danh sách mặc định
            caNhanList = new ArrayList<>();
            caNhanList.add(new CaNhan("Nguyễn Văn A", "Công ty X", "01/01/2025", 2, 2));
            caNhanList.add(new CaNhan("Trần Thị B", "Công ty Y", "02/01/2025", 2, 2));
            caNhanList.add(new CaNhan("Lê Văn C", "Công ty Z", "03/01/2025", 2, 2));

            // Lưu 3 item mặc định vào DB
            for (CaNhan cn : caNhanList) {
                db.add(cn);
            }
        } else {
            caNhanList = new ArrayList<>(listFromDB);
        }
    }
}
