package com.example.q.animequizz;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


/*Activity to choose between one player or split-screen multiplayer. Might contain a third mode for Bluetooth multiplayer in the future*/
public class ChoixModeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Theme
        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("ANIME_QUIZZ_PREF", Context.MODE_PRIVATE);
        int theme =sharedPref.getInt(getString(R.string.theme), R.style.AppTheme_LightTheme);
        setTheme(theme);

        setContentView(R.layout.activity_choix_mode);


        //Get layout elements
        Button solo = findViewById(R.id.btn_solo);
        Button duel = findViewById(R.id.btn_duel);

        Button title = findViewById(R.id.btn_ctitlemenu);

        Bundle extras = getIntent().getExtras();



        //Are we playing with the custom made questions ?
        final Boolean custom = extras!=null && extras.getBoolean("custom");

        //Start solo play
        solo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent party = new Intent(getApplicationContext(), SoloQuestionActivity.class);
                if(custom)
                {
                    party.putExtra("custom", custom);
                }
                startActivity(party);
            }
        });

        //Start duel play
        duel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent party = new Intent(getApplicationContext(), DuoQuestionActivity.class);
                if(custom)
                {
                    party.putExtra("custom", custom);
                }
                startActivity(party);
            }
        });

        //Back to main activity
        title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent party = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(party);
            }
        });

    }
}
