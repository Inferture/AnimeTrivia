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


import pl.droidsonroids.gif.GifImageView;

/*Activity that shows the questions in the solo mode*/
public class SoloQuestionActivity extends AppCompatActivity {


    String realAnswer="D.D.D.";

    TextView question;

    Button answer1;
    Button answer2;
    Button answer3;
    Button answer4;
    GifImageView loading;

    int score;

    int maxQuestion=OptionsActivity.numQuestions;
    int numQuestion;
    int id;
    Boolean custom;
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("ANIME_QUIZZ_PREF", Context.MODE_PRIVATE);

        //Theme
        int theme =sharedPref.getInt(getString(R.string.theme), R.style.AppTheme_LightTheme);
        setTheme(theme);

        //Number of questions
        maxQuestion = sharedPref.getInt(getString(R.string.questions_number), 10);

        Bundle extras = getIntent().getExtras();

        //Parameters
        if(extras!=null)
        {
            score = extras.getInt("score", 0);
            numQuestion = extras.getInt("num", 1);
            id = extras.getInt("questionid", -1);
            custom=extras.getBoolean("custom", false);
        }
        else
        {
            id=-1;
            score=0;
            numQuestion=1;
            custom=false;
        }



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_solo);

        //Layout elements
        loading = findViewById(R.id.im_loading);
        loading.setVisibility(View.INVISIBLE);
        question = findViewById(R.id.question);

        answer1 = findViewById(R.id.answer1);
        answer2 = findViewById(R.id.answer2);
        answer3 = findViewById(R.id.answer3);
        answer4 = findViewById(R.id.btn_next);

        TextView scoreText = findViewById(R.id.score);
        TextView numeroText= findViewById(R.id.numquestion);

        scoreText.setText("Score: " + score);
        numeroText.setText("Question " + numQuestion +"/" + maxQuestion);

        //Buttons used to give an answer

        answer1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(answer1.getText().equals(realAnswer))
                {
                    Log.i("AnimeQuizz", "AnimeStuff: Yeeey:");
                    answer1.setBackgroundColor(Color.GREEN);
                    ShowAnswer(answer1.getText().toString(), true);
                }
                else
                {
                    Log.i("AnimeQuizz", "AnimeStuff: Nooo:");
                    answer1.setBackgroundColor(Color.RED);
                    ShowAnswer(answer1.getText().toString(), false);
                }
            }
        });
        answer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(answer2.getText().equals(realAnswer))
                {
                    Log.i("AnimeQuizz", "AnimeStuff: Yeeey:");
                    answer2.setBackgroundColor(Color.GREEN);
                    ShowAnswer(answer2.getText().toString(), true);
                }
                else
                {
                    Log.i("AnimeQuizz", "AnimeStuff: Nooo:");
                    answer2.setBackgroundColor(Color.RED);
                    ShowAnswer(answer2.getText().toString(), false);
                }
            }
        });
        answer3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(answer3.getText().equals(realAnswer))
                {
                    Log.i("AnimeQuizz", "AnimeStuff: Yeeey:");
                    answer3.setBackgroundColor(Color.GREEN);
                    ShowAnswer(answer3.getText().toString(), true);
                }
                else
                {
                    Log.i("AnimeQuizz", "AnimeStuff: Nooo:");
                    answer3.setBackgroundColor(Color.RED);
                    ShowAnswer(answer3.getText().toString(), false);
                }
            }
        });
        answer4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(answer4.getText().equals(realAnswer))
                {
                    Log.i("AnimeQuizz", "AnimeStuff: Yeeey:");
                    answer4.setBackgroundColor(Color.GREEN);
                    ShowAnswer(answer4.getText().toString(), true);
                }
                else
                {
                    Log.i("AnimeQuizz", "AnimeStuff: Nooo:");
                    answer4.setBackgroundColor(Color.RED);
                    ShowAnswer(answer4.getText().toString(), false);
                }
            }
        });

        NextQuestion();



    }

    //Show the true answer and the anwer given in the AnswerActivity
    protected  void ShowAnswer(String answerGiven, boolean right)
    {
        Intent party = new Intent(getApplicationContext(), SoloAnswerActivity.class);
        party.putExtra("right", right);
        party.putExtra("answer", realAnswer);
        party.putExtra("question",  question.getText());
        party.putExtra("proposition",  answerGiven);
        party.putExtra("num",  numQuestion);
        party.putExtra("max",  maxQuestion);
        party.putExtra("questionid", id);
        party.putExtra("custom", custom);
        if(right)
        {
            party.putExtra("score",  score+1);
        }
        else
        {
            party.putExtra("score",  score);
        }


        startActivity(party);
    }

    //Gets to the next question if there is still at least a question left
    //or gets to the results
    protected  void NextQuestion()
    {

        if(numQuestion<=maxQuestion)
        {
            try
            {
                question.setVisibility(View.INVISIBLE);
                answer1.setVisibility(View.INVISIBLE);
                answer2.setVisibility(View.INVISIBLE);
                answer3.setVisibility(View.INVISIBLE);
                answer4.setVisibility(View.INVISIBLE);
                loading.setVisibility(View.VISIBLE);



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
            Intent party = new Intent(getApplicationContext(), SoloResultsActivity.class);
            party.putExtra("score", score);
            party.putExtra("max", maxQuestion);
            startActivity(party);
        }




    }

    /*Loads a question in the layout*/

    protected  void LoadQuestion(String question, String rightAnswer, String falseAnswer1, String falseAnswer2, String falseAnswer3, int questionid)
    {
        id=questionid;
        LoadQuestion( question,  rightAnswer,  falseAnswer1,  falseAnswer2,  falseAnswer3);
    }

    protected  void LoadQuestion(String question, String rightAnswer, String falseAnswer1, String falseAnswer2, String falseAnswer3)
    {
        answer1.setBackgroundColor(Color.GRAY);
        answer2.setBackgroundColor(Color.GRAY);
        answer3.setBackgroundColor(Color.GRAY);
        answer4.setBackgroundColor(Color.GRAY);

        realAnswer=rightAnswer;
        this.question.setText(question);
        String[] ans={rightAnswer,falseAnswer1,falseAnswer2,falseAnswer3};


        loading.setVisibility(View.INVISIBLE);
        this.question.setVisibility(View.VISIBLE);
        answer1.setVisibility(View.VISIBLE);
        answer2.setVisibility(View.VISIBLE);

        if(ans[2]==null)
        {
            ans=new String[]{rightAnswer,falseAnswer1};
            answer3.setVisibility(View.INVISIBLE);
            answer4.setVisibility(View.INVISIBLE);
        }
        else if(ans[3]==null)
        {
            ans=new String[]{rightAnswer,falseAnswer1,falseAnswer2};
            answer3.setVisibility(View.VISIBLE);
            answer4.setVisibility(View.INVISIBLE);
        }
        else
        {
            answer3.setVisibility(View.VISIBLE);
            answer4.setVisibility(View.VISIBLE);
        }

        //answers are shown in a random order
        String[] answers = Permute(ans);

        Button[] buzzers={answer1, answer2,answer3,answer4};
        for(int i=0;i<answers.length;i++)
        {
            buzzers[i].setText(answers[i]);
        }

    }

    //Randomizes the order of elements in tab
    //Used to give the propositions in a random order
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
