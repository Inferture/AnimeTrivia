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

public class SoloAnswerActivity extends AppCompatActivity {
/*Activity that shows the answers in the solo mode*/

    ImageView image;
    GifImageView loading;
    int score;
    int numQuestion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);

        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("ANIME_QUIZZ_PREF", Context.MODE_PRIVATE);
        int theme =sharedPref.getInt(getString(R.string.theme), R.style.AppTheme_LightTheme);
        setTheme(theme);

        setContentView(R.layout.activity_answer);

        Button title = findViewById(R.id.btn_qtitlemenu);
        Button next = findViewById(R.id.btn_next);
        TextView ans = findViewById(R.id.answer);
        TextView question = findViewById(R.id.question);
        TextView proposition = findViewById(R.id.proposition);
        image = findViewById(R.id.im_answer);
        loading = findViewById(R.id.im_searching);

        Bundle extras = getIntent().getExtras();
        String q = extras.getString("question");
        String a = extras.getString("answer");
        String p = extras.getString("proposition");
        Boolean b = extras.getBoolean("right");

        //custom
        final int id = extras.getInt("questionid",-1);
        final Boolean custom = extras.getBoolean("custom");
        //\custom
        score=extras.getInt("score");
        numQuestion=extras.getInt("num");

        ans.setText("Answer:\n" + a);
        question.setText("Question:\n" + q);
        proposition.setText("Your answer: " + p);



        if(b)
        {
            proposition.setTextColor(Color.GREEN);
        }
        else
        {
            proposition.setTextColor(Color.RED);
        }


        final ImageSearch is = new ImageSearch(this);
        final CustomImageSearch cis = new CustomImageSearch(this);
        loading.setVisibility(View.VISIBLE);
        image.setVisibility(View.INVISIBLE);

        //custom
        if(custom)
        {
            cis.qdh=new QuestionDbHelper(getApplicationContext());
            cis.execute(id);
        }
        else
        {
            is.execute(q, a);
        }
        //\custom

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent party = new Intent(getApplicationContext(), SoloQuestionActivity.class);
                is.cancel(true);
                party.putExtra("score",  score);
                party.putExtra("num",  numQuestion+1);

                //custom
                party.putExtra("questionid", id);
                party.putExtra("custom", custom);
                //\custom

                startActivity(party);


            }
        });
        title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent party = new Intent(getApplicationContext(), MainActivity.class);
                is.cancel(true);
                startActivity(party);
            }
        });

    }

    public void LoadImage(Bitmap bm, final int type, final int malid)
    {
        Log.i("AnimeQuizz", "AnimeStuff: About to link to mal page:  type " + type +" mal id " +malid);
        image.setOnClickListener(new View.OnClickListener() {
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
        loading.setVisibility(View.INVISIBLE);
        image.setVisibility(View.VISIBLE);
        try
        {
            image.setImageBitmap(bm);
        }
        catch(Exception e)
        {
            Log.i("AnimeQuizz", "AnimeStuff: Erreur when loading bitmap:" + e.toString());
        }

    }


}
