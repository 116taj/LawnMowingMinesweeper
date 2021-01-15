package com.example.randhawaminesweeper;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class StoryEnd extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.endstory);
        getvalue();
    }
    //get the run time and best time, also name
    public void getvalue (){
        try {
            FileInputStream in = openFileInput("score.txt");
            int last  = in.read();
            in = openFileInput("highscore.txt");
            int high = in.read();
            in = openFileInput("name.txt");
            int l = in.read();
            String x = "";
            for (int i = 0; i < l; i++){
                int data = in.read();
                char c = (char) data;
                x+=c;
            }
            if (!x.equals("")){
                TextView name = (TextView)findViewById(R.id.name);
                name.setText("Congratulations "+x+ "!");
            }
            in.close();
            //check for new record
            if (last < high) {
                FileOutputStream out = openFileOutput("highscore.txt", MODE_PRIVATE);
                out.write(last);
                out.flush();
                out.close();
                Toast.makeText(getApplicationContext(),"New Record!!",Toast.LENGTH_SHORT).show();
            }
            TextView time = (TextView)findViewById(R.id.timer);
            time.setText("Your time: " + last);
            TextView time2 = (TextView)findViewById(R.id.besttimer);
            time2.setText("Best time: " + high);
        } catch (IOException e){
            Toast.makeText(getApplicationContext(),""+e,Toast.LENGTH_SHORT).show();
        }
    }
    public void play (View view) {
        Intent play = new Intent (this,  StoryBegin.class);
        startActivity(play);
    }
    public void back (View view){
        Intent back = new Intent (this, Title.class);
        startActivity(back);
    }

}