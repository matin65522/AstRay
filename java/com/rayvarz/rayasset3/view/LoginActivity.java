package com.rayvarz.rayasset3.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.rayvarz.rayasset3.databinding.ActivityLoginBinding;
import com.rayvarz.rayasset3.viewmodel.LoginViewModel;

public class LoginActivity extends AppCompatActivity {

    private LoginViewModel viewModel;
    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(this);

        observeViewModel();
    }

    private void observeViewModel() {
        // Navigate to MainActivity when login is successful
        viewModel.navigateToMain.observe(this, success -> {
            if (success) {
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                // viewModel.onNavigationComplete();
                finish();
            } else {
                viewModel.usernameError.setValue("نام کاربری نادرست است");
                viewModel.passwordError.setValue("رمز عبور نادرست است");
            }
        });

        // Observe bottom sheet trigger event
        viewModel.getBottomSheetEvent().observe(this, unused -> showBottomSheet());

        // Observe selected branch
        viewModel.selectedBranch.observe(this, branch -> {
            binding.textField.setText(branch);
            Toast.makeText(this, "شما " + branch + " را انتخاب کردید.", Toast.LENGTH_SHORT).show();
        });
    }

    private void showBottomSheet() {
        BS_branch bottomSheet = new BS_branch();
        bottomSheet.setBottomSheetListener(option -> viewModel.setSelectedBranch(option));
        bottomSheet.show(getSupportFragmentManager(), "MyBottomSheet");
    }
}
