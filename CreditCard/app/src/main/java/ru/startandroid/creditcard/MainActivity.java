package ru.startandroid.creditcard;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.text.method.KeyListener;
import android.view.KeyEvent;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.TextView;
import android.text.Html;
import android.text.Spanned;


import ru.startandroid.creditcard.R;

public class MainActivity extends AppCompatActivity {
    PaymentForm paymentForm;
    Animation shake;

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
        paymentForm = new PaymentForm();
        final EditText eText = (EditText)findViewById(R.id.eText);
        final TextView textView = (TextView)findViewById(R.id.textView);
        shake = AnimationUtils.loadAnimation(this, R.anim.shake);

        eText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() != KeyEvent.ACTION_UP)
                    return true;
                android.util.Log.v("CreditCard", Integer.toString(keyCode));
                String s = "";
                switch (keyCode) {
                    case 7: s = "0"; break;
                    case 8: s = "1"; break;
                    case 9: s = "2"; break;
                    case 10: s = "3"; break;
                    case 11: s = "4"; break;
                    case 12: s = "5"; break;
                    case 13: s = "6"; break;
                    case 14: s = "7"; break;
                    case 15: s = "8"; break;
                    case 16: s = "9"; break;
                }
                try {
                    paymentForm.OnUserInput(s, -1);
                } catch (WrongPaymentException e) {
                    eText.startAnimation(shake);
                } catch (Exception e) {
                }
                EditText eText = (EditText)findViewById(R.id.eText);
                Spanned formattedString = Html.fromHtml(paymentForm.GetText());
                eText.setText(formattedString);

                switch (paymentForm.GetCardIcon()){
                    case visafront:
                        eText.setCompoundDrawablesWithIntrinsicBounds(R.drawable.vis, 0, 0, 0);
                        break;
                    case mastercardfront:
                        eText.setCompoundDrawablesWithIntrinsicBounds(R.drawable.mcard, 0, 0, 0);
                        break;
                    case back:
                        eText.setCompoundDrawablesWithIntrinsicBounds(R.drawable.dol, 0, 0, 0);
                        break;
                    default:
                        eText.setCompoundDrawablesWithIntrinsicBounds(R.drawable.dol, 0, 0, 0);
                }
                return true;
            }
        });

    }
}








