package ch.heigvd.data.models;

public class Snake {
    private String userId;
    private String username;
    private Color color;
    private Point position;
    private int length;
    private Direction direction;

    public Snake(String userId, String username, Point position, Color color) {
        this.userId = userId;
        this.position = position;
        this.username = username;
        this.color = Color.Blue;
    }

    public String getUserId(){
        return userId;
    }
    public String getUsername() {
        return username;
    }
    public Color getColor() {
        return color;
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
