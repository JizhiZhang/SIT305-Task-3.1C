package com.example.sit305task31c;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {

    private TextView mTvWelcome, mTvQuizNum, mTvQuizTitle, mTvQuizDetail;
    private Button mBtnAnswer1, mBtnAnswer2, mBtnAnswer3, mBtnSubmit, mBtnNext;
    private ProgressBar mPbQuizNum;
    private Integer quizNum;
    private Integer score;
    private Integer selectedAnswer = null;
    private Integer correctAnswer = 0;
    private Integer maxNum = 5;
    private Button[] mBtnAnswer;
    String name;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        mTvWelcome = (TextView) findViewById(R.id.tv_welcome);
        mTvQuizNum = (TextView) findViewById(R.id.tv_quiznum);
        mTvQuizTitle = (TextView) findViewById(R.id.tv_quiztitle);
        mTvQuizDetail = (TextView) findViewById(R.id.tv_quizdetail);
        mBtnAnswer1 = (Button) findViewById(R.id.btn_answer1);
        mBtnAnswer2 = (Button) findViewById(R.id.btn_answer2);
        mBtnAnswer3 = (Button) findViewById(R.id.btn_answer3);
        mBtnSubmit = (Button) findViewById(R.id.btn_submit);
        mBtnNext = (Button) findViewById(R.id.btn_next);
        mPbQuizNum = (ProgressBar) findViewById(R.id.pb_quiznum);
        mBtnAnswer = new Button[]{mBtnAnswer1, mBtnAnswer2, mBtnAnswer3};

        Intent intent = getIntent();
        quizNum = intent.getIntExtra("quizNum", 0);
        score = intent.getIntExtra("score", 0);
        name = intent.getStringExtra("name");
        mTvWelcome.setText("Welcome " + name + "!");

        //Initialize the UI
        displayQuizInfo();

        //Button answer1
        mBtnAnswer1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBtnAnswer1.setBackgroundColor(Color.CYAN);
                mBtnAnswer2.setBackgroundColor(Color.WHITE);
                mBtnAnswer3.setBackgroundColor(Color.WHITE);
                selectedAnswer = 0;
            }
        });

        //Button answer2
        mBtnAnswer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBtnAnswer2.setBackgroundColor(Color.CYAN);
                mBtnAnswer1.setBackgroundColor(Color.WHITE);
                mBtnAnswer3.setBackgroundColor(Color.WHITE);
                selectedAnswer = 1;
            }
        });

        //Button answer3
        mBtnAnswer3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBtnAnswer3.setBackgroundColor(Color.CYAN);
                mBtnAnswer1.setBackgroundColor(Color.WHITE);
                mBtnAnswer2.setBackgroundColor(Color.WHITE);
                selectedAnswer = 2;
            }
        });

        //Button submit -> Check the correctness of the options
        mBtnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedAnswer != null) {
                    switch (quizNum) {
                        case 0:
                        case 3:
                            correctAnswer = 0;
                            break;
                        case 1:
                        case 4:
                            correctAnswer = 1;
                            break;
                        case 2:
                            correctAnswer = 2;
                            break;
                    }
                    mBtnAnswer1.setClickable(false);
                    mBtnAnswer2.setClickable(false);
                    mBtnAnswer3.setClickable(false);
                    if (selectedAnswer.equals(correctAnswer)) {
                        //select the correct answer, change the button color and add the score number
                        mBtnAnswer[selectedAnswer].setBackgroundColor(Color.GREEN);
                        score = score + 1;
                    } else {
                        //select the wrong answer, set the button color: selected button -> red, correct answer -> green
                        mBtnAnswer[selectedAnswer].setBackgroundColor(Color.RED);
                        mBtnAnswer[correctAnswer].setBackgroundColor(Color.GREEN);
                    }
                    mBtnSubmit.setVisibility(View.GONE);
                    mBtnNext.setVisibility(View.VISIBLE);
                    quizNum = quizNum + 1;
                    // Reset selectAnswer, because when entering the next quiz (quiz2, quiz3...)
                    // The value of selectAnswer is still the value selected in the previous quiz
                    // So you need to set it to null to ensure that when you click submit again, you will select the answer from the beginning
                    selectedAnswer = null;
                } else {
                    //if user directly click the submit button without choosing answers, shows the warning
                    Toast.makeText(QuizActivity.this, "Please choose an answer!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //Button next -> Jump to the Finish page
        mBtnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (quizNum < maxNum) {
                    displayQuizInfo();
                } else {
                    Intent intent1 = new Intent(QuizActivity.this, FinishActivity.class);
                    intent1.putExtra("name", name);
                    intent1.putExtra("score", score);
                    startActivityForResult(intent1, 1);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Intent intent2 = new Intent();
        setResult(RESULT_OK, intent2);
        finish();
    }

    //Set the layout of the Quiz page
    public void displayQuizInfo() {
        //Contains all array elements defined in the array.xml file
        TypedArray mTypedArray = getResources().obtainTypedArray(R.array.arrayQuiz);
        int quizID = mTypedArray.getResourceId(quizNum, 0);
        String[] quizContent = getResources().getStringArray(quizID);
        //Set text for Quiz title and details
        mTvQuizTitle.setText(quizContent[0]);
        mTvQuizDetail.setText(quizContent[1]);
        //Set text for Button Answer1
        mBtnAnswer1.setBackgroundColor(Color.WHITE);
        mBtnAnswer1.setText(quizContent[2]);
        mBtnAnswer1.setClickable(true);
        //Set text for Button Answer2
        mBtnAnswer2.setBackgroundColor(Color.WHITE);
        mBtnAnswer2.setText(quizContent[3]);
        mBtnAnswer2.setClickable(true);
        //Set text for Button Answer3
        mBtnAnswer3.setBackgroundColor(Color.WHITE);
        mBtnAnswer3.setText(quizContent[4]);
        mBtnAnswer3.setClickable(true);
        //Set text for Progress bar and text
        mPbQuizNum.setProgress((quizNum + 1));
        mTvQuizNum.setText((quizNum + 1) + "/5");
        // Hide next button and show submit button
        mBtnSubmit.setVisibility(View.VISIBLE);
        mBtnNext.setVisibility(View.GONE);
    }
}