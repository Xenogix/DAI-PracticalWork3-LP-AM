package ch.heigvd.shared.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Game {
    private final List<Snake> snakes = Collections.synchronizedList(new ArrayList<>());
    private final List<Apple> apples = Collections.synchronizedList(new ArrayList<>());
    private Board board;

    public List<Snake> getSnakes() {
        return snakes;
    }

    public List<Apple> getApples() {
        return apples;
    }

    public Board getBoard() {
        return board;
    }
}
