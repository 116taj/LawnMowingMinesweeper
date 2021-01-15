package com.example.randhawaminesweeper;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.IOException;
//Name: Taj Randhawa
//Date: Jan 11 2020
//Purpose: To develop a mobile grid game for grade 6 students.
//Suggested comment reading:
// Title ->  MainGame -> MainGameSmall -> MainGameLarge -> GameOptions -> Instructions -> StoryBegin -> StoryEasy -> StoryMed -> StoryHard -> StoryEnd

public class Title extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.title);
        File f1 = new File(getFilesDir(),"score.txt");
        File f2 = new File(getFilesDir(),"name.txt");
        File f3 = new File(getFilesDir(),"highscore.txt");
        File f4 = new File(getFilesDir(),"grid.txt");
        File f5 = new File(getFilesDir(),"count.txt");
        File f6 = new File(getFilesDir(),"save.txt");
        try {
            f1.createNewFile();
            f2.createNewFile();
            f3.createNewFile();
            f4.createNewFile();
            f5.createNewFile();
            f6.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    // to freeplay mode
    public void freeplay(View view){
        Intent free = new Intent(this,GameOptions.class);
        //adding extra instead of a file because there is only 1 value going, which changes a button
        free.putExtra("sender","Title");
        startActivity(free);
    }
    // to story mode
    public void story(View view){
        Intent story = new Intent(this,StoryBegin.class);
        startActivity(story);
    }
    // to instructions
    public void instruct(View view){
        Intent ins = new Intent(this,Instructions.class);
        startActivity(ins);
    }
}
