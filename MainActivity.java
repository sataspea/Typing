package com.example.khmertypinggame;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    String[] words = {"ឆ្មា", "ឆ្កែ", "ចាប", "មាន់", "ទា", "ជ្រូក", "គោ", "ក្របី", "សេះ", "ពពែ"};
    int currentWordIndex = 0;
    int score = 0;
    long startTime = 0;
    boolean bonusChecked = false;

    TextView wordText, scoreText, timerText;
    EditText userInput;
    Button submitButton;

    CountDownTimer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        wordText = findViewById(R.id.wordText);
        scoreText = findViewById(R.id.scoreText);
        timerText = findViewById(R.id.timerText);
        userInput = findViewById(R.id.userInput);
        submitButton = findViewById(R.id.submitButton);

        wordText.setText(words[currentWordIndex]);
        startTime = System.currentTimeMillis();

        timer = new CountDownTimer(120000, 1000) {
            public void onTick(long millisUntilFinished) {
                timerText.setText("Time left: " + millisUntilFinished / 1000);
            }

            public void onFinish() {
                timerText.setText("Time's up!");
                submitButton.setEnabled(false);
                userInput.setEnabled(false);
                checkBonus();
            }
        }.start();

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String typed = userInput.getText().toString().trim();
                if (typed.equals(words[currentWordIndex])) {
                    score++;
                }
                currentWordIndex++;
                userInput.setText("");

                if (currentWordIndex < words.length) {
                    wordText.setText(words[currentWordIndex]);
                } else {
                    wordText.setText("Finished!");
                    submitButton.setEnabled(false);
                    userInput.setEnabled(false);
                    timer.cancel();
                    checkBonus();
                }

                scoreText.setText("Score: " + score);
            }
        });
    }

    void checkBonus() {
        if (!bonusChecked && currentWordIndex >= words.length) {
            long elapsed = (System.currentTimeMillis() - startTime) / 1000;
            if (elapsed <= 60) {
                score += 5;
                Toast.makeText(this, "Bonus +5!", Toast.LENGTH_LONG).show();
                scoreText.setText("Score: " + score);
            }
            bonusChecked = true;
        }
    }
}
