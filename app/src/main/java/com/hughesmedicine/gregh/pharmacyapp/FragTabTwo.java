package com.hughesmedicine.gregh.pharmacyapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by DeanC on 10/14/2016.
 */

public class FragTabTwo extends Fragment {

    View mView;

    public FragTabTwo() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.frag_tab_two, container, false);


        return mView;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onPause() {
        Log.d("MyTag", "Fragment Two Paused");
        super.onPause();
    }

    @Override
    public void onResume() {
        Log.d("MyTag", "Fragment Two Resumed");
        super.onResume();
    }

    @Override
    public void onStop() {
        super.onStop();
    }


}
