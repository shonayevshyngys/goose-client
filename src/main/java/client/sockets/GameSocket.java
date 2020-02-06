package client.sockets;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.WebSocket;

import java.util.Scanner;

public class GameSocket {
    private Request request;
    private SocketConnection connection;
    private OkHttpClient client;
    private WebSocket ws;

    public GameSocket(String address, String username){
        request = new Request.Builder().url(address).build();
        connection = new SocketConnection(username);
        client = new OkHttpClient();
        ws = client.newWebSocket(request, connection);
    }

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    public SocketConnection getConnection() {
        return connection;
    }

    public void setConnection(SocketConnection connection) {
        this.connection = connection;
    }

    public OkHttpClient getClient() {
        return client;
    }

    public void setClient(OkHttpClient client) {
        this.client = client;
    }

    public WebSocket getWs() {
        return ws;
    }

    public void setWs(WebSocket ws) {
        this.ws = ws;
    }

    public void play(){
        Scanner sc = new Scanner(System.in);
        GameStats.exit = true;
        while (GameStats.exit){
            String command = sc.nextLine();
            if (command.contains("/chat")){
                ws.send(command);
            }
            if (command.equalsIgnoreCase("/showplayers") || command.equalsIgnoreCase("/start")){
                ws.send(command.toLowerCase());
            }
            if (command.equalsIgnoreCase("/roll") && GameStats.isIsYourTurn()){
                ws.send("/roll");
                GameStats.setIsYourTurn(false);
            }
            System.out.println();
        }

    }


}
