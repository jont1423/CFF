package com.jthomann.cff_mvvm1.model;

import com.jthomann.cff_mvvm1.BR;

import java.util.ArrayList;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

public class NewPostModel extends BaseObservable {

    private String projectName;
    private String projectDesc;
    private String lookingForDesc;

    private ArrayList<String> selectedStrings = new ArrayList<>();

    public void setSelectedStrings(ArrayList<String> selectedStrings) {
        this.selectedStrings = selectedStrings;
        notifyPropertyChanged(BR.selectedStrings);
    }

    @Bindable
    public ArrayList<String> getSelectedStrings() {

        return selectedStrings;
    }

    @Bindable
    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
        notifyPropertyChanged(BR.projectName);
    }

    @Bindable
    public String getProjectDesc() {
        return projectDesc;
    }

    public void setProjectDesc(String projectDesc) {
        this.projectDesc = projectDesc;
        notifyPropertyChanged(BR.projectDesc);
    }

    @Bindable
    public String getLookingForDesc() {
        return lookingForDesc;
    }

    public void setLookingForDesc(String lookingForDesc) {
        this.lookingForDesc = lookingForDesc;
        notifyPropertyChanged(BR.lookingForDesc);
    }
}
