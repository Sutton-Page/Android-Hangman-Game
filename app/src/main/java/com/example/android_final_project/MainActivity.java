package com.example.android_final_project;

import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.snackbar.Snackbar;
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
    public static String randomWord;
    public static String userInput;
    public static int lengthMatched;
    public static ArrayList<String> test = new ArrayList<>();
    public static List<Integer> indexes = new ArrayList<>();

    public ArrayList<String> generateBlanks(int blankNumber){
        ArrayList<String> holder = new ArrayList<>();
        for(int i = 0; i < blankNumber; i++){
            holder.add( "    ");
        }
        return holder;
    }

    public AlertDialog.Builder generateDialog(boolean won){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        if(won){
            builder.setTitle(R.string.win);
        } else{
            builder.setTitle(R.string.lose);
        }
        builder.setMessage(R.string.game_message);
        builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                hang.resetGuess();
                resetGame();
            }
        });
        builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancels the dialog.
                Toast.makeText(MainActivity.this, "You have won this game already! Click reset to start a new game", Toast.LENGTH_SHORT).show();
            }
        });

        return builder;
    }
    private void resetGame() {
        System.out.println("Random Word: " + randomWord);
        hang.resetGuess();
        Snackbar gameMessage = Snackbar.make(reset, "Reset Game", Snackbar.LENGTH_LONG);
        gameMessage.show();
        updateToNextWord();
        lengthMatched = 0;
    }
    //generating blanks for the new word after resetting
    public void updateToNextWord(){
        this.contentData.clear();
        RandomWordGenerator wordGenerator = new RandomWordGenerator(this);
        randomWord = wordGenerator.getRandomWord();
        ArrayList<String> blank = generateBlanks(randomWord.length());

        this.contentData.addAll(blank);
        this.adapter.notifyDataSetChanged();

     }

public static List<Integer> getLetterIndexes(String randomWord, String userInput) {
    List<Integer> indexes = new ArrayList<>();
    for (int i = 0; i < randomWord.length(); i++) {
        if (Character.toLowerCase(randomWord.charAt(i) ) == Character.toLowerCase(userInput.charAt(0))) {
            indexes.add(i); // Add index to the list
        }
    }
    return indexes;
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
        lengthMatched = 0;

        RandomWordGenerator wordGenerator = new RandomWordGenerator(this);
        randomWord = wordGenerator.getRandomWord();
        System.out.println("Random Word: " + randomWord);

        ArrayList<String> blank = generateBlanks(randomWord.length());
        test = generateBlanks(randomWord.length());

        this.contentData = blank;
        adapter = new CustomAdapter(this.contentData);
        word.setAdapter(adapter);
        word.setLayoutManager(new GridLayoutManager(this,5));

        reset.setOnClickListener(r -> resetGame());

        run.setOnClickListener(r -> {
            indexes.clear();
            System.out.println("Random Word: " + randomWord);
            userInput = guessInput.getText().toString();
            System.out.println("Length matched: "+ lengthMatched );
            if(userInput.isEmpty()){
                Toast.makeText(this, "Guess cannot be empty. Please guess a letter!", Toast.LENGTH_SHORT).show();
            }
            else{
                indexes = getLetterIndexes(randomWord, userInput);
                System.out.println("Indexes size is: " + indexes.size());

                for(int i : indexes){
                    System.out.println("Character is: " + i);
                }
                if (!indexes.isEmpty()) {
                    boolean letterAlreadyFound = false;
                    for (String letter : test) {
                        // Check if the letter was already found
                        if (letter.equals(userInput)) {
                            letterAlreadyFound = true;
                            break;
                        }
                    }
                    if (letterAlreadyFound) {
                        Toast.makeText(this, "Letter Already Found", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Letter Found", Toast.LENGTH_SHORT).show();
                        lengthMatched += indexes.size();
                    }


                    for (int index : indexes) {
                        if(index == 0){ //if its the first letter, make it uppercase
                            test.set(index, userInput.toUpperCase());
                        }
                        else{
                            // Set the element at the indexes in the test ArrayList to userInput
                            test.set(index, userInput);
                        }
                    }
                    this.contentData = test;
                    adapter = new CustomAdapter(this.contentData);
                    word.setAdapter(adapter);
                    word.setLayoutManager(new GridLayoutManager(this,5));

                    System.out.println("Length matched: " + lengthMatched);
                    if( lengthMatched == randomWord.length()){
                        Snackbar snack = Snackbar.make(r,"You Win",Snackbar.LENGTH_LONG);
                        snack.show();
                        AlertDialog dialog = generateDialog(true).create();
                        dialog.show();
                    }
                } else {
                    hang.addWrongGuess();
                    Toast.makeText(this, "Letter Not Found", Toast.LENGTH_SHORT).show();
                }
            }
                if(hang.failed()) {
                    Snackbar snack = Snackbar.make(r,"You Lose",Snackbar.LENGTH_LONG);
                    snack.show();
                    AlertDialog dialog = generateDialog(false).create();
                    dialog.show();
                }
        });
    }
}