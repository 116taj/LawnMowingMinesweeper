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
public class StoryHard extends AppCompatActivity {
    int row = 10;
    int col = 10;
    ImageView pics[] = new ImageView[row * col];
    int levels[][][] = {{{0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 12, 11, 0, 0, 0}, {0, 0, 0, 11, 0, 0, 0, 11, 12, 0},
            {0, 0, 0, 0, 11, 12, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 12, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 12, 11, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 11, 11, 11, 0, 12, 0}, {0, 0, 0, 12, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 12, 11, 0, 0, 0, 0}},
            {{0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}},
            {{17, 0, 0, 0, 0, 0, 0, 0, 0, 17}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}}};
    int[] addmines = {12, 20, 30};
    int currentlevel = 0;
    int grid[][] = {{0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0}};
    int flag[][] = {{0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0}};
    int visible[][] = {{0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0}};
    int repelremoved[][] = {{0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0}};
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
        setContentView(R.layout.storylarge);
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
        level8();
    }

    public void setpic(ImageView pic, int k) {
        int x = k / col;
        int y = k % col;
        if (visible[x][y] == 1 && flag[x][y] == 0) {
            switch (grid[x][y]) {
                case 1:
                    pic.setImageResource(R.drawable.onebig);
                    break;
                case 2:
                    pic.setImageResource(R.drawable.twobig);
                    break;
                case 3:
                    pic.setImageResource(R.drawable.threebig);
                    break;
                case 4:
                    pic.setImageResource(R.drawable.fourbig);
                    break;
                case 5:
                    pic.setImageResource(R.drawable.fivebig);
                    break;
                case 6:
                    pic.setImageResource(R.drawable.sixbig);
                    break;
                case 7:
                    pic.setImageResource(R.drawable.sevenbig);
                    break;
                case 8:
                    pic.setImageResource(R.drawable.eightbig);
                    break;
                case 9:
                    getmine(pic);
                    break;
                case 10:
                    pic.setImageResource(R.drawable.mineblackbig);
                    break;
                case 11:
                    pic.setImageResource(R.drawable.weedbig);
                    break;
                case 12:
                    pic.setImageResource(R.drawable.emptysprinklerbig);
                    break;
                case 13:
                    pic.setImageResource(R.drawable.wetminebig);
                    break;
                case 14:
                    pic.setImageResource(R.drawable.sprinklerbig);
                    break;
                case 15:
                    pic.setImageResource(R.drawable.wetweedbig);
                    break;
                case 16:
                    pic.setImageResource(R.drawable.killerbig);
                    break;
                case 17:
                    pic.setImageResource(R.drawable.hivebig);
                    break;
                case 18:
                    pic.setImageResource(R.drawable.honeyminebig);
                    break;
                case 19:
                    pic.setImageResource(R.drawable.deadzonebig);
                    break;
                case 20:
                    pic.setImageResource(R.drawable.honeyhivebig);
                    break;
                default:
                    pic.setImageResource(R.drawable.emptybig);
            }
        } else if (flag[x][y] == 1)
            pic.setImageResource(R.drawable.flagbig);
        else {
            pic.setImageResource(R.drawable.grassbig);
        }
    }

    public void getmine(ImageView pic) {
        int x = (int) (Math.random() * 5);
        switch (x) {
            case 0:
                pic.setImageResource(R.drawable.mineredbig);
                break;
            case 1:
                pic.setImageResource(R.drawable.minebluebig);
                break;
            case 2:
                pic.setImageResource(R.drawable.minepurplebig);
                break;
            case 3:
                pic.setImageResource(R.drawable.minepinkbig);
                break;
            default:
                pic.setImageResource(R.drawable.minewhitebig);
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
        if (x + 1 < row) {
            if (grid[x + 1][y] == 11) {
                fail++;
                grid[x + 1][y] = 15;
            } else if (grid[x + 1][y] == 9) {
                grid[x + 1][y] = 13;
            }
            visible[x + 1][y] = 1;
        }
        if (x - 1 >= 0) {
            if (grid[x - 1][y] == 11) {
                fail++;
                grid[x - 1][y] = 15;
            } else if (grid[x - 1][y] == 9) {
                grid[x - 1][y] = 13;
            }
            visible[x - 1][y] = 1;
        }
        if (y + 1 < col) {
            if (grid[x][y + 1] == 11) {
                fail++;
                grid[x][y + 1] = 15;
            } else if (grid[x][y + 1] == 9) {
                grid[x][y + 1] = 13;
            }
            visible[x][y + 1] = 1;
        }
        if (y - 1 >= 0) {
            if (grid[x][y - 1] == 11) {
                fail++;
                grid[x][y - 1] = 15;
            } else if (grid[x][y - 1] == 9) {
                grid[x][y - 1] = 13;
            }
            visible[x][y - 1] = 1;
        }
        if (x + 1 < row && y + 1 < col) {
            if (grid[x + 1][y + 1] == 11) {
                fail++;
                grid[x + 1][y + 1] = 15;
            } else if (grid[x + 1][y + 1] == 9) {
                grid[x + 1][y + 1] = 13;
            }
            visible[x + 1][y + 1] = 1;
        }
        if (x + 1 < row && y - 1 >= 0) {
            if (grid[x + 1][y - 1] == 11) {
                fail++;
                grid[x + 1][y - 1] = 15;
            } else if (grid[x + 1][y - 1] == 9) {
                grid[x + 1][y - 1] = 13;
            }
            visible[x + 1][y - 1] = 1;
        }
        if (x - 1 >= 0 && y + 1 < col) {
            if (grid[x - 1][y + 1] == 11) {
                fail++;
                grid[x - 1][y + 1] = 15;
            } else if (grid[x + 1][y + 1] == 9) {
                grid[x - 1][y + 1] = 13;
            }
            visible[x - 1][y + 1] = 1;
        }
        if (x - 1 >= 0 && y - 1 >= 0) {
            if (grid[x - 1][y - 1] == 11) {
                fail++;
                grid[x - 1][y - 1] = 15;
            } else if (grid[x - 1][y - 1] == 9) {
                grid[x - 1][y - 1] = 13;
            }
            visible[x - 1][y - 1] = 1;
        }
        grid[x][y] = 14;
        if (fail > 0) {
            freeze = 1;
            condition = 3;
            revealgrid();
            showcondition();
        }
    }

    public void repel(int x, int y, int recursed) {
        if ((grid[x][y] != 11 && grid[x][y] != 16) || recursed > 1)
            return;
        if (x + 1 < row && grid[x + 1][y] == 11) {
            repelremoved[x + 1][y] = 19;
            visible[x + 1][y] = 1;
            repel(x + 1, y, recursed + 1);
        }
        if (x - 1 >= 0 && grid[x - 1][y] == 11) {
            visible[x - 1][y] = 1;
            repelremoved[x - 1][y] = 19;
            repel(x - 1, y, recursed + 1);
        }
        if (y + 1 < col && grid[x][y + 1] == 11) {
            visible[x][y + 1] = 1;
            repelremoved[x][y + 1] = 19;
            repel(x, y + 1, recursed + 1);
        }
        if (y - 1 >= 0 && grid[x][y - 1] == 11) {
            visible[x][y - 1] = 1;
            repelremoved[x][y - 1] = 19;
            repel(x, y - 1, recursed + 1);
        }
        if (x + 1 < row && y + 1 < col && grid[x + 1][y + 1] == 11) {
            visible[x + 1][y + 1] = 1;
            repelremoved[x + 1][y + 1] = 19;
            repel(x + 1, y + 1, recursed + 1);
        }
        if (x + 1 < row && y - 1 >= 0 && grid[x + 1][y - 1] == 11) {
            visible[x + 1][y - 1] = 1;
            repelremoved[x + 1][y - 1] = 19;
            repel(x + 1, y - 1, recursed + 1);
        }
        if (x - 1 >= 0 && y + 1 < col && grid[x - 1][y + 1] == 11) {
            visible[x - 1][y + 1] = 1;
            repelremoved[x - 1][y + 1] = 19;
            repel(x - 1, y + 1, recursed + 1);
        }
        if (x - 1 >= 0 && y - 1 >= 0 && grid[x - 1][y - 1] == 11) {
            repelremoved[x - 1][y - 1] = 19;
            visible[x - 1][y - 1] = 1;
            repel(x - 1, y - 1, recursed + 1);
        }
    }

    public void hive() {
        int c = 5;
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (c == 0)
                    return;
                if (grid[i][j] == 9) {
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
        }
        if (freeze == 0) {
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
            } else if (grid[x][y] == 12) {
                visible[x][y] = 1;
                sprinkler(x, y);
            } else if (grid[x][y] == 16) {
                visible[x][y] = 1;
                repel(x, y, 0);
                for (int i = 0; i < row; i++) {
                    for (int j = 0; j < col; j++) {
                        if (repelremoved[i][j] == 19)
                            grid[i][j] = repelremoved[i][j];
                        repelremoved[i][j] = 0;
                    }
                }
            } else if (grid[x][y] == 17) {
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
        }
        if (x + 1 < row && visible[x + 1][y] == 0) {
            if (grid[x + 1][y] != 11 && grid[x + 1][y] != 9)
                visible[x + 1][y] = 1;
            if (grid[x + 1][y] == 0) {
                opengrid(x + 1, y);
            }
        }
        if (x - 1 >= 0 && visible[x - 1][y] == 0) {
            if (grid[x - 1][y] != 11 && grid[x - 1][y] != 9)
                visible[x - 1][y] = 1;
            if (grid[x - 1][y] == 0) {
                opengrid(x - 1, y);
            }
        }
        if (y + 1 < col && visible[x][y + 1] == 0) {
            if (grid[x][y + 1] != 11 && grid[x][y + 1] != 9)
                visible[x][y + 1] = 1;
            if (grid[x][y + 1] == 0) {
                opengrid(x, y + 1);
            }
        }
        if (y - 1 >= 0 && visible[x][y - 1] == 0) {
            if (grid[x][y - 1] != 11 && grid[x][y - 1] != 9)
                visible[x][y - 1] = 1;
            if (grid[x][y - 1] == 0) {
                opengrid(x, y - 1);
            }
        }
        if (x + 1 < row && y + 1 < col && visible[x + 1][y + 1] == 0) {
            if (grid[x + 1][y + 1] != 11 && grid[x + 1][y + 1] != 9)
                visible[x + 1][y + 1] = 1;
            if (grid[x + 1][y + 1] == 0) {
                opengrid(x + 1, y + 1);
            }
        }
        if (x + 1 < row && y - 1 >= 0 && visible[x + 1][y - 1] == 0) {
            if (grid[x + 1][y - 1] != 11 && grid[x + 1][y - 1] != 9)
                visible[x + 1][y - 1] = 1;
            if (grid[x + 1][y - 1] == 0) {
                opengrid(x + 1, y - 1);
            }
        }
        if (x - 1 >= 0 && y + 1 < col && visible[x - 1][y + 1] == 0) {
            if (grid[x - 1][y + 1] != 11 && grid[x - 1][y + 1] != 9)
                visible[x - 1][y + 1] = 1;
            if (grid[x - 1][y + 1] == 0) {
                opengrid(x - 1, y + 1);
            }
        }
        if (x - 1 >= 0 && y - 1 >= 0 && visible[x - 1][y - 1] == 0) {
            if (grid[x - 1][y - 1] != 11 && grid[x - 1][y - 1] != 9)
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

    public int getflags() {
        int count = 0;
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
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
        TextView levelnum = (TextView) findViewById(R.id.levelnum);
        levelnum.setText("Level: " + (currentlevel + 7));
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                grid[i][j] = levels[currentlevel][i][j];
                flag[i][j] = 0;
                visible[i][j] = 0;
            }
        }
        if (currentlevel == 1) {
            deadcount = 5;
            hivecount = 3;
        }
        if (currentlevel == 2) {
            hivecount = 0;
            sprinkcount = 2;
            deadcount = 10;
            weedcount = 8;
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
            if (currentlevel == 0) {
                level9();
            } else if (currentlevel == 1) {
                level10();
            } else if (currentlevel == 2) {
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

    public void toend(View view) throws IOException {
        FileInputStream in = openFileInput("score.txt");
        savetime = in.read() + savetime;
        in.close();
        FileOutputStream out = openFileOutput("score.txt", MODE_PRIVATE);
        out.write(savetime);
        out.flush();
        out.close();
        Intent hard = new Intent(this, StoryEnd.class);
        startActivity(hard);
    }

    public void back(View view) {
        Intent back = new Intent(this, Title.class);
        startActivity(back);
    }

    public void level8() {
        AlertDialog.Builder build = new AlertDialog.Builder(this);
        ImageView i = new ImageView(this);
        i.setImageResource(R.drawable.wetweed);
        build.setView(i);
        build.setTitle("Level 8");
        build.setMessage("You've made it to the largest lawns of them all!" +
                "\nIf the testing goes well here, your robot will be able to work automatically! \n In this lawn, you'll see what happens when you a wet a weed...");
        build.setPositiveButton("Close", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        }).show();
    }

    public void level9() {
        AlertDialog.Builder build = new AlertDialog.Builder(this);
        build.setTitle("Level 9");
        ImageView i = new ImageView(this);
        i.setImageResource(R.drawable.honeyhive);
        build.setView(i);
        build.setMessage("This lawn owner loves bees so much they own their own hives!" +
                "\nThese bees pollinate flowers and drench them with honey. Up to 5 will be disabled by their honey, and the best part is they go from top-left to bottom-right, clearing even more potential areas! \n Why wouldn't you like bees after this? ")
                .setPositiveButton("Close", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        next();
                    }
                }).show();
    }

    public void level10() {
        AlertDialog.Builder build = new AlertDialog.Builder(this);
        build.setTitle("Level 10");
        ImageView i = new ImageView(this);
        i.setImageResource(R.drawable.uiflag);
        build.setView(i);
        build.setMessage("This is it. The final lawn. Weeds, sprinklers, deadzones, and hives all appear, along with LOTS of flowers." +
                "\nI'll tell you this much: There's a hive on the top corners. Use those wisely.")
                .setPositiveButton("Close", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        next();
                    }
                }).show();
    }

    public void end() {
        new AlertDialog.Builder(this)
                //The title on the Dialog
                .setTitle("The End")
                //The message that will appear
                .setMessage("Fantastic work!" +
                        "\nYou have mowed all the lawns! Press the \"end\" button to finish the adventure and see what happens to your revolutionary robot!")
                .setPositiveButton("Close", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Button tohard = (Button) findViewById(R.id.zone);
                        tohard.setVisibility(View.VISIBLE);
                    }
                }).show();
    }

}