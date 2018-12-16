package com.example.q.animequizz;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.provider.BaseColumns;
import android.util.Log;

import org.json.JSONObject;

import java.util.Random;

public class CustomQuestionSearch extends AsyncTask<String, String, Cursor> {
/*Searches for a question in the database (custom questions created by the user)*/



    SoloQuestionActivity act;

    DuoQuestionActivity actDuo;

    int mode=0;//0: solo, 1: duo

    CustomQuestionSearch(SoloQuestionActivity act)
    {
        this.act=act;
        mode=0;
    }

    CustomQuestionSearch(DuoQuestionActivity act)
    {
        this.actDuo=act;
        mode=1;
    }




    public QuestionDbHelper qdh;
    protected Cursor doInBackground(String... a)  {


        SQLiteDatabase db = qdh.getWritableDatabase();

        String[] projection = {
                BaseColumns._COUNT,
        };
        int r=1;
        Cursor cursor=null;
        try
        {
            cursor=db.rawQuery("SELECT Count(*) from " + AnimeContract.QuestionEntry.TABLE_NAME +";",null);
            cursor.moveToFirst();
            int num = cursor.getInt(cursor.getColumnIndexOrThrow("Count(*)"));
            r=new Random().nextInt(num);
            Log.i("AnimeQuizz", "AnimeStuff: made a query, Count(*)=" + num);
        }
        catch(Exception e)
        {
            Log.i("AnimeQuizz", "AnimeStuff: error when making a query :" + e.toString());
        }




        projection = new String[]{
                BaseColumns._ID,
                AnimeContract.QuestionEntry.COLUMN_NAME_QUESTION,
                AnimeContract.QuestionEntry.COLUMN_NAME_RIGHTANSWER,
                AnimeContract.QuestionEntry.COLUMN_NAME_FALSEANSWER1,
                AnimeContract.QuestionEntry.COLUMN_NAME_FALSEANSWER2,
                AnimeContract.QuestionEntry.COLUMN_NAME_FALSEANSWER3,
                AnimeContract.QuestionEntry.COLUMN_NAME_IMAGEURL,
                AnimeContract.QuestionEntry.COLUMN_NAME_SUBJECT,
                AnimeContract.QuestionEntry.COLUMN_NAME_TYPE,
                AnimeContract.QuestionEntry.COLUMN_NAME_MALID
        };
        String selection = BaseColumns._ID + " = ?";

        try
        {
            cursor = db.query(
                    AnimeContract.QuestionEntry.TABLE_NAME,   // The table to query
                    projection,             // The array of columns to return (pass null to get all)
                    null,              // The columns for the WHERE clause
                    null,          // The values for the WHERE clause
                    null,                   // don't group the rows
                    null,                   // don't filter by row groups
                    null               // The sort order
            );
        }
        catch(Exception e)
        {
            Log.i("AnimeQuizz", "AnimeStuff: error when making a query :" + e.toString());
        }


        for(int i=0;i<=r;i++)
        {
            cursor.moveToNext();
        }







        return cursor;
    }

    @Override
    protected void onPostExecute(Cursor cursor) {

        super.onPostExecute(cursor);
        int id = cursor.getInt(cursor.getColumnIndexOrThrow(AnimeContract.QuestionEntry._ID));
        String question = cursor.getString(cursor.getColumnIndexOrThrow(AnimeContract.QuestionEntry.COLUMN_NAME_QUESTION));
        String rightanswer = cursor.getString(cursor.getColumnIndexOrThrow(AnimeContract.QuestionEntry.COLUMN_NAME_RIGHTANSWER));
        String falseanswer1 = cursor.getString(cursor.getColumnIndexOrThrow(AnimeContract.QuestionEntry.COLUMN_NAME_FALSEANSWER1));
        String falseanswer2 = cursor.getString(cursor.getColumnIndexOrThrow(AnimeContract.QuestionEntry.COLUMN_NAME_FALSEANSWER2));
        String falseanswer3 = cursor.getString(cursor.getColumnIndexOrThrow(AnimeContract.QuestionEntry.COLUMN_NAME_FALSEANSWER3));
        String imageurl = cursor.getString(cursor.getColumnIndexOrThrow(AnimeContract.QuestionEntry.COLUMN_NAME_IMAGEURL));
        String subject = cursor.getString(cursor.getColumnIndexOrThrow(AnimeContract.QuestionEntry.COLUMN_NAME_SUBJECT));
        int type = cursor.getInt(cursor.getColumnIndexOrThrow(AnimeContract.QuestionEntry.COLUMN_NAME_TYPE));
        int malid = cursor.getInt(cursor.getColumnIndexOrThrow(AnimeContract.QuestionEntry.COLUMN_NAME_MALID));

        if(mode==0)
        {
            act.LoadQuestion(question,rightanswer,falseanswer1,falseanswer2,falseanswer3,id);
        }
        else if(mode==1)
        {
            actDuo.LoadQuestion(question,rightanswer,falseanswer1,falseanswer2,falseanswer3,id);
        }


    }
}
