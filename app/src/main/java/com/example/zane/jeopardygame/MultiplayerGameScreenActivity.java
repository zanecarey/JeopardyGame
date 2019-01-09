package com.example.zane.jeopardygame;

import android.content.DialogInterface;
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
    @BindView(R.id.currentTurn_textView)
    TextView currentTurnTextView;
    @BindView(R.id.currentTurnName_textView)
    TextView currentTurnNameTextView;

    DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference player1ScoreRef = rootRef.child("player1Score");
    DatabaseReference player2ScoreRef = rootRef.child("player2Score");
    DatabaseReference player3ScoreRef = rootRef.child("player3Score");
    DatabaseReference currentQ = rootRef.child("currentQ");
    DatabaseReference currentTurn = rootRef.child("playerTurn");
    DatabaseReference questionTotal = rootRef.child("questionTotal");

    String cat1Q1, cat1Q2, cat1Q3, cat1Q4, cat1Q5, cat1A1, cat1A2, cat1A3, cat1A4, cat1A5;
    String cat2Q1, cat2Q2, cat2Q3, cat2Q4, cat2Q5, cat2A1, cat2A2, cat2A3, cat2A4, cat2A5;
    String cat3Q1, cat3Q2, cat3Q3, cat3Q4, cat3Q5, cat3A1, cat3A2, cat3A3, cat3A4, cat3A5;
    String cat4Q1, cat4Q2, cat4Q3, cat4Q4, cat4Q5, cat4A1, cat4A2, cat4A3, cat4A4, cat4A5;
    String cat5Q1, cat5Q2, cat5Q3, cat5Q4, cat5Q5, cat5A1, cat5A2, cat5A3, cat5A4, cat5A5;
    String cat6Q1, cat6Q2, cat6Q3, cat6Q4, cat6Q5, cat6A1, cat6A2, cat6A3, cat6A4, cat6A5;
    String player1, player2, player3;

    int playerScore = 0;
    int playerSlot;

    boolean yourTurn = false;

    String scoreTag;

    int qCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_screen);
        ButterKnife.bind(this);

        playerSlot = getPlayerSlot();
        scoreTag = "player" + playerSlot + "Score";
        //retrieve all values from db
        rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                //set player names
                player1 = dataSnapshot.child("player1Email").getValue(String.class);
                player2 = dataSnapshot.child("player2Email").getValue(String.class);
                player3 = dataSnapshot.child("player3Email").getValue(String.class);

                player1ScoreTextView.setText(player1);
                player2ScoreTextView.setText(player2);
                player3ScoreTextView.setText(player3);

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

                //set q's and a's cat 2
                cat2Q1 = dataSnapshot.child("clues").child("cat2").child("cat2Q1").getValue(String.class);
                cat2Q2 = dataSnapshot.child("clues").child("cat2").child("cat2Q2").getValue(String.class);
                cat2Q3 = dataSnapshot.child("clues").child("cat2").child("cat2Q3").getValue(String.class);
                cat2Q4 = dataSnapshot.child("clues").child("cat2").child("cat2Q4").getValue(String.class);
                cat2Q5 = dataSnapshot.child("clues").child("cat2").child("cat2Q5").getValue(String.class);

                cat2A1 = dataSnapshot.child("clues").child("cat2").child("cat2A1").getValue(String.class);
                cat2A2 = dataSnapshot.child("clues").child("cat2").child("cat2A2").getValue(String.class);
                cat2A3 = dataSnapshot.child("clues").child("cat2").child("cat2A3").getValue(String.class);
                cat2A4 = dataSnapshot.child("clues").child("cat2").child("cat2A4").getValue(String.class);
                cat2A5 = dataSnapshot.child("clues").child("cat2").child("cat2A5").getValue(String.class);

                //set q's and a's cat 3
                cat3Q1 = dataSnapshot.child("clues").child("cat3").child("cat3Q1").getValue(String.class);
                cat3Q2 = dataSnapshot.child("clues").child("cat3").child("cat3Q2").getValue(String.class);
                cat3Q3 = dataSnapshot.child("clues").child("cat3").child("cat3Q3").getValue(String.class);
                cat3Q4 = dataSnapshot.child("clues").child("cat3").child("cat3Q4").getValue(String.class);
                cat3Q5 = dataSnapshot.child("clues").child("cat3").child("cat3Q5").getValue(String.class);

                cat3A1 = dataSnapshot.child("clues").child("cat3").child("cat3A1").getValue(String.class);
                cat3A2 = dataSnapshot.child("clues").child("cat3").child("cat3A2").getValue(String.class);
                cat3A3 = dataSnapshot.child("clues").child("cat3").child("cat3A3").getValue(String.class);
                cat3A4 = dataSnapshot.child("clues").child("cat3").child("cat3A4").getValue(String.class);
                cat3A5 = dataSnapshot.child("clues").child("cat3").child("cat3A5").getValue(String.class);

                //set q's and a's cat 4
                cat4Q1 = dataSnapshot.child("clues").child("cat4").child("cat4Q1").getValue(String.class);
                cat4Q2 = dataSnapshot.child("clues").child("cat4").child("cat4Q2").getValue(String.class);
                cat4Q3 = dataSnapshot.child("clues").child("cat4").child("cat4Q3").getValue(String.class);
                cat4Q4 = dataSnapshot.child("clues").child("cat4").child("cat4Q4").getValue(String.class);
                cat4Q5 = dataSnapshot.child("clues").child("cat4").child("cat4Q5").getValue(String.class);

                cat4A1 = dataSnapshot.child("clues").child("cat4").child("cat4A1").getValue(String.class);
                cat4A2 = dataSnapshot.child("clues").child("cat4").child("cat4A2").getValue(String.class);
                cat4A3 = dataSnapshot.child("clues").child("cat4").child("cat4A3").getValue(String.class);
                cat4A4 = dataSnapshot.child("clues").child("cat4").child("cat4A4").getValue(String.class);
                cat4A5 = dataSnapshot.child("clues").child("cat4").child("cat4A5").getValue(String.class);

                //set q's and a's cat 5
                cat5Q1 = dataSnapshot.child("clues").child("cat5").child("cat5Q1").getValue(String.class);
                cat5Q2 = dataSnapshot.child("clues").child("cat5").child("cat5Q2").getValue(String.class);
                cat5Q3 = dataSnapshot.child("clues").child("cat5").child("cat5Q3").getValue(String.class);
                cat5Q4 = dataSnapshot.child("clues").child("cat5").child("cat5Q4").getValue(String.class);
                cat5Q5 = dataSnapshot.child("clues").child("cat5").child("cat5Q5").getValue(String.class);

                cat5A1 = dataSnapshot.child("clues").child("cat5").child("cat5A1").getValue(String.class);
                cat5A2 = dataSnapshot.child("clues").child("cat5").child("cat5A2").getValue(String.class);
                cat5A3 = dataSnapshot.child("clues").child("cat5").child("cat5A3").getValue(String.class);
                cat5A4 = dataSnapshot.child("clues").child("cat5").child("cat5A4").getValue(String.class);
                cat5A5 = dataSnapshot.child("clues").child("cat5").child("cat5A5").getValue(String.class);

                //set q's and a's cat 6
                cat6Q1 = dataSnapshot.child("clues").child("cat6").child("cat6Q1").getValue(String.class);
                cat6Q2 = dataSnapshot.child("clues").child("cat6").child("cat6Q2").getValue(String.class);
                cat6Q3 = dataSnapshot.child("clues").child("cat6").child("cat6Q3").getValue(String.class);
                cat6Q4 = dataSnapshot.child("clues").child("cat6").child("cat6Q4").getValue(String.class);
                cat6Q5 = dataSnapshot.child("clues").child("cat6").child("cat6Q5").getValue(String.class);

                cat6A1 = dataSnapshot.child("clues").child("cat6").child("cat6A1").getValue(String.class);
                cat6A2 = dataSnapshot.child("clues").child("cat6").child("cat6A2").getValue(String.class);
                cat6A3 = dataSnapshot.child("clues").child("cat6").child("cat6A3").getValue(String.class);
                cat6A4 = dataSnapshot.child("clues").child("cat6").child("cat6A4").getValue(String.class);
                cat6A5 = dataSnapshot.child("clues").child("cat6").child("cat6A5").getValue(String.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //Listeners for score updates
        player1ScoreRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                player1ScoreValueTextView.setText(dataSnapshot.getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        player2ScoreRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                player2ScoreValueTextView.setText(dataSnapshot.getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        player3ScoreRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                player3ScoreValueTextView.setText(dataSnapshot.getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //listener for current question updates
        currentQ.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String q = dataSnapshot.getValue(String.class);
                if(!q.equals("")){
                    int questionID = Integer.parseInt(q);
                    deactivateQ(questionID);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        currentTurn.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int turn = dataSnapshot.getValue(Integer.class);
                if(turn == 1){
                    currentTurnNameTextView.setText(player1);
                } else if (turn == 2){
                    currentTurnNameTextView.setText(player2);
                } else {
                    currentTurnNameTextView.setText(player3);
                }
                if (turn == playerSlot) {
                    yourTurn = true;

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        questionTotal.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String count = dataSnapshot.getValue(String.class);
                if(Integer.parseInt(count) == 30){
                    //game ends
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    int getPlayerSlot() {
        return getIntent().getIntExtra("playerSlot", 0);
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

        //if not your turn, display appropriate message
        if(!yourTurn){
            builder.setTitle("Not your turn yet!");
            builder.setPositiveButton("OK", (dialogInterface, i) -> {

            });
            AlertDialog dialog = builder.create();
            dialog.show();
        } else {
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
                case R.id.cat2Q1_textView:
                    builder.setMessage(cat2Q1);
                    rightAnswer = cat2A1;
                    qValue = 200;
                    break;
                case R.id.cat2Q2_textView:
                    builder.setMessage(cat2Q2);
                    rightAnswer = cat2A2;
                    qValue = 400;
                    break;
                case R.id.cat2Q3_textView:
                    builder.setMessage(cat2Q3);
                    rightAnswer = cat2A3;
                    qValue = 600;
                    break;
                case R.id.cat2Q4_textView:
                    builder.setMessage(cat2Q4);
                    rightAnswer = cat2A4;
                    qValue = 800;
                    break;
                case R.id.cat2Q5_textView:
                    builder.setMessage(cat2Q5);
                    rightAnswer = cat2A5;
                    qValue = 1000;
                    break;
                case R.id.cat3Q1_textView:
                    builder.setMessage(cat3Q1);
                    rightAnswer = cat3A1;
                    qValue = 200;
                    break;
                case R.id.cat3Q2_textView:
                    builder.setMessage(cat3Q2);
                    rightAnswer = cat3A2;
                    qValue = 400;
                    break;
                case R.id.cat3Q3_textView:
                    builder.setMessage(cat3Q3);
                    rightAnswer = cat3A3;
                    qValue = 600;
                    break;
                case R.id.cat3Q4_textView:
                    builder.setMessage(cat3Q4);
                    rightAnswer = cat3A4;
                    qValue = 800;
                    break;
                case R.id.cat3Q5_textView:
                    builder.setMessage(cat3Q5);
                    rightAnswer = cat3A5;
                    qValue = 1000;
                    break;
                case R.id.cat4Q1_textView:
                    builder.setMessage(cat4Q1);
                    rightAnswer = cat4A1;
                    qValue = 200;
                    break;
                case R.id.cat4Q2_textView:
                    builder.setMessage(cat4Q2);
                    rightAnswer = cat4A2;
                    qValue = 400;
                    break;
                case R.id.cat4Q3_textView:
                    builder.setMessage(cat4Q3);
                    rightAnswer = cat4A3;
                    qValue = 600;
                    break;
                case R.id.cat4Q4_textView:
                    builder.setMessage(cat4Q4);
                    rightAnswer = cat4A4;
                    qValue = 800;
                    break;
                case R.id.cat4Q5_textView:
                    builder.setMessage(cat4Q5);
                    rightAnswer = cat4A5;
                    qValue = 1000;
                    break;
                case R.id.cat5Q1_textView:
                    builder.setMessage(cat5Q1);
                    rightAnswer = cat5A1;
                    qValue = 200;
                    break;
                case R.id.cat5Q2_textView:
                    builder.setMessage(cat5Q2);
                    rightAnswer = cat5A2;
                    qValue = 400;
                    break;
                case R.id.cat5Q3_textView:
                    builder.setMessage(cat5Q3);
                    rightAnswer = cat5A3;
                    qValue = 600;
                    break;
                case R.id.cat5Q4_textView:
                    builder.setMessage(cat5Q4);
                    rightAnswer = cat5A4;
                    qValue = 800;
                    break;
                case R.id.cat5Q5_textView:
                    builder.setMessage(cat5Q5);
                    rightAnswer = cat5A5;
                    qValue = 1000;
                    break;
                case R.id.cat6Q1_textView:
                    builder.setMessage(cat6Q1);
                    rightAnswer = cat6A1;
                    qValue = 200;
                    break;
                case R.id.cat6Q2_textView:
                    builder.setMessage(cat6Q2);
                    rightAnswer = cat6A2;
                    qValue = 400;
                    break;
                case R.id.cat6Q3_textView:
                    builder.setMessage(cat6Q3);
                    rightAnswer = cat6A3;
                    qValue = 600;
                    break;
                case R.id.cat6Q4_textView:
                    builder.setMessage(cat6Q4);
                    rightAnswer = cat6A4;
                    qValue = 800;
                    break;
                case R.id.cat6Q5_textView:
                    builder.setMessage(cat6Q5);
                    rightAnswer = cat6A5;
                    qValue = 1000;
                    break;
            }

            final String correct = rightAnswer.toLowerCase();
            final int val = qValue;

            builder.setPositiveButton("Ok", (dialogInterface, i) -> {

                String answer = editText.getText().toString().toLowerCase();
                if (answer.equals(correct)) {
                    playerScore += val;
                    builder2.setTitle("Correct!");
                    builder2.setMessage(val + " points added");
                } else {
                    playerScore -= val;
                    builder2.setTitle("Incorrect!");
                    builder2.setMessage("Correct answer was: " + correct);
                }
                //update score value
                String score = Integer.toString(playerScore);
                rootRef.child(scoreTag).setValue(score);
                //update chosen q
                String currQ = Integer.toString(viewID);
                rootRef.child("currentQ").setValue(currQ);
                //update playerTurn
                if (playerSlot == 1) {
                    rootRef.child("playerTurn").setValue(2);
                } else if (playerSlot == 2) {
                    rootRef.child("playerTurn").setValue(3);
                } else {
                    rootRef.child("playerTurn").setValue(1);
                }
                //increment game q counter
                qCount++;
                rootRef.child("questionTotal").setValue(qCount);

                yourTurn = false;
                AlertDialog dialog = builder2.create();
                dialog.show();

            });

            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }

    private void deactivateQ(int viewID) {
        TextView tv = findViewById(viewID);
        tv.setTextColor(Color.GRAY);
        findViewById(viewID).setEnabled(false);
    }

    @Override
    protected void onDestroy() {
        rootRef.child("currentQ").setValue("");
        rootRef.child("player1Email").setValue("");
        rootRef.child("player2Email").setValue("");
        rootRef.child("player3Email").setValue("");
        rootRef.child("player1Score").setValue("");
        rootRef.child("player2Score").setValue("");
        rootRef.child("player3Score").setValue("");
        rootRef.child("playerTurn").setValue(1);
        super.onDestroy();
    }
}

