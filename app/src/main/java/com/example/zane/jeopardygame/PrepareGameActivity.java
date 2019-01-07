package com.example.zane.jeopardygame;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.zane.jeopardygame.model.Categories;
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


    private ArrayList<String> categoryTitles = new ArrayList<>();
    private ArrayList<Integer> categoryIDs = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prepare_game);
        ButterKnife.bind(this);

        playerName1TextView.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());

        uploadCategories();
    }

    private void uploadCategories() {
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
                for (int i = 0; i < 1; i++) {
                    int index = new Random().nextInt(100);
                    if (results.get(index).getClues_count() >= 5) {

                        //categoryTitles.add(results.get(index).getTitle());
                        //categoryIDs.add(results.get(index).getId());
                        //categoryClues_count.add(results.get(index).getClues_count());


                    } else {
                        i--;
                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Categories>> call, Throwable t) {

            }
        });
    }

    @OnClick({R.id.addPlayer_btn, R.id.start_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.addPlayer_btn:
                //get player from firebase, add to player 2, then 3
                String playerName = emailEditText.getText().toString();
                if(playerName2TextView.getText().toString().equals("")){
                    playerName2TextView.setText(playerName);
                    FirebaseDatabase.getInstance().getReference().setValue("player2Name", playerName);
                    //FirebaseAuth.getInstance().getCurrentUser().getEmail();
                } else {
                    playerName2TextView.setText(playerName);
                    FirebaseDatabase.getInstance().getReference().setValue("player3Name", playerName);
                }

                break;
            case R.id.start_btn:
                //get game data?

                break;
        }
    }
}
