import javax.swing.*;
import java.util.*;
import java.awt.*;

public class Cell {

    public int size_cell = 50;
    boolean clickable = true;
    int neighbour = -1;
    boolean flagged =  false;

    public Cell() {
        this.clickable = true;
        this.flagged = false;
    }

    public int getSize_cell() {
        return size_cell;
    }

    public boolean isExploaded() {
        return false;
    }

    public void clicked(Graphics g, int x, int y){

    }

    public void flag(Graphics g, int x, int y){
        if (flagged == true){
            g.setColor(Color.pink);
        }
    }

    public int getNeighbour() {
        return this.neighbour;
    }

    public void setClickable(boolean clickable) {
        this.clickable = clickable;
    }

    public void setExploded(boolean exploded) {
    }

    public void setFlagged(boolean flagged) {
        this.flagged = flagged;
    }

    public void setNeighbour(int neighbour) {
    }

    public boolean isClickable() {
        return clickable;
    }

    public boolean isFlagged() {
        return flagged;
    }
}
