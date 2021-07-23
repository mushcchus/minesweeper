public class Main implements Runnable {

    Player player =  new Player();

    public static void main(String[] args) {
        new Thread(new Main()).start();
    }

    @Override
    public void run(){
        while(true) {
            player.repaint();
            player.setSize(player.board.getWidth(), player.board.getHeight()+40);
        }
    }
}