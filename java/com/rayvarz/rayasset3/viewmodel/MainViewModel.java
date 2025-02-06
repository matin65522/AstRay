package com.rayvarz.rayasset3.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.fragment.app.Fragment;

import com.rayvarz.rayasset3.view.AmvalFragment;
import com.rayvarz.rayasset3.view.AstFragment;
import com.rayvarz.rayasset3.view.HomeFragment;
import com.rayvarz.rayasset3.view.ProfileFragment;
import com.rayvarz.rayasset3.R;

public class MainViewModel extends ViewModel {

    private final MutableLiveData<Fragment> selectedFragment = new MutableLiveData<>();

    public MainViewModel() {
        // مقدار پیش‌فرض به صفحه خانه
        selectedFragment.setValue(new HomeFragment());
    }

    public LiveData<Fragment> getSelectedFragment() {
        return selectedFragment;
    }

    public void onNavigationItemSelected(int itemId) {
        Fragment fragment = null;

        if (itemId == R.id.mHome) {
            fragment = new HomeFragment();
        } else if (itemId == R.id.mSearch) {
            fragment = new AmvalFragment();
        } else if (itemId == R.id.mPerson) {
            fragment = new AstFragment();
        } else if (itemId == R.id.mSetting) {
            fragment = new ProfileFragment();
        }

        if (fragment != null) {
            selectedFragment.setValue(fragment);
        }
    }
}
