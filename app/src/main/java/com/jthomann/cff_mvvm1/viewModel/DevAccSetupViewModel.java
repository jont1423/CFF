package com.jthomann.cff_mvvm1.viewModel;

import android.view.View;
import android.widget.AdapterView;

import com.jthomann.cff_mvvm1.interfaces.Observer;
import com.jthomann.cff_mvvm1.model.SpinnerModel;
import com.jthomann.cff_mvvm1.repository.UserRepo;
import com.jthomann.cff_mvvm1.utils.MyUtils;

import java.util.ArrayList;

import androidx.databinding.BaseObservable;

public class DevAccSetupViewModel extends BaseObservable {

    public String[] operatingSystems;
    public String[] languages;
    private ArrayList<Observer> observers;
    private SpinnerModel spinnerModel;
    private UserRepo repository;

    public DevAccSetupViewModel(String[] operatingSystems, String[] languages, SpinnerModel spinnerModel) {
        this.operatingSystems = operatingSystems;
        this.spinnerModel = spinnerModel;
        this.languages = languages;
        observers = new ArrayList<>();
        repository = new UserRepo(spinnerModel, observers);
    }

    public void onSelectOsItem(AdapterView<?> parent, View view, int pos, long id) {
        spinnerModel.setSelectedOS(String.valueOf(parent.getSelectedItem()));

        notifyObservers(MyUtils.SHOW_TOAST, spinnerModel.getSelectedOS());

    }

    public void onSelectCountryItem(AdapterView<?> parent, View view, int pos, long id) {
        spinnerModel.setCountry(String.valueOf(parent.getSelectedItem()));

        notifyObservers(MyUtils.SHOW_TOAST, spinnerModel.getCountry());
    }

    public void saveUserInformation(String username) {
        repository.saveUserInformation(username);
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
