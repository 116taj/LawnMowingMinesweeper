package com.example.randhawaminesweeper;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class StoryBegin extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.beginstory);
        getvalue();
    }
    //get best time
    public void getvalue (){
        try {
            FileInputStream in = openFileInput("highscore.txt");
            int high = in.read();
            if (high < 0){
                FileOutputStream out = openFileOutput("highscore.txt",MODE_PRIVATE);
                high = 99999;
                out.write(high);
                out.flush();
                out.close();
            }
            in.close();
            TextView time = (TextView)findViewById(R.id.timer);
            time.setText("Best time: " + high);
        } catch (IOException e){
            Toast.makeText(getApplicationContext(),""+e,Toast.LENGTH_SHORT).show();
        }
    }
    //starts story mode and resets time if player quit before, also writes name to use at end
    public void play (View view) throws IOException {
        Intent play = new Intent (this, StoryEasy.class);
        FileInputStream in = openFileInput("score.txt");
        int time = in.read();
        in.close();
        if (time > 0){
            FileOutputStream out = openFileOutput("score.txt",MODE_PRIVATE);
            out.write(0);
            out.flush();
            out.close();
        }
        FileOutputStream out = openFileOutput("name.txt",MODE_PRIVATE);
        EditText edit = (EditText)findViewById(R.id.name);
        String x = edit.getText().toString();
        out.write(x.length());
        for (int i = 0; i < x.length(); i++){
            out.write(x.charAt(i));
        }
        out.flush();
        out.close();
        startActivity(play);
    }
    public void back (View view){
        Intent back = new Intent (this, Title.class);
        startActivity(back);
    }

}