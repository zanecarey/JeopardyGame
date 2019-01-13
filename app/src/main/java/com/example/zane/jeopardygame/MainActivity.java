package com.example.zane.jeopardygame;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    private static int SIGN_IN_REQUEST_CODE = 1;

    @BindView(R.id.startGame_btn)
    Button startGameBtn;
    @BindView(R.id.mainActivity_layout)
    ConstraintLayout mainActivityLayout;
    @BindView(R.id.startMultiplayerGame_btn)
    Button startMultiplayerGameBtn;

    DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference player3Ref = rootRef.child("player3Email");
    String player3 = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        //check if sign in
        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder().build(), SIGN_IN_REQUEST_CODE);
        } else {
            Snackbar.make(mainActivityLayout, "Welcome " + FirebaseAuth.getInstance().getCurrentUser().getEmail(), Snackbar.LENGTH_SHORT).show();
        }

        player3Ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                player3 = dataSnapshot.getValue(String.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @OnClick({R.id.startGame_btn, R.id.startMultiplayerGame_btn})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.startGame_btn:
                intent = new Intent(MainActivity.this, GameScreenActivity.class);
                startActivity(intent);
                break;
            case R.id.startMultiplayerGame_btn:
                if(player3.equals("")){
                    intent = new Intent(MainActivity.this, PrepareGameActivity.class);
                    startActivity(intent);
                } else {
                    Snackbar.make(mainActivityLayout, "Lobby full!", Snackbar.LENGTH_SHORT).show();
                }
                break;
        }
    }
}

