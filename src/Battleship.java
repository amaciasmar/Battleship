import java.util.*;
public class Battleship {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        Board player = new Board(10, 10);
        player.addShip(0, 0, 2, true);
        player.addShip(7, 1, 3, false);
        player.addShip(2, 9, 3, true);
        player.addShip(2, 3, 4, false);
        player.addShip(4, 7, 5, true);
        Board enemy = new Board(10, 10);
        enemy.addShip(8, 9, 2, true);
        enemy.addShip(2, 6, 3, false);
        enemy.addShip(5, 0, 3, true);
        enemy.addShip(7, 3, 4, false);
        enemy.addShip(1, 2, 5, true);
        Board enemyView = new Board(10, 10);
        boolean sunkNotify = false;
        while (player.contains("S") && enemy.contains("S")) {
            System.out.println("------------------------------------------------------------------------");
            System.out.println("-OPPONENT-");
            System.out.println(enemyView);
            System.out.println("-YOU-");
            System.out.println(player);
            String input;
            int x;
            int y;
            boolean check = false;
            while (!check) {
                if(sunkNotify) {
                    System.out.println("You sunk a battleship");
                    sunkNotify = false;
                }
                System.out.println("Which column do you want to target?");
                input = s.nextLine();
                x = input.charAt(0) - 97;
                System.out.println("Which row do you want to target?");
                input = s.nextLine();
                y = input.charAt(0) - 97;
                if (x < 0 || x > enemy.getWidth() - 1 || y < 0 || y > enemy.getHeight() - 1) {
                    System.out.println("Invalid coordinates");
                }  else if (!enemy.get(x, y).equals("S") && !enemy.get(x, y).equals("-")) {
                    System.out.println("Coordinates already hit");
                } else {
                    if (enemy.hit(x, y)) {
                        enemyView.set(x, y, "X");
                        Ship sunk = enemy.checkShips();
                        if (sunk != null) {
                            sunkNotify = true;
                            if(sunk.getHorizontal()) {
                                for (int j = 0; j < sunk.getLength(); j++) {
                                    enemyView.set(sunk.getX() + j, sunk.getY(), "Z");
                                }
                            } else {
                                for (int j = 0; j < sunk.getLength(); j++) {
                                    enemyView.set(sunk.getX(), sunk.getY() + j, "Z");
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
}
