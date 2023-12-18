# Practical Work 3 - Arthur Menétrey & Lionel Pollien

## Introduction

This program allows you to play a multiplayer game based on the famous game "Snake."
In this game, you will have to survive against other players and eat apples to gain points.
You can choose to host a game server locally or join a server and play with friends!
Please be aware this game can only be played on a LAN as it uses UDP for client-to-server communication.

## Protocol

In this section, we will detail everything you should know about the protocol the game uses.
This protocol is very versatile and allows you to customize the game however you want!

### Overview

The main purpose of the protocol is to be able to receive the inputs from the player and send updates about the game.
This is why we are using UDP as this allows for fast data transition at a high rate.
The inputs of the player are sent to the server using unicast, and the updates are sent to every listening device on the network using multicast.

### Transport Mechanism

As this protocol needs to send a lot of data about the state of the game, we are using an object-oriented approach.
This means we are transmitting data as JSON through the network.

### Commands

The protocol uses the following commands :

| Type        | Parameters       | From   | To     | Transport | Description                                                                              |
|-------------|------------------|--------|--------|-----------|------------------------------------------------------------------------------------------|
| JOIN        | player data      | Client | Server | Unicast   | Ask the server to join the game                                                          |
| INPUT       | client id, input | Client | Server | Unicast   | Ask the server to process the given input for the given player                           |
| ACCEPT      | client id        | Server | Client | Unicast   | Accept the player join request and send a player id back                                 |
| ACKNOWLEDGE | <none>           | Server | Client | Unicast   | Acknowledge the player input command                                                     |
| REFUSE      | error message    | Server | Client | Unicast   | Refuse the request when the it cannot be performed (I.E refuse JOIN when a game is full) |
| UPDATE      | game data        | Server | Client | Multicast | Send the state of the game to every listener on the network                              |

The parameter's formats will be described in the next paragraph.

### Data format

The data is sent as json through the network as it allows transmitting heterogeneous objects through the network.
The main object is the command object that has the following structure :

```json

{
  "commandType": "<COMMAND TYPE>",
  "value":{
    
  }
}

```

### Error Handling

### Diagram

## Architecture

### Global Diagram

### Join & Input - Unicast

### Update game - Multicast

## Game Engine

<u>Overview</u>

GameEngine.java serves as the core of the multiplayer snake game, managing game logic, player actions, and game state updates. Implemented as a Runnable, this class is responsible for executing game ticks, processing player movements, and detecting collisions.
Game Loop and Tick Management

The game loop in GameEngine runs at a fixed rate, defined by GAME_TPS (ticks per second), ensuring a consistent update rate across different systems. Each tick represents a single update cycle where the game state is advanced. The doTick() method is called every tick to move snakes, handle collisions, and update the game state. The notifyListeners() method informs all registered listeners about the latest game state, enabling real-time updates for players and the UI.

<u>Game Mechanics</u>

Key functionalities of the game engine include:

  - Snake Movement: Snakes are moved based on their current direction, with the head leading and the body following. The movement logic ensures that each segment of the snake follows the path of the segment ahead of it.
  - Collision Detection: The engine checks for various types of collisions, such as snakes colliding with the game borders, with each other, or with apples. Appropriate actions, like increasing the snake's length or marking it as dead, are taken based on the type of collision.
  - Snake Spawning: New snakes are spawned on the game board using the spawnSnake() method, which assigns them a random start position and an initial direction.
  - Direction Control: Players can change their snake's direction using the setSnakeDirection() method, which updates the direction of the specified snake.

<u>Extensibility and Interaction</u>

GameEngine is designed to be extensible and interactive. Listeners can be added or removed to respond to game updates, allowing for a flexible integration with various front-end implementations, such as GUIs or network interfaces. The game supports a maximum number of players, defined by MAX_PLAYER_COUNT, ensuring scalability and performance.

### Graphical User Interface (GUI)

<u>Overview</u>

The Multiplayer Snake Game employs a Java Swing-based Graphical User Interface (GUI) to provide an interactive and visual experience. The GUI mainly consists of two panels: GuiPanelMenu for initial setup and game connection, and GuiPanelGame for displaying and playing the game.

<u>Main Menu </u> (GuiPanelMenu)

Upon launching the application, users are greeted by the GuiPanelMenu. This panel allows players to enter the necessary IP addresses for server and multicast connections, as well as to choose their snake color from available options (Yellow, Red, Blue, Green, Brown). Once the details are input and confirmed using the "Join Game" button, the application attempts to connect to the game server using the provided details.

<u>Game Panel</u> (GuiPanelGame)

After a successful connection, the interface switches to the GuiPanelGame. This panel displays the game field, represented by a grid where snakes move around. Players can see the snakes of different players in distinct colors, as well as apples scattered on the field to be collected. Game controls are managed via keyboard inputs, allowing players to navigate their snake in different directions. The game unfolds in real-time, and the GUI updates accordingly to reflect the current state of the game.

 <u>Panel Management and Transitions</u>

GuiFrame is the application's main window that contains a CardLayout managing the transitions between different panels (GuiPanelMenu and GuiPanelGame). Users start at the main menu and, once connected, switch to the game panel. This modular approach makes the user interface flexible and easy to navigate.

### Rules

## CLI eventually

## Example of operation (screenshot probably)

## Conclusion