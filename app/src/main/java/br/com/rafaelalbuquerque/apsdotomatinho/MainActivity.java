package br.com.rafaelalbuquerque.apsdotomatinho;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TabHost;
import android.widget.Chronometer;
import android.widget.Toast;
import android.os.SystemClock;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private Chronometer chronometer;
    private Chronometer chronometer2;
    private Chronometer chronometer3;
    private long pauseOffset;
    private boolean running = false;
    private int count;
    private int count2;
    private int pomodoro;
    private int pause;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        chronometer = findViewById(R.id.chronometer);
        chronometer2 = findViewById(R.id.chronometer2);
        chronometer3 = findViewById(R.id.chronometer3);
        chronometer.setFormat("%s");
        chronometer2.setFormat("%s");
        chronometer3.setFormat("%s");
        chronometer.setBase(SystemClock.elapsedRealtime());
        chronometer2.setBase(SystemClock.elapsedRealtime());
        chronometer3.setBase(SystemClock.elapsedRealtime());
        chronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {

                if ((SystemClock.elapsedRealtime() - chronometer.getBase()) >= 10000) {
                    chronometer.setBase(SystemClock.elapsedRealtime());
                    count=1;
                    count2++;
                    pomodoro++;
                    Toast.makeText(MainActivity.this, "Você completou mais um pomodoro, agora você possui : " + pomodoro + " Pomodoro feitos", Toast.LENGTH_SHORT).show();
                    chronometer.stop();
                    pauseOffset = 0;
                }
            }
        });

        chronometer2.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer2) {
                if ((SystemClock.elapsedRealtime() - chronometer2.getBase()) >= 10000) {
                    chronometer2.setBase(SystemClock.elapsedRealtime());
                    chronometer2.stop();
                    Toast.makeText(MainActivity.this, "Você completou sua pausa curta!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        chronometer3.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer3) {
                if ((SystemClock.elapsedRealtime() - chronometer3.getBase()) >= 10000) {
                    chronometer3.setBase(SystemClock.elapsedRealtime());
                    chronometer3.stop();
                    Toast.makeText(MainActivity.this, "Você completou sua pausa longa!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        TabHost tabHost = (TabHost) findViewById(R.id.tabHost);
        tabHost.setup();
        TabHost.TabSpec aba1 = tabHost.newTabSpec("Tomatinho");
        aba1.setContent(R.id.Tomatinho);
        aba1.setIndicator("Tomatinho");
        TabHost.TabSpec aba2 = tabHost.newTabSpec("Pausa_Curta");
        aba2.setContent(R.id.Pausa_Curta);
        aba2.setIndicator("Pausa curta");
        TabHost.TabSpec aba3 = tabHost.newTabSpec("Pausa_Longa");
        aba3.setContent(R.id.Pausa_Longa);
        aba3.setIndicator("Pausa longa");

        tabHost.addTab(aba1);
        tabHost.addTab(aba2);
        tabHost.addTab(aba3);

    }

    public void startChronometer(View v) {
        if (count == 1) {
            validate4(v);
        } else if (count2 == 4) {
            validate1(v);
        } else if (running == false) {
            running = true;
            chronometer.setBase(SystemClock.elapsedRealtime());
            chronometer.start();
            Toast.makeText(MainActivity.this, "O cronômetro foi iniciado.", Toast.LENGTH_SHORT).show();
        } else if (running && pause == 1) {
            resume(v);
            pause = 0;
        }else if (running){
            Toast.makeText(MainActivity.this, "O cronômetro já está rodando.", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(MainActivity.this, "Vá para sua pausa.", Toast.LENGTH_SHORT).show();
        }
    }

    public void resume(View v){
        chronometer.setBase(SystemClock.elapsedRealtime() - pauseOffset);
        chronometer.start();
    }
    public void pauseChronometer(View v) {
        if (running == true) {
            chronometer.stop();
            pauseOffset = SystemClock.elapsedRealtime() - chronometer.getBase();
            pause = 1;
        }
    }
    public void resetChronometer(View v) {
        chronometer.setBase(SystemClock.elapsedRealtime());
        pauseOffset = 0;
    }
    public void pausaCurta(View v) {
        chronometer2.setBase(SystemClock.elapsedRealtime());
        chronometer2.start();
        count = 0;
        running = false;
    }
    public void pausaLonga(View v) {
        chronometer3.setBase(SystemClock.elapsedRealtime());
        chronometer3.start();
        count2 = count2 - 4;
        count = 0;
        running = false;

    }
    public void validate1(View v){
        Toast.makeText(MainActivity.this, "Você precisa fazer uma pausa longa", Toast.LENGTH_SHORT).show();
    }
    public void validate2(View v) {
        if (count2 < 4 && count == 1) {
            pausaCurta(v);
        }else if(count2 >= 4){
            Toast.makeText(MainActivity.this, "Você precisa fazer uma pausa longa", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(MainActivity.this, "Você precisa completar um pomodoro para uma pausa curta", Toast.LENGTH_SHORT).show();
        }
    }
    public void validate3(View v) {
        if (count2 >= 4) {
            pausaLonga(v);
        } else {
            Toast.makeText(MainActivity.this, "São necessários 4 pomodoros completos para uma pausa longa", Toast.LENGTH_SHORT).show();
        }
    }
    public void validate4(View V){
        Toast.makeText(MainActivity.this, "Você precisa fazer uma pausa curta", Toast.LENGTH_SHORT).show();
    }
}