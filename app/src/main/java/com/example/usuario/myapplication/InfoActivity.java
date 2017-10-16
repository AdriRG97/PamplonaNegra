package com.example.usuario.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;



public class InfoActivity extends Activity {


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_activity);
        Spinner miSpinner = (Spinner) findViewById(R.id.spinner);
        String[] elemento = {"Cómo jugar","Ver nuestra web","Créditos"};
        miSpinner.setAdapter(new ArrayAdapter<String>(this, R.layout.spinner_items, elemento));
        final WebView webViewq = (WebView) this.findViewById(R.id.webview);
        webViewq.setVisibility(View.INVISIBLE);

        webViewq.getSettings().setJavaScriptEnabled(true);
  miSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


        public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id)
        {
//TODO: SI SELECCIONA "CÓMO JUGAR" ABRIMOS EL HTML LOCAL CON EL TUTORIAL
            if(pos == 3){
                adapterView.getItemAtPosition(pos);

                webViewq.setVisibility(View.VISIBLE);
                webViewq.getSettings().setSupportZoom(true);
                webViewq.loadUrl("file:///android_asset/comoJugar.html");


            }
            //TODO: SI SELECCIONA "VER NUESTRA WEB" ABRIMOS LA WEB (PONEMOS DE PRUEBA UNA WEB CUALAQUIERA)
            if(pos == 1){
                adapterView.getItemAtPosition(pos);
                webViewq.setVisibility(View.VISIBLE);
                webViewq.getSettings().setSupportZoom(true);
                webViewq.loadUrl("https://www.pokemon.com/es/");


            }

            Toast.makeText(adapterView.getContext(),(String) adapterView.getItemAtPosition(pos), Toast.LENGTH_SHORT).show();
        }



      public void onNothingSelected(AdapterView<?> parent)
        {    }
    });



    }



}
