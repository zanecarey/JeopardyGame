package com.example.zane.jeopardygame;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.zane.jeopardygame.model.Categories;
import com.example.zane.jeopardygame.model.ClueResults;
import com.example.zane.jeopardygame.model.Clues;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
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

public class PrepareGameActivity extends AppCompatActivity {

    private static final String BASE_URL = "http://jservice.io/";


    @BindView(R.id.playerName1_textView)
    TextView playerName1TextView;
    @BindView(R.id.playerName2_textView)
    TextView playerName2TextView;
    @BindView(R.id.playerName3_textView)
    TextView playerName3TextView;
    @BindView(R.id.addPlayer_btn)
    Button addPlayerBtn;
    @BindView(R.id.start_btn)
    Button startBtn;
    @BindView(R.id.email_editText)
    EditText emailEditText;
    @BindView(R.id.prepareGame_layout)
    ConstraintLayout prepareGameLayout;


    private ArrayList<String> categoryTitles = new ArrayList<>();
    private ArrayList<Integer> categoryIDs = new ArrayList<>();

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

    DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();

    private int playerSlot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prepare_game);
        ButterKnife.bind(this);

        setPlayerSlot();

        getCategories();
    }

    private void setPlayerSlot() {
        if (rootRef.child("player1Email").equals("")) {
            rootRef.child("player1Email").setValue(FirebaseAuth.getInstance().getCurrentUser());
        } else if (rootRef.child("player2Email").equals("")) {
            rootRef.child("player2Email").setValue(FirebaseAuth.getInstance().getCurrentUser());
        } else if (rootRef.child("player3Email").equals("")) {
            rootRef.child("player3Email").setValue(FirebaseAuth.getInstance().getCurrentUser());
        } else {
            Snackbar.make(prepareGameLayout, "Lobby Full", Snackbar.LENGTH_SHORT).show();
        }
    }

    //retrieve random categories from jservice
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
                    if (results.get(index).getClues_count() >= 5 && !categoryIDs.contains(results.get(index).getId())) {

                        categoryTitles.add(results.get(index).getTitle());
                        categoryIDs.add(results.get(index).getId());


                    } else {
                        i--;
                    }
                }
                uploadCategories();
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

                @Override
                public void onFailure(Call<ClueResults> call, Throwable t) {

                }
            });
        }
    }


    //upload categorie titles and ids to firebase db
    private void uploadCategories() {

        rootRef.child("cat1").setValue(categoryTitles.get(0));
        rootRef.child("cat1ID").setValue(categoryIDs.get(0));

        rootRef.child("cat2").setValue(categoryTitles.get(1));
        rootRef.child("cat2ID").setValue(categoryIDs.get(1));

        rootRef.child("cat3").setValue(categoryTitles.get(2));
        rootRef.child("cat3ID").setValue(categoryIDs.get(2));

        rootRef.child("cat4").setValue(categoryTitles.get(3));
        rootRef.child("cat4ID").setValue(categoryIDs.get(3));

        rootRef.child("cat5").setValue(categoryTitles.get(4));
        rootRef.child("cat5ID").setValue(categoryIDs.get(4));

        rootRef.child("cat6").setValue(categoryTitles.get(5));
        rootRef.child("cat6ID").setValue(categoryIDs.get(5));

    }

    private void uploadClues() {

        //cat 1 q's
        rootRef.child("clues").child("cat1").child("cat1Q1").setValue(cat1Questions.get(0));
        rootRef.child("clues").child("cat1").child("cat1Q2").setValue(cat1Questions.get(1));
        rootRef.child("clues").child("cat1").child("cat1Q3").setValue(cat1Questions.get(2));
        rootRef.child("clues").child("cat1").child("cat1Q4").setValue(cat1Questions.get(3));
        rootRef.child("clues").child("cat1").child("cat1Q5").setValue(cat1Questions.get(4));

        //cat 2
        rootRef.child("clues").child("cat2").child("cat2Q1").setValue(cat2Questions.get(0));
        rootRef.child("clues").child("cat2").child("cat2Q2").setValue(cat2Questions.get(1));
        rootRef.child("clues").child("cat2").child("cat2Q3").setValue(cat2Questions.get(2));
        rootRef.child("clues").child("cat2").child("cat2Q4").setValue(cat2Questions.get(3));
        rootRef.child("clues").child("cat2").child("cat2Q5").setValue(cat2Questions.get(4));

        //cat 3
        rootRef.child("clues").child("cat3").child("cat3Q1").setValue(cat3Questions.get(0));
        rootRef.child("clues").child("cat3").child("cat3Q2").setValue(cat3Questions.get(1));
        rootRef.child("clues").child("cat3").child("cat3Q3").setValue(cat3Questions.get(2));
        rootRef.child("clues").child("cat3").child("cat3Q4").setValue(cat3Questions.get(3));
        rootRef.child("clues").child("cat3").child("cat3Q5").setValue(cat3Questions.get(4));

        //cat 4
        rootRef.child("clues").child("cat4").child("cat4Q1").setValue(cat4Questions.get(0));
        rootRef.child("clues").child("cat4").child("cat4Q2").setValue(cat4Questions.get(1));
        rootRef.child("clues").child("cat4").child("cat4Q3").setValue(cat4Questions.get(2));
        rootRef.child("clues").child("cat4").child("cat4Q4").setValue(cat4Questions.get(3));
        rootRef.child("clues").child("cat4").child("cat4Q5").setValue(cat4Questions.get(4));

        //cat 5
        rootRef.child("clues").child("cat5").child("cat5Q1").setValue(cat5Questions.get(0));
        rootRef.child("clues").child("cat5").child("cat5Q2").setValue(cat5Questions.get(1));
        rootRef.child("clues").child("cat5").child("cat5Q3").setValue(cat5Questions.get(2));
        rootRef.child("clues").child("cat5").child("cat5Q4").setValue(cat5Questions.get(3));
        rootRef.child("clues").child("cat5").child("cat5Q5").setValue(cat5Questions.get(4));

        //cat 6
        rootRef.child("clues").child("cat6").child("cat6Q1").setValue(cat6Questions.get(0));
        rootRef.child("clues").child("cat6").child("cat6Q2").setValue(cat6Questions.get(1));
        rootRef.child("clues").child("cat6").child("cat6Q3").setValue(cat6Questions.get(2));
        rootRef.child("clues").child("cat6").child("cat6Q4").setValue(cat6Questions.get(3));
        rootRef.child("clues").child("cat6").child("cat6Q5").setValue(cat6Questions.get(4));

    }

    private void uploadAnswers() {

        //cat 1 a's
        rootRef.child("clues").child("cat1").child("cat1A1").setValue(cat1Answers.get(0));
        rootRef.child("clues").child("cat1").child("cat1A2").setValue(cat1Answers.get(1));
        rootRef.child("clues").child("cat1").child("cat1A3").setValue(cat1Answers.get(2));
        rootRef.child("clues").child("cat1").child("cat1A4").setValue(cat1Answers.get(3));
        rootRef.child("clues").child("cat1").child("cat1A5").setValue(cat1Answers.get(4));

        //cat 2 a's
        rootRef.child("clues").child("cat2").child("cat2A1").setValue(cat2Answers.get(0));
        rootRef.child("clues").child("cat2").child("cat2A2").setValue(cat2Answers.get(1));
        rootRef.child("clues").child("cat2").child("cat2A3").setValue(cat2Answers.get(2));
        rootRef.child("clues").child("cat2").child("cat2A4").setValue(cat2Answers.get(3));
        rootRef.child("clues").child("cat2").child("cat2A5").setValue(cat2Answers.get(4));

        //cat 3 a's
        rootRef.child("clues").child("cat3").child("cat3A1").setValue(cat3Answers.get(0));
        rootRef.child("clues").child("cat3").child("cat3A2").setValue(cat3Answers.get(1));
        rootRef.child("clues").child("cat3").child("cat3A3").setValue(cat3Answers.get(2));
        rootRef.child("clues").child("cat3").child("cat3A4").setValue(cat3Answers.get(3));
        rootRef.child("clues").child("cat3").child("cat3A5").setValue(cat3Answers.get(4));

        //cat 4 a's
        rootRef.child("clues").child("cat4").child("cat4A1").setValue(cat4Answers.get(0));
        rootRef.child("clues").child("cat4").child("cat4A2").setValue(cat4Answers.get(1));
        rootRef.child("clues").child("cat4").child("cat4A3").setValue(cat4Answers.get(2));
        rootRef.child("clues").child("cat4").child("cat4A4").setValue(cat4Answers.get(3));
        rootRef.child("clues").child("cat4").child("cat4A5").setValue(cat4Answers.get(4));

        //cat 5 a's
        rootRef.child("clues").child("cat5").child("cat5A1").setValue(cat5Answers.get(0));
        rootRef.child("clues").child("cat5").child("cat5A2").setValue(cat5Answers.get(1));
        rootRef.child("clues").child("cat5").child("cat5A3").setValue(cat5Answers.get(2));
        rootRef.child("clues").child("cat5").child("cat5A4").setValue(cat5Answers.get(3));
        rootRef.child("clues").child("cat5").child("cat5A5").setValue(cat5Answers.get(4));

        //cat 6 a's
        rootRef.child("clues").child("cat6").child("cat6A1").setValue(cat6Answers.get(0));
        rootRef.child("clues").child("cat6").child("cat6A2").setValue(cat6Answers.get(1));
        rootRef.child("clues").child("cat6").child("cat6A3").setValue(cat6Answers.get(2));
        rootRef.child("clues").child("cat6").child("cat6A4").setValue(cat6Answers.get(3));
        rootRef.child("clues").child("cat6").child("cat6A5").setValue(cat6Answers.get(4));
    }

    @OnClick({R.id.addPlayer_btn, R.id.start_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.addPlayer_btn:
                //get player from firebase, add to player 2, then 3
                String playerName = emailEditText.getText().toString();
                if (playerName2TextView.getText().toString().equals("")) {
                    playerName2TextView.setText(playerName);
                    FirebaseDatabase.getInstance().getReference().child("player2Email").setValue(playerName);
                    //FirebaseAuth.getInstance().getCurrentUser().getEmail();
                } else {
                    playerName2TextView.setText(playerName);
                    FirebaseDatabase.getInstance().getReference().child("player3Email").setValue(playerName);
                }

                break;
            case R.id.start_btn:
                //get game data?
                uploadClues();
                uploadAnswers();
                Intent intent = new Intent(PrepareGameActivity.this, MultiplayerGameScreenActivity.class);
                startActivity(intent);
                break;
        }
    }
}
