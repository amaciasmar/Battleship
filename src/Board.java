import java.util.*;
public class Board {

    // this line creates an arraylist that contains arraylists, which will be our boards.
    private ArrayList<ArrayList<String>> board = new ArrayList<ArrayList<String>>();
    private ArrayList<Ship> shipList = new ArrayList<Ship>();
    private int width;
    private int height;
    // if you want to access the square on the board that is 0 lines down (at the top) and
    // 2 lines to the right, you would type: "board.get(0).get(2)"  but we will likely
    // write methods that simplify this.

    // Board's constructor generates the game board, "w" is the width of the board, while "h" is the height
    // "w" and "h" must be greater than 0, and less than 31, the max is 30 because values higher than that
    // sort of break the toString method.
    // right now, the character '-' equals an empty space, we can discuss what character should represent what
    public Board(int w, int h) {
        if (0 < w && w < 31 && 0 < h && h < 31) {
            width = w;
            height = h;
            for (int i = 0; i < h; i++) {
                board.add(new ArrayList<String>());
                for (int j = 0; j < w; j++) {
                    board.get(i).add("-");
                }
            }
        } else {
            throw new IllegalArgumentException();
        }
    }

    // printBoard prints the board into the console. Usually in battleship, either x or y is
    // represented by a letter, while the other is represented by a number. But we need to represent
    // both as characters instead of numbers, because numbers higher than 9 ruin this method.
    public String toString() {
        String product = " ";
        for (int i = 0; i < board.get(0).size(); i++) {
            char index = 'a';
            index += i;
            product += (" " + index);
        }
        product += "\n";
        for (int i = 0; i < board.size(); i++) {
            char index = 'a';
            index += i;
            product += index;
            for (int j = 0; j < board.get(i).size(); j++) {
                product +=" " + board.get(i).get(j);
            }
            product += "\n";
        }
        return product;
    }

    //overwrites the string stored at the position "x" rows from the left, and "y" rows from the top with "data"
    public void set(int x, int y, String data) {
        if (y >= 0 && y < height && x >= 0 && x < width) {
            board.get(y).set(x, data);
        } else {
            throw new IndexOutOfBoundsException();
        }
    }

    //returns the string stored at the position "x" rows from the left, and "y" rows from the top
    public String get(int x, int y) {
        if (y >= 0 && y < height && x >= 0 && x < width) {
            return board.get(y).get(x);
        } else {
            throw new IndexOutOfBoundsException();
        }
    }

    //adds a ship that is "length" units long. If "horizontal" is true, (x, y) is the left-most square of the ship.
    // if "horizontal" is false, (x, y) is the top most square of the ship.
    public void addShip(int x, int y, int length, boolean horizontal) {
        if (horizontal) {
            for (int i = 0; i < length; i ++) {
                set(x + i, y, "S");
            }
        } else {
            for (int i = 0; i < length; i ++) {
                set(x, y + i, "S");
            }
        }
        shipList.add(new Ship(x, y, length, horizontal));
    }

    // simulates incoming fire at (x, y). returns true if a ship was hit, returns false if not
    // we'll have to make sure you can't hit the same spot twice for this to work.
    // that should be easy though, just check for an "X" or "O" (capitalized!)
    public boolean hit(int x, int y) {
        if (get(x, y).equals("S")) {
            set(x, y, "X");
            return true;
        } else {
            set(x, y, "O");
            return false;
        }
    }

    // returns true if the board has a square whose String matches "data"
    public boolean contains(String data) {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (board.get(i).get(j).equals(data)) {
                    return true;
                }
            }
        }
        return false;
    }

    // returns the width value
    public int getWidth() {
        return width;
    }

    // returns the height value
    public int getHeight() {
        return height;
    }

    // returns the ship list
    public ArrayList<Ship> getShipList() {
        return shipList;
    }

    // checks the board for any sunk ships, if it finds one, it will remove it from the ship list and return it.
    // it can only remove one ship at a time (the last sunken one it finds,) so it's important to
    // call this every time a shot is fired.
    public Ship checkShips() {
        int index = -1;
        for (int i = 0; i < shipList.size(); i++) {
            boolean sunk = true;
            if(shipList.get(i).getHorizontal()) {
                for (int j = 0; j < shipList.get(i).getLength(); j++) {
                    if(get(shipList.get(i).getX() + j, shipList.get(i).getY()).equals("S")) {
                        sunk = false;
                    }
                }
            } else {
                for (int j = 0; j < shipList.get(i).getLength(); j++) {
                    if(get(shipList.get(i).getX(), shipList.get(i).getY() + j).equals("S")) {
                        sunk = false;
                    }
                }
            }
            if (sunk) {
                index = i;
            }
        }
        if (index > -1) {
            return shipList.remove(index);
        } else {
            return null;
        }
    }
}