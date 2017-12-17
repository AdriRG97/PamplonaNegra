package com.example.usuario.myapplication;


import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {
    private AudioService a = new AudioService();
    public SharedPreferences prefs;
    private SharedPreferences.Editor editor;
    Button siguiente, next, conf;
//    MediaPlayer mediaPlayer;

    @Override
    public void onPause() {
        super.onPause();
      // if (prefs.getBoolean("sonido", true) == true) {
       pausarCancion();
      //  }
    }



    @Override
    public void onResume() {
        super.onResume();
        prefs = getSharedPreferences("configs", Context.MODE_PRIVATE);

        if (prefs.getBoolean("sonido", true) == true) {
            reproducirCancion();
        }
        comprobarIdioma();

}

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 25);
//                }
        prefs = getSharedPreferences("configs", Context.MODE_PRIVATE);
        if (prefs.getBoolean("sonido", true) == true) {
        reproducirCancion();
        }
// Obtenemos la id de los botones donde queremos cambiar la fuente
        siguiente = (Button) findViewById(R.id.button2);
        next = (Button) findViewById(R.id.button);
        conf = (Button) findViewById(R.id.btnAjustes);
// Seteamos en una Variable donde tenemos la fuente (podemos omitir este paso y ponerla directamente cuando cargamos la fuente)
        String carpetaFuente = "fonts/lon.ttf";

// Cargamos la fuente
        Typeface fuente = Typeface.createFromAsset(getAssets(), carpetaFuente);

// Aplicamos la fuente
        siguiente.setTypeface(fuente);
        next.setTypeface(fuente);
        conf.setTypeface(fuente);

// Aplicamos el tamaño de fuente
        siguiente.setTextSize(18);
        next.setTextSize(18);
        conf.setTextSize(18);

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
                        Intent video = new Intent(MainActivity.this, VideoActivity.class);
//                mediaPlayer.release();
                        startActivity(video);
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

    public void comprobarIdioma(){
        prefs = getSharedPreferences("configs", Context.MODE_PRIVATE);
        if (prefs.getBoolean("euskera", true) == true) {
            TextView tv = (TextView) findViewById(R.id.btnAjustes);
            tv.setText(R.string.eus_ajustesMayus);
            conf.setTextSize(12);
            tv = (TextView) findViewById(R.id.button);
            tv.setText(R.string.eus_jugarMayus);
            tv = (TextView) findViewById(R.id.button2);
            tv.setText(R.string.eus_infoMayus);

        } else {
            TextView tv = (TextView) findViewById(R.id.btnAjustes);
            tv.setText(R.string.ajustesMayus);
            tv = (TextView) findViewById(R.id.button);
            tv.setText(R.string.jugarMayus);
            tv = (TextView) findViewById(R.id.button2);
            tv.setText(R.string.infoMayus);

        }
    }
    public void reproducirCancion(){
        Intent i = new Intent(this, AudioService.class);
        i.putExtra("action", AudioService.START);
        startService(i);
    }

    public void pausarCancion(){
        Intent i = new Intent(this, AudioService.class);
        i.putExtra("action", AudioService.PAUSE);
        startService(i);
    }
}
