package com.example.q.animequizz;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.net.URL;

public class CreditsActivity extends AppCompatActivity {
    /*Activity to credit resources used etc...*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("ANIME_QUIZZ_PREF", Context.MODE_PRIVATE);
        int theme =sharedPref.getInt(getString(R.string.theme), R.style.AppTheme_LightTheme);
        setTheme(theme);

        setContentView(R.layout.activity_credits);

        ImageView im_mal = findViewById(R.id.im_mal);
        TextView txt_mal = findViewById(R.id.txt_mal);

        TextView txt_jikan = findViewById(R.id.txt_jikan);

        ImageView im_trivia =  findViewById(R.id.im_opentrivia);
        TextView txt_trivia = findViewById(R.id.txt_opentrivia);

        ImageView im_loadingio =  findViewById(R.id.im_loadingio);
        TextView txt_loadingio = findViewById(R.id.txt_loadingio);

        ImageView im_overlordppp =  findViewById(R.id.im_overlordppp);
        TextView txt_overlordppp = findViewById(R.id.txt_overlordppp);


        TextView txt_sourcecode = findViewById(R.id.txt_sourcecode);

        Button btn_title = findViewById(R.id.btn_ctitle);


        im_mal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Browser  https://myanimelist.net/
                Intent checkMAL = new Intent(Intent.ACTION_VIEW, Uri.parse("https://myanimelist.net/"));
                startActivity(checkMAL);

            }
        });
        txt_mal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Browser  https://myanimelist.net/
                Intent checkMAL = new Intent(Intent.ACTION_VIEW, Uri.parse("https://myanimelist.net/"));
                startActivity(checkMAL);

            }
        });

        txt_jikan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Browser  https://myanimelist.net/
                Intent checkMAL = new Intent(Intent.ACTION_VIEW, Uri.parse("https://jikan.moe/"));
                startActivity(checkMAL);

            }
        });

        im_trivia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Browser  https://myanimelist.net/
                Intent checkMAL = new Intent(Intent.ACTION_VIEW, Uri.parse("https://opentdb.com/"));
                startActivity(checkMAL);

            }
        });
        txt_trivia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Browser  https://myanimelist.net/
                Intent checkMAL = new Intent(Intent.ACTION_VIEW, Uri.parse("https://opentdb.com/"));
                startActivity(checkMAL);

            }
        });


        im_loadingio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Browser  https://myanimelist.net/
                Intent checkMAL = new Intent(Intent.ACTION_VIEW, Uri.parse("https://loading.io/"));
                startActivity(checkMAL);

            }
        });
        txt_loadingio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Browser  https://myanimelist.net/
                Intent checkMAL = new Intent(Intent.ACTION_VIEW, Uri.parse("https://loading.io/"));
                startActivity(checkMAL);

            }
        });


        im_overlordppp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Browser  https://myanimelist.net/
                Intent checkMAL = new Intent(Intent.ACTION_VIEW, Uri.parse("https://myanimelist.net/anime/31138/"));
                startActivity(checkMAL);

            }
        });
        txt_overlordppp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Browser  https://myanimelist.net/
                Intent checkMAL = new Intent(Intent.ACTION_VIEW, Uri.parse("https://myanimelist.net/anime/31138/"));
                startActivity(checkMAL);

            }
        });


        txt_sourcecode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Browser  https://myanimelist.net/
                Intent checkMAL = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/Inferture/AnimeQuizz/tree/efbcfc3dbaf55321c4dec7e680a87d83ae5c994a/"));
                startActivity(checkMAL);

            }
        });

        btn_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Browser  https://myanimelist.net/
                Intent party = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(party);

            }
        });
    }
}
