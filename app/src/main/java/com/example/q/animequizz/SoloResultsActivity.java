package com.example.q.animequizz;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import pl.droidsonroids.gif.GifImageView;

/*Activity that shows the results (how many good answer out of how many questions + gifs...) in the solo mode*/
public class SoloResultsActivity extends AppCompatActivity {


    //To display animated gifs
    GifImageView gif;

    int score;
    int maxScore;//Maximum possible score
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);

        //Theme
        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("ANIME_QUIZZ_PREF", Context.MODE_PRIVATE);
        int theme =sharedPref.getInt(getString(R.string.theme), R.style.AppTheme_LightTheme);
        setTheme(theme);

        setContentView(R.layout.activity_results_solo);

        //Parameters
        Bundle extras = getIntent().getExtras();
        score = extras.getInt("score", 0);
        maxScore = extras.getInt("max", 1);


        //Layout elements
        Button menu = findViewById(R.id.btn_menu);

        TextView scoreText = findViewById(R.id.finalscore);
        TextView comment= findViewById(R.id.comment);

        scoreText.setText("Score: " + score + "/" + maxScore);


        gif = findViewById(R.id.im_results);

        float finalScore=(float) score / (float)maxScore;
        //The image and the results depends on the ratio
        //score/maxScore:
        //<=0.5: defeat ->defeat gif
        //>0.5 && <1: not bad -> victory gif
        //1: perfect -> victory gif
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


        //Back to the Title (MainActivity)
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent party = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(party);
            }
        });

    }


}
