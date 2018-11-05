package com.jthomann.cff_mvvm1.viewModel;

import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.jthomann.cff_mvvm1.interfaces.Observer;
import com.jthomann.cff_mvvm1.model.SpinnerModel;
import com.jthomann.cff_mvvm1.model.User;
import com.jthomann.cff_mvvm1.utils.MyUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;

import androidx.databinding.BaseObservable;

public class DevAccSetupViewModel extends BaseObservable {

    public String[] operatingSystems;
    public String[] languages;
    private ArrayList<Observer> observers;
    private SpinnerModel spinnerModel;
    private User userModel;
    private FirebaseAuth mAuth;
    private DatabaseReference userRef;
    private String currentUserID;

    final String accountType = "developer";

//    private String username = "";

    public DevAccSetupViewModel(String[] operatingSystems, String[] languages, SpinnerModel spinnerModel) {
        this.operatingSystems = operatingSystems;
        this.spinnerModel = spinnerModel;
        this.languages = languages;
//        userModel = new User();
//        username = userModel.getUsername();
        observers = new ArrayList<>();
    }

    public void onSelectOsItem(AdapterView<?> parent, View view, int pos, long id) {
        spinnerModel.setSelectedOS(String.valueOf(parent.getSelectedItem()));

        notifyObservers(MyUtils.SHOW_TOAST, spinnerModel.getSelectedOS());

    }

    public void onSelectCountryItem(AdapterView<?> parent, View view, int pos, long id) {
        spinnerModel.setCountry(String.valueOf(parent.getSelectedItem()));

        notifyObservers(MyUtils.SHOW_TOAST, spinnerModel.getCountry());
    }

//    public void onSelectLanguageItem(AdapterView<?> parent, View view, int pos, long id) {
//        spinnerModel.setSelectedLang(String.valueOf(parent.getSelectedItem()));
//
//        notifyObservers(MyUtils.SHOW_TOAST, spinnerModel.getSelectedLang());
//    }

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

//    public void onUsernameChanged(Editable e) {
//        username.setSilently(e.toString());
//    }
//      public void onUsernameTextChanged(CharSequence s, int start, int before, int count){
//           Log.d("Test", "user text changed now: " + s);
//    }

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
