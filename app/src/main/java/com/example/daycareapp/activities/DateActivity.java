package com.example.daycareapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.daycareapp.R;
import com.example.daycareapp.network.RetrofitAPIClient;
import com.example.daycareapp.models.Order;
import com.example.daycareapp.network.response.CreateOrderResponse;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DateActivity extends AppCompatActivity {

    Button paybt, startDateBt, endDateBt;
    TextView startDateTv, endDateTv, calculatedAmountTv;
    DatePickerDialog datePickerDialog;
    TimePickerDialog timePickerDialog;

    Calendar startDateCalender = Calendar.getInstance();
    Calendar endDateCalender = Calendar.getInstance();
    String startDate, startTime;
    String endDate, endTime;
    int startYear, startMonthOfYear, startDateOfMonth, startHourOfDay, startMinute;
    int endYear, endMonthOfYear, endDateOfMonth, endHourOfDay, endMinute;
    Long caregiverId, babyId;
    Long amount = 0L;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date);

        caregiverId = this.getIntent().getLongExtra("caregiver_id", 1);
        babyId = this.getIntent().getLongExtra("baby_id", 1);
        paybt = findViewById(R.id.paybtid);
        startDateTv = findViewById(R.id.startDateTvId);
        endDateTv = findViewById(R.id.endDateTvId);
        startDateBt = findViewById(R.id.startDateBtId);
        endDateBt = findViewById(R.id.endDateBtId);
        calculatedAmountTv = findViewById(R.id.calculatedAmountTvId);

        paybt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createOrder();
                Intent intent = new Intent(getApplicationContext(), PaymentActivity.class);
                finish();
                startActivity(intent);
            }
        });

        startDateBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                int mHour = c.get(Calendar.HOUR_OF_DAY);
                int mMinute = c.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog = new TimePickerDialog(DateActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                startTime = hourOfDay + ":" + minute;
                                startDateTv.setText("Start Date: " + startDate + " " + startTime);
                                startMinute = minute;
                                startHourOfDay = hourOfDay;
                                calculate();
                                startDateCalender.set(startYear, startMonthOfYear, startDateOfMonth, startHourOfDay, startMinute);

                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();

                startDateTv.setText("Start Date: " + startDate + " " + startTime);


                // calender class's instance and get current date , month and year from calender
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day

                // date picker dialog
                 datePickerDialog = new DatePickerDialog(DateActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                monthOfYear = monthOfYear + 1;
                                startDate = (dayOfMonth + "/"
                                        + monthOfYear + "/" + year);
                                startDateTv.setText("Start Date: " + startDate + " " + startTime);
                                startDateOfMonth = dayOfMonth;
                                startMonthOfYear = monthOfYear;
                                startYear = year;
                                startDateCalender.set(year, monthOfYear, dayOfMonth, startHourOfDay, startMinute);
                                calculate();
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();

            }
        });

        endDateBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //  final Calendar c = Calendar.getInstance();
                final Calendar c = Calendar.getInstance();
                int mHour = c.get(Calendar.HOUR_OF_DAY);
                int mMinute = c.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(DateActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {

                                endTime = hourOfDay + ":" + minute;
                                endMinute = minute;
                                endHourOfDay = hourOfDay;
                                endDateTv.setText("End Date: " + endDate + " " + endTime);
                                endDateCalender.set(endYear, endMonthOfYear, endDateOfMonth, endHourOfDay, endMinute);
                                calculate();

                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();

                endDateTv.setText("End Date: " + endDate + " " + endTime);

                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                datePickerDialog = new DatePickerDialog(DateActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                monthOfYear = monthOfYear + 1;
                                endDateOfMonth = dayOfMonth;
                                endMonthOfYear = monthOfYear;
                                endYear = year;
                                endDate = (dayOfMonth + "/"
                                        + monthOfYear + "/" + year);
                                endDateCalender.set(year, monthOfYear, dayOfMonth, endHourOfDay, endMinute);
                                endDateTv.setText("End Date: " + endDate + " " + endTime);
                                calculate();
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
    }

    private void calculate() {
        long numberOfMiliseconds = endDateCalender.getTimeInMillis() - startDateCalender.getTimeInMillis();
        long numberOfHours = numberOfMiliseconds / (1000*60*60);
        calculatedAmountTv.setVisibility(View.VISIBLE);
        if(numberOfHours>0){

            amount = numberOfHours * 200;
            calculatedAmountTv.setText("Total hours: "+ numberOfHours + ", and " +"Price: " + numberOfHours*200 + "tk (BDT)");
            paybt.setVisibility(View.VISIBLE);

        }else{
            calculatedAmountTv.setText("Please enter first and last date corretly");
        }
        Toast.makeText(getApplicationContext(), "Calculated time : " + numberOfHours, Toast.LENGTH_SHORT).show();
    }

    private void createOrder() {
//        progressBar.setVisibility(View.VISIBLE);
        Call<CreateOrderResponse> call = RetrofitAPIClient
                .getInstance()
                .getAPI()
                .createOrder(new Order("nope", babyId, caregiverId, amount, false, false));

        call.enqueue(new Callback<CreateOrderResponse>() {
            @Override
            public void onResponse(Call<CreateOrderResponse> call, Response<CreateOrderResponse> response) {
                if (response.isSuccessful() && response.code() == 200) {
                    String message = response.body().getStatus();
                    Toast.makeText(getApplicationContext(), "Successfully created order! status: "+message, Toast.LENGTH_LONG).show();

                } else {
//                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(), "Some unknown problem occurred!! "+response.code(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<CreateOrderResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

}