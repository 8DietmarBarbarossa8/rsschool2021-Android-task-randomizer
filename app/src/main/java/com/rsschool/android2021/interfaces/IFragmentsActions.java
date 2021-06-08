package com.rsschool.android2021.interfaces;

public interface IFragmentsActions {
    void sendPreviousNumber(int previousResult);
    void sendMinAndMaxValues(int minValue, int maxValue);
    void sendCountOfResults(int minValue, int maxValue, int count);
}