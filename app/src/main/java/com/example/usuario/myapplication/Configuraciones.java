package com.example.usuario.myapplication;

import android.content.res.ColorStateList;
import android.graphics.*;
import android.graphics.Color;
import android.media.Image;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.ImageButton;

public class Configuraciones extends Activity {

    private ImageButton euskera;
    private ImageButton castellano;
    private ImageButton bang;

    private final ColorStateList cslSeleccionado = new ColorStateList(new int[][]{new int[0]}, new int[]{0xff00ff00});
    private final ColorStateList cslDeseleccionado = new ColorStateList(new int[][]{new int[0]}, new int[]{0x00000000});
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuraciones);

//        euskera = (ImageButton) findViewById(R.id.imgEuskera);
  //      castellano = (ImageButton) findViewById(R.id.imgCastellano);


        euskera = (ImageButton)findViewById(R.id.imgEuskera);
        castellano = (ImageButton) findViewById(R.id.imgCastellano);
        bang = (ImageButton)findViewById(R.id.imgBang);

        euskera.setBackgroundTintList(cslDeseleccionado);

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

}
