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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


/*Activity used to make/modify/delete custom questions*/
public class AddCustomActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Theme
        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("ANIME_QUIZZ_PREF", Context.MODE_PRIVATE);
        int theme =sharedPref.getInt(getString(R.string.theme), R.style.AppTheme_LightTheme);
        setTheme(theme);
        setContentView(R.layout.activity_custom);

        //Gets the id, -1 if it's a new question
        Bundle extras = getIntent().getExtras();
        long idget=-1;
        if(extras != null)
        {
            idget = extras.getLong("id", -1);
        }

        final long id=idget;

        final QuestionDbHelper qdh = new QuestionDbHelper(getApplicationContext());
        final SQLiteDatabase db = qdh.getWritableDatabase();


        final Spinner spn_maltype = (Spinner) findViewById(R.id.spn_maltype);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.mal_types, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spn_maltype.setAdapter(adapter);
        spn_maltype.setEnabled(false);


        //Get elements in the layout

        final TextView txt_question = findViewById(R.id.txt_question);

        final TextView txt_answer = findViewById(R.id.txt_rightanswer);

        final TextView txt_falseanswer1 = findViewById(R.id.txt_falseanswer1);


        final TextView txt_prop1 = findViewById(R.id.txt_rightanswer);

        final TextView txt_prop2 = findViewById(R.id.txt_falseanswer1);


        final Button btn_del = findViewById(R.id.btn_delete);


        final Button btn_prop3 =  findViewById(R.id.btn_prop3);
        final TextView txt_prop3 = findViewById(R.id.txt_falseanswer2);

        final Button btn_prop4 =  findViewById(R.id.btn_prop4);
        final TextView txt_prop4 = findViewById(R.id.txt_falseanswer3);

        final Button btn_imageurl =  findViewById(R.id.btn_imageurl);
        final TextView txt_imageurl = findViewById(R.id.txt_imageurl);

        final Button btn_malid =  findViewById(R.id.btn_malid);
        final TextView txt_malid = findViewById(R.id.txt_malid);

        final Button btn_subject =  findViewById(R.id.btn_subject);
        final TextView txt_subject = findViewById(R.id.txt_subject);


        final Button btn_back =  findViewById(R.id.btn_backcustom);

        final Button btn_submit =  findViewById(R.id.btn_submitquestion);

        final Button btn_maltype =  findViewById(R.id.btn_maltype);


        btn_del.setEnabled(false);
        if(id>=0)//Modifying an existing element
        {
            //Taking the existing data using a query
            String[] projection = {
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

            String[] selectionArgs = {String.valueOf(id)};

            String sortOrder =
                    AnimeContract.QuestionEntry._ID + " ASC";


            Log.i("AnimeQuizz", "AnimeStuff: prepared to look into it");

            Cursor cursor=null;

            //Trying to make the query
            try
            {
                cursor = db.query(
                        AnimeContract.QuestionEntry.TABLE_NAME,   // The table to query
                        projection,             // The array of columns to return (pass null to get all)
                        selection,              // The columns for the WHERE clause
                        selectionArgs,          // The values for the WHERE clause
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

            //Storing the query's result to display in the view
            cursor.moveToNext();

            String question = cursor.getString(cursor.getColumnIndexOrThrow(AnimeContract.QuestionEntry.COLUMN_NAME_QUESTION));
            String rightanswer = cursor.getString(cursor.getColumnIndexOrThrow(AnimeContract.QuestionEntry.COLUMN_NAME_RIGHTANSWER));
            String falseanswer1 = cursor.getString(cursor.getColumnIndexOrThrow(AnimeContract.QuestionEntry.COLUMN_NAME_FALSEANSWER1));
            String falseanswer2 = cursor.getString(cursor.getColumnIndexOrThrow(AnimeContract.QuestionEntry.COLUMN_NAME_FALSEANSWER2));
            String falseanswer3 = cursor.getString(cursor.getColumnIndexOrThrow(AnimeContract.QuestionEntry.COLUMN_NAME_FALSEANSWER3));
            String imageurl = cursor.getString(cursor.getColumnIndexOrThrow(AnimeContract.QuestionEntry.COLUMN_NAME_IMAGEURL));
            String subject = cursor.getString(cursor.getColumnIndexOrThrow(AnimeContract.QuestionEntry.COLUMN_NAME_SUBJECT));
            int type = cursor.getInt(cursor.getColumnIndexOrThrow(AnimeContract.QuestionEntry.COLUMN_NAME_TYPE));
            int malid = cursor.getInt(cursor.getColumnIndexOrThrow(AnimeContract.QuestionEntry.COLUMN_NAME_MALID));


            //Activating and deactivating elements according to the current data
            if(falseanswer2 != null && !falseanswer2.equals(""))
            {
                txt_prop3.setEnabled(true);
                btn_prop3.setText("Delete proposition 3");
                btn_prop4.setEnabled(true);
            }

            if(falseanswer3 != null && !falseanswer3.equals(""))
            {
                txt_prop4.setEnabled(true);
                btn_prop4.setText("Delete proposition 4");
            }
            if(imageurl != null && !imageurl.equals(""))
            {
                txt_imageurl.setEnabled(true);
                btn_imageurl.setText("Delete image url");
            }
            if(subject != null && !subject.equals(""))
            {
                txt_subject.setEnabled(true);
                btn_subject.setText("Delete subject");
            }
            if(type >=0)
            {
                spn_maltype.setEnabled(true);
                btn_maltype.setText("Delete MyAnimeList type");
            }
            if(malid >=0)
            {
                txt_malid.setEnabled(true);
                btn_malid.setText("Delete MyAnimeList ID");
                txt_malid.setText(String.valueOf(malid));
            }

            //Setting the components values with the data from the query
            txt_question.setText(question);
            txt_prop1.setText(rightanswer);
            txt_prop2.setText(falseanswer1);
            txt_prop3.setText(falseanswer2);
            txt_prop4.setText(falseanswer3);
            txt_imageurl.setText(imageurl);
            txt_subject.setText(subject);


            if(type>0 && type<=3)
            {
                spn_maltype.setSelection(type-1);
            }

            btn_del.setEnabled(true);
            btn_del.setVisibility(View.VISIBLE);



            btn_submit.setText("Modify the question !");
            Log.i("AnimeQuizz", "AnimeStuff: Item: ID:" + id + " question:" + question + " Right answer:" + rightanswer +"!!!!!!!");



        }

        Log.i("AnimeQuizz", "AnimeStuff: Made it out alive ?");

        //Setting buttons that can enable or disable elements

        btn_prop3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Boolean proposition3Enabled = !txt_prop3.isEnabled();
                txt_prop3.setEnabled(proposition3Enabled);
                btn_prop4.setEnabled(proposition3Enabled);

                if(proposition3Enabled)
                {

                    btn_prop3.setText("Delete proposition 3");

                }
                else
                {
                    btn_prop4.setText("Add proposition 4");
                    txt_prop4.setEnabled(false);
                    btn_prop3.setText("Add proposition 3");
                }
            }
        });

        Log.i("AnimeQuizz", "AnimeStuff: One button defined ");
        btn_prop4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Boolean proposition4Enabled = !txt_prop4.isEnabled();
                txt_prop4.setEnabled(proposition4Enabled);
                if(proposition4Enabled)
                {
                    btn_prop4.setText("Delete proposition 4");

                }
                else
                {
                    btn_prop4.setText("Add proposition 4");
                }
            }
        });

        btn_subject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Boolean subjectEnabled = !txt_subject.isEnabled();
                txt_subject.setEnabled(subjectEnabled);
                if(subjectEnabled)
                {
                    btn_subject.setText("Delete subject");

                }
                else
                {
                    btn_subject.setText("Add subject");
                }
            }
        });


        btn_imageurl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Boolean imageUrlEnabled = !txt_imageurl.isEnabled();
                txt_imageurl.setEnabled(imageUrlEnabled);
                if(imageUrlEnabled)
                {
                    btn_imageurl.setText("Delete image url");

                }
                else
                {
                    btn_imageurl.setText("Add image url");
                }
            }
        });

        btn_malid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Boolean malIdEnabled = !txt_malid.isEnabled();
                txt_malid.setEnabled(malIdEnabled);
                if(malIdEnabled)
                {
                    btn_malid.setText("Delete MyAnimeList ID");

                }
                else
                {
                    btn_malid.setText("Add MyAnimeList ID");
                }
            }
        });

        btn_maltype.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Boolean malTypeEnabled = !spn_maltype.isEnabled();
                spn_maltype.setEnabled(malTypeEnabled);
                if(malTypeEnabled)
                {
                    btn_maltype.setText("Delete MyAnimeList type");

                }
                else
                {
                    btn_maltype.setText("Add MyAnimeList type");
                }
            }
        });

        //Back to the CustomListActivity
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent party = new Intent(getApplicationContext(), CustomListActivity.class);
                startActivity(party);
            }
        });

        //Delete the current question
        btn_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                QuestionDbHelper.DeleteQuestion(db, id);
                Intent party = new Intent(getApplicationContext(), CustomListActivity.class);
                Toast.makeText(getApplicationContext(), "Question deleted !",Toast.LENGTH_SHORT).show();
                startActivity(party);
            }
        });


        //Submit the current question
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String question=txt_question.getText().toString();
                String answer = txt_answer.getText().toString();
                String falseanswer1 = txt_falseanswer1.getText().toString();

                String falseanswer2 =null;
                if(txt_prop3.isEnabled())
                {
                     falseanswer2=txt_prop3.getText().toString();
                }

                String falseanswer3 =null;
                if(txt_prop4.isEnabled())
                {
                    falseanswer3=txt_prop4.getText().toString();
                }

                String imageurl =null;
                if(txt_imageurl.isEnabled())
                {
                    Log.i("AnimeQuizz", "AnimeStuff: Image url enabled ");
                    imageurl=txt_imageurl.getText().toString();
                }

                String subject =null;
                if(txt_subject.isEnabled())
                {
                    subject=txt_subject.getText().toString();
                }

                String type = spn_maltype.getSelectedItem().toString();

                int typeid=-1;
                if(spn_maltype.isEnabled())
                {
                    Log.i("AnimeQuizz", "AnimeStuff: Spinner enabled ");
                    if(type.equals("Anime"))
                    {
                        typeid=1;
                    }
                    else if(type.equals("Manga/Novel"))
                    {
                        typeid=2;
                    }
                    else if(type.equals("Character"))
                    {
                        typeid=3;
                    }
                }


                int malid =-1;
                if(txt_malid.isEnabled())
                {
                    Log.i("AnimeQuizz", "AnimeStuff: MALID enabled ");
                    try
                    {
                        malid=Integer.parseInt(txt_malid.getText().toString());
                    }
                    catch(Exception e)
                    {
                        Log.i("AnimeQuizz", "AnimeStuff: Error: invalid text for an integer ");
                    }

                }


                if(question==null || question.equals(""))
                {
                    Toast.makeText(getApplicationContext(), "Question missing !",Toast.LENGTH_SHORT).show();
                }
                else if(answer==null || answer.equals(""))
                {
                    Toast.makeText(getApplicationContext(), "Question missing !",Toast.LENGTH_SHORT).show();
                }
                else if(falseanswer3 != null && !falseanswer3.equals("") && (falseanswer2 == null || falseanswer2.equals("")))
                {
                    Toast.makeText(getApplicationContext(), "Proposition 3 without proposition 2 !",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    if(id<=0)
                    {
                        QuestionDbHelper.AddQuestion(db, question, answer, falseanswer1, falseanswer2, falseanswer3, imageurl, subject, typeid, malid);
                    }
                    else
                    {
                        //Modify the value of question of id
                        QuestionDbHelper.ChangeQuestion(db, id, question, answer, falseanswer1, falseanswer2, falseanswer3, imageurl, subject, typeid, malid);
                    }




                    Intent party = new Intent(getApplicationContext(), CustomListActivity.class);
                    if(id >=0)
                    {
                        Toast.makeText(getApplicationContext(), "Question modified !",Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(), "Question added !",Toast.LENGTH_SHORT).show();
                    }

                    startActivity(party);
                }


            }
        });


        Log.i("AnimeQuizz", "AnimeStuff: done ");


    }
}
