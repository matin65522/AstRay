package com.rayvarz.rayasset3.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.rayvarz.rayasset3.R;
import com.rayvarz.rayasset3.databinding.ActivityMainBinding;
import com.rayvarz.rayasset3.viewmodel.MainViewModel;

public class MainActivity extends AppCompatActivity {

    private MainViewModel viewModel;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(this);

        viewModel.getSelectedFragment().observe(this, fragment ->
                getSupportFragmentManager().beginTransaction().replace(R.id.framecontainer, fragment).commit()
        );

        binding.bottomnavigationbar.setOnItemSelectedListener(item -> {
            viewModel.onNavigationItemSelected(item.getItemId());
            return true;
        });
    }
}
