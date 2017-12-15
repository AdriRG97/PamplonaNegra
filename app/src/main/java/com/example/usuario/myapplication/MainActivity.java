package com.example.usuario.myapplication;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

    public SharedPreferences prefs;
    Button siguiente, next, conf;
//    MediaPlayer mediaPlayer;

    @Override
    public void onPause() {
        super.onPause();
        Intent i = new Intent(this, AudioService.class);
        i.putExtra("action", AudioService.PAUSE);
        startService(i);
    }

    @Override
    public void onResume() {
        prefs = getSharedPreferences("sonido", Context.MODE_PRIVATE);
        super.onResume();
        Intent i = new Intent(this, AudioService.class);
        i.putExtra("action", AudioService.START);
        startService(i);
        if (prefs.getBoolean("sonido", true) == false) {
            onPause();
        }
    }


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 25);
//                }

        siguiente = (Button) findViewById(R.id.button2);
        siguiente.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent siguiente = new Intent(MainActivity.this, InfoActivity.class);
                startActivity(siguiente);
            }
        });

        next = (Button) findViewById(R.id.button);
        next.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                //--hay que comprobar que la ubicación esta activada (no el permiso) si no da 'error' ( no error pero no funciona)

                    int numPista = prefs.getInt("pista",0);
                    if(numPista == 1|| numPista == 5 || numPista == 0){
                        Intent siguiente = new Intent(MainActivity.this, VideoActivity.class);
//                mediaPlayer.release();
                        startActivity(siguiente);
                    }else{
                        Intent siguiente = new Intent(MainActivity.this, Jugar.class);
//                mediaPlayer.release();
                        startActivity(siguiente);
                    }


            }
        });

        conf = (Button) findViewById(R.id.btnAjustes);
        conf.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                Intent conf = new Intent(MainActivity.this, Configuraciones.class);
                startActivity(conf);
            }
        });


    }


}
