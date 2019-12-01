package com.example.q.animequizz;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import pl.droidsonroids.gif.GifImageView;

/*Activity that shows the questions in the split screen duo mode*/
public class DuoQuestionActivity extends AppCompatActivity {



    String realAnswer="D.D.D.";

    //Layout elements
    TextView questionJ1;
    TextView questionJ2;

    Button answer1J1;
    Button answer2J1;
    Button answer3J1;
    Button answer4J1;

    Button answer1J2;
    Button answer2J2;
    Button answer3J2;
    Button answer4J2;

    GifImageView loadingJ1;
    GifImageView loadingJ2;

    int scorej1;
    int scorej2;



    int maxQuestion = OptionsActivity.numQuestions;

    int numQuestion;

    String ansJ1="";
    String ansJ2="";
    int id;
    Boolean custom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("ANIME_QUIZZ_PREF", Context.MODE_PRIVATE);
        //Preferred number of questions
        maxQuestion = sharedPref.getInt(getString(R.string.questions_number), 10);
        //Theme
        int theme =sharedPref.getInt(getString(R.string.theme), R.style.AppTheme_LightTheme);
        setTheme(theme);


        //Data given when calling the activity
        Bundle extras = getIntent().getExtras();
        if(extras!=null)
        {
            scorej1 = extras.getInt("scorej1", 0);
            scorej2 = extras.getInt("scorej2", 0);
            numQuestion = extras.getInt("num", 1);
            id = extras.getInt("questionid", -1);
            custom=extras.getBoolean("custom", false);
        }
        else
        {
            id=-1;
            custom=false;
            scorej1=0;
            scorej2=0;
            numQuestion=1;
        }



        super.onCreate(savedInstanceState);




        setContentView(R.layout.activity_question_duo);

        //Layout elements
        loadingJ1 = findViewById(R.id.im_loadingj1);
        loadingJ1.setVisibility(View.INVISIBLE);

        loadingJ2 = findViewById(R.id.im_loadingj2);
        loadingJ2.setVisibility(View.INVISIBLE);


        questionJ1 = findViewById(R.id.questionj1);
        questionJ2 = findViewById(R.id.questionj2);

        answer1J1 = findViewById(R.id.answer1j1);
        answer2J1 = findViewById(R.id.answer2j1);
        answer3J1 = findViewById(R.id.answer3j1);
        answer4J1 = findViewById(R.id.answer4j1);

        answer1J2 = findViewById(R.id.answer1j2);
        answer2J2 = findViewById(R.id.answer2j2);
        answer3J2 = findViewById(R.id.answer3j2);
        answer4J2 = findViewById(R.id.answer4j2);

        TextView scoreTextJ1 = findViewById(R.id.scorej1);
        TextView scoreTextJ2 = findViewById(R.id.scorej2);

        TextView numeroTextJ1= findViewById(R.id.numquestionj1);
        TextView numeroTextJ2= findViewById(R.id.numquestionj2);

        //Scores and question number
        scoreTextJ1.setText("Score: (You) " + scorej1 + ":" + scorej2 + "(Opponent)");
        scoreTextJ2.setText("Score: (You) " + scorej2 + ":" + scorej1 + "(Opponent)");

        numeroTextJ1.setText("Question " + numQuestion +"/" + maxQuestion);
        numeroTextJ2.setText("Question " + numQuestion +"/" + maxQuestion);

        //Buttons to propose an answer

