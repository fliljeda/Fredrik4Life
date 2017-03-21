package fliljeda.fredrik4life;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Random;

public class DiceActivity extends AppCompatActivity {
    private int current_roll = 0;
    private int previous_roll = 0;
    Thread roll_thread = null;
    Handler dice_roll_handler = null;
    private boolean roll_active = false;
    private boolean first_roll = true;
    private int size_of_dice = 6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dice);
        roll_thread = new Thread();


        dice_roll_handler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message inputMessage){
                int message_code = inputMessage.what;
                if(message_code == 0) {
                    TextView current_roll_view = (TextView) findViewById(R.id.dice_result_1);
                    current_roll = (int) inputMessage.obj;
                    current_roll_view.setText(Integer.toString(current_roll));
                }else if(message_code == 1){
                    roll_active = false;
                }
            }
        };
    }


    /* Activates from dice button */
    public void rollDice(View view){
        if(!roll_active){
            roll_active = true;
        }else{
            return;
        }

        if(!first_roll) {
            TextView previous_roll_view = (TextView) findViewById(R.id.dice_result_2);
            previous_roll = current_roll;
            previous_roll_view.setText(Integer.toString(previous_roll));
        }else{
            first_roll = false;
        }

        // Thread which sends message of the roll to dice_roll_handler
        // This is so the UI thread doesn't do the waiting and changing
        // views is only possible in the thread creating them (ui thread)
        Thread diceRollThread = new Thread(){
            @Override
            public void run() {
                //ROLLING DICE ANIMATION
                Random rand = new Random();
                int size_of_dice_now = size_of_dice;
                double roll_time_ms = 3000;
                double roll_delay_ms = 100;
                double roll_delay_slowdown_ms = 10; //Note: correlated with n.o. rolls in animation
                double start = System.currentTimeMillis();
                int roll = 0;
                ROLL:
                while(System.currentTimeMillis() - start < roll_time_ms){
                    //Moves more slowly towards the end
                    roll_delay_ms += roll_delay_slowdown_ms;

                    //  Not to show the same number twice
                    // (because that is not how rolling a dice works, clearly)
                    while(roll == (roll = rand.nextInt(size_of_dice_now) + 1)){
                    }

                    Message message = dice_roll_handler.obtainMessage(0, roll);
                    message.sendToTarget();

                    //Handle the delay
                    double delay_start = System.currentTimeMillis();
                    while(System.currentTimeMillis() - delay_start < roll_delay_ms){
                        if(System.currentTimeMillis() - start > roll_time_ms){
                            break ROLL;
                        }
                    }
                }
                //The message which is sent to the handler
                Message message = dice_roll_handler.obtainMessage(0, roll);
                message.sendToTarget();
                message = dice_roll_handler.obtainMessage(1, false);
                message.sendToTarget();
            }
        };

        diceRollThread.start();
    }

   public void increaseDiceSize(View view){
       if(size_of_dice < 25) {
           size_of_dice++;
           TextView tv = (TextView) findViewById(R.id.dice_size);
           tv.setText(Integer.toString(size_of_dice));
       }
   }


    public void decreaseDiceSize(View view){
        if(size_of_dice > 4) {
            size_of_dice--;
            TextView tv = (TextView) findViewById(R.id.dice_size);
            tv.setText(Integer.toString(size_of_dice));
        }
    }


}
