package com.rsschool.android2021;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.rsschool.android2021.fragments.FirstFragment;
import com.rsschool.android2021.fragments.SecondFragment;
import com.rsschool.android2021.fragments.ThirdFragment;
import com.rsschool.android2021.interfaces.IBackActivity;
import com.rsschool.android2021.interfaces.IFragmentsActions;

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

    private void openThirdFragment(int min, int max, int count){
        transitFragment(ThirdFragment.newInstance(min, max, count));
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
    public void sendCountOfResults(int minValue, int maxValue, int count) {
        openThirdFragment(minValue, maxValue, count);
    }

    @Override
    public void onBackPressed() {
        if (!back.isMayBackPrevious()){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setIcon(android.R.drawable.ic_dialog_alert);
            builder.setTitle(R.string.titleAlertDialog);
            builder.setMessage(R.string.messageAlertDialog);

            builder.setPositiveButton(R.string.positiveButton,
                    (dialog, which) -> finish());

            builder.setNegativeButton(R.string.negativeButton,
                    (dialog, which) -> {
                        Toast.makeText(this, R.string.canceledMessage,
                                Toast.LENGTH_SHORT).show();
                    });

            AlertDialog alertDialog = builder.create();
            alertDialog.show();

            Button positiveButton = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
            positiveButton.setTextColor(Color.RED);
            Button negativeButton = alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE);
            negativeButton.setTextColor(Color.GRAY);
        }
    }
}