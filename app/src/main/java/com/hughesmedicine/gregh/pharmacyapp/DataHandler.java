package com.hughesmedicine.gregh.pharmacyapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.app.FragmentManager;
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

    public Context mContext;
    public Activity mActivity;
    public FragmentManager FM;
    private ArrayList<String> dose, frequency;
    public String id,gender, heightUnit, weightUnit;
    public int age, doseSelection, frequencySelection;
    public double SCr, height, weight;

    private static DataHandler instance = new DataHandler();

    private DataHandler() {
        dose = new ArrayList<>();
        frequency = new ArrayList<>();
        populateLists();
    }

    public static DataHandler getInstance() {
        return instance;
    }

    public ArrayList<String> getDose(){
        return dose;
    }

    public ArrayList<String> getFrequency(){
        return frequency;
    }

    public void populateLists(){
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




}
