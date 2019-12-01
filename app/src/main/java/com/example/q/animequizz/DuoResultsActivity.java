package com.example.q.animequizz;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import pl.droidsonroids.gif.GifImageView;

/*Activity that shows the end result (winner/loser or draw) in the split screen duo mode*/
public class DuoResultsActivity extends AppCompatActivity {



    int scoreJ1;
    int scoreJ2;
    int maxScore;
    GifImageView gifJ1;
    GifImageView gifJ2;
    Boolean j1ready=false;
    Boolean j2ready=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //Theme
        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("ANIME_QUIZZ_PREF", Context.MODE_PRIVATE);
        int theme =sharedPref.getInt(getString(R.string.theme), R.style.AppTheme_LightTheme);
        setTheme(theme);

        setContentView(R.layout.activity_results_duo);

        //Parameters
        Bundle extras = getIntent().getExtras();
        scoreJ1 = extras.getInt("scorej1", 0);
        scoreJ2 = extras.getInt("scorej2", 0);
        maxScore = extras.getInt("max", 1);


        //Layout elements
        final Button menuJ1 = findViewById(R.id.btn_menuj1);
        final Button menuJ2 = findViewById(R.id.btn_menuj2);

        TextView scoreTextJ1 = findViewById(R.id.finalscorej1);
        TextView scoreTextJ2 = findViewById(R.id.finalscorej2);
        TextView commentJ1= findViewById(R.id.commentj1);
        TextView commentJ2= findViewById(R.id.commentj2);

        gifJ1 = findViewById(R.id.im_resultsj1);
        gifJ2 = findViewById(R.id.im_resultsj2);

        //End score
        scoreTextJ1.setText("Score: " + scoreJ1 + "/" + maxScore);
        scoreTextJ2.setText("Score: " + scoreJ2 + "/" + maxScore);




        //We set the pictures according to who won
        if(scoreJ1>scoreJ2)
        {
            gifJ1.setImageResource(R.drawable.overlord_victory);
            commentJ1.setText("You win ! Congratulations !");

            gifJ2.setImageResource(R.drawable.overlord_defeat);
            commentJ2.setText("You lose... Better luck next time !");

        }
        else if(scoreJ2>scoreJ1)
        {
            gifJ1.setImageResource(R.drawable.overlord_defeat);
            commentJ1.setText("You lose... Better luck next time !");

            gifJ2.setImageResource(R.drawable.overlord_victory);
            commentJ2.setText("You win ! Congratulations !");

        }
        else
        {
            gifJ1.setImageResource(R.drawable.overlord_draw);
            commentJ1.setText("Draw game, you were almost there !");

            gifJ2.setImageResource(R.drawable.overlord_draw);
            commentJ2.setText("Draw game, you were almost there !");

        }


        //Back to the menu when they are both ready for it
        menuJ1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                j1ready = !j1ready;
                if(j1ready)
                {
                    menuJ1.setText("Wait a second !");
                }
                else
                {
                    menuJ1.setText("Return to main menu !");
                }

                if(j1ready && j2ready)
                {
                    Intent party = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(party);
                }

            }
        });

        menuJ2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                j2ready = !j2ready;

                if(j2ready)
                {
                    menuJ2.setText("Wait a second !");
                }
                else
                {
                    menuJ2.setText("Return to main menu !");
                }
                if(j1ready && j2ready)
                {
                    Intent party = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(party);
                }

            }
        });


    }

}
