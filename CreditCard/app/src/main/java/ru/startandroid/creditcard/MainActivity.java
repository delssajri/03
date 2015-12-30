package ru.startandroid.creditcard;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import ru.startandroid.creditcard.R;

public class MainActivity extends AppCompatActivity {



    //public int[] masFirst = {0, 2, 5, 7, 10, 12};
    //public int[] masSecond = {1, 3, 6, 8, 11, 13};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        //final EditText eText = (EditText)findViewById(R.id.eText);
        final EditText eText = (EditText)findViewById(R.id.eText);
        final TextView textView = (TextView)findViewById(R.id.textView);

        eText.addTextChangedListener(new TextWatcher() {
                                        @Override
                                         public void afterTextChanged(Editable s) {
                                             // Прописываем то, что надо выполнить после изменения текста
                                            Editable txt = eText.getText();
                                            String txtt = txt.toString();
                                            char myChar = txtt.charAt(0);
                                            int len = txtt.length();


                                            if (len >=  1) {

                                                if (myChar == '5')
                                                eText.setCompoundDrawablesWithIntrinsicBounds(R.drawable.vis, 0, 0, 0);
                                                else if (myChar == '4')
                                                    eText.setCompoundDrawablesWithIntrinsicBounds(R.drawable.mcard, 0, 0, 0);
                                                else
                                                    eText.setCompoundDrawablesWithIntrinsicBounds(R.drawable.dol, 0, 0, 0);
                                            }
                                            else
                                                eText.setCompoundDrawablesWithIntrinsicBounds(R.drawable.dol, 0, 0, 0);


                                            if (len == 4 || len == 9 || len == 14) {

                                                 eText.setText(eText.getText() + " ");
                                                 eText.setSelection(eText.getText().length());

                                             }
                                         }
                                         @Override
                                         public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                         }

                                         @Override
                                         public void onTextChanged(CharSequence s, int start, int before, int count) {
                                             Editable txt = eText.getText();
                                             String txtt = txt.toString();
                                             int[] sum = new int[8];
                                             int[] masFirst = {0, 2, 5, 7, 10, 12, 15, 17};
                                             int[] masSecond = {1, 3, 6, 8, 11, 13, 16, 18};
                                             int sumEnd = 0;

                                             if (txtt.length() == 19) {
                                                 for (int i = 0; i < 8; i++) {

                                                     sum[i] = (txtt.charAt(masFirst[i])-'0') * 2;
                                                     if (sum[i] > 9) {
                                                         int sumCharfirsr = sum[i] / 10;
                                                         int sumCharsecond = sum[i] % 10;
                                                         sum[i] = sumCharfirsr + sumCharsecond;
                                                     }
                                                 }
                                                 for (int i = 0; i < 8; i++) {
                                                     sumEnd = sumEnd + sum[i] + (txtt.charAt(masSecond[i])-'0');
                                                 }
                                                 if (sumEnd % 10 == 0) {
                                                     textView.setText("карта верна");
                                                 }
                                                 else
                                                 {
                                                     textView.setText("карта неверна");
                                                 }

                                             }


                                         }
                                     }
        );
    }
}








