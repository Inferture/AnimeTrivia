package com.example.q.animequizz;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.net.URL;

public class CreditsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
                Intent checkMAL = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/"));
                startActivity(checkMAL);

            }
        });


    }
}
