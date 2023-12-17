package ch.heigvd.data.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Snake {
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

    //maximum depending of the size of the panel - todo maybe clear that cause now it's ugly
    private final int MAX_LENGTH = 600*600 / 25;

    public Snake(String userId, String username, Point headPosition, Color color) {
        this.userId = userId;
        this.headPosition = headPosition;
        this.username = username;
        this.color = color;
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
    public void setLength(int length) {
        this.length = length;
    }
    public Direction getDirection() {
        return direction;
    }
    public void setDirection(Direction direction) {
        this.direction = direction;
    }
    public Point[] getBody() { return body; }
    public void setBody(Point[] body) { this.body = body; }

    public void setSnakeDead(){
        setBody(null);
        setLength(0);
        setHeadPosition(null);
    }
}
