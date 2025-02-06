package com.rayvarz.rayasset3.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class LoginViewModel extends ViewModel {

    public MutableLiveData<String> username = new MutableLiveData<>("");
    public MutableLiveData<String> usernameError = new MutableLiveData<>("");

    public MutableLiveData<String> password = new MutableLiveData<>("");
    public MutableLiveData<String> passwordError = new MutableLiveData<>("");

    private final MutableLiveData<Boolean> _navigateToMain = new MutableLiveData<>();
    public LiveData<Boolean> navigateToMain = _navigateToMain;

    private final MutableLiveData<String> _selectedBranch = new MutableLiveData<>("");
    public LiveData<String> selectedBranch = _selectedBranch;

    private final MutableLiveData<Void> bottomSheetEvent = new MutableLiveData<>();
    public LiveData<Void> getBottomSheetEvent() {
        return bottomSheetEvent;
    }

    //  Btn Check Login
    public void onLoginClicked() {
        Log.d("LoginViewModel", "Login button clicked");

        // Check username and password
        if ("1".equals(username.getValue()) && "1".equals(password.getValue())) {
            Log.d("LoginViewModel", "Login successful");
            _navigateToMain.setValue(true);
        } else {
            _navigateToMain.setValue(false);
            Log.d("LoginViewModel", "Login failed");
        }
    }

    //    Btn Select Branch
    public void onBranchClicked() {
        bottomSheetEvent.setValue(null);
        Log.d("LoginViewModel", "Branch selection triggered");
    }

    //    set Branch
    public void setSelectedBranch(String branch) {
        _selectedBranch.setValue(branch);
        Log.d("LoginViewModel", "Branch selected: " + branch);
    }

    //    public void onNavigationComplete() {
    //        _navigateToMain.setValue(false);
    //    }
}
