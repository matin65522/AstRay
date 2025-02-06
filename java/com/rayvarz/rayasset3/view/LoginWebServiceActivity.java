package com.rayvarz.rayasset3.view;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.rayvarz.rayasset3.databinding.ActivityLoginWebserviceBinding;
import com.rayvarz.rayasset3.viewmodel.LoginWebServiceViewModel;

public class LoginWebServiceActivity extends AppCompatActivity {

    private ActivityLoginWebserviceBinding binding;
    private LoginWebServiceViewModel loginViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // مقداردهی ViewModel
        loginViewModel = new ViewModelProvider(this).get(LoginWebServiceViewModel.class);

        // مقداردهی DataBinding
        binding = ActivityLoginWebserviceBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        // اتصال ViewModel به DataBinding
        binding.setViewModel(loginViewModel);
        binding.setLifecycleOwner(this);

        // نظارت بر LiveData
        loginViewModel.navigateToLogin.observe(this, navigate -> {
            // بررسی اینکه مقدار LiveData true باشد
            if (navigate != null && navigate) {
                // هدایت به صفحه ورود
                startActivity(new  Intent(LoginWebServiceActivity.this, LoginActivity.class));
                finish();

                // بعد از هدایت مقدار LiveData به false تغییر می‌کند
                loginViewModel.onNavigationComplete();
            }
        });
    }
}
