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
import android.widget.Button;
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

        paymentForm = new PaymentForm();
        final EditText eText = (EditText)findViewById(R.id.eText);
        Button btnPrev = (Button)findViewById(R.id.btnPrev);
        Button btnNext = (Button)findViewById(R.id.btnNext);
        Button btnDone = (Button)findViewById(R.id.btnDone);
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
                int currentCursorPos = eText.getSelectionStart();

                try {
                    paymentForm.OnUserInput(s, -1);
                } catch (WrongPaymentException e) {
                    eText.startAnimation(shake);
                } catch (Exception e) {
                }
                UpdatePaymentView();
                UpdateButtons();
                return true;
            }
        });
        UpdateButtons();
    }
    private  void UpdatePaymentView(){
        EditText eText = (EditText)findViewById(R.id.eText);
        Spanned formattedString = Html.fromHtml(paymentForm.GetText());
        eText.setText(formattedString);
        eText.setSelection(paymentForm.GetCursorPos());

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
    }
    private  void UpdateButtons()
    {
        Button btnPrev = (Button)findViewById(R.id.btnPrev);
        Button btnNext = (Button)findViewById(R.id.btnNext);
        Button btnDone = (Button)findViewById(R.id.btnDone);
        btnPrev.setEnabled(paymentForm.GetPrevEnabled());
        btnNext.setEnabled(paymentForm.GetNextEnabled());
        btnDone.setEnabled(paymentForm.GetPayment().Valid());
    }

    public void onClickDone(View view) {
    }

    public void onClickNext(View view) {
        paymentForm.Next();
        UpdatePaymentView();
        UpdateButtons();
    }

    public void onClickPrev(View view) {
        paymentForm.Prev();
        UpdatePaymentView();
        UpdateButtons();
    }
}








