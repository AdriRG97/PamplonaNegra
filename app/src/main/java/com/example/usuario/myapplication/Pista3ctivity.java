package com.example.usuario.myapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.lang.reflect.Array;

public class Pista3ctivity extends Activity {

    EditText[] numerosIntroducidos=new EditText[9];
    EditText numUno;
    EditText numDos;
    EditText numTre;
    EditText numCua;
    EditText numCin;
    EditText numSei;
    EditText numSie;
    EditText numOch;
    EditText numNue;
   String numeros[]={"8","3","6", "4","1","2","5","9","7"};
    SharedPreferences.Editor editor;
    public SharedPreferences prefs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pista3ctivity);
        numUno= (EditText) findViewById(R.id.num1);
        numDos= (EditText) findViewById(R.id.num2);
        numTre= (EditText) findViewById(R.id.num3);
        numCua= (EditText) findViewById(R.id.num4);
        numCin= (EditText) findViewById(R.id.num5);
        numSei = (EditText) findViewById(R.id.num6);
        numSie= (EditText) findViewById(R.id.num7);
        numOch= (EditText) findViewById(R.id.num8);
        numNue= (EditText) findViewById(R.id.num9);
        numerosIntroducidos[0] = numUno;
        numerosIntroducidos[1] = numDos;
        numerosIntroducidos[2] = numTre;
        numerosIntroducidos[3] = numCua;
        numerosIntroducidos[4] = numCin;
        numerosIntroducidos[5] = numSei;
        numerosIntroducidos[6] = numSie;
        numerosIntroducidos[7] = numOch;
        numerosIntroducidos[8] = numNue;
        prefs = getSharedPreferences("configs", Context.MODE_PRIVATE);






    }


    public int comprobarNums(){
        int errores=9;
for (int i = 0; i<9; i++){
    String numAux = numerosIntroducidos[i].getText().toString();
    if (numAux.equals(numeros[i]) ){
    errores = errores -1;
    }
}
        return errores;
    }
    private AlertDialog alerta(){
        AlertDialog.Builder builder =
                new AlertDialog.Builder(getActivity());

        builder.setMessage("Has tenido "+comprobarNums()+" errores")
                .setTitle("Información")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        if(comprobarNums() == 0){
                            finish();
                        }

                    }
                });

        return builder.create();
    }
    public void Avanzar(View view){
        if(comprobarNums() == 0){
            alerta().show();
            int pistaAux = prefs.getInt("pista", 0);
            editor = prefs.edit();
            editor.putInt("pista", pistaAux + 1);
            editor.commit();


        }else{
            alerta().show();

        }
    }

    public Context getActivity() {
        return this;
    }

}
