public class Ship {
    private int x, y, length;
    private boolean horizontal;
    public Ship(int x, int y, int length, boolean horizontal) {
        this.x = x;
        this.y = y;
        this.length = length;
        this.horizontal = horizontal;
    }
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public int getLength() {
        return length;
    }
    public boolean getHorizontal() {
        return horizontal;
    }
}
