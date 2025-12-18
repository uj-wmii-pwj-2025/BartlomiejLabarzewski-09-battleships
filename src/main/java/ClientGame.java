import controllers.DisplayController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class ClientGame {

    DisplayController displayController;
    String hostName;
    int port;

    public ClientGame(DisplayController displayController, String hostName, int port) {
        this.displayController = displayController;
        this.hostName = hostName;
        this.port = port;
    }

    public void process() {
        try (Socket clientSocket = new Socket(hostName, port)) {
            System.out.println("Connected to server!");
            PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            Scanner consoleScanner = new Scanner(System.in);
            displayController.setStatusLine("Your turn");
            displayController.draw();
            boolean yourTurn = true;
            while (true) {
                if (yourTurn) {
                    String message = consoleScanner.nextLine();
                    displayController.addChatMessage("YOU: " + message);
                    displayController.setStatusLine("Enemy turn");
                    displayController.draw();
                    writer.println(message);
                    yourTurn = false;
                    if (message.equals("q!")) break;
                }
                else {
                    String message = reader.readLine();
                    displayController.addChatMessage("ENM: " + message);
                    displayController.setStatusLine("Your turn");
                    displayController.draw();
                    yourTurn = true;
                    if (message.equals("q!")) break;
                }
            }
        } catch (UnknownHostException e) {
            throw new RuntimeException("Host \"" + hostName + "\" is unknown...", e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
