package com.example.zane.jeopardygame;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SinglePlayerResults extends AppCompatActivity {

    @BindView(R.id.player1_textView)
    TextView player1TextView;
    @BindView(R.id.highscore_textView)
    TextView highscoreTextView;
    @BindView(R.id.button)
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_player_results);
        ButterKnife.bind(this);

        int score = getScore();
        player1TextView.setText("Score: " + score);

        SharedPreferences settings = getSharedPreferences("Game_DATA", Context.MODE_PRIVATE);
        int highScore = settings.getInt("HIGH_SCORE", 0);

        if (score > highScore) {
            highscoreTextView.setText("High Score : " + score);

            //save score
            SharedPreferences.Editor editor = settings.edit();
            editor.putInt("HIGH_SCORE", score);
            editor.commit();
        } else {
            highscoreTextView.setText("High Score : " + highScore);
        }
    }

    private int getScore() {
        int score = 0;
        if (getIntent().hasExtra("score")) {
            score = getIntent().getIntExtra("score", 0);
        }
        return score;
    }

    @OnClick(R.id.button)
    public void onViewClicked() {
        Intent intent = new Intent(SinglePlayerResults.this, MainActivity.class);
        startActivity(intent);
    }
}
