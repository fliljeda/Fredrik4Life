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
                tryConvertToDec(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    /* Tries to convert to hex from decimal*/
    private void tryConvertToHex(CharSequence field){
        int fieldsize;

        //Empty field
        if((fieldsize = field.length()) == 0){
            hexValue.setText("");
            return;
        }

        //Check if all digits
        for(int i = 0; i < fieldsize; i++){
            if(field.charAt(i) < '0' || field.charAt(i) > '9'){
                //does not match
                hexValue.setText("Unable to convert");
                return;
            }
        }
        String hex;
        //If too big to fit in long
        //TODO ANY SIZE CONVERTER (maybe not needed)
        try {
            hex = Long.toHexString(Long.parseLong(field.toString()));
        }catch (NumberFormatException e){
            hexValue.setText("Too big");
            return;
        }
        hexValue.setText(hex);
    }

    /* Tries to convert to dec from hex*/
    private void tryConvertToDec(CharSequence field){
        int fieldsize = field.length();

        //Empty field
        if(fieldsize == 0){
            decValue.setText("");
            return;
        }else if(fieldsize > 15){ //needs to fit into a signed long TODO UNSIGNED
            decValue.setText("Too big");
            return;
        }

        long value = 0;

        //Check if all digits
        for(int i = 0; i < fieldsize; i++){
            int charValue = getDecValueFromHex(field.charAt(i));
            if(charValue == -1){
                decValue.setText("Unable to convert");
            }
            value |= ((long)charValue << (fieldsize-i-1)*4); //Shifting hexvalues into place
        }
        String hex = "" + value;
        decValue.setText(hex);
    }

    /* If character is a hexadecimal numeral*/
    private int getDecValueFromHex(char c){
        if(c >= '0' && c <= '9'){
            return c%48; // '0' = 48
        }else if (c >= 'A' && c <= 'F'){
            return c%55; // 'A' = 65 --> 10
        }else if(c >= 'a' && c <= 'f'){
            return c%87; // 'a' = 97 --> 10
        }else{
            return -1;
        }

    }
}
