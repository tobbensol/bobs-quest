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
        - Feleted all the bodies in the world when reloading the level.
        
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
  
