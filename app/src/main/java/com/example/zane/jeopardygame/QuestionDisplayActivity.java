package com.example.zane.jeopardygame;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.zane.jeopardygame.model.Categories;
import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class QuestionDisplayActivity extends AppCompatActivity {

    //base url
    private static final String BASE_URL = "http://jservice.io/";
    private static int SIGN_IN_REQUEST_CODE = 1;

    @BindView(R.id.title_textView)
    TextView titleTextView;
    @BindView(R.id.question_textView)
    TextView questionTextView;
    @BindView(R.id.answer_editText)
    EditText answerEditText;
    @BindView(R.id.sumbit_button)
    Button sumbitButton;
    @BindView(R.id.multiplayer_constraintLayout)
    ConstraintLayout multiplayerConstraintLayout;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_display);
        ButterKnife.bind(this);


    }


}
