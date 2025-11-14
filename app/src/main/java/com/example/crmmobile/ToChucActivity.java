package com.example.crmmobile;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class ToChucActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 1. Load layout khung
        setContentView(R.layout.activity_tochuc);

        // 2. Tải ToChucFragment vào khung
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.tochuc_container, new ToChucFragment())
                    .commit();
        }
    }
}
