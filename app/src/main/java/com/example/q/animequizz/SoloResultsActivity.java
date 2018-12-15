package com.example.q.animequizz;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import pl.droidsonroids.gif.GifImageView;

public class SoloResultsActivity extends AppCompatActivity {



    GifImageView gif;
    int score;
    int maxScore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results_solo);

        Bundle extras = getIntent().getExtras();
        score = extras.getInt("score", 0);
        maxScore = extras.getInt("max", 1);



        Button menu = findViewById(R.id.btn_menu);

        TextView scoreText = findViewById(R.id.finalscore);
        TextView comment= findViewById(R.id.comment);

        scoreText.setText("Score: " + score + "/" + maxScore);


        gif = findViewById(R.id.im_results);

        float finalScore=(float) score / (float)maxScore;
        if(finalScore<=0.5)
        {
            gif.setImageResource(R.drawable.overlord_defeat);
            comment.setText("Try another time");
        }
        else
        {
            gif.setImageResource(R.drawable.overlord_victory);
            comment.setText("Not bad !");
        }
        if(finalScore==1)
        {
            comment.setText("Perfect !");
        }


        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent party = new Intent(getApplicationContext(), MainActivity.class);

                startActivity(party);


            }
        });

    }


}
