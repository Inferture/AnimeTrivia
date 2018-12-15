package com.example.q.animequizz;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;



public class BitmapDownloader extends AsyncTask<String, String, Bitmap> {


    //BitmapAdapter adapter = new BitmapAdapter(list.getContext());


    SoloAnswerActivity act;
    DuoAnswerActivity actDuo;
    int mode=0;

    public BitmapDownloader(SoloAnswerActivity act)
    {
        this.act=act;
        mode=0;
    }

    public BitmapDownloader(DuoAnswerActivity act)
    {
        this.actDuo=act;
        mode=1;
    }

    protected Bitmap doInBackground(String... strings)
    {
        URL url=null;
        HttpURLConnection conn=null;
        Bitmap bm =null;

        try
        {
            publishProgress("Trying to connect");
            String urlmedia = strings[0];
            url=new URL(urlmedia);

            Log.i("Anime", "Pomme1: "+urlmedia);
            conn = (HttpURLConnection)url.openConnection();
            Log.i("Anime", "Pomme1: Connexion opened"+urlmedia);

            InputStream stream = null;
            try
            {
                InputStream is = conn.getInputStream();

                stream = new BufferedInputStream(is);
            }
            catch (Exception e)
            {
                Log.i("Anime", "Pomme1: Error when getting stream "+ e.toString());
            }
            //InputStream stream = new BufferedInputStream(conn.getInputStream());

            publishProgress("Trying decode stream");
            Log.i("Anime", "Pomme1: Trying to decode stream "+urlmedia);
            bm=BitmapFactory.decodeStream(stream);

            stream.close();
            Log.i("Anime", "Pomme1: Decoded stream "+urlmedia);
            publishProgress("Decoded stream");

        }
        catch(Exception e)
        {
            publishProgress(e.toString());
            Log.i("Anime", "Pomme1: when dling bitmap "+e.toString());
        }



        return bm;

    }


    @Override
    protected void onProgressUpdate(String... strings) {
        super.onProgressUpdate(strings);
        for(String i:strings)
        {
            Log.i("AnimeQuizz", "AnimeStuff: " +i);
        }
    }


    @Override
    protected void onPostExecute(Bitmap bitmap) {


        super.onPostExecute(bitmap);

        if(mode==0)
        {
            act.LoadImage(bitmap);
        }
        else if(mode==1)
        {
            actDuo.LoadImage(bitmap);
        }

    }


}
