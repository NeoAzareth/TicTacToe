package com.sichengzhu.tictactoe;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class TwoPlayerActivity extends Activity implements OnClickListener {
    // create 9 buttons
    private Button[][] button = new Button[3][3];

    private TextView gameMessageLabel;

    private GameLogic gameLogic;
    private SharedPreferences prefs;

    private Player playerOne;
    private Player playerTwo;

    private MediaPlayer sound;
    private MediaPlayer winSound;

    // Flag to check if one player wins game
    private boolean isGameOver;
    // Flag to check if game tie
    private boolean isTie;
    //set turn
    private char isTurn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two_player);

        gameLogic = new GameLogic();

        //initialize buttons
        button[0][0] = (Button)findViewById(R.id.one_one);
        button[0][1] = (Button)findViewById(R.id.one_two);
        button[0][2] = (Button)findViewById(R.id.one_three);
        button[1][0] = (Button)findViewById(R.id.two_one);
        button[1][1] = (Button)findViewById(R.id.two_two);
        button[1][2] = (Button)findViewById(R.id.two_three);
        button[2][0] = (Button)findViewById(R.id.three_one);
        button[2][1] = (Button)findViewById(R.id.three_two);
        button[2][2] = (Button)findViewById(R.id.three_three);

        // initialize message label
        gameMessageLabel = (TextView)findViewById(R.id.game_message);

