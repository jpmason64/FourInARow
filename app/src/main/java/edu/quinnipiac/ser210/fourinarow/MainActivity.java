package edu.quinnipiac.ser210.fourinarow;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onPlayClick(View view){
        Intent intent = new Intent(this, GameActivity.class);
        EditText player = (EditText) findViewById(R.id.player_name);
        intent.putExtra("PLAYER_NAME", player.getText().toString());
        startActivity(intent);
    }
}