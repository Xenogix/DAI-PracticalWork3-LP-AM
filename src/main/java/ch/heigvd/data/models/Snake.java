package ch.heigvd.data.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Random;

public class Snake {
    @JsonIgnore
    private final static Random random = new Random();
    @JsonProperty("id")
    private String userId;
    @JsonProperty("username")
    private String username;
    @JsonProperty("position")
    private Point headPosition;
    @JsonProperty("color")
    private Color color;
    @JsonProperty("direction")
    private Direction direction;
    @JsonProperty("body")
    private Point[] body;
    @JsonProperty("length")
    private int length;

    public Snake(String userId, String username, Point headPosition, Color color, int length) {
        this.userId = userId;
        this.headPosition = headPosition;
        this.username = username;
        this.color = color;
        this.length = length;

        // Create the snake body
        this.body = new Point[length];
        for (int i = 0; i < length; ++i)
            this.body[i] = new Point(headPosition.getX(), headPosition.getY());

        // Set random direction
        Direction[] directions = Direction.values();
        this.direction = directions[random.nextInt(0,directions.length)];
    }

    @JsonCreator
    public Snake(@JsonProperty("id") String userId,
                 @JsonProperty("username") String username,
                 @JsonProperty("position") Point position,
                 @JsonProperty("color") Color color,
                 @JsonProperty("direction") Direction direction,
                 @JsonProperty("body") Point[] body,
                 @JsonProperty("length") int length){
        this.userId = userId;
        this.username = username;
        this.headPosition = position;
        this.color = color;
        this.direction = direction;
        this.body = body;
        this.length = length;
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
    public Point getHeadPosition() {
        return headPosition;
    }
    public void setHeadPosition(Point headPosition) {
        this.headPosition = headPosition;
    }
    public int getLength() {
        return length;
    }
    public void setLength(int newLength) {
        if(this.length == newLength || newLength < 0) return;
        Point[] newBody = new Point[newLength];
        for(int i = 0; i < newLength; ++i) {
            if(i < this.length)
                newBody[i] = body[i];
            else
                newBody[i] = new Point(body[length - 1]);
        }
        this.length = newLength;
        this.body = newBody;
    }
    public Direction getDirection() {
        return direction;
    }
    public void setDirection(Direction direction) {
        this.direction = direction;
    }
    public Point[] getBody() { return body; }
}
