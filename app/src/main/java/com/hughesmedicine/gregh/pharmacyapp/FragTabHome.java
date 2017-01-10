package com.hughesmedicine.gregh.pharmacyapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by DeanC on 10/18/2016.
 */

public class FragTabHome extends Fragment {

    View mView;
    char bullet, square;
    DataHandler DH;

    final String MYTAG = "SEE ALL VALUES";

    public FragTabHome() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.frag_tab_home, container, false);

        DH = DataHandler.getInstance();

        bullet = '\u2022';
        square = '\u25AA';

        TextView textA = (TextView) mView.findViewById(R.id.textA);
        TextView text1 = (TextView) mView.findViewById(R.id.text1);
        TextView text2 = (TextView) mView.findViewById(R.id.text2);
        TextView text3 = (TextView) mView.findViewById(R.id.text3);
        TextView text4 = (TextView) mView.findViewById(R.id.text4);
        TextView text5 = (TextView) mView.findViewById(R.id.text5);

        textA.setText(square + " What this app does:");

        text1.setText(square + " Use this tool to determine a Vancomycin regimen, estimate a trough, " +
                "or adjust a regimen once a trough is obtained\n");

        text2.setText(square + " Instructions:");

        text3.setText(square + " Swipe to the middle tab if just starting a regimen and a trough is not yet obtained\n" +
                square + " Swipe to the right tab if the patient is already receiving vancomycin, " +
                "has a steady state trough from the lab, and you wish to adjust the regimen\n" +
                square + " Use the menu in the upper right for more information about this tool\n");

        text4.setText(square + " Optimal drug monitoring:");

        text5.setText(square + " The trough should be obtained prior to the next dose once steady " +
                "state is reached (often just before the fourth dose)\n" +
                square + " The minimum trough should always be above 10mg/L to avoid development of resistance\n" +
                square + " The minimum trough should be 15-20mg/L in complicated infections (ie. bacteremia, endocarditis, " +
                "osteomyelitis, meningitis, hospital-acquired pneumonia) OR any infection if the MIC is at least 1mg/L");


        if (!DH.TOS) showTermsAndConditions();

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

    public void showTermsAndConditions() {

        new AlertDialog.Builder(DH.mActivity)
                .setTitle("Terms and Conditions")

                .setMessage("Care has been taken to confirm the accuracy of the information " +
                        "present.  However, the designer is not responsible for errors or " +
                        "omissions or for any consequences from application of this information " +
                        "and make no warranty with respect to the contents of the publication.  " +
                        "Application in any particular situation remains the responsibility " +
                        "of the practitioner and the treatments recommended are not universal recommendations.")

                .setPositiveButton("Agree", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        DH.TOS = true;
                        dialog.dismiss();
                    }
                })

                .setNegativeButton("Refuse", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        DH.mActivity.finish();

                    }
                })

                .setOnKeyListener(new DialogInterface.OnKeyListener() {
                    @Override
                    public boolean onKey(DialogInterface dialogInterface, int keyCode, KeyEvent event) {
                        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP)
                            DH.mActivity.finish();
                        return false;
                    }
                })

                .setCancelable(false)

                .show();

    }

}