//        Typeface type = Typeface.createFromAsset(getAssets(), "fonts/Cheveuxdange.ttf");
//        gameMessageLabel.setTypeface(type);

        playerOne = new Player("O",'o');
        playerTwo = new Player("X",'x');

        sound = MediaPlayer.create(this, R.raw.pencil);
        winSound = MediaPlayer.create(this, R.raw.woohoo);

        /**
         *  Set initial message for new game or resumed game
         *  Assume player one kicks off this game first if new game
         */
        if(isTurn == '\0' || isTurn == playerOne.getPlayerMarker())
            gameMessageLabel.setText("Player " + playerOne.getName() + "'s turn");
        else if(isTurn == playerTwo.getPlayerMarker())
            gameMessageLabel.setText("Player " + playerTwo.getName() + "'s turn");

        //set click listeners
        button[0][0].setOnClickListener(this);
        button[0][1].setOnClickListener(this);
        button[0][2].setOnClickListener(this);
        button[1][0].setOnClickListener(this);
        button[1][1].setOnClickListener(this);
        button[1][2].setOnClickListener(this);
        button[2][0].setOnClickListener(this);
        button[2][1].setOnClickListener(this);
        button[2][2].setOnClickListener(this);

        // Enable screen rotation
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR);

        // get default SharedPreferences object
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
    }

    @Override
    public void onPause() {
        // save the instance variables
        Editor editor = prefs.edit();

        char[][] currentBoard = gameLogic.getBoard();
        editor.putString("one_one_marker",String.valueOf(currentBoard[0][0]));
        editor.putString("one_two_marker",String.valueOf(currentBoard[0][1]));
        editor.putString("one_three_marker",String.valueOf(currentBoard[0][2]));
        editor.putString("two_one_marker",String.valueOf(currentBoard[1][0]));
        editor.putString("two_two_marker",String.valueOf(currentBoard[1][1]));
        editor.putString("two_three_marker",String.valueOf(currentBoard[1][2]));
        editor.putString("three_one_marker",String.valueOf(currentBoard[2][0]));
        editor.putString("three_two_marker",String.valueOf(currentBoard[2][1]));
        editor.putString("three_three_marker", String.valueOf(currentBoard[2][2]));

        editor.putString("turn", String.valueOf(isTurn));
        editor.putBoolean("isGameOver", isGameOver);
        editor.putBoolean("isTie", isTie);
        editor.commit();

        super.onPause();
    }

    /**
     *  Do nothing for very first game
     *  Set the board for continued game
     */
    @Override
    public void onResume() {
        super.onResume();

        char[][] newBoard = new char[3][3];

        if(prefs.getString("one_one_marker", "").length() != 0) {
            newBoard[0][0] = prefs.getString("one_one_marker", "").charAt(0);
            setBackground(newBoard[0][0],button[0][0]);
        }

        if(prefs.getString("one_two_marker", "").length() != 0) {
            newBoard[0][1] = prefs.getString("one_two_marker", "").charAt(0);
            setBackground(newBoard[0][1],button[0][1]);
        }

        if(prefs.getString("one_three_marker", "").length() != 0) {
            newBoard[0][2] = prefs.getString("one_three_marker", "").charAt(0);
            setBackground(newBoard[0][2],button[0][2]);
        }

        if(prefs.getString("two_one_marker", "").length() != 0) {
            newBoard[1][0] = prefs.getString("two_one_marker", "").charAt(0);
            setBackground(newBoard[1][0],button[1][0]);
        }

        if(prefs.getString("two_two_marker", "").length() != 0) {
            newBoard[1][1] = prefs.getString("two_two_marker", "").charAt(0);
            setBackground(newBoard[1][1],button[1][1]);
        }

        if(prefs.getString("two_three_marker", "").length() != 0) {
            newBoard[1][2] = prefs.getString("two_three_marker", "").charAt(0);
            setBackground(newBoard[1][2],button[1][2]);
        }

        if(prefs.getString("three_one_marker", "").length() != 0) {
            newBoard[2][0] = prefs.getString("three_one_marker", "").charAt(0);
            setBackground(newBoard[2][0],button[2][0]);
        }

        if(prefs.getString("three_two_marker", "").length() != 0) {
            newBoard[2][1] = prefs.getString("three_two_marker", "").charAt(0);
            setBackground(newBoard[2][1],button[2][1]);
        }

        if(prefs.getString("three_three_marker", "").length() != 0) {
            newBoard[2][2] = prefs.getString("three_three_marker", "").charAt(0);
            setBackground(newBoard[2][2],button[2][2]);
        }

        if(prefs.getString("turn", "").length() != 0)
            isTurn = prefs.getString("turn", "").charAt(0);

        if(isTurn == playerOne.getPlayerMarker())
            gameMessageLabel.setText("Player " + playerOne.getName() + "'s turn");
        else if(isTurn == playerTwo.getPlayerMarker())
            gameMessageLabel.setText("Player " + playerTwo.getName() + "'s turn");

        isGameOver = prefs.getBoolean("isGameOver", false);
        isTie = prefs.getBoolean("isTie", false);

        // If player one has won, give turn to player for next game, and vice versa
        if(isGameOver) {
            if(isTurn == playerTwo.getPlayerMarker())
                gameMessageLabel.setText(playerOne.getName() + " wins!");
            else if(isTurn == playerOne.getPlayerMarker())
                gameMessageLabel.setText(playerTwo.getName() + " wins!");

            disableBoard();
        } else if(isTie) {
            gameMessageLabel.setText("Game tie");
            disableBoard();
        }

        gameLogic.setBoard(newBoard);
    }

    /**
     * If one player wins, or tie,disable all buttons
     */
    private void disableBoard() {
        for(int row = 0; row < 3; row++)
            for(int column = 0; column < 3; column++)
                if(button[row][column].isEnabled())
                    button[row][column].setEnabled(false);
    }

    /**
     * When one player click a button, override a picture with "o" or "x" mark on this button
     * @param c player's turn
     * @param b a specific button
     */
    public void setBackground(char c, Button b){
        if (c == 'x')
            b.setBackgroundResource(R.drawable.tic_tac_toe_x);
         else if (c == 'o')
            b.setBackgroundResource(R.drawable.tic_tac_toe_o);
    }

    @Override
    public void onClick(View v){
        switch (v.getId()) {
            case R.id.one_one:
                processClick(0, 0);
                break;

            case R.id.one_two:
                processClick(0, 1);
                break;

            case R.id.one_three:
                processClick(0, 2);
                break;

            case R.id.two_one:
                processClick(1, 0);
                break;

            case R.id.two_two:
                processClick(1, 1);
                break;

            case R.id.two_three:
                processClick(1, 2);
                break;

            case R.id.three_one:
                processClick(2, 0);
                break;

            case R.id.three_two:
                processClick(2, 1);
                break;

            case R.id.three_three:
                processClick(2, 2);
                break;
        }
    }

    /**
     * process button, and check game status
     * @param row row index of specified button
     * @param column colunm index of specified button
     */
    private void processClick(int row, int column){
        if(isTurn == 'x'){
            if(isGameOver)
                button[row][column].setEnabled(false);

            else{
                gameLogic.addMarker('x', row, column);
                button[row][column].setBackgroundResource(R.drawable.tic_tac_toe_x);
                sound.start();
                button[row][column].setEnabled(false);
                checkGameStatus();

                if(!isGameOver)
                    gameMessageLabel.setText("Player " + playerOne.getName() + "'s turn");

                if(isTie)
                    gameMessageLabel.setText("Game tie");
            }
            isTurn = 'o';
        }
        else {
            if(isGameOver)
                button[row][column].setEnabled(false);

            else{
                gameLogic.addMarker('o', row, column);
                button[row][column].setBackgroundResource(R.drawable.tic_tac_toe_o);
                sound.start();
                button[row][column].setEnabled(false);
                checkGameStatus();

                if(!isGameOver)
                    gameMessageLabel.setText("Player " + playerTwo.getName() + "'s turn");

                if(isTie)
                    gameMessageLabel.setText("Game tie");
            }
            isTurn = 'x';
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_two_player, menu);
        return true;
    }

    /**
     * check if one player wins game, or game tie
     */
    public void checkGameStatus(){
        if (gameLogic.hasWon()) {
            isGameOver = true;

            if (gameLogic.getWinningMarker() == playerOne.getPlayerMarker()) {
                gameMessageLabel.setText(playerOne.getName() + " wins!");
                isTurn = playerTwo.getPlayerMarker();
            } else {
                gameMessageLabel.setText(playerTwo.getName() + " wins!");
                isTurn = playerOne.getPlayerMarker();
            }

            winSound.start();

        } else if (gameLogic.checkFull()) {
            isTie = true;

            if(isTurn == playerOne.getPlayerMarker()){
                isTurn = playerTwo.getPlayerMarker();
            }else if(isTurn == playerTwo.getPlayerMarker()){
                isTurn = playerOne.getPlayerMarker();
            }
            gameMessageLabel.setText("Game ties.");

            winSound = MediaPlayer.create(this, R.raw.crowd_boo);
            winSound.start();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_new_game:
                Intent newGame = new Intent(TwoPlayerActivity.this, TwoPlayerActivity.class);
                newGame.putExtra("playerOne", playerOne);
                newGame.putExtra("playerTwo", playerTwo);
                newGame.putExtra("isTurn", isTurn);
                isGameOver = false;
                isTie = false;
                newGame.putExtra("isGameOver", isGameOver);
                newGame.putExtra("isTie", isTie);
                gameLogic.initBoard();
                startActivity(newGame);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
