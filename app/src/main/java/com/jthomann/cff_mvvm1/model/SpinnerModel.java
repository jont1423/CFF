package com.jthomann.cff_mvvm1.model;

import com.jthomann.cff_mvvm1.BR;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

public class Spinner extends BaseObservable
{

    private String selectedOS;
    private String selectedCountry;
    private String selectedLanguage;
    private int selectedOsPos;
    private int selectedCountryPos;
    private int selectedLanguagePos;

    public String getSelectedOS() {
        return selectedOS;
    }

    public void setSelectedOS(String selectedOS) {
        this.selectedOS = selectedOS;
    }

    public String getSelectedCountry() {
        return selectedCountry;
    }

    public void setSelectedCountry(String selectedCountry) {
        this.selectedCountry = selectedCountry;
    }

    public String getSelectedLanguage() {
        return selectedLanguage;
    }

    public void setSelectedLanguage(String selectedLanguage) {
        this.selectedLanguage = selectedLanguage;
    }

    @Bindable
    public int getSelectedOsPos() {
        return selectedOsPos;
    }

    public void setSelectedOsPos(int selectedOsPos) {
        this.selectedOsPos = selectedOsPos;
        notifyPropertyChanged(BR.selectedOsPos);
    }

    public int getSelectedCountryPos() {
        return selectedCountryPos;
    }

    public void setSelectedCountryPos(int selectedCountryPos) {
        this.selectedCountryPos = selectedCountryPos;
    }

    @Bindable
    public int getSelectedLanguagePos() {
        return selectedLanguagePos;
    }

    public void setSelectedLanguagePos(int selectedLanguagePos) {
        this.selectedLanguagePos = selectedLanguagePos;
        notifyPropertyChanged(BR.selectedLanguagePos);
    }
}
