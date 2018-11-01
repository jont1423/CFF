package com.jthomann.cff_mvvm1.viewModel;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.jthomann.cff_mvvm1.databinding.ActivitySignUpBinding;

import androidx.annotation.NonNull;
import androidx.databinding.BaseObservable;

public class SignUpViewModel extends BaseObservable {

    public Context mContext;

    public SignUpViewModel(Context context){

        mContext = context;

    }

    public void firebaseSignUp(String email, String pass, String confirm_pass){

        if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(pass) && !TextUtils.isEmpty(confirm_pass)) {
            if (android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                if (pass.length() > 6) {
                    if (pass.equals(confirm_pass)) {

                        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, pass)
                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {

                                        if (task.isSuccessful()) {

                                        } else {

                                        }
                                    }
                                });
                    }
                }
            }
        }
    }

}
