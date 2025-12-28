package com.example.crmmobile.AuthDirectory;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.crmmobile.DataBase.NhanVienRepository;
import com.example.crmmobile.LeadDirectory.Nhanvien;
import com.example.crmmobile.MainDirectory.MainActivity;
import com.example.crmmobile.R;
import com.google.android.material.textfield.TextInputEditText;

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText edtEmail, edtPassword;
    private Button btnLogin;
    private TextView tvError;
    private NhanVienRepository nhanVienRepository;
    private SharedPreferences sharedPreferences;
    private static final String PREFS_NAME = "LoginPrefs";
    private static final String KEY_IS_LOGGED_IN = "isLoggedIn";
    private static final String KEY_USER_ID = "userId";
    private static final String KEY_USER_EMAIL = "userEmail";
    private static final String KEY_USER_ROLE = "userRole";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initViews();
        setupEvents();
        
        // Initialize database và khởi tạo admin account
        nhanVienRepository = new NhanVienRepository(this);
        nhanVienRepository.AddNhanVien();
        
        // Check if already logged in
        sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        if (sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false)) {
            navigateToMain();
        }
    }

    private void initViews() {
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvError = findViewById(R.id.tvError);
    }

    private void setupEvents() {
        btnLogin.setOnClickListener(v -> handleLogin());
    }

    private void handleLogin() {
        String email = edtEmail.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();

        // Validate input
        if (TextUtils.isEmpty(email)) {
            showError("Vui lòng nhập email");
            return;
        }

        if (TextUtils.isEmpty(password)) {
            showError("Vui lòng nhập mật khẩu");
            return;
        }

        // Authenticate
        Nhanvien nv = nhanVienRepository.authenticate(email, password);
        
        if (nv != null) {
            // Save login state
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(KEY_IS_LOGGED_IN, true);
            editor.putInt(KEY_USER_ID, nv.getId());
            editor.putString(KEY_USER_EMAIL, nv.getEmail());
            if (nv.getRole() != null) {
                editor.putString(KEY_USER_ROLE, nv.getRole());
            }
            editor.apply();
            
            // Navigate to main activity
            navigateToMain();
        } else {
            showError("Email hoặc mật khẩu không đúng");
        }
    }

    private void showError(String message) {
        tvError.setText(message);
        tvError.setVisibility(View.VISIBLE);
    }

    private void navigateToMain() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}

