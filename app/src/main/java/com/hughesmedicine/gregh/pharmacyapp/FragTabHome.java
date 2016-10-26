package com.hughesmedicine.gregh.pharmacyapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by DeanC on 10/18/2016.
 */

public class FragTabHome extends Fragment{

    View mView;
    char bullet, square;

    final String MYTAG = "SEE ALL VALUES";

    public FragTabHome() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.frag_tab_home, container, false);

        bullet = '\u2022';
        square = '\u25AA';

        TextView text = (TextView)mView.findViewById(R.id.text);

        text.setText(square + " Use this tool to determine a Vancomycin regimen, estimate a trough, " +
                "or adjust a regimen once a trough is obtained.\n\n" +
                square + " Optimal drug monitoring:\n\n" +
                square + " The trough should be obtained prior to the next dose once steady " +
                "state is reached (often just before the fourth dose)\n" +
                square + " The minimum trough should always be above 10mg/L to avoid development of resistance\n" +
                square + " The minimum trough should be 15-20mg/L in complicated infections (ie. bacteremia, endocarditis, " +
                "osteomyelitis, meningitis, hospital-acquired pneumonia) OR any infection if the MIC is at least 1mg/L\n\n" +
                square + " Instructions:\n\n" +
                square + " Swipe to the middle tab if just starting a regimen and a trough is not yet obtained\n" +
                square + " Swipe to the right tab if the patient is already receiving vancomycin, " +
                "has a steady state trough from the lab, and you wish to adjust the regimen\n" +
                square + " Use the menu in the upper right for more information about this tool\n\n" +
                square + " Any saved images are stored in your Downloads Folder");

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
