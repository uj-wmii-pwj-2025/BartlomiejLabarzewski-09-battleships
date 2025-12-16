import controllers.DisplayController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ServerGame {

    DisplayController displayController;
    int port;

    public ServerGame(DisplayController displayController, int port) {
        this.displayController = displayController;
        this.port = port;
    }

    public void process() {

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            Socket socket = serverSocket.accept();
            System.out.println("A client connected to you!");
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
            Scanner consoleScanner = new Scanner(System.in);
            displayController.draw();
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
        }

        catch (IOException e) {
            throw new RuntimeException("Could not bind to port " + port, e);
        }
    }
}
