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
import android.widget.TextView;


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
    private TextView lblConf, lblMapa, lblSonido, lblIdioma;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuraciones);

        swSonido = (SwitchButton)findViewById(R.id.swSonido);
        swMapa = (SwitchButton)findViewById(R.id.swMapaOscuro);
        euskera = (ImageButton)findViewById(R.id.imgEuskera);
        castellano = (ImageButton) findViewById(R.id.imgCastellano);
        bang = (ImageButton)findViewById(R.id.imgBang);

        lblConf = (TextView)findViewById(R.id.textView2);
        lblMapa = (TextView)findViewById(R.id.lblMapaOscuro);
        lblSonido = (TextView)findViewById(R.id.lblMapaOscuro2);
        lblIdioma = (TextView)findViewById(R.id.lblIdioma);

        prefs = getSharedPreferences("configs", Context.MODE_PRIVATE);

        euskera.setBackgroundTintList(cslDeseleccionado);
        if(prefs.getBoolean("euskera", true) == true) {
            euskeraSeleccionado();
        }else{
            castellanoSeleccionado();
        }



// Seteamos en una Variable donde tenemos la fuente (podemos omitir este paso y ponerla directamente cuando cargamos la fuente)
        String carpetaFuente = "fonts/lon.ttf";

// Cargamos la fuente
        Typeface fuente = Typeface.createFromAsset(getAssets(), carpetaFuente);

// Aplicamos la fuente
        lblConf.setTypeface(fuente);
        lblMapa.setTypeface(fuente);
        lblIdioma.setTypeface(fuente);
        lblSonido.setTypeface(fuente);

// Aplicamos el tamaño de fuente
        lblConf.setTextSize(36);
        lblMapa.setTextSize(24);
        lblSonido.setTextSize(24);
        lblIdioma.setTextSize(24);

      /*  prefs = getSharedPreferences("sonido", Context.MODE_PRIVATE);
        if(prefs.getBoolean("sonido", true) == false) {

     swSonido.setChecked(false);
            onPause();

               }else{
            swSonido.setChecked(true);
            onResume();
               }*/

        if(prefs.getBoolean("mapaNegro", true) == false) {
            swMapa.setChecked(false);
            //  reproducirDisparo(); //TODO Se deberá quitar el tema oscuro de GMaps
        }else{
            swMapa.setChecked(true);
            // reproducirBala();//Se deberá aplicar el tema oscuro
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
                    reproducirCancion();
                }else{
                    editor = prefs.edit();
                    editor.putBoolean("sonido", false);
                    editor.commit();
                    pausarCancion();
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
        prefs = getSharedPreferences("configs", Context.MODE_PRIVATE);
        editor = prefs.edit();
        editor.putBoolean("euskera", true);
        editor.commit();

            TextView tv = (TextView) findViewById(R.id.lblIdioma);
            tv.setText(R.string.eus_lblIdioma);
            tv = (TextView) findViewById(R.id.lblMapaOscuro);
            tv.setText(R.string.eus_lblMapaOscuro);
            tv = (TextView) findViewById(R.id.lblMapaOscuro2);
            tv.setText(R.string.eus_lblSonido);
            tv = (TextView) findViewById(R.id.textView2);
            tv.setText(R.string.eus_configuracion);

}

    public void castellanoSeleccionado(){
        euskera.setBackgroundTintList(cslDeseleccionado);
        castellano.setBackgroundTintList(cslSeleccionado);
        euskera.setEnabled(true);
        castellano.setEnabled(false);
        prefs = getSharedPreferences("configs", Context.MODE_PRIVATE);
        editor = prefs.edit();
        editor.putBoolean("euskera", false);
        editor.commit();
                 TextView tv = (TextView) findViewById(R.id.lblIdioma);
            tv.setText(R.string.lblIdioma);
            tv = (TextView) findViewById(R.id.lblMapaOscuro);
            tv.setText(R.string.lblMapaOscuro);
            tv = (TextView) findViewById(R.id.lblMapaOscuro2);
            tv.setText(R.string.lblSonido);
            tv = (TextView) findViewById(R.id.textView2);
            tv.setText(R.string.configuracion);
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
        if (prefs.getBoolean("sonido", true) == true) {
            pausarCancion();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
       prefs = getSharedPreferences("configs", Context.MODE_PRIVATE);
        if (prefs.getBoolean("sonido", true) == true) {
            reproducirCancion();
            swSonido.setChecked(true);
        }else{
            pausarCancion();
            swSonido.setChecked(false);
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
