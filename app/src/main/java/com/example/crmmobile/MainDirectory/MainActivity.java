package com.example.crmmobile.MainDirectory;

import android.content.Intent;
import android.os.Bundle;

import com.example.crmmobile.Adapter.AdapterViewPager;
import com.example.crmmobile.IndividualDirectory.DanhSachCaNhanFragment;
import com.example.crmmobile.OpportunityDirectory.OpportunityFragment;
import com.example.crmmobile.OrganizationDirectory.ToChucFragment;
import com.example.crmmobile.QuoteDirectory.QuoteFragment;
import com.example.crmmobile.R;
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

        navFooter.setOnItemSelectedListener(item->{
            int itemID = item.getItemId();

            //return home
            if(container.getVisibility() == View.VISIBLE){
                getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                container.setVisibility(View.GONE);
                viewPager2.setVisibility(View.VISIBLE);
            }

            if(item.getItemId() == R.id.nav_home){
                viewPager2.setCurrentItem(0);
                return true;
            } else if (item.getItemId() == R.id.nav_lead) {
                viewPager2.setCurrentItem(1);
                return true;
            }
            else if (item.getItemId() == R.id.nav_order){
                viewPager2.setCurrentItem(2);
                return true;
            }
            else if(item.getItemId() == R.id.nav_calendar){
                viewPager2.setCurrentItem(3);
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

        if(moduleName.equals("Cá nhân")){
            Fragment danhsachcanhanFragment = new DanhSachCaNhanFragment();

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main_container, danhsachcanhanFragment)
                    .addToBackStack(null)
                    .commit();

            findViewById(R.id.viewPager).setVisibility(View.GONE);
            findViewById(R.id.main_container).setVisibility(View.VISIBLE);
            navFooter.getMenu().findItem(R.id.nav_menu).setChecked(true);
        }

        if(moduleName.equals("Công ty")){
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

        if(moduleName.equals("Báo cáo")){
            Intent intent = new Intent(this, com.example.crmmobile.ReportDirectory.BaoCaoActivity.class);
            startActivity(intent);
        }
    }
}

