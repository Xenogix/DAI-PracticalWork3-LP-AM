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

    public Point(Point point) {
        setPoint(point);
    }

    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }

    public void setX(int x) { this.x = x; }
    public void setY(int y) { this.y = y; }

    public boolean posEqual(Point point){
        if(this.getX() == point.getX() && this.getY() == point.getY())
            return true;
        return false;
    }

    public void setPoint(Point point){
        this.x = point.getX();
        this.y = point.getY();
    }

}
