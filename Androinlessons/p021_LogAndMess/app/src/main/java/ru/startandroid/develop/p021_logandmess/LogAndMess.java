package ru.startandroid.develop.p021_logandmess;

import android.nfc.Tag;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class LogAndMess extends AppCompatActivity implements View.OnClickListener {


    TextView tvOut;
    Button btnOk;
    Button btnCancel;
    /** Called when the activity is first created. */
    private static final String TAG = "myLogs";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
// найдем View-элементы
        Log.d(TAG,"найдем View-элементы");
        tvOut = (TextView) findViewById(R.id.tvOut);
        btnOk = (Button) findViewById(R.id.btnOk);
        btnCancel = (Button) findViewById(R.id.btnCancel);
// присваиваем обработчик кнопкам
        Log.d(TAG, "присваиваем обработчик кнопкам");
        btnOk.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
// по id определеяем кнопку, вызвавшую этот обработчик
        Log.d(TAG, "по id определеяем кнопку, вызвавшую этот обработчик");

        switch (v.getId()) {
            case R.id.btnOk:
// кнопка ОК
                Log.d(TAG, "кнопка ОК");
                Toast.makeText(this, "Нажата кнопка ОК", Toast.LENGTH_LONG).show();
                tvOut.setText("Нажата кнопка ОК");
                break;
            case R.id.btnCancel:
// кнопка Cancel
                Log.d(TAG, "кнопка Cancel");
                Toast.makeText(this, "Нажата кнопка Cancel", Toast.LENGTH_LONG).show();
                tvOut.setText("Нажата кнопка Cancel");
                break;
        }
    }
}

