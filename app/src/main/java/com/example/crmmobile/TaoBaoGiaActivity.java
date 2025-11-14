package com.example.crmmobile;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import com.google.android.material.tabs.TabLayout;

public class TaoBaoGiaActivity extends AppCompatActivity {

    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_taobaogia);

        tabLayout = findViewById(R.id.tabLayout);

        // Hiển thị fragment đầu tiên (Thông tin chung) mặc định
        replaceFragment(new TaoBaoGiaThongTinChungFragment());

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

    // Hàm thay fragment trong FrameLayout
    private void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameLayoutThongTin, fragment);
        transaction.commit();
    }
}