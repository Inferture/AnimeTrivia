package com.example.q.animequizz;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;

public class QuestionSearch extends AsyncTask<String, String, JSONObject> {
    /*Searches asynchronously for questions  in the normal mode (with questions from the online Open Trivia Database accessed to with a json API)*/

    SoloQuestionActivity act;

    DuoQuestionActivity actDuo;

    int mode=0;//0: solo, 1: duo

    QuestionSearch(SoloQuestionActivity act)
    {
        this.act=act;
        mode=0;
    }

    QuestionSearch(DuoQuestionActivity act)
    {
        this.actDuo=act;
        mode=1;
    }


    protected JSONObject doInBackground(String... strings)  {


        HttpURLConnection conn;
        URL url;

        try
        {
            url = new URL(strings[0]);
            conn = (HttpURLConnection)url.openConnection();
            String jsonAnswer="";
            try
            {
                publishProgress("trying to read stream");
                InputStream in = new BufferedInputStream(conn.getInputStream());
                jsonAnswer = readStream(in);


                publishProgress("already read stream");
            }
            catch(Exception e)
            {
                Log.i("AnimeQuizz", "AnimeStuff: Mistake happened: " + e.toString());
            }


            //

            Log.i("AnimeQuizz", "AnimeStuff:" + jsonAnswer);
            //
            return new JSONObject(jsonAnswer);

        }
        catch(Exception e)
        {
            Log.i("AnimeQuizz", "AnimeStuff: Mistake happened: " + e.toString());
        }
        return null;



    }


    @Override
    protected void onProgressUpdate(String... strings) {
        super.onProgressUpdate(strings);

        for(String s:strings)
        {
            Log.i("AnimeQuizz", "AnimeStuff: "+s);
        }

    }

    @Override
    protected void onPostExecute(JSONObject json) {

        String question=null;
        String rightAnswer=null;
        String falseAnswer1=null;
        String falseAnswer2=null;
        String falseAnswer3=null;

        try
        {
            JSONArray results = json.getJSONArray("results");
            int len = results.length();
            Random rn = new Random();
            int rand = rn.nextInt(len);
            JSONObject firstResult = results.getJSONObject(rand);

            question=firstResult.getString("question");

            rightAnswer = firstResult.getString("correct_answer");

            JSONArray incorrectAnswers = firstResult.getJSONArray("incorrect_answers");

            falseAnswer1=incorrectAnswers.getString(0);
            if(incorrectAnswers.length()>1)
            {
                falseAnswer2=incorrectAnswers.getString(1);
            }
            if(incorrectAnswers.length()>2)
            {
                falseAnswer3=incorrectAnswers.getString(2);
            }


        }
        catch(Exception e)
        {
            Log.i("AnimeQuizz", "AnimeStuff: PostExe1 Error:" + e.toString());
        }
        question=FormatString(question);
        rightAnswer=FormatString(rightAnswer);
        falseAnswer1=FormatString(falseAnswer1);
        falseAnswer2=FormatString(falseAnswer2);
        falseAnswer3=FormatString(falseAnswer3);

        try
        {
            if(mode==0)
            {
                act.LoadQuestion(question,rightAnswer,falseAnswer1,falseAnswer2,falseAnswer3);
            }
            else if(mode==1)
            {
                actDuo.LoadQuestion(question,rightAnswer,falseAnswer1,falseAnswer2,falseAnswer3);
            }

        }
        catch(Exception e)
        {
            Log.i("AnimeQuizz", "AnimeStuff: PostExe2 Error:" + e.toString());
        }

    }

    protected String FormatString(String s)
    {
        try
        {
            String s2 = s.replace("&quot;","\"");
            s2 = s2.replace("&#039;","'");
            s2 = s2.replace("&eacute;","é");
            s2 = s2.replace("&amp;","&");
            return s2;
        }
        catch(Exception e)
        {
            Log.i("AnimeQuizz", "AnimeStuffError Not formattable:" +s+  e.toString());
        }
        return null;
    }


    /*Reads an InputStream and returns the string*/
    String readStream(InputStream stream) throws IOException
    {

        StringBuilder sb = new StringBuilder();
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));


        for(String line = reader.readLine();line != null ;line=reader.readLine())
        {
            sb = sb.append(line);
        }

        String s = sb.toString();

        return s;

    }



}
