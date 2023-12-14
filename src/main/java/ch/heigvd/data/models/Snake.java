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
    private int length;

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
                 @JsonProperty("direction") Direction direction) {
        this.userId = userId;
        this.username = username;
        this.headPosition = position;
        this.color = color;
        this.direction = direction;
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
}
