# Practical Work 3 - Arthur Menétrey & Lionel Pollien

## Table of contents

* [Practical Work 3 - Arthur Menétrey & Lionel Pollien](#practical-work-3---arthur-menétrey--lionel-pollien)
  * [Introduction](#introduction)
  * [Protocol](#protocol)
    * [Overview](#overview)
    * [Transport Mechanism](#transport-mechanism)
    * [Commands](#commands)
    * [Data format](#data-format)
      * [JOIN data](#join-data)
      * [INPUT data](#input-data)
      * [ACCEPT data](#accept-data)
      * [ACKNOWLEDGE data](#acknowledge-data)
      * [REFUSE data](#refuse-data)
      * [UPDATE data](#update-data)
    * [Error Handling](#error-handling)
    * [Diagram](#diagram)
  * [Architecture](#architecture)
    * [Global Diagram](#global-diagram)
    * [UPD](#upd)
  * [Game Engine](#game-engine)
    * [Graphical User Interface (GUI)](#graphical-user-interface-gui)
    * [Rules](#rules)
  * [CLI](#cli)
  * [Docker](#docker)
  * [Example of operation (screenshot probably)](#example-of-operation-screenshot-probably)
  * [Conclusion](#conclusion)

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
  "value": {
    "<field1>": "<value1>",
    "<field2>": "<value1>",
    "<field3>": "<value1>"
  }
}
```

The command types allow identifying how to process the given data.

The list of command data and their structure follows :


#### JOIN data

```json
{
  "username": "<username>",
  "snakeColor": "<color>"
}
```

There is no standardized way of displaying color, and the color interpretation is subject to variations.

The available color identifiers can be found in the following list :

- RED
- BLUE
- GREEN
- YELLOW
- BROWN


#### INPUT data

```json
{
  "userId": "<user id>",
  "direction": "<input>"
}
```

UserId corresponds to the client personal identifier. 
If a "JOIN" command succeeds, a player id will be given in response through an "ACCEPT" command.
This identifier should be stored and used in these commands as this allows the server to identify the snake to update.

The available input identifiers can be found in the following list :

- UP
- DOWN
- LEFT
- RIGHT


#### ACCEPT data

```json
{
  "userId": "<user id>"
}
```

The ACCEPT data contains the user id that should be used in the INPUT command.
It follows a successful JOIN command


#### ACKNOWLEDGE data

```json
{
}
```

The ACKNOWLEDGE data is empty as the ACKNOWLEDGE command is simply used to notify a client that the sent INPUT command was successfully processed.


#### REFUSE data

```json
{
  "message": "<error message>"
}
```

The REFUSE contains an error message that follows any client command that was not processed successfully.
There are no predefined messages that should be sent and any server could send its own to notify an error to the client.

#### UPDATE data

The update data contains the game data which can be then processed by the client.

```json
{
"game": {
  "snakes":[
    {
      "id":"<user id>",
      "username":"<snake username>",
      "position":{"x":79,"y":35},
      "color":"Red",
      "direction":"RIGHT",
      "body":[{"x":79,"y":35},{"x":78,"y":35},{"x":77,"y":35},{"x":76,"y":35}],
      "length":4
    }
  ],
  "apples":[
    {"position":{"x":87,"y":30}},
    {"position":{"x":15,"y":84}},
    {"position":{"x":93,"y":50}},
    {"position":{"x":23,"y":53}},
    {"position":{"x":85,"y":96}},
    {"position":{"x":27,"y":60}}
  ],
  "board":{"width":100,"height":100}
  }
}
```
<u>Snakes</u>

Snake are stored in an array and contains information about the snakes :

user id corresponds to a string with a UUID that can be used to identify a user.

username can be used to display the username of the player and is not unique.

position indicates the position of its head, and the body corresponds to each segment of the snake from the head to the tail.

lenght corresponds to the snake lenght and is directly linked to its body part count.

<u>Apples</u>

The apples are stored in an array and solely contain their position on the game.

<u>Board</u>

The board indicates what size is the game and the board start coordinates are x = 0 and y = 0.
If a board size is 100x100 this means its coordinates start from x = 0 and y = 0 and extend to x = 100 and y = 100.

### Error Handling

For the connection to the game, if a player send wrong datas, the server will simply refuse the connection. And if a player is connected to the server and play the game, if he is disconnected, the snake will die sooner or later.

### Diagram

[![Functionnal use](www.plantuml.com/plantuml/png/XP0n2W8n44NxESLSm28fBMJ5Ii5AjfOmEwp1E0d9h5xVP1k987QVzyFZPuQYBklo2E18OyP3y2nwDUWAakcXNQ65vB9wyic9ueNpatChIaq0P72pMpDkyHIzGCkDaQbaJyH9GQ8oPZ_V5057yUIA0ik4hG2oFntsu-Z5OvgSSYy-Bq5xTI_gnnubBV-MVVtbBl4LU9ePXlga7l4D)]

[![Error](www.plantuml.com/plantuml/png/7Swnoi9G3CNnFKznnkyR7Exg80KtHyLPmcsiWPfRSbDzVTDk1txyuGmzs6DzpyGrckCygrWy2hYZ5beIjUh25hY9VyGtwBc8Qi7Xj6ySSMbg-FjwimaZ1_-JjH2uJgy0uMvQkGU9ZTY0zYppMZA-odFjGZGavvKV)]

## Architecture

For the global architecture of the game, we reuse the structure we did for the TCP practical work. 

Of course we adapt it to the UDP protocol by sending JSON data in the datagram and we create a converter to use the data. 

### Global Diagram

### UPD

<u>Overview</u>

To create this application, we were instructed to use UDP protocol in both multicast and unicast modes. To achieve this, we decided to communicate via UDP unicast to establish the connection between the client and the server, and from that point, to connect to the game. For transmitting the game state, the server sends information over a multicast channel, which is joined by the clients.

<u>Join & Input - Unicast</u>

The ServerCommandEndpoint and ClientCommandSender classes in this application play crucial roles in handling network communication between the server and clients using the UDP protocol. The ServerCommandEndpoint, running on the server side, listens on a specific port for incoming UDP packets. Upon receiving a packet, it initiates a new thread using CommandResponder to process the command contained in the packet. This design allows for concurrent handling of multiple client requests, enhancing the server's ability to manage simultaneous commands. On the client side, ClientCommandSender implements the VirtualClient interface to facilitate sending commands to the server. It serializes command objects into byte data, sends them to the server via UDP packets, and waits for a response. This response is then deserialized back into a command object, providing a seamless and efficient communication mechanism between the client and the server. This architecture ensures a reliable and scalable way of handling network communication in the multiplayer game environment.

<u>Update game - Multicast</u>

In the multiplayer snake game, the ClientUpdateEndpoint and ServerUpdateSender classes play pivotal roles in handling real-time game updates using UDP multicast. The ClientUpdateEndpoint, part of the client-side architecture, subscribes to a multicast group identified by a specific address and port. It continuously listens for incoming data packets on this multicast channel. Upon receiving a packet, it deserializes the command contained within and processes it using the clientCommandHandler. This mechanism allows all clients in the multicast group to receive and process game state updates simultaneously. Conversely, the ServerUpdateSender functions on the server-side, broadcasting game state updates to all clients. It serializes the game state into a command using CommandFactory and CommandSerializer, then sends this data to the multicast group address. This approach ensures efficient and synchronized state management among all participants in the game, providing a consistent and real-time multiplayer experience.

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

Objective

The primary goal is to grow your snake to be the largest in the game.
Ways to Grow

  Eating Apples: Similar to the classic snake game, consuming apples increases your snake's length.
  Eating Other Snakes:
    If your snake's head collides with another snake's body, you cut that snake at the point of impact. Your snake then grows by 50% of the length of the portion consumed. For example, if you eat 4 (index 5 on 9) segments of a 9-segment snake, your snake grows by 2 segments.
    Head-to-Head Collisions: When two snakes' heads collide, the larger snake survives, consuming 50% of the smaller snake's length. In the event of a tie in size, the outcome is random.

Game Constraints

  Colliding with Borders: If your snake hits a border, it dies.
  Respawning: If your snake dies, you have the option to respawn and re-enter the game.

These rules create a dynamic and competitive environment where players must strategically navigate and grow their snakes while avoiding hazards and outmaneuvering opponents.

## CLI

The server can be run using a CLI. To do this, run the snake_server.jar using the following command :

```
java -jar "<folder>/snake_server.jar"
```

Take care of replacing the "<folder>" part with the file path to the snake_server.jar file.

This command will use the default configuration :

- Server port => 3432
- Multicast address => 224.12.17.11
- Multicast port => 3433

Please refer to the parameter list if you want to configure these parameters yourself :

| Name |     FullName     | Parameter                                   |
|:-----|:----------------:|:--------------------------------------------|
| -p   |      --port      | Port on which the server will be hosted     |
| -up  |  --update_port   | Multicast address used to send game updates |
| -ua  | --update_address | Multicast address used to send game updates |

Note that no parameter is mandatory and they can be used independently.

For example, we are setting the ports and address manually in this example
```
java -jar "<folder>/snake_server.jar" -p 5343 -up 5444 -ua 224.12.17.6
```

## Docker

## Example of operation (screenshot probably)

## Conclusion

Overview

  Enjoyable Experience: The project was ambitious yet enjoyable, providing a great learning experience.
  Technical Implementation:
    UDP Protocol: Successfully implemented, ensuring efficient data transmission.
    Data Converter: Our custom converter effectively sends data within UDP datagrams.
  Game Development:
    Simplicity Due to Time Constraints: While there are many potential enhancements, time limitations led us to maintain simplicity.
    Testing: Limited testing was conducted, but the game functions well for one or two players.
  Graphical User Interface (GUI):
    Functional Focus: The project prioritized functionality over aesthetics, leading to a basic but functional GUI.

Areas for Improvement

  Enhancing the GUI:
      Current State: The GUI is functional but lacks visual appeal.
      Potential Enhancement: With additional effort, the GUI can be improved to be more aesthetically pleasing.

  Move Prediction for Snakes:
      Objective: To enhance game fluidity and visual appeal.
      Approach: Implementing movement prediction algorithms for smoother snake movements.

  Adding New Game Rules and Features:
      Expandability: The application's scalable design allows for easy addition of new features.
      Ideas for New Features: Introducing various fruits with different effects, such as bonuses, maluses, length variations, speed adjustments, etc., to add diversity and fun to the gameplay.

These improvements aim to enhance user experience, gameplay fluidity, and visual appeal, making the game more engaging and enjoyable.
