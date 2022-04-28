package model.helper;

public class Constants {

    public static final float PPM = 100.0f;
    public static final int TILE_SIZE = 64;


    /**
     * The bit value of each object types.
     * The bit value has to be a power of 2.
     */
    public static final short DESTROYED_BIT = 0;
    public static final short DEFAULT_BIT = 1;
    public static final short PLAYER_BIT = 2;
    public static final short COIN_BIT = 4;
    public static final short ENEMY_BIT = 8;
    public static final short GOAL_BIT = 16;
    public static final short PLATFORM_BIT = 32;
    public static final short CAMERA_WALL_BIT = 64;
    public static final short EDGE_BIT = 128;


    /**
     * The bit values that a given body can interact with.
     */
    public static final short PLAYER_MASK_BITS = DEFAULT_BIT | ENEMY_BIT | COIN_BIT | GOAL_BIT | PLATFORM_BIT | CAMERA_WALL_BIT;
    public static final short GOOMBA_MASK_BITS = DEFAULT_BIT | PLAYER_BIT | ENEMY_BIT | PLATFORM_BIT | EDGE_BIT;
    public static final short FLOATER_MASK_BITS = DEFAULT_BIT | PLAYER_BIT | ENEMY_BIT;
    public static final short INTERACTIVE_MASK_BITS = PLAYER_BIT;
    public static final short DEFAULT_MASK_BITS = DEFAULT_BIT | ENEMY_BIT | PLAYER_BIT;
    public static final short EDGE_MASK_BITS = ENEMY_BIT;
    public static final short DESTROYED_MASK_BITS = 0;
}
