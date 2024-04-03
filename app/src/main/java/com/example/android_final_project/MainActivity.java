package com.example.android_final_project;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Button run;
    Button reset;

    HangMan hang;

    RecyclerView word;

    EditText guessInput;

    ArrayList<String> contentData;

    CustomAdapter adapter;

    public ArrayList<String> generateBlanks(int blankNumber){

        ArrayList<String> holder = new ArrayList<>();

        for(int i = 0; i < blankNumber; i++){

            holder.add( "    ");
        }

        return holder;
    }



    public void updateToNextWord(){

        this.contentData.clear();

        // Blank number will be the length of the next word in the future
        ArrayList<String> blank = generateBlanks(7);

        this.contentData.addAll(blank);

        this.adapter.notifyDataSetChanged();



     }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.reset), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        hang = findViewById(R.id.hangMan);
        run = findViewById(R.id.run);
        reset = findViewById(R.id.gameReset);
        word = findViewById(R.id.word);
        guessInput = findViewById(R.id.guessInput);


        String phrase = "ILOVEPIZZA";


        ArrayList<String> test = new ArrayList<>();

        for(int i = 0; i < phrase.length(); i++){

            test.add(String.valueOf(phrase.charAt(i)));

        }

        this.contentData = test;

        adapter = new CustomAdapter(this.contentData);

        word.setAdapter(adapter);

        word.setLayoutManager(new GridLayoutManager(this,5));



        reset.setOnClickListener(r -> {

                hang.resetGuess();
                Snackbar gameMessage = Snackbar.make(r,"Reset Game",Snackbar.LENGTH_LONG);
                gameMessage.show();

                updateToNextWord();



        });

        run.setOnClickListener(r -> {

                hang.addWrongGuees();

                if(hang.failed()) {

                    Snackbar snack = Snackbar.make(r,"You Lose",Snackbar.LENGTH_LONG);
                    snack.show();
                    


                }

        });


    }
}