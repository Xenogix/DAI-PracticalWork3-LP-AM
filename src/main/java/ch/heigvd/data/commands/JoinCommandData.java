package ch.heigvd.data.commands;

import ch.heigvd.data.models.Color;

public record JoinCommandData(String username, Color snakeColor) {
}
