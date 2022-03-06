package kamil.frac.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.Random;

public class GameActivity extends AppCompatActivity {

    MainActivity.Player[] player = new MainActivity.Player[2];
    Button [][] buttons = new Button[3][3];
    int currentPlayer;
    int countMarkedFields;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        player[0]=  (MainActivity.Player) getIntent().getSerializableExtra("player1");
        player[1] =  (MainActivity.Player) getIntent().getSerializableExtra("player2");

       reset(this.getCurrentFocus());
    }

    public void playerClick(View v) {
        Button button = (Button) findViewById(v.getId());
        if(!button.getText().toString().equals("")) return;
        countMarkedFields++;
        button.setText(player[currentPlayer].sign);

        if(countMarkedFields == 9) {
            endGame(false);
            return;
        }
        if( checkWin() ) endGame(true);
        changePlayer();
    }

    private void changePlayer(){
        TextView whoIsPlaying = (TextView) findViewById(R.id.currentPlayer);
        if(currentPlayer == 0) currentPlayer = 1;
        else currentPlayer = 0;
        whoIsPlaying.setText(player[currentPlayer].name);
    }

    public boolean checkWin()
    {
        int rowPoints , columnPoints = 0; int xPoints = 0;
        int yDiagonal = 2, yPoints = 0;

        for (int i = 0; i < 3; i++)
        {
            rowPoints = 0;
            for (int j = 0; j < 3; j++) {
                String button = buttons[i][j].getText().toString();
                if (button.equals( player[currentPlayer].sign )){
                    rowPoints++;
                    if (rowPoints == 3)
                        return true;

                    for (int o = 0; o < 3; o++) {
                         button = buttons[o][j].getText().toString();
                        if (button.equals( player[currentPlayer].sign )){
                            columnPoints++;
                            if (columnPoints == 3)
                                return true;
                        }
                    }
                    columnPoints = 0;

                    if (i == j) {
                        xPoints++;
                        if (xPoints == 3)
                            return true;
                    }

                    if (j == yDiagonal)   {
                        yDiagonal--; yPoints++;
                        if (yPoints == 3)
                            return true;
                    }
                }
            }
        }
        return false;
    }

    public void endGame(boolean isWinner){

        TextView gameResult = (TextView) findViewById(R.id.gameResult);
        String result;
        if(isWinner) {
            result = "Winner: " + player[currentPlayer].name;
            countMarkedFields = 9;
        }
        else
            result = "Nobody won";
        gameResult.setText(result);
        gameResult.setVisibility(View.VISIBLE);
        TextView str1 = (TextView) findViewById(R.id.textView);
        TextView str2 = (TextView) findViewById(R.id.currentPlayer);
        str1.setVisibility(View.INVISIBLE);
        str2.setVisibility(View.INVISIBLE);
    }

    public void reset(View v){
        Random rand = new Random();
        currentPlayer = rand.nextInt(2);

        TextView str1 = (TextView) findViewById(R.id.textView);
            str1.setVisibility(View.VISIBLE);
        TextView whoIsPlaying = (TextView) findViewById(R.id.currentPlayer);
            whoIsPlaying.setVisibility(View.VISIBLE);
            whoIsPlaying.setText(player[currentPlayer].name);
        TextView gameResult = (TextView) findViewById(R.id.gameResult);
            gameResult.setVisibility(View.INVISIBLE);


        TableLayout table = (TableLayout) findViewById(R.id.tableLayout);
        countMarkedFields = 0;
        for(int j = 0; j < 3; j++)
        {
            TableRow row = (TableRow) table.getChildAt(j);
            for(int i = 0; i < 3; i++)
            {
                buttons[j][i] = (Button) row.getChildAt(i);
                buttons[j][i].setText("");
            }
        }

    }

}