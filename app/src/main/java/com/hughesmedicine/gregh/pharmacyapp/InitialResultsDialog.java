package com.hughesmedicine.gregh.pharmacyapp;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by DeanC on 10/11/2016.
 */

public class InitialResultsDialog extends DialogFragment {

    DataHandler DH;
    AlertDialog resultText;
    TextView ID, Gender, Age, SCr, Height, Weight, idealWeight, CrCl, HalfLife, TTSS, ESST;
    TextView Dose, DoseInterval, Timestamp, OORN;
    final private int REQUEST_CODE_ASK_PERMISSIONS = 1;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        LayoutInflater i = getActivity().getLayoutInflater();
        View v = i.inflate(R.layout.initial_results_dialog, null);

        DH = DataHandler.getInstance();

        Timestamp = (TextView) v.findViewById(R.id.Timestamp);
        ID = (TextView) v.findViewById(R.id.Results_ID);
        Gender = (TextView) v.findViewById(R.id.Results_Gender);
        Age = (TextView) v.findViewById(R.id.Results_Age);
        SCr = (TextView) v.findViewById(R.id.Results_SCr);
        Height = (TextView) v.findViewById(R.id.Results_Height);
        Weight = (TextView) v.findViewById(R.id.Results_Weight);
        idealWeight = (TextView) v.findViewById(R.id.Results_Ideal_Weight);
        Dose = (TextView) v.findViewById(R.id.Results_Dose);
        DoseInterval = (TextView) v.findViewById(R.id.Results_Dose_Interval);
        CrCl = (TextView) v.findViewById(R.id.Results_CrCl);
        HalfLife = (TextView) v.findViewById(R.id.Results_Half_Life);
        TTSS = (TextView) v.findViewById(R.id.Results_Time_to_Steady);
        ESST = (TextView) v.findViewById(R.id.Results_Estimated_Steady_State);
        OORN = (TextView) v.findViewById(R.id.Outside_Of_Range_Notice);


        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("E yyyy.MM.dd 'at' hh:mm:ss a zzz");
        String currentTime = sdf.format(date);

        int x = (int) DH.originalDoseSelection;
        String doseInMG = String.valueOf(x);

        Timestamp.setText(currentTime);
        ID.setText(DH.id);
        Gender.setText(DH.gender);
        Age.setText(String.valueOf(DH.age));
        SCr.setText(String.valueOf(DH.SCr));
        Height.setText(String.valueOf(DH.displayHeight) + " " + DH.heightUnit);
        Weight.setText(String.valueOf(DH.displayWeight) + " " + DH.weightUnit);
        idealWeight.setText(DH.displayIdealWeight + " " + DH.weightUnit);
        Dose.setText(doseInMG);
        DoseInterval.setText(String.valueOf(DH.originalFrequencySelection));
        CrCl.setText(DH.displayCrCl);
        HalfLife.setText(String.valueOf(DH.halfLife));
        TTSS.setText(String.valueOf(DH.TTSS) + " hrs");
        ESST.setText(String.valueOf(DH.ESST));

        if ((DH.ESST < 10) || (DH.ESST) > 20) {
            OORN.setText("Outside of therapeutic range\n" +
                    "(usually 10-20 mg/L or\n" +
                    "15-20 mg/L if complicated),\n" +
                    "adjust and try again!");
        }

        TextView title = new TextView(DH.mActivity);
        title.setText("Initial Trough Calculation Results");
        title.setBackgroundColor(Color.DKGRAY);
        title.setPadding(10, 10, 10, 10);
        title.setGravity(Gravity.CENTER);
        title.setTextColor(Color.WHITE);


        AlertDialog.Builder b;
        b = new AlertDialog.Builder(getActivity());
        b.setView(v)

                .setCustomTitle(title)

                .setPositiveButton("Email Results",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                resultText = AlertDialog.class.cast(dialog);
                                checkPermissions();
                            }
                        }
                )
                .setNegativeButton("Dismiss",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                dialog.dismiss();
                            }
                        }
                );


        return b.create();
    }

    private void checkPermissions() {
        if (android.os.Build.VERSION.SDK_INT >= 23) {
            int hasLocationPermission = DH.mContext.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if (hasLocationPermission != PackageManager.PERMISSION_GRANTED) {
                if (!shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    showMessageOKCancel("Allow access to storage to save screenshot.",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if (android.os.Build.VERSION.SDK_INT >= 23) {

                                        DH.mActivity.requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                                REQUEST_CODE_ASK_PERMISSIONS);
                                    }
                                }
                            });
                    return;
                }

                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        REQUEST_CODE_ASK_PERMISSIONS);
                return;
            }
        }

        DH.takeScreenshot(resultText);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay!
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(DH.mActivity, "Storage permission denied.", Toast.LENGTH_SHORT).show();
                }
                break;
            }
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
                DH.takeScreenshot(resultText);
        }
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(DH.mActivity)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }


}
