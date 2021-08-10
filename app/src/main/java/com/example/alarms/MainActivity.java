package com.example.alarms;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    Button setAlarm;//Инцицилизировали кнопку

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());

        setAlarm = findViewById(R.id.alarmbutton);//привязали айди к кнопке


        setAlarm.setOnClickListener(v -> {
            MaterialTimePicker materialTimePicker = new MaterialTimePicker.Builder().setTimeFormat(TimeFormat.CLOCK_24H)
                    .setHour(12)
                    .setMinute(0)
                    .setTitleText("Выберете время будильника").build();//Сконструировали диалог

            //Пользователь выбирает время в диалоге
            materialTimePicker.addOnPositiveButtonClickListener(view -> {
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.HOUR_OF_DAY, materialTimePicker.getHour());
                calendar.set(Calendar.MINUTE, materialTimePicker.getMinute());
                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                AlarmManager.AlarmClockInfo alarmClockInfo = new AlarmManager.AlarmClockInfo(calendar.getTimeInMillis(), getAlarmInfoPendingIntent());

                alarmManager.setAlarmClock(alarmClockInfo,getAlarmActionPendingIntent());
                Toast.makeText(this,"Будильник установлен"+sdf.format(calendar.getTime()),Toast.LENGTH_SHORT).show();
            });
            //  materialTimePicker.show(getSupportFragmentManager(),"gg");

        });
    }
        private PendingIntent getAlarmInfoPendingIntent(){
            Intent alarmsInfoIntent = new Intent(this,MainActivity.class);
            alarmsInfoIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);//указение андроиду таска в котором будет запущено наше активити
            return PendingIntent.getActivity(this,0,alarmsInfoIntent,PendingIntent.FLAG_UPDATE_CURRENT) ;
        }


        private PendingIntent getAlarmActionPendingIntent(){
        Intent intent = new Intent(this,AlarmActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        return PendingIntent.getActivity(this,1,intent,PendingIntent.FLAG_CANCEL_CURRENT);

        }


    }
