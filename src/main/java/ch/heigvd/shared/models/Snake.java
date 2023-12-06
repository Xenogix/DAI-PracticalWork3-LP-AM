package ch.heigvd.shared.models;

public class Snake {
    private String id;
    private Point position;
    private int length;
    private Direction direction;

    public Snake(String id, Point position) {
        this.id = id;
        this.position = position;
    }

    public String getId(){
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public Point getPosition() {
        return position;
    }
    public void setPosition(Point position) {
        this.position = position;
    }
    public int getLength() {
        return length;
    }
    public void setLength(int length) {
        this.length = length;
    }
    public Direction getDirection() {
        return direction;
    }
    public void setDirection(Direction direction) {
        this.direction = direction;
    }
}
