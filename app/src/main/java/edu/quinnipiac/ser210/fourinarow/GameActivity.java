package edu.quinnipiac.ser210.fourinarow;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class GameActivity extends AppCompatActivity {

    private static int HUMAN_PLAYER = FourInARow.RED;
    private static int COMPUTER_PLAYER = FourInARow.BLUE;

    private boolean playing;

    private FourInARow FIRboard;

    private TextView currentPlayer;
    private String playerName;
    private ArrayList<Button> buttons;
    private static final int[] BUTTON_IDS = {
        R.id.row_0_col_0,
        R.id.row_0_col_1,
        R.id.row_0_col_2,
        R.id.row_0_col_3,
        R.id.row_0_col_4,
        R.id.row_0_col_5,
        R.id.row_1_col_0,
        R.id.row_1_col_1,
        R.id.row_1_col_2,
        R.id.row_1_col_3,
        R.id.row_1_col_4,
        R.id.row_1_col_5,
        R.id.row_2_col_0,
        R.id.row_2_col_1,
        R.id.row_2_col_2,
        R.id.row_2_col_3,
        R.id.row_2_col_4,
        R.id.row_2_col_5,
        R.id.row_3_col_0,
        R.id.row_3_col_1,
        R.id.row_3_col_2,
        R.id.row_3_col_3,
        R.id.row_3_col_4,
        R.id.row_3_col_5,
        R.id.row_4_col_0,
        R.id.row_4_col_1,
        R.id.row_4_col_2,
        R.id.row_4_col_3,
        R.id.row_4_col_4,
        R.id.row_4_col_5,
        R.id.row_5_col_0,
        R.id.row_5_col_1,
        R.id.row_5_col_2,
        R.id.row_5_col_3,
        R.id.row_5_col_4,
        R.id.row_5_col_5
    };

    private Button reset;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        if(savedInstanceState != null){
            playing = savedInstanceState.getBoolean("playing");
            playerName = savedInstanceState.getString("playerName");
            currentPlayer = (TextView) findViewById(R.id.current_player);
            FIRboard = new FourInARow();
            FIRboard.setBoard((int[][]) savedInstanceState.getSerializable("board"));
            FIRboard.setCurrentPlayer(savedInstanceState.getInt("currentPlayer"));
            if(FIRboard.getCurrentPlayer() == HUMAN_PLAYER){
                currentPlayer.setText("Current Player: " + playerName);
            }else{
                currentPlayer.setText("Current Player: Computer");
            }
        }else{
            FIRboard = new FourInARow();
            FIRboard.setCurrentPlayer(HUMAN_PLAYER);
            currentPlayer = findViewById(R.id.current_player);
            playerName = getIntent().getStringExtra("PLAYER_NAME");
            currentPlayer.setText("Current Player: " + playerName);
            playing = true;
            reset = (Button) findViewById(R.id.reset_button);

            buttons = new ArrayList<Button>();

            for(int id : BUTTON_IDS){
                Button button = (Button)findViewById(id);
                buttons.add(button);
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putBoolean("playing", playing);
        savedInstanceState.putString("playerName", playerName);
        savedInstanceState.putInt("currentPlayer", FIRboard.getCurrentPlayer());
        savedInstanceState.putSerializable("board", FIRboard.getBoard());
    }

    public void onTileClick(View view){
        int checkPlayer = FIRboard.getCurrentPlayer();
        if(checkPlayer == HUMAN_PLAYER){
            String clickedButton = getResources().getResourceEntryName(view.getId());

            int row = Integer.parseInt(clickedButton.charAt(4)+"");
            int col = Integer.parseInt(clickedButton.charAt(10)+"");

            if(FIRboard.getBoard()[row][col] == FIRboard.EMPTY){
                int playerMove = 6 * row + col;

                FIRboard.setMove(HUMAN_PLAYER, playerMove);
                buttons.get(playerMove).setBackgroundResource(R.drawable.x);
                checkGameState();
                if(playing){
                    computerMove();
                }
            }
        }
    }

    public void onReset(View view){
        if(reset.isEnabled() == true){
            Intent intent = getIntent();
            finish();
            startActivity(intent);
        }
    }

    public void computerMove(){
        FIRboard.setCurrentPlayer(COMPUTER_PLAYER);
        currentPlayer.setText("Current Player: Computer");

        //This implementation, while not needed, makes it seem like the computer player is thinking.
        //Yes, this could end up taking a while, but that just makes the computer seem like it's thinking more deeply.
        Handler handler = new Handler();
        handler.postDelayed(new Runnable(){

            @Override
            public void run() {
                int computerMove = FIRboard.getComputerMove();
                int computerRow = computerMove / 6;
                int computerCol = computerMove % 6;

                if(FIRboard.getBoard()[computerRow][computerCol] == FIRboard.EMPTY){
                    FIRboard.setMove(COMPUTER_PLAYER, computerMove);
                    buttons.get(computerMove).setBackgroundResource(R.drawable.o);
                    checkGameState();
                    if(playing){
                        FIRboard.setCurrentPlayer(HUMAN_PLAYER);
                        currentPlayer.setText("Current Player: " + playerName);
                    }
                }else{
                    computerMove();
                }
            }
        }, 1500);

    }

    public void checkGameState(){
        int gameState = FIRboard.checkForWinner();
        if(gameState == FourInARow.RED_WON){
            playing = false;
            reset.setEnabled(true);
            reset.setVisibility(View.VISIBLE);
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("You Win!").setTitle("Congrats!");
            AlertDialog dialog = builder.create();
            dialog.show();
        }else if(gameState == FourInARow.BLUE_WON){
            playing = false;
            reset.setEnabled(true);
            reset.setVisibility(View.VISIBLE);
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("You Lose...").setTitle("Oh no...");
            AlertDialog dialog = builder.create();
            dialog.show();
        }else if(gameState == FourInARow.TIE){
            playing = false;
            reset.setEnabled(true);
            reset.setVisibility(View.VISIBLE);
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("You Tied!").setTitle("Way to go!");
            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }
}