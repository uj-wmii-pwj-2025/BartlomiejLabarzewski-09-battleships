import controllers.DisplayController;
import map.Board;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import org.jline.utils.NonBlockingReader;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

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

            Terminal terminal = TerminalBuilder.builder().system(true).build();
            terminal.enterRawMode();
            NonBlockingReader terminalReader = terminal.reader();

            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            String chosenField = "A1";

            displayController.setPlayerBoard(new Board(map, 10, 10));
            displayController.setEnemyBoard(new Board(10, 10));
            displayController.setStatusLine("Enemy turn");
            displayController.setChosenField(chosenField);
            displayController.draw();

            boolean yourTurn = false;
            while (true) {
                if (yourTurn) {
                    StringBuilder messageBuilder = new StringBuilder();
                    // TODO: Response to enemy attack

                    messageBuilder.append("foo");

                    messageBuilder.append(';');

                    boolean shouldEndTurn = false;

                    char ch;

                    while (!shouldEndTurn) {
                        ch = (char) terminalReader.read();
                        displayController.setStatusLine(ch == '\r' ? "ENTER" : String.valueOf(ch));
                        switch (ch) {
                            case 'a':
                                displayController.getEnemyBoardController().moveLeft();
                                break;
                            case 's':
                                displayController.getEnemyBoardController().moveDown();
                                break;
                            case 'd':
                                displayController.getEnemyBoardController().moveRight();
                                break;
                            case 'w':
                                displayController.getEnemyBoardController().moveUp();
                                break;
                            case '\r':
                                messageBuilder.append(displayController.getEnemyBoardController().getChosenCell());
                                shouldEndTurn = true;
                                break;
                        }
                        displayController.draw();
                    }

                    String message = messageBuilder.toString();

                    displayController.addChatMessage("YOU: " + message);
                    displayController.setStatusLine("Enemy turn");
                    displayController.draw();

                    writer.println(message);
                    yourTurn = false;
                }
                else {
                    String message = reader.readLine();
                    displayController.addChatMessage("ENM: " + message);
                    displayController.setStatusLine("Your turn");
                    displayController.draw();
                    yourTurn = true;
                }
            }
        }
        catch (IOException e) {
            throw new RuntimeException("Could not bind to port " + port, e);
        }
    }
}
