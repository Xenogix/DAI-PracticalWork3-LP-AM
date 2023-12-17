package ch.heigvd.data.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Point {
    @JsonProperty("x")
    private int x;
    @JsonProperty("y")
    private int y;

    @JsonCreator
    public Point(@JsonProperty("x") int x, @JsonProperty("y") int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
}
