package com.example.crmmobile.QuoteDirectory;

import android.os.Bundle;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.example.crmmobile.DataBase.QuoteRepository;
import com.example.crmmobile.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.tabs.TabLayout;

public class TaoBaoGiaActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ImageButton btnBack;
    private MaterialButton abort_button, save_button;
    private CreateQuoteViewModel createQuoteViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_taobaogia);
        createQuoteViewModel = new ViewModelProvider(this).get(CreateQuoteViewModel.class);
        initViews();

        // Hiển thị fragment đầu tiên (Thông tin chung) mặc định
        replaceFragment(new TaoBaoGiaThongTinChungFragment());
        btnBack.setOnClickListener(v -> {
            finish();
        });
        abort_button.setOnClickListener(v -> {
            finish();
        });
        createQuoteViewModel.getQuoteCreateEvent().observe(this, created->{
            if (Boolean.TRUE.equals(created)){
                finish();
                createQuoteViewModel.clearCreatedEvent();
            }
        });
        save_button.setOnClickListener(v -> saveCreateQuote());

        // Lắng nghe khi người dùng chuyển tab
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Fragment selectedFragment;

                if (tab.getPosition() == 0) {
                    selectedFragment = new TaoBaoGiaThongTinChungFragment();
                } else {
                    selectedFragment = new TaoBaoGiaSanPhamFragment();
                }

                replaceFragment(selectedFragment);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                // Không cần xử lý gì
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                // Không cần xử lý gì
            }
        });
    }

    private void saveCreateQuote() {
        createQuoteViewModel.saveQuote();
    }

    private void initViews() {
        tabLayout = findViewById(R.id.tabLayout);
        btnBack = findViewById(R.id.btnBack);
        abort_button = findViewById(R.id.abort_button);
        save_button = findViewById(R.id.save_button);
    }

    // Hàm thay fragment trong FrameLayout
    private void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameLayoutThongTin, fragment);
        transaction.commit();
    }
}