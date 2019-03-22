package com.example.zane.jeopardygame;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.zane.jeopardygame.model.Categories;
import com.example.zane.jeopardygame.model.ClueResults;
import com.example.zane.jeopardygame.model.Clues;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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

    @BindView(R.id.start_btn)
    Button startBtn;
    @BindView(R.id.prepareGame_layout)
    LinearLayout prepareGameLayout;
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
    @BindView(R.id.player1_textView)
    TextView player1TextView;
    @BindView(R.id.player2_textView)
    TextView player2TextView;
    @BindView(R.id.player3_textView)
    TextView player3TextView;


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
    DatabaseReference player1Ref = rootRef.child("player1Email");
    DatabaseReference player2Ref = rootRef.child("player2Email");
    DatabaseReference player3Ref = rootRef.child("player3Email");
    DatabaseReference gameStartedRef = rootRef.child("gameStarted");

    private int playerSlot;
    private String player1 = "" , player2 = "" , player3 = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prepare_game);
        ButterKnife.bind(this);

        rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                player1 = dataSnapshot.child("player1Email").getValue(String.class);
                player2 = dataSnapshot.child("player2Email").getValue(String.class);
                player3 = dataSnapshot.child("player3Email").getValue(String.class);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        setPlayerSlot();

        gameStartedRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue(String.class).equals("true")) {

                    Intent intent = new Intent(PrepareGameActivity.this, MultiplayerGameScreenActivity.class);
                    intent.putExtra("playerSlot", playerSlot);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        player1Ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                player1TextView.setText("Player 1: " + dataSnapshot.getValue(String.class));


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        player2Ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                player2TextView.setText("Player 2: " + dataSnapshot.getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        player3Ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                player3TextView.setText("Player 3: " + dataSnapshot.getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    private void setPlayerSlot() {
        if(player1.equals("")){
            getCategories();
            rootRef.child("player1Email").setValue(FirebaseAuth.getInstance().getCurrentUser().getEmail());
            playerSlot = 1;
        } else if (player2.equals("")){
            rootRef.child("player2Email").setValue(FirebaseAuth.getInstance().getCurrentUser().getEmail());
            playerSlot = 2;
        } else if (player3.equals("")) {
            rootRef.child("player3Email").setValue(FirebaseAuth.getInstance().getCurrentUser().getEmail());
            playerSlot = 3;
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
                    if (results.get(index).getClues_count() >= 5 && !categoryIDs.contains(results.get(index).getId()) && results.get(index).getTitle().length() < 15) {
                        categoryTitles.add(results.get(index).getTitle());
                        categoryIDs.add(results.get(index).getId());

                    } else {
                        i--;
                    }
                }
                uploadCategories();
                displayCategories();
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
                        if (results.get(i).getAnswer().equals("") || results.get(i).getQuestion().equals("")) {
                            categoryTitles.clear();
                            categoryIDs.clear();
                            getCategories();
                            break;
                        } else {
                            switch (index) {
                                //check if q is null
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

    private void displayCategories() {
        cat1TitleTextView.setText(categoryTitles.get(0));
        cat2TitleTextView.setText(categoryTitles.get(1));
        cat3TitleTextView.setText(categoryTitles.get(2));
        cat4TitleTextView.setText(categoryTitles.get(3));
        cat5TitleTextView.setText(categoryTitles.get(4));
        cat6TitleTextView.setText(categoryTitles.get(5));
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

    @OnClick(R.id.start_btn)
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.start_btn:
                uploadClues();
                uploadAnswers();
                rootRef.child("gameStarted").setValue("true");
                break;
        }
    }

    //dialog if back pressed
    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(this, R.style.AlertDialog);
        builder.setTitle("Leave Lobby?");
        builder.setPositiveButton("Yes", (dialogInterface, i) -> {
            //remove player from lobby list
            if (playerSlot == 1) {
                player1TextView.setText("Player 1: ");
                rootRef.child("player1Email").setValue("");
            } else if (playerSlot == 2) {
                player2TextView.setText("Player 2: ");
                rootRef.child("player2Email").setValue("");
            } else {
                player3TextView.setText("Player 3: ");
                rootRef.child("player3Email").setValue("");
            }

            Intent intent = new Intent(PrepareGameActivity.this, MainActivity.class);
            startActivity(intent);
        });
        builder.setNegativeButton("No", (dialogInterface, i) -> {

        });
        AlertDialog dialog = builder.create();
        dialog.show();

    }
}
