package com.app.practicaltask;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.widget.Toast;

import com.app.practicaltask.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding binding;
    private static final int REQUEST_CODE = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        binding.setMLogin(this);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
            return;
        }

        Log.e("aaaaaaa", ""+SharedPref.getStringPref(LoginActivity.this, "KEY_PASSWORD"));
    }

    public void passwordToggle() {
        if (binding.imageViewShowPassword.isSelected()) {
            binding.editTextPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            binding.imageViewShowPassword.setSelected(false);
        } else {
            binding.editTextPassword.setInputType(InputType.TYPE_CLASS_TEXT);
            binding.imageViewShowPassword.setSelected(true);
        }
    }

    public void doLogin() {
        String email = binding.editTextEmail.getText().toString().trim();
        String password = binding.editTextPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(LoginActivity.this, "Enter your Email Address", Toast.LENGTH_SHORT).show();
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(LoginActivity.this, "Invalid Email Address", Toast.LENGTH_SHORT).show();
        } else {
            if (TextUtils.isEmpty(password)) {
                Toast.makeText(LoginActivity.this, "Enter your Password", Toast.LENGTH_SHORT).show();
            } else {
                if (SharedPref.getStringPref(LoginActivity.this, "KEY_PASSWORD").equalsIgnoreCase("EMPTY")) {
                    if (email.equalsIgnoreCase("iflair@gmail.com") || email.equalsIgnoreCase("iflair@mailinator.com") && password.equalsIgnoreCase("123456")) {
                        startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                        SharedPref.setStringPref(LoginActivity.this, "KEY_USERNAME", email);
                        SharedPref.setStringPref(LoginActivity.this, "KEY_PASSWORD", password);
                        SharedPref.setBooleanPref(LoginActivity.this, "KEY_FIRST_INSTALL", true);
                    } else {
                        Toast.makeText(this, "Wrong Username password", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.e("bbbbbb", ""+SharedPref.getStringPref(LoginActivity.this, "KEY_PASSWORD"));
                    if (email.equalsIgnoreCase("iflair@gmail.com") || email.equalsIgnoreCase("iflair@mailinator.com") && password.equalsIgnoreCase(SharedPref.getStringPref(LoginActivity.this, "KEY_PASSWORD"))) {
                        startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                        SharedPref.setStringPref(LoginActivity.this, "KEY_USERNAME", email);
                        SharedPref.setStringPref(LoginActivity.this, "KEY_PASSWORD", password);
                        SharedPref.setBooleanPref(LoginActivity.this, "KEY_FIRST_INSTALL", true);
                    } else {
                        Toast.makeText(this, "Wrong Username password", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    }
}