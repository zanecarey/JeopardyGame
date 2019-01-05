package com.example.zane.jeopardygame;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class QuestionDisplayActivity extends AppCompatActivity {

    @BindView(R.id.title_textView)
    TextView titleTextView;
    @BindView(R.id.question_textView)
    TextView questionTextView;
    @BindView(R.id.answer_editText)
    EditText answerEditText;
    @BindView(R.id.sumbit_button)
    Button sumbitButton;

    String answer;
    int value;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_display);
        ButterKnife.bind(this);

        getQuestionInfo();
    }

    //get our question info
    private void getQuestionInfo() {
        if (getIntent().hasExtra("question") && getIntent().hasExtra("answer") && getIntent().hasExtra("value")) {
            questionTextView.setText(getIntent().getStringExtra("question"));
            answer = getIntent().getStringExtra("answer");
            value = getIntent().getIntExtra("value", 0);
        }
    }

    @OnClick(R.id.sumbit_button)
    public void onViewClicked() {
        if(answerEditText.getText().toString().equals(answer.toLowerCase())) {
            GameScreenActivity.TOTAL_SCORE += value;
        }
    }

}
