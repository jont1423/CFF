package com.jthomann.cff_mvvm1.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.jthomann.cff_mvvm1.R;
import com.jthomann.cff_mvvm1.databinding.ActivitySignUpBinding;
import com.jthomann.cff_mvvm1.interfaces.Observer;
import com.jthomann.cff_mvvm1.model.User;
import com.jthomann.cff_mvvm1.utils.MyUtils;
import com.jthomann.cff_mvvm1.viewModel.SignUpViewModel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

public class SignUpActivity extends AppCompatActivity implements Observer<String> {

    private SignUpViewModel signUpViewModel;
    private FirebaseAuth.AuthStateListener authStateListener;
    private User user = new User();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivitySignUpBinding signUpBinding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up);

        signUpViewModel = new SignUpViewModel();
        signUpBinding.setVModel(signUpViewModel);
        signUpBinding.setActivity(this);
        signUpBinding.setUserModel(user);
        signUpBinding.executePendingBindings();

    }

    public void signUp() {
        signUpViewModel.firebaseSignUp(user.getEmail(), user.getPassword(), user.getConfPassword());

        authStateListener = auth -> {
            FirebaseUser firebaseUser = auth.getCurrentUser();
            if (firebaseUser != null) {
                Intent sentToChoose = new Intent(SignUpActivity.this, ChooseTypeActivity.class);
                startActivity(sentToChoose);
                finish();
            }
        };
        FirebaseAuth.getInstance().addAuthStateListener(authStateListener);
    }

    @Override
    protected void onResume() {
        super.onResume();

        signUpViewModel.addObserver(this);
    }

    @Override
    protected void onPause() {
        super.onPause();

        signUpViewModel.removeObserver(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        FirebaseAuth.getInstance().removeAuthStateListener(authStateListener);
    }

    public void toSignIn(){
        Intent toSignIn = new Intent(this, LoginActivity.class);
        startActivity(toSignIn);
        finish();
    }

    @Override
    public void onObserve(int event, String eventMessage) {
        if (event == MyUtils.SHOW_TOAST)
            Toast.makeText(this, eventMessage, Toast.LENGTH_SHORT).show();

    }
}
