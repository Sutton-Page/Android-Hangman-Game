package com.example.android_final_project;

import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.snackbar.Snackbar;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Button run;
    Button reset;
    TextView display;
    Handler handle;

    HangMan hang;


    public static String makeRequest(String apiUrl) throws Exception{

        URL request = new URL(apiUrl);
        HttpURLConnection con = (HttpURLConnection) request.openConnection();
        con.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));

        String inputLine;

        StringBuffer content = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
        con.disconnect();

        return content.toString();

    }

    public static String listVideos(List<Video> videos,int limit){

        String message = "";

        for(int i =0; i < limit; i++){

            Video item = videos.get(i);
            message+= item.getTitle() + "\n" + item.getUrl() + "\n";
        }


        return message;
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

        reset.setOnClickListener(r -> {

                hang.resetGuess();
                Snackbar gameMessage = Snackbar.make(r,"Reset Game",Snackbar.LENGTH_LONG);
                gameMessage.show();

        });

        run.setOnClickListener(r -> {

                hang.addWrongGuees();

                if(hang.failed()) {

                    Snackbar snack = Snackbar.make(r,"You Lose",Snackbar.LENGTH_LONG);
                    snack.show();
                    //hang.resetGuess();


                }

        });

        /*
        handle = new Handler(Looper.getMainLooper());

        run = findViewById(R.id.run);
        display = findViewById(R.id.display);

        run.setOnClickListener((event) -> {

            Thread thread = new Thread(() ->{

                YoutubeApi api = new YoutubeApi("US","25",12);
                try {
                    String result = makeRequest(api.getVideoAPIUrl());
                    List<Video> parsedData = YoutubeApi.parseJson(result);

                    String message = listVideos(parsedData,5);

                    handle.post(() ->{

                        display.append(message);

                    });


                } catch (Exception e) {
                    Log.d("URL",e.getMessage());
                    throw new RuntimeException(e);
                }




            });

            thread.start();

        });

        */

    }
}