package com.jthomann.cff_mvvm1.viewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jthomann.cff_mvvm1.interfaces.Observer;
import com.jthomann.cff_mvvm1.utils.MyUtils;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.databinding.BaseObservable;

public class MainViewModel extends BaseObservable {

    private DatabaseReference usersRef;
    private FirebaseAuth mAuth;
    private ArrayList<Observer> observers;


    public MainViewModel(){
        mAuth = FirebaseAuth.getInstance();
        usersRef = FirebaseDatabase.getInstance().getReference().child("Users");
        observers = new ArrayList<>();

    }


        public void checkUserExistence() {

        final String current_user_id = FirebaseAuth.getInstance().getCurrentUser().getUid();

        usersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (!snapshot.hasChild(current_user_id)) {

                    notifyObservers(MyUtils.OPEN_ACTIVITY, "");
                    notifyObservers(MyUtils.SHOW_TOAST, "Account must be setup before continuing.");

//                    Toast.makeText(MainActivity.this, "Please setup your account before using the app.",
//                            Toast.LENGTH_LONG).show();
                } else {
                    notifyObservers(MyUtils.SHOW_TOAST, "UserRepo exists");
//                    Toast.makeText(MainActivity.this, "UserRepo exists", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                notifyObservers(MyUtils.SHOW_TOAST, error.getMessage());

            }
        });


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
