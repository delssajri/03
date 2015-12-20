package ru.startandroid.develop.p0091_onclickbuttons;

import android.support.v7.app.AppCompatActivity;

        TextView tvOut;
        Button btnOk;
        Button btnCancel;

/** Called when the activity is first created. */
@Override
import android.net.Uri;
        import android.os.Bundle;
        import android.support.design.widget.FloatingActionButton;
        import android.support.design.widget.Snackbar;
        import android.support.v7.app.AppCompatActivity;
        import android.support.v7.widget.Toolbar;
        import android.view.View;
        import android.view.Menu;
        import android.view.MenuItem;
        import android.widget.Button;
        import android.widget.TextView;

        import com.google.android.gms.appindexing.Action;
        import com.google.android.gms.appindexing.AppIndex;
        import com.google.android.gms.common.api.GoogleApiClient;

public class MainActivity extends AppCompatActivity {
public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        // найдем View-элементы
        tvOut = (TextView) findViewById(R.id.tvOut);
        btnOk = (Button) findViewById(R.id.btnOk);
        btnCancel = (Button) findViewById(R.id.btnCancel);

        // создание обработчика
        OnClickListener oclBtn = new OnClickListener() {
@Override
public void onClick(View v) {
        // TODO Auto-generated method stub

        }
        };

        }
        }
