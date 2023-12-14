package ch.heigvd.data.models;

public class Board {
    private final int width;
    private final int height;

    public Board(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public int getHeight() {
        return  height;
    }
    public int getWidth() {
        return  width;
    }
}
