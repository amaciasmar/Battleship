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
    public static ArrayList<Integer> shipInfo= new ArrayList<Integer>();

    static Scanner s;
    static Board player, enemy, enemyView;

    static int width = 10, height = 10;

    public static void main(String[] args) {
        shipInfo.add(5);
        shipInfo.add(4);
        shipInfo.add(3);
        shipInfo.add(3);
        shipInfo.add(2);
        s = new Scanner(System.in);
        System.out.println("Welcome to Battleship. What would you like to do?\n 1. Play \n 2. View instructions\n 3. Customize");
        boolean check = false;
        while (!check) {
            if(s.hasNextInt()){
                int n = s.nextInt();
                if (n == 1) {
                    check = true;
                } else if (n == 2) {
                    System.out.println("This is a game of battleship.");
                    System.out.println("Try to sink your opponent's ships before they can.");
                    System.out.println(" \"-\" represents water, \"S\" represents part of a ship,\n \"O\" represents a missed shot, and \"X\" represents a part of a ship that's been hit.");
                    System.out.println("Finally, a \"Z\" represents a whole ship that has been sunk.");
                    System.out.println();
                    System.out.println("What would you like to do?\n 1. Play \n 2. View instructions\n 3. Customize");
                } else if (n == 3) {
                    Customize();
                } else {
                    System.out.println("Invalid input");
                }
            } else {
                s.next();
                System.out.println("Invalid input");
            }
        }

        player = new Board(width, height);
        //player.addShip(0, 0, 2, true);
        //player.addShip(7, 1, 3, false);
        //player.addShip(2, 9, 3, true);
        //player.addShip(2, 3, 4, false);
        //player.addShip(4, 7, 5, true);
        enemy = new Board(width, height);
        /*enemy.addShip(8, 9, 2, true);
        enemy.addShip(2, 6, 3, false);
        enemy.addShip(5, 0, 3, true);
        enemy.addShip(7, 3, 4, false);*/

        enemyView = new Board(width, height);

        // the main game loop
        // the game should end when either side loses all its ships (represented by 'S')
        enemyPlaceShips();
        placeShips();
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
            System.out.println("Opponent's board: ");
            System.out.println(enemy);
        }
    }

    static void Customize() {
        System.out.println("What would you like to change?\n 1. Board size \n 2. Ships\n 3. Back to menu");
        boolean check = false;
        while (!check) {
            if(s.hasNextInt()){
                int n = s.nextInt();
                if (n == 1) {
                    boolean check0 = false;
                    while (!check0) {
                        int temp;
                        System.out.println("How tall do you want your board to be? (From 1 to 30)");
                        temp = checkInt(1, 30);
                        height = temp;
                        System.out.println("How wide do you want your board to be? (From 1 to 30)");
                        temp = checkInt(1, 30);
                        width = temp;
                        int totalUnits = 0;
                        for (int i = 0; i < shipInfo.size(); i++) {
                            totalUnits += shipInfo.get(i);
                        }
                        if (totalUnits > (width * height / 2)) {
                            System.out.println("Your ships can not take up more than half the board space!");
                        } else {
                            System.out.println("What would you like to change?\n 1. Board size \n 2. Ships\n 3. Back to menu");
                            check0 = true;
                        }
                    }
                } else if (n == 2) {
                    boolean check0 = false;
                    while (!check0) {
                        shipInfo.clear();
                        System.out.println("How many ships would you like to have?");
                        int sa = 5;
                        boolean check1 = false;
                        while (!check1) {
                            if (s.hasNextInt()) {
                                sa = s.nextInt();
                                check1 = true;
                            } else {
                                s.next();
                                System.out.println("Invalid input");
                            }
                        }
                        for (int i = 0; i < sa; i++){
                            System.out.println("How long do you want ship #" + (i + 1) + " to be?");
                            check1 = false;
                            while (!check1) {
                                if (s.hasNextInt()) {
                                    int l = s.nextInt();
                                    if (l >= height && l >= width) {
                                        System.out.println("Your ship's length can not be bigger than both the width and height of your board.");
                                    } else {
                                        shipInfo.add(l);
                                        check1 = true;
                                    }
                                } else {
                                    s.next();
                                    System.out.println("Invalid input");
                                }
                            }
                        }
                        int totalUnits = 0;
                        for (int i = 0; i < shipInfo.size(); i++) {
                            totalUnits += shipInfo.get(i);
                        }
                        if (totalUnits > (width * height / 2)) {
                            System.out.println("Your ships can not take up more than half the board space!");
                        } else {
                            System.out.println("What would you like to change?\n 1. Board size \n 2. Ships\n 3. Back to menu");
                            check0 = true;
                        }
                    }
                } else if (n == 3) {
                    System.out.println("Welcome to Battleship. What would you like to do?\n 1. Play \n 2. View instructions\n 3. Customize");
                    check = true;
                } else {
                    System.out.println("Invalid input");
                }
            } else {
                s.next();
                System.out.println("Invalid input");
            }
        }
    }

    public static int[] firstHit = {-1, -1};
    public static int[] secondHit = {-1, -1};

    static void enemyTurn(){

        Random r = new Random();

        boolean check = false;
        int x = 0,y = 0;
        while (!check) {
            int loopStopper = 0;

            boolean check1 = false;
            while (!check1) {
                check1 = true;
                if (firstHit[0] > -1) {
                    if (secondHit[0] > -1) {
                        if (firstHit[0] < secondHit[0]) {
                            if (secondHit[0] + 1 >= width || player.get(secondHit[0] + 1, secondHit[1]).equals("X") || player.get(secondHit[0] + 1, secondHit[1]).equals("O")) {
                                secondHit[0] = -1;
                                secondHit[1] = -1;
                                check1 = false;
                            } else {
                                x = secondHit[0] + 1;
                                y = secondHit[1];
                            }
                        } else if (firstHit[0] > secondHit[0]){
                            if (secondHit[0] - 1 < 0 || player.get(secondHit[0] - 1, secondHit[1]).equals("X") || player.get(secondHit[0] - 1, secondHit[1]).equals("O")) {
                                secondHit[0] = -1;
                                secondHit[1] = -1;
                                check1 = false;
                            } else {
                                x = secondHit[0] - 1;
                                y = secondHit[1];
                            }
                        } else if (firstHit[1] < secondHit[1]) {
                            if (secondHit[1] + 1 >= height || player.get(secondHit[0], secondHit[1] + 1).equals("X") || player.get(secondHit[0], secondHit[1] + 1).equals("O")) {
                                secondHit[0] = -1;
                                secondHit[1] = -1;
                                check1 = false;
                            } else {
                                x = secondHit[0];
                                y = secondHit[1] + 1;
                            }
                        } else{
                            if (secondHit[1] - 1 < 0 || player.get(secondHit[0], secondHit[1] - 1).equals("X") || player.get(secondHit[0], secondHit[1] - 1).equals("O")) {
                                secondHit[0] = -1;
                                secondHit[1] = -1;
                                check1 = false;
                            } else {
                                x = secondHit[0];
                                y = secondHit[1] - 1;
                            }
                        }
                    } else {
                        int direction = r.nextInt(4);
                        if (direction == 0) {
                            x = firstHit[0] + 1;
                            y= firstHit[1];
                        } else if (direction == 1) {
                            x = firstHit[0] - 1;
                            y= firstHit[1];
                        } else if (direction == 2) {
                            x = firstHit[0];
                            y= firstHit[1] + 1;
                        } else {
                            x = firstHit[0];
                            y= firstHit[1] - 1;
                        }
                    }
                } else {
                    x = r.nextInt(width);
                    y = r.nextInt(height);
                }

            }

            if(x < 0 || x >= width || y < 0 || y >= height || player.get(x,y).contains("O") || player.get(x,y).contains("X") || player.get(x,y).contains("Z")){
                continue;
            } else {
                if (player.hit(x, y)) {
                    if (firstHit[0] < 0) {
                        firstHit[0] = x;
                        firstHit[1] = y;
                    } else {
                        secondHit[0] = x;
                        secondHit[1] = y;
                    }

                    Ship sunk = player.checkShips();
                    if (sunk != null) {
                        firstHit[0] = -1;
                        firstHit[1] = -1;
                        secondHit[0] = -1;
                        secondHit[1] = -1;
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
                } else {
                    secondHit[0] = -1;
                    secondHit[1] = -1;
                }
                check = true;
            }
        }
    }

    public static boolean whyIsItPrintingWhichColumnTwice = false;
    static void playerTurn(){
        String input;
        int x;
        int y;

        boolean check = false;
        while (!check) {
            while(true){
                if (whyIsItPrintingWhichColumnTwice) {
                    System.out.println(ANSI_CYAN+"Which column do you want to target?"+ANSI_RESET);
                }
                whyIsItPrintingWhichColumnTwice = true;
                input = s.nextLine();
                if (input == "")
                    continue;
                x = input.charAt(0) - 97;
                break;
            }
            while(true) {
                System.out.println(ANSI_CYAN + "Which row do you want to target?" + ANSI_RESET);
                input = s.nextLine();
                if(input == "")
                    continue;
                y = input.charAt(0) - 97;
                break;
            }
            if (x < 0 || x > enemy.getWidth() - 1 || y < 0 || y > enemy.getHeight() - 1) {
                System.out.println(ANSI_RED+"Invalid coordinates"+ANSI_RESET);
            }  else if (!enemy.get(x, y).contains("S") && !enemy.get(x, y).contains("-")) {
                System.out.println("Coordinates already hit");
            } else {
                if (enemy.hit(x, y)) {
                    enemyView.set(x, y, ANSI_RED+"X"+ANSI_RESET);
                    Ship sunk = enemy.checkShips();
                    if (sunk != null) {
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
    public static int checkInt(int min, int max) {
        int n = 0;
        boolean check = false;
        while (!check) {
            if (s.hasNextInt()) {
                n = s.nextInt();
                if (n < min || n > max) {
                    System.out.println("Invalid input");
                } else {
                    check = true;
                }
            } else {
                s.next();
                System.out.println("Invalid input");
            }
        }
        return n;
    }
    public static void placeShips(){
        for (int i = 0; i < shipInfo.size(); i++) {
            String input;
            int x = 0;
            int y = 0;
            boolean horizontal = true;
            boolean check = false;
            while (!check) {
                check = true;
                System.out.println(player);
                System.out.println("Your current ship is of length " + shipInfo.get(i) + ",\nwhat column do you want the top / leftmost unit to be in?");
                while(true){
                    input = s.nextLine();
                    if (input == "")
                        continue;
                    x = input.charAt(0) - 97;
                    break;
                }
                System.out.println("what row do you want the top / leftmost unit to be in?");
                while(true) {
                    input = s.nextLine();
                    if(input == "")
                        continue;
                    y = input.charAt(0) - 97;
                    break;
                }
                System.out.println("Type \"1\" if you want the ship to be horizontal, or \"2\" if you want it to be vertical.");
                int temp = checkInt(1, 2);
                if (temp == 1) {
                    horizontal = true;
                } else {
                    horizontal = false;
                }
                if (x < 0 || y < 0 || x >= width || y >= height) {
                    check = false;
                }
                if (horizontal) {
                    if (x + shipInfo.get(i) > width) {
                        check = false;
                    } else {
                        for (int j = 0; j < shipInfo.get(i); j++) {
                            if (player.get(x + j, y).contains("S")) {
                                check = false;
                            }
                        }
                    }
                } else  {
                    if (y + shipInfo.get(i) > height) {
                        check = false;
                    } else {
                        for (int j = 0; j < shipInfo.get(i); j++) {
                            if (player.get(x, y + j).contains("S")) {
                                check = false;
                            }
                        }
                    }
                }
                if (!check) {
                    System.out.println("Invalid coordinates");
                }
            }
            player.addShip(x, y, shipInfo.get(i), horizontal);
        }
    }
    public static void enemyPlaceShips(){
        Random r = new Random();
        for (int i = 0; i < shipInfo.size(); i++) {
            String input;
            int x = 0;
            int y = 0;
            boolean horizontal = true;
            boolean check = false;
            while (!check) {
                check = true;
                horizontal = r.nextBoolean();
                if (horizontal) {
                    x = r.nextInt(width - shipInfo.get(i));
                    y = r.nextInt(height);
                    for (int j = 0; j < shipInfo.get(i); j++) {
                        if (enemy.get(x + j, y).contains("S")) {
                            check = false;
                        }
                    }
                } else {
                    x = r.nextInt(width);
                    y = r.nextInt(height - shipInfo.get(i));
                    for (int j = 0; j < shipInfo.get(i); j++) {
                        if (enemy.get(x, y + j).contains("S")) {
                            check = false;
                        }
                    }
                }
            }
            enemy.addShip(x, y, shipInfo.get(i), horizontal);
        }
    }
}
