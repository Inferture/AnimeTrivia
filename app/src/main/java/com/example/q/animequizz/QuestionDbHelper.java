package com.example.q.animequizz;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public class QuestionDbHelper extends SQLiteOpenHelper {
/*Helper to make it easier to do operations on the database (add/change/delete entry, create a table...)*/
    private static final String SQL_CREATE_ENTRIES = AnimeContract.SQL_CREATE_ENTRIES;


    private static final String SQL_DELETE_ENTRIES =AnimeContract.SQL_DELETE_ENTRIES;

    // If you change the database schema, you must increment the database version.

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "AnimeQuizz.db";

    public QuestionDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);

    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }


    public static long AddQuestion(SQLiteDatabase db, String question, String rightanswer, String falseanswer1, String falseanswer2, String falseanswer3, String imageurl, String subject, int type, int malid)
    {
        ContentValues values = new ContentValues();
        values.put(AnimeContract.QuestionEntry.COLUMN_NAME_QUESTION, question);
        values.put(AnimeContract.QuestionEntry.COLUMN_NAME_RIGHTANSWER, rightanswer);
        values.put(AnimeContract.QuestionEntry.COLUMN_NAME_FALSEANSWER1, falseanswer1);
        if(falseanswer2 != null && !falseanswer2.equals(""))
        {
            values.put(AnimeContract.QuestionEntry.COLUMN_NAME_FALSEANSWER2, falseanswer2);
        }
        if(falseanswer3 != null && !falseanswer3.equals(""))
        {
            values.put(AnimeContract.QuestionEntry.COLUMN_NAME_FALSEANSWER3, falseanswer3);
        }
        if(imageurl != null && !imageurl.equals(""))
        {
            values.put(AnimeContract.QuestionEntry.COLUMN_NAME_IMAGEURL, imageurl);
        }
        if(subject != null && !subject.equals(""))
        {
            values.put(AnimeContract.QuestionEntry.COLUMN_NAME_SUBJECT, subject);
        }
        if(type != -1)
        {
            values.put(AnimeContract.QuestionEntry.COLUMN_NAME_TYPE, type);
        }
        if(malid != -1)
        {
            values.put(AnimeContract.QuestionEntry.COLUMN_NAME_MALID, malid);
        }


        long newRowId = db.insert(AnimeContract.QuestionEntry.TABLE_NAME, null, values);

        return newRowId;
    }

    public static long ChangeQuestion(SQLiteDatabase db, long id, String question, String rightanswer, String falseanswer1, String falseanswer2, String falseanswer3, String imageurl, String subject, int type, int malid)
    {


        ContentValues values = new ContentValues();
        values.put(AnimeContract.QuestionEntry.COLUMN_NAME_QUESTION, question);
        values.put(AnimeContract.QuestionEntry.COLUMN_NAME_RIGHTANSWER, rightanswer);
        values.put(AnimeContract.QuestionEntry.COLUMN_NAME_FALSEANSWER1, falseanswer1);
        if(falseanswer2 != null && !falseanswer2.equals(""))
        {
            values.put(AnimeContract.QuestionEntry.COLUMN_NAME_FALSEANSWER2, falseanswer2);
        }
        else
        {
            values.putNull(AnimeContract.QuestionEntry.COLUMN_NAME_FALSEANSWER2);
        }
        if(falseanswer3 != null && !falseanswer3.equals(""))
        {
            values.put(AnimeContract.QuestionEntry.COLUMN_NAME_FALSEANSWER3, falseanswer3);
        }
        else
        {
            values.putNull(AnimeContract.QuestionEntry.COLUMN_NAME_FALSEANSWER3);
        }
        if(imageurl != null && !imageurl.equals(""))
        {
            values.put(AnimeContract.QuestionEntry.COLUMN_NAME_IMAGEURL, imageurl);
        }
        else
        {
            values.putNull(AnimeContract.QuestionEntry.COLUMN_NAME_IMAGEURL);
        }
        if(subject != null && !subject.equals(""))
        {
            values.put(AnimeContract.QuestionEntry.COLUMN_NAME_SUBJECT, subject);
        }
        else
        {
            values.putNull(AnimeContract.QuestionEntry.COLUMN_NAME_SUBJECT);
        }
        if(type != -1)
        {
            values.put(AnimeContract.QuestionEntry.COLUMN_NAME_TYPE, type);
        }
        else
        {
            values.put(AnimeContract.QuestionEntry.COLUMN_NAME_TYPE,-1);
        }
        if(malid != -1)
        {
            values.put(AnimeContract.QuestionEntry.COLUMN_NAME_MALID, malid);
        }
        else
        {
            values.put(AnimeContract.QuestionEntry.COLUMN_NAME_MALID,-1);
        }

        String selection = BaseColumns._ID +  " = ?";
        String[] selectionArgs = {Long.toString(id)};

        int count = db.update(
                AnimeContract.QuestionEntry.TABLE_NAME,
                values,
                selection,
                selectionArgs);

        return count;

    }

    public static long DeleteQuestion(SQLiteDatabase db, long id)
    {
        String selection = BaseColumns._ID +  " = ?";
        String[] selectionArgs = {Long.toString(id)};

        int count=db.delete(
                AnimeContract.QuestionEntry.TABLE_NAME,
                selection,
                selectionArgs
        );


        return count;

    }


}
