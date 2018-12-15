package com.jthomann.cff_mvvm1.utils;

import java.io.Serializable;

import androidx.databinding.BaseObservable;
import androidx.databinding.BindingConversion;

public class ObservableString extends BaseObservable implements Serializable {
    static final long serialVersionUID = 1L;
    private String mValue;

    public ObservableString() {
    }

    public ObservableString(String value) {
        mValue = value;
    }

    public String get() {
        return mValue;
    }

    public void set(String value) {
        if (value == null && mValue == null) return;
        if (value == null || !value.equals(mValue)) {
            mValue = value;
            notifyChange();
        }
    }

    /** Sets the stored value without notifying of change */
    public void setSilently(String value) {
        mValue = value;
    }


    @BindingConversion
    public static String observableStringToString(ObservableString string) {
        return string.get();
    }
}