//Main Game
package com.example.rockpaperscissors;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainGame extends AppCompatActivity {

    Button rockButton, paperButton, scissorsButton, exitButton, endButton;
    TextView humanPoints, compPoints, scoreSystem, endTitle, endText, monitor;
    ImageView humanChoice, compChoice;
    int humanScore, compScore;
    Dialog endScreen;
    MediaPlayer background_music;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main_game);
        rockButton = findViewById(R.id.rockButton);
        paperButton = findViewById(R.id.paperButton);
        scissorsButton = findViewById(R.id.scissorsButton);
        exitButton = findViewById(R.id.exitButton);
        humanPoints = findViewById(R.id.humanPoints);
        compPoints = findViewById(R.id.compPoints);
        scoreSystem = findViewById(R.id.scoreSystem);
        humanChoice = findViewById(R.id.humanChoice);
        compChoice = findViewById(R.id.compChoice);
        monitor = findViewById(R.id.monitor);
        endScreen = new Dialog(this );


        background_music = new MediaPlayer( );
        background_music = MediaPlayer.create(this, R.raw.ultrainstinct);
        background_music.start();
        background_music.setLooping(true);
        String score = getIntent().getStringExtra("Score");
        int number = Integer.parseInt(score.toString().trim());

        final EditText inputNumber = findViewById(R.id.inputNumber);

        scoreSystem.setText("Best of " + score + " points!");
        exitButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                background_music.stop();
                startActivity(new Intent(MainGame.this, MainMenu.class));
            }
        });

        rockButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                humanChoice.setImageResource(R.drawable.rock);
                comp_turn("rock");

            }
        });

        paperButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                humanChoice.setImageResource(R.drawable.paper);
                comp_turn("paper");
            }
        });

        scissorsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                humanChoice.setImageResource(R.drawable.scissors);
                comp_turn("scissors");
            }
        });
      /*
    while (humanScore == number && compScore == number) {
        if (humanScore > compScore) {
            endScreen.setContentView(R.layout.tutorial_popup);
            endScreen.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            endTitle.setText("YOU WIN!");
            endText.setText("Congratulations! You won! You can retry if you would like but however, you will be playing with the same points as the last round.");
            endScreen.show();
        } else if (compScore > humanScore) {
            endScreen.setContentView(R.layout.tutorial_popup);
            endScreen.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            endTitle.setText("YOU LOSE!");
            endText.setText("You lost? How? Come on man try again this is embarrassing.");
            endScreen.show();
        }
    }*/

    monitor.addTextChangedListener(new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {

            endScreen.setOnShowListener(new DialogInterface.OnShowListener() {
                @Override
                public void onShow(DialogInterface dialog) {
                    String score = getIntent().getStringExtra("Score");
                    int number = Integer.parseInt(score.toString().trim());
                    endButton = endScreen.findViewById(R.id.endButton);
                    endButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //Turn this into zeroing all points instead of going back to menu
                            humanScore = 0;
                            compScore = 0;
                            endScreen.dismiss();
                            compPoints.setText("CPU: "+ compScore + " POINTS");
                            humanPoints.setText("YOU: "+ humanScore + " POINTS");

                        }

                    });

                }

            });
            if (humanScore >= number || compScore >= number) {
                endScreen.setContentView(R.layout.end_screen);
                endScreen.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                endText = endScreen.findViewById(R.id.endText);
                endTitle = endScreen.findViewById(R.id.endTitle);
                if (humanScore >= compScore) {
                    endTitle.setText("YOU WIN!");
                    endText.setText("Congratulations! You won! You can retry if you would like but however, you will be playing with the same points as the last round.");
                    //openDialog();
                } else {
                    endTitle.setText("YOU LOSE!");
                    endText.setText("You lost? How? Come on man try again this is embarrassing.");
                    //openDialog();
                }
                endScreen.show();
            }

        }
    });


    }



    public String comp_turn(String human_choice) {
        String cpu_choice = "";
        Random r = new Random();

        int cpu_choice_number = r.nextInt(3)+1;

        if (cpu_choice_number == 1) {
            cpu_choice = "rock";
            compChoice.setImageResource(R.drawable.rock);
        } else if (cpu_choice_number == 2) {
            cpu_choice = "paper";
            compChoice.setImageResource(R.drawable.paper);
        } else if (cpu_choice_number == 3) {
            cpu_choice = "scissors";
            compChoice.setImageResource(R.drawable.scissors);
        }

        if (cpu_choice == human_choice) {
            Toast.makeText(getBaseContext(), "Draw! Nobody takes the point!" , Toast.LENGTH_SHORT).show();
            return "Draw! Nobody takes the point!";
        }
        else if (cpu_choice == "rock" && human_choice == "scissors") {
            compScore++;
            compPoints.setText("CPU: "+ compScore + " POINTS");
            monitor.setText(Integer.toString(humanScore+compScore));
            Toast.makeText(getBaseContext(), "Rock crushes scissors! Point to CPU!" , Toast.LENGTH_SHORT).show();
            return "Rock crushes scissors! Point to CPU!";
        }
        else if (cpu_choice == "paper" && human_choice == "rock") {
            compScore++;
            compPoints.setText("CPU: "+ compScore + " POINTS");
            monitor.setText(Integer.toString(humanScore+compScore));
            Toast.makeText(getBaseContext(), "Paper beats rock! Point to CPU!" , Toast.LENGTH_SHORT).show();
            return "Paper beats rock! Point to CPU!";
        }
        else if (cpu_choice == "scissors" && human_choice == "paper") {
            compScore++;
            compPoints.setText("CPU: "+ compScore + " POINTS");
            monitor.setText(Integer.toString(humanScore+compScore));
            Toast.makeText(getBaseContext(), "Scissors cuts paper! Point to CPU!" , Toast.LENGTH_SHORT).show();
            return "Scissors cuts paper! Point to CPU!";
        }
        else if (cpu_choice == "rock" && human_choice == "paper") {
            humanScore++;
            humanPoints.setText("YOU: "+ humanScore + " POINTS");
            monitor.setText(Integer.toString(humanScore+compScore));
            Toast.makeText(getBaseContext(), "Paper beats rock! Point to Human!" , Toast.LENGTH_SHORT).show();
            return "Paper beats rock! Point to Human!";
        }
        else if (cpu_choice == "paper" && human_choice == "scissors") {
            humanScore++;
            humanPoints.setText("YOU: "+ humanScore + " POINTS");
            monitor.setText(Integer.toString(humanScore+compScore));
            Toast.makeText(getBaseContext(), "Scissors cuts paper! Point to Human!" , Toast.LENGTH_SHORT).show();
            return "Scissors cuts paper! Point to Human!";
        }
        else if (cpu_choice == "scissors" && human_choice == "rock") {
            humanScore++;
            humanPoints.setText("YOU: "+ humanScore + " POINTS");
            monitor.setText(Integer.toString(humanScore+compScore));
            Toast.makeText(getBaseContext(), "Rock crushes scissors! Point to Human!" , Toast.LENGTH_SHORT).show();
            return "Rock crushes scissors! Point to Human!";
        } else {
            return "Return Nothing";
        }
    }


}
