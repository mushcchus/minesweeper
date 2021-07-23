import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.MouseInfo;

public class Board extends JPanel {

    int size_cell = 30;
    int spacing = 3;
    int[] map_Info;
    public int width;
    public int height;
    public double mx;
    public double my;
    int count = 0;
    int[] clicks = new int[3];
    Difficulty difficulty = Difficulty.Expert;
    int displayedMine;

    Random random = new Random();
    EmptyCell emptyCell = new EmptyCell();
    BombCell bombCell = new BombCell();
    Cell[][] field;
    boolean defeat = false;
    Date startDate = new Date();
    int sec;
    int pause;
    int flagged;
    boolean victory = false;

    public void getDiff() {
        if (difficulty == Difficulty.Beginner) {
            map_Info = new int[]{8, 8, 10};
        } else if (difficulty == Difficulty.Intermediate) {
            map_Info = new int[]{16, 16, 40};
        } else if (difficulty == Difficulty.Expert) {
            map_Info = new int[]{16, 30, 99};
        }
        width = size_cell * map_Info[1] + 16;
        height = size_cell * map_Info[0] + 70;
    }

    public void map_Bombs() {
        field = new Cell[map_Info[1]][map_Info[0]];
            for (int i = 0; i < map_Info[1]; i++) {
                for (int j = 0; j < map_Info[0]; j++) {
                    EmptyCell emptyCel = new EmptyCell();
                    field[i][j] = emptyCel;
                    field[i][j].setClickable(true);
                }
            }


            for (int b = 0; b < map_Info[2]; b++) {
                int x = random.nextInt(map_Info[1]);
                int y = random.nextInt(map_Info[0]);

                if (field[x][y].getNeighbour() != -1 ) {
                    BombCell bombCel = new BombCell();
                    field[x][y] = bombCel;
                } else if (field[x][y].getNeighbour() == -1) {
                    b--;
                }
            }
    }

    public void mapAdjust() {
        if (clicks[0]  == 1 && field[clicks[1]][clicks[2]].getNeighbour() == -1) {
            field[clicks[1]][clicks[2]] = emptyCell;
            field[clicks[1]][clicks[2]].setClickable(false);
            BombCell bombCel = new BombCell();

            for (int b = 0; b < 1; b++) {
                    int x = random.nextInt(map_Info[1]);
                    int y = random.nextInt(map_Info[0]);
                    if (field[x][y].getNeighbour() != -1 ) {
                        field[x][y] = bombCel;
                    } else if (field[x][y].getNeighbour() == -1) {
                    b--;
                }
            }
        }
    }


    public void paintComponent(Graphics g) {

        mx = MouseInfo.getPointerInfo().getLocation().getX();
        my = MouseInfo.getPointerInfo().getLocation().getY();

        getDiff();

        g.setColor(Color.lightGray);
        g.fillRect(0, 0, width - 16, height - 39);
        mapAdjust();

        g.setColor(Color.darkGray);
        g.fillRect(width-8-155, height - 70, 46,28);
        g.fillRect(width-8-105, height - 70, 46,28);
        g.fillRect(width-8-55, height - 70, 46,28);
        g.setColor(Color.pink);
        g.setFont(new Font("Tahoma", Font.ITALIC, 25));
        g.drawString("BEG", width-8-155, height - 45);
        g.drawString("INT", width-8-105, height - 45);
        g.drawString("EXP", width-8-55, height - 45);


        for (int i = 0; i < map_Info[1]; i++) {
            for (int j = 0; j < map_Info[0]; j++) {
                g.setColor(Color.darkGray);
                nrOfNeighbour(i, j);

                /* if u want to see the bombs
                if (field[i][j].getClass() == bombCell.getClass()) {
                    g.setColor(Color.yellow);
                }*/


                if (!field[i][j].clickable) {
                    g.setColor(Color.white);
                }

                if (field[i][j].isFlagged()) {
                    field[i][j].flag(g, i * size_cell + spacing + 9, size_cell * j + size_cell - 15);

                }

                if (mx >= spacing + i * size_cell && mx < spacing + i * size_cell + size_cell && my >= spacing + j * size_cell + 26 && my < spacing + j * size_cell + size_cell + 26) {
                    g.setColor(Color.orange);
                }
                g.fillRect(i * size_cell + spacing, spacing + j * size_cell, size_cell - 2 * spacing, size_cell - 2 * spacing);

                if (!field[i][j].clickable) {
                    field[i][j].clicked(g, i * size_cell + spacing + 6, size_cell * j + size_cell - 9);
                    expands(i, j);
                }

                if (field[i][j].isExploaded()) {
                    defeat = true;
                    g.setColor(Color.pink);
                    g.setFont(new Font("Tahoma", Font.ITALIC, 30));
                    g.drawString("You lost", 15, height - 12);
                }
            }
        }

        if (!defeat && !victory){
            if (count > 0) {
                sec = (int) ((new Date().getTime() - startDate.getTime()) / 1000 - pause);
            } else if (count > 0 && (victory == false && defeat == false)) {
                sec = sec;
            } else {
                sec = 0;
                pause = (int) ((new Date().getTime() - startDate.getTime()) / 1000);
            }
        }

        if (victory == true) {
            g.setColor(Color.pink);
            g.setFont(new Font("Tahoma", Font.ITALIC, 30));
            g.drawString("You won", 15, height - 12);
        }

        g.setColor(Color.gray);
        g.fillRect(width - 80, height - 33, 60, 30);
        g.fillRect(width-120, height - 33, 30, 30);


        displayedMine = map_Info[2]-flagged;
        g.setColor(Color.pink);
        g.setFont(new Font("Tahoma", Font.PLAIN, 25));
        g.drawString(Integer.toString(displayedMine), width-120, height -6);

        if (sec < 10) {
            g.drawString("000" + Integer.toString(sec), width - 80, height - 6);
        } else if (sec < 100) {
            g.drawString("00" + Integer.toString(sec), width - 80, height - 6);
        } else if (sec < 1000) {
            g.drawString("0" + Integer.toString(sec), width - 80, height - 6);
        } else {
            g.drawString(Integer.toString(sec), width - 80, height - 6);
        }

    }



