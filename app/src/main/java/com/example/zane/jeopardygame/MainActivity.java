package com.example.zane.jeopardygame;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;

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
    }

    @OnClick({R.id.startGame_btn, R.id.startMultiplayerGame_btn})
    public void onViewClicked(View view) {
        if(view.getId() == R.id.startGame_btn){

            Intent intent = new Intent(MainActivity.this, GameScreenActivity.class);
            startActivity(intent);
        } else {
            //check if lobby full before launching?
            Intent intent = new Intent(MainActivity.this, PrepareGameActivity.class);
            startActivity(intent);
        }
    }
}
