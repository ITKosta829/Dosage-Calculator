package com.hughesmedicine.gregh.pharmacyapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;

import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by dcsir on 8/13/2016.
 */
public class DataHandler {

    final String MYTAG = "SEE ALL VALUES";

    public Context mContext;
    public Activity mActivity;
    public FragmentManager FM;
    private ArrayList<String> dose, frequency;
    public String id, gender, heightUnit, weightUnit, displayHeight, displayWeight, displayIdealWeight, displayCrCl;
    public int age, frequencySelection;
    public double doseSelection, SCr, calcHeight, calcWeight, idealBodyWeight, dosingBodyWeight, dosingCreatinine;
    public double CrCl_mLmin, CrCl_Lhr, Vd_L, ke, halfLife, infusionTime, TTSS, ESST;

    private static DataHandler instance = new DataHandler();

    private DataHandler() {
        dose = new ArrayList<>();
        frequency = new ArrayList<>();
        populateLists();
    }

    public static DataHandler getInstance() {
        return instance;
    }

    public ArrayList<String> getDose() {
        return dose;
    }

    public ArrayList<String> getFrequency() {
        return frequency;
    }

    public void populateLists() {
        dose.add("500");
        dose.add("750");
        dose.add("1000");
        dose.add("1250");
        dose.add("1500");
        dose.add("2000");

        frequency.add("6");
        frequency.add("8");
        frequency.add("12");
        frequency.add("24");
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public void takeScreenshot(AlertDialog dialog) {
        Date now = new Date();
        android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss", now);

        try {
            // image naming and path  to include sd card  appending name you choose for file
            String mPath = Environment.getExternalStorageDirectory().toString() + "/" + now + ".jpg";

            // create bitmap screen capture
            View v1 = dialog.getWindow().getDecorView().getRootView();

            v1.setDrawingCacheEnabled(true);
            Bitmap bitmap = Bitmap.createBitmap(v1.getDrawingCache());
            v1.setDrawingCacheEnabled(false);

            File imageFile = new File(mPath);

            FileOutputStream outputStream = new FileOutputStream(imageFile);
            int quality = 100;
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
            outputStream.flush();
            outputStream.close();

            //Toast.makeText(mActivity, "Screenshot Saved.", Toast.LENGTH_SHORT).show();
            openScreenshot(imageFile);

        } catch (Throwable e) {
            // Several error may come out with file handling or OOM
            e.printStackTrace();
        }
    }

    private void openScreenshot(File imageFile) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        Uri uri = Uri.fromFile(imageFile);
        intent.setDataAndType(uri, "image/*");
        mActivity.startActivity(intent);
    }

    public void setCalculationValues() {

        if (heightUnit.equals("inches")) {
            int height = Integer.valueOf(displayHeight);
            calcHeight = round((height * 2.54), 1);
        }
        if (heightUnit.equals("cm")) {
            calcHeight = Double.valueOf(displayHeight);
        }
        if (weightUnit.equals("lbs")) {
            int weight = Integer.valueOf(displayWeight);
            calcWeight = round((weight / 2.2), 1);
        }
        if (weightUnit.equals("kg")) {
            calcWeight = Double.valueOf(displayWeight);
        }

        //Log.d(MYTAG, "Actual Weight: "+calcWeight);
        //Log.d(MYTAG, "Actual Height: "+calcHeight);

        setIdealBodyWeight();
        setDosingCreatinine();
        setVd_L();

    }

    private void setIdealBodyWeight() {

        if (gender.equals("Male")) {
            idealBodyWeight = 50 + 2.3 * ((calcHeight / 2.54) - 60);
        }

        if (gender.equals("Female")) {
            idealBodyWeight = 45.5 + 2.3 * ((calcHeight / 2.54) - 60);
        }

        if (weightUnit.equals("lbs")) {
            displayIdealWeight = String.valueOf(round((idealBodyWeight * 2.2), 1));
        }
        if (weightUnit.equals("kg")) {
            displayIdealWeight = String.valueOf(round(idealBodyWeight, 1));
        }

        //Log.d(MYTAG, "Ideal Weight: "+idealBodyWeight);

        setDosingBodyWeight();
    }

    private void setDosingBodyWeight() {
        if (calcWeight < idealBodyWeight) {
            dosingBodyWeight = calcWeight;
        } else {
            dosingBodyWeight = idealBodyWeight;
        }
        //Log.d(MYTAG, "Dosing Body Weight: "+dosingBodyWeight);
    }

    private void setDosingCreatinine() {
        if (age >= 65 && SCr < 1.0){
            dosingCreatinine = 1.0;
        } else {
            dosingCreatinine = SCr;
        }
        //Log.d(MYTAG, "Dosing Creatinine: "+dosingCreatinine);
        setCrCl_ml_min();
    }

    private void setCrCl_ml_min(){
        if (gender.equals("Female")) {
            CrCl_mLmin = ((140-age)*dosingBodyWeight/(72*dosingCreatinine))*0.85;
        } else {
            CrCl_mLmin = (140-age)*dosingBodyWeight/(72*dosingCreatinine);
        }
        //Log.d(MYTAG, "CrCl mL/min: "+CrCl_mLmin);
        displayCrCl = String.valueOf(round(CrCl_mLmin,2));
        setCrCl_L_hr();
    }

    private void setCrCl_L_hr(){
        CrCl_Lhr = CrCl_mLmin * 0.06;
        //Log.d(MYTAG, "CrCl L/hr: "+CrCl_Lhr);
    }

    private void setVd_L(){
        Vd_L = calcWeight * 0.7;
        //Log.d(MYTAG, "Vd L: "+Vd_L);

        setKE();
    }

    private void setKE(){
        ke = CrCl_Lhr / Vd_L;
        //Log.d(MYTAG, "ke: "+ke);

        setHalfLife();
    }

    private void setHalfLife(){
        halfLife = 0.693 / ke;
        halfLife = round(halfLife, 1);
        //Log.d(MYTAG, "Half Life: "+halfLife);

        setTimeToSteadyState();
    }

    private void setTimeToSteadyState(){
        TTSS = 4 * halfLife;
        TTSS = round(TTSS, 2);
        //Log.d(MYTAG, "Time to Steady State: "+TTSS);

        setInfusionTime();
        setEstimatedSteadyStateTrough();
    }

    private void setInfusionTime(){
        //Log.d(MYTAG, "DoseSelection: " + doseSelection);
        infusionTime = doseSelection / 1000;
        //Log.d(MYTAG, "InfusionTime: " + infusionTime);
    }

    private void setEstimatedSteadyStateTrough(){
        Double a, b, c, d;
        //ESST = (((doseSelection/infusionTime/CrCl_Lhr)*(EXP(-ke*(frequencySelection-infusionTime))*(1-EXP(-ke*infusionTime))/(1-EXP(-ke*frequencySelection)))));
        a = (doseSelection/infusionTime/CrCl_Lhr);
        //Log.d("ESST", "a: " + doseSelection + " / " + infusionTime + " / " + CrCl_Lhr);
        //Log.d("ESST", "a: " + a);
        b = (Math.exp(-ke*(frequencySelection-infusionTime)));
        //Log.d("ESST", "b: " + b);
        c = 1-Math.exp(-ke*infusionTime);
        //Log.d("ESST", "c: " + c);
        d = 1-Math.exp(-ke*frequencySelection);
        //Log.d("ESST", "d: " + d);
        ESST = (a * b * c) / d;
        ESST = round(ESST,3);

    }


}
