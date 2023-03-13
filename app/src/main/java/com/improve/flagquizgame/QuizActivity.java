package com.improve.flagquizgame;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashSet;

public class QuizActivity extends AppCompatActivity {

    private TextView textViewCorrect, textViewEmpty, textViewWrong, textViewQuestion;
    private ImageView imageViewFlag, imageViewNext;
    private Button buttonA, buttonB, buttonC, buttonD;
    private  FlagsDatabase fDatabase;
    private ArrayList<FlagsModel> questionsList;
    int correct = 0;
    int wrong = 0;
    int empty = 0;
    int question = 0;
    private FlagsModel correctFlag;
    private ArrayList<FlagsModel> wrongOptionsList;
    HashSet<FlagsModel> mixOptions = new HashSet<>();
    ArrayList<FlagsModel> options = new ArrayList<>();

    boolean buttonControl = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        textViewCorrect = findViewById(R.id.textViewCorrect);
        textViewEmpty = findViewById(R.id.textViewEmpty);
        textViewWrong = findViewById(R.id.textViewWrong);
        textViewQuestion = findViewById(R.id.textViewQuestion);

        imageViewFlag = findViewById(R.id.imageViewFlag);
        imageViewNext = findViewById(R.id.imageViewNext);

        buttonA = findViewById(R.id.buttonA);
        buttonB = findViewById(R.id.buttonB);
        buttonC = findViewById(R.id.buttonC);
        buttonD = findViewById(R.id.buttonD);

        fDatabase = new FlagsDatabase(QuizActivity.this);
        questionsList = new FlagsDAO().getRandomTenQuestions(fDatabase);

        loadQuestions();

        buttonA.setOnClickListener(v -> {
            answerControl(buttonA);
        });

        buttonB.setOnClickListener(v -> {
            answerControl(buttonB);
        });

        buttonC.setOnClickListener(v -> {
            answerControl(buttonC);
        });

        buttonD.setOnClickListener(v -> {
            answerControl(buttonD);
        });

        imageViewNext.setOnClickListener(v -> {

            question++;

            if (!buttonControl && question < questionsList.size())
            {
                empty++;
                textViewEmpty.setText("Empty: " + empty);
                loadQuestions();
            }
            else if (buttonControl && question < questionsList.size())
            {
                loadQuestions();

                buttonA.setClickable(true);
                buttonB.setClickable(true);
                buttonC.setClickable(true);
                buttonD.setClickable(true);

                buttonA.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#2849F6")));
                buttonB.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#2849F6")));
                buttonC.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#2849F6")));
                buttonD.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#2849F6")));

            }
            else if (question == questionsList.size())
            {
                Intent intent = new Intent(QuizActivity.this, ResultActivity.class);
                intent.putExtra(String.valueOf(correct), correct);
                intent.putExtra("wrong", wrong);
                intent.putExtra("empty", empty);
                startActivity(intent);
                finish();

            }

            buttonControl = false;

        });

    }

    public void loadQuestions()
    {
        textViewQuestion.setText("Question " + (question+1));

        correctFlag = questionsList.get(question);

        imageViewFlag.setImageResource(getResources().getIdentifier(correctFlag.getFlag_image(),"drawable", getPackageName()));
        wrongOptionsList = new FlagsDAO().getRandomThreeOptions(fDatabase, correctFlag.getFlag_id());

        mixOptions.clear();
        mixOptions.add(correctFlag);
        mixOptions.add(wrongOptionsList.get(0));
        mixOptions.add(wrongOptionsList.get(1));
        mixOptions.add(wrongOptionsList.get(2));

        options.clear();
        for (FlagsModel flg : mixOptions)
        {
            options.add(flg);
        }

        buttonA.setText(options.get(0).getFlag_name());
        buttonB.setText(options.get(1).getFlag_name());
        buttonC.setText(options.get(2).getFlag_name());
        buttonD.setText(options.get(3).getFlag_name());


    }


    public void answerControl(Button button)
    {
        String buttonText = button.getText().toString();
        String correctAnswer = correctFlag.getFlag_name();

        if (buttonText.equals(correctAnswer))
        {
            correct++;
            textViewCorrect.setText("Correct Answers: " + correct);
            button.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
        }
        else
        {
            wrong++;
            textViewWrong.setText("Wrong Answers: " + wrong);
            button.setBackgroundTintList(ColorStateList.valueOf(Color.RED));

            if (buttonA.getText().equals(correctAnswer))
            {
                buttonA.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
            }
            else if (buttonB.getText().equals(correctAnswer))
            {
                buttonB.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
            }
            else if (buttonC.getText().equals(correctAnswer))
            {
                buttonC.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
            }
            else if (buttonD.getText().equals(correctAnswer))
            {
                buttonD.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
            }
        }

        buttonA.setClickable(false);
        buttonB.setClickable(false);
        buttonC.setClickable(false);
        buttonD.setClickable(false);

        textViewCorrect.setText("Correct Answers: " + correct);
        textViewWrong.setText("Wrong Answers: " + wrong);
        textViewEmpty.setText("Empty Answers: " + empty);

        buttonControl = true;
    }
}