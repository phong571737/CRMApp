package com.example.crmmobile;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class EditLeadActivity extends AppCompatActivity {
    private ImageButton btn_back;
    private TabLayout tabLayout;
    private ViewPager2 vp_tab;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_lead);

        btn_back = findViewById(R.id.btn_back);
        tabLayout = findViewById(R.id.tablayout);
        vp_tab = findViewById(R.id.vp_tab);

        AdapterEditLead adapterEdit = new AdapterEditLead(this);
        vp_tab.setAdapter(adapterEdit);

        new TabLayoutMediator(tabLayout, vp_tab, ((tab, i) -> {
            if(i == 0) tab.setText("Thông tin Lead");
            else tab.setText("Thông tin khác");
        })).attach();

        btn_back.setOnClickListener(v -> {
            finish();
        });
    }
}
