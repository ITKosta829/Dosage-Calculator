package com.hughesmedicine.gregh.pharmacyapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private final String TAG = "Testing";
    Toolbar toolbar;
    TabLayout tabLayout;
    ViewPager viewPager;
    DataHandler DH;
    char bullet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DH = DataHandler.getInstance();
        DH.mContext = MainActivity.this;
        DH.mActivity = this;
        DH.FM = getSupportFragmentManager();

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);


        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Log.d(TAG, "onPageScrolled");
            }

            @Override
            public void onPageSelected(int position) {
                Log.d(TAG, "onPageSelected");
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        bullet = '\u2022';


    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new FragTabHome(), "Home");
        adapter.addFragment(new FragTabOne(), "Initial\nRegimen");
        adapter.addFragment(new FragTabTwo(), "Adjust\nRegimen");
        viewPager.setAdapter(adapter);

    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {

            case R.id.menu_references:
                new AlertDialog.Builder(this)
                        .setTitle(R.string.references_title)
                        .setMessage("Ambrose PJ, Winter ME. Vancomycin. In:  Winter ME, ed. " +
                                "Basic Clinical Pharmacokinetics. 5th ed. Baltimore, MD: Lippincott Williams & Wilkins; 2010.\n\n" +
                                "Rybak M, Lomaestro B, Rotschafer JC, et al.  Therapeutic monitoring of Vancomycin in adult patients: " +
                                "A consensus review of the American Society of Health-System Pharmacists, " +
                                "the Infectious Diseases Society of America, and the Society of Infectious Diseases Pharmacists.\n" +
                                "Am J Health-Syst Pharm  2009;66:82-98.")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // continue with delete
                            }
                        })
                        .show();
                return true;

            case R.id.menu_limitations:
                new AlertDialog.Builder(this)
                        .setTitle(R.string.limitations_title)
                        .setMessage(bullet + " Adjustment regimens are based on a trough that is assumed to " +
                                "be correctly drawn and at steady state\n\n" +
                                bullet + " Use for non-pregnant adults with stable renal function " +
                                "- extremes of age, weight, and critically ill are more difficult to predict\n\n" +
                                bullet + " If actual trough returns significantly different from what " +
                                "was expected, use clinical judgement and consider redrawing level\n\n" +
                                bullet + " Validation studies currently underway")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // continue with delete
                            }
                        })
                        .show();
                return true;

            case R.id.menu_liability:
                new AlertDialog.Builder(this)
                        .setTitle(R.string.liability_title)
                        .setMessage("Care has been taken to confirm the accuracy of the information " +
                                "present.  However, the designer is not responsible for errors or " +
                                "omissions or for any consequences from application of this information " +
                                "and make no warranty with respect to the contents of the publication.  " +
                                "Application in any particular situation remains the responsibility " +
                                "of the practitioner and the treatments recommended are not universal recommendations.")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // continue with delete
                            }
                        })
                        .show();
                return true;

            case R.id.menu_learn_more:
                String url = "http://www.hughesmedicine.com";
                String msg = "For information about other pharmacotherapy topics related to" +
                        " Internal Medicine, please visit: \n" + url + "\n\n" +
                        "App Coded and Designed by:\n" +
                        "Dean Siroky\n" +
                        "dsirokydroidapps@gmail.com\n\n" +
                        "Product of HughesMedicine, LLC";

                final SpannableString s = new SpannableString(msg);
                Linkify.addLinks(s, Linkify.ALL);

                final AlertDialog d = new AlertDialog.Builder(this)
                        .setTitle(R.string.more_info_title)
                        .setMessage(s)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // continue with delete
                            }
                        })
                        .show();

                ((TextView) d.findViewById(android.R.id.message)).setMovementMethod(LinkMovementMethod.getInstance());
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
