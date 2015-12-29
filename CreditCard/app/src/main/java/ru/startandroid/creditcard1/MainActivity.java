package ru.startandroid.creditcard1;

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

import ru.startandroid.creditcard1.R;

public class MainActivity extends AppCompatActivity {

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
        final EditText eText = (EditText)findViewById(R.id.eText);
        eText.addTextChangedListener(new TextWatcher() {
                                        @Override
                                         public void afterTextChanged(Editable s) {
                                             // Прописываем то, что надо выполнить после изменения текста
                                            Editable txt = eText.getText();
                                            String txtt = txt.toString();
                                            //char myChar = txtt.charAt(0);


                                            if (txtt.length() >=  1) {
                                                char myChar = txtt.charAt(0);
                                                if (myChar == '5')
                                                eText.setCompoundDrawablesWithIntrinsicBounds(R.drawable.vis, 0, 0, 0);
                                                else if (myChar == '4')
                                                    eText.setCompoundDrawablesWithIntrinsicBounds(R.drawable.mcard, 0, 0, 0);
                                                else
                                                    eText.setCompoundDrawablesWithIntrinsicBounds(R.drawable.dol, 0, 0, 0);
                                            }
                                            else
                                                eText.setCompoundDrawablesWithIntrinsicBounds(R.drawable.dol, 0, 0, 0);


                                            if (txtt.length() == 4 || txtt.length() == 9 || txtt.length() == 14) {

                                                 eText.setText(eText.getText() + " ");
                                                 eText.setSelection(eText.getText().length());

                                             }
                                         }
                                         @Override
                                         public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                         }

                                         @Override
                                         public void onTextChanged(CharSequence s, int start, int before, int count) {
                                         }


                                     }
        );
    }
}








