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
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
    //NOTE: despite looking like maingame, there are differences which are noted
public class StoryEasy extends AppCompatActivity {
    int row = 6;
    int col = 6;
    ImageView pics[] = new ImageView[row * col];
    //3D array to store levels
    int levels[][][] =  {{{9, 0, 0, 0, 0, 9}, {0, 0, 0, 0, 0, 0}, {0, 0, 9, 9, 0, 0,},
        {0, 0, 9, 9, 0, 0,},  {0, 0, 0, 0, 0, 0,},{9, 0, 0, 0, 0, 9}},
            {{0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0,},
            {0, 0, 0, 0, 0, 0,},  {0, 0, 0, 0, 0, 0,},{0, 0, 0, 0, 0, 0}},
            {{11, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 11, 11, 0, 0,},
            {0, 0, 0, 0, 11, 11,},  {0, 0,0, 0, 0, 0,},{0, 0, 0, 0, 0, 0}}};
    // array to store randomized mines that are added per level
    int [] addmines = {0,7,5};
    //current level of zone 0 = first, 1 = second, etc
    int currentlevel = 0;
    int grid[][] = {{0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0,},
            {0, 0, 0, 0, 0, 0,},  {0, 0, 0, 0, 0, 0,},{0, 0, 0, 0, 0, 0}};
    int flag[][] ={{0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0,},
            {0, 0, 0, 0, 0, 0,},  {0, 0, 0, 0, 0, 0,},{0, 0, 0, 0, 0, 0}};
    int visible[][] ={{0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0,},
            {0, 0, 0, 0, 0, 0,},  {0, 0, 0, 0, 0, 0,},{0, 0, 0, 0, 0, 0}};
    int minecount = 0;
    int weedcount = 0;
    int flagcount = minecount;
    int placeflag = 0;
    int freeze = 0;
    int inittime = 0;
    int savetime = 0;
    int condition = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.storysmall);
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
        //update copy level # to grid
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                grid[i][j] = levels[currentlevel][i][j];
            }
        }
        minecount = addmines[currentlevel];
        flagcount = getflags();
        setmines(minecount);
        setweeds(weedcount);
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                setproximity(i, j);
                setweedprox(i,j);
            }
        }
        redraw();
        //timer is not reset ever on purpose
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
        //call dialog box
        level1();
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
    //new flagcount method required due to hardcoded mines, cycles through grid and looks for mines
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
        //resets just like oncreate
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
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                setproximity(i, j);
                setweedprox(i, j);
            }
        }
        condition = -1;
        flagcount = getflags();
        redraw();
    }
    public void next() {
        //to next level, similar to resrt
            placeflag = 0;
            freeze = 0;
            currentlevel++;
            minecount = addmines[currentlevel];
            ImageView flagpic = (ImageView) findViewById(R.id.flag);
            flagpic.setImageResource(R.drawable.uiflag);
            TextView levelnum = (TextView)findViewById(R.id.levelnum);
            //update level number
            levelnum.setText("Level: "+(currentlevel+1));
            for (int i = 0; i < row; i++) {
                for (int j = 0; j < col; j++) {
                    grid[i][j] = levels[currentlevel][i][j];
                    flag[i][j] = 0;
                    visible[i][j] = 0;
                }
            }
            setmines(minecount);
            setweeds(weedcount);
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
                level2();
            } else if (currentlevel == 1){
                level3();
            } else if (currentlevel == 2){
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
        }
    }
    //to next zone, writes time for this portion
    public void tomedium(View view) throws IOException {
        FileOutputStream out = openFileOutput("score.txt",MODE_PRIVATE);
        out.write(savetime);
        out.flush();
        out.close();
        Intent med = new Intent(this, StoryMed.class);
        startActivity(med);
    }
    public void back (View view){
        Intent back = new Intent(this, Title.class);
        startActivity(back);
    }
    //dialog boxes
    public void level1(){
        new AlertDialog.Builder(this)
                .setTitle("Level 1")
                .setMessage("The first lawn owner keeps his flowers in the same spot." +
                        "\nIf you mess up, you will know where to flag, so keep that in mind.")
                .setPositiveButton("Close", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which){

                    }
                }).show();
    }
    public void level2(){
        new AlertDialog.Builder(this)
                .setTitle("Level 2")
                .setMessage("Nice!" +
                        "\nThe next lawn's owner places their flowers randomly. Good luck!")
                .setPositiveButton("Close", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which){
                        next();
                    }
                }).show();
    }
    public void level3() {
        AlertDialog.Builder build = new AlertDialog.Builder(this);
                build.setTitle("Level 3");
                ImageView i = new ImageView(this);
                 i.setImageResource(R.drawable.weed);
                build.setView(i);
                build.setMessage("There's an issue with the next lawn. There's weeds everywhere!" +
                        "\nWeeds will confuse your robot and so it *might* give the wrong proximity.")
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
                        Button tomed = (Button)findViewById(R.id.zone);
                        tomed.setVisibility(View.VISIBLE);
                    }
                }).show();
    }
}
