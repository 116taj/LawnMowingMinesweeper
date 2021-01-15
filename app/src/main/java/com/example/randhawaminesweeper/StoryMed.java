package com.example.randhawaminesweeper;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
    //no comments here; read storyeasy for story changes
public class StoryMed extends AppCompatActivity {
    int row = 8;
    int col = 8;
    ImageView pics[] = new ImageView[row * col];
    int levels[][][] = {{{0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0}},
            {{16, 11, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0},
            {11, 0, 11, 11, 11, 11, 0, 0}, {0, 11, 11, 16, 16, 11, 11, 0}, {0, 0, 11, 11, 11, 11, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 11}, {0, 0, 0, 0, 0, 0, 11, 16}},
            {{0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 9, 9, 9, 0, 0}, {0, 0, 0, 9, 12, 9, 0, 0}, {0, 0, 0, 9, 9, 9, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0}},
            {{0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0},
            {19, 19, 19, 19, 19, 19, 19, 19}, {19, 19, 19, 19, 19, 19, 19, 19}, {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0}}};
    int [] addmines = {10,8,6,12};
    int currentlevel = 0;
    int grid[][] = {{0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0}};
    int flag[][] = {{0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0}};
    int visible[][] = {{0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0}};
    int repelremoved[][] = {{0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0}};
    int minecount = 0;
    int weedcount = 0;
    int sprinkcount = 0;
    int repellentcount = 0;
    int deadcount = 0;
    int hivecount = 0;
    int flagcount = minecount;
    int placeflag = 0;
    int freeze = 0;
    int inittime = 0;
    int savetime = 0;
    int condition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.storymed);
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
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                grid[i][j] = levels[currentlevel][i][j];
            }
        }
        minecount = addmines[currentlevel];
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
        flagcount = getflags();
        redraw();
        Chronometer timer = (Chronometer) findViewById(R.id.chronometer);
        timer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer timer) {
                int s = 0;
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
        level4();
    }
    public void setpic(ImageView pic, int k) {
        int x = k / col;
        int y = k % col;
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
        } else if (flag[x][y] == 1)
            pic.setImageResource(R.drawable.flag);
        else {
            pic.setImageResource(R.drawable.grass);
        }
    }
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
            freeze = 1;
            condition = 3;
            revealgrid();
            Chronometer timer = (Chronometer) findViewById(R.id.chronometer);
            timer.stop();
            showcondition();
        }
    }

    public void repel(int x, int y, int recursed) {
        if ((grid[x][y] != 11 && grid[x][y] != 16) || recursed > 1)
            return;
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
    public void hive(){
        int c = 5;
        for (int i = 0; i < row; i++){
            for (int j = 0; j < col; j++){
                if (c == 0)
                    return;
                if (grid[i][j] == 9){
                    c--;
                    grid[i][j] = 18;
                    visible[i][j] = 1;
                }
            }
        }
    }
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

    public void gridclick(int k) {
        if (inittime == 0) {
            Chronometer timer = (Chronometer) findViewById(R.id.chronometer);
            timer.setBase(SystemClock.elapsedRealtime());
            timer.start();
            inittime = 1;
        } if (freeze == 0) {
            int x = k / col;
            int y = k % col;
            if (placeflag == 0) {
                opengrid(x, y);
                visible[x][y] = 1;
            }
            if (placeflag == 1 && flagcount > 0 && flag[x][y] == 0 && visible[x][y] == 0) {
                flag[x][y] = 1;
                visible[x][y] = 1;
                flagcount--;
            } else if (flag[x][y] == 1 && placeflag == 1) {
                flag[x][y] = 0;
                visible[x][y] = 0;
                flagcount++;
            } else if (placeflag == 1 && flagcount == 0) {
                visible[x][y] = 0;
                Toast.makeText(getApplicationContext(), "You are out of flags!", Toast.LENGTH_SHORT).show();
            }else if (grid[x][y] == 12) {
                visible[x][y] = 1;
                sprinkler(x, y);
            } else if (grid[x][y] == 16) {
                visible[x][y] = 1;
                repel(x, y,0);
                for (int i = 0; i < row; i++){
                    for (int j = 0; j < col; j++){
                        if (repelremoved[i][j] == 19)
                            grid[i][j] = repelremoved[i][j];
                        repelremoved[i][j] = 0;
                    }
                }
            } else if (grid[x][y] == 17){
                visible[x][y] = 1;
                grid[x][y] = 20;
                hive();
            } else if (grid[x][y] == 9 && flag[x][y] == 0 && placeflag == 0) {
                visible[x][y] = 1;
                freeze = 1;
                condition = 1;
                grid[x][y] = 10;
                revealgrid();
                showcondition();
            } else
                visible[x][y] = 1;
            redraw();
        } else {
            showcondition();
        }
    }
    public void opengrid(int x, int y) {
        if (grid[x][y] != 0) {
            return;
        } if (x + 1 < row && visible[x + 1][y] == 0) {
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

    public void revealgrid() {
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                visible[i][j] = 1;
            }
        }
    }

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
    public int getflags(){
        int count = 0;
        for (int i = 0; i < row; i++){
            for (int j = 0; j < col; j++){
                if (grid[i][j] == 9)
                    count++;
            }
        }
        return count;
    }
    public void reset(View view) {
        placeflag = 0;
        freeze = 0;
        minecount = addmines[currentlevel];
        ImageView flagpic = (ImageView) findViewById(R.id.flag);
        flagpic.setImageResource(R.drawable.uiflag);
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                grid[i][j] = levels[currentlevel][i][j];
                flag[i][j] = 0;
                visible[i][j] = 0;
            }
        }
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
        condition = -1;
        flagcount = getflags();
        redraw();
    }
    public void next() {
            placeflag = 0;
            freeze = 0;
            currentlevel++;
            minecount = addmines[currentlevel];
            ImageView flagpic = (ImageView) findViewById(R.id.flag);
            flagpic.setImageResource(R.drawable.uiflag);
            TextView levelnum = (TextView)findViewById(R.id.levelnum);
            levelnum.setText("Level: "+(currentlevel+4));
            for (int i = 0; i < row; i++) {
                for (int j = 0; j < col; j++) {
                    grid[i][j] = levels[currentlevel][i][j];
                    flag[i][j] = 0;
                    visible[i][j] = 0;
                }
            }
            if (currentlevel == 2){
                sprinkcount = 1;
            }
            if (currentlevel == 3){
                sprinkcount = 0;
                deadcount = 10;
            }
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
                }
            }
            flagcount = getflags();
            condition = -1;
            redraw();
    }
    public void win() {
        int count = 0;
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (grid[i][j] == 9 && flag[i][j] == 1 || (grid[i][j] == 13 || grid[i][j] == 18))
                    count++;
                if (visible[i][j] == 0)
                    return;
            }
        }
        if (count == getflags() && condition == -1) {
            freeze = 1;
            condition = 2;
            if (currentlevel == 0){
                level5();
            } else if (currentlevel == 1){
                level6();
            } else if (currentlevel == 2){
                level7();
            } else {
                end();
            }
            showcondition();
        }
    }

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

    public void tohard(View view) throws IOException {
        FileInputStream in = openFileInput("score.txt");
        savetime = in.read() + savetime;
        in.close();
        FileOutputStream out = openFileOutput("score.txt",MODE_PRIVATE);
        out.write(savetime);
        out.flush();
        out.close();
        Intent hard = new Intent(this, StoryHard.class);
        startActivity(hard);
    }
    public void back (View view){
        Intent back = new Intent(this, Title.class);
        startActivity(back);
    }
    public void level4(){
        new AlertDialog.Builder(this)
                .setTitle("Level 4")
                .setMessage("Looks bigger doesn't it?" +
                        "\nYour robot works well enough it's been promoted to bigger lawns, this one with no weeds.")
                .setPositiveButton("Close", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which){

                    }
                }).show();
    }
    public void level5() {
        AlertDialog.Builder build = new AlertDialog.Builder(this);
        build.setTitle("Level 5");
        ImageView i = new ImageView(this);
        i.setImageResource(R.drawable.killer);
        build.setView(i);
        build.setMessage("More weeds?!" +
                "\nWeeds have infested the lawn again, but this time the owner placed some repellent on the lawn. They failed to realise you must spray it for it to work. \n Luckily, if you run it over, you can kill weeds nearby, and if they are more near that weed they will chain react to kill even more.")
                .setPositiveButton("Close", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        next();
                    }
                }).show();
    }
    public void level6() {
        AlertDialog.Builder build = new AlertDialog.Builder(this);
        build.setTitle("Level 6");
        ImageView i = new ImageView(this);
        i.setImageResource(R.drawable.sprinkler);
        build.setView(i);
        build.setMessage("Sprinklers are at this lawn, and they actually help you!" +
                "\nSprinklers reveal everything that's around them. It will water flowers too, disabling them entirely.")
                .setPositiveButton("Close", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        next();
                    }
                }).show();
    }
    public void level7() {
        AlertDialog.Builder build = new AlertDialog.Builder(this);
        build.setTitle("Level 7");
        ImageView i = new ImageView(this);
        i.setImageResource(R.drawable.deadzone);
        build.setView(i);
        build.setMessage("This lawn owner must hate their lawn. They've left dead spots everywhere!" +
                "\n No proximity will show on these deadzones. This is the last of this zone, so finish strong!")
                .setPositiveButton("Close", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        next();
                    }
                }).show();
    }
    public void end(){
        new AlertDialog.Builder(this)
                //The title on the Dialog
                .setTitle("Next Area!")
                //The message that will appear
                .setMessage("Nice work!" +
                        "\nNew lawns await. Press the \"next zone\" button to proceed.")
                .setPositiveButton("Close", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which){
                        Button tohard = (Button)findViewById(R.id.zone);
                        tohard.setVisibility(View.VISIBLE);
                    }
                }).show();
    }

}