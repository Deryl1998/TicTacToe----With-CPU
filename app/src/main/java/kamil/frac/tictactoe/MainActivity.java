package kamil.frac.tictactoe;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import java.io.Serializable;

public class MainActivity extends AppCompatActivity implements Serializable {

    public class Player implements Serializable  {
        public String name;
        public String sign;
        Player(String name,String sign){
            this.name = name;
            this.sign=sign;
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
    private static final String TAG = "MyActivity";
    public void startGame(View view) {
        String player2Sign,player1Sign = ((Spinner) findViewById(R.id.spinner1)).getSelectedItem().toString();
        String player1Name = ((EditText) findViewById(R.id.playerInput1)).getText().toString();
        String player2Name = ((EditText) findViewById(R.id.playerInput2)).getText().toString();
        if(player1Sign.equals("O")) player2Sign = "X";
        else player2Sign = "O";
        Player player1 = new Player(player1Name,player1Sign);
        Player player2 = new Player(player2Name,player2Sign);
        Intent myIntent = new Intent(this , GameActivity.class);
        Bundle extras = new Bundle();
        extras.putSerializable("player1", player1);
        extras.putSerializable("player2", player2);
        myIntent.putExtras(extras);
        startActivity(myIntent);
    }

}