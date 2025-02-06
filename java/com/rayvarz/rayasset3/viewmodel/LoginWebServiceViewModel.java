package com.rayvarz.rayasset3.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class LoginWebServiceViewModel extends ViewModel {

    public MutableLiveData<String> webServicePath = new MutableLiveData<>("");


    private final MutableLiveData<Boolean> _navigateToLogin = new MutableLiveData<>();
    public LiveData<Boolean> navigateToLogin = _navigateToLogin;

/*    public void onLoginClicked(){
        startActivity( new Intent(WebServiceActivity.this, LoginActivity.class));
    }*/

    public void onLoginClicked() {
        Log.d("webservice", "onLoginClicked called");
        // بررسی و اعتبارسنجی مسیر وب‌سرویس
        if (webServicePath.getValue() != null && !webServicePath.getValue().isEmpty()) {
            _navigateToLogin.setValue(true);
        }
    }

    public void onContactUsClicked() {
        // مدیریت کلیک روی دکمه "ارتباط با ما"
    }

    public void onNavigationComplete() {
        _navigateToLogin.setValue(false);
    }
}
