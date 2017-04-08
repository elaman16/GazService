package kz.elaman.gazservice;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.shawnlin.numberpicker.NumberPicker;

import java.util.Calendar;

import kz.elaman.gazservice.utils.Constants;
import kz.elaman.gazservice.utils.PrefHelper;

public class SendIndicateActivity extends AppCompatActivity implements View.OnClickListener {


    private NumberPicker np1, np2, np3, np4, np5;
    private DatePicker datePicker;
    private Calendar calendar;
    private TextView dateView;
    private int year, month, day;

    private PrefHelper prefHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_indicate);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("Передача показания");

        prefHelper = new PrefHelper(this);


        np1 = (NumberPicker) findViewById(R.id.number_picker);
        np2 = (NumberPicker) findViewById(R.id.number_picker1);
        np3 = (NumberPicker) findViewById(R.id.number_picker2);
        np4 = (NumberPicker) findViewById(R.id.number_picker3);
        np5 = (NumberPicker) findViewById(R.id.number_picker4);

        dateView = (TextView) findViewById(R.id.date_view);
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);

        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        showDate(year, month + 1, day);


        findViewById(R.id.btn_send_counter).setOnClickListener(this);
        findViewById(R.id.btn_choose_date).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_send_counter: {
                uploadIndicateReading();
            }
            break;

            case R.id.btn_choose_date: {
                showDialog(999);
                Toast.makeText(getApplicationContext(), "ca",
                        Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {
            DatePickerDialog datePicker =
                    new DatePickerDialog(this,
                            myDateListener, year, month, day);
            datePicker.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
            return datePicker;
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker arg0,
                                      int arg1, int arg2, int arg3) {
                    // TODO Auto-generated method stub
                    // arg1 = year
                    // arg2 = month
                    // arg3 = day
                    showDate(arg1, arg2 + 1, arg3);
                }
            };

    private void showDate(int year, int month, int day) {
        dateView.setText(new StringBuilder().append(day).append("/")
                .append(month).append("/").append(year));
    }


    private void uploadIndicateReading() {
        String indicateValue = String.valueOf(np1.getValue()) + String.valueOf(np2.getValue()) + String.valueOf(np3.getValue()) +
                String.valueOf(np4.getValue()) + String.valueOf(np5.getValue());

        Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
        emailIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        emailIntent.setType("vnd.android.cursor.item/email");
        emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{Constants.MY_EMAIL});
        emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Показания счетчика");
        emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Ф.И.О: " + prefHelper.getUserName() +
                "\n Адресс " + prefHelper.getAddress() +
                "\n Дата " + dateView.getText() +
                "\n Показание счетчика: " + indicateValue +
                "\n ");
        startActivity(Intent.createChooser(emailIntent, "Send mail using..."));
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
