package com.example.crmmobile.QuoteDirectory;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.crmmobile.R;

public class QuoteDetailActivity extends AppCompatActivity {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quote_detail);

        quote_detail quoteDetail = quote_detail.newInstance();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.activity_quote_detail, quoteDetail)
                .commit();
    }
}
