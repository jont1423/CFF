package com.jthomann.cff_mvvm1.repository;

import android.text.TextUtils;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.jthomann.cff_mvvm1.interfaces.Observer;
import com.jthomann.cff_mvvm1.model.SpinnerModel;
import com.jthomann.cff_mvvm1.utils.MyUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;

import androidx.databinding.BaseObservable;

public class UserRepo extends BaseObservable {

    private DatabaseReference userRef;
    private FirebaseAuth mAuth;
    private ArrayList<Observer> observers;
    private String currentUserID;
    private SpinnerModel spinnerModel;

    final String accountType = "developer";

    public UserRepo(){
        mAuth = FirebaseAuth.getInstance();
        observers = new ArrayList<>();
    }

    public UserRepo(SpinnerModel spinnerModel, ArrayList<Observer> observers){
        this.spinnerModel = spinnerModel;
        mAuth = FirebaseAuth.getInstance();
        this.observers = observers;
    }

    public void saveUserInformation(String username) {
        mAuth = FirebaseAuth.getInstance();
        currentUserID = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
        userRef = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserID);

        if (spinnerModel.getCountry().isEmpty()) {
            notifyObservers(MyUtils.SHOW_TOAST, MyUtils.SAVE_INFO_ORIGIN_EMPTY);
        } else if (TextUtils.isEmpty(spinnerModel.getSelectedOS())) {
            notifyObservers(MyUtils.SHOW_TOAST, MyUtils.SAVE_INFO_OPERATING_SYSTEM_EMPTY);
        } else if (TextUtils.isEmpty(username)) {
            notifyObservers(MyUtils.SHOW_TOAST, MyUtils.SAVE_INFO_USERNAME_EMPTY);
        } else if (TextUtils.isEmpty(Arrays.toString(spinnerModel.getSelectedStrings().toArray()))) {
            notifyObservers(MyUtils.SHOW_TOAST, MyUtils.SAVE_INFO_P_LANG_EMPTY);
        } else {
            HashMap userMap = new HashMap();
            userMap.put("username", username);
            userMap.put("country", spinnerModel.getCountry());
            userMap.put("operating_system", spinnerModel.getSelectedOS());
            userMap.put("account_type", accountType);
            userMap.put("languages", spinnerModel.getSelectedStrings());
            userMap.put("dob", "set up later");


            userRef.updateChildren(userMap).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
//                    userRef.setValue(spinnerModel.getSelectedStrings());
                    notifyObservers(MyUtils.SHOW_TOAST, MyUtils.USER_INFO_STORED);
                    notifyObservers(MyUtils.OPEN_ACTIVITY, "hi");
                } else {
                    String message = task.getException().getMessage();
                    notifyObservers(MyUtils.SHOW_TOAST, message);
                }
            });
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
