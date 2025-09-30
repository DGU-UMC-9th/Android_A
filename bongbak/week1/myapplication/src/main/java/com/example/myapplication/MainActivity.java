package com.example.myapplication;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.myapplication.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(binding.getRoot());

        // 각 버튼에 OnClickListener 설정
        binding.btn2.setOnClickListener(this);
        binding.btn3.setOnClickListener(this);
        binding.btn5.setOnClickListener(this);
        binding.btn7.setOnClickListener(this);
        binding.btn8.setOnClickListener(this);

        // --- BottomNavigationView 설정 코드 추가 ---
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);
    }

    @Override
    public void onClick(View v) {
        resetTextColors();

        switch (v.getId()) {
            case R.id.btn2:
                binding.txt3.setTextColor(Color.YELLOW);
                break;
            case R.id.btn3:
                binding.txt4.setTextColor(Color.BLUE);
                break;
            case R.id.btn5:
                binding.txt5.setTextColor(Color.rgb(128, 0, 128));
                break;
            case R.id.btn7:
                binding.txt2.setTextColor(Color.GREEN);
                break;
            case R.id.btn8:
                binding.txt2.setTextColor(Color.RED);
                break;
        }
    }

    private void resetTextColors() {
        int defaultColor = ContextCompat.getColor(this, android.R.color.black);
        binding.txt2.setTextColor(defaultColor);
        binding.txt3.setTextColor(defaultColor);
        binding.txt4.setTextColor(defaultColor);
        binding.txt5.setTextColor(defaultColor);
    }
}