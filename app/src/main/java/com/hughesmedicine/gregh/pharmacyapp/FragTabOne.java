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
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.ToggleButton;

/**
 * Created by DeanC on 7/19/2016.
 */
public class FragTabOne extends Fragment {

    View mView;
    DataHandler DH;
    int dosePosition, frequencyPosition;

    EditText ET_Age, ET_SCr, ET_Height, ET_Weight;
    ToggleButton TB_Gender, TB_Height, TB_Weight;

    public FragTabOne() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.frag_tab_one, container, false);
        DH = DataHandler.getInstance();

        ET_Age = (EditText) mView.findViewById(R.id.Age_Entry);
        ET_SCr = (EditText) mView.findViewById(R.id.SCr_Entry);
        ET_Height = (EditText) mView.findViewById(R.id.Height_Entry);
        ET_Weight = (EditText) mView.findViewById(R.id.Weight_Entry);
        TB_Gender = (ToggleButton) mView.findViewById(R.id.Gender_Switch);
        TB_Height = (ToggleButton) mView.findViewById(R.id.Height_Switch);
        TB_Weight = (ToggleButton) mView.findViewById(R.id.Weight_Switch);

        final Spinner doseSpinner = (Spinner) mView.findViewById(R.id.Dose_Spinner);
        final Spinner frequencySpinner = (Spinner) mView.findViewById(R.id.Frequency_Spinner);

        ArrayAdapter<String> doseAdapter = new ArrayAdapter<>(DH.mContext, R.layout.spinner_textview, DH.getDose());
        ArrayAdapter<String> frequencyAdapter = new ArrayAdapter<>(DH.mContext, R.layout.spinner_textview, DH.getFrequency());

        doseAdapter.setDropDownViewResource(R.layout.spinner_textview);
        doseSpinner.setAdapter(doseAdapter);
        doseSpinner.setSelection(2);
        doseSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                DH.doseSelection = Integer.valueOf(doseSpinner.getSelectedItem().toString());
                dosePosition = i;
                Log.d("DOSE SPINNER", DH.doseSelection + " " + dosePosition);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        frequencyAdapter.setDropDownViewResource(R.layout.spinner_textview);
        frequencySpinner.setAdapter(frequencyAdapter);
        frequencySpinner.setSelection(2);
        frequencySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                DH.frequencySelection = Integer.valueOf(frequencySpinner.getSelectedItem().toString());
                frequencyPosition = i;
                Log.d("FREQUENCY SPINNER", DH.frequencySelection + " " + frequencyPosition);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        Button calculate = (Button) mView.findViewById(R.id.Calculate);
        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFieldValues();
                ResultsDialog resultsDialog = new ResultsDialog();
                resultsDialog.show(DH.FM, "display");
            }
        });

        Button clear = (Button) mView.findViewById(R.id.Clear);
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ET_Age.setText("");
                ET_SCr.setText("");
                ET_Height.setText("");
                ET_Weight.setText("");
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
        Log.d("MyTag", "Fragment One Paused");
        super.onPause();
    }

    @Override
    public void onResume() {
        Log.d("MyTag", "Fragment One Resumed");
        super.onResume();
    }

    public void getFieldValues() {

        TB_Gender.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    // The toggle is enabled
                    DH.gender = "MALE";
                } else {
                    // The toggle is disabled
                    DH.gender = "FEMALE";
                }
            }
        });

        DH.age = Integer.valueOf(ET_Age.getText().toString());
        DH.SCr = Double.valueOf(ET_SCr.getText().toString());

        TB_Height.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    // The toggle is enabled
                    int height = Integer.valueOf(ET_Height.getText().toString());
                    DH.height = height * 2.54;

                } else {
                    // The toggle is disabled
                    DH.height = Double.valueOf(ET_Height.getText().toString());
                }
            }
        });

        TB_Weight.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    // The toggle is enabled
                    DH.gender = "LBS";
                } else {
                    // The toggle is disabled
                    DH.gender = "KG";
                }
            }
        });


    }


}
