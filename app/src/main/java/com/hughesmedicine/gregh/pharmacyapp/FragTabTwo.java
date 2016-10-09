package com.hughesmedicine.gregh.pharmacyapp;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by DeanC on 7/19/2016.
 */
public class FragTabTwo extends Fragment {

    View mView;

    public FragTabTwo() {
        // Required empty public constructor
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

    public void setFonts() {
        Typeface tf = Typeface.createFromAsset(getActivity().getAssets(), "ANDYB.TTF");

        TextView tv1 = (TextView) mView.findViewById(R.id.camera_details);
        tv1.setTypeface(tf);
        TextView tv2 = (TextView) mView.findViewById(R.id.gallery_details);
        tv2.setTypeface(tf);

    }

}
