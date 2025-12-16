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
            displayController.addHistoryEntry("YOU: szczur");
            displayController.draw();
            writer.println("szczur");
            while (true) {
                String line = reader.readLine();
                if (line.equals("exit")) break;
                else {
                    displayController.addHistoryEntry("ENM: " + line);
                    displayController.draw();
                    String yourLine = consoleScanner.nextLine();
                    displayController.addHistoryEntry("YOU: " + yourLine);
                    displayController.draw();
                    writer.println(yourLine);
                }
            }
        } catch (UnknownHostException e) {
            throw new RuntimeException("Host \"" + hostName + "\" is unknown...", e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
