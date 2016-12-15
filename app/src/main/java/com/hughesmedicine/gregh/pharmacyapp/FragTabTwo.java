package com.hughesmedicine.gregh.pharmacyapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;

/**
 * Created by DeanC on 10/14/2016.
 */

public class FragTabTwo extends Fragment implements OnCheckedChangeListener{

    View mView;
    DataHandler DH;

    Spinner originalDoseSpinner, newDoseSpinner, originalFrequencySpinner, newFrequencySpinner;
    EditText ET_ID, ET_Age, ET_Weight, ET_Actual_ESST;
    SegmentedRadioGroup RG_Weight;

    Toast mToast;

    final String MYTAG = "SEE ALL VALUES";

    public FragTabTwo() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.frag_tab_two, container, false);

        DH = DataHandler.getInstance();

        mToast = Toast.makeText(DH.mActivity, "", Toast.LENGTH_SHORT);

        ET_ID = (EditText) mView.findViewById(R.id.ID_Entry);
        ET_Age = (EditText) mView.findViewById(R.id.Age_Entry);
        ET_Weight = (EditText) mView.findViewById(R.id.Weight_Entry);
        ET_Actual_ESST = (EditText) mView.findViewById(R.id.Actual_ESST_Entry);

        RG_Weight = (SegmentedRadioGroup) mView.findViewById(R.id.Weight_Segment);
        RG_Weight.setOnCheckedChangeListener(this);

        originalDoseSpinner = (Spinner) mView.findViewById(R.id.Original_Dose_Spinner);
        originalFrequencySpinner = (Spinner) mView.findViewById(R.id.Original_Frequency_Spinner);
        newDoseSpinner = (Spinner) mView.findViewById(R.id.New_Dose_Spinner);
        newFrequencySpinner = (Spinner) mView.findViewById(R.id.New_Frequency_Spinner);

        ArrayAdapter<String> originalDoseAdapter = new ArrayAdapter<>(DH.mContext, R.layout.spinner_textview, DH.getDose());
        ArrayAdapter<String> originalFrequencyAdapter = new ArrayAdapter<>(DH.mContext, R.layout.spinner_textview, DH.getFrequency());
        ArrayAdapter<String> newDoseAdapter = new ArrayAdapter<>(DH.mContext, R.layout.spinner_textview, DH.getDose());
        ArrayAdapter<String> newFrequencyAdapter = new ArrayAdapter<>(DH.mContext, R.layout.spinner_textview, DH.getFrequency());

        originalDoseAdapter.setDropDownViewResource(R.layout.spinner_textview);
        originalDoseSpinner.setAdapter(originalDoseAdapter);
        originalDoseSpinner.setSelection(2);
        originalDoseSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                DH.originalDoseSelection = Double.valueOf(originalDoseSpinner.getSelectedItem().toString());
                Log.d(MYTAG, "InitialDose Selection: "+DH.originalDoseSelection);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        originalFrequencyAdapter.setDropDownViewResource(R.layout.spinner_textview);
        originalFrequencySpinner.setAdapter(originalFrequencyAdapter);
        originalFrequencySpinner.setSelection(2);
        originalFrequencySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                DH.originalFrequencySelection = Integer.valueOf(originalFrequencySpinner.getSelectedItem().toString());
                Log.d(MYTAG, "InitialDose Frequency: "+DH.originalFrequencySelection);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        newDoseAdapter.setDropDownViewResource(R.layout.spinner_textview);
        newDoseSpinner.setAdapter(originalDoseAdapter);
        newDoseSpinner.setSelection(2);
        newDoseSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                DH.newDoseSelection = Double.valueOf(newDoseSpinner.getSelectedItem().toString());
                Log.d(MYTAG, "New InitialDose Selection: "+DH.newDoseSelection);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        newFrequencyAdapter.setDropDownViewResource(R.layout.spinner_textview);
        newFrequencySpinner.setAdapter(originalFrequencyAdapter);
        newFrequencySpinner.setSelection(2);
        newFrequencySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                DH.newFrequencySelection = Integer.valueOf(newFrequencySpinner.getSelectedItem().toString());
                Log.d(MYTAG, "New InitialDose Frequency: "+DH.newFrequencySelection);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        Button calculate = (Button) mView.findViewById(R.id.Calculate);
        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getValues();
            }
        });

        Button clear = (Button) mView.findViewById(R.id.Clear);
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ET_ID.setText("");
                ET_Weight.setText("");
                ET_Actual_ESST.setText("");
            }
        });


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

    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (group == RG_Weight) {
            if (checkedId == R.id.Weight_LBS) {
                //what does button do
                DH.weightUnit = "lbs";
            } else if (checkedId == R.id.Weight_KG) {
                //what does button do
                DH.weightUnit = "kg";
            }
        }
    }

    public void getValues() {

        String id = ET_ID.getText().toString();
        String a = ET_Age.getText().toString();
        String w = ET_Weight.getText().toString();
        String e = ET_Actual_ESST.getText().toString();

        Log.d(MYTAG, "Weight: " + w + " Unit:" + DH.weightUnit);
        Log.d(MYTAG, "Actual ESST: " + e);

        if (DH.weightUnit == null || w.equals("") || e.equals("")) {
            mToast.setText("Please enter missing values.");
            mToast.show();
        } else {
            if (id.equals("")) {
                DH.id = "Not Provided";
            } else {
                DH.id = ET_ID.getText().toString();
            }

            DH.displayWeight = w;
            DH.age = Integer.valueOf(a);
            DH.actualLabESST = Double.valueOf(e);

            DH.startFragTabTwoCalculations();

            AdjustResultsDialog resultsDialog = new AdjustResultsDialog();
            resultsDialog.show(DH.FM, "display");
        }


    }


}
