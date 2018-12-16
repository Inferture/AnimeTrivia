package com.example.q.animequizz;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import pl.droidsonroids.gif.GifImageView;

public class DuoAnswerActivity extends AppCompatActivity {
/*Activity that shows the answers in the split screen duo mode*/

    ImageView imageJ1;
    ImageView imageJ2;
    GifImageView loadingJ1;
    GifImageView loadingJ2;
    int scoreJ1;
    int scoreJ2;
    int numQuestion;
    Boolean j1ready=false;
    Boolean j2ready=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);

        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("ANIME_QUIZZ_PREF", Context.MODE_PRIVATE);
        int theme =sharedPref.getInt(getString(R.string.theme), R.style.AppTheme_LightTheme);
        setTheme(theme);

        setContentView(R.layout.activity_answer_duo);


        final Button okayJ1 = findViewById(R.id.btn_okay_j1);
        final Button okayJ2 = findViewById(R.id.btn_okay_j2);

        TextView ansJ1 = findViewById(R.id.answer_j1);
        TextView ansJ2 = findViewById(R.id.answer_j2);

        TextView questionJ1 = findViewById(R.id.question_j1);
        TextView questionJ2 = findViewById(R.id.question_j2);

        TextView propositionJ1 = findViewById(R.id.proposition_j1);
        TextView propositionJ2 = findViewById(R.id.proposition_j2);

        imageJ1 = findViewById(R.id.im_answer_j1);
        loadingJ1 = findViewById(R.id.im_searching_j1);

        imageJ2 = findViewById(R.id.im_answer_j2);
        loadingJ2 = findViewById(R.id.im_searching_j2);


        Bundle extras = getIntent().getExtras();
        String q = extras.getString("question");
        String a = extras.getString("answer");
        String p1 = extras.getString("propositionJ1");
        String p2 = extras.getString("propositionJ2");

        int winner = extras.getInt("winner");

        scoreJ1=extras.getInt("scorej1");
        scoreJ2=extras.getInt("scorej2");

        numQuestion=extras.getInt("num");

        //custom
        final int id = extras.getInt("questionid",-1);
        final Boolean custom = extras.getBoolean("custom");
        //\custom


        ansJ1.setText("Answer:\n" + a);
        ansJ2.setText("Answer:\n" + a);
        questionJ1.setText(q);
        questionJ2.setText(q);
        if(!p1.equals(""))
        {
            propositionJ1.setText("Your answer: " + p1);
        }
        else
        {
            propositionJ1.setText("");
        }

        if(!p2.equals(""))
        {
            propositionJ2.setText("Your answer: " + p2);
        }
        else
        {
            propositionJ2.setText("");
        }


        if(winner==1)
        {
            propositionJ1.setTextColor(Color.GREEN);
            propositionJ2.setTextColor(Color.RED);
        }
        else if(winner==2)
        {
            propositionJ1.setTextColor(Color.RED);
            propositionJ2.setTextColor(Color.GREEN);
        }
        else
        {
            propositionJ1.setTextColor(Color.RED);
            propositionJ2.setTextColor(Color.RED);
        }



        final ImageSearch is = new ImageSearch(this);
        final CustomImageSearch cis = new CustomImageSearch(this);

        loadingJ1.setVisibility(View.VISIBLE);
        loadingJ2.setVisibility(View.VISIBLE);

        imageJ1.setVisibility(View.INVISIBLE);
        imageJ2.setVisibility(View.INVISIBLE);


        if(custom)
        {
            cis.qdh=new QuestionDbHelper(getApplicationContext());
            cis.execute(id);
        }
        else
        {
            is.execute(q, a);
        }
        okayJ1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                j1ready=!j1ready;
                if(j1ready)
                {
                    okayJ1.setText("Not yet!");
                }
                else
                {
                    okayJ1.setText("Ready !");
                }

                if(j1ready && j2ready) {
                    Intent party = new Intent(getApplicationContext(), DuoQuestionActivity.class);
                    is.cancel(true);
                    party.putExtra("scorej1", scoreJ1);
                    party.putExtra("scorej2", scoreJ2);
                    party.putExtra("num", numQuestion + 1);
                    //custom
                    party.putExtra("questionid", id);
                    party.putExtra("custom", custom);
                    //\custom
                    startActivity(party);
                }
            }
        });


        okayJ2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                j2ready=!j2ready;
                if(j2ready)
                {
                    okayJ2.setText("Not yet!");
                }
                else
                {
                    okayJ2.setText("Ready !");
                }
                if(j1ready && j2ready) {
                    Intent party = new Intent(getApplicationContext(), DuoQuestionActivity.class);
                    is.cancel(true);
                    party.putExtra("scorej1", scoreJ1);
                    party.putExtra("scorej2", scoreJ2);
                    party.putExtra("num", numQuestion + 1);
                    //custom
                    party.putExtra("questionid", id);
                    party.putExtra("custom", custom);
                    //\custom
                    startActivity(party);
                }
            }
        });


    }

    public void LoadImage(Bitmap bm, final int type, final int malid)//, int malid puis set onclick
    {
        imageJ1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(type==1)
                {
                    Intent checkanime = new Intent(Intent.ACTION_VIEW, Uri.parse("https://myanimelist.net/anime/" + malid));
                    startActivity(checkanime);
                }
                else if(type==2)
                {
                    Intent checkmanga = new Intent(Intent.ACTION_VIEW, Uri.parse("https://myanimelist.net/manga/" + malid));
                    startActivity(checkmanga);
                }
                else if(type==3)
                {
                    Intent checkchar = new Intent(Intent.ACTION_VIEW, Uri.parse("https://myanimelist.net/character/" + malid));
                    startActivity(checkchar);
                }

            }
        });

        imageJ2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(type==1)
                {
                    Intent checkanime = new Intent(Intent.ACTION_VIEW, Uri.parse("https://myanimelist.net/anime/" + malid));
                    startActivity(checkanime);
                }
                else if(type==2)
                {
                    Intent checkmanga = new Intent(Intent.ACTION_VIEW, Uri.parse("https://myanimelist.net/manga/" + malid));
                    startActivity(checkmanga);
                }
                else if(type==3)
                {
                    Intent checkchar = new Intent(Intent.ACTION_VIEW, Uri.parse("https://myanimelist.net/character/" + malid));
                    startActivity(checkchar);
                }

            }
        });



        LoadImage(bm);


    }

    public void LoadImage(Bitmap bm)
    {


        try
        {
            imageJ1.setImageBitmap(bm);
            imageJ2.setImageBitmap(bm);
        }
        catch(Exception e)
        {
            Log.i("AnimeQuizz", "AnimeStuff: Erreur when loading bitmap:" + e.toString());
        }
        loadingJ1.setVisibility(View.INVISIBLE);
        loadingJ2.setVisibility(View.INVISIBLE);
        imageJ1.setVisibility(View.VISIBLE);
        imageJ2.setVisibility(View.VISIBLE);

    }


}
