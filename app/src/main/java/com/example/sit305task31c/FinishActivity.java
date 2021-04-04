package com.example.sit305task31c;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class FinishActivity extends AppCompatActivity {

    private TextView mTvCon, mTvScore;
    private Button mBtnNew, mBtnFinish;
    String name;
    Integer score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish);

        mTvCon = (TextView) findViewById(R.id.tv_congrat);
        mTvScore = (TextView) findViewById(R.id.tv_score);
        mBtnNew = (Button) findViewById(R.id.btn_newquiz);
        mBtnFinish = (Button) findViewById(R.id.btn_finish);

        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        mTvCon.setText("Congratulation " + name);
        score = intent.getIntExtra("score", 0);
        mTvScore.setText(score + "/5");

        mBtnNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent();
                setResult(RESULT_OK, intent1);
                finish();
            }
        });

        mBtnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAffinity();
            }
        });
    }
}