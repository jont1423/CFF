package com.jthomann.cff_mvvm1.viewModel;

import android.text.Editable;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.jthomann.cff_mvvm1.interfaces.Observer;
import com.jthomann.cff_mvvm1.utils.MyUtils;
import com.jthomann.cff_mvvm1.utils.ObservableString;

import java.util.ArrayList;

import androidx.databinding.BaseObservable;

public class LoginViewModel extends BaseObservable {

    public ObservableString password = new ObservableString("");
    public ObservableString email = new ObservableString("");
    private ArrayList<Observer> observers;


    public LoginViewModel() {
        observers = new ArrayList<>();
    }

    public void firebaseEmailAndPasswordAuth() {

        FirebaseAuth.getInstance().signInWithEmailAndPassword(email.get(), password.get())
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        notifyObservers(MyUtils.SHOW_TOAST, MyUtils.MESSAGE_AUTHENTICATION_FAILED);
                    }
                });
    }

    public void onPasswordChanged(Editable e) {
        password.setSilently(e.toString());
    }

    public void onEmailChanged(Editable e) {
        email.setSilently(e.toString());
    }

    public void onEmailTextChanged(CharSequence s, int start, int before, int count) {
        Log.d("Test", "Email text changed. Now: " + s);
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
