package com.example.crmmobile;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.widget.FrameLayout;

import androidx.viewpager2.widget.ViewPager2;

public class MainActivity extends AppCompatActivity implements main_screen.onModuleItemSelectedListener{
    private ViewPager2 viewPager2;
    private BottomNavigationView navFooter;
    FrameLayout container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager2 = findViewById(R.id.viewPager);
        navFooter = findViewById(R.id.nav_footer);
        container = findViewById(R.id.main_container);

        AdapterViewPager adapter = new AdapterViewPager(this);
        viewPager2.setAdapter(adapter);

        navFooter.setOnItemSelectedListener(item -> {
            int itemID = item.getItemId();

            // Nếu đang mở fragment trong container thì pop về viewPager
            if (container.getVisibility() == View.VISIBLE) {
                getSupportFragmentManager().popBackStack(
                        null,
                        FragmentManager.POP_BACK_STACK_INCLUSIVE
                );
                container.setVisibility(View.GONE);
                viewPager2.setVisibility(View.VISIBLE);
            }

            if (itemID == R.id.nav_home) {
                // 🏠 Trang chủ -> vẫn dùng ViewPager tab 0
                viewPager2.setCurrentItem(0);
                return true;

            } else if (itemID == R.id.nav_lead) {
                // Lead -> vẫn dùng ViewPager tab 1
                viewPager2.setCurrentItem(1);
                return true;

            } else if (itemID == R.id.nav_order) {
                // 🧾 ĐƠN HÀNG -> MỞ ACTIVITY MỚI
                Intent intent = new Intent(
                        MainActivity.this,
                        com.example.crmmobile.feature.salesorder.ui.list.MainActivity.class
                );
                startActivity(intent);
                // các tab khác KHÔNG bị ảnh hưởng
                return true;

            } else if (itemID == R.id.nav_calendar) {
                // ví dụ: tab Lịch (nếu có)
                viewPager2.setCurrentItem(3);
                return true;

            } else if (itemID == R.id.nav_menu) {
                // tab Thêm / Menu
                viewPager2.setCurrentItem(4);
                return true;
            }

            return false;
        });


        //back to main screen
        getSupportFragmentManager().addOnBackStackChangedListener(()->{
            Fragment current = getSupportFragmentManager().findFragmentById(R.id.main_container);
            if(current == null){
                viewPager2.setVisibility(View.VISIBLE);
                container.setVisibility(View.GONE);

//                navFooter.getMenu().findItem(R.id.nav_home).setChecked(true);//footer nav back to home tab
            }
        });
    }

    @Override
    public void onModuleSelectedListener(String moduleName){
        if(moduleName.equals("Báo giá")){
            Fragment quoteFragment = new QuoteFragment();

            getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_container, quoteFragment)
                .addToBackStack(null)
                .commit();
            findViewById(R.id.viewPager).setVisibility(View.GONE); //hide viewpager2
            findViewById(R.id.main_container).setVisibility(View.VISIBLE);

            navFooter.getMenu().findItem(R.id.nav_menu).setChecked(true);
            }
        if(moduleName.equals("Cơ hội")){
            Fragment opportunityFragment = new OpportunityFragment();

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main_container, opportunityFragment)
                    .addToBackStack(null)
                    .commit();

            findViewById(R.id.viewPager).setVisibility(View.GONE);
            findViewById(R.id.main_container).setVisibility(View.VISIBLE);

            navFooter.getMenu().findItem(R.id.nav_menu).setChecked(true);
        }
        if(moduleName.equals("Liên hệ")){
            Intent intent = new Intent(MainActivity.this, ThongTinLienHeActivity.class);
            startActivity(intent);
        }

        if(moduleName.equals("Tổ chức")){
            Fragment toChucFragment  = new ToChucFragment() ;

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main_container, toChucFragment)
                    .addToBackStack(null)
                    .commit();

            findViewById(R.id.viewPager).setVisibility(View.GONE);
            findViewById(R.id.main_container).setVisibility(View.VISIBLE);

            navFooter.getMenu().findItem(R.id.nav_menu).setChecked(true);
        }
    }
}