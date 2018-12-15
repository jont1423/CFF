package com.jthomann.cff_mvvm1.viewModel;

import com.jthomann.cff_mvvm1.interfaces.Observer;
import com.jthomann.cff_mvvm1.model.NewPostModel;
import com.jthomann.cff_mvvm1.repository.GroupFinderRepo;

import java.util.ArrayList;

public class NewPostViewModel {

    private ArrayList<Observer> observers;
    private GroupFinderRepo groupFinderRepo;

    public NewPostViewModel(NewPostModel newPostModel){
        observers = new ArrayList<>();

        groupFinderRepo = new GroupFinderRepo(newPostModel);
    }

    public void saveNewPost(){
        groupFinderRepo.uploadNewPost();
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
