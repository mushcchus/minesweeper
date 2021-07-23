import org.junit.Assert;
import org.junit.jupiter.api.Test;


class MainTest {

    BombCell bombCell = new BombCell();
    Board board = new Board();
    EmptyCell emptyCell = new EmptyCell();


    @Test
    void victory()  {
        board.getDiff();
        board.map_Bombs();
        int[] map_Info=board.getMap_Info();
        for (int i = 0; i < map_Info[1]; i++) {
            for (int j = 0; j < map_Info[0]; j++) {
                if (board.getField()[i][j].getClass() != bombCell.getClass()) {
                    board.getField()[i][j].setClickable(false);
                }
            }
        }
        board.discovered();
        Assert.assertTrue(board.isVictory());
    }

    @Test
    void gameOver()  {
        board.getDiff();
        board.map_Bombs();
        int[] map_Info=board.getMap_Info();
        int count = 0;
        while (count < 3) {
            for (int i = 0; i < map_Info[1]; i++) {
                for (int j = 0; j < map_Info[0]; j++) {
                    if (board.getField()[i][j].getClass() != emptyCell.getClass()) {
                        board.getField()[i][j].setExploded(true);
                        count++;
                    }
                }
            }
        }
        board.defeat();
        Assert.assertTrue(board.isDefeat());
    }


    @Test
    void neighbourTest() {
        bombCell.setNeighbour(8);
        Assert.assertEquals(-1,bombCell.getNeighbour());
    }

    @Test
    void initialMap(){

    }

    @Test
    void numberOfBombs(){
        board.getDiff();
        board.map_Bombs();
        int bombs =0;
        int[] map_Info=board.getMap_Info();
        for (int i = 0; i < map_Info[1]; i++) {
            for (int j = 0; j < map_Info[0]; j++) {
                if (board.getField()[i][j].getClass() == bombCell.getClass()) {
                    board.getField()[i][j].setExploded(true);
                    bombs ++;
                }
            }
        }
        Assert.assertEquals(board.map_Info[2],bombs);
    }


    int[] clicked = new int[2];

    int[] clickTheFirstBomb(){
        board.getDiff();
        board.map_Bombs();
        int[] map_Info=board.getMap_Info();
        for (int i = 0; i < map_Info[1]; i++) {
            for (int j = 0; j < map_Info[0]; j++) {
                if (board.getField()[i][j].getClass() == bombCell.getClass()) {
                    board.getField()[i][j].setClickable(false);
                    board.clicks[0] = 1;
                    board.clicks[1] = i;
                    board.clicks[2] = j;
                    board.mapAdjust();
                    clicked[0]=i;
                    clicked[1]=j;
                    return clicked;
                }
            }
        }
        return null;
    }

    @Test
    void boardSizeCheckX(){
        int counter = 0;
        board.getDiff();
        board.map_Bombs();
        int[] map_Info=board.getMap_Info();
        for (int i = 0; i < map_Info[1]; i++){
            counter ++;
        }
        Assert.assertEquals(map_Info[1],counter);
    }

    @Test
    void boardSizeCheckY(){
        int counter = 0;
        board.getDiff();
        board.map_Bombs();
        int[] map_Info=board.getMap_Info();
        for (int i = 0; i < map_Info[2]; i++){
            counter ++;
        }
        Assert.assertEquals(map_Info[2],counter);
    }

    @Test
    void moveBombs(){
        clickTheFirstBomb();
        Assert.assertFalse(board.getField()[clicked[0]][clicked[1]].getClass() == bombCell.getClass());
    }

}