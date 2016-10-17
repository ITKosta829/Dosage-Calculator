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
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * Created by DeanC on 7/19/2016.
 */
public class FragTabOne extends Fragment implements OnCheckedChangeListener {

    View mView;
    DataHandler DH;

    Spinner doseSpinner, frequencySpinner;
    EditText ET_ID, ET_Age, ET_SCr, ET_Height, ET_Weight;
    SegmentedRadioGroup RG_Gender, RG_Height, RG_Weight;

    Toast mToast;

    final String MYTAG = "SEE ALL VALUES";

    public FragTabOne() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.frag_tab_one, container, false);
        DH = DataHandler.getInstance();

        mToast = Toast.makeText(DH.mActivity, "", Toast.LENGTH_SHORT);

        ET_ID = (EditText) mView.findViewById(R.id.ID_Entry);
        ET_Age = (EditText) mView.findViewById(R.id.Age_Entry);
        ET_SCr = (EditText) mView.findViewById(R.id.SCr_Entry);
        ET_SCr.addTextChangedListener(new DecimalFilter(ET_SCr, DH.mActivity));
        ET_Height = (EditText) mView.findViewById(R.id.Height_Entry);
        ET_Weight = (EditText) mView.findViewById(R.id.Weight_Entry);

        RG_Gender = (SegmentedRadioGroup) mView.findViewById(R.id.Gender_Segment);
        RG_Gender.setOnCheckedChangeListener(this);
        RG_Height = (SegmentedRadioGroup) mView.findViewById(R.id.Height_Segment);
        RG_Height.setOnCheckedChangeListener(this);
        RG_Weight = (SegmentedRadioGroup) mView.findViewById(R.id.Weight_Segment);
        RG_Weight.setOnCheckedChangeListener(this);

        doseSpinner = (Spinner) mView.findViewById(R.id.Dose_Spinner);
        frequencySpinner = (Spinner) mView.findViewById(R.id.Frequency_Spinner);

        ArrayAdapter<String> doseAdapter = new ArrayAdapter<>(DH.mContext, R.layout.spinner_textview, DH.getDose());
        ArrayAdapter<String> frequencyAdapter = new ArrayAdapter<>(DH.mContext, R.layout.spinner_textview, DH.getFrequency());

        doseAdapter.setDropDownViewResource(R.layout.spinner_textview);
        doseSpinner.setAdapter(doseAdapter);
        doseSpinner.setSelection(2);
        doseSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                DH.originalDoseSelection = Double.valueOf(doseSpinner.getSelectedItem().toString());
                Log.d(MYTAG, "InitialDose Selection: "+DH.originalDoseSelection);
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
                DH.originalFrequencySelection = Integer.valueOf(frequencySpinner.getSelectedItem().toString());
                Log.d(MYTAG, "InitialDose Frequency: "+DH.originalFrequencySelection);
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

    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (group == RG_Gender) {
            if (checkedId == R.id.Gender_Male) {
                //what does button do
                DH.gender = "Male";
            } else if (checkedId == R.id.Gender_Female) {
                //what does button do
                DH.gender = "Female";
            }
        } else if (group == RG_Height) {
            if (checkedId == R.id.Height_Inches) {
                //what does button do
                DH.heightUnit = "inches";
            } else if (checkedId == R.id.Height_CM) {
                //what does button do
                DH.heightUnit = "cm";
            }
        } else if (group == RG_Weight) {
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
        String s = ET_SCr.getText().toString();
        String h = ET_Height.getText().toString();
        String w = ET_Weight.getText().toString();

        if (DH.gender.equals("") || a.equals("") || s.equals("") || h.equals("") || w.equals("")) {
            mToast.setText("Please enter missing values.");
            mToast.show();
        } else {
            if (id.equals("")) {
                DH.id = "Not Provided";
            } else {
                DH.id = ET_ID.getText().toString();
            }
            DH.age = Integer.valueOf(a);
            DH.SCr = Double.valueOf(s);
            DH.displayHeight = h;
            DH.displayWeight = w;

            DH.setCalculationValues();

            InitialResultsDialog resultsDialog = new InitialResultsDialog();
            resultsDialog.show(DH.FM, "display");
        }


    }

}
