package fliljeda.fredrik4life;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

public class NumberConverterActivity extends AppCompatActivity {
    private EditText textField;
    private TextView hexValue;
    private TextView decValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number_converter);
        textField = (EditText) findViewById(R.id.numberconverter_change);
        hexValue = (TextView) findViewById(R.id.numberconverter_display_hex);
        decValue = (TextView) findViewById(R.id.numberconverter_display_dec);
        addResponsiveness();

    }


    /* Adding the necessary responsiveness to the layout */
    protected void addResponsiveness(){

        textField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tryConvertToHex(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    /* Tries to convert to hex from decimal*/
    //TODO HANDLE OVERFLOW
    private void tryConvertToHex(CharSequence field){
        int fieldsize = field.length();
        for(int i = 0; i < fieldsize; i++){
            if(field.charAt(i) < '0' || field.charAt(i) > '9'){
                //does not match
                return;
            }
        }
        String hex = Long.toHexString(Long.parseLong(field.toString()));
        hexValue.setText(hex);
    }

    /* Tries to convert to dec from hex*/
    private void tryConvertToDec(){

    }
}
