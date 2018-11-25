package com.jthomann.cff_mvvm1.viewModel;

import com.jthomann.cff_mvvm1.interfaces.Observer;

import java.util.ArrayList;

public class NewPostViewModel {

    private ArrayList<Observer> observers;

    public NewPostViewModel(){
        observers = new ArrayList<>();
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
