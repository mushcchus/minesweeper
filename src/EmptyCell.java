import java.awt.*;

public class EmptyCell extends Cell {

    boolean empty;
    int neighbour;


    public EmptyCell() {
        this.empty= true;
    }

    @Override
    public void setNeighbour(int neighbour) {
        super.setNeighbour(neighbour);
        this.neighbour=neighbour;
    }

    @Override
    public void clicked(Graphics g, int x, int y) {
        super.clicked(g, x, y);
        if (!clickable) {
            if (neighbour != 0) {
                g.setColor(Color.pink);
                g.setFont(new Font("Tahoma", Font.BOLD, 18));
                g.drawString(Integer.toString(neighbour), x, y);
            }
        }
    }

    @Override
    public int getNeighbour() {
        return neighbour;
    }

    @Override
    public void setExploded(boolean exploaded) {
        super.setExploded(exploaded);
    }
}
