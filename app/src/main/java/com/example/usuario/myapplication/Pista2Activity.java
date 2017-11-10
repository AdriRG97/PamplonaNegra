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
import android.widget.VideoView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

class CustomController extends MediaController {
    public CustomController(Context context, View anchor) {
        super(context);
        super.setAnchorView(anchor);
    }
}

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
        videoView= (VideoView) findViewById(R.id.videoPrueba);
        texto = (EditText)findViewById(R.id.textoPista);
prefs =  getSharedPreferences("pistas", Context.MODE_PRIVATE);
        //falta otro metodo que lo primero identifique la pista para comprovar que debe haber un VideoView

        if(SaberPista()==0 || SaberPista()==4){
            MostrarVideo();//aqui falta un condiciona que ponga el video SOLO en las pistas que tienen que tener video
        }else{
        OcultarVideo();//rompe y para la aplicación
        }
         OcultarTexto();
            EscribirPista();



    }
    private int SaberAvance(){
        int avance = prefs.getInt("avance", 0);
        return avance;
    }
private void OcultarTexto(){
        if(SaberAvance()>0){
            //hacer un view que sea el texto y ponerlo visible

            texto.setVisibility(View.VISIBLE);
            //metodo de resolucion d pista que deje todo como debe estar

        }else{
            texto.setVisibility(View.INVISIBLE);

        }
    }
    private void CambiarPista(){
        int pistaAux= prefs.getInt("pista", 0);
    editor = prefs.edit();
        if(pistaAux==0){
            editor.putInt("pista",0);
            editor.commit();
        }else{

            editor.putInt("pista", pistaAux+1);
            editor.commit();
        }


    }
    private void EscribirPista(){
        String linea;
        ArrayList<String> lineas = new ArrayList<String>();
        try
        {
            InputStream fraw =
                    getResources().openRawResource(R.raw.pista);

            BufferedReader brin =
                    new BufferedReader(new InputStreamReader(fraw));
            //hay que poner un condicional y una lectura de el fichero/BBDD donde tengamos almacenada la puntuación y segun el número de pista en el que esté muestre una u otra
            for (int i = 0; i < 5; i++){
                linea = brin.readLine();
                lineas.add(linea.split("//")[1]);
            }

        }
        catch (Exception ex)
        {
            Log.e("Ficheros", "Error al leer fichero desde recurso raw");
        }
        SaberPista();
        pistaTexto = (TextView) findViewById(R.id.txtPista);//quizás falta comprobar que el int que devulve la pista no sobrepasa los limites ya que si eso asi rompreá
        pistaTexto.setText(lineas.get(pista));

    }
private void MostrarVideo(){

videoView.setVisibility(View.VISIBLE);
    Uri directorio = Uri.parse("android.resource://com.example.usuario.myapplication/" + R.raw.resu);
    videoView.setVideoURI(directorio);

    MediaController mc = new MediaController(this);
    mc.setAnchorView(videoView);
    mc.setMediaPlayer(videoView);
    videoView.setMediaController(mc);
    videoView.start();
}
private int SaberPista(){
//hacer este método casi entero para que busque el número de pista y la devuelva
     pista = prefs.getInt("pista", 0);
    return pista;
}
private void OcultarVideo(){

    videoView.setVisibility(View.INVISIBLE);
}

}
