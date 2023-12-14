package ch.heigvd.data.commands.data;

import ch.heigvd.data.models.Color;

public record JoinCommandData(String username, Color snakeColor) {
}
