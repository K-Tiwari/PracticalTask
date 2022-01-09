package com.app.practicaltask;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.app.practicaltask.databinding.ActivityHomeBinding;
import com.google.android.material.tabs.TabLayout;

public class HomeActivity extends AppCompatActivity {

    ActivityHomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home);

        Toolbar toolbar = findViewById(R.id.appbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Practical Task");

        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Home"));
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Map"));
        binding.tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final MyAdapter adapter = new MyAdapter(this, getSupportFragmentManager(), binding.tabLayout.getTabCount());
        binding.viewPager.setAdapter(adapter);
        binding.viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(binding.tabLayout));

        binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                binding.viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_change_password:
                LayoutInflater inflater1 = (LayoutInflater) HomeActivity.this.getSystemService(HomeActivity.LAYOUT_INFLATER_SERVICE);
                final PopupWindow pw1 = new PopupWindow(inflater1.inflate(R.layout.pop_up_change_password, null), WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT, true);
                pw1.showAtLocation(HomeActivity.this.findViewById(R.id.appbar), Gravity.CENTER, 0, 0);
                TextView textViewClose1 = pw1.getContentView().findViewById(R.id.TvClose);
                final EditText edit_text_current_password, edit_text_new_password, edit_text_confirm_password;
                TextView text_view_save;

                edit_text_current_password = pw1.getContentView().findViewById(R.id.edit_text_current_password);
                edit_text_new_password = pw1.getContentView().findViewById(R.id.edit_text_new_password);
                edit_text_confirm_password = pw1.getContentView().findViewById(R.id.edit_text_confirm_password);

                text_view_save = pw1.getContentView().findViewById(R.id.text_view_save);

                text_view_save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (TextUtils.isEmpty(edit_text_current_password.getText().toString().trim())) {
                            Toast.makeText(HomeActivity.this, "Enter Current Password", Toast.LENGTH_SHORT).show();
                        } else {
                            if (TextUtils.isEmpty(edit_text_new_password.getText().toString().trim())) {
                                Toast.makeText(HomeActivity.this, "Enter New Password", Toast.LENGTH_SHORT).show();
                            } else {
                                if (edit_text_new_password.getText().toString().length() < 6) {
                                    Toast.makeText(HomeActivity.this, "Password too Short", Toast.LENGTH_SHORT).show();
                                } else {
                                    if (TextUtils.isEmpty(edit_text_confirm_password.getText().toString().trim())) {
                                        Toast.makeText(HomeActivity.this, "Please Confirm Password", Toast.LENGTH_SHORT).show();
                                    } else if (!edit_text_new_password.getText().toString().equalsIgnoreCase(edit_text_confirm_password.getText().toString())) {
                                        Toast.makeText(HomeActivity.this, "Confirm Password Does Mot Match", Toast.LENGTH_SHORT).show();
                                    } else {
                                        SharedPref.setStringPref(HomeActivity.this, "KEY_PASSWORD", edit_text_confirm_password.getText().toString());
                                        Toast.makeText(HomeActivity.this, "Password Changed Successfully", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(HomeActivity.this, LoginActivity.class));
                                        finishAffinity();
                                    }
                                }
                            }
                        }
                    }
                });

                textViewClose1.setOnClickListener(view -> pw1.dismiss());
                break;
            case R.id.menu_logout:
                startActivity(new Intent(this, LoginActivity.class));
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (!isFinishing()) {
            alertDialogExit(this);
        }
    }

    public void alertDialogExit(Context context) {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context, R.style.AlertDialog);
        builder.setTitle("Exit");
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setMessage("Do you really want to exit?");
        builder.setCancelable(false);

        builder.setPositiveButton("Yes", (dialog, id) -> {
            dialog.cancel();
            System.exit(0);
        });

        builder.setNegativeButton("No", (dialog, id) -> dialog.cancel());
        android.app.AlertDialog alert = builder.create();
        alert.show();
    }

}