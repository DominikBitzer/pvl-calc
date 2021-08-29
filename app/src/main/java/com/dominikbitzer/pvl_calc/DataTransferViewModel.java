package com.dominikbitzer.pvl_calc;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DataTransferViewModel extends ViewModel {

    // Tracks the score for Team A
    public String scoreTeamA = "0";

    // Tracks the score for Team B
    public int scoreTeamB = 0;

}
