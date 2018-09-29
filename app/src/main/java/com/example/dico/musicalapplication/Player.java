package com.example.dico.musicalapplication;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

import java.io.File;
import java.util.ArrayList;

public class Player extends Activity {
    Bundle b;
    ArrayList<File> mysongs;
    int position;
    static MediaPlayer mp;
    Uri u;

    Button btPlay,btFF,btFB,btPv,btNxt;
    SeekBar sb;
    Thread updateSeekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(getWindow().FEATURE_NO_TITLE);
        setContentView(R.layout.activity_player);

        btPlay = (Button)findViewById(R.id.btnPlay);
        btFF = (Button)findViewById(R.id.btnFivePlus);
        btFB = (Button)findViewById(R.id.btnFiveMinus);
        btNxt = (Button)findViewById(R.id.btnNest);
        btPv = (Button)findViewById(R.id.btnPrevious);
        sb = (SeekBar)findViewById(R.id.seekBar);

        updateSeekBar = new Thread(){

            @Override
            public void run(){
                int totalDuration = mp.getDuration();

                int currentPosition = 0;
                //sb.setMax(totalDuration);

                while(currentPosition < totalDuration){
                    try {
                        sleep(500);
                        currentPosition = mp.getCurrentPosition();
                        sb.setProgress(currentPosition);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                /*if (currentPosition == totalDuration){
                    mp.stop();
                    mp.release();
                    position = position +1;
                    position = position % (mysongs.size());

                    u = Uri.parse(mysongs.get(position).toString());
                    mp = MediaPlayer.create(getApplicationContext(),u);
                    //btPlay.setText("Play");
                    mp.start();
                    sb.setMax(mp.getDuration());
                }*/
            }
        };


        if (mp != null){

            mp.stop();
            mp.release();
        }



        Intent i = getIntent();
        b = i.getExtras();
        mysongs = (ArrayList) b.getParcelableArrayList("songlist");
        position = b.getInt("pos",0);

        u = Uri.parse(mysongs.get(position).toString());
        mp = MediaPlayer.create(getApplicationContext(),u);

        mp.start();
        sb.setMax(mp.getDuration());


        updateSeekBar.start();

        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mp.seekTo(seekBar.getProgress());
            }
        });

        btPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mp.isPlaying()){
                    //btPlay.setText("Pause");
                    btPlay.setBackgroundResource(R.drawable.play);
                    mp.pause();

                }else{
                    //btPlay.setText("Play");
                    btPlay.setBackgroundResource(R.drawable.pause);
                    mp.start();
                }
            }
        });




        btFF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mp.seekTo(mp.getCurrentPosition() + 5000);
            }
        });
        btFB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mp.seekTo(mp.getCurrentPosition()-5000);
            }
        });

        btNxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mp.stop();
                mp.release();
                position = position +1;
                position = position % (mysongs.size());

                u = Uri.parse(mysongs.get(position).toString());
                mp = MediaPlayer.create(getApplicationContext(),u);
                //btPlay.setText("Play");
                mp.start();
                sb.setMax(mp.getDuration());


            }
        });

        btPv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mp.stop();
                mp.release();

                if (position -1 < 0){
                    position = (mysongs.size())-1;
                }else {
                    position = position -1;
                }

                u = Uri.parse(mysongs.get(position).toString());
                mp =MediaPlayer.create(getApplicationContext(),u);
                //btPlay.setText("Play");
                mp.start();
                sb.setMax(mp.getDuration());
            }
        });

    }
}
