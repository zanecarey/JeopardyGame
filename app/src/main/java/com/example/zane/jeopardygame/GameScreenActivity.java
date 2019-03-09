package com.example.zane.jeopardygame;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

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

    @BindView(R.id.cat1Title_textView)
    TextView cat1TitleTextView;
    @BindView(R.id.cat2Title_textView)
    TextView cat2TitleTextView;
    @BindView(R.id.cat3Title_textView)
    TextView cat3TitleTextView;
    @BindView(R.id.player1Score_textView)
    TextView totalScoreTextView;
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
    @BindView(R.id.cat2Q1_textView)
    TextView cat2Q1TextView;
    @BindView(R.id.cat2Q2_textView)
    TextView cat2Q2TextView;
    @BindView(R.id.cat2Q3_textView)
    TextView cat2Q3TextView;
    @BindView(R.id.cat2Q4_textView)
    TextView cat2Q4TextView;
    @BindView(R.id.cat2Q5_textView)
    TextView cat2Q5TextView;
    @BindView(R.id.cat3Q1_textView)
    TextView cat3Q1TextView;
    @BindView(R.id.cat3Q2_textView)
    TextView cat3Q2TextView;
    @BindView(R.id.cat3Q3_textView)
    TextView cat3Q3TextView;
    @BindView(R.id.cat3Q4_textView)
    TextView cat3Q4TextView;
    @BindView(R.id.cat3Q5_textView)
    TextView cat3Q5TextView;
    @BindView(R.id.cat4Title_textView)
    TextView cat4TitleTextView;
    @BindView(R.id.cat5Title_textView)
    TextView cat5TitleTextView;
    @BindView(R.id.cat6Title_textView)
    TextView cat6TitleTextView;
    @BindView(R.id.cat4Q1_textView)
    TextView cat4Q1TextView;
    @BindView(R.id.cat5Q1_textView)
    TextView cat5Q1TextView;
    @BindView(R.id.cat6Q1_textView)
    TextView cat6Q1TextView;
    @BindView(R.id.cat4Q2_textView)
    TextView cat4Q2TextView;
    @BindView(R.id.cat5Q2_textView)
    TextView cat5Q2TextView;
    @BindView(R.id.cat6Q2_textView)
    TextView cat6Q2TextView;
    @BindView(R.id.cat4Q3_textView)
    TextView cat4Q3TextView;
    @BindView(R.id.cat5Q3_textView)
    TextView cat5Q3TextView;
    @BindView(R.id.cat6Q3_textView)
    TextView cat6Q3TextView;
    @BindView(R.id.cat4Q4_textView)
    TextView cat4Q4TextView;
    @BindView(R.id.cat5Q4_textView)
    TextView cat5Q4TextView;
    @BindView(R.id.cat6Q4_textView)
    TextView cat6Q4TextView;
    @BindView(R.id.cat4Q5_textView)
    TextView cat4Q5TextView;
    @BindView(R.id.cat5Q5_textView)
    TextView cat5Q5TextView;
    @BindView(R.id.cat6Q5_textView)
    TextView cat6Q5TextView;
    @BindView(R.id.currentTurn_textView)
    TextView currentTurnTextView;
    @BindView(R.id.currentTurnName_textView)
    TextView currentTurnNameTextView;
    @BindView(R.id.player2Score_textView)
    TextView player2ScoreTextView;
    @BindView(R.id.player3Score_textView)
    TextView player3ScoreTextView;
    @BindView(R.id.currentTurn_layout)
    LinearLayout currentTurn_layout;
    @BindView(R.id.game_layout)
    LinearLayout game_layout;

    private ArrayList<String> categoryTitles = new ArrayList<>();
    private ArrayList<Integer> categoryIDs = new ArrayList<>();
    private ArrayList<Integer> categoryClues_count = new ArrayList<>();

    //Category 1 stuff
    private ArrayList<String> cat1Questions = new ArrayList<>();
    private ArrayList<String> cat1Answers = new ArrayList<>();

    //Category 2 stuff
    private ArrayList<String> cat2Questions = new ArrayList<>();
    private ArrayList<String> cat2Answers = new ArrayList<>();

    //Category 3 stuff
    private ArrayList<String> cat3Questions = new ArrayList<>();
    private ArrayList<String> cat3Answers = new ArrayList<>();

    //Category 4 stuff
    private ArrayList<String> cat4Questions = new ArrayList<>();
    private ArrayList<String> cat4Answers = new ArrayList<>();

    //Category 5 stuff
    private ArrayList<String> cat5Questions = new ArrayList<>();
    private ArrayList<String> cat5Answers = new ArrayList<>();

    //Category 6 stuff
    private ArrayList<String> cat6Questions = new ArrayList<>();
    private ArrayList<String> cat6Answers = new ArrayList<>();

    //running total score of game
    public static int TOTAL_SCORE = 0;
    //question total
    public static int QUESTION_TOTAL = 0;


    //list of question textviews
    private ArrayList<TextView> questionTextViewsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_screen);
        ButterKnife.bind(this);

        //deactivate multiplayer views
        currentTurn_layout.setVisibility(View.INVISIBLE);
        currentTurnTextView.setVisibility(View.INVISIBLE);
        player2ScoreTextView.setVisibility(View.INVISIBLE);
        player3ScoreTextView.setVisibility(View.INVISIBLE);

        addTextViews();

        //restore values if activity is out of foreground
        if (savedInstanceState != null) {

            totalScoreTextView.setText("Score: $" + TOTAL_SCORE);

            categoryTitles = savedInstanceState.getStringArrayList("categoryTitles");

            cat1Questions = savedInstanceState.getStringArrayList("cat1Questions");
            cat1Answers = savedInstanceState.getStringArrayList("cat1Answers");

            cat2Questions = savedInstanceState.getStringArrayList("cat2Questions");
            cat2Answers = savedInstanceState.getStringArrayList("cat2Answers");

            cat3Questions = savedInstanceState.getStringArrayList("cat3Questions");
            cat3Answers = savedInstanceState.getStringArrayList("cat3Answers");

            cat4Questions = savedInstanceState.getStringArrayList("cat4Questions");
            cat4Answers = savedInstanceState.getStringArrayList("cat4Answers");

            cat5Questions = savedInstanceState.getStringArrayList("cat5Questions");
            cat5Answers = savedInstanceState.getStringArrayList("cat5Answers");

            cat6Questions = savedInstanceState.getStringArrayList("cat6Questions");
            cat6Answers = savedInstanceState.getStringArrayList("cat6Answers");

            //restore textview statuses
            boolean[] tvs = savedInstanceState.getBooleanArray("textViewStatuses");
            for (int i = 0; i < 30; i++) {
                if (!tvs[i]) {
                    questionTextViewsList.get(i).setTextColor(Color.GRAY);
                    questionTextViewsList.get(i).setEnabled(false);
                }
            }
            initCardViews();
        } else {
            getCategories();
        }
    }

    //get categories
    private void getCategories() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //offset is used to ensure categories dont repeat too often, range of 0 to 10000
        int offset = new Random().nextInt(101);
        offset = offset * 100;

        String url = "/api/categories?count=100&offset=" + offset;

        CategoryResultsInterface categoryResultsInterface = retrofit.create(CategoryResultsInterface.class);
        Call<ArrayList<Categories>> call = categoryResultsInterface.getData(url);

        call.enqueue(new Callback<ArrayList<Categories>>() {
            @Override
            public void onResponse(Call<ArrayList<Categories>> call, Response<ArrayList<Categories>> response) {

                ArrayList<Categories> results = response.body();
                for (int i = 0; i < 6; i++) {
                    int index = new Random().nextInt(100);
                    if (results.get(index).getClues_count() >= 5 && !categoryIDs.contains(results.get(index).getId()) && results.get(index).getTitle().length() < 15) {

                        categoryTitles.add(results.get(index).getTitle());
                        categoryIDs.add(results.get(index).getId());
                        categoryClues_count.add(results.get(index).getClues_count());
                    } else {
                        i--;
                    }
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
        urlList.add("/api/category?id=" + categoryIDs.get(3));
        urlList.add("/api/category?id=" + categoryIDs.get(4));
        urlList.add("/api/category?id=" + categoryIDs.get(5));

        CluesInterface cluesInterface = retrofit.create(CluesInterface.class);
        for (int i = 0; i < 6; i++) {
            int index = i;
            Call<ClueResults> call = cluesInterface.getData(urlList.get(i));

            call.enqueue(new Callback<ClueResults>() {
                @Override
                public void onResponse(Call<ClueResults> call, Response<ClueResults> response) {
                    ArrayList<Clues> results = response.body().getClues();
                    for (int i = 0; i < results.size(); i++) {
                        if (results.get(i).getAnswer().equals(null) || results.get(i).getQuestion().equals(null)) {
                            categoryTitles.clear();
                            categoryIDs.clear();
                            categoryClues_count.clear();
                            getCategories();
                        } else {
                            switch (index) {
                                case 0:
                                    cat1Questions.add(results.get(i).getQuestion());
                                    cat1Answers.add(results.get(i).getAnswer());
                                    break;
                                case 1:
                                    cat2Questions.add(results.get(i).getQuestion());
                                    cat2Answers.add(results.get(i).getAnswer());
                                    break;
                                case 2:
                                    cat3Questions.add(results.get(i).getQuestion());
                                    cat3Answers.add(results.get(i).getAnswer());
                                    break;
                                case 3:
                                    cat4Questions.add(results.get(i).getQuestion());
                                    cat4Answers.add(results.get(i).getAnswer());
                                    break;
                                case 4:
                                    cat5Questions.add(results.get(i).getQuestion());
                                    cat5Answers.add(results.get(i).getAnswer());
                                    break;
                                case 5:
                                    cat6Questions.add(results.get(i).getQuestion());
                                    cat6Answers.add(results.get(i).getAnswer());
                                    break;
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<ClueResults> call, Throwable t) {

                }
            });
        }
    }

    //display card view categories, expandable
    private void initCardViews() {
        cat1TitleTextView.append(categoryTitles.get(0));
        cat2TitleTextView.append(categoryTitles.get(1));
        cat3TitleTextView.append(categoryTitles.get(2));
        cat4TitleTextView.append(categoryTitles.get(3));
        cat5TitleTextView.append(categoryTitles.get(4));
        cat6TitleTextView.append(categoryTitles.get(5));
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
                builder.setMessage(cat1Questions.get(0));
                rightAnswer = cat1Answers.get(0).toLowerCase();
                qValue = 200;
                break;
            case R.id.cat1Q2_textView:
                builder.setMessage(cat1Questions.get(1));
                rightAnswer = cat1Answers.get(1).toLowerCase();
                qValue = 400;
                break;
            case R.id.cat1Q3_textView:
                builder.setMessage(cat1Questions.get(2));
                rightAnswer = cat1Answers.get(2).toLowerCase();
                qValue = 600;
                break;
            case R.id.cat1Q4_textView:
                builder.setMessage(cat1Questions.get(3));
                rightAnswer = cat1Answers.get(3).toLowerCase();
                qValue = 800;
                break;
            case R.id.cat1Q5_textView:
                builder.setMessage(cat1Questions.get(4));
                rightAnswer = cat1Answers.get(4).toLowerCase();
                qValue = 1000;
                break;
            case R.id.cat2Q1_textView:
                builder.setMessage(cat2Questions.get(0));
                rightAnswer = cat2Answers.get(0).toLowerCase();
                qValue = 200;
                break;
            case R.id.cat2Q2_textView:
                builder.setMessage(cat2Questions.get(1));
                rightAnswer = cat2Answers.get(1).toLowerCase();
                qValue = 400;
                break;
            case R.id.cat2Q3_textView:
                builder.setMessage(cat2Questions.get(2));
                rightAnswer = cat2Answers.get(2).toLowerCase();
                qValue = 600;
                break;
            case R.id.cat2Q4_textView:
                builder.setMessage(cat2Questions.get(3));
                rightAnswer = cat2Answers.get(3).toLowerCase();
                qValue = 800;
                break;
            case R.id.cat2Q5_textView:
                builder.setMessage(cat2Questions.get(4));
                rightAnswer = cat2Answers.get(4).toLowerCase();
                qValue = 1000;
                break;
            case R.id.cat3Q1_textView:
                builder.setMessage(cat3Questions.get(0));
                rightAnswer = cat3Answers.get(0).toLowerCase();
                qValue = 200;
                break;
            case R.id.cat3Q2_textView:
                builder.setMessage(cat3Questions.get(1));
                rightAnswer = cat3Answers.get(1).toLowerCase();
                qValue = 400;
                break;
            case R.id.cat3Q3_textView:
                builder.setMessage(cat3Questions.get(2));
                rightAnswer = cat3Answers.get(2).toLowerCase();
                qValue = 600;
                break;
            case R.id.cat3Q4_textView:
                builder.setMessage(cat3Questions.get(3));
                rightAnswer = cat3Answers.get(3).toLowerCase();
                qValue = 800;
                break;
            case R.id.cat3Q5_textView:
                builder.setMessage(cat3Questions.get(4));
                rightAnswer = cat3Answers.get(4).toLowerCase();
                qValue = 1000;
                break;
            case R.id.cat4Q1_textView:
                builder.setMessage(cat4Questions.get(0));
                rightAnswer = cat4Answers.get(0).toLowerCase();
                qValue = 200;
                break;
            case R.id.cat4Q2_textView:
                builder.setMessage(cat4Questions.get(1));
                rightAnswer = cat4Answers.get(1).toLowerCase();
                qValue = 400;
                break;
            case R.id.cat4Q3_textView:
                builder.setMessage(cat4Questions.get(2));
                rightAnswer = cat4Answers.get(2).toLowerCase();
                qValue = 600;
                break;
            case R.id.cat4Q4_textView:
                builder.setMessage(cat4Questions.get(3));
                rightAnswer = cat4Answers.get(3).toLowerCase();
                qValue = 800;
                break;
            case R.id.cat4Q5_textView:
                builder.setMessage(cat4Questions.get(4));
                rightAnswer = cat4Answers.get(4).toLowerCase();
                qValue = 1000;
                break;
            case R.id.cat5Q1_textView:
                builder.setMessage(cat5Questions.get(0));
                rightAnswer = cat5Answers.get(0).toLowerCase();
                qValue = 200;
                break;
            case R.id.cat5Q2_textView:
                builder.setMessage(cat5Questions.get(1));
                rightAnswer = cat5Answers.get(1).toLowerCase();
                qValue = 400;
                break;
            case R.id.cat5Q3_textView:
                builder.setMessage(cat5Questions.get(2));
                rightAnswer = cat5Answers.get(2).toLowerCase();
                qValue = 600;
                break;
            case R.id.cat5Q4_textView:
                builder.setMessage(cat5Questions.get(3));
                rightAnswer = cat5Answers.get(3).toLowerCase();
                qValue = 800;
                break;
            case R.id.cat5Q5_textView:
                builder.setMessage(cat5Questions.get(4));
                rightAnswer = cat5Answers.get(4).toLowerCase();
                qValue = 1000;
                break;
            case R.id.cat6Q1_textView:
                builder.setMessage(cat6Questions.get(0));
                rightAnswer = cat6Answers.get(0).toLowerCase();
                qValue = 200;
                break;
            case R.id.cat6Q2_textView:
                builder.setMessage(cat6Questions.get(1));
                rightAnswer = cat6Answers.get(1).toLowerCase();
                qValue = 400;
                break;
            case R.id.cat6Q3_textView:
                builder.setMessage(cat6Questions.get(2));
                rightAnswer = cat6Answers.get(2).toLowerCase();
                qValue = 600;
                break;
            case R.id.cat6Q4_textView:
                builder.setMessage(cat6Questions.get(3));
                rightAnswer = cat6Answers.get(3).toLowerCase();
                qValue = 800;
                break;
            case R.id.cat6Q5_textView:
                builder.setMessage(cat6Questions.get(4));
                rightAnswer = cat6Answers.get(4).toLowerCase();
                qValue = 1000;
                break;
        }

        final String correct = rightAnswer;
        final int val = qValue;

        builder.setPositiveButton("Ok", (dialogInterface, i) -> {

            String answer = editText.getText().toString().toLowerCase();
            //remove html tags like <i> from answer
            String correctTrim = android.text.Html.fromHtml(correct).toString();
            if (answer.equals(correctTrim)) {
                TOTAL_SCORE += val;
                totalScoreTextView.setText("Score: $" + TOTAL_SCORE);
                builder2.setTitle("Correct!");
                builder2.setMessage(val + " points added");
            } else {
                TOTAL_SCORE -= val;
                totalScoreTextView.setText("Score: $" + TOTAL_SCORE);
                builder2.setTitle("Incorrect!");
                builder2.setMessage("Correct answer was: " + correct);
            }

            AlertDialog dialog = builder2.create();
            dialog.show();

            //remove question from pool after submission, change coloring
            TextView tv = findViewById(viewID);
            tv.setTextColor(Color.GRAY);
            findViewById(viewID).setEnabled(false);

            //increment question counter
            QUESTION_TOTAL++;
            if (QUESTION_TOTAL == 30) {
                Intent intent = new Intent(GameScreenActivity.this, SinglePlayerResults.class);
                intent.putExtra("score", TOTAL_SCORE);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("Cancel", (dialogInterface, i) -> {

        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //save all game state data
        super.onSaveInstanceState(outState);

        outState.putInt("score", TOTAL_SCORE);

        outState.putStringArrayList("categoryTitles", categoryTitles);

        outState.putStringArrayList("cat1Questions", cat1Questions);
        outState.putStringArrayList("cat1Answers", cat1Answers);

        outState.putStringArrayList("cat2Questions", cat2Questions);
        outState.putStringArrayList("cat2Answers", cat2Answers);

        outState.putStringArrayList("cat3Questions", cat3Questions);
        outState.putStringArrayList("cat3Answers", cat3Answers);

        outState.putStringArrayList("cat4Questions", cat4Questions);
        outState.putStringArrayList("cat4Answers", cat4Answers);

        outState.putStringArrayList("cat5Questions", cat5Questions);
        outState.putStringArrayList("cat5Answers", cat5Answers);

        outState.putStringArrayList("cat6Questions", cat6Questions);
        outState.putStringArrayList("cat6Answers", cat6Answers);

        boolean[] textViewStatuses = new boolean[30];
        for (int i = 0; i < 30; i++) {
            if (questionTextViewsList.get(i).isEnabled()) {
                textViewStatuses[i] = true;
            }
        }
        outState.putBooleanArray("textViewStatuses", textViewStatuses);
    }

    private void addTextViews() {
        questionTextViewsList.add(cat1Q1textView);
        questionTextViewsList.add(cat1Q2textView);
        questionTextViewsList.add(cat1Q3textView);
        questionTextViewsList.add(cat1Q4textView);
        questionTextViewsList.add(cat1Q5textView);

        questionTextViewsList.add(cat2Q1TextView);
        questionTextViewsList.add(cat2Q2TextView);
        questionTextViewsList.add(cat2Q3TextView);
        questionTextViewsList.add(cat2Q4TextView);
        questionTextViewsList.add(cat2Q5TextView);

        questionTextViewsList.add(cat3Q1TextView);
        questionTextViewsList.add(cat3Q2TextView);
        questionTextViewsList.add(cat3Q3TextView);
        questionTextViewsList.add(cat3Q4TextView);
        questionTextViewsList.add(cat3Q5TextView);

        questionTextViewsList.add(cat4Q1TextView);
        questionTextViewsList.add(cat4Q2TextView);
        questionTextViewsList.add(cat4Q3TextView);
        questionTextViewsList.add(cat4Q4TextView);
        questionTextViewsList.add(cat4Q5TextView);

        questionTextViewsList.add(cat5Q1TextView);
        questionTextViewsList.add(cat5Q2TextView);
        questionTextViewsList.add(cat5Q3TextView);
        questionTextViewsList.add(cat5Q4TextView);
        questionTextViewsList.add(cat5Q5TextView);

        questionTextViewsList.add(cat6Q1TextView);
        questionTextViewsList.add(cat6Q2TextView);
        questionTextViewsList.add(cat6Q3TextView);
        questionTextViewsList.add(cat6Q4TextView);
        questionTextViewsList.add(cat6Q5TextView);
    }


}
