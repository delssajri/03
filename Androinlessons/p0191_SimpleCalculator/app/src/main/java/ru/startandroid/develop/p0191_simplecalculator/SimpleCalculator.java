package ru.startandroid.develop.p0191_simplecalculator;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SimpleCalculator extends AppCompatActivity implements View.OnClickListener {
    EditText etNum1;
    EditText etNum2;
    Button btnAdd;
    Button btnSub;
    Button btnMult;
    Button btnDiv;
    TextView tvResult;
    String oper = "";
    final int MENU_RESET_ID = 1;
    final int MENU_QUIT_ID = 2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        etNum1 = (EditText) findViewById(R.id.etNum1);
        etNum2 = (EditText) findViewById(R.id.etNum2);
        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnSub = (Button) findViewById(R.id.btnSub);
        btnMult = (Button) findViewById(R.id.btnMult);
        btnDiv = (Button) findViewById(R.id.btnDiv);
        tvResult = (TextView) findViewById(R.id.tvResult);
        btnAdd.setOnClickListener(this);
        btnSub.setOnClickListener(this);
        btnMult.setOnClickListener(this);
        btnDiv.setOnClickListener(this);
    }

    @Override

    public boolean onCreateOptionsMenu(Menu menu) {
// TODO Auto-generated method stub
        menu.add(0, MENU_RESET_ID, 0, "Reset");
        menu.add(0, MENU_QUIT_ID, 0, "Quit");
        return super.onCreateOptionsMenu(menu);
    }
    // обработка нажатий на пункты меню

// TODO Auto-generated method stub
@Override
 public boolean onOptionsItemSelected(MenuItem item) {

    switch (item.getItemId()) {
            case MENU_RESET_ID:
// очищаем поля
                etNum1.setText("");
                etNum2.setText("");
                tvResult.setText("");
                break;
            case MENU_QUIT_ID:
// выход из приложения
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }




    @Override
    public void onClick(View v) {
// TODO Auto-generated method stub
            float num1 = 0;
            float num2 = 0;
            float result = 0;
// Проверяем поля на пустоту
            if (TextUtils.isEmpty(etNum1.getText().toString())
                    || TextUtils.isEmpty(etNum2.getText().toString())) {
                return;
            }
// читаем EditText и заполняем переменные числами
            num1 = Float.parseFloat(etNum1.getText().toString());
            num2 = Float.parseFloat(etNum2.getText().toString());
// определяем нажатую кнопку и выполняем соответствующую операцию
// в oper пишем операцию, потом будем использовать в выводе
            switch (v.getId()) {
                case R.id.btnAdd:
                    oper = "+";
                    result = num1 + num2;
                    break;
                case R.id.btnSub:
                    oper = "-";
                    result = num1 - num2;
                    break;
                case R.id.btnMult:
                    oper = "*";
                    result = num1 * num2;
                    break;
                case R.id.btnDiv:
                    oper = "/";
                    result = num1 / num2;
                    break;
                default:
                    break;
            }
// формируем строку вывода
        tvResult.setText(num1 + " " + oper + " " + num2 + " = " + result);

    }
}