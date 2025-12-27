package com.example.crmmobile.OrderDirectory;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.crmmobile.DataBase.CaNhanRepository;
import com.example.crmmobile.DataBase.CompanyRepository;
import com.example.crmmobile.DataBase.DBCRMHandler;
import com.example.crmmobile.DataBase.DonHangRepository;
import com.example.crmmobile.IndividualDirectory.CaNhan;
import com.example.crmmobile.OrganizationDirectory.ToChuc;
import com.example.crmmobile.R;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class SOCreate1Activity extends AppCompatActivity {

    // ===== Host Draft (để fragment có thể ghi vào Activity nếu bạn muốn mở rộng sau) =====
    public interface DraftOrderHost {
        ArrayList<ProductLine> getDraftProducts();
        JSONObject getDraftExtra();
        void putExtra(String key, String value);
    }


    private final ArrayList<ProductLine> draftProducts = new ArrayList<>();
    private final JSONObject draftExtra = new JSONObject();

    // ===== Views =====
    private TextInputEditText edtOrderDate;
    private View generalContainer;
    private View otherTabContainer;

    // ===== Repo =====
    private DonHangRepository donHangRepository;

    // ===== Cache fragment instances (QUAN TRỌNG: không bị mất dữ liệu khi đổi tab) =====
    private final Fragment productsFragment = new SOProductsFragment();
    private final Fragment paymentFragment  = new ThanhToanFragment();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_socreate1);

        donHangRepository = new DonHangRepository(this);

        // ----- Toolbar + nút back -----
        MaterialToolbar tb = findViewById(R.id.toolbar);
        setSupportActionBar(tb);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        if (tb != null) tb.setNavigationOnClickListener(v -> goBack());

        // ----- Containers -----
        generalContainer  = findViewById(R.id.generalContainer);
        otherTabContainer = findViewById(R.id.otherTabContainer);

        // ----- TabLayout -----
        TabLayout tabs = findViewById(R.id.tabLayout);
        if (tabs != null) {
            tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    if (tab == null) return;
                    switch (tab.getPosition()) {
                        case 0: // Thông tin chung
                            showGeneral();
                            break;
                        case 1: // Sản phẩm
                            showOther(productsFragment);
                            break;
                        case 2: // Thanh toán
                            showOther(paymentFragment);
                            break;
                        default:
                            showGeneral();
                            break;
                    }
                }

                @Override public void onTabUnselected(TabLayout.Tab tab) {}
                @Override public void onTabReselected(TabLayout.Tab tab) {}
            });

            if (savedInstanceState == null) {
                tabs.selectTab(tabs.getTabAt(0));
                showGeneral();
            }
        } else {
            showGeneral();
        }

        // ========== Logic "Thông tin chung" ==========
        AutoCompleteTextView actCompany  = findViewById(R.id.actCompany);
        AutoCompleteTextView actContact  = findViewById(R.id.actContact);
        AutoCompleteTextView actStatus   = findViewById(R.id.actStatus);

        // ✅ Không set cứng: lấy danh sách từ DB (nếu DB có dữ liệu thì tự show)
        bindCompanyAutoComplete(actCompany);
        bindContactAutoComplete(actContact);

        // Status: có thể để cứng vì là enum trạng thái đơn (không phải data DB)
        if (actStatus != null) {
            actStatus.setAdapter(new ArrayAdapter<>(
                    this, android.R.layout.simple_list_item_1,
                    new String[]{"Mới", "Đang xử lý", "Hoàn tất"}));
        }

        TextInputLayout tilCompany = findViewById(R.id.tilCompany);
        TextInputLayout tilContact = findViewById(R.id.tilContact);
        if (tilCompany != null) tilCompany.setEndIconOnClickListener(v -> { /* nếu muốn mở picker thì làm sau */ });
        if (tilContact != null) tilContact.setEndIconOnClickListener(v -> { /* nếu muốn mở picker thì làm sau */ });

        TextInputLayout tilOrderDate = findViewById(R.id.tilOrderDate);
        edtOrderDate = findViewById(R.id.edtOrderDate);
        if (tilOrderDate != null) tilOrderDate.setEndIconOnClickListener(v -> openDatePicker());
        if (edtOrderDate != null) edtOrderDate.setOnClickListener(v -> openDatePicker());

        View btnCancel = findViewById(R.id.btnCancel);
        if (btnCancel != null) btnCancel.setOnClickListener(v -> goBack());

        View btnSave = findViewById(R.id.btnSave);
        if (btnSave != null) btnSave.setOnClickListener(v -> saveOrder());
    }

    // ===== AutoComplete from DB =====
    private void bindCompanyAutoComplete(AutoCompleteTextView actCompany) {
        if (actCompany == null) return;
        try {
            CompanyRepository companyRepo = new CompanyRepository(this);
            List<ToChuc> companies = companyRepo.getAllCompany();
            ArrayList<String> names = new ArrayList<>();
            for (ToChuc c : companies) {
                if (c != null && c.getCompanyName() != null && !c.getCompanyName().trim().isEmpty()) {
                    names.add(c.getCompanyName().trim());
                }
            }
            if (!names.isEmpty()) {
                actCompany.setAdapter(new ArrayAdapter<>(
                        this, android.R.layout.simple_list_item_1, names));
            }
        } catch (Exception ignored) {
            // nếu DB chưa có bảng/company repo lỗi -> không crash
        }
    }

    private void bindContactAutoComplete(AutoCompleteTextView actContact) {
        if (actContact == null) return;
        try {
            CaNhanRepository cnRepo = new CaNhanRepository(this);
            List<CaNhan> contacts = cnRepo.getAllCaNhan();
            ArrayList<String> names = new ArrayList<>();
            for (CaNhan cn : contacts) {
                if (cn != null && cn.getHoVaTen() != null && !cn.getHoVaTen().trim().isEmpty()) {
                    names.add(cn.getHoVaTen().trim());
                }
            }
            if (!names.isEmpty()) {
                actContact.setAdapter(new ArrayAdapter<>(
                        this, android.R.layout.simple_list_item_1, names));
            }
        } catch (Exception ignored) {
            // nếu DB chưa có bảng/contact repo lỗi -> không crash
        }
    }

    // ===== Back / Up =====
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            goBack();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void goBack() {
        finish();
    }

    @Override
    public boolean onSupportNavigateUp() {
        goBack();
        return true;
    }

    // ===== Hiển thị container =====
    private void showGeneral() {
        if (generalContainer != null) generalContainer.setVisibility(View.VISIBLE);
        if (otherTabContainer != null) otherTabContainer.setVisibility(View.GONE);
    }

    private void showOther(Fragment fragment) {
        if (generalContainer != null) generalContainer.setVisibility(View.GONE);
        if (otherTabContainer != null) {
            otherTabContainer.setVisibility(View.VISIBLE);
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.otherTabContainer, fragment);
            ft.commit();
        }
    }

    // ===== Lưu đơn hàng vào DB (FLOW 10) =====
    private void saveOrder() {
        TextInputEditText edtTitle = findViewById(R.id.edtTitle);
        TextInputEditText edtPhone = findViewById(R.id.edtPhone);

        AutoCompleteTextView actCompany = findViewById(R.id.actCompany);
        AutoCompleteTextView actContact = findViewById(R.id.actContact);
        AutoCompleteTextView actStatus  = findViewById(R.id.actStatus);

        String title      = edtTitle != null ? edtTitle.getText().toString().trim() : "";
        String phoneStr   = edtPhone != null ? edtPhone.getText().toString().trim() : "";
        String companyStr = actCompany != null ? actCompany.getText().toString().trim() : "";
        String contactStr = actContact != null ? actContact.getText().toString().trim() : "";
        String statusStr  = actStatus != null ? actStatus.getText().toString().trim() : "";
        String dateStr    = edtOrderDate != null ? edtOrderDate.getText().toString().trim() : "";

        // Validate
        if (title.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập tiêu đề đơn hàng", Toast.LENGTH_SHORT).show();
            return;
        }
        if (dateStr.isEmpty()) {
            Toast.makeText(this, "Vui lòng chọn ngày đặt hàng", Toast.LENGTH_SHORT).show();
            return;
        }
        if (statusStr.isEmpty()) statusStr = "Mới";

        // ===== Lấy sản phẩm (ưu tiên draftProducts; nếu rỗng thì pull từ SOProductsFragment) =====
        List<ProductLine> productsForSave = getProductsForSave();

        int totalQty = calcTotalQty(productsForSave);
        long totalMoney = calcTotalMoney(productsForSave);

        String productsJson = encodeProductsJson(productsForSave);


        // ===== Tạo DonHang để insert (không set cứng nữa) =====
        DonHang dh = new DonHang();
        dh.setTenDonHang(title);
        dh.setNgayDatHang(dateStr);
        dh.setTinhTrang(statusStr);

        // ✅ Lưu extra (thanh toán + thông tin create4) xuống DB
        dh.setExtraJson(draftExtra.toString());


        // Chưa có UI -> để trống
        dh.setNgayNhanHang("");

        // ✅ Lưu JSON sản phẩm vào cột SANPHAM
        dh.setSanPham(productsJson);

        // ✅ Lưu tổng
        dh.setSoLuong(totalQty);

        long grandTotal = getGrandTotalFromProductsFragment(); // ✅ tổng cộng sau giảm giá + thuế
        if (grandTotal <= 0) grandTotal = totalMoney;         // fallback nếu fragment chưa sẵn sàng

        dh.setTongTien(grandTotal);


        // donGia bạn có thể để 0 (vì đã có chi tiết từng dòng trong JSON)
        dh.setDonGia(0);

        // ✅ MoTa: giữ format "Công ty - Liên hệ" để các màn chi tiết bạn parse vẫn chạy
        // (phoneStr bạn có thể dùng sau)
        dh.setMoTa(companyStr + " - " + contactStr);

        // Các ID module khác: tạm 0 (không set cứng tên, chỉ là placeholder số)
        dh.setCongTyId(0);
        dh.setNguoiLienHeId(0);
        dh.setCoHoiId(0);
        dh.setBaoGiaId(0);
        dh.setGiaoChoId(0);

        long newId = donHangRepository.insert(dh);

        if (newId > 0) {
            // ✅ Nếu bạn đã thêm cột EXTRA_JSON thì Activity tự update (không cần sửa DonHang.java)
            trySaveExtraJsonIfExists(newId);

            Toast.makeText(this, "Lưu đơn hàng thành công", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Lưu đơn hàng thất bại", Toast.LENGTH_SHORT).show();
        }
    }

    // ===== Pull products from fragment (để không cần sửa SOProductsFragment vẫn lưu được) =====
    private List<ProductLine> getProductsForSave() {
        ArrayList<ProductLine> out = new ArrayList<>();

        // 1) nếu bạn đã sửa fragment theo host -> draftProducts có dữ liệu
        if (!draftProducts.isEmpty()) {
            out.addAll(draftProducts);
            return out;
        }

        // 2) nếu chưa sửa fragment -> đọc từ field "data" của SOProductsFragment bằng reflection
        try {
            if (productsFragment instanceof SOProductsFragment) {
                Field f = SOProductsFragment.class.getDeclaredField("data");
                f.setAccessible(true);
                Object val = f.get(productsFragment);
                if (val instanceof List) {
                    List<?> raw = (List<?>) val;
                    for (Object o : raw) {
                        if (o instanceof ProductLine) out.add((ProductLine) o);
                    }
                }
            }
        } catch (Exception ignored) {}

        return out;
    }
    private long getGrandTotalFromProductsFragment() {
        try {
            if (productsFragment instanceof SOProductsFragment) {
                return ((SOProductsFragment) productsFragment).getCurrentTotal();
            }
        } catch (Exception ignored) {}
        return 0L;
    }



    private String encodeProductsJson(List<ProductLine> list) {
        try {
            JSONArray arr = new JSONArray();
            if (list != null) {
                for (ProductLine p : list) {
                    if (p == null) continue;
                    JSONObject o = new JSONObject();
                    o.put("name", p.getName());
                    o.put("note", p.getNote());
                    o.put("qty", p.getQty());
                    o.put("price", p.getPrice());
                    arr.put(o);
                }
            }
            return arr.toString();
        } catch (Exception e) {
            return "[]";
        }
    }

    private int calcTotalQty(List<ProductLine> list) {
        int qty = 0;
        if (list != null) {
            for (ProductLine p : list) {
                if (p == null) continue;
                qty += Math.max(0, p.getQty());
            }
        }
        return qty;
    }

    private long calcTotalMoney(List<ProductLine> list) {
        long total = 0L;
        if (list != null) {
            for (ProductLine p : list) {
                if (p == null) continue;
                total += (p.getPrice() * (long) p.getQty());
            }
        }
        return total;
    }

    // ===== EXTRA_JSON (thanh toán/vận chuyển) =====
    // ThanhToanFragment sau này chỉ cần gọi: ((DraftOrderHost) requireActivity()).putExtra("paymentMethod", "...");
    private void trySaveExtraJsonIfExists(long orderId) {
        try {
            if (draftExtra.length() == 0) return;

            DBCRMHandler helper = new DBCRMHandler(this);
            SQLiteDatabase db = helper.getWritableDatabase();

            ContentValues cv = new ContentValues();
            cv.put("EXTRA_JSON", draftExtra.toString());

            // Nếu DB chưa có cột EXTRA_JSON -> sẽ throw, mình bắt để không crash
            db.update("DONHANG", cv, "ID=?", new String[]{String.valueOf(orderId)});
            db.close();
        } catch (Exception ignored) {
        }
    }

    // ===== Date picker =====
    private void openDatePicker() {
        Calendar c = Calendar.getInstance();
        new DatePickerDialog(this, (view, y, m, d) -> {
            String s = String.format(Locale.getDefault(), "%02d/%02d/%04d",
                    d, m + 1, y);
            if (edtOrderDate != null) edtOrderDate.setText(s);
        }, c.get(Calendar.YEAR), c.get(Calendar.MONTH),
                c.get(Calendar.DAY_OF_MONTH)).show();
    }

    // ===== DraftOrderHost methods (để fragment dùng nếu bạn muốn) =====
    public ArrayList<ProductLine> getDraftProducts() {
        return draftProducts;
    }

    public JSONObject getDraftExtra() {
        return draftExtra;
    }

    public void putExtra(String key, String value) {
        try { draftExtra.put(key, value); } catch (Exception ignored) {}
    }
}






