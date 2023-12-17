package ch.heigvd.data.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Board {
    @JsonProperty("width")
    private final int width;
    @JsonProperty("height")
    private final int height;

    @JsonCreator
    public Board(@JsonProperty("width") int width, @JsonProperty("height") int height) {
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
