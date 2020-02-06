import client.RetrofitWrapper;
import client.calls.*;
import client.model.Credentials;
import client.sockets.GameSocket;
import client.sockets.GameStats;


import java.util.ArrayList;
import java.util.Scanner;

public class Main{
    public static void main(String[] args){

        Scanner sc = new Scanner(System.in);
        String token = null;
        String usernameGlobal = null;
        String httpAddress = null;
        String wsAddress = null;
        System.out.println("Welcom to goose game!");
        System.out.println("Please enter IP address of server with a port");
        System.out.println("It can be different depending where server is running! But you still can play it in local network!");
        System.out.println("Example: 127.0.0.1:7000 or something like this 192.168.1.243:7000 if your server runs in local network");
        boolean isConnected = false;
        while (!isConnected){
            String ipAddress = sc.next();
            httpAddress = "http://"+ipAddress;
            wsAddress = "ws://"+ipAddress;
            RetrofitWrapper wrapper = new RetrofitWrapper(httpAddress); //init retrofit
            TestConnectionCall testConnection = new TestConnectionCall();
            isConnected = testConnection.testConnection();
            if (isConnected){
                System.out.println("Connected to server");
            }
            else {
                System.out.println("Sorry, this address doesn't respond.");
                System.out.println("Try again");
            }
        }
        boolean mainLoop = true;
        while (mainLoop){
            System.out.println("Welcome to the goose game!");
            System.out.println("Please login or if you don't have an account sign up!");
            System.out.println("1: login");
            System.out.println("2: sign up");
            System.out.println("Enter only the number");

            String gameToConnect = null;
            boolean loggedIn = false;
            while (!loggedIn){
                int num = sc.nextInt();
                if (num == 1){
                    LoginCall login = new LoginCall();
                    System.out.println("Please enter your credentials");
                    System.out.println("Login:");
                    String username = sc.next();
                    System.out.println("Password:");
                    String password = sc.next();
                    Credentials cr = new Credentials(username, password);
                    loggedIn = login.login(cr);
                    if (loggedIn){
                        usernameGlobal = cr.getUsername();
                        token = login.getToken();
                    }
                    else {
                        System.out.println("Bad credentials, please try again!");
                        System.out.println("Enter the number");
                    }
                }
                if (num == 2){
                    SignUpCall signUp = new SignUpCall();
                    System.out.println("Please enter your new login and password to register in system");
                    System.out.println("Login:");
                    String username = sc.next();
                    System.out.println("Password:");
                    String password = sc.next();
                    Credentials cr = new Credentials(username, password);
                    boolean createdAccount = signUp.signUp(cr);
                    if (createdAccount){
                        System.out.println("Your account created!");
                        System.out.println("Logging in");
                        LoginCall login = new LoginCall();
                        login.login(cr);
                        loggedIn = true;
                        token = login.getToken();
                        usernameGlobal = cr.getUsername();
                    }
                    else {
                        System.out.println("User exists please try again or something went wrong!");
                        System.out.println("Enter the number");
                    }
                }
            }
            System.out.println("Welcome "+usernameGlobal);
            boolean mainMenu = false;
            System.out.println("This is main menu of the game");
            System.out.println("You can connect to existing game");
            System.out.println("Or you can create your own lobby");
            while (!mainMenu){
                System.out.println("1: See the list of available lobbies");
                System.out.println("2: Create your own lobby");
                System.out.println("3: exit");
                int num = sc.nextInt();
                if (num == 3 ) System.exit(130);
                boolean enteredTheGame = false;
                if (num == 1){
                    GetLobbiesCall getLobbies =  new GetLobbiesCall();
                    boolean gotGames = getLobbies.getLobbies(token);
                    if (gotGames){
                        ArrayList<String> gamesNames = getLobbies.getNames();
                        if (gamesNames.size() == 0){
                            System.out.println("There's no available games but you can create your own lobby!");
                        }
                        else {
                            int counter = 0;
                            for (String line : gamesNames){
                                System.out.println(counter+": "+line);
                                counter++;
                            }
                            System.out.println("if you want to connect any of this game then type the number of game from list");
                            int numOfGame = sc.nextInt();
                            if (numOfGame >= gamesNames.size() || numOfGame<0){  //careful there
                                System.out.println("Sorry wrong number");
                            }
                            else {
                                gameToConnect = gamesNames.get(numOfGame);
                                enteredTheGame = true;
                            }
                        }

                    }
                    else {
                        System.out.println("Sorry something went wrong");
                    }
                }
                if (num == 2){
                    CreateGameCall createGame = new CreateGameCall();
                    System.out.println("Please name your lobby without space like this 'goose_game':");
                    String lobbyName = sc.next();
                    boolean createdGame = createGame.createGame(lobbyName, token);
                    if (createdGame){
                        gameToConnect = lobbyName;
                        enteredTheGame = true;
                        System.out.println("Game is created");
                    }
                    else {
                        System.out.println("Game isn't created. Maybe the with same name exists or something went wrong");
                    }
                }


                if (enteredTheGame){
                    System.out.println(wsAddress+"/game_"+gameToConnect);
                    GameSocket gameSocket = new GameSocket(wsAddress+"/game_"+gameToConnect, usernameGlobal);
                    System.out.println("Type /chat [your_message] - to send something on chat");
                    System.out.println("Type /showplayer - to see players in lobby");
                    System.out.println("Type /roll - to move, work only on your turn");
                    System.out.println("Type /start - to start game, works only if you created game");
                    GameStats.setIsstarted(false);
                    GameStats.setUsername(usernameGlobal);
                    GameStats.setIsYourTurn(false);
                    gameSocket.play();
                }
                GameStats.exit = true;
            }
        }


    }
}
