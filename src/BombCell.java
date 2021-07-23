import java.awt.*;

public class BombCell extends Cell {

    boolean exploaded;



    public BombCell (){
        this.neighbour = -1;
    }

    @Override
    public void setNeighbour(int neighbour) {
        this.neighbour= -1;
    }

    @Override
    public boolean isExploaded() {
        return exploaded;
    }

    @Override
    public void setExploded(boolean exploaded) {
        super.setExploded(exploaded);
        this.exploaded = exploaded;
    }

    @Override
    public int getNeighbour() {
        return super.getNeighbour();
    }

    @Override
    public void clicked(Graphics g, int x, int y) {
        super.clicked(g, x, y);
        if (!clickable) {
            g.setColor(Color.pink);
            g.fillRect(x + 1, y - 13, 8, 16);
            g.fillRect(x - 2, y - 9, 16, 8);
            g.fillRect(x, y - 11, 12, 12);
            g.fillRect(x + 5, y - 15, 2, 20);
            g.fillRect(x - 4, y - 6, 20, 2);
            exploaded = true;
        }
    }


}
