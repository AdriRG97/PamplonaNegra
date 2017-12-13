package com.example.usuario.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.EditText;
import android.content.SharedPreferences.Editor;
import android.content.res.ColorStateList;
import android.graphics.*;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.Image;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageButton;



import com.kyleduo.switchbutton.SwitchButton;

public class Configuraciones extends Activity {

    private ImageButton euskera;
    private ImageButton castellano;
    private ImageButton bang;
    private com.kyleduo.switchbutton.SwitchButton swSonido;
    private com.kyleduo.switchbutton.SwitchButton swMapa;
    public SharedPreferences prefs;
    private final ColorStateList cslSeleccionado = new ColorStateList(new int[][]{new int[0]}, new int[]{Color.GREEN});
    private final ColorStateList cslDeseleccionado = new ColorStateList(new int[][]{new int[0]}, new int[]{Color.TRANSPARENT});
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuraciones);


//        euskera = (ImageButton) findViewById(R.id.imgEuskera);
  //      castellano = (ImageButton) findViewById(R.id.imgCastellano);

        prefs = getSharedPreferences("sonido", Context.MODE_PRIVATE);
        swSonido = (SwitchButton)findViewById(R.id.swSonido);
        swMapa = (SwitchButton)findViewById(R.id.swMapaOscuro);
        euskera = (ImageButton)findViewById(R.id.imgEuskera);
        castellano = (ImageButton) findViewById(R.id.imgCastellano);
        bang = (ImageButton)findViewById(R.id.imgBang);

        euskera.setBackgroundTintList(cslDeseleccionado);

        if(prefs.getBoolean("sonido", true) == false) {

            swSonido.setChecked(false);
            onPause();

               }else{
            swSonido.setChecked(true);
            onResume();
               }

        if(prefs.getBoolean("mapaNegro", true) == false) {
            swMapa.setChecked(false);
            reproducirDisparo();
        }else{
            swMapa.setChecked(true);
            reproducirBala();
        }
        euskera.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
               euskeraSeleccionado();
            }
        });

        castellano.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                castellanoSeleccionado();
            }
        });

        bang.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                reproducirBala();
            }

        });

        bang.setOnLongClickListener(new View.OnLongClickListener(){

            @Override
            public boolean onLongClick(View v) {
                reproducirDisparo();
                return false;
            }
        });

        swSonido.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(swSonido.isChecked()) {
                    editor = prefs.edit();
                    editor.putBoolean("sonido", true);
                    editor.commit();
                    onResume();
                }else{
                    editor = prefs.edit();
                    editor.putBoolean("sonido", false);
                    editor.commit();
                    onPause();
                }
            }
        });



        swMapa.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(swMapa.isChecked()) {
                    editor = prefs.edit();
                    editor.putBoolean("mapaNegro", true);
                    editor.commit();

                }else{
                    editor = prefs.edit();
                    editor.putBoolean("mapaNegro", false);
                    editor.commit();

                }
            }
        });


    }


    public void euskeraSeleccionado(){
        euskera.setBackgroundTintList(cslSeleccionado);
        castellano.setBackgroundTintList(cslDeseleccionado);
        euskera.setEnabled(false);
        castellano.setEnabled(true);
}

    public void castellanoSeleccionado(){
        euskera.setBackgroundTintList(cslDeseleccionado);
        castellano.setBackgroundTintList(cslSeleccionado);
        euskera.setEnabled(true);
        castellano.setEnabled(false);
    }


    private void reproducirBala() {
        MediaPlayer mp = MediaPlayer.create(this, R.raw.bala);
        mp.start();
    }

    private void reproducirDisparo() {
        MediaPlayer mp = MediaPlayer.create(this, R.raw.disparo);
        mp.start();
    }

    @Override
    public void onPause() {
        super.onPause();
        Intent i = new Intent(this, AudioService.class);
        i.putExtra("action", AudioService.PAUSE);
        startService(i);
    } @Override
    public void onResume() {
        prefs = getSharedPreferences("sonido", Context.MODE_PRIVATE);
        if (prefs.getBoolean("sonido", true) == false) {
            onPause();
        } else {
            super.onResume();
            Intent i = new Intent(this, AudioService.class);
            i.putExtra("action", AudioService.START);
            startService(i);
        }
    }



}
