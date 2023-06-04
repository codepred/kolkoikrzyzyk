package com.example.kolkoikrzyzyk;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView playerOneScore, playerTwoScore, playerStatus;
    private Button [] buttons = new Button[9];
    private Button resetGame;

    private int rountCount;

    private Player player1 = new Player(0);
    private Player player2 = new Player(0);
    boolean activePlayer;

    int [] gameState = {2,2,2,2,2,2,2,2,2};

    int [][] winningPositions = {
            {0,1,2}, {3,4,5}, {6,7,8},
            {0,3,6}, {1,4,7}, {2,5,8},
            {0,4,8}, {2,4,6}
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        playerOneScore = (TextView) findViewById(R.id.playerOneScore);
        playerTwoScore = (TextView) findViewById(R.id.playerTwoScore);
        playerStatus = (TextView) findViewById(R.id.playerStatus);

        resetGame = (Button) findViewById(R.id.resetGame);

        for(int i=0; i< buttons.length; i++){
            String buttonID = "btn_" + i;
            int resourceID = getResources().getIdentifier(buttonID,"id",getPackageName());
            buttons[i] = (Button) findViewById(resourceID);
            buttons[i].setOnClickListener(this);
        }

        rountCount = 0;
        activePlayer = true;
    }

    @Override
    public void onClick(View v) {
        System.out.println("CLICKEDDD");

        if(!((Button)v).getText().toString().equals("")){
            return;
        }
        String buttonID = v.getResources().getResourceEntryName(v.getId());
        int gameStatePointer = Integer.parseInt(buttonID.substring(buttonID.length()-1,buttonID.length()));

        if(activePlayer){
            ((Button) v).setText("X");
            ((Button) v).setTextColor(Color.parseColor("#FFC34A"));
            gameState[gameStatePointer] = 0;
        }else {
            ((Button) v).setText("0");
            (( Button) v).setTextColor(Color.parseColor("#70FFEA"));
            gameState[gameStatePointer] = 1;
        }
        rountCount ++;

        if(checkWinner()){
            if(activePlayer){
                player1.setScoreCount(player1.getScoreCount()+1);
                updatePlayerScore();
                Toast.makeText(this,"Player one won!", Toast.LENGTH_SHORT).show();
                playAgain();
            }else {
                player2.setScoreCount(player2.getScoreCount()+1);
                updatePlayerScore();
                Toast.makeText(this,"Player two won!", Toast.LENGTH_SHORT).show();
                playAgain();
            }
        }else if(rountCount == 9){
            playAgain();
            Toast.makeText(this,"No winner!",Toast.LENGTH_SHORT).show();
        }else {
            activePlayer = !activePlayer;
        }

        if(player1.getScoreCount() > player2.getScoreCount()){
            playerStatus.setText("Player one is winning!");
        }
        else if(player1.getScoreCount() < player2.getScoreCount()){
            playerStatus.setText("Player two is winning!");
        }
        else {
            playerStatus.setText("");
        }

        resetGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playAgain();
                player1.setScoreCount(0);
                player2.setScoreCount(0);
                playerStatus.setText("");
                updatePlayerScore();
            }
        });

    }

    public boolean checkWinner(){
        boolean winnerResult = false;

        for(int [] winningPosition: winningPositions){
            if(gameState[winningPosition[0]] == gameState[winningPosition[1]] &&
                    gameState[winningPosition[1]] == gameState[winningPosition[2]] &&
            gameState[winningPosition[0]] !=2){
                winnerResult = true;
            }
        }
        return winnerResult;
    }

    public void updatePlayerScore(){
        playerOneScore.setText(Integer.toString(player1.getScoreCount()));
        playerTwoScore.setText(Integer.toString(player2.getScoreCount()));
    }

    public void playAgain(){
        rountCount = 0;
        activePlayer = true;
        for(int i=0; i< buttons.length ; i++){
            gameState[i] = 2;
            buttons[i].setText("");
        }
    }

}