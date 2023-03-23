package com.improve.flagquizgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Button buttonStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonStart = findViewById(R.id.buttonStart);

        copyDatabase();

        buttonStart.setOnClickListener(v -> {

            Intent intent = new Intent(MainActivity.this, QuizActivity.class);
            startActivity(intent);
        });
    }

    public void copyDatabase()
    {
        try {

            DatabaseCopyHelper helper = new DatabaseCopyHelper(MainActivity.this);
            helper.createDataBase();
            helper.openDataBase();


        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}