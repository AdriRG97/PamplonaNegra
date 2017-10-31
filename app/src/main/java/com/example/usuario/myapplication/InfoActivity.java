package com.example.usuario.myapplication;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.widget.VideoView;

public class InfoActivity extends Activity {

    private WebView webViewq;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_activity);

        webViewq = (WebView) findViewById(R.id.webview);

        webViewq.setVisibility(View.INVISIBLE);

        webViewq.getSettings().setJavaScriptEnabled(true);


        // Coger la referencia desde XML layout
        final Spinner spinner = (Spinner) findViewById(R.id.spinner);
        final VideoView videoView = (VideoView) findViewById(R.id.videoView_video);

        videoView.setVisibility(VideoView.INVISIBLE);
        // Initializing a String Array
        String[] elementos = new String[]{"Elige", "Cómo jugar", "Ver nuestra web", "Créditos"};

        final List<String> listaElementos = new ArrayList<>(Arrays.asList(elementos));

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id) {
// SI SELECCIONA "CÓMO JUGAR" ABRIMOS EL HTML LOCAL CON EL TUTORIAL TODO: FALTAN HACER EL HTML BIEN
                if (pos == 0) {
                    adapterView.getSelectedView().setEnabled(false);
                }
                if (pos != 0) {
                    Toast.makeText(adapterView.getContext(), (String) adapterView.getItemAtPosition(pos), Toast.LENGTH_SHORT).show();
                }
                if (pos == 1) {
                    webViewq.loadUrl("about:blank");
                    if (webViewq.getVisibility() == View.INVISIBLE) {
                        webViewq.clearCache(true);
                        webViewq.setInitialScale(175);
                        webViewq.setVisibility(View.VISIBLE);
                        webViewq.loadUrl("file:///android_asset/comoJugar.html");
                        webViewq.getSettings().setBuiltInZoomControls(true);
                        webViewq.getSettings().setDisplayZoomControls(false);
                        adapterView.setSelection(0);
                    }

                }
                // SI SELECCIONA "VER NUESTRA WEB" ABRIMOS LA WEB TODO:FALTA LA WEB VERDADERA
                if (pos == 2) {
                    webViewq.loadUrl("about:blank");
                    if (webViewq.getVisibility() == View.INVISIBLE) {
                        webViewq.setVisibility(View.VISIBLE);
                        webViewq.loadUrl("https://es.lipsum.com/");
                        webViewq.getSettings().setBuiltInZoomControls(true);
                        webViewq.getSettings().setDisplayZoomControls(false);

                        adapterView.setSelection(0);
                    }
                }
                //TODO: Créditos
                if (pos == 3) {
                    webViewq.loadUrl("about:blank");
                    adapterView.setSelection(0);
                }
            }

            //Obligatoria al parecer
            public void onNothingSelected(AdapterView<?> parent) {
            }


        });

        // Initializing an ArrayAdapter
        final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, R.layout.spinner_items, listaElementos) {
            @Override
            public boolean isEnabled(int position) {
                if (position == 0) {
                    return false;
                } else {
                    return true;
                }

            }

            @Override
            public View getDropDownView(int pos, View convertView, ViewGroup parent) {
                View adapterView = super.getDropDownView(pos, convertView, parent);
                TextView tv = (TextView) adapterView;
                if (pos == 0) {
                    tv.setTextColor(Color.GRAY);
                }
                    //flor
                return adapterView;
            }

        };

        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_items);
        spinner.setAdapter(spinnerArrayAdapter);

    }


    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (keyCode) {
                case KeyEvent.KEYCODE_BACK:
                    if (webViewq.getVisibility() == View.VISIBLE) {
                        webViewq.setVisibility(View.INVISIBLE);
                    } else {
                        onBackPressed();
                    }
                    return true;
            }

        }
        return super.onKeyDown(keyCode, event);
    }

}






