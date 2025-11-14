package com.example.crmmobile;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

public class OpportunityFormActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_opportunity_form);

        String mode = getIntent().getStringExtra("mode");
        Opportunity opportunity = (Opportunity) getIntent().getSerializableExtra("opportunity");
        int position = getIntent().getIntExtra("position", -1);

        OpportunityFormFragment fragment = OpportunityFormFragment.newInstance(opportunity, position, mode);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.layout_opportunity_form, fragment);
        ft.commit();
    }
}
