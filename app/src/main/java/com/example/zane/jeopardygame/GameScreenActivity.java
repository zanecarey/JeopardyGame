package com.example.zane.jeopardygame;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zane.jeopardygame.model.Categories;
import com.example.zane.jeopardygame.model.ClueResults;
import com.example.zane.jeopardygame.model.Clues;

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

public class GameScreenActivity extends AppCompatActivity {

    //base url
    private static final String BASE_URL = "http://jservice.io/";

    private static final String TAG = "GameScreenActivity";

    @BindView(R.id.cat1Title_textView)
    TextView cat1TitleTextView;
    @BindView(R.id.cat1_cardView)
    CardView cat1CardView;
    @BindView(R.id.cat2Title_textView)
    TextView cat2TitleTextView;
    @BindView(R.id.cat2_cardView)
    CardView cat2CardView;
    @BindView(R.id.cat3Title_textView)
    TextView cat3TitleTextView;
    @BindView(R.id.cat3_cardView)
    CardView cat3CardView;
    @BindView(R.id.totalScore_textView)
    TextView totalScoreTextView;
    @BindView(R.id.totalScoreValue_textView)
    TextView totalScoreValueTextView;
    @BindView(R.id.cat1Q1_textView)
    TextView cat1Q1textView;
    @BindView(R.id.cat1Q2_textView)
    TextView cat1Q2textView;
    @BindView(R.id.cat1Q3_textView)
    TextView cat1Q3textView;
    @BindView(R.id.cat1Q4_textView)
    TextView cat1Q4textView;
    @BindView(R.id.cat1Q5_textView)
    TextView cat1Q5textView;

    //running total score of game
    public static int TOTAL_SCORE = 0;

    private ArrayList<String> categoryTitles = new ArrayList<>();
    private ArrayList<Integer> categoryIDs = new ArrayList<>();
    private ArrayList<Integer> categoryClues_count = new ArrayList<>();

    //Category 1 stuff
    private ArrayList<String> cat1Questions = new ArrayList<>();
    private ArrayList<String> cat1Answers = new ArrayList<>();
    private ArrayList<Integer> cat1Values = new ArrayList<>();
    //Category 2 stuff
    private ArrayList<String> cat2Questions = new ArrayList<>();
    private ArrayList<String> cat2Answers = new ArrayList<>();
    private ArrayList<Integer> cat2Values = new ArrayList<>();
    //Category 3 stuff
    private ArrayList<String> cat3Questions = new ArrayList<>();
    private ArrayList<String> cat3Answers = new ArrayList<>();
    private ArrayList<Integer> cat3Values = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_screen);
        ButterKnife.bind(this);

        getCategories();
    }

    //get categories
    private void getCategories() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //offset is used to ensure categories dont repeat too often, range of 0 to 1000
        int offset = new Random().nextInt(11);
        offset = offset * 100;

        String url = "/api/categories?count=100&offset=" + offset;

        CategoryResultsInterface categoryResultsInterface = retrofit.create(CategoryResultsInterface.class);
        Call<ArrayList<Categories>> call = categoryResultsInterface.getData(url);

        call.enqueue(new Callback<ArrayList<Categories>>() {
            @Override
            public void onResponse(Call<ArrayList<Categories>> call, Response<ArrayList<Categories>> response) {

                // Toast.makeText(GameScreenActivity.this, "response: " + response.body().get(1).getTitle(), Toast.LENGTH_LONG).show();
                ArrayList<Categories> results = response.body();
                for (int i = 0; i < 3; i++) {
                    int index = new Random().nextInt(101);
                    categoryTitles.add(results.get(index).getTitle());
                    categoryIDs.add(results.get(index).getId());
                    categoryClues_count.add(results.get(index).getClues_count());
                }
                initCardViews();
                getCategory1Clues();
            }

            @Override
            public void onFailure(Call<ArrayList<Categories>> call, Throwable t) {

            }
        });
    }

    private void getCategory1Clues() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ArrayList<String> urlList = new ArrayList<>();
        urlList.add("/api/category?id=" + categoryIDs.get(0));
        urlList.add("/api/category?id=" + categoryIDs.get(1));
        urlList.add("/api/category?id=" + categoryIDs.get(2));
