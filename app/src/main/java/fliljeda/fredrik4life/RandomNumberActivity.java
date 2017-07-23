package fliljeda.fredrik4life;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Random;

public class RandomNumberActivity extends AppCompatActivity {

    Random random;
    EditText input_from, input_to;
    TextView output_target;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random_number);
        random = new Random();
        input_from = (EditText) findViewById(R.id.random_number_from);
        input_to = (EditText) findViewById(R.id.random_number_to);
        output_target = (TextView) findViewById((R.id.random_number_target));
    }

    public void generateRandomNumber(View view){
        //TODO make long
        int low,high;
        try {
            low = Integer.parseInt(input_from.getText().toString());
            high = Integer.parseInt(input_to.getText().toString());
        }catch (NumberFormatException e){
            output_target.setText("");
            sendAlert("Format error", "Could not interpret input as 32 bit integer");
            //Feedback: too big
            return;
        }

        output_target.setText("" + getRandomNumber(low,high));
        if(high < low){
            input_to.setText("" + (low+1));
        }
    }

    /**
     * Follows specific rules, if low > high then high gets set to low+1
     */
    public static int getRandomNumber(int low, int high){
        if(high < low){
            high = low+1;
        }
        boolean negative = true;
        int shift = 0;
        if(negative = low < 0){
            shift = low;
            low -= low;
            high -= low;
        }
        Random random = new Random();

        int r = random.nextInt(high-low + 1) + low;
        if(negative){
            r -= shift;
        }
        return r;
    }


    private void sendAlert(String title, String message){
        AlertDialog alertDialog = new AlertDialog.Builder(RandomNumberActivity.this).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();}
}
