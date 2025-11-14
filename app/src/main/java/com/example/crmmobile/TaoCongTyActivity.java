package com.example.crmmobile;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import com.google.android.material.tabs.TabLayout;

public class TaoCongTyActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private TextView tvHeaderTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_taocongty);
        tvHeaderTitle = findViewById(R.id.tv_header_title);
        String title = getIntent().getStringExtra("EXTRA_TITLE");

        if (title != null && !title.isEmpty()) {
            tvHeaderTitle.setText(title);
        }

        ImageButton btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> {
            finish();
        });

        tabLayout = findViewById(R.id.tabLayout);

        // Hiển thị fragment đầu tiên mặc định
        replaceFragment(new TaoCongTyThongTinCongTyFragment());

        // Lắng nghe khi người dùng chuyển tab
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Fragment selectedFragment;

                if (tab.getPosition() == 0) {
                    selectedFragment = new TaoCongTyThongTinCongTyFragment();
                } else {
                    selectedFragment = new TaoCongTyThongTinKhacFragment();
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

    // Hàm thay fragment trong FrameLayout
    private void replaceFragment(Fragment fragment) {
        // (Sau này khi có database, sẽ truyền ID vào đây)
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameLayoutThongTin, fragment);
        transaction.commit();
    }
}