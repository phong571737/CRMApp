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
import com.example.crmmobile.DataBase.CompanyRepository;
import com.example.crmmobile.DataBase.CaNhanRepository;
import com.example.crmmobile.OrganizationDirectory.ToChuc;
import com.example.crmmobile.IndividualDirectory.CaNhan;
import com.example.crmmobile.OrderDirectory.OrderFragment;
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
    private final ArrayList<ProductLine> draftProducts = new ArrayList<>();
    private final JSONObject draftExtra = new JSONObject();
    private TextInputEditText edtOrderDate, edtTitle, edtPhone;
    private TextInputLayout tilOrderDate, tilCompany, tilContact;
    private View generalContainer, otherTabContainer, btnCancel, btnSave;
    private DonHangRepository donHangRepository;
    private CompanyRepository companyRepository;
    private CaNhanRepository caNhanRepository;
    private final Fragment productsFragment = new SOProductsFragment();
    private final Fragment paymentFragment  = new ThanhToanFragment();
    private List<ToChuc> companyList = new ArrayList<>();
    private int selectedCongTyId = 0;
    private List<CaNhan> contactList = new ArrayList<>();
    private int selectedNguoiLienHeId = 0;
    private AutoCompleteTextView actCompany, actContact, actStatus;
    private MaterialToolbar tb;
    private TabLayout tabs;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_socreate1);

        companyRepository = new CompanyRepository(this);
        donHangRepository = new DonHangRepository(this);
        caNhanRepository = new CaNhanRepository(this);
        initVariables();

        setSupportActionBar(tb);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        if (tb != null) tb.setNavigationOnClickListener(v -> goBack());

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

        bindCompanyAutoComplete(actCompany);
        bindContactAutoComplete(actContact);
        // Load danh sách công ty từ database
        if (actCompany != null) {
            companyList = companyRepository.getAllCompany();
            ArrayAdapter<ToChuc> companyAdapter = new ArrayAdapter<>(
                    this,
                    android.R.layout.simple_list_item_1,
                    companyList
            );
            actCompany.setAdapter(companyAdapter);
            
            // Lưu ID công ty khi người dùng chọn
            actCompany.setOnItemClickListener((parent, view, position, id) -> {
                if (position >= 0 && position < companyList.size()) {
                    selectedCongTyId = companyList.get(position).getId();
                } else {
                    selectedCongTyId = 0;
                }
            });
        }

        // Load danh sách người liên hệ từ database
        if (actContact != null) {
            contactList = caNhanRepository.getAllCaNhan();
            ArrayAdapter<CaNhan> contactAdapter = new ArrayAdapter<CaNhan>(
                    this,
                    android.R.layout.simple_list_item_1,
                    contactList
            ) {
                @Override
                public android.view.View getView(int position, android.view.View convertView, android.view.ViewGroup parent) {
                    android.view.View view = super.getView(position, convertView, parent);
                    setFullName(view, position);
                    return view;
                }

                @Override
                public android.view.View getDropDownView(int position, android.view.View convertView, android.view.ViewGroup parent) {
                    android.view.View view = super.getDropDownView(position, convertView, parent);
                    setFullName(view, position);
                    return view;
                }

                private void setFullName(android.view.View view, int position) {
                    CaNhan cn = getItem(position);
                    if (cn != null) {
                        // Hiển thị họ tên đầy đủ: hoVaTen + " " + ten
                        String hoVaTen = cn.getHoVaTen() != null ? cn.getHoVaTen() : "";
                        String ten = cn.getTen() != null ? cn.getTen() : "";
                        String fullName = (hoVaTen + " " + ten).trim();
                        ((android.widget.TextView) view).setText(fullName);
                    }
                }
            };
            actContact.setAdapter(contactAdapter);
            
            // Lưu ID người liên hệ khi người dùng chọn
            actContact.setOnItemClickListener((parent, view, position, id) -> {
                if (position >= 0 && position < contactList.size()) {
                    CaNhan cn = contactList.get(position);
                    selectedNguoiLienHeId = cn.getId();
                    // Đảm bảo hiển thị họ tên đầy đủ
                    String hoVaTen = cn.getHoVaTen() != null ? cn.getHoVaTen() : "";
                    String ten = cn.getTen() != null ? cn.getTen() : "";
                    String fullName = (hoVaTen + " " + ten).trim();
                    actContact.setText(fullName, false);
                } else {
                    selectedNguoiLienHeId = 0;
                }
            });
        }

        // Status
        if (actStatus != null) {
            actStatus.setAdapter(new ArrayAdapter<>(
                    this, android.R.layout.simple_list_item_1,
                    new String[]{"Mới", "Đang xử lý", "Hoàn tất"}));
        }

        if (tilCompany != null) tilCompany.setEndIconOnClickListener(v -> {});
        if (tilContact != null) tilContact.setEndIconOnClickListener(v -> {});
        if (tilOrderDate != null) tilOrderDate.setEndIconOnClickListener(v -> openDatePicker());
        if (edtOrderDate != null) edtOrderDate.setOnClickListener(v -> openDatePicker());
        if (btnCancel != null) btnCancel.setOnClickListener(v -> goBack());
        if (btnSave != null) btnSave.setOnClickListener(v -> saveOrder());
    }

    private void initVariables() {
        generalContainer  = findViewById(R.id.generalContainer);
        otherTabContainer = findViewById(R.id.otherTabContainer);
        actCompany  = findViewById(R.id.actCompany);
        actContact  = findViewById(R.id.actContact);
        actStatus   = findViewById(R.id.actStatus);
        tb = findViewById(R.id.toolbar);
        tabs = findViewById(R.id.tabLayout);
        btnCancel = findViewById(R.id.btnCancel);
        btnSave = findViewById(R.id.btnSave);
        edtOrderDate = findViewById(R.id.edtOrderDate);
        tilCompany = findViewById(R.id.tilCompany);
        tilContact = findViewById(R.id.tilContact);
        tilOrderDate = findViewById(R.id.tilOrderDate);
        edtTitle = findViewById(R.id.edtTitle);
        edtPhone = findViewById(R.id.edtPhone);
    }

    private void bindCompanyAutoComplete(AutoCompleteTextView actCompany) {
        if (actCompany == null) return;
        try {
            List<ToChuc> companies = companyRepository.getAllCompany();
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

        }
    }

    private void bindContactAutoComplete(AutoCompleteTextView actContact) {
        if (actContact == null) return;
        try {
            List<CaNhan> contacts = caNhanRepository.getAllCaNhan();
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

    private void saveOrder() {
        String title      = edtTitle != null ? edtTitle.getText().toString().trim() : "";
        String phoneStr   = edtPhone != null ? edtPhone.getText().toString().trim() : "";
        String companyStr = actCompany != null ? actCompany.getText().toString().trim() : "";
        String contactStr = actContact != null ? actContact.getText().toString().trim() : "";
        String statusStr  = actStatus != null ? actStatus.getText().toString().trim() : "";
        String dateStr    = edtOrderDate != null ? edtOrderDate.getText().toString().trim() : "";

        if (title.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập tiêu đề đơn hàng", Toast.LENGTH_SHORT).show();
            return;
        }
        if (dateStr.isEmpty()) {
            Toast.makeText(this, "Vui lòng chọn ngày đặt hàng", Toast.LENGTH_SHORT).show();
            return;
        }
        if (statusStr.isEmpty()) statusStr = "Mới";

        List<ProductLine> productsForSave = getProductsForSave();

        int totalQty = calcTotalQty(productsForSave);
        long totalMoney = calcTotalMoney(productsForSave);
        String productsJson = encodeProductsJson(productsForSave);

        DonHang dh = new DonHang();
        dh.setTenDonHang(title);
        dh.setNgayDatHang(dateStr);
        dh.setTinhTrang(statusStr);
        dh.setExtraJson(draftExtra.toString());
        dh.setNgayNhanHang("");
        dh.setCongTyId(selectedCongTyId); // Lưu ID công ty và người liên hệ đã chọn
        dh.setNguoiLienHeId(selectedNguoiLienHeId);
        dh.setCoHoiId(0);
        dh.setBaoGiaId(0);
        dh.setSanPham(productsJson);
        dh.setSoLuong(totalQty);
        long grandTotal = getGrandTotalFromProductsFragment(); //tổng cộng sau giảm giá + thuế
        if (grandTotal <= 0) grandTotal = totalMoney;
        dh.setTongTien(grandTotal);
        dh.setDonGia(0);
        dh.setMoTa(companyStr + " - " + contactStr);
        dh.setCongTyId(0);
        dh.setNguoiLienHeId(0);
        dh.setCoHoiId(0);
        dh.setBaoGiaId(0);
        dh.setGiaoChoId(0);

        long newId = donHangRepository.insert(dh);

        if (newId > 0) {
            trySaveExtraJsonIfExists(newId);

            Toast.makeText(this, "Lưu đơn hàng thành công", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Lưu đơn hàng thất bại", Toast.LENGTH_SHORT).show();
        }
    }

    private List<ProductLine> getProductsForSave() {
        ArrayList<ProductLine> out = new ArrayList<>();

        if (!draftProducts.isEmpty()) {
            out.addAll(draftProducts);
            return out;
        }

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

    private void trySaveExtraJsonIfExists(long orderId) {
        try {
            if (draftExtra.length() == 0) return;

            DBCRMHandler helper = new DBCRMHandler(this);
            SQLiteDatabase db = helper.getWritableDatabase();

            ContentValues cv = new ContentValues();
            cv.put("EXTRA_JSON", draftExtra.toString());

            db.update("DONHANG", cv, "ID=?", new String[]{String.valueOf(orderId)});
            db.close();
        } catch (Exception ignored) {
        }
    }

    //Date
    private void openDatePicker() {
        Calendar c = Calendar.getInstance();
        new DatePickerDialog(this, (view, y, m, d) -> {
            String s = String.format(Locale.getDefault(), "%02d/%02d/%04d",
                    d, m + 1, y);
            if (edtOrderDate != null) edtOrderDate.setText(s);
        }, c.get(Calendar.YEAR), c.get(Calendar.MONTH),
                c.get(Calendar.DAY_OF_MONTH)).show();
    }

    public JSONObject getDraftExtra() {
        return draftExtra;
    }

    public void putExtra(String key, String value) {
        try { draftExtra.put(key, value); } catch (Exception ignored) {}
    }
}






