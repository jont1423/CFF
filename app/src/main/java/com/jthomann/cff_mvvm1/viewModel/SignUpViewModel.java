package com.jthomann.cff_mvvm1.viewModel;

import android.text.TextUtils;

import com.google.firebase.auth.FirebaseAuth;
import com.jthomann.cff_mvvm1.interfaces.Observer;
import com.jthomann.cff_mvvm1.utils.MyUtils;

import java.util.ArrayList;

import androidx.databinding.BaseObservable;

public class SignUpViewModel extends BaseObservable {

    private ArrayList<Observer> observers;

    public SignUpViewModel() {
        observers = new ArrayList<>();
    }

    public void firebaseSignUp(String email, String password, String confPassword) {

        if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password) && !TextUtils.isEmpty(confPassword)) {
            if (android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                if (password.length() > 6) {
                    if (password.equals(confPassword)) {

                        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                                .addOnCompleteListener(task -> {

                                    if (!task.isSuccessful())
                                        notifyObservers(MyUtils.SHOW_TOAST, MyUtils.MESSAGE_AUTHENTICATION_FAILED);
                                });
                    }
                }
            }
        }
    }

    public void addObserver(Observer client) {
        if (!observers.contains(client)) {
            observers.add(client);
        }
    }

    public void removeObserver(Observer clientToRemove) {

        observers.remove(clientToRemove);
    }

    private void notifyObservers(int eventType, String message) {
        for (int i = 0; i < observers.size(); i++) {
            observers.get(i).onObserve(eventType, message);
        }
    }

}
