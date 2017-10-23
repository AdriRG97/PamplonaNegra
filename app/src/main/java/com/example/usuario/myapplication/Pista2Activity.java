package com.example.usuario.myapplication;

import android.app.Activity;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.VideoView;

public class Pista2Activity extends Activity {
    VideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pista2);
         videoView= (VideoView) findViewById(R.id.videoPrueba);
        Uri directorio = Uri.parse("android.resource://com.example.usuario.myapplication/"+R.raw.intromono);
        videoView.setVideoURI(directorio);
    }
    public  void PlayVideo(View view){
        videoView.start();
    }
}
