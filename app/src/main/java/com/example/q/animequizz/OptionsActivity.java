package com.example.q.animequizz;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.NumberPicker;

public class OptionsActivity extends AppCompatActivity {

    public static int numQuestions=10;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);
        final NumberPicker numquestions = findViewById(R.id.num_questions);

        numquestions.setMinValue(1);
        numquestions.setMaxValue(100);
        numquestions.setValue(numQuestions);


        numquestions.setOnValueChangedListener( new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker np, int i1, int i2) {
                numQuestions=i2;
                SharedPreferences sharedPref = getSharedPreferences("ANIME_QUIZZ_PREF", Context.MODE_PRIVATE);
                 sharedPref.edit().putInt(getString(R.string.questions_number), i2).commit();
            }
        });

    }
}
