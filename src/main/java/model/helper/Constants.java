package model.helper;

public class Constants {
    
    //public static final float PPM = 32.0f;
    public static final float PPM = 100.0f;

    public static final int TILE_SIZE = 64;

    public static final short DEFAULT_BIT = 1;
    public static final short PLAYER_BIT = 2;
    public static final short COIN_BIT = 4;
    public static final short ENEMY_BIT = 8;
    public static final short DESTROYED_BIT = 16;

    /**
     * The bit values that a given body can interact with.
     * TODO: These are not constants! Maybe in the wrong place????????
     */
    public static short PLAYER_MASK_BITS = DEFAULT_BIT | ENEMY_BIT | COIN_BIT;
    public static short ENEMY_MASK_BITS = DEFAULT_BIT | PLAYER_BIT | ENEMY_BIT;
    public static short COIN_MASK_BITS = PLAYER_BIT;
}