    public void expands(int i, int j){
        nrOfNeighbour(i, j);
        if (field[i][j].getNeighbour() == 0) {
            for (int m = 0; m < map_Info[1]; m++) {
                for (int n = 0; n < map_Info[0]; n++) {
                    if (i - m < 2 && i - m > -2 && j - n < 2 && j - n > -2 && field[m][n].getNeighbour() == 0 ) {
                        if (m + 1 < map_Info[1]) {
                            field[m + 1][n].clickable = false;
                        }
                        if (n + 1 < map_Info[0]) {
                            field[m][n + 1].clickable = false;
                        }
                        if (n - 1 >= 0) {
                            field[m][n - 1].clickable = false;
                        }
                        if (m - 1 >= 0) {
                            field[m - 1][n].clickable = false;
                        }
                        if (m + 1 < map_Info[1] && n + 1 < map_Info[0] ){
                            field[m+1][n+1].clickable = false;
                        }
                        if (m - 1 >= 0 && n - 1 >= 0) {
                            field[m - 1][n - 1].clickable = false;
                        }
                        if (m + 1 < map_Info[1] && n - 1 >= 0) {
                            field[m+1][n-1].clickable = false;
                        }
                        if (m -1 >=0 && n + 1 < map_Info[0]) {
                            field[m-1][n+1].clickable = false;
                        }
                    }
                }
            }
        }
    }

    public void defeat(){
        for (int i = 0; i < map_Info[1]; i++) {
            for (int j = 0; j < map_Info[0]; j++) {
                if (field[i][j].isExploaded()) {
                    defeat = true;
                }
            }
        }
    }
    public void discovered(){
        int discovered = 0;
        for (int i = 0; i < map_Info[1]; i++) {
            for (int j = 0; j < map_Info[0]; j++) {
                if (field[i][j].clickable== false) {
                    discovered = discovered+1;
                }
                if (discovered == map_Info[0]*map_Info[1]-map_Info[2]){
                    victory = true;
                }
            }
        }
    }

    public int inBoxX() {
        for (int i = 0; i < map_Info[1]; i++) {
            for (int j = 0; j < map_Info[0]; j++) {
                if (mx >= spacing+i*size_cell && mx < spacing+i*size_cell+size_cell && my >= spacing+j*size_cell +26 && my <spacing+j*size_cell +size_cell +26) {
                    return i;
                }
            }
        }
        return -1;
    }

    public int inBoxY() {
        for (int i = 0; i < map_Info[1]; i++) {
            for (int j = 0; j < map_Info[0]; j++) {
                if (mx >= spacing+i*size_cell && mx < spacing+i*size_cell+size_cell && my >= spacing+j*size_cell +26 && my <spacing+j*size_cell +size_cell +26){
                    return j;
                }
            }
        }
        return -1;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int[] getMap_Info() {
        return map_Info;
    }

    public void nrOfNeighbour(int i, int j){
        int neighs = 0;
        for (int m = 0; m < map_Info[1]; m++) {
            for (int n = 0; n < map_Info[0]; n++) {
                if (!(i == m && j == n)) {
                    if (i-m < 2 && i - m > -2 && j-n < 2 && j - n >-2 && field[m][n].getNeighbour() == -1 ) {
                        neighs++;
                    }
                }
            }
        }
        field[i][j].setNeighbour(neighs);
    }

    public int getCount() {
        return count;
    }

    public Cell[][] getField() {
        return field;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getFlagged() {
        return flagged;
    }

    public void setFlagged(int flagged) {
        this.flagged = flagged;
    }

    public boolean isDefeat() {
        return defeat;
    }

    public boolean isVictory() {
        return victory;
    }

    public int[] getClicks() {
        return clicks;
    }



    public void inDiff(){
        if (my < height - 15  && my > height -39 && mx < width - 8-9 && mx >width-8-155) {
            if (mx > width-8-55 && mx < width - 8-9){
                difficulty = Difficulty.Expert;
                this.getDiff();
                this.map_Bombs();
                defeat=false;
                victory = false;
                count = 0;
                flagged = 0;
                clicks[0]=0;
            }else if ( mx > width-8-105 && mx < width - 8-59){
                difficulty = Difficulty.Intermediate;
                this.getDiff();
                this.map_Bombs();
                defeat=false;
                victory = false;
                count = 0;
                flagged = 0;
                clicks[0]=0;
            }else if (mx > width-8-155 && mx < width - 8-109){
                difficulty = Difficulty.Beginner;
                this.getDiff();
                this.map_Bombs();
                defeat=false;
                victory = false;
                count = 0;
                flagged = 0;
                clicks[0]=0;
            }
        }
    }

}

