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

    public SharedPreferences prefs;
    @Override
    public void onPause() {
        super.onPause();
        Intent i = new Intent(this, AudioService.class);
        i.putExtra("action", AudioService.PAUSE);
        startService(i);
    }

    @Override
    public void onResume() {
        prefs = getSharedPreferences("sonido", Context.MODE_PRIVATE);
        super.onResume();
        Intent i = new Intent(this, AudioService.class);
        i.putExtra("action", AudioService.START);
        startService(i);
        if (prefs.getBoolean("sonido", true) == false) {
            onPause();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_activity);

        miImagen = (ImageView) findViewById(R.id.creditos);

        webViewq = (WebView) findViewById(R.id.webview);

        webViewq.setVisibility(View.INVISIBLE);

        webViewq.getSettings().setJavaScriptEnabled(true);
// Coger la referencia desde XML layout
        final Spinner spinner = (Spinner) findViewById(R.id.spinner);



        // Initializing a String Array
        String[] elementos = new String[]{"Elige", "Cómo jugar", "Ver nuestra web", "Créditos"};

        final List<String> listaElementos = new ArrayList<>(Arrays.asList(elementos));

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id) {
// SI SELECCIONA "CÓMO JUGAR" ABRIMOS EL HTML LOCAL CON EL TUTORIAL TODO: FALTAN HACER EL HTML BIEN
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
                        webViewq.loadUrl("https://es.lipsum.com/");
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


}

