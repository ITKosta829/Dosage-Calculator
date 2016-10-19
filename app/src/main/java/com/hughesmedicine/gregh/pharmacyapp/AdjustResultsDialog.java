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
 * Created by DeanC on 10/17/2016.
 */

public class AdjustResultsDialog extends DialogFragment {

    DataHandler DH;
    AlertDialog resultText;
    TextView ID, InitialDose, InitialDoseInterval, Timestamp, ASST, NewDose, NewDoseInterval, NewESST;
    final private int REQUEST_CODE_ASK_PERMISSIONS = 1;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        LayoutInflater i = getActivity().getLayoutInflater();
        View v = i.inflate(R.layout.adjust_results_dialog, null);

        DH = DataHandler.getInstance();

        Timestamp = (TextView) v.findViewById(R.id.Timestamp);
        ID = (TextView) v.findViewById(R.id.Results_ID);
        InitialDose = (TextView) v.findViewById(R.id.Results_Initial_Dose);
        InitialDoseInterval = (TextView) v.findViewById(R.id.Results_Initial_Dose_Interval);
        ASST = (TextView) v.findViewById(R.id.Results_Actual_Steady_State);
        NewDose = (TextView) v.findViewById(R.id.Results_Adjust_Dose);
        NewDoseInterval = (TextView) v.findViewById(R.id.Results_Adjust_Dose_Interval);
        NewESST = (TextView) v.findViewById(R.id.Results_New_Estimated_Steady_State);

        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("E yyyy.MM.dd 'at' hh:mm:ss a zzz");
        String currentTime = sdf.format(date);

        int x = (int) DH.originalDoseSelection;
        int y = (int) DH.newDoseSelection;
        String originalDoseInMG = String.valueOf(x);
        String newDoseInMG = String.valueOf(y);

        Timestamp.setText(currentTime);
        ID.setText(DH.id);
        InitialDose.setText(originalDoseInMG);
        InitialDoseInterval.setText(String.valueOf(DH.originalFrequencySelection));
        NewDose.setText(newDoseInMG);
        NewDoseInterval.setText(String.valueOf(DH.newFrequencySelection));
        NewESST.setText(String.valueOf(DH.newESST));
        ASST.setText(String.valueOf(DH.actualLabESST));

        TextView title = new TextView(DH.mActivity);
        title.setText("Adjustment Trough Calculation Results");
        title.setBackgroundColor(Color.DKGRAY);
        title.setPadding(10, 10, 10, 10);
        title.setGravity(Gravity.CENTER);
        title.setTextColor(Color.WHITE);


        AlertDialog.Builder b;
        b = new AlertDialog.Builder(getActivity());
        b.setView(v)

                .setCustomTitle(title)

                .setPositiveButton("Save",
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
                                        requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
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
