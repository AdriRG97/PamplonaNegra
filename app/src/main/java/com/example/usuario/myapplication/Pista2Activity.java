package com.example.usuario.myapplication;

import android.app.Activity;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Pista2Activity extends Activity {
    VideoView videoView;
     TextView pistaTexto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pista2);
         videoView= (VideoView) findViewById(R.id.videoPrueba);
        Uri directorio = Uri.parse("android.resource://com.example.usuario.myapplication/"+R.raw.intromono);
        videoView.setVideoURI(directorio);

        MediaController mc = new MediaController(this);
        videoView.setMediaController(mc);
        videoView.start();

        try
        {
            InputStream fraw =
                    getResources().openRawResource(R.raw.pista);

            BufferedReader brin =
                    new BufferedReader(new InputStreamReader(fraw));

            String linea = brin.readLine();
            String lineas[]= linea.split("//");
           pistaTexto = (TextView) findViewById(R.id.txtPista);
           pistaTexto.setText(lineas[1]);


            fraw.close();
        }
        catch (Exception ex)
        {
            Log.e("Ficheros", "Error al leer fichero desde recurso raw");
        }
    }




//    public  void PlayVideo(View view){
//
//    }
}