        answer1J1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ansJ1=answer1J1.getText().toString();
                if(answer1J1.getText().equals(realAnswer) )
                {
                    Log.i("AnimeQuizz", "AnimeStuff: Yeeey:");
                    answer1J1.setBackgroundColor(Color.GREEN);
                    ShowAnswer(ansJ1, ansJ2, 1);
                }
                else if(!ansJ2.equals(""))
                {
                    Log.i("AnimeQuizz", "AnimeStuff: Nooo:");
                    answer1J1.setBackgroundColor(Color.RED);
                    ShowAnswer(ansJ1, ansJ2, 0);
                }
                else
                {
                    Log.i("AnimeQuizz", "AnimeStuff: Nooo:");
                    Mute(1);
                    answer1J1.setBackgroundColor(Color.RED);

                }
            }
        });

        answer2J1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ansJ1=answer2J1.getText().toString();
                if(answer2J1.getText().equals(realAnswer) )
                {
                    Log.i("AnimeQuizz", "AnimeStuff: Yeeey:");
                    answer2J1.setBackgroundColor(Color.GREEN);
                    ShowAnswer(ansJ1, ansJ2, 1);
                }
                else if(!ansJ2.equals(""))
                {
                    Log.i("AnimeQuizz", "AnimeStuff: Nooo:");
                    answer2J1.setBackgroundColor(Color.RED);
                    ShowAnswer(ansJ1, ansJ2, 0);
                }
                else
                {
                    Log.i("AnimeQuizz", "AnimeStuff: Nooo:");
                    Mute(1);
                    answer2J1.setBackgroundColor(Color.RED);
                }
            }
        });
        answer3J1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ansJ1=answer3J1.getText().toString();
                if(answer3J1.getText().equals(realAnswer) )
                {
                    Log.i("AnimeQuizz", "AnimeStuff: Yeeey:");
                    answer3J1.setBackgroundColor(Color.GREEN);
                    ShowAnswer(ansJ1, ansJ2, 1);
                }
                else if(!ansJ2.equals(""))
                {
                    Log.i("AnimeQuizz", "AnimeStuff: Nooo:");
                    answer3J1.setBackgroundColor(Color.RED);
                    ShowAnswer(ansJ1, ansJ2, 0);
                }
                else
                {
                    Log.i("AnimeQuizz", "AnimeStuff: Nooo:");
                    Mute(1);
                    answer3J1.setBackgroundColor(Color.RED);
                }
            }
        });
        answer4J1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ansJ1=answer4J1.getText().toString();
                if(answer4J1.getText().equals(realAnswer) )
                {
                    Log.i("AnimeQuizz", "AnimeStuff: Yeeey:");
                    answer4J1.setBackgroundColor(Color.GREEN);
                    ShowAnswer(ansJ1, ansJ2, 1);
                }
                else if(!ansJ2.equals(""))
                {
                    Log.i("AnimeQuizz", "AnimeStuff: Nooo:");
                    answer4J1.setBackgroundColor(Color.RED);
                    ShowAnswer(ansJ1, ansJ2, 0);
                }
                else
                {
                    Log.i("AnimeQuizz", "AnimeStuff: Nooo:");
                    Mute(1);
                    answer4J1.setBackgroundColor(Color.RED);
                }
            }
        });

        answer1J2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ansJ2=answer1J2.getText().toString();
                if(answer1J2.getText().equals(realAnswer) )
                {
                    Log.i("AnimeQuizz", "AnimeStuff: Yeeey:");
                    answer1J2.setBackgroundColor(Color.GREEN);
                    ShowAnswer(ansJ1, ansJ2, 2);
                }
                else if(!ansJ1.equals(""))
                {
                    Log.i("AnimeQuizz", "AnimeStuff: Nooo:");
                    answer1J2.setBackgroundColor(Color.RED);
                    ShowAnswer(ansJ1, ansJ2, 0);
                }
                else
                {
                    Log.i("AnimeQuizz", "AnimeStuff: Nooo:");
                    Mute(2);
                    answer1J2.setBackgroundColor(Color.RED);

                }
            }
        });

        answer2J2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ansJ2=answer2J2.getText().toString();
                if(answer2J2.getText().equals(realAnswer) )
                {
                    Log.i("AnimeQuizz", "AnimeStuff: Yeeey:");
                    answer2J2.setBackgroundColor(Color.GREEN);
                    ShowAnswer(ansJ1, ansJ2, 2);
                }
                else if(!ansJ1.equals(""))
                {
                    Log.i("AnimeQuizz", "AnimeStuff: Nooo:");
                    answer2J2.setBackgroundColor(Color.RED);
                    ShowAnswer(ansJ1, ansJ2, 0);
                }
                else
                {
                    Log.i("AnimeQuizz", "AnimeStuff: Nooo:");
                    Mute(2);
                    answer2J2.setBackgroundColor(Color.RED);
                }
            }
        });
        answer3J2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ansJ2=answer3J2.getText().toString();
                if(answer3J2.getText().equals(realAnswer) )
                {
                    Log.i("AnimeQuizz", "AnimeStuff: Yeeey:");
                    answer3J2.setBackgroundColor(Color.GREEN);
                    ShowAnswer(ansJ1, ansJ2, 2);
                }
                else if(!ansJ1.equals(""))
                {
                    Log.i("AnimeQuizz", "AnimeStuff: Nooo:");
                    answer3J2.setBackgroundColor(Color.RED);
                    ShowAnswer(ansJ1, ansJ2, 0);
                }
                else
                {
                    Log.i("AnimeQuizz", "AnimeStuff: Nooo:");
                    Mute(2);
                    answer3J2.setBackgroundColor(Color.RED);
                }
            }
        });
        answer4J2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ansJ2=answer4J2.getText().toString();
                if(answer4J2.getText().equals(realAnswer) )
                {
                    Log.i("AnimeQuizz", "AnimeStuff: Yeeey:");
                    answer4J2.setBackgroundColor(Color.GREEN);
                    ShowAnswer(ansJ1, ansJ2, 2);
                }
                else if(!ansJ1.equals(""))
                {
                    Log.i("AnimeQuizz", "AnimeStuff: Nooo:");
                    answer4J2.setBackgroundColor(Color.RED);
                    ShowAnswer(ansJ1, ansJ2, 0);
                }
                else
                {
                    Log.i("AnimeQuizz", "AnimeStuff: Nooo:");
                    Mute(2);
                    answer4J2.setBackgroundColor(Color.RED);
                }
            }
        });

        NextQuestion();
    }

    //Go to the answer activity
    protected  void ShowAnswer(String answerGivenJ1, String answerGivenJ2, int winner)
    {
        Intent party = new Intent(getApplicationContext(), DuoAnswerActivity.class);
        party.putExtra("winner", winner);
        party.putExtra("answer", realAnswer);
        party.putExtra("question",  questionJ1.getText());
        party.putExtra("propositionJ1",  answerGivenJ1);
        party.putExtra("propositionJ2",  answerGivenJ2);
        party.putExtra("num",  numQuestion);
        party.putExtra("max",  maxQuestion);
        party.putExtra("questionid", id);
        party.putExtra("custom", custom);

        if(winner==1)
        {
            party.putExtra("scorej1",  scorej1+1);
            party.putExtra("scorej2",  scorej2);
        }
        else if(winner==2)
        {
            party.putExtra("scorej1",  scorej1);
            party.putExtra("scorej2",  scorej2+1);
        }
        else
        {
            party.putExtra("scorej1",  scorej1);
            party.putExtra("scorej2",  scorej2);
        }


        startActivity(party);
    }

    //If there are questions left, load another question, else, show the score
    protected  void NextQuestion()
    {

        if(numQuestion<=maxQuestion)
        {
            try
            {
                Log.i("AnimeQuizz", "AnimeStuff: Starting visibility");
                questionJ1.setVisibility(View.INVISIBLE);
                Log.i("AnimeQuizz", "AnimeStuff: question1 done visibility");
                questionJ2.setVisibility(View.INVISIBLE);
                Log.i("AnimeQuizz", "AnimeStuff: questions done");
                answer1J1.setVisibility(View.INVISIBLE);
                answer2J1.setVisibility(View.INVISIBLE);
                answer3J1.setVisibility(View.INVISIBLE);
                answer4J1.setVisibility(View.INVISIBLE);
                Log.i("AnimeQuizz", "AnimeStuff: answerj1 done");
                answer1J2.setVisibility(View.INVISIBLE);
                answer2J2.setVisibility(View.INVISIBLE);
                answer3J2.setVisibility(View.INVISIBLE);
                answer4J2.setVisibility(View.INVISIBLE);
                Log.i("AnimeQuizz", "AnimeStuff: answerj2 done");
                loadingJ1.setVisibility(View.VISIBLE);
                loadingJ2.setVisibility(View.VISIBLE);
                Log.i("AnimeQuizz", "AnimeStuff: loading done");

                QuestionSearch qs = new QuestionSearch(this);
                CustomQuestionSearch cqs = new CustomQuestionSearch(this);
                if(custom)
                {
                    cqs.qdh=new QuestionDbHelper(getApplicationContext());
                    cqs.execute();
                }
                else
                {
                    qs.execute("https://opentdb.com/api.php?amount=10&category=31");
                }


            }
            catch(Exception e)
            {
                Log.i("AnimeQuizz", "AnimeStuff: Error:" + e.toString());
            }
        }
        else
        {
            Intent party = new Intent(getApplicationContext(), DuoResultsActivity.class);

            party.putExtra("scorej1", scorej1);
            party.putExtra("scorej2", scorej2);
            party.putExtra("max", maxQuestion);

            startActivity(party);
        }




    }


    protected  void LoadQuestion(String question, String rightAnswer, String falseAnswer1, String falseAnswer2, String falseAnswer3, int questionid)
    {
        id=questionid;
        LoadQuestion( question,  rightAnswer,  falseAnswer1,  falseAnswer2,  falseAnswer3);
    }



    protected  void LoadQuestion(String question, String rightAnswer, String falseAnswer1, String falseAnswer2, String falseAnswer3)
    {
        answer1J1.setBackgroundColor(Color.GRAY);
        answer2J1.setBackgroundColor(Color.GRAY);
        answer3J1.setBackgroundColor(Color.GRAY);
        answer4J1.setBackgroundColor(Color.GRAY);

        answer1J2.setBackgroundColor(Color.GRAY);
        answer2J2.setBackgroundColor(Color.GRAY);
        answer3J2.setBackgroundColor(Color.GRAY);
        answer4J2.setBackgroundColor(Color.GRAY);


        realAnswer=rightAnswer;
        questionJ1.setText(question);
        questionJ2.setText(question);
        String[] ans={rightAnswer,falseAnswer1,falseAnswer2,falseAnswer3};


        loadingJ1.setVisibility(View.INVISIBLE);
        loadingJ2.setVisibility(View.INVISIBLE);

        questionJ1.setVisibility(View.VISIBLE);
        questionJ2.setVisibility(View.VISIBLE);

        answer1J1.setVisibility(View.VISIBLE);
        answer2J1.setVisibility(View.VISIBLE);

        answer1J2.setVisibility(View.VISIBLE);
        answer2J2.setVisibility(View.VISIBLE);

        if(ans[2]==null)
        {
            ans=new String[]{rightAnswer,falseAnswer1};
            answer3J1.setVisibility(View.INVISIBLE);
            answer4J1.setVisibility(View.INVISIBLE);

            answer3J2.setVisibility(View.INVISIBLE);
            answer4J2.setVisibility(View.INVISIBLE);

        }
        else if(ans[3]==null)
        {
            ans=new String[]{rightAnswer,falseAnswer1,falseAnswer2};

            answer3J1.setVisibility(View.VISIBLE);
            answer4J1.setVisibility(View.INVISIBLE);

            answer3J2.setVisibility(View.VISIBLE);
            answer4J2.setVisibility(View.INVISIBLE);
        }
        else
        {
            answer3J1.setVisibility(View.VISIBLE);
            answer4J1.setVisibility(View.VISIBLE);

            answer3J2.setVisibility(View.VISIBLE);
            answer4J2.setVisibility(View.VISIBLE);
        }




        String[] answers = Permute(ans);

        Button[] buzzersJ1={answer1J1, answer2J1,answer3J1,answer4J1};
        for(int i=0;i<answers.length;i++)
        {
            buzzersJ1[i].setText(answers[i]);
        }

        Button[] buzzersJ2={answer1J2, answer2J2,answer3J2,answer4J2};
        for(int i=0;i<answers.length;i++)
        {
            buzzersJ2[i].setText(answers[i]);
        }

        Log.i("AnimeQuizz", "AnimeStuff: Question loaded:");


    }

    //When someone gives a false answer, they cannot answer again so
    //they are "muted"
    protected void Mute(int i)
    {
        if(i==1)
        {
            answer1J1.setEnabled(false);
            answer2J1.setEnabled(false);
            answer3J1.setEnabled(false);
            answer4J1.setEnabled(false);

        }
        else if(i==2)
        {
            answer1J2.setEnabled(false);
            answer2J2.setEnabled(false);
            answer3J2.setEnabled(false);
            answer4J2.setEnabled(false);

        }
    }

    //To show the answer in a random order
    protected <T> T[] Permute(T[] tab)
    {
        T[] newTab = tab.clone();


        for(int i=0; i<tab.length; i++)
        {
            int r = (int)Math.floor(Math.random()*(i+1));
            T c = newTab[r];
            newTab[r]=newTab[i];
            newTab[i]=c;
        }
        return newTab;
    }

}
