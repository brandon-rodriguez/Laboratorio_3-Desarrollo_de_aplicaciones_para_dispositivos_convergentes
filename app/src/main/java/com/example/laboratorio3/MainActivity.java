package com.example.laboratorio3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;


import androidx.annotation.NonNull;
import android.os.PersistableBundle;

public class MainActivity extends AppCompatActivity {
    long seconds;
    TextView time;
    boolean running;
    ArrayList<String> laps ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button start = (Button) findViewById(R.id.start);
        Button stop = (Button) findViewById(R.id.stop);
        Button restart = (Button) findViewById(R.id.restart);
        Button lap = (Button) findViewById(R.id.lap);
        time = (TextView) findViewById(R.id.time);
        laps = new ArrayList<String>();

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                running= true;
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                running= false;
                showLaps();
            }
        });

        restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                running= false;
                laps= new ArrayList<String>();
                seconds=0;
                time.setText(format(seconds));
            }
        });

        lap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long timeLap= seconds;
                laps.add(format(timeLap)+"");
                seconds=0;
            }
        });

        if (savedInstanceState != null) {
            this.seconds = savedInstanceState.getLong("SEGUNDOS");
            this.running = savedInstanceState.getBoolean("EJECUTANDO");
            this.laps = savedInstanceState.getStringArrayList("laps");
            showLaps();
        }

        iniciar();
    }

    public void iniciar(){
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                time.setText(format(seconds));
                handler.postDelayed(this, 1000);
                seconds = running ? seconds + 1 : seconds;
            }
        });
    }

    public String format(long time){
        int hours = (int) time/3600;
        int minutes= (int) (time%3600)/60;
        int seconds= (int)  time%60;
        return String.format(Locale.getDefault(), "%02d:%02d:%02d", hours, minutes, seconds);
    }

    public void showLaps(){
        TextView lapsView = (TextView) findViewById(R.id.laps);
        String result="";
        lapsView.setText(result);
        for (int i = 0; i < laps.size(); i ++){
            result+= i+" : " +laps.get(i)+ "\n";
        }
        lapsView.setText(result);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putLong("seconds", this.seconds);
        outState.putBoolean("running", this.running);
        outState.putStringArrayList("laps", this.laps);
    }

}