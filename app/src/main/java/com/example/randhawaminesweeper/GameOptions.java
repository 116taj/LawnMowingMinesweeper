package com.example.randhawaminesweeper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;


public class GameOptions extends AppCompatActivity {
    //object counts
    int minecount = 0;
    int weedcount = 0;
    int sprinkcount = 0;
    int repellentcount = 0;
    int deadcount = 0;
    int hivecount = 0;
    //size of grid selected
    int gridsize = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gamesettings);
        //read in current values
        try {
            FileInputStream in = openFileInput("count.txt");
            minecount = in.read();
            weedcount = in.read();
            repellentcount = in.read();
            sprinkcount = in.read();
            deadcount = in.read();
            hivecount = in.read();
            in.close();
            in = openFileInput("grid.txt");
            gridsize = in.read();
            in.close();
            ImageView pic = (ImageView)findViewById(R.id.s);
            if (gridsize == 36)
            pic.setImageResource(R.drawable.gridsmalls);
            pic = (ImageView) findViewById(R.id.m);
            if (gridsize == 64)
            pic.setImageResource(R.drawable.gridmeds);
            pic = (ImageView) findViewById(R.id.b);
            if (gridsize == 100)
            pic.setImageResource(R.drawable.gridbigs);
        } catch (IOException e){
            Toast.makeText(getApplicationContext(),e+"",Toast.LENGTH_SHORT).show();
        }
        //update edittexts with current values
        EditText a = (EditText) findViewById(R.id.mine);
        a.setText(minecount+"");
        EditText b = (EditText) findViewById(R.id.weed);
        b.setText(weedcount+"");
        EditText c = (EditText) findViewById(R.id.repel);
        c.setText(repellentcount+"");
        EditText d = (EditText) findViewById(R.id.sprinkler);
        d.setText(sprinkcount+"");
        EditText e = (EditText) findViewById(R.id.dead);
        e.setText(deadcount+"");
        EditText f = (EditText) findViewById(R.id.hive);
        f.setText(hivecount+"");
        Button back = (Button)findViewById(R.id.back);
        String where = getIntent().getStringExtra("sender");
        //setting button to continue if came from title
        if (where.equals("Title"))
            back.setText("Continue");
    }
    //back button
    public void back (View view){
        try {
            //writing values if the edit text isn't empty
            FileOutputStream out = openFileOutput("count.txt",MODE_PRIVATE);
            EditText mine = (EditText)findViewById(R.id.mine);
            EditText weed = (EditText)findViewById(R.id.weed);
            EditText repel = (EditText)findViewById(R.id.repel);
            EditText sprink = (EditText)findViewById(R.id.sprinkler);
            EditText dead = (EditText)findViewById(R.id.dead);
            EditText hive = (EditText)findViewById(R.id.hive);
            String temp = mine.getText().toString();
            if (!temp.equals(""))
            minecount = Integer.parseInt(temp);
            temp = weed.getText().toString();
            if (!temp.equals(""))
            weedcount = Integer.parseInt(temp);
            temp = repel.getText().toString();
            if (!temp.equals(""))
            repellentcount = Integer.parseInt(temp);
            temp = sprink.getText().toString();
            if (!temp.equals(""))
            sprinkcount = Integer.parseInt(temp);
            temp = dead.getText().toString();
            if (!temp.equals(""))
            deadcount = Integer.parseInt(temp);
            temp = hive.getText().toString();
            if (!temp.equals(""))
            hivecount = Integer.parseInt(temp);
            if (minecount + weedcount + repellentcount + sprinkcount + deadcount + hivecount > gridsize){
                Toast.makeText(getApplicationContext(),"The grid cannot contain all these values!",Toast.LENGTH_SHORT).show();
                return;
            }
            out.write(minecount);
            out.write(weedcount);
            out.write(repellentcount);
            out.write(sprinkcount);
            out.write(deadcount);
            out.write(hivecount);
            out.flush();
            out.close();
        } catch (IOException e){
            Toast.makeText(getApplicationContext(), ""+e,Toast.LENGTH_SHORT).show();
        }
        // chooses which class to go to bases on which was selected
        Intent game = new Intent(this,MainGame.class);
        Intent game2 = new Intent(this,MainGameSmall.class);
        Intent game3 = new Intent(this,MainGameLarge.class);
        if (gridsize == 36)
        startActivity(game2);
        else if (gridsize == 64)
        startActivity(game);
        else if (gridsize == 100)
        startActivity(game3);
        else {
            Toast.makeText(getApplicationContext(), "Select a grid size!",Toast.LENGTH_SHORT).show();
        }
    }
    //select small
    public void smallgrid(View view){
        try {
            FileOutputStream out = openFileOutput("grid.txt",MODE_PRIVATE);
            gridsize = 36;
            out.write(36);
            out.flush();
            out.close();
            ImageView pic = (ImageView)findViewById(R.id.s);
            pic.setImageResource(R.drawable.gridsmalls);
            pic = (ImageView) findViewById(R.id.m);
            pic.setImageResource(R.drawable.gridmed);
            pic = (ImageView) findViewById(R.id.b);
            pic.setImageResource(R.drawable.gridbig);
        } catch (IOException e){
            Toast.makeText(getApplicationContext(),e+"",Toast.LENGTH_SHORT).show();
        }
    }
    //select medium
    public void medgrid(View view){
        try {
            FileOutputStream out = openFileOutput("grid.txt",MODE_PRIVATE);
            gridsize = 64;
            out.write(64);
            out.flush();
            out.close();
            ImageView pic = (ImageView)findViewById(R.id.m);
            pic.setImageResource(R.drawable.gridmeds);
            pic = (ImageView) findViewById(R.id.s);
            pic.setImageResource(R.drawable.gridsmall);
            pic = (ImageView) findViewById(R.id.b);
            pic.setImageResource(R.drawable.gridbig);
        } catch (IOException e){
            Toast.makeText(getApplicationContext(),e+"",Toast.LENGTH_SHORT).show();
        }
    }
    //select big
    public void biggrid(View view){
        try {
            FileOutputStream out = openFileOutput("grid.txt",MODE_PRIVATE);
            gridsize = 100;
            out.write(100);
            out.flush();
            out.close();
            ImageView pic = (ImageView)findViewById(R.id.s);
            pic.setImageResource(R.drawable.gridsmall);
            pic = (ImageView) findViewById(R.id.m);
            pic.setImageResource(R.drawable.gridmed);
            pic = (ImageView) findViewById(R.id.b);
            pic.setImageResource(R.drawable.gridbigs);
        } catch (IOException e){
            Toast.makeText(getApplicationContext(),e+"",Toast.LENGTH_SHORT).show();
        }
    }
}
