package com.example.usuario.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.MediaController;
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
    public int pista;
    SharedPreferences.Editor editor;
    public SharedPreferences prefs;
    EditText texto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pista2);
        videoView = (VideoView) findViewById(R.id.videoPrueba);
        texto = (EditText) findViewById(R.id.textoPista);
        prefs = getSharedPreferences("pistas", Context.MODE_PRIVATE);

        pista = SaberPista();
        if (SaberPista() == 0 || SaberPista() == 4) {
            MostrarVideo();
        } else {
            OcultarVideo();
        }
        OcultarTexto();
        EscribirPista();


    }

    private int SaberAvance() {
        int avance = prefs.getInt("avance", 0);
        return avance;
    }

    private void OcultarTexto() {
        //lo he puesto para poder resolver pista sin estar en los lugares.
        // if(SaberAvance()>0){


        texto.setVisibility(View.VISIBLE);


        //  }else{
        //    texto.setVisibility(View.INVISIBLE);

        //  }
    }

    private void CambiarPista() {
        int pistaAux = prefs.getInt("pista", 0);
        editor = prefs.edit();
        if (pistaAux == 0) {
            editor.putInt("pista", 1);
            editor.commit();
        } else {

            editor.putInt("pista", pistaAux + 1);
            editor.commit();
            pista = prefs.getInt("pista", 0);
        }


    }

    private boolean SolucionCorrecta() {
//falta comprobar en mayus minus con acentos sin acentos y que sea exactamente o no
        if (texto.getText().toString().equals(SaberSolucion())) {
            Toast.makeText(this, "Has Acertadum!", Toast.LENGTH_SHORT).show();
            return true;
        } else {
            Toast.makeText(this, "Has fallado", Toast.LENGTH_SHORT).show();
            return false;
        }


    }

    public void SeguirAvance(View view) {
        if (SolucionCorrecta()) {
            CambiarPista();
            Toast.makeText(this, "hola mundo", Toast.LENGTH_SHORT).show();
            onBackPressed();
        }
    }

    private String SaberSolucion() {
        String solucion;
        int numPista = SaberPista();
        ArrayList<String> soluciones = new ArrayList<String>();
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
        pistaTexto.setText(lineas.get(pista));

    }

    private void MostrarVideo() {

        videoView.setVisibility(View.VISIBLE);
        Uri directorio = Uri.parse("android.resource://com.example.usuario.myapplication/" + R.raw.resu);
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

}
