package com.jthomann.cff_mvvm1.model;

import android.widget.Spinner;

import com.jthomann.cff_mvvm1.BR;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.InverseBindingMethod;
import androidx.databinding.InverseBindingMethods;

@InverseBindingMethods({
        @InverseBindingMethod(type = Spinner.class, attribute = "android:selectedItemPosition"),
})

public class SpinnerModel extends BaseObservable
{

    private String selectedOS;
    private String selectedLang;
    private String[] countries;
    private int countryPos;
    private String country;
    private ArrayList<String> selectedStrings = new ArrayList<>();

    public void setSelectedStrings(ArrayList<String> selectedStrings) {
        this.selectedStrings = selectedStrings;
        notifyPropertyChanged(BR.selectedStrings);
    }

    @Bindable
    public ArrayList<String> getSelectedStrings() {

        return selectedStrings;
    }

    public SpinnerModel() {
        List<String> allCountries = new ArrayList<>();
        String[] locales = Locale.getISOCountries();

        for (String countryCode : locales) {

            Locale obj = new Locale("", countryCode);

            allCountries.add(obj.getDisplayCountry());

        }

        countries = allCountries.toArray(new String[0]);
    }

    @Bindable
    public String[] getCountries() {
        return countries;
    }

    public void setCountries(String[] countries) {
        this.countries = countries;
        notifyPropertyChanged(BR.countries);
    }

    @Bindable
    public int getCountryPos() {
        return countryPos;
    }

    public void setCountryPos(int countryPos) {
        this.countryPos = countryPos;

        setCountry(countries[countryPos]);
    }

    public int getCountryPos(Spinner spinner) {
        return spinner.getSelectedItemPosition();
    }

    @Bindable
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
        notifyPropertyChanged(BR.country);
    }

    public String getSelectedOS() {
        return selectedOS;
    }

    public void setSelectedOS(String selectedOS) {
        this.selectedOS = selectedOS;
    }

    public String getSelectedLang() {
        return selectedLang;
    }

    public void setSelectedLang(String selectedLang) {
        this.selectedLang = selectedLang;
    }


}
