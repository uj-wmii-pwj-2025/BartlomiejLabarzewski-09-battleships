import controllers.DisplayController;
import map.Board;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import org.jline.utils.NonBlockingReader;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientGame {

    DisplayController displayController;
    char[][] map;
    String hostName;
    int port;

    public ClientGame(DisplayController displayController, char[][] map, String hostName, int port) {
        this.displayController = displayController;
        this.map = map;
        this.hostName = hostName;
        this.port = port;
    }

    public void process() {
        try (Socket clientSocket = new Socket(hostName, port)) {
            System.out.println("Connected to server!");

            Terminal terminal = TerminalBuilder.builder().system(true).build();
            terminal.enterRawMode();
            NonBlockingReader terminalReader = terminal.reader();

            PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            String chosenField = "A1";

            displayController.setPlayerBoard(new Board(map, 10, 10));
            displayController.setEnemyBoard(new Board(10, 10));
            displayController.setStatusLine("Your turn");
            displayController.setChosenField(chosenField);
            displayController.draw();
            boolean yourTurn = true;
            boolean firstTurn = true;
            while (true) {
                if (yourTurn) {

                    StringBuilder messageBuilder = new StringBuilder();

                    // TODO: Response to enemy attack

                    if (firstTurn) {
                        messageBuilder.append("start");
                    }
                    else {
                        messageBuilder.append("foo");
                    }

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
                                firstTurn = false;
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
        } catch (UnknownHostException e) {
            throw new RuntimeException("Host \"" + hostName + "\" is unknown...", e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
