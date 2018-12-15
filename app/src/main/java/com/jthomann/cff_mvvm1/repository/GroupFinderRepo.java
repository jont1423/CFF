package com.jthomann.cff_mvvm1.repository;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.jthomann.cff_mvvm1.interfaces.Observer;
import com.jthomann.cff_mvvm1.model.NewPostModel;
import com.jthomann.cff_mvvm1.utils.MyUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import androidx.databinding.BaseObservable;

public class GroupFinderRepo extends BaseObservable {

    private DatabaseReference userRef;
    private FirebaseAuth mAuth;
    private ArrayList<Observer> observers;
    private String currentUserID;
    private NewPostModel newPostModel;

    public GroupFinderRepo(NewPostModel newPostModel){
        this.newPostModel = newPostModel;

        mAuth = FirebaseAuth.getInstance();
        observers = new ArrayList<>();
    }

    public void uploadNewPost(){
        mAuth = FirebaseAuth.getInstance();
        currentUserID = Objects.requireNonNull(mAuth.getCurrentUser().getUid());
        userRef = FirebaseDatabase.getInstance().getReference().child("NewPost").child(currentUserID);

        HashMap postMap = new HashMap();
        postMap.put("project_name", newPostModel.getProjectName());
        postMap.put("project_desc", newPostModel.getProjectDesc());
        postMap.put("looking_for_desc", newPostModel.getLookingForDesc());

        userRef.updateChildren(postMap).addOnCompleteListener(task -> {
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
