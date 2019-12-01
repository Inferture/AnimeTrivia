package com.example.q.animequizz;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.HashMap;
import java.util.Map;


/*Shows all the questions of the database and allows you to either create a new one or choose to modify an existing one */
public class CustomListActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("ANIME_QUIZZ_PREF", Context.MODE_PRIVATE);
        int theme =sharedPref.getInt(getString(R.string.theme), R.style.AppTheme_LightTheme);
        setTheme(theme);

        final Map<Integer, Integer> correspondance = new HashMap<Integer, Integer>(); //correspondance entre id dans la bdd et id dans le listview


        setContentView(R.layout.activity_custom_list);

        Log.i("AnimeQuizz", "AnimeStuff: Entered the custom class");

        //Access to the databaase
        QuestionDbHelper qdh = new QuestionDbHelper(getApplicationContext());
        SQLiteDatabase db = qdh.getWritableDatabase();

        //Layout elements
        ArrayAdapter<String> tab = new ArrayAdapter<String>(getApplicationContext(), R.layout.list_questions);

        final ListView listQuestions = (ListView) findViewById(R.id.lst_questions);

        Button btn_add = findViewById(R.id.btn_newquestion);

        Button btn_titlemenu = findViewById(R.id.btn_titlemenu);


        //Add a new question
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent party = new Intent(getApplicationContext(), AddCustomActivity.class);
                startActivity(party);
            }
        });

        //Back to the main activity
        btn_titlemenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent party = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(party);
            }
        });



        //We create the table if it does not already exists
        try
        {
            db.execSQL(AnimeContract.SQL_CREATE_ENTRIES);
            Log.i("AnimeQuizz", "AnimeStuff: Table created");
        }
        catch(Exception e)
        {
            //the table exists
            Log.i("AnimeQuizz", "AnimeStuff: Table already exists");
        }



        Log.i("AnimeQuizz", "AnimeStuff: got the database");
        /*
        //Example of question:
        qdh.AddQuestion(db, "In the anime \"Naruto Shippuden\", which of the following attribute does Naruto has an affinity for ?" ,
                "Wind", "Fire", "Water", "Thunder",
                "http://narutoblazing.gamea.co/file/content/15vfbxgh/6s3h6vzx/277816/18b6215a71cef9fc71bb6271973b48c35bcf938a_500.jpg",
                "Naruto", 3, 17);
        */


        Log.i("AnimeQuizz", "AnimeStuff: put stuff in it");
        //SQL reading the values
        String[] projection = {
                BaseColumns._ID,
                AnimeContract.QuestionEntry.COLUMN_NAME_QUESTION,
                AnimeContract.QuestionEntry.COLUMN_NAME_RIGHTANSWER
        };


        String selection = "";

        String[] selectionArgs = {};

        String sortOrder =
                AnimeContract.QuestionEntry._ID + " ASC";


        Log.i("AnimeQuizz", "AnimeStuff: prepared to look into it");

        Cursor cursor=null;
        try
        {
            cursor = db.query(
                    AnimeContract.QuestionEntry.TABLE_NAME,   // The table to query
                    projection,             // The array of columns to return (pass null to get all)
                    null,              // The columns for the WHERE clause
                    null,          // The values for the WHERE clause
                    null,                   // don't group the rows
                    null,                   // don't filter by row groups
                    sortOrder               // The sort order
            );
            Log.i("AnimeQuizz", "AnimeStuff: made a query");
        }
        catch(Exception e)
        {
            Log.i("AnimeQuizz", "AnimeStuff: error when making a query :" + e.toString());
        }


        try {
            int k=0;
            //Add all the questions
            while (cursor.moveToNext()) {
                long itemId = cursor.getLong(cursor.getColumnIndexOrThrow(AnimeContract.QuestionEntry._ID));
                String itemQuestion = cursor.getString(cursor.getColumnIndexOrThrow(AnimeContract.QuestionEntry.COLUMN_NAME_QUESTION));
                String itemRightAnswer = cursor.getString(cursor.getColumnIndexOrThrow(AnimeContract.QuestionEntry.COLUMN_NAME_RIGHTANSWER));

                Log.i("AnimeQuizz", "AnimeStuff: Item: ID:" + itemId + " question:" + itemQuestion + " Right answer:" + itemRightAnswer);

                //
                correspondance.put(k,(int)itemId);
                tab.add(itemQuestion);
                k++;
            }
            Log.i("AnimeQuizz", "AnimeStuff: what's next ?");
        }
        catch(Exception e)
        {
            Log.i("AnimeQuizz", "AnimeStuff: error when reading query result :" + e.toString());
        }

        //Sets the listview with the tab ArrayAdapter (tab contains all the questions)
        listQuestions.setAdapter(tab);

        //When clicking on an object, we go to the AddCustomActivity to modify the question.
        //We give the id so that the activity knows we are updating a question and not adding a new one
        //and which question it is.
        listQuestions.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("AnimeQuizz", "AnimeStuff: clicked the item :" + id);
                Log.i("AnimeQuizz", "AnimeStuff: number of children :" + listQuestions.getChildCount());
                Log.i("AnimeQuizz", "AnimeStuff: click the item :" + listQuestions.getChildAt((int)id));
                Log.i("AnimeQuizz", "AnimeStuff: clicked the item :" + listQuestions.getItemAtPosition((int)id));
                Log.i("AnimeQuizz", "AnimeStuff: clicked the item with the bdd id:" + correspondance.get((int)id));

                Intent party = new Intent(getApplicationContext(), AddCustomActivity.class);

                long itemID = correspondance.get((int)id);

                party.putExtra("id",itemID);
                startActivity(party);
            }
        });


        Log.i("AnimeQuizz", "AnimeStuff: number of children p:" + listQuestions.getChildCount());

        Log.i("AnimeQuizz", "AnimeStuff: over :");





    }
}