//        urlList.add("/api/category?id=" + categoryIDs.get(3));
//        urlList.add("/api/category?id=" + categoryIDs.get(4));
//        urlList.add("/api/category?id=" + categoryIDs.get(5));

        CluesInterface cluesInterface = retrofit.create(CluesInterface.class);
        for (int i = 0; i < 3; i++) {
            int index = i;
            Call<ClueResults> call = cluesInterface.getData(urlList.get(i));

            call.enqueue(new Callback<ClueResults>() {
                @Override
                public void onResponse(Call<ClueResults> call, Response<ClueResults> response) {
                    ArrayList<Clues> results = response.body().getClues();
                    for (int i = 0; i < results.size(); i++) {
                        switch (index) {
                            case 0:
                                cat1Questions.add(results.get(i).getQuestion());
                                cat1Answers.add(results.get(i).getAnswer());
                                cat1Values.add(results.get(i).getValue());
                                break;
                            case 1:
                                cat2Questions.add(results.get(i).getQuestion());
                                cat2Answers.add(results.get(i).getAnswer());
                                cat2Values.add(results.get(i).getValue());
                                break;
                            case 2:
                                cat3Questions.add(results.get(i).getQuestion());
                                cat3Answers.add(results.get(i).getAnswer());
                                cat3Values.add(results.get(i).getValue());
                                break;
                        }
                    }
                    //initClues(index);
                }

                @Override
                public void onFailure(Call<ClueResults> call, Throwable t) {

                }
            });
        }
    }
//    private void initClues(int category){
//        //category 1
//        if(category == 0){
//            cat1Q1textView.
//        }
//    }
    //display card view categories, expandable
    private void initCardViews() {

        cat1TitleTextView.append(categoryTitles.get(0));
        cat2TitleTextView.append(categoryTitles.get(1));
        cat3TitleTextView.append(categoryTitles.get(2));
    }

    @OnClick({R.id.cat1Q1_textView, R.id.cat1Q2_textView, R.id.cat1Q3_textView, R.id.cat1Q4_textView, R.id.cat1Q5_textView})
    public void onViewClicked(View view) {
//        Intent intent = new Intent(GameScreenActivity.this, QuestionDisplayActivity.class);
//        intent.putExtra("question", cat1Questions.get(0));
//        intent.putExtra("answer", cat1Answers.get(0));
//        intent.putExtra("value", cat1Values.get(0));
//        startActivity(intent);
        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(this, R.style.Theme_AppCompat_Light_Dialog_Alert);
        builder.setTitle("Question");

        final EditText editText = new EditText(this);
        builder.setView(editText);
        String rightAnswer = "";

        switch (view.getId()) {
            case R.id.cat1Q1_textView:
                builder.setMessage(cat1Questions.get(0));
                rightAnswer = cat1Answers.get(0).toLowerCase();
                break;
            case R.id.cat1Q2_textView:
                builder.setMessage(cat1Questions.get(1));
                rightAnswer = cat1Answers.get(1).toLowerCase();
                break;
            case R.id.cat1Q3_textView:
                builder.setMessage(cat1Questions.get(2));
                rightAnswer = cat1Answers.get(2).toLowerCase();
                break;
            case R.id.cat1Q4_textView:
                builder.setMessage(cat1Questions.get(3));
                rightAnswer = cat1Answers.get(3).toLowerCase();
                break;
            case R.id.cat1Q5_textView:
                builder.setMessage(cat1Questions.get(4));
                rightAnswer = cat1Answers.get(4).toLowerCase();
                break;
        }

        final String correct = rightAnswer;
        builder.setPositiveButton("Ok", (dialogInterface, i) -> {
            String answer = editText.getText().toString();
            if(answer.equals(correct)){
                Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show();
                TOTAL_SCORE += cat1Values.get(0);
                totalScoreValueTextView.setText(""+TOTAL_SCORE);
            } else {
                Toast.makeText(this, "Incorrect! " + answer, Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("Cancel", (dialogInterface, i) -> {

        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
