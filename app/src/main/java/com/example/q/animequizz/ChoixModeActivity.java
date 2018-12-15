package com.example.q.animequizz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ChoixModeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choix_mode);


        Button solo = findViewById(R.id.btn_solo);
        Button duel = findViewById(R.id.btn_duel);

        Button title = findViewById(R.id.btn_ctitlemenu);

        Bundle extras = getIntent().getExtras();

        Boolean b;
        b=extras!=null && extras.getBoolean("custom");

        final Boolean custom = b;


        solo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent party = new Intent(getApplicationContext(), SoloQuestionActivity.class);//The right one
                Intent party = new Intent(getApplicationContext(), SoloQuestionActivity.class);
                if(custom)
                {
                    party.putExtra("custom", custom);
                }
                startActivity(party);
            }
        });

        duel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent party = new Intent(getApplicationContext(), SoloQuestionActivity.class);//The right one
                Intent party = new Intent(getApplicationContext(), DuoQuestionActivity.class);
                if(custom)
                {
                    party.putExtra("custom", custom);
                }
                startActivity(party);
            }
        });


        title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent party = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(party);
            }
        });

    }
}
