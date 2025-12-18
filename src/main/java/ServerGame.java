import controllers.DisplayController;
import map.Board;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ServerGame {

    DisplayController displayController;
    char[][] map;
    int port;

    public ServerGame(DisplayController displayController, char[][] map, int port) {
        this.displayController = displayController;
        this.map = map;
        this.port = port;
    }

    public void process() {

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            Socket socket = serverSocket.accept();
            System.out.println("A client connected to you!");
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
            Scanner consoleScanner = new Scanner(System.in);
            displayController.setPlayerBoard(new Board(map, 10, 10));
            displayController.setEnemyBoard(new Board(10, 10));
            displayController.setStatusLine("Enemy turn");
            displayController.draw();
            boolean yourTurn = false;
            while (true) {
                if (yourTurn) {
                    String message = consoleScanner.nextLine();
                    displayController.addChatMessage("YOU: " + message);
                    displayController.setStatusLine("Enemy turn");
                    displayController.draw();
                    writer.println(message);
                    yourTurn = false;
                    if (message.equals("q!")) break;
                } else {
                    String message = reader.readLine();
                    displayController.addChatMessage("ENM: " + message);
                    displayController.setStatusLine("Your turn");
                    displayController.draw();
                    yourTurn = true;
                    if (message.equals("q!")) break;
                }
            }
        }
        catch (IOException e) {
            throw new RuntimeException("Could not bind to port " + port, e);
        }
    }
}
