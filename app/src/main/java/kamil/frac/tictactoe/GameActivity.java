package kamil.frac.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class GameActivity extends AppCompatActivity{
    private GamePlay gamePlay = null;
    boolean gameActivityEnd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        initialize();
    }

    private void initialize() {
        MainActivity.Player[] player = new MainActivity.Player[2];
        player[0] = (MainActivity.Player) getIntent().getSerializableExtra("player1");
        player[1] = (MainActivity.Player) getIntent().getSerializableExtra("player2");
        TextView str1 = findViewById(R.id.textView);
        str1.setVisibility(View.VISIBLE);
        TextView whoIsPlaying = findViewById(R.id.currentPlayer);
        whoIsPlaying.setVisibility(View.VISIBLE);
        TextView gameResult = findViewById(R.id.gameResult);
        gameResult.setVisibility(View.INVISIBLE);
        TableLayout table = findViewById(R.id.tableLayout);
        Button[][] buttons = new Button[3][3];
        for (int j = 0; j < 3; j++) {
            TableRow row = (TableRow) table.getChildAt(j);
            for (int i = 0; i < 3; i++) {
                buttons[j][i] = (Button) row.getChildAt(i);
                buttons[j][i].setText("");
            }
        }
        if (gamePlay != null) gamePlay.stopGamePlayThread();

        gamePlay = new GamePlay(buttons ,player, this);
        gamePlay.startGamePlayThread();
        gameActivityEnd = false;
    }

     public void playerClick(View v) {
        Button button = findViewById(v.getId());
        if(!button.getText().toString().equals("") || gameActivityEnd || gamePlay.getCurrentPlayer().isCPU) return;
        gamePlay.countMarkedFields++;
        button.setText(gamePlay.getCurrentPlayer().sign);
        gamePlay.getCurrentPlayer().clickedButton = true;
    }

    public void resetButton(View v){
        if(gamePlay != null) gamePlay.stopGamePlayThread();
        initialize();
    }

    public void changePlayer(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                TextView whoIsPlaying = findViewById(R.id.currentPlayer);
                gamePlay.currentPlayer = gamePlay.currentPlayer == 0? 1:0;
                whoIsPlaying.setText(gamePlay.getCurrentPlayer().name);
            }
        });
    }

    public void endGame(boolean isDraw){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                gameActivityEnd = true;
                TextView gameResult = findViewById(R.id.gameResult);
                String result;
                if(!isDraw) {
                    result = "Winner: " + gamePlay.getCurrentPlayer().name;
                    gamePlay.countMarkedFields = 9;
                }
                else
                    result = "Draw";
                gameResult.setText(result);
                gameResult.setVisibility(View.VISIBLE);
                TextView str1 = findViewById(R.id.textView);
                TextView str2 = findViewById(R.id.currentPlayer);
                str1.setVisibility(View.INVISIBLE);
                str2.setVisibility(View.INVISIBLE);
            }
        });
    }

}