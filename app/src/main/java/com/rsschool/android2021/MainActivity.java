package com.rsschool.android2021;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class MainActivity extends AppCompatActivity implements IFragmentsActions {
    private IBackActivity back;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        openFirstFragment(0);
    }

    private void openFirstFragment(int previousNumber) {
        transitFragment(FirstFragment.newInstance(previousNumber));
    }

    private void openSecondFragment(int min, int max) {
        transitFragment(SecondFragment.newInstance(min, max));
    }

    private void transitFragment(Fragment fragment){
        getSupportFragmentManager().beginTransaction().
                replace(R.id.container, fragment).commit();
         back = (IBackActivity) fragment;
    }

    @Override
    public void sendPreviousNumber(int previousResult) {
        openFirstFragment(previousResult);
    }

    @Override
    public void sendMinAndMaxValues(int minValue, int maxValue) {
        openSecondFragment(minValue, maxValue);
    }

    @Override
    public void onBackPressed() {
        if (!back.isMayBackPrevious())
            super.onBackPressed();
    }
}