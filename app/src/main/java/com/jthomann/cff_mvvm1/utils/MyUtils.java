package com.jthomann.cff_mvvm1.utils;

import android.util.Patterns;

public class MyUtils {
    public static String MESSAGE_AUTHENTICATION_FAILED = "Firebase authentication failed, please check your Email or Password.";

    public static final int OPEN_ACTIVITY = 1;
    public static final int SHOW_TOAST = 2;
    public static final int UPDATE_MESSAGES=1;

    public static boolean isEmailValid(String email){
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }


}
