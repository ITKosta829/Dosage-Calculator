package com.hughesmedicine.gregh.pharmacyapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

/**
 * Created by DeanC on 10/11/2016.
 */

public class ResultsDialog extends DialogFragment {

    DataHandler DH;

    TextView Age, SCr, Height, Weight, CrCl, HalfLife, TTSS, ESST;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        LayoutInflater i = getActivity().getLayoutInflater();
        View v = i.inflate(R.layout.results_dialog, null);

        DH = DataHandler.getInstance();

        Age = (TextView) v.findViewById(R.id.Results_Age);
        SCr = (TextView) v.findViewById(R.id.Results_SCr);
        Height = (TextView) v.findViewById(R.id.Results_Height);
        Weight = (TextView) v.findViewById(R.id.Results_Weight);
        CrCl = (TextView) v.findViewById(R.id.Results_CrCl);
        HalfLife = (TextView) v.findViewById(R.id.Results_Half_Life);
        TTSS = (TextView) v.findViewById(R.id.Results_Time_to_Steady);
        ESST = (TextView) v.findViewById(R.id.Results_Estimated_Steady_State);

        Age.setText("Test 1");
        SCr.setText("Test 1");
        Height.setText("Test 1");
        Weight.setText("Test 1");
        CrCl.setText("Test 1");
        HalfLife.setText("Test 1");
        TTSS.setText("Test 1");
        ESST.setText("Test 1");

        AlertDialog.Builder b;
        b = new AlertDialog.Builder(getActivity());
        b.setView(v)

                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {

                                dialog.dismiss();
                            }
                        }
                );

        return b.create();
    }
}
