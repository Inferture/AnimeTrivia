package com.example.q.animequizz;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.NumberPicker;
import android.widget.Switch;

public class OptionsActivity extends AppCompatActivity implements SensorEventListener {
    /*Activity to modify the options, for now, the number of question in a round and the theme (light/dark) used, uses the light sensor for automatic theme use*/
    public static int numQuestions=10;

    public static float frontier=20;

    private SensorManager sensorManager;
    private Sensor lightSensor;

    Boolean light=true;
    Switch sw_lum;
    Switch sw_theme;
    public static int theme= R.style.AppTheme_DarkTheme;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        final SharedPreferences sharedPref = getSharedPreferences("ANIME_QUIZZ_PREF", Context.MODE_PRIVATE);
        numQuestions = sharedPref.getInt(getString(R.string.questions_number), 10);
        theme = sharedPref.getInt(getString(R.string.theme), R.style.AppTheme_LightTheme);

        light=theme==R.style.AppTheme_LightTheme;

        setTheme(theme);

        setContentView(R.layout.activity_options);


        final Button btn_title = findViewById(R.id.btn_otitle);

        final NumberPicker numquestions = findViewById(R.id.num_questions);

        numquestions.setMinValue(1);
        numquestions.setMaxValue(100);
        numquestions.setValue(numQuestions);


        sw_lum = findViewById(R.id.sw_lum);
        sw_theme = findViewById(R.id.sw_theme);

        sw_theme.setChecked(!light);
        numquestions.setOnValueChangedListener( new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker np, int i1, int i2) {
                numQuestions=i2;

                 sharedPref.edit().putInt(getString(R.string.questions_number), i2).commit();
            }
        });

        btn_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent party = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(party);
            }
        });

        sw_lum.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                sw_theme.setEnabled(!sw_lum.isChecked());
            }
        });

        sw_theme.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                light=!sw_theme.isChecked();
                if(light)
                {
                    theme=R.style.AppTheme_LightTheme;
                }
                else
                {
                    theme=R.style.AppTheme_DarkTheme;
                }
                setTheme(theme);
                sharedPref.edit().putInt(getString(R.string.theme), theme).commit();
                recreate();
            }
        });



        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        if(lightSensor==null)
        {
            Log.i("AnimeQuizz", "AnimeStuff: light sensor is null");
        }
        else
        {
            Log.i("AnimeQuizz", "AnimeStuff: light sensor:" + lightSensor);
        }
        //setTheme(R.style.AppTheme_DarkTheme);

    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, lightSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }




    //Light sensor
    @Override
    public final void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Do something here if sensor accuracy changes.
    }

    @Override
    public final void onSensorChanged(SensorEvent event) {
        final SharedPreferences sharedPref = getSharedPreferences("ANIME_QUIZZ_PREF", Context.MODE_PRIVATE);
        float lum = event.values[0];
        Log.i("AnimeQuizz", "AnimeStuff: luminosity:" + lum);
        if(sw_lum.isChecked())
        {

        if(lum<frontier)
        {
            theme=R.style.AppTheme_DarkTheme;
            setTheme(theme);
            sharedPref.edit().putInt(getString(R.string.theme), theme).commit();
            if(light)
            {
                //recreate();//unnecessary because the theme switch does it
                light=false;
                sw_theme.setChecked(true);
            }

        }
        else
        {
            theme=R.style.AppTheme_LightTheme;
            setTheme(theme);
            sharedPref.edit().putInt(getString(R.string.theme), theme).commit();


            if(!light)
            {
                //recreate();//unnecessary because the theme switch does it
                light=true;
                sw_theme.setChecked(false);
            }

        }

        }
    }





}
