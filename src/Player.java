import javax.swing.*;
import java.awt.event.*;
import java.util.*;
import java.awt.*;

public class Player extends JFrame{

    Board board = new Board();
    Click click = new Click();

    public Player(){

        this.setContentPane(board);
        this.addMouseListener(click);
        board.getDiff();
        board.map_Bombs();
       // board.count_next();
        this.setTitle("Minesweeper");
        this.setSize(board.getWidth(), board.getHeight() + 40);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setResizable(false);

        this.addComponentListener( new ComponentListener() {
            public void componentResized( ComponentEvent e ) {}
            public void componentMoved( ComponentEvent e ) {
                setLocation( 0, 0 );
            }
            public void componentShown( ComponentEvent e ) {}
            public void componentHidden( ComponentEvent e ) {}
        } );


        }

    public class Click implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {
            if (SwingUtilities.isLeftMouseButton(e) && e.getClickCount() == 1) {
                if (board.isDefeat() == false && board.isVictory() == false) {
                    if (board.inBoxX() != -1 && board.inBoxY() != -1) {
                        board.field[board.inBoxX()][board.inBoxY()].setClickable(false);
                        board.setCount(board.getCount() + 1);
                        board.discovered();
                        board.getClicks()[0] = board.getCount();
                        board.getClicks()[1] = board.inBoxX();
                        board.getClicks()[2] = board.inBoxY();
                    }
                }
                board.inDiff();
            }
            if (board.isDefeat()== false && board.isVictory() == false){
                if (SwingUtilities.isRightMouseButton(e) && e.getClickCount() == 1) {
                if (board.inBoxX() != -1 && board.inBoxY() != -1 && board.field[board.inBoxX()][board.inBoxY()].isClickable() == true) {
                    if (board.field[board.inBoxX()][board.inBoxY()].isFlagged() == false) {
                        board.field[board.inBoxX()][board.inBoxY()].setFlagged(true);
                        board.setFlagged(board.getFlagged() + 1);
                    } else {
                        board.field[board.inBoxX()][board.inBoxY()].setFlagged(false);
                        board.setFlagged(board.getFlagged() - 1);
                    }
                    board.defeat();
                }
            }
        }
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }

        @Override
        public void mousePressed(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }
    }
    }



