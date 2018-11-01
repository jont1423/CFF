package com.jthomann.cff_mvvm1.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;

import com.jthomann.cff_mvvm1.R;
import com.jthomann.cff_mvvm1.databinding.ActivitySignUpBinding;
import com.jthomann.cff_mvvm1.viewModel.SignUpViewModel;

public class SignUpActivity extends AppCompatActivity {

    private ActivitySignUpBinding signUpBinding;
    private SignUpViewModel signUpViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        signUpBinding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up);
        signUpViewModel = new SignUpViewModel(this);


    }

    public void toSignIn(){
        Intent toSignIn = new Intent(this, LoginActivity.class);
        startActivity(toSignIn);
        finish();
    }
}
