package com.example.zane.jeopardygame;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MultiplayerGameScreenActivity extends AppCompatActivity {

    @BindView(R.id.player1Score_textView)
    TextView player1ScoreTextView;
    @BindView(R.id.player1ScoreValue_textView)
    TextView player1ScoreValueTextView;
    @BindView(R.id.player2Score_textView)
    TextView player2ScoreTextView;
    @BindView(R.id.player2ScoreValue_textView)
    TextView player2ScoreValueTextView;
    @BindView(R.id.player3Score_textView)
    TextView player3ScoreTextView;
    @BindView(R.id.player3ScoreValue_textView)
    TextView player3ScoreValueTextView;
    @BindView(R.id.cat1Title_textView)
    TextView cat1TitleTextView;
    @BindView(R.id.cat2Title_textView)
    TextView cat2TitleTextView;
    @BindView(R.id.cat3Title_textView)
    TextView cat3TitleTextView;
    @BindView(R.id.cat4Title_textView)
    TextView cat4TitleTextView;
    @BindView(R.id.cat5Title_textView)
    TextView cat5TitleTextView;
    @BindView(R.id.cat6Title_textView)
    TextView cat6TitleTextView;
    @BindView(R.id.cat1Q1_textView)
    TextView cat1Q1TextView;
    @BindView(R.id.cat2Q1_textView)
    TextView cat2Q1TextView;
    @BindView(R.id.cat3Q1_textView)
    TextView cat3Q1TextView;
    @BindView(R.id.cat4Q1_textView)
    TextView cat4Q1TextView;
    @BindView(R.id.cat5Q1_textView)
    TextView cat5Q1TextView;
    @BindView(R.id.cat6Q1_textView)
    TextView cat6Q1TextView;
    @BindView(R.id.cat1Q2_textView)
    TextView cat1Q2TextView;
    @BindView(R.id.cat2Q2_textView)
    TextView cat2Q2TextView;
    @BindView(R.id.cat3Q2_textView)
    TextView cat3Q2TextView;
    @BindView(R.id.cat4Q2_textView)
    TextView cat4Q2TextView;
    @BindView(R.id.cat5Q2_textView)
    TextView cat5Q2TextView;
    @BindView(R.id.cat6Q2_textView)
    TextView cat6Q2TextView;
    @BindView(R.id.cat1Q3_textView)
    TextView cat1Q3TextView;
    @BindView(R.id.cat2Q3_textView)
    TextView cat2Q3TextView;
    @BindView(R.id.cat3Q3_textView)
    TextView cat3Q3TextView;
    @BindView(R.id.cat4Q3_textView)
    TextView cat4Q3TextView;
    @BindView(R.id.cat5Q3_textView)
    TextView cat5Q3TextView;
    @BindView(R.id.cat6Q3_textView)
    TextView cat6Q3TextView;
    @BindView(R.id.cat1Q4_textView)
    TextView cat1Q4TextView;
    @BindView(R.id.cat2Q4_textView)
    TextView cat2Q4TextView;
    @BindView(R.id.cat3Q4_textView)
    TextView cat3Q4TextView;
    @BindView(R.id.cat4Q4_textView)
    TextView cat4Q4TextView;
    @BindView(R.id.cat5Q4_textView)
    TextView cat5Q4TextView;
    @BindView(R.id.cat6Q4_textView)
    TextView cat6Q4TextView;
    @BindView(R.id.cat1Q5_textView)
    TextView cat1Q5TextView;
    @BindView(R.id.cat2Q5_textView)
    TextView cat2Q5TextView;
    @BindView(R.id.cat3Q5_textView)
    TextView cat3Q5TextView;
    @BindView(R.id.cat4Q5_textView)
    TextView cat4Q5TextView;
    @BindView(R.id.cat5Q5_textView)
    TextView cat5Q5TextView;
    @BindView(R.id.cat6Q5_textView)
    TextView cat6Q5TextView;
    @BindView(R.id.game_scrollView)
    HorizontalScrollView gameScrollView;


    DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
    //DatabaseReference cat1Ref = rootRef.child("clues").child("cat1");

    String cat1Q1,cat1Q2,cat1Q3,cat1Q4,cat1Q5, cat1A1, cat1A2, cat1A3, cat1A4, cat1A5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_screen);
        ButterKnife.bind(this);

        //retrieve all values from db
        rootRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                //set player names
                player1ScoreTextView.setText(dataSnapshot.child("player1Email").getValue(String.class));

                //set category titles
                cat1TitleTextView.setText(dataSnapshot.child("cat1").getValue(String.class));
                cat2TitleTextView.setText(dataSnapshot.child("cat2").getValue(String.class));
                cat3TitleTextView.setText(dataSnapshot.child("cat3").getValue(String.class));
                cat4TitleTextView.setText(dataSnapshot.child("cat4").getValue(String.class));
                cat5TitleTextView.setText(dataSnapshot.child("cat5").getValue(String.class));
                cat6TitleTextView.setText(dataSnapshot.child("cat6").getValue(String.class));

                //set q's and a's cat 1
                cat1Q1 = dataSnapshot.child("clues").child("cat1").child("cat1Q1").getValue(String.class);
                cat1Q2 = dataSnapshot.child("clues").child("cat1").child("cat1Q2").getValue(String.class);
                cat1Q3 = dataSnapshot.child("clues").child("cat1").child("cat1Q3").getValue(String.class);
                cat1Q4 = dataSnapshot.child("clues").child("cat1").child("cat1Q4").getValue(String.class);
                cat1Q5 = dataSnapshot.child("clues").child("cat1").child("cat1Q5").getValue(String.class);

                cat1A1 = dataSnapshot.child("clues").child("cat1").child("cat1A1").getValue(String.class);
                cat1A2 = dataSnapshot.child("clues").child("cat1").child("cat1A2").getValue(String.class);
                cat1A3 = dataSnapshot.child("clues").child("cat1").child("cat1A3").getValue(String.class);
                cat1A4 = dataSnapshot.child("clues").child("cat1").child("cat1A4").getValue(String.class);
                cat1A5 = dataSnapshot.child("clues").child("cat1").child("cat1A5").getValue(String.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        setPlayerNames();


    }

    private void setPlayerNames(){
        player1ScoreTextView.setText(rootRef.child("player1Email").toString());
        player2ScoreTextView.setText(rootRef.child("player2Email").toString());
        player3ScoreTextView.setText(rootRef.child("player3Email").toString());
    }


    @OnClick({R.id.cat1Q1_textView, R.id.cat1Q2_textView, R.id.cat1Q3_textView, R.id.cat1Q4_textView, R.id.cat1Q5_textView,
            R.id.cat2Q1_textView, R.id.cat2Q2_textView, R.id.cat2Q3_textView, R.id.cat2Q4_textView, R.id.cat2Q5_textView,
            R.id.cat3Q1_textView, R.id.cat3Q2_textView, R.id.cat3Q3_textView, R.id.cat3Q4_textView, R.id.cat3Q5_textView,
            R.id.cat4Q1_textView, R.id.cat4Q2_textView, R.id.cat4Q3_textView, R.id.cat4Q4_textView, R.id.cat4Q5_textView,
            R.id.cat5Q1_textView, R.id.cat5Q2_textView, R.id.cat5Q3_textView, R.id.cat5Q4_textView, R.id.cat5Q5_textView,
            R.id.cat6Q1_textView, R.id.cat6Q2_textView, R.id.cat6Q3_textView, R.id.cat6Q4_textView, R.id.cat6Q5_textView})
    public void onViewClicked(View view) {

        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(this, R.style.AlertDialog);
        builder.setTitle("Question");


        AlertDialog.Builder builder2;
        builder2 = new AlertDialog.Builder(this, R.style.AlertDialog);


        final EditText editText = new EditText(this);
        editText.setTextColor(getResources().getColor(R.color.jeopardyYellow));
        builder.setView(editText);
        String rightAnswer = "";
        int qValue = 0;
        int viewID = view.getId();

        switch (viewID) {
            case R.id.cat1Q1_textView:
                //builder.setMessage(rootRef.child("clues").child("cat1").child("cat1Q1").toString());
                builder.setMessage(cat1Q1);
                rightAnswer = cat1A1;
                qValue = 200;
                break;
            case R.id.cat1Q2_textView:
                builder.setMessage(cat1Q2);
                rightAnswer = cat1A2;
                qValue = 400;
                break;
            case R.id.cat1Q3_textView:
                builder.setMessage(cat1Q3);
                rightAnswer = cat1A3;
                qValue = 600;
                break;
            case R.id.cat1Q4_textView:
                builder.setMessage(cat1Q4);
                rightAnswer = cat1A4;
                qValue = 800;
                break;
            case R.id.cat1Q5_textView:
                builder.setMessage(cat1Q5);
                rightAnswer = cat1A5;
                qValue = 1000;
                break;
//            case R.id.cat2Q1_textView:
//                builder.setMessage(cat2Questions.get(0));
//                rightAnswer = cat2Answers.get(0).toLowerCase();
//                qValue = 200;
//                break;
//            case R.id.cat2Q2_textView:
//                builder.setMessage(cat2Questions.get(1));
//                rightAnswer = cat2Answers.get(1).toLowerCase();
//                qValue = 400;
//                break;
//            case R.id.cat2Q3_textView:
//                builder.setMessage(cat2Questions.get(2));
//                rightAnswer = cat2Answers.get(2).toLowerCase();
//                qValue = 600;
//                break;
//            case R.id.cat2Q4_textView:
//                builder.setMessage(cat2Questions.get(3));
//                rightAnswer = cat2Answers.get(3).toLowerCase();
//                qValue = 800;
//                break;
//            case R.id.cat2Q5_textView:
//                builder.setMessage(cat2Questions.get(4));
//                rightAnswer = cat2Answers.get(4).toLowerCase();
//                qValue = 1000;
//                break;
//            case R.id.cat3Q1_textView:
//                builder.setMessage(cat3Questions.get(0));
//                rightAnswer = cat3Answers.get(0).toLowerCase();
//                qValue = 200;
//                break;
//            case R.id.cat3Q2_textView:
//                builder.setMessage(cat3Questions.get(1));
//                rightAnswer = cat3Answers.get(1).toLowerCase();
//                qValue = 400;
//                break;
//            case R.id.cat3Q3_textView:
//                builder.setMessage(cat3Questions.get(2));
//                rightAnswer = cat3Answers.get(2).toLowerCase();
//                qValue = 600;
//                break;
//            case R.id.cat3Q4_textView:
//                builder.setMessage(cat3Questions.get(3));
//                rightAnswer = cat3Answers.get(3).toLowerCase();
//                qValue = 800;
//                break;
//            case R.id.cat3Q5_textView:
//                builder.setMessage(cat3Questions.get(4));
//                rightAnswer = cat3Answers.get(4).toLowerCase();
//                qValue = 1000;
//                break;
//            case R.id.cat4Q1_textView:
//                builder.setMessage(cat4Questions.get(0));
//                rightAnswer = cat4Answers.get(0).toLowerCase();
//                qValue = 200;
//                break;
//            case R.id.cat4Q2_textView:
//                builder.setMessage(cat4Questions.get(1));
//                rightAnswer = cat4Answers.get(1).toLowerCase();
//                qValue = 400;
//                break;
//            case R.id.cat4Q3_textView:
//                builder.setMessage(cat4Questions.get(2));
//                rightAnswer = cat4Answers.get(2).toLowerCase();
//                qValue = 600;
//                break;
//            case R.id.cat4Q4_textView:
//                builder.setMessage(cat4Questions.get(3));
//                rightAnswer = cat4Answers.get(3).toLowerCase();
//                qValue = 800;
//                break;
//            case R.id.cat4Q5_textView:
//                builder.setMessage(cat4Questions.get(4));
//                rightAnswer = cat4Answers.get(4).toLowerCase();
//                qValue = 1000;
//                break;
//            case R.id.cat5Q1_textView:
//                builder.setMessage(cat5Questions.get(0));
//                rightAnswer = cat5Answers.get(0).toLowerCase();
//                qValue = 200;
//                break;
//            case R.id.cat5Q2_textView:
//                builder.setMessage(cat5Questions.get(1));
//                rightAnswer = cat5Answers.get(1).toLowerCase();
//                qValue = 400;
//                break;
//            case R.id.cat5Q3_textView:
//                builder.setMessage(cat5Questions.get(2));
//                rightAnswer = cat5Answers.get(2).toLowerCase();
//                qValue = 600;
//                break;
//            case R.id.cat5Q4_textView:
//                builder.setMessage(cat5Questions.get(3));
//                rightAnswer = cat5Answers.get(3).toLowerCase();
//                qValue = 800;
//                break;
//            case R.id.cat5Q5_textView:
//                builder.setMessage(cat5Questions.get(4));
//                rightAnswer = cat5Answers.get(4).toLowerCase();
//                qValue = 1000;
//                break;
//            case R.id.cat6Q1_textView:
//                builder.setMessage(cat6Questions.get(0));
//                rightAnswer = cat6Answers.get(0).toLowerCase();
//                qValue = 200;
//                break;
//            case R.id.cat6Q2_textView:
//                builder.setMessage(cat6Questions.get(1));
//                rightAnswer = cat6Answers.get(1).toLowerCase();
//                qValue = 400;
//                break;
//            case R.id.cat6Q3_textView:
//                builder.setMessage(cat6Questions.get(2));
//                rightAnswer = cat6Answers.get(2).toLowerCase();
//                qValue = 600;
//                break;
//            case R.id.cat6Q4_textView:
//                builder.setMessage(cat6Questions.get(3));
//                rightAnswer = cat6Answers.get(3).toLowerCase();
//                qValue = 800;
//                break;
//            case R.id.cat6Q5_textView:
//                builder.setMessage(cat6Questions.get(4));
//                rightAnswer = cat6Answers.get(4).toLowerCase();
//                qValue = 1000;
//                break;
        }

        final String correct = rightAnswer;
        final int val = qValue;

        builder.setPositiveButton("Ok", (dialogInterface, i) -> {

            String answer = editText.getText().toString().toLowerCase();
            if (answer.equals(correct)) {
               // TOTAL_SCORE += val;
                //totalScoreValueTextView.setText("" + TOTAL_SCORE);
                builder2.setTitle("Correct!");
                builder2.setMessage(val + " points added");
            } else {
               // TOTAL_SCORE -= val;
                //totalScoreValueTextView.setText("" + TOTAL_SCORE);
                builder2.setTitle("Incorrect!");
                builder2.setMessage("Correct answer was: " + correct);
            }

            AlertDialog dialog = builder2.create();
            dialog.show();

            //remove question from pool after submission, change coloring
            TextView tv = findViewById(viewID);
            tv.setTextColor(Color.GRAY);
            findViewById(viewID).setEnabled(false);
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }


}

