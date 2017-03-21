package fliljeda.fredrik4life;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainMenuActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
    }


    public void startDiceActivity(View view){
        Intent intent = new Intent(this, DiceActivity.class);
        startActivity(intent);
    }
}
