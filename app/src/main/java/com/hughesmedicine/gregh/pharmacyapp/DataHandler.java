package com.hughesmedicine.gregh.pharmacyapp;

import android.content.Context;
import android.support.v4.app.FragmentManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dcsir on 8/13/2016.
 */
public class DataHandler {

    public Context mContext;
    public FragmentManager FM;
    private ArrayList<String> dose, frequency;
    public String gender;
    public int age, doseSelection, frequencySelection, weight;
    public double SCr, height;

    private static DataHandler instance = new DataHandler();

    private DataHandler() {
        dose = new ArrayList<>();
        frequency = new ArrayList<>();
        populateLists();
    }

    public static DataHandler getInstance() {
        return instance;
    }

    public ArrayList<String> getDose(){
        return dose;
    }

    public ArrayList<String> getFrequency(){
        return frequency;
    }

    public void populateLists(){
        dose.add("500");
        dose.add("750");
        dose.add("1000");
        dose.add("1250");
        dose.add("1500");
        dose.add("2000");

        frequency.add("6");
        frequency.add("8");
        frequency.add("12");
        frequency.add("24");
    }


}
