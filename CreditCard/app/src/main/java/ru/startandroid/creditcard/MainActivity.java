package ru.startandroid.creditcard;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
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
import de.tavendo.autobahn.WebSocketConnection;
import de.tavendo.autobahn.WebSocketException;
import de.tavendo.autobahn.WebSocketHandler;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;

import java.io.IOException;

import ru.startandroid.creditcard.R;
class SrvResponse {
    public int status;
    public String reason;
}
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
                if ( event.getAction() != KeyEvent.ACTION_UP && keyCode != KeyEvent.KEYCODE_DEL) {
                    return true;
                } else if(event.getUnicodeChar() ==
                        (int)EditableAccomodatingLatinIMETypeNullIssues.ONE_UNPROCESSED_CHARACTER.charAt(0))
                {
                    //We are ignoring this character, and we want everyone else to ignore it, too, so
                    // we return true indicating that we have handled it (by ignoring it).
                    return true;
                }
                android.util.Log.v("CreditCard", Integer.toString(keyCode) + " " + Integer.toString(event.getAction()));
                if((KeyEvent.KEYCODE_0 <= keyCode && keyCode <= KeyEvent.KEYCODE_9) || keyCode == KeyEvent.KEYCODE_DEL) {
                    OnKeyPress(keyCode);
                }
                return true;
            }
        });
        UpdatePaymentView();
        UpdateButtons();
    }
    private void OnKeyPress(int keyCode) {
        EditText eText = (EditText)findViewById(R.id.eText);
        try {
            switch (keyCode) {
                case 7:
                    paymentForm.OnUserInput("0", -1);
                    break;
                case 8:
                    paymentForm.OnUserInput("1", -1);
                    break;
                case 9:
                    paymentForm.OnUserInput("2", -1);
                    break;
                case 10:
                    paymentForm.OnUserInput("3", -1);
                    break;
                case 11:
                    paymentForm.OnUserInput("4", -1);
                    break;
                case 12:
                    paymentForm.OnUserInput("5", -1);
                    break;
                case 13:
                    paymentForm.OnUserInput("6", -1);
                    break;
                case 14:
                    paymentForm.OnUserInput("7", -1);
                    break;
                case 15:
                    paymentForm.OnUserInput("8", -1);
                    break;
                case 16:
                    paymentForm.OnUserInput("9", -1);
                    break;
                case KeyEvent.KEYCODE_DEL:
                    paymentForm.OnDelete(-1);
                    break;
            }
        } catch (WrongPaymentException e) {
            eText.startAnimation(shake);
        } catch (Exception e) {
        }
        UpdatePaymentView();
        UpdateButtons();
    }
    private void UpdatePaymentView(){
        CreditEditText eText = (CreditEditText)findViewById(R.id.eText);
        Spanned formattedString = Html.fromHtml(paymentForm.GetText());
        eText.setText(formattedString);
        eText.setFixedSelection(Math.min(paymentForm.GetCursorPos(), eText.getText().toString().length()));

        eText.setCompoundDrawablePadding(20);
        SetCardIcon(paymentForm.GetCardIcon());
    }
    private void SetCardIcon(CardIcon cardIcon) {
        CreditEditText eText = (CreditEditText)findViewById(R.id.eText);
        switch (cardIcon){
            case visafront:
                eText.setCompoundDrawablesWithIntrinsicBounds(R.drawable.visa, 0, 0, 0);
                break;
            case mastercardfront:
                eText.setCompoundDrawablesWithIntrinsicBounds(R.drawable.mcard, 0, 0, 0);
                break;
            case back:
                eText.setCompoundDrawablesWithIntrinsicBounds(R.drawable.card_back, 0, 0, 0);
                break;
            default:
                eText.setCompoundDrawablesWithIntrinsicBounds(R.drawable.unknown, 0, 0, 0);
        }
        eText.setContentDescription(cardIcon.toString());
    }
    private void UpdateButtons()
    {
        Button btnPrev = (Button)findViewById(R.id.btnPrev);
        Button btnNext = (Button)findViewById(R.id.btnNext);
        Button btnDone = (Button)findViewById(R.id.btnDone);
        if (paymentForm.GetPayment().GetConfirmed()) {
            btnPrev.setEnabled(false);
            btnNext.setEnabled(false);
            btnDone.setEnabled(false);
        }
        else {
            btnPrev.setEnabled(paymentForm.GetPrevEnabled());
            btnNext.setEnabled(paymentForm.GetNextEnabled());
            btnDone.setEnabled(paymentForm.GetPayment().Valid());
        }

    }

    public void onClickDone(View view) {
        Button btnPrev = (Button)findViewById(R.id.btnPrev);
        Button btnNext = (Button)findViewById(R.id.btnNext);
        Button btnDone = (Button)findViewById(R.id.btnDone);
        btnPrev.setEnabled(false);
        btnNext.setEnabled(false);
        btnDone.setEnabled(false);
        btnDone.setText("Processing...");
        final String TAG = "de.tavendo.test1";
        final WebSocketConnection mConnection = new WebSocketConnection();
        final String wsuri = "ws://farm:8888/websocket";

        try {
            mConnection.connect(wsuri, new WebSocketHandler() {

                @Override
                public void onOpen() {
                    Log.d(TAG, "Status: Connected to " + wsuri);
                    try {
                        String request = paymentForm.GetPaymentRequest();
                        Log.d(TAG, "sending request " + request);
                        mConnection.sendTextMessage(request);
                    } catch (IOException e) {

                    }
                }

                @Override
                public void onTextMessage(String payload) {
                    ObjectMapper mapper = new ObjectMapper();
                    Log.d(TAG, "Got echo: " + payload);
                    Button btnDone = (Button)findViewById(R.id.btnDone);
                    CreditEditText eText = (CreditEditText)findViewById(R.id.eText);
                    try {
                        SrvResponse srvResponse = mapper.readValue(payload, SrvResponse.class);
                        if (srvResponse.status >= 200 && srvResponse.status < 300) {
                            btnDone.setText("Confirmed");
                            eText.setEnabled(false);
                            paymentForm.GetPayment().Confirm();
                        }
                        else {
                            String caption = "";
                            if (srvResponse.status >= 300 && srvResponse.status < 500) {
                                caption = "Application error";
                            }
                            if (srvResponse.status >= 500){
                                caption = "Server error";
                            }
                            ShowError(caption, "Server responded with error " + Integer.toString(srvResponse.status) + " " + srvResponse.reason);
                            btnDone.setText("Retry");
                        }
                    } catch (JsonParseException e) {
                        ShowError("Server error","Server responded with wrong answer. Press retry to send request again");
                        UpdateButtons();
                        btnDone.setText("Retry");
                    } catch (JsonMappingException e)  {
                        ShowError("Server error","Server responded with wrong answer. Press retry to send request again");
                        UpdateButtons();
                        btnDone.setText("Retry");
                    } catch (IOException e) {
                        ShowError("Server error","Server responded with wrong answer. Press retry to send request again");
                        UpdateButtons();
                        btnDone.setText("Retry");
                    }
                    UpdateButtons();
                }

                @Override
                public void onClose(int code, String reason) {
                    Log.d(TAG, "Connection lost.");
                    if ( ! paymentForm.GetPayment().GetConfirmed()) {
                        Button btnDone = (Button) findViewById(R.id.btnDone);
                        UpdateButtons();
                        ShowError("Server unavailable", "Couldn't establish connection with server. Check your network connections and retry");
                        btnDone.setText("Retry");
                    }
                }
            });
        } catch (WebSocketException e) {

            Log.d(TAG, e.toString());
        }
    }
    private void ShowError (String caption, String message){
        new AlertDialog.Builder(this)
                .setTitle(caption)
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
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








