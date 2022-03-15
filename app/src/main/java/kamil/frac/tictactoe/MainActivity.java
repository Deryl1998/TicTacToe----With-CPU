package kamil.frac.tictactoe;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import java.io.Serializable;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements Serializable {

    public class Player implements Serializable  {
        public String name;
        public String sign;
        public boolean clickedButton = false;
        public boolean isCPU;
        Player(String name,String sign ,boolean isCPU){
            this.name = name;
            this.sign = sign;
            this.isCPU = isCPU;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Spinner spinner1 = (Spinner) findViewById(R.id.spinner1);
        ArrayAdapter<CharSequence> staticAdapter = ArrayAdapter.createFromResource(this, R.array.playerChoose,
                android.R.layout.simple_spinner_item);
        staticAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(staticAdapter);

    }

    public void startGame(View view) {
        Player player1 , player2;

        Random rand = new Random();
        int player2Sign,player1Sign = rand.nextInt(2);
        player2Sign =  player1Sign == 1? 0:1;
        String select = ((Spinner) findViewById(R.id.spinner1)).getSelectedItem().toString();
        String[] sing = new String[]{"O","X"};
        if(select.equals("CPU")) player2 = new Player("CPU",sing[player2Sign],true);
        else player2 = new Player("Player_2",sing[player2Sign],false);

        player1 = new Player("Player_1",sing[player1Sign],false);

        Intent myIntent = new Intent(this , GameActivity.class);
        Bundle extras = new Bundle();
        extras.putSerializable("player1", player1);
        extras.putSerializable("player2", player2);
        myIntent.putExtras(extras);
        startActivity(myIntent);
    }

}