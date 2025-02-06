package com.rayvarz.rayasset3.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.rayvarz.rayasset3.R;
import com.rayvarz.rayasset3.databinding.ActivityAstDscBinding;

import org.jetbrains.annotations.NotNull;

import java.util.Random;

import ir.hamsaa.persiandatepicker.PersianDatePickerDialog;
import ir.hamsaa.persiandatepicker.api.PersianPickerDate;
import ir.hamsaa.persiandatepicker.api.PersianPickerListener;

public class AstDscActivity extends AppCompatActivity {
    private ActivityAstDscBinding binding;
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // set binding
        binding = ActivityAstDscBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // toolbar back button
        setSupportActionBar(binding.toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.baseline_arrow_back_ios_24);
            getSupportActionBar().setTitle("مشخصات دارایی");
        }

        // Asset Id From Intent
        Intent intent = getIntent();
        if (intent.hasExtra("idNo")) {
            String idNo = intent.getStringExtra("idNo");


            // set Asset Id
            binding.txtAstTitle.setText(idNo);
        }

        // select طبقه بندی onclick
        binding.txtAstCategory.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                showBottomSheet(binding.txtAstCategory);
                return true;
            }
            return false;
        });
        // select دارایی مادر onclick
        binding.txtAstParent.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                showBottomSheet(binding.txtAstParent);
                return true;
            }
            return false;
        });

        // generate random No.Num
        binding.inpNoNum.setEndIconOnClickListener(v ->{
            Random random = new Random();
            int randomNumber = 10000000 + random.nextInt(90000000);
            binding.txtNoNum.setText(String.valueOf(randomNumber));
        });

        // DatePicker CrtDate
        binding.inpDateCrt.setEndIconOnClickListener(v ->{
            DatePicker(binding.txtDateCrt);
        });

        // DatePicker EditDate
        binding.inpDateEdit.setEndIconOnClickListener(v ->{
            DatePicker(binding.txtDateEdit);
        });
    }

    // show bottom sheet
    private void showBottomSheet(TextInputEditText txtAstParent) {
        BS_AstCategory bottomSheet = new BS_AstCategory();
        bottomSheet.setBottomSheetListener(option -> {
            txtAstParent.setText(option);
            Toast.makeText(AstDscActivity.this, "شما " + option + " را انتخاب کردید.", Toast.LENGTH_SHORT).show();
        });

        bottomSheet.show(getSupportFragmentManager(), "BS_AstCategory");
    }

    // DatePicker Void
    private void DatePicker(TextInputEditText input_datepicker){
        PersianDatePickerDialog picker = new PersianDatePickerDialog(this)
                .setPositiveButtonString("تایید")
                .setNegativeButton("بیخیال")
                .setTodayButton("امروز")
                .setTodayButtonVisible(true)
                .setMinYear(1300)
                .setMaxYear(PersianDatePickerDialog.THIS_YEAR)
                .setMaxMonth(PersianDatePickerDialog.THIS_MONTH)
                .setMaxDay(PersianDatePickerDialog.THIS_DAY)
                .setInitDate(1403, 3, 13)
                .setActionTextColor(Color.GRAY)
                .setTitleType(PersianDatePickerDialog.WEEKDAY_DAY_MONTH_YEAR)
                .setShowInBottomSheet(true)
                .setListener(new PersianPickerListener() {
                    @Override
                    public void onDateSelected(@NotNull PersianPickerDate persianPickerDate) {
                        input_datepicker.setText(String.valueOf(persianPickerDate.getPersianYear() + "/" + persianPickerDate.getPersianMonth() + "/" + persianPickerDate.getPersianDay()));

                    }

                    @Override
                    public void onDismissed() {

                    }
                });

        picker.show();
    }

    // toolbar back button
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
