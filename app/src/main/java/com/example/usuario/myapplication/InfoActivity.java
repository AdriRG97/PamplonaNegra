/*
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;


public class InfoActivity extends Activity {

    private WebView webViewq;

    private ImageView miImagen;


   //final String[] SPINNERLIST = {"Elige", "Cómo jugar", "Ver nuestra web", "Créditos"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_activity);


        miImagen = (ImageView) findViewById(R.id.creditos);

        webViewq = (WebView) findViewById(R.id.webview);

        webViewq.setVisibility(View.INVISIBLE);

        webViewq.getSettings().setJavaScriptEnabled(true);

        // Coger la referencia desde XML layout
        final MaterialBetterSpinner materialDesignSpinner = (MaterialBetterSpinner) findViewById(R.id.android_material_design_spinner);



        // Initializing a String Array
     final String[] elementos = new String[]{"Elige", "Cómo jugar", "Ver nuestra web", "Créditos"};

       final List<String> listaElementos = new ArrayList<>(Arrays.asList(elementos));

        materialDesignSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

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
                    miImagen.setVisibility(View.VISIBLE);
                    adapterView.setSelection(0);
                }
            }

            //Obligatoria al parecer
            public void onNothingSelected(AdapterView<?> parent) {
            }


        });

        // Initializing an ArrayAdapter


          final  ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_dropdown_item_1line, listaElementos){


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
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        materialDesignSpinner.setAdapter(spinnerArrayAdapter);


    }

//TODO
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (keyCode) {
                case KeyEvent.KEYCODE_BACK:

                    if (webViewq.getVisibility() == View.VISIBLE) {
                        webViewq.setVisibility(View.INVISIBLE);

                    } else if (miImagen.getVisibility() == View.VISIBLE) {
                        miImagen.setVisibility(View.INVISIBLE);

                    }else{
                        onBackPressed();
                    }

            }            return true;
        }

            return super.onKeyDown(keyCode, event);
        }


}

*/

package com.example.usuario.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InfoActivity extends Activity {

    private WebView webViewq;
    private ImageView miImagen;
    private TextView lblInfo, lblElige;

    private  String[] elementos = new String[]{"Elige", "Cómo jugar", "Ver nuestra web", "Créditos"};

    public SharedPreferences prefs;
    @Override
    public void onPause() {
        super.onPause();
   //     if (prefs.getBoolean("sonido", true) == true) {
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

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_activity);
        // Seteamos en una Variable donde tenemos la fuente (podemos omitir este paso y ponerla directamente cuando cargamos la fuente)

        String carpetaFuente = "fonts/lon.ttf";

        lblElige = (TextView) findViewById(R.id.lblElige);
        lblInfo = (TextView) findViewById(R.id.lblInfo);
// Cargamos la fuente
        Typeface fuente = Typeface.createFromAsset(getAssets(), carpetaFuente);

// Aplicamos la fuente
        lblElige.setTypeface(fuente);
        lblInfo.setTypeface(fuente);

// Aplicamos el tamaño de fuente
       // reproducirCancion();

        lblInfo.setTextSize(36);

        miImagen = (ImageView) findViewById(R.id.creditos);

        webViewq = (WebView) findViewById(R.id.webview);

        webViewq.setVisibility(View.INVISIBLE);

        webViewq.getSettings().setJavaScriptEnabled(true);
// Coger la referencia desde XML layout
        final Spinner spinner = (Spinner) findViewById(R.id.spinner);

        // Initializing a String Array

        comprobarIdioma();
        //elementos[0] = "Aukeratu";
        final List<String> listaElementos = new ArrayList<>(Arrays.asList(elementos));

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id) {
// SI SELECCIONA "CÓMO JUGAR" ABRIMOS EL HTML LOCAL CON EL TUTORIAL TODO: FALTA HACER EL HTML BIEN
                if (pos == 0) {
                    adapterView.getSelectedView().setEnabled(false);

                    if (miImagen.getVisibility() == View.INVISIBLE){
                        adapterView.setEnabled(true);
                    }
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
                        spinner.setEnabled(false);
                    }

                }
                // SI SELECCIONA "VER NUESTRA WEB" ABRIMOS LA WEB TODO:FALTA LA WEB VERDADERA
                if (pos == 2) {
                    webViewq.loadUrl("about:blank");
                    if (webViewq.getVisibility() == View.INVISIBLE) {
                        webViewq.setVisibility(View.VISIBLE);
                        webViewq.loadUrl("http://asir.com.es:1719/index.php");
                        webViewq.getSettings().setBuiltInZoomControls(true);
                        webViewq.getSettings().setDisplayZoomControls(false);
                        adapterView.setSelection(0);
                        spinner.setEnabled(false);

                    }
                }
                //TODO: Créditos
                if (pos == 3) {
                    webViewq.loadUrl("about:blank");
                    miImagen.setVisibility(View.VISIBLE);
                    spinner.setEnabled(false);
                    adapterView.setSelection(0);

                }
            }

            //Obligatoria al parecer
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        // Initializing an ArrayAdapter
        final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, listaElementos) {
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
                return adapterView;
            }
        };

        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(spinnerArrayAdapter);
    }

    //TODO
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            final Spinner spinner = (Spinner) findViewById(R.id.spinner);
            switch (keyCode) {
                case KeyEvent.KEYCODE_BACK:

                    if (webViewq.getVisibility() == View.VISIBLE) {

                        webViewq.setVisibility(View.INVISIBLE);
                        spinner.setEnabled(true);


                    } else if (miImagen.getVisibility() == View.VISIBLE) {
                        miImagen.setVisibility(View.INVISIBLE);
                        spinner.setEnabled(true);
                    }else{
                        onBackPressed();
                    }

            }            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    public void comprobarIdioma(){
        prefs = getSharedPreferences("configs", Context.MODE_PRIVATE);
        if (prefs.getBoolean("euskera", true) == true) {
            TextView tv = (TextView) findViewById(R.id.lblInfo);
            tv.setText(R.string.eus_infoMayus);
            tv = (TextView) findViewById(R.id.lblElige);
            tv.setText(R.string.eus_elige);
            lblElige.setTextSize(20);

            elementos[0] = "Aukeratu";
            elementos[1] = "Nola jolastu?";
            elementos[2] = "Ikusi gure web orrialde";
            elementos[3] = "Kredituak";
        } else {
            TextView tv = (TextView) findViewById(R.id.lblInfo);
            tv.setText(R.string.infoMayus);
            tv = (TextView) findViewById(R.id.lblElige);
            tv.setText(R.string.elige);
            lblElige.setTextSize(32);

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

