# Bob's Quest
In this epic 2022 adventure, join Bob in his quest against bats and mice!
Bob's Quest is a 2D platformer game, where players can move around, collect coins and interact with bats and mice.
Play through different levels and explore different terrains and encounter different obstacles.
Play alone or play with up to two people with couch co-op multiplayer.



## Project Description
The 2022 semester assignment in the course INF112 - Introduction to Systems Development at the University of Bergen (UiB).
The project is based on a given [Maven template](https://git.app.uib.no/inf112/22v/inf112.22v.libgdx-template.git). 
We use the game engine [libGDX](https://libgdx.com/) for this project.


## Table of Contents
- [Project Description](#project-description)
- [Requirements](#requirements)
- [Build Instructions](#build-instructions)
  - [Setup](#setup)
  - [Build](#build-optional)
  - [Run from IDE](#run-from-ide)
  - [Run from commandline](#run-from-commandline)
- [Controls](#controls)
- [Testing](#testing)
- [Credits](#credits)
- [License](#license)

## Requirements
An IDE running on either Windows or Linux (MacOS probably works, but not tested). You can for example use one of the IDEs [IntelliJ](https://www.jetbrains.com/idea/) or [Eclipse](https://www.eclipse.org/ide/).
The computer must be able to run the game at 60 FPS (the game will feel slow if not).

## Build Instructions
#### Note: The stable version of the project is on the `master` branch.

### Setup
1. To run this project, you will need to install [Java](https://www.oracle.com/java/technologies/downloads/) (version 17 recommended. We cannot assure that older versions work).
2. Clone the project from our [GitLab repository](https://git.app.uib.no/grabbane/inf112.22v.bob-quest) using either `SSH` or `HTTPS`. 
Links to set up [SSH](https://git.app.uib.no/help/ssh/index.md) and [HTTPS](https://docs.gitlab.com/omnibus/settings/ssl.html) and [tutorial for cloning repository](https://docs.gitlab.com/ee/user/project/repository/#clone-a-repository). Alternatively you can download the source code with the button to the left of the clone button and unzip the source code at the desired location.
3. Inside the IDE of your choice, open the project by 
   - IntelliJ: navigate `File -> New -> Project from Version Control` and paste the link provided by step 2 (either with `SSH` or `HTTPS`).
   - Eclipse: navigate `File -> Import -> Git -> Clone URI` and paste the link provided by step 2 (either with `SSH` or `HTTPS`).
   - Other IDEs have similar ways to import projects from version control systems.
   - If you are downloading the source code directly. Open the project by `File -> New -> Project from Existing Source...` (IntelliJ). Eclipse have similar procedure.

### Build (optional)
1. Install [maven](https://maven.apache.org/download.cgi).
2. Open the terminal of your choice (Command Prompt, Terminal, Git Bash etc).
3. Navigate to the project folder containing `src` using the `cd 'directory_name'` command.
4. When the project folder is located, use the command `mvn package` to build the project.

### Run from IDE
1. To run the project from the IDE, locate the file `src/main/java/launcher/DesktopLauncher.java`.
2. Run DesktopLauncher to start the game.

### Run from commandline
1. To run the project from the commandline, you must first [build](#build-optional) the project as instructed above.
2. Use the commandline to locate the project folder as above and navigate to the `target` folder.
3. From that folder, use the `java -jar bob-quest-app-1.0-SNAPSHOT-fat.jar` command in the commandline to run the project.

## Controls
- Use `WASD`, `Arrow keys` and `IJKL` to control the different players.
- Kill enemies by pressing `S`/`Down`/`K` when jumping on top of enemies.
- Press `Esc` to pause/resume the game.
- Press `R` to restart current level.

## Testing
Known bugs in the game and manual tests can be found [here](src/main/resources/BugReplication.md).

To run unit tests, locate the folder `src/test/java` and run them in your IDE. This will differ depending on IDE. 
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
  - Music by Henrik Hergerland Aga
- Sound-effects:
  - Sound effects from [OpenGameArt.Org](https://opengameart.org/content/level-up-sound-effects) by Bart Kelsey "bart" with license: (CC-BY 3.0).
  - Sound effects from [OpenGameArt.Org](https://opengameart.org/content/512-sound-effects-8-bit-style) by Juhani Junkala "SubspaceAudio" with license: (CC-BY 3.0).
  - Sound effects from [Mixkit](https://mixkit.co/free-sound-effects/) with license: [Mixkit Sound Effects Free License](https://mixkit.co/license/#sfxFree).

## License
The project licensed under Attribution-NonCommercial 4.0 International (CC BY-NC 4.0), and can be found [here](LICENSE).