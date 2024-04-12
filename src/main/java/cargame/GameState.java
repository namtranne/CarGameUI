package cargame;

public enum GameState {
    PLAYING, MENU, GAME_OVER;

    public static String playerName = "";

    public static GameState gameState = MENU;

    public static void SetGameState(GameState state) {
        gameState = state;
    }

    public static void setPlayerName(String name) {
        playerName = name;
    }
}
