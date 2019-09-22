package com.bayanaka.myactivity.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TimePicker;
import android.widget.Toast;

import com.bayanaka.myactivity.R;
import com.bayanaka.myactivity.adapters.ViewPagerAdapter;
import com.bayanaka.myactivity.models.Activities;
import com.bayanaka.myactivity.utils.Const.Day;
import com.bayanaka.myactivity.viewmodels.ActivityViewModel;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.textfield.TextInputEditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.zip.Inflater;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TabLayout tabLayout;
    ViewPager viewPager;
    ActivityViewModel activiViewModel;
    DayFragment sundayFragment, mondayFragment, tuesdayFragment;
    FloatingActionButton fabAdd;
    TextInputEditText startTime, endTime, activityText;
    TimePickerDialog picker;
    String activityName;
    Date startTimeValue, endTimeValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabLayout = findViewById(R.id.tabContent);
        viewPager = findViewById(R.id.contentViewPager);
        fabAdd = findViewById(R.id.fabAdd);
        fabAdd.setOnClickListener(this);

        instantiateFragments();

        setupTabLayout(viewPager);
        tabLayout.setupWithViewPager(viewPager);

        activiViewModel = ViewModelProviders.of(this).get(ActivityViewModel.class);

        activiViewModel.getStartTime().observe(this, new Observer<Date>() {
            @Override
            public void onChanged(Date startTime) {
                startTimeValue = startTime;
            }
        });

        activiViewModel.getEndTime().observe(this, new Observer<Date>() {
            @Override
            public void onChanged(Date endTime) {
                endTimeValue = endTime;
            }
        });

        activiViewModel.getActivityName().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                activityName = s;
            }
        });
    }

    private void instantiateFragments() {
        sundayFragment = new DayFragment();
        sundayFragment.setDay(Day.MINGGU);

        mondayFragment = new DayFragment();
        mondayFragment.setDay(Day.SENIN);

        tuesdayFragment = new DayFragment();
        tuesdayFragment.setDay(Day.SELASA);
    }

    private void setupTabLayout(ViewPager viewPager) {
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPagerAdapter.add(sundayFragment);
        viewPagerAdapter.add(mondayFragment );
        viewPagerAdapter.add(tuesdayFragment);
        viewPager.setAdapter(viewPagerAdapter);
    }

    @Override
    public void onClick(View v) {
        final CharSequence day = tabLayout.getTabAt(tabLayout.getSelectedTabPosition()).getText();
        final int timeStart, timeEnd;
        Log.d("tes", "onClick: " + day);

        View view = getLayoutInflater().inflate(R.layout.layout_add_activity, null);

        startTime = view.findViewById(R.id.startTime);
        endTime = view.findViewById(R.id.endTime);
        activityText = view.findViewById(R.id.activityText);

        startTime.setInputType(InputType.TYPE_NULL);
        endTime.setInputType(InputType.TYPE_NULL);

        startTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int hour = cldr.get(Calendar.HOUR_OF_DAY);
                int minutes = cldr.get(Calendar.MINUTE);
                // time picker dialog
                picker = new TimePickerDialog(MainActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker tp, int sHour, int sMinute) {
                                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                                Date date = new Date();
                                try {
                                    date = sdf.parse((sHour + ":" +sMinute));
                                    Log.d("tag", "onTimeSet: " + date);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                activiViewModel.setStartTime(date);
                            }
                        }, hour, minutes, true);
                picker.show();
            }
        });

        endTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                cldr.getTime();
                int hour = cldr.get(Calendar.HOUR_OF_DAY);
                int minutes = cldr.get(Calendar.MINUTE);

                // time picker dialog
                picker = new TimePickerDialog(MainActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker tp, int sHour, int sMinute) {
                                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                                Date date = new Date();
                                try {
                                    date = sdf.parse((sHour + ":" +sMinute));
                                    Log.d("tag", "onTimeSet: " + date);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                activiViewModel.setEndTime(date);
                            }

                        }, hour, minutes, true);
                picker.show();
            }
        });

        new MaterialAlertDialogBuilder(this)
                .setView(view)
                .setPositiveButton("Simpan", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        activiViewModel.insert(new Activities(activityText.getText().toString(), day.toString(),startTimeValue, endTimeValue));
                    }
                })
                .setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .show();

    }
}
