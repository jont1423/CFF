package com.jthomann.cff_mvvm1.model;

import com.jthomann.cff_mvvm1.BR;

import java.util.ArrayList;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

public class NewPostModel extends BaseObservable {

    private ArrayList<String> selectedStrings = new ArrayList<>();


    public void setSelectedStrings(ArrayList<String> selectedStrings) {
        this.selectedStrings = selectedStrings;
        notifyPropertyChanged(BR.selectedStrings);
    }

    @Bindable
    public ArrayList<String> getSelectedStrings() {

        return selectedStrings;
    }
}
