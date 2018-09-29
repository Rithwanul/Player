package com.example.dico.musicalapplication;

import android.app.Activity;
import android.app.Application;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;


public class songs extends Activity {

    ListView lv;
    ArrayList<File> mySongs;

    String[] items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(getWindow().FEATURE_NO_TITLE);
        setContentView(R.layout.activity_songs);

        mySongs = findsongs(Environment.getExternalStorageDirectory());
        items = new String[mySongs.size()];
        lv = (ListView)findViewById(R.id.lvplayer);

        for (int i = 0; i<mySongs.size(); i++){
            //toast(mySongs.get(i).getName().toString());
                items[i] = mySongs.get(i).getName().toString()
                        .replace(".mp3","")
                        .replace(".wav","");
        }
        ArrayAdapter<String> adp = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_list_item_1,items);
        lv.setAdapter(adp);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                startActivity(new Intent(getApplicationContext(),Player.class)
                        .putExtra("pos",position)
                        .putExtra("songlist",mySongs));
            }
        });
    }

    public ArrayList<File> findsongs(File root){

        ArrayList<File> al = new ArrayList<File>();

        File[] files = root.listFiles();

        for (File singleFile: files){
            if (singleFile.isDirectory() && !singleFile.isHidden()){
                al.addAll(findsongs(singleFile));
            }else {
                if (singleFile.getName().endsWith(".mp3")){
                    al.add(singleFile);
                }
            }
        }


        return al;
    }
    public void toast(String text){
        Toast.makeText(getApplicationContext(),text,Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onBackPressed(){
        //Toast.makeText(getApplicationContext(),"Blocked",Toast.LENGTH_SHORT).show();
        //super.onBackPressed();

       /* new AlertDialog.Builder(songs.this)
                .setTitle("Exit")
                .setMessage("Want To Exit App !!")
                .setCancelable(true)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //android.os.Process.killProcess(android.os.Process.myPid());
                        //System.exit(1);
                        //finish();
                        //finishAffinity();
                        //QuitApplication();
                        //onDestroy();

                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).create().show();*/
    }
    private void QuitApplication(){

        int pid = android.os.Process.myPid();
        android.os.Process.killProcess(pid);
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);

    }
    @Override
    public void onDestroy(){


        super.onDestroy();

    }

}
