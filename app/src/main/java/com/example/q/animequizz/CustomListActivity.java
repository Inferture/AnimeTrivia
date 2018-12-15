package com.example.q.animequizz;

import android.content.Intent;
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
import android.widget.TextView;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;

public class CustomListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final Map<Integer, Integer> correspondance = new HashMap<Integer, Integer>(); //correspondance entre id dans la bdd et id dans le listview


        setContentView(R.layout.activity_custom_list);

        Log.i("AnimeQuizz", "AnimeStuff: Entered the custom class");

        QuestionDbHelper qdh = new QuestionDbHelper(getApplicationContext());
        SQLiteDatabase db = qdh.getWritableDatabase();


        ArrayAdapter<String> tab = new ArrayAdapter<String>(getApplicationContext(), R.layout.list_questions);

        final ListView listQuestions = (ListView) findViewById(R.id.lst_questions);

        Button btn_add = findViewById(R.id.btn_newquestion);

        Button btn_titlemenu = findViewById(R.id.btn_titlemenu);

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent party = new Intent(getApplicationContext(), AddCustomActivity.class);
                startActivity(party);
            }
        });


        btn_titlemenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent party = new Intent(getApplicationContext(), SoloQuestionActivity.class);
                Intent party = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(party);
            }
        });








        //On crée la table si elle n'existe pas
        try
        {
            db.execSQL(AnimeContract.SQL_CREATE_ENTRIES);
            Log.i("AnimeQuizz", "AnimeStuff: Table created");
        }
        catch(Exception e)
        {
            //la table existe
            Log.i("AnimeQuizz", "AnimeStuff: Table already exists");
        }



        Log.i("AnimeQuizz", "AnimeStuff: got the database");
        /*qdh.AddQuestion(db, "In the anime \"Naruto Shippuden\", which of the following attribute does Naruto has an affinity for ?" ,
                "Wind", "Fire", "Water", "Thunder",
                "http://narutoblazing.gamea.co/file/content/15vfbxgh/6s3h6vzx/277816/18b6215a71cef9fc71bb6271973b48c35bcf938a_500.jpg",
                "Naruto", 3, 17);*/


        Log.i("AnimeQuizz", "AnimeStuff: put stuff in it");
        //SQL lecture des valeurs
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
        /*Après désinstallation puis réinstallation: table existe toujours, pas les données, créer la table si elle n'existe pas.*/

        /*for(int i=0;i<170;i++)
        {

            tab.add("Haha " + i);
        }*/




        listQuestions.setAdapter(tab);

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

        /*for(int i=0; i<listQuestions.getChildCount(); i++)
        {
            TextView tv =null;
            Log.i("AnimeQuizz", "AnimeStuff: adding touch to the textview :");

                //tv = (TextView)listQuestions.getItemAtPosition(i);
                tv= (TextView)listQuestions.getChildAt(i);




            Log.i("AnimeQuizz", "AnimeStuff: 1st step for touch :");

            try
            {
                tv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent party = new Intent(getApplicationContext(), AddCustomActivity.class);
                        startActivity(party);
                    }
                });
            }

            catch(Exception e)
            {
                Log.i("AnimeQuizz", "AnimeStuff: error when doing stuff :" + e.toString());
            }

            Log.i("AnimeQuizz", "AnimeStuff: added touch :");
        }*/

        Log.i("AnimeQuizz", "AnimeStuff: over :");





    }
}
