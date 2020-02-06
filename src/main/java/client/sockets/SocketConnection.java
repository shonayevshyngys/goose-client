package client.sockets;

import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;

public class SocketConnection extends WebSocketListener {
    String username;

    public SocketConnection(String username) {
        super();
        this.username = username;

    }

    @Override
    public void onClosed(@NotNull WebSocket webSocket, int code, @NotNull String reason) {
        super.onClosed(webSocket, code, reason);
    }

    @Override
    public void onClosing(@NotNull WebSocket webSocket, int code, @NotNull String reason) {
        GameStats.exit = false;
        System.out.println("Server closed");
        System.out.println("Write anything!");
    }

    @Override
    public void onFailure(@NotNull WebSocket webSocket, @NotNull Throwable t, @Nullable Response response) {
        super.onFailure(webSocket, t, response);
    }

    @Override
    public void onMessage(@NotNull WebSocket webSocket, @NotNull String text) {
        System.out.println(text);
        if (text.equalsIgnoreCase("Server: game is starting")){
            System.err.println("Game switched to started");
            GameStats.setIsstarted(true);
        }

        if (text.equalsIgnoreCase("Server: "+GameStats.getUsername() +" is turn to move")){
            System.err.println("It's your turn to move");
            GameStats.setIsYourTurn(true);
        }
    }

    @Override
    public void onMessage(@NotNull WebSocket webSocket, @NotNull ByteString bytes) {
        super.onMessage(webSocket, bytes);
    }

    @Override
    public void onOpen(@NotNull WebSocket webSocket, @NotNull Response response) {
        webSocket.send("/registration "+username);
    }

}
