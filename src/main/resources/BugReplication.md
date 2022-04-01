# Bug Replication / Manuel Tests

- TiledMaps with the attribute "infinite" will not render, remember to deselect after using it in Tiled
    - How to replicate:
        - Open one of the "tiledmaps" in tiled, and make the map infinite in map properties
        - Try to load the level in the game
    - Reason:
        - Unknown
    - Status
        - Still a bug


- You can stick to wall when walking into it
    - How to replicate:
        - Walk next to any wall
        - Jump
        - While in the air, press the button to move towards the wall
        - You are stuck to the wall as long as you hold towards the wall
    - Reason:
        - You are able to walk into a wall while in the air, so the player gains speed towards the wall which lets them
          stick to the wall
    - Status:
        - Fixed
        - Check if there is a side collision before letting you move towards a wall.


- Unable to move away from wall when jumping next to a wall
    - How to replicate:
        - Walk next to any wall
        - Jump
        - While in the air, press the button to move the opposite direction from the wall
        - You are unable to move in that direction when next to the wall
    - Reason:
        - Player doesn't differentiate between right and left collision
    - Status:
        - Fixed
        - Make a left collision and right collision to differentiate between left and right collision so that you can
          walk left when there is a right collision


- Unable to jump after sliding down a slope
    - How to replicate:
        - Stand on flat ground
        - See if you can jump
        - Slide down slope
        - See if you can jump
        - Walk into the corner between the slope and the ground while holding jump to regain the ability to jump
    - Reason:
        - Collision doesn't update when going from the slope to ground
        - This is the same reason why you can keep walking into a goomba without taking damage
    - Status:
        - Still a bug


- Game crashes when trying to reload game
    - How to replicate:
        - Jump off the cliff to restart
        - Game crashes
    - Reason
        - Game deletes and remakes all objects when restarting
        - All objects have bodies, but the bodies aren't deleted when you delete the objects
        - When something tries to interact with the body, it's unable to affect the object since it doesn't exist
          anymore
    - Status:
        - Fixed
        - Deleted all the bodies in the world when reloading the level.


- Players start colliding with each other after a bit of time:
    - How to replicate:
        - Walk around for a few seconds with your players
        - After around 10 seconds, the players should start colliding with each other.
    - Reason:
        - The mask bits is also changed when changing category bits
        - We need to change both at the same time
    - Status:
        - Still a bug


- Zoom not working when at the edges of the map:
    - How to replicate:
        - Walk to the edge of the map with all the characters
        - Walk away with one of the players
        - The camera doesn't zoom out anymore
        - Alternative:
            - Walk away from the edge of the map with your characters
            - Move all except one until the camera hits the edge of the map
            - Move the last one to the edge of the map
            - The camera stays zoomed out
    - Reason:
        - Unknown
    - Status:
        - Still a bug


- goomba moves while on start screen
    - how to replicate:
        - wait for a while before pressing space to start the game
        - see that the first goomba has moved to the left
        - see that the player has lost health
        - if two players, the goomba will launch both players off the map
            - causes game over from start screen
            - model keeps updating, causing repeated game overs
    - reason:
        - likely that the model starts updating after launching, before the player starts the game 
    - status:
        - still a bug


- low jumps on platforms
    - how to replicate:
        - stand on a platform
        - try jumping
        - see that the player jumps lower than usual
    - reason:
        - unknown
    - status:
        - still a bug


- player flickers left and right when jumping up platforms
    - how to replicate:
        - try jumping up to a platform from below
        - see that the player flickers left and right for a bit before settling
    - reason:
        - unknown
    - status:
        - still a bug


- player is alive with -1 hp when dropping
    - how to replicate:
        - hold down to drop (while on ground)
        - touch goomba 3 times until hp == -1
        - see that player cannot interact with coins, platforms or goal
    - reason:
        - likely related to how players are set to dead, and that drop() messes with it
    - status:
        - still a bug
        
## Manuel Tests

- Player can jump up through platforms
  - How to replicate:
    - Move a player underneath a platform and jump (press up).
    - The player should jump up through the platform (and not collide).
    - The player should be able to stand at the top of the platform without falling down through the platform. Platform should feel solid.
  - Reasons it might not work:
    - When trying to change the Category Bit (on/off switch that lets the player move through the platform without colliding), it might not change the Category Bit to the desired Bit or the Mask Bits for the different objects are wrong.
  - Status:
    - It works.

    
- Player can drop down through platforms
    - How to replicate:
        - Move a player on top of a platform. The player should stand at the top of the platform without falling down through the platform. Platform should feel solid.
        - Press down.
        - The player should now drop through the platform and land on the surface underneath. 
        - Move the player on top of the platform again and try again.
    - Reasons it might not work:
        - When trying to change the Category Bit (on/off switch that lets the player move through the platform without colliding), it might not change the Category Bit to the desired Bit or the Mask Bits for the different objects are wrong.
    - Status:
        - It works.


- A player jumping/dropping through a platform should not affect other players on/or off the same platform.
    - How to replicate:
        - Spawn multiple players (two or three) when starting the game.
        - Move all the players on top of a platform player on top of a platform. Make sure that the platform feel solid (not falling through).
        - Press down with one of the players. This should just affect the player performing the action. The rest should stand on top of the players.
        - For the player dropping underneath. Try jumping back up. The player should be able to jump through the platform while the other players are still standing at the platform.
        - Repeat this with the other players.
    - Reasons it might not work:
        - When changing the Category Bit for a player (on/off switch that lets the player move through the platform without colliding), it might set the Category Bit for all players. 
    - Status:
        - It works.
  

- Goomba moves towards a player if the player is within range of the Goomba.
  - How to replicate:
    - Move a player towards a Goomba. The Goomba radar is clearly visible by a circle surrounding the Goomba.
    - Move the player inside of this sensor, and see if the Goomba starts to move towards you.
    - Try to step in and out of the sensor to see it works properly.
    - When no player is within range, see if the Goomba objects is walking back and forth.
  - Status: 
    - It works.


- Player can kill Goombas.
  - How to replicate:
    - Move a player towards a Goomba object.
    - Jump up and press down over the Goomba, such that the player hits the top of the Goomba.
    - See that the Goomba object dies.
    - Check that the HP of the player remains the same.
  - Status:
    - It works.