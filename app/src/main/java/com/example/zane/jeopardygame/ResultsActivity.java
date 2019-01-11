package com.example.zane.jeopardygame;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ResultsActivity extends AppCompatActivity {


    DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
    int score1, score2, score3;
    String player1, player2, player3;
    @BindView(R.id.player1_textView)
    TextView player1TextView;
    @BindView(R.id.player2_textView)
    TextView player2TextView;
    @BindView(R.id.player3_textView)
    TextView player3TextView;
    @BindView(R.id.player1Score_textView)
    TextView player1ScoreTextView;
    @BindView(R.id.player2Score_textView)
    TextView player2ScoreTextView;
    @BindView(R.id.player3Score_textView)
    TextView player3ScoreTextView;
    @BindView(R.id.button)
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        ButterKnife.bind(this);

        rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                player1ScoreTextView.setText("$" + dataSnapshot.child("player1Score").getValue(Integer.class));
                player2ScoreTextView.setText("$" + dataSnapshot.child("player2Score").getValue(Integer.class));
                player3ScoreTextView.setText("$" + dataSnapshot.child("player3Score").getValue(Integer.class));

                player1TextView.setText(dataSnapshot.child("player1Email").getValue(String.class));
                player2TextView.setText(dataSnapshot.child("player2Email").getValue(String.class));
                player3TextView.setText(dataSnapshot.child("player3Email").getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        resetValues();
    }

    private void resetValues() {
        rootRef.child("currentQ").setValue(0);
        rootRef.child("player1Email").setValue("");
        rootRef.child("player2Email").setValue("");
        rootRef.child("player3Email").setValue("");
        rootRef.child("player1Score").setValue(0);
        rootRef.child("player2Score").setValue(0);
        rootRef.child("player3Score").setValue(0);
        rootRef.child("playerTurn").setValue(1);
        rootRef.child("questionTotal").setValue(0);
        rootRef.child("gameStarted").setValue("");
        rootRef.child("gameEnded").setValue(false);
    }

    @OnClick(R.id.button)
    public void onViewClicked() {
        Intent intent = new Intent(ResultsActivity.this, MainActivity.class);
        startActivity(intent);
    }
}
