package com.example.crmmobile.feature.salesorder.ui.create;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.crmmobile.R;
import com.example.crmmobile.feature.salesorder.ui.list.MainActivity;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Calendar;
import java.util.Locale;

public class SOCreate1Activity extends AppCompatActivity {

    private TextInputEditText edtOrderDate;
    private View generalContainer;
    private View otherTabContainer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_socreate1);

        // ----- Toolbar + nút back -----
        MaterialToolbar tb = findViewById(R.id.toolbar);
        setSupportActionBar(tb);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        tb.setNavigationOnClickListener(v -> goBack());

        // ----- Containers -----
        generalContainer  = findViewById(R.id.generalContainer);   // layout "Thông tin chung"
        otherTabContainer = findViewById(R.id.otherTabContainer);  // FrameLayout cho các tab còn lại

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
                            showOther(new SOProductsFragment());
                            break;
                        case 2: // Thanh toán
                            showOther(new ThanhToanFragment());
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
                // mở mặc định tab 0
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

        if (actCompany != null) {
            actCompany.setAdapter(new ArrayAdapter<>(
                    this, android.R.layout.simple_list_item_1,
                    new String[]{"Công ty A", "Công ty B", "Công ty C"}));
        }

        if (actContact != null) {
            actContact.setAdapter(new ArrayAdapter<>(
                    this, android.R.layout.simple_list_item_1,
                    new String[]{"Nguyễn Văn An", "Trần B", "Lê C"}));
        }

        if (actStatus != null) {
            actStatus.setAdapter(new ArrayAdapter<>(
                    this, android.R.layout.simple_list_item_1,
                    new String[]{"Mới", "Đang xử lý", "Hoàn tất"}));
        }

        TextInputLayout tilCompany = findViewById(R.id.tilCompany);
        TextInputLayout tilContact = findViewById(R.id.tilContact);
        if (tilCompany != null) tilCompany.setEndIconOnClickListener(v -> { /* TODO */ });
        if (tilContact != null) tilContact.setEndIconOnClickListener(v -> { /* TODO */ });

        TextInputLayout tilOrderDate = findViewById(R.id.tilOrderDate);
        edtOrderDate = findViewById(R.id.edtOrderDate);
        if (tilOrderDate != null) tilOrderDate.setEndIconOnClickListener(v -> openDatePicker());
        if (edtOrderDate != null) edtOrderDate.setOnClickListener(v -> openDatePicker());

        View btnCancel = findViewById(R.id.btnCancel);
        if (btnCancel != null) btnCancel.setOnClickListener(v -> goBack());

        View btnSave = findViewById(R.id.btnSave);
        if (btnSave != null) {
            btnSave.setOnClickListener(v -> {
                // TODO: Validate & lưu đơn hàng
            });
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
        if (isTaskRoot()) {
            Intent i = new Intent(this, MainActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(i);
            finish();
        } else {
            finish();
        }
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
}





