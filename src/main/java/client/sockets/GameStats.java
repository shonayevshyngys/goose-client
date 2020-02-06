package client.sockets;

public class GameStats {
    private static boolean isstarted;
    private static boolean isYourTurn;
    private static String username;
    public static boolean exit;


    public static boolean isExit() {
        return exit;
    }

    public static void setExit(boolean exit) {
        GameStats.exit = exit;
    }

    public static boolean isIsstarted() {
        return isstarted;
    }

    public static void setIsstarted(boolean isstarted) {
        GameStats.isstarted = isstarted;
    }

    public static boolean isIsYourTurn() {
        return isYourTurn;
    }

    public static void setIsYourTurn(boolean isYourTurn) {
        GameStats.isYourTurn = isYourTurn;
    }

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        GameStats.username = username;
    }
}
