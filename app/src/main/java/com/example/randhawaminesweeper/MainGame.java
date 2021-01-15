package com.example.randhawaminesweeper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.view.View;
import android.widget.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainGame extends AppCompatActivity {
    //number of rows and columns
    int row = 8;
    int col = 8;
    //images for the grid stored here
    ImageView pics[] = new ImageView[row * col];
    //initializing grid which will store proximity, mines and other objects
    int grid[][] = {{0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0}};
    //flag will store 0 or 1 depending on if flag is placed or not
    int flag[][] = {{0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0}};
    //visible will store 0 or 1 depending on if visible or not
    int visible[][] = {{0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0}};
    //repelremoved stores dead zones where weeds are killed and then updates grid after the recursive method finishes
    int repelremoved[][] = {{0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0}};
    //thing counts
    int minecount = 0;
    int weedcount = 0;
    int sprinkcount = 0;
    int repellentcount = 0;
    int deadcount = 0;
    int hivecount = 0;
    int flagcount = minecount;
    //placeflag stores if flag is placed 0 = no 1 = yes
    int placeflag = 0;
    //game is frozen, so no input
    int freeze = 0;
    //stores if time is begun
    int inittime = 0;
    //global time variable for saving/opening
    int savetime = 0;
    // win condition (-1,1,2,3)
    int condition = -1;
    //mediaplayer to play sound on mine click
    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.maingame);
        //initializing grid layout
        GridLayout g = (GridLayout) findViewById(R.id.grid);
        int k = 0;
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                pics[k] = new ImageView(this);
                setpic(pics[k], k);
                pics[k].setId(k);
                pics[k].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        gridclick(v.getId());
                    }
                });
                g.addView(pics[k]);
                k++;
            }
        }
        //initialize audio
        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.explosion);
        //read in values and set them onto the grid
        getvalues();
        flagcount = minecount;
        setmines(minecount);
        setweeds(weedcount);
        setsprinkler(sprinkcount);
        setrepel(repellentcount);
        setdead(deadcount);
        sethive(hivecount);
        //setting proximity and making dead zones visible
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                setproximity(i, j);
                setweedprox(i, j);
                if (grid[i][j] == 19)
                    visible[i][j] = 1;
            }
        }
        //redraw to make dead zones visible
        redraw();
        //timer overriding to make it only seconds
        Chronometer timer = (Chronometer) findViewById(R.id.chronometer);
        timer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer timer) {
                int s = 0;
                // t is in ms so dividing by 1000 will give value in seconds
                long t = SystemClock.elapsedRealtime() - timer.getBase();
                while (t >= 60000) {
                    t -= 60000;
                    s += 60;
                }
                while (t > 1000) {
                    t -= 1000;
                    s++;
                }
                savetime = s;
                if (s < 10)
                    timer.setText(": 00" + s);
                else if (s < 100)
                    timer.setText(": 0" + s);
                else
                    timer.setText(": " + s);
            }
        });
        timer.setText(": 000");
    }
    public void getvalues (){
        //reading in values and checking if it is greater than grid size (will be caught in infinite while loop)
        try {
            FileInputStream in = openFileInput("count.txt");
            minecount = in.read();
            weedcount = in.read();
            repellentcount = in.read();
            sprinkcount = in.read();
            deadcount = in.read();
            hivecount = in.read();
            in.close();
            if (minecount + weedcount + sprinkcount + repellentcount + deadcount + hivecount > row*col){
                 minecount = 0;
                 weedcount = 0;
                 sprinkcount = 0;
                 repellentcount = 0;
                 deadcount = 0;
                hivecount = 0;
                Toast.makeText(getApplicationContext(),"Error: # of objects > grid size.",Toast.LENGTH_SHORT).show();
            }
        } catch (IOException e){
            Toast.makeText(getApplicationContext(),e+"",Toast.LENGTH_SHORT).show();
        }
    }
    //setting images
    public void setpic(ImageView pic, int k) {
        int x = k / col;
        int y = k % col;
        // if visible and no flag show what it actually is
        if (visible[x][y] == 1 && flag[x][y] == 0) {
            switch (grid[x][y]) {
                case 1:
                    pic.setImageResource(R.drawable.one);
                    break;
                case 2:
                    pic.setImageResource(R.drawable.two);
                    break;
                case 3:
                    pic.setImageResource(R.drawable.three);
                    break;
                case 4:
                    pic.setImageResource(R.drawable.four);
                    break;
                case 5:
                    pic.setImageResource(R.drawable.five);
                    break;
                case 6:
                    pic.setImageResource(R.drawable.six);
                    break;
                case 7:
                    pic.setImageResource(R.drawable.seven);
                    break;
                case 8:
                    pic.setImageResource(R.drawable.eight);
                    break;
                case 9:
                    //get a random image
                    getmine(pic);
                    break;
                case 10:
                    pic.setImageResource(R.drawable.mineblack);
                    break;
                case 11:
                    pic.setImageResource(R.drawable.weed);
                    break;
                case 12:
                    pic.setImageResource(R.drawable.emptysprinkler);
                    break;
                case 13:
                    pic.setImageResource(R.drawable.wetmine);
                    break;
                case 14:
                    pic.setImageResource(R.drawable.sprinkler);
                    break;
                case 15:
                    pic.setImageResource(R.drawable.wetweed);
                    break;
                case 16:
                    pic.setImageResource(R.drawable.killer);
                    break;
                case 17:
                    pic.setImageResource(R.drawable.hive);
                    break;
                case 18:
                    pic.setImageResource(R.drawable.honeymine);
                    break;
                case 19:
                    pic.setImageResource(R.drawable.deadzone);
                    break;
                case 20:
                    pic.setImageResource(R.drawable.honeyhive);
                    break;
                default:
                    pic.setImageResource(R.drawable.empty);
            }
            //shows flag if not visible
        } else if (flag[x][y] == 1)
            pic.setImageResource(R.drawable.flag);
        //if nothing then show nothing at all
        else {
            pic.setImageResource(R.drawable.grass);
        }
    }
    //gets a random mine color
    public void getmine(ImageView pic) {
        int x = (int) (Math.random() * 5);
        switch (x) {
            case 0:
                pic.setImageResource(R.drawable.minered);
                break;
            case 1:
                pic.setImageResource(R.drawable.mineblue);
                break;
            case 2:
                pic.setImageResource(R.drawable.minepurple);
                break;
            case 3:
                pic.setImageResource(R.drawable.minepink);
                break;
            default:
                pic.setImageResource(R.drawable.minewhite);
        }
    }
    //sets mines randomly
    public void setmines(int mines) {
        for (int i = 0; i < mines; i++) {
            int x = (int) (Math.random() * row);
            int y = (int) (Math.random() * col);
            while (grid[x][y] != 0) {
                x = (int) (Math.random() * row);
                y = (int) (Math.random() * col);
            }
            grid[x][y] = 9;
        }
    }
    //sets proximity by counting them
    public void setproximity(int x, int y) {
        int count = 0;
        if (grid[x][y] < 10) {
            if (x + 1 < row && grid[x + 1][y] == 9)
                count++;
            if (x - 1 >= 0 && grid[x - 1][y] == 9)
                count++;
            if (y + 1 < col && grid[x][y + 1] == 9)
                count++;
            if (y - 1 >= 0 && grid[x][y - 1] == 9)
                count++;
            if (x + 1 < row && y + 1 < col && grid[x + 1][y + 1] == 9)
                count++;
            if (x + 1 < row && y - 1 >= 0 && grid[x + 1][y - 1] == 9)
                count++;
            if (x - 1 >= 0 && y + 1 < col && grid[x - 1][y + 1] == 9)
                count++;
            if (x - 1 >= 0 && y - 1 >= 0 && grid[x - 1][y - 1] == 9)
                count++;
            if (grid[x][y] != 9)
                grid[x][y] = count;
        }
    }
    //sets weed proximity, similar to normal proximity but randomizes it if there is 2 weeds or more nearby
    public void setweedprox(int x, int y) {
        int count = 0;
        if (x + 1 < row && grid[x + 1][y] == 11)
            count++;
        if (x - 1 >= 0 && grid[x - 1][y] == 11)
            count++;
        if (y + 1 < col && grid[x][y + 1] == 11)
            count++;
        if (y - 1 >= 0 && grid[x][y - 1] == 11)
            count++;
        if (x + 1 < row && y + 1 < col && grid[x + 1][y + 1] == 11)
            count++;
        if (x + 1 < row && y - 1 >= 0 && grid[x + 1][y - 1] == 11)
            count++;
        if (x - 1 >= 0 && y + 1 < col && grid[x - 1][y + 1] == 11)
            count++;
        if (x - 1 >= 0 && y - 1 >= 0 && grid[x - 1][y - 1] == 11)
            count++;
        if (grid[x][y] < 9 && count > 1)
            grid[x][y] = (int) (Math.random() * count);
    }
    //set weeds randomly
    public void setweeds(int weeds) {
        for (int i = 0; i < weeds; i++) {
            int x = (int) (Math.random() * row);
            int y = (int) (Math.random() * col);
            while (grid[x][y] > 8) {
                x = (int) (Math.random() * row);
                y = (int) (Math.random() * col);
            }
            grid[x][y] = 11;
        }
    }
    //set sprinklers randomly
    public void setsprinkler(int sprinkler) {
        for (int i = 0; i < sprinkler; i++) {
            int x = (int) (Math.random() * row);
            int y = (int) (Math.random() * col);
            while (grid[x][y] > 8) {
                x = (int) (Math.random() * row);
                y = (int) (Math.random() * col);
            }
            grid[x][y] = 12;
        }
    }
    //set repels randomly
    public void setrepel(int repel) {
        for (int i = 0; i < repel; i++) {
            int x = (int) (Math.random() * row);
            int y = (int) (Math.random() * col);
            while (grid[x][y] > 8) {
                x = (int) (Math.random() * row);
                y = (int) (Math.random() * col);
            }
            grid[x][y] = 16;
        }
    }
    //set deadzones randomly
    public void setdead(int dead) {
        for (int i = 0; i < dead; i++) {
            int x = (int) (Math.random() * row);
            int y = (int) (Math.random() * col);
            while (grid[x][y] > 8) {
                x = (int) (Math.random() * row);
                y = (int) (Math.random() * col);
            }
            grid[x][y] = 19;
        }
    }
    //set hives randomly
    public void sethive(int hive) {
        for (int i = 0; i < hive; i++) {
            int x = (int) (Math.random() * row);
            int y = (int) (Math.random() * col);
            while (grid[x][y] > 8) {
                x = (int) (Math.random() * row);
                y = (int) (Math.random() * col);
            }
            grid[x][y] = 17;
        }
    }
    //sprinkler method, if there is a weed, you lose
    //if there is a mine, disable it
    //in any case, reveal everything around it
    public void sprinkler(int x, int y) {
        int fail = 0;
        if (x+1 < row){
            if (grid[x+1][y] == 11){
                fail++;
                grid[x + 1][y] = 15;
            } else if (grid[x+1][y] == 9){
                grid[x + 1][y] = 13;
            }
            visible[x+1][y] = 1;
        } if (x-1 >= 0){
            if (grid[x-1][y] == 11){
                fail++;
                grid[x - 1][y] = 15;
            } else if (grid[x-1][y] == 9){
                grid[x - 1][y] = 13;
            }
            visible[x-1][y] = 1;
        } if (y+1 < col){
            if (grid[x][y+1] == 11){
                fail++;
                grid[x][y+1] = 15;
            } else if (grid[x][y+1] == 9){
                grid[x][y+1] = 13;
            }
            visible[x][y+1] = 1;
        } if (y-1 >= 0){
            if (grid[x][y-1] == 11){
                fail++;
                grid[x][y-1] = 15;
            } else if (grid[x][y-1] == 9){
                grid[x][y-1] = 13;
            }
            visible[x][y-1] = 1;
        } if (x+1 < row && y+1 < col){
            if (grid[x+1][y+1] == 11){
                fail++;
                grid[x + 1][y+1] = 15;
            } else if (grid[x+1][y+1] == 9){
                grid[x + 1][y+1] = 13;
            }
            visible[x+1][y+1] = 1;
        } if (x+1 < row && y-1 >= 0){
            if (grid[x+1][y-1] == 11){
                fail++;
                grid[x + 1][y-1] = 15;
            } else if (grid[x+1][y-1] == 9){
                grid[x + 1][y-1] = 13;
            }
            visible[x+1][y-1] = 1;
        } if (x-1 >= 0 && y+1 < col){
            if (grid[x-1][y+1] == 11){
                fail++;
                grid[x - 1][y+1] = 15;
            } else if (grid[x+1][y+1] == 9){
                grid[x - 1][y+1] = 13;
            }
            visible[x-1][y+1] = 1;
        } if (x-1 >= 0 && y-1 >= 0){
            if (grid[x-1][y-1] == 11){
                fail++;
                grid[x -1][y-1] = 15;
            } else if (grid[x-1][y-1] == 9){
                grid[x - 1][y-1] = 13;
            }
            visible[x-1][y-1] = 1;
        }
        grid[x][y] = 14;
        if (fail > 0) {
            //lose condition is met, freeze game, set condition, stop timer
            freeze = 1;
            condition = 3;
            revealgrid();
            Chronometer timer = (Chronometer) findViewById(R.id.chronometer);
            timer.stop();
            showcondition();
        }
    }
    //repel method, somewhat recursive but stopped early to balance
    public void repel(int x, int y, int recursed) {
        //if its not a weed or repellent or it has been recursed more than once, stop.
        if ((grid[x][y] != 11 && grid[x][y] != 16) || recursed > 1)
            return;
        // if its a weed, note that in repelremoved, make it visible and recurse
        if (x + 1 < row && grid[x + 1][y] == 11) {
            repelremoved[x + 1][y] = 19;
            visible[x + 1][y] = 1;
            repel(x + 1, y,recursed+1);
        }
        if (x - 1 >= 0 && grid[x - 1][y] == 11) {
            visible[x - 1][y] = 1;
            repelremoved[x - 1][y] = 19;
            repel(x - 1, y,recursed+1);
        }
        if (y + 1 < col && grid[x][y + 1] == 11) {
            visible[x][y + 1] = 1;
            repelremoved[x][y + 1] = 19;
            repel(x, y + 1,recursed+1);
        }
        if (y - 1 >= 0 && grid[x][y - 1] == 11) {
            visible[x][y - 1] = 1;
            repelremoved[x][y - 1] = 19;
            repel(x, y - 1,recursed+1);
        }
        if (x + 1 < row && y + 1 < col && grid[x + 1][y + 1] == 11) {
            visible[x + 1][y + 1] = 1;
            repelremoved[x + 1][y + 1] = 19;
            repel(x + 1, y + 1,recursed+1);
        }
        if (x + 1 < row && y - 1 >= 0 &&  grid[x + 1][y - 1] == 11) {
            visible[x + 1][y - 1] = 1;
            repelremoved[x + 1][y - 1] = 19;
            repel(x + 1, y - 1,recursed+1);
        }
        if (x - 1 >= 0 && y + 1 < col &&  grid[x - 1][y + 1] == 11) {
            visible[x - 1][y + 1] = 1;
            repelremoved[x - 1][y + 1] = 19;
            repel(x - 1, y + 1,recursed+1);
        }
        if (x - 1 >= 0 && y - 1 >= 0 && grid[x - 1][y - 1] == 11) {
            repelremoved[x - 1][y - 1] = 19;
            visible[x - 1][y - 1] = 1;
            repel(x - 1, y - 1,recursed+1);
        }
    }
    //hive method
    public void hive(){
        int c = 5;
        for (int i = 0; i < row; i++){
            for (int j = 0; j < col; j++){
                if (c == 0)
                    return;
                //if its a mine turn it into a honey mine and make it visible
                if (grid[i][j] == 9){
                    c--;
                    grid[i][j] = 18;
                    visible[i][j] = 1;
                }
            }
        }
    }
    //redraws grid by using setpic, updates flagcount and checks if win
    public void redraw() {
        int k = 0;
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                setpic(pics[k], k);
                k++;
            }
        }
        TextView flagview = (TextView) findViewById(R.id.flagnum);
        flagview.setText(": " + flagcount);
        win();
    }
    //gridclick occurs when you click a position on the grid
    public void gridclick(int k) {
        if (inittime == 0) {
            // if time hasn't started, start it
            Chronometer timer = (Chronometer) findViewById(R.id.chronometer);
            timer.setBase(SystemClock.elapsedRealtime());
            timer.start();
            inittime = 1;
            // if game isn't frozen get x and y
        } if (freeze == 0) {
            int x = k / col;
            int y = k % col;
            // if not placing flag open grid
            if (placeflag == 0) {
                opengrid(x, y);
                visible[x][y] = 1;
            }
            // placing flags if no flag and there is enough
            if (placeflag == 1 && flagcount > 0 && flag[x][y] == 0 && visible[x][y] == 0) {
                flag[x][y] = 1;
                visible[x][y] = 1;
                flagcount--;
                //removing flag
            } else if (flag[x][y] == 1 && placeflag == 1) {
                flag[x][y] = 0;
                visible[x][y] = 0;
                flagcount++;
                // out of flags
            } else if (placeflag == 1 && flagcount == 0) {
                visible[x][y] = 0;
                Toast.makeText(getApplicationContext(), "You are out of flags!", Toast.LENGTH_SHORT).show();
                //if sprinkler was clicked
            }else if (grid[x][y] == 12) {
                visible[x][y] = 1;
                sprinkler(x, y);
                //if repel is clicked
            } else if (grid[x][y] == 16) {
                visible[x][y] = 1;
                repel(x, y,0);
                //applying repelremoved changes to the grid
                for (int i = 0; i < row; i++){
                    for (int j = 0; j < col; j++){
                        if (repelremoved[i][j] == 19)
                            grid[i][j] = repelremoved[i][j];
                        repelremoved[i][j] = 0;
                    }
                }
                //if hive is clicked
            } else if (grid[x][y] == 17){
                visible[x][y] = 1;
                grid[x][y] = 20;
                hive();
                //if mine is clicked and no flag is there
            } else if (grid[x][y] == 9 && flag[x][y] == 0 && placeflag == 0) {
                //game over, freeze game and reveal the whole grid
                visible[x][y] = 1;
                freeze = 1;
                condition = 1;
                grid[x][y] = 10;
                revealgrid();
                mediaPlayer.start();
                Chronometer timer = (Chronometer) findViewById(R.id.chronometer);
                timer.stop();
                showcondition();
            } else
                //reveal the position
                visible[x][y] = 1;
            redraw();
            //redraw to update
        } else {
            //shows how game is over
            showcondition();
        }
    }
    //opening the grid
    public void opengrid(int x, int y) {
        if (grid[x][y] != 0) {
            return;
            //if its not visible,
        } if (x + 1 < row && visible[x + 1][y] == 0) {
            //check if its a weed or mine
            //then make it visible if it is not
            //if that position is empty, open the grid again
            if (grid[x+1][y] != 11 && grid[x+1][y] != 9)
            visible[x + 1][y] = 1;
            if (grid[x + 1][y] == 0) {
                opengrid(x + 1, y);
            }
        } if (x - 1 >= 0 && visible[x - 1][y] == 0) {
            if (grid[x-1][y] != 11 && grid[x-1][y] != 9)
            visible[x - 1][y] = 1;
            if (grid[x - 1][y] == 0) {
                opengrid(x - 1, y);
            }
        } if (y + 1 < col && visible[x][y + 1] == 0) {
            if (grid[x][y+1] != 11 && grid[x][y+1] != 9)
            visible[x][y + 1] = 1;
            if (grid[x][y + 1] == 0) {
                opengrid(x, y + 1);
            }
        } if (y - 1 >= 0 && visible[x][y - 1] == 0) {
            if (grid[x][y-1] != 11 && grid[x][y-1] != 9)
            visible[x][y - 1] = 1;
            if (grid[x][y - 1] == 0) {
                opengrid(x, y - 1);
            }
        } if (x + 1 < row && y + 1 < col && visible[x + 1][y + 1] == 0) {
            if (grid[x+1][y+1] != 11 && grid[x+1][y+1] != 9)
            visible[x + 1][y + 1] = 1;
            if (grid[x + 1][y + 1] == 0) {
                opengrid(x + 1, y + 1);
            }
        } if (x + 1 < row && y - 1 >= 0 && visible[x + 1][y - 1] == 0) {
            if (grid[x+1][y-1] != 11 && grid[x+1][y-1] != 9)
            visible[x + 1][y - 1] = 1;
            if (grid[x + 1][y - 1] == 0) {
                opengrid(x + 1, y - 1);
            }
        } if (x - 1 >= 0 && y + 1 < col && visible[x - 1][y + 1] == 0) {
            if (grid[x-1][y+1] != 11 && grid[x-1][y+1] != 9)
            visible[x - 1][y + 1] = 1;
            if (grid[x - 1][y + 1] == 0) {
                opengrid(x - 1, y + 1);
            }
        } if (x - 1 >= 0 && y - 1 >= 0 && visible[x - 1][y - 1] == 0) {
            if (grid[x-1][y-1] != 11 && grid[x-1][y-1] != 9)
            visible[x - 1][y - 1] = 1;
            if (grid[x - 1][y - 1] == 0) {
                opengrid(x - 1, y - 1);
            }
        }
    }
    //revels the whole grid
    public void revealgrid() {
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                visible[i][j] = 1;
            }
        }
    }
    //toggles the flag and updates UI
    public void toggleflag(View view) {
        ImageView flagpic = (ImageView) findViewById(R.id.flag);
        if (placeflag == 1) {
            placeflag = 0;
            flagpic.setImageResource(R.drawable.uiflag);
        } else {
            placeflag = 1;
            flagpic.setImageResource(R.drawable.uiyesflag);
        }
    }
    //resets global variables, timer, and flag in UI.
    public void reset(View view) {
        placeflag = 0;
        freeze = 0;
        flagcount = minecount;
        inittime = 0;
        ImageView flagpic = (ImageView) findViewById(R.id.flag);
        flagpic.setImageResource(R.drawable.uiflag);
        Chronometer timer = (Chronometer) findViewById(R.id.chronometer);
        timer.stop();
        timer.setBase(SystemClock.elapsedRealtime());
        timer.setText(": 000");
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                grid[i][j] = 0;
                flag[i][j] = 0;
                visible[i][j] = 0;
            }
        }
        //then reinitialize the grid just like onCreate
        setmines(minecount);
        setweeds(weedcount);
        setsprinkler(sprinkcount);
        setrepel(repellentcount);
        setdead(deadcount);
        sethive(hivecount);
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                setproximity(i, j);
                setweedprox(i, j);
                if (grid[i][j] == 19)
                    visible[i][j] = 1;
            }
        }
        //reset condition and redraw
        condition = -1;
        redraw();
    }
    //win method
    public void win() {
        int count = 0;
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                //if its a mine and its a flag, or if its a disabled mine: up the count
                if (grid[i][j] == 9 && flag[i][j] == 1 || (grid[i][j] == 13 || grid[i][j] == 18))
                    count++;
                // if not visible, you did not win
                if (visible[i][j] == 0)
                    return;
            }
        }
        // if count is equal to minecount you win! stop timer and show
        if (count == minecount && condition == -1) {
            freeze = 1;
            condition = 2;
            Chronometer timer = (Chronometer) findViewById(R.id.chronometer);
            timer.stop();
            showcondition();
        }
    }
    // shows win/loss condition
    public void showcondition() {
        switch (condition) {
            case 1:
                Toast.makeText(getApplicationContext(), "Game over! You hit a mine!", Toast.LENGTH_SHORT).show();
                break;
            case 2:
                Toast.makeText(getApplicationContext(), "You won by clearing the mines!", Toast.LENGTH_SHORT).show();
                break;
            case 3:
                Toast.makeText(getApplicationContext(), "Game over! You wet a weed!", Toast.LENGTH_SHORT).show();
        }
    }
    //saves by writing all meaningful global variables
    public void save(View view) {
        try {
            FileOutputStream file = openFileOutput("save.txt", MODE_PRIVATE);
            for (int i = 0; i < row; i++) {
                for (int j = 0; j < col; j++) {
                    file.write(grid[i][j]);
                    file.write(flag[i][j]);
                    file.write(visible[i][j]);
                }
            }
            file.write(flagcount);
            file.write(placeflag);
            file.write(freeze);
            file.write(condition);
            file.write(savetime);
            file.flush();
            file.close();
        } catch (IOException e) {
            Toast.makeText(getApplicationContext(), e + "", Toast.LENGTH_SHORT).show();
        }
    }
    //saves by reading all meaningful global variables
    public void open(View view) {
        try {
            FileInputStream file = openFileInput("save.txt");
            for (int i = 0; i < row; i++) {
                for (int j = 0; j < col; j++) {
                    grid[i][j] = file.read();
                    flag[i][j] = file.read();
                    visible[i][j] = file.read();
                }
            }
            flagcount = file.read();
            placeflag = file.read();
            freeze = file.read();
            condition = file.read();
            savetime = file.read();
            Chronometer timer = (Chronometer) findViewById(R.id.chronometer);
            timer.setBase(SystemClock.elapsedRealtime() - savetime * 1000);
            if (savetime < 10)
                timer.setText(": 00" + savetime);
            else if (savetime < 100)
                timer.setText(": 0" + savetime);
            else
                timer.setText(": " + savetime);
            if (savetime > 0)
                inittime = 1;
            file.close();
            redraw();
        } catch (IOException e) {
            Toast.makeText(getApplicationContext(), e + "", Toast.LENGTH_SHORT).show();
        }
    }
    //go to title
    public void title (View view){
        Intent title = new Intent(this,Title.class);
        startActivity(title);
    }
    //go to instruct
    public void instruct (View view){
        Intent instruct = new Intent(this,Instructions.class);
        startActivity(instruct);
    }
    // go to settings, putting extra to update button
    public void settings (View view){
        Intent settings = new Intent(this,GameOptions.class);
        settings.putExtra("sender","Not");
        startActivity(settings);
    }

}