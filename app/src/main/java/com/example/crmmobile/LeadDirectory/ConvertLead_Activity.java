package com.example.crmmobile.LeadDirectory;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.crmmobile.R;

public class ConvertLead_Activity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_convert_lead);

        ConvertLead convertLead = ConvertLead.newInstance();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.activity_convert_lead, convertLead)
                .commit();
    }
}
