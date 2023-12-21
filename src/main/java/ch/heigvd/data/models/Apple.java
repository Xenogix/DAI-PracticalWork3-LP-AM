package ch.heigvd.data.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Apple {
    @JsonProperty("position")
    private Point position;

    @JsonProperty("power")
    private int power;

    @JsonCreator
    public Apple(@JsonProperty("position") Point position, @JsonProperty("power") int power) {
        this.position = position;
        this.power = power;
    }

    public Point getPosition() {
        return position;
    }
    public void setPosition(Point position) {
        this.position = position;
    }

    public int getPower() {
        return power;
    }
}
