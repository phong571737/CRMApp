package com.example.crmmobile;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class DetailLeadActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_lead);

        String name = getIntent().getStringExtra("name");
        String company = getIntent().getStringExtra("company");
        String daycontact = getIntent().getStringExtra("daycontact");

        leaddetail fragment = leaddetail.newInstance(name, company, daycontact);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.activity_detail_lead, fragment)
                .commit();
    }
}
