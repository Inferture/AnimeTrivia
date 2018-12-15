package com.example.q.animequizz;
import android.provider.BaseColumns;


public class AnimeContract {

    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + QuestionEntry.TABLE_NAME + " (" +
                    QuestionEntry._ID + " INTEGER PRIMARY KEY," +
                    QuestionEntry.COLUMN_NAME_QUESTION + " TEXT," +
                    QuestionEntry.COLUMN_NAME_RIGHTANSWER + " TEXT," +
                    QuestionEntry.COLUMN_NAME_FALSEANSWER1 + " TEXT," +
                    QuestionEntry.COLUMN_NAME_FALSEANSWER2 + " TEXT," +
                    QuestionEntry.COLUMN_NAME_FALSEANSWER3 + " TEXT," +
                    QuestionEntry.COLUMN_NAME_IMAGEURL + " TEXT," +
                    QuestionEntry.COLUMN_NAME_SUBJECT + " TEXT," +
                    QuestionEntry.COLUMN_NAME_TYPE + " INTEGER," +
                    QuestionEntry.COLUMN_NAME_MALID + " INTEGER)";

    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + QuestionEntry.TABLE_NAME;

    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private AnimeContract() {}

    /* Inner class that defines the table contents */
    public static class QuestionEntry implements BaseColumns {
        public static final String TABLE_NAME = "questions";
        public static final String COLUMN_NAME_QUESTION = "question";
        public static final String COLUMN_NAME_RIGHTANSWER = "rightanswer";
        public static final String COLUMN_NAME_FALSEANSWER1 = "falseanswer1";
        public static final String COLUMN_NAME_FALSEANSWER2= "falseanswer2";
        public static final String COLUMN_NAME_FALSEANSWER3= "falseanswer3";

        public static final String COLUMN_NAME_IMAGEURL= "imageurl";
        public static final String COLUMN_NAME_SUBJECT= "subject";

        public static final String COLUMN_NAME_TYPE= "type";
        public static final String COLUMN_NAME_MALID= "malid";
    }


}
