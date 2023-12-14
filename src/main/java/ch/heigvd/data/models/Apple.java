package ch.heigvd.data.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Apple {
    @JsonProperty("position")
    private Point position;

    @JsonCreator
    public Apple(@JsonProperty("position") Point position) {
        this.position = position;
    }

    public Point getPosition() {
        return position;
    }
    public void setPosition(Point position) {
        this.position = position;
    }
}
