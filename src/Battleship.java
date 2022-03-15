import java.util.*;

public class Battleship {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";
    public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
    public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
    public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
    public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
    public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
    public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
    public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
    public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";


    static Scanner s;
    static Board player, enemy, enemyView;

    static int width = 10, height = 10;

    public static void main(String[] args) {

        s = new Scanner(System.in);
        player = new Board(width, height);
        player.addShip(0, 0, 2, true);
        player.addShip(7, 1, 3, false);
        player.addShip(2, 9, 3, true);
        player.addShip(2, 3, 4, false);
        player.addShip(4, 7, 5, true);
        enemy = new Board(width, height);
        /*enemy.addShip(8, 9, 2, true);
        enemy.addShip(2, 6, 3, false);
        enemy.addShip(5, 0, 3, true);
        enemy.addShip(7, 3, 4, false);*/
        enemy.addShip(1, 2, 5, true);
        enemy.addShip(0, 0, 2, true);

        enemyView = new Board(width, height);


        // the main game loop
        // the game should end when either side loses all its ships (represented by 'S')
        while (player.contains("S") && enemy.contains("S")) {
            System.out.println("------------------------------------------------------------------------");
            System.out.println("-OPPONENT-");
            System.out.println(enemyView);
            System.out.println("-YOU-");
            System.out.println(player);

            playerTurn();
            enemyTurn();
        }

        if(player.contains("S")) {
            System.out.println("Congratulations, you've won the game!");
        } else {
            System.out.println("You lose!");
        }

    }

    static void enemyTurn(){

        Random r = new Random();

        boolean check = false;
        int x,y;
        boolean sunkNotify = false;
        while (!check) {
            x = r.nextInt(width);
            y = r.nextInt(height);
            if(player.get(x,y).contains("O") || player.get(x,y).contains("X")){
                continue;
            } else {
                if (player.hit(x, y)) {
                    Ship sunk = enemy.checkShips();
                    if (sunk != null) {
                        if(sunk.getHorizontal()) {
                            for (int j = 0; j < sunk.getLength(); j++) {
                                player.set(sunk.getX() + j, sunk.getY(), ANSI_RED+"Z"+ANSI_RESET);
                            }
                        } else {
                            for (int j = 0; j < sunk.getLength(); j++) {
                                player.set(sunk.getX(), sunk.getY() + j, ANSI_RED+"Z"+ANSI_RESET);
                            }
                        }
                    }
                }
                check = true;

            }
        }
    }

    static void playerTurn(){

        boolean sunkNotify = false;
        String input;
        int x;
        int y;

        boolean check = false;
        while (!check) {
            if(sunkNotify) {
                System.out.println("You sunk a battleship");
                sunkNotify = false;
            }
            System.out.println(ANSI_CYAN+"Which column do you want to target?"+ANSI_RESET);
            input = s.nextLine();
            x = input.charAt(0) - 97;
            System.out.println(ANSI_CYAN+"Which row do you want to target?"+ANSI_RESET);
            input = s.nextLine();
            y = input.charAt(0) - 97;
            if (x < 0 || x > enemy.getWidth() - 1 || y < 0 || y > enemy.getHeight() - 1) {
                System.out.println(ANSI_RED+"Invalid coordinates"+ANSI_RESET);
            }  else if (!enemy.get(x, y).contains("S") && !enemy.get(x, y).contains("-")) {
                System.out.println("Coordinates already hit");
            } else {
                if (enemy.hit(x, y)) {
                    enemyView.set(x, y, ANSI_RED+"X"+ANSI_RESET);
                    Ship sunk = enemy.checkShips();
                    if (sunk != null) {
                        sunkNotify = true;
                        if(sunk.getHorizontal()) {
                            for (int j = 0; j < sunk.getLength(); j++) {
                                enemyView.set(sunk.getX() + j, sunk.getY(), ANSI_RED+"Z"+ANSI_RESET);
                            }
                        } else {
                            for (int j = 0; j < sunk.getLength(); j++) {
                                enemyView.set(sunk.getX(), sunk.getY() + j, ANSI_RED+"Z"+ANSI_RESET);
                            }
                        }
                    }
                } else {
                    enemyView.set(x, y, "O");
                }
                check = true;
            }
        }
    }
}
