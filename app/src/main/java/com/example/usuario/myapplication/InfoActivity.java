package com.example.usuario.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;



public class InfoActivity extends Activity {


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_activity);
        Spinner miSpinner = (Spinner) findViewById(R.id.spinner);
        String[] elemento = {"Cómo jugar","Ver nuestra web","Créditos"};
        miSpinner.setAdapter(new ArrayAdapter<String>(this, R.layout.spinner_items, elemento));


  miSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


        public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id)
        {
            Toast.makeText(adapterView.getContext(),
                    (String) adapterView.getItemAtPosition(pos), Toast.LENGTH_SHORT).show();
        }


        public void onNothingSelected(AdapterView<?> parent)
        {    }
    });


}


}
