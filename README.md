# INF112 - Platform Game
The 2022 semester assignment in the course INF112 - Introduction to Systems Development at the University of Bergen (UiB).

## Project Description
This project is a 2D platform game, where players can move around, collect items and interact/take damage with other objects. 
The game has multiple levels, and you can move on to next level ones you have completed the current level. 
The game supports local multiplayer on the same computer.
The project is based on a given [Maven template](https://git.app.uib.no/inf112/22v/inf112.22v.libgdx-template.git). 
We use the game engine [libGDX](https://libgdx.com/) for this project.


## Table of Contents
- [Project Description](#project-description)
- [Requirements](#requirements)
- [Build Instructions](#build-instructions)
- [Credits](#credits)

## Requirements
An IDE running on either Windows or Linux (MacOS probably works, but not tested). You can for example use one of the IDEs [IntelliJ](https://www.jetbrains.com/idea/) or [Eclipse](https://www.eclipse.org/ide/).
The computer must be able to run the game at 60 FPS (the game will feel slow if not).

## Build Instructions

### Setup
1. To run this project, you will need to install [Java](https://www.oracle.com/java/technologies/downloads/) (version 17 recommended. We cannot assure that older versions work).
2. Download the Source code from [here](https://git.app.uib.no/grabbane/inf112.22v.libgdx-template.git).
3. Unzip the source code. 
4. Open the project with an IDE of your choice.
5. Find DesktopLauncher in src/main/java/launcher/DesktopLauncher and press the run button.

### Running
#### Note: The stable version of the project is on the `master` branch.
1. Locate the file "src/main/java/launcher/DesktopLauncher.java".
2. Run DesktopLauncher to start the game.
3. Use "WASD", "Arrow keys" and "IJKL" to control the different players.

### Testing
Known bugs in the game and manual tests can be found [here](src/main/resources/BugReplication.md).

To run unit tests, locate the folder "src/test/java" and run them in your IDE. This will differ depending on IDE. 
In IntelliJ, this is done by right clicking the folder and selecting "Run 'All Tests'" or "More Run/Debug -> Run 'All Tests' with Coverage".

To be able to create unit tests testing logic that uses LibGdx, it is necessary to create a headless application.
This is an application that mocks the graphics component. Therefore, be wary that unit tests of graphics-related components can be difficult. 

## Credits
This project is created by:
- Martin Andvik Øvsttun
- Tobias Soltvedt
- Kristoffer Jensvoll-Johnsen
- Espen Lade Kalvatn

Additional credits:
- Tileset:
    - Tileset from [Multi_Platformer_Tileset_v2](https://shackhal.itch.io/multi-platformer-tileset) by Diego del Solar/"Shackhal" with license: CC0 license (Creative Commons Zero).
- Spritesheet:
  - Sprites from [GameDev Market](https://www.gamedevmarket.net/) with license: [gamedevmarket pro-licence](https://www.gamedevmarket.net/terms-conditions/#pro-licence).
- Music:
  - Music from [OpenGameArt.Org](https://opengameart.org/content/platformer-game-music-pack) by Manuel Bolaños Gómez "CodeManu" with license: (CC-BY 3.0).
- Sound-effects:
  - Sound effects from [OpenGameArt.Org](https://opengameart.org/content/level-up-sound-effects) by Bart Kelsey "bart" with license: (CC-BY 3.0).
  - Sound effects from [OpenGameArt.Org](https://opengameart.org/content/512-sound-effects-8-bit-style) by Juhani Junkala "SubspaceAudio" with license: (CC-BY 3.0).
  - Sound effects from [Mixkit](https://mixkit.co/free-sound-effects/) with license: [Mixkit Sound Effects Free License](https://mixkit.co/license/#sfxFree).
