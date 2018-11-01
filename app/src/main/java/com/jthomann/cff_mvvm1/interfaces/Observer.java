package com.jthomann.cff_mvvm1.interfaces;

public interface Observer<T>
{
        void onObserve(int event, T eventMessage);
}
