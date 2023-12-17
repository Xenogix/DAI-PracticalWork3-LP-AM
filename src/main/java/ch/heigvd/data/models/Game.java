package ch.heigvd.data.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Game {
    @JsonProperty("snakes")
    private final List<Snake> snakes;
    @JsonProperty("apples")
    private final List<Apple> apples;
    @JsonProperty("board")
    private Board board;

    public Game(int width, int height) {
        this(Collections.synchronizedList(new ArrayList<>()),
             Collections.synchronizedList(new ArrayList<>()),
             new Board(width, height));
    }
    @JsonCreator
    public Game(@JsonProperty("snakes") List<Snake> snakes,
                @JsonProperty("apples") List<Apple> apples,
                @JsonProperty("board")Board board) {
        this.snakes = Collections.synchronizedList(snakes);
        this.apples = Collections.synchronizedList(apples);
        this.board = board;
    }

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
