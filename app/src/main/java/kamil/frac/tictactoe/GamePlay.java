package kamil.frac.tictactoe;

import android.util.Log;
import android.widget.Button;
import java.util.Random;

public class GamePlay {

    MainActivity.Player[] player;
    private final Button[][] buttons;
    private final GameActivity gameActivity;
    int currentPlayer , countMarkedFields;

    public final Thread gamePlayThread = new Thread(
            new Runnable(){
                @Override
                public void run() {
                    gameActivity.changePlayer();
                    while (!Thread.interrupted())
                        try {
                            Thread.sleep(100);
                            if (player[currentPlayer].clickedButton || player[currentPlayer].isCPU) {
                                player[currentPlayer].clickedButton = false;
                                if (player[currentPlayer].isCPU) cpuMove();
                                if (countMarkedFields == 9 || checkWin()) {
                                    gameActivity.endGame(countMarkedFields == 9 && !checkWin());
                                    return;
                                }
                                gameActivity.changePlayer();
                            }
                        }
                        catch (InterruptedException e) {
                            e.printStackTrace();
                            gamePlayThread.interrupt();
                        }
                }
            });

    GamePlay(Button [][] buttons ,MainActivity.Player[] player, GameActivity gameActivity){
        this.gameActivity = gameActivity;
        this.player = player;
        this.buttons = buttons;
        Random rand = new Random();
        currentPlayer = rand.nextInt(2);
        countMarkedFields = 0;
    }

    public void startGamePlayThread(){
        gamePlayThread.start();
    }

    public void stopGamePlayThread(){
        if(gamePlayThread.isAlive()) gamePlayThread.interrupt();
    }

    public  MainActivity.Player getCurrentPlayer(){
        return player[currentPlayer];
    }

    public  MainActivity.Player getNextPlayer(){
        int nextPlayer = currentPlayer == 0? 1:0;
        return player[nextPlayer];
    }

    private void cpuMove(){
        try {
            Thread.sleep(500);
            CPU();
            countMarkedFields++;
        }catch (InterruptedException e){
            e.printStackTrace();
            gamePlayThread.interrupt();
        }
    }

    private Button[] getColumn(int index){
        Button[] column = new Button[3];
        for(int i=0; i<3; i++){
            column[i] = buttons[i][index];
        }
        return column;
    }

    // return  player points on row
    private int checkRowPoints(Button [] row , String playerSign){
        int points = 0;
        for(int i = 0; i < 3; i++){
            String sign = row[i].getText().toString();
            if(sign.equals(playerSign)) points++;
        }
        return points;
    }

    private int checkDiagonalPoints(boolean isDiagonalPositive , String playerSign){
        int points = 0 , j = 3;
        for(int i = 0; i < 3; i++){
            if(isDiagonalPositive) j = i;
            else j--;
            String sign = buttons[i][j].getText().toString();
            if(sign.equals(playerSign)) points++;
        }
        return points;
    }

    private boolean checkWin() {
        String playerSign = getCurrentPlayer().sign;
        for(int i=0; i < 3; i++){
            if(checkRowPoints(buttons[i],playerSign) == 3) return true;
            if(checkRowPoints(getColumn(i),playerSign) == 3) return true;
        }
        if(checkDiagonalPoints(true,playerSign) == 3 ) return true;
        if(checkDiagonalPoints(false,playerSign) == 3 ) return true;

        return false;
    }

    private boolean setMoveCPUonRow(Button[] row){
        for(int i=0; i<3 ; i++){
            String buttonSign = row[i].getText().toString();
            if(buttonSign.equals("")) {
                row[i].setText(getCurrentPlayer().sign);
                return true;
            }
        }
        return false;
    }
    private boolean setMoveCPUonDiagonal(boolean isDiagonalPositive){
        int j = 3;
        for(int i = 0; i < 3; i++){
            if(isDiagonalPositive) j = i;
            else j--;
            String buttonSign = buttons[i][j].getText().toString();
            if(buttonSign.equals("")) {
                buttons[i][j].setText(getCurrentPlayer().sign);
                return true;
            }
        }
        return false;
    }

    private void setRandomMoveCPU(){
        int i; int j;
        Random rand = new Random();
        while(true)
        {
            i = rand.nextInt(3);
            j = rand.nextInt(3);
            String buttonSign = buttons[i][j].getText().toString();
            if (buttonSign.equals("")) {
                buttons[i][j].setText(getCurrentPlayer().sign);
                return;
            }
        }
    }

    private void CPU(){
        // first time loop check if can win , second time loop check if enemy can win
        String[] playersSign = new String[]{getCurrentPlayer().sign, getNextPlayer().sign};
        for(String playerSign:playersSign) {
            for (int i = 0; i < 3; i++) {
                if (checkRowPoints(buttons[i],playerSign) == 2) {
                    if (setMoveCPUonRow(buttons[i])) return;
                }
                if (checkRowPoints(getColumn(i),playerSign) == 2) {
                    if (setMoveCPUonRow(getColumn(i))) return;
                }
            }
            if (checkDiagonalPoints(true,playerSign) == 2) {
                if (setMoveCPUonDiagonal(true)) return;
            }
            if (checkDiagonalPoints(false,playerSign) == 2) {
                if (setMoveCPUonDiagonal(false)) return;
            }
        }
        setRandomMoveCPU();
    }

}
