package com.jthomann.cff_mvvm1.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.jthomann.cff_mvvm1.R;
import com.jthomann.cff_mvvm1.databinding.ActivityLoginBinding;
import com.jthomann.cff_mvvm1.interfaces.Observer;
import com.jthomann.cff_mvvm1.utils.MyUtils;
import com.jthomann.cff_mvvm1.viewModel.LoginViewModel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

public class LoginActivity extends AppCompatActivity implements Observer<String> {

    private LoginViewModel loginViewModel;
    private FirebaseAuth.AuthStateListener authStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityLoginBinding loginBinding = DataBindingUtil.setContentView(this,
                R.layout.activity_login);

        loginViewModel = new LoginViewModel();
        loginBinding.setVModel(loginViewModel);
        loginBinding.setActivity(this);
        loginBinding.executePendingBindings();

    }

    public void login() {

        loginViewModel.firebaseEmailAndPasswordAuth();

        authStateListener = auth -> {
            FirebaseUser user = auth.getCurrentUser();
            if (user != null) {
                Intent sendToMain = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(sendToMain);
                finish();
            }
        };

        FirebaseAuth.getInstance().addAuthStateListener(authStateListener);
    }

    public void startSignUpActivity() {
        Intent sendToSignUp = new Intent(this, SignUpActivity.class);
        startActivity(sendToSignUp);
    }

    @Override
    protected void onStop() {
        super.onStop();
        FirebaseAuth.getInstance().removeAuthStateListener(authStateListener);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loginViewModel.addObserver(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        loginViewModel.removeObserver(this);
    }

    @Override
    public void onObserve(int event, String eventMessage) {

        if (event == MyUtils.SHOW_TOAST)
            Toast.makeText(this, eventMessage, Toast.LENGTH_SHORT).show();
    }
}
