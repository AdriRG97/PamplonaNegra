package com.example.usuario.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.MediaController;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;



public class Pista2Activity extends Activity {
    VideoView videoView;
    TextView pistaTexto;
    EditText texto;

    public int pista;

    SharedPreferences.Editor editor;
    public SharedPreferences prefs;


    //TODO: Poner soniditos en las pistas

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pista2);

        videoView = (VideoView) findViewById(R.id.videoPrueba);
        texto = (EditText) findViewById(R.id.textoPista);
       Button btnPistaDos =(Button)findViewById(R.id.btnPstDos);
        Button btnPistaNormal =(Button)findViewById(R.id.btnEnviar);

        pista = SaberPista();
        if (pista == 0 || pista == 4) {
            MostrarVideo();
        } else if (pista == 1) {
            OcultarVideo();
            btnPistaDos.setVisibility(View.VISIBLE);
            btnPistaNormal.setVisibility(View.INVISIBLE);
            texto.setVisibility(View.INVISIBLE);
            texto.setEnabled(false);
        } else {
            OcultarVideo();
            btnPistaDos.setVisibility(View.INVISIBLE);
            btnPistaNormal.setVisibility(View.VISIBLE);
            texto.setVisibility(View.VISIBLE);
            texto.setEnabled(true);
        }

        OcultarTexto();
        EscribirPista();


    }


    private int SaberAvance() {
        return prefs.getInt("avance", 0);
    }

    private void OcultarTexto() {
        //lo he puesto para poder resolver pista sin estar en los lugares.
        if (SaberAvance() > 0) {


        texto.setVisibility(View.VISIBLE);


        } else {
            texto.setVisibility(View.INVISIBLE);

        }
    }

    private void CambiarPista() {
        int pistaAux = prefs.getInt("pista", 0);
        editor = prefs.edit();
        if (pistaAux == 0) {
            editor.putInt("pista", 1);
            editor.commit();
        } else if (pistaAux == 4) {
            editor.putInt("pista", -1);
            editor.commit();
        } else {

            editor.putInt("pista", pistaAux + 1);
            editor.commit();
            pista = prefs.getInt("pista", 0);
        }

    }

    private boolean SolucionCorrecta() {
        String solucion = SaberSolucion();
        Boolean correcto = false;

        if (solucion.contains("%")) {
            String solucionSplit[] = solucion.split("%");
            if (texto.getText().toString().toUpperCase().equals(solucionSplit[0].toUpperCase()) || texto.getText().toString().toUpperCase().equals(solucionSplit[1].toUpperCase())) {
                correcto = true;
            }
        } else {
            if (texto.getText().toString().equals(SaberSolucion())) {
                correcto = true;
            }
        }


        if (correcto) {
            Toast.makeText(this, R.string.acierto, Toast.LENGTH_SHORT).show();
            return true;
        } else {
            Toast.makeText(this, R.string.fallo, Toast.LENGTH_SHORT).show();
            return false;
        }


    }

    public void SeguirAvance(View view) {
        if (SolucionCorrecta() && SaberPista() != 4) {
            CambiarPista();
            Toast.makeText(this, R.string.seguirAvance, Toast.LENGTH_SHORT).show();
            onBackPressed();
        } else if (SolucionCorrecta() && SaberPista() == 4) {
            CambiarPista();
//            Toast.makeText(this, "Fin del Juego", Toast.LENGTH_SHORT).show();
            onBackPressed();
        }
    }

    private String SaberSolucion() {
        String solucion;
        int numPista = SaberPista();
        ArrayList<String> soluciones = new ArrayList<>();
        try {
            InputStream fraw =
                    getResources().openRawResource(R.raw.soluciones);

            BufferedReader brin =
                    new BufferedReader(new InputStreamReader(fraw));

            for (int i = 0; i < 5; i++) {
                solucion = brin.readLine();
                soluciones.add(solucion.split("-")[1]);
            }

        } catch (Exception ex) {
            Log.e("Ficheros", "Error al leer fichero desde recurso raw");
        }


        return soluciones.get(numPista);
    }

    private void EscribirPista() {
        String linea;
        ArrayList<String> lineas = new ArrayList<String>();
        try {
            InputStream fraw =
                    getResources().openRawResource(R.raw.pista);

            BufferedReader brin =
                    new BufferedReader(new InputStreamReader(fraw));

            for (int i = 0; i < 5; i++) {
                linea = brin.readLine();
                lineas.add(linea.split("//")[1]);
            }

        } catch (Exception ex) {
            Log.e("Ficheros", "Error al leer fichero desde recurso raw");
        }
        SaberPista();
        pistaTexto = (TextView) findViewById(R.id.txtPista);//quizás falta comprobar que el int que devulve la pista no sobrepasa los limites ya que si eso asi rompreá
        try {
            pistaTexto.setText(lineas.get(pista));
        } catch (Exception e) {
            Log.e("Pistas", "Ya no hay mas pistas");
        }
    }

    private void MostrarVideo() {

        videoView.setVisibility(View.VISIBLE);
        Uri directorio = Uri.parse("android.resource://com.example.usuario.myapplication/" + R.raw.video1);
        videoView.setVideoURI(directorio);

        MediaController mc = new MediaController(this);
        mc.setAnchorView(videoView);
        mc.setMediaPlayer(videoView);
        videoView.setMediaController(mc);
        videoView.start();
    }

    private int SaberPista() {


        pista = prefs.getInt("pista", 0);
        return pista;
    }

    private void OcultarVideo() {

        videoView.setVisibility(View.INVISIBLE);
    }
    public void AbrirPista3(View view) {
        Intent irAPista3 = new Intent(getApplicationContext(), Pista3ctivity.class);

        startActivity(irAPista3);
        finish();
    }


}
