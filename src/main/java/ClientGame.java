import controllers.DisplayController;
import map.Board;
import map.BoardObjectCell;
import misc.Coordinates;
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

            displayController.setPlayerBoard(new Board(map, 10, 10));
            displayController.setEnemyBoard(new Board(10, 10));
            displayController.setStatusLine("Your turn");
            displayController.setChosenField("A1");
            displayController.draw();
            boolean yourTurn = true;
            boolean firstTurn = true;

            String enemyFieldChoice = null;
            String yourAnswer = null;
            String yourFieldChoice = null;
            String enemyAnswer = null;

            while (true) {
                if (yourTurn) {

                    StringBuilder messageBuilder = new StringBuilder();

                    // TODO: Response to enemy attack

                    if (firstTurn) {
                        messageBuilder.append("start");
                    }
                    else if (enemyFieldChoice == null) {
                        System.err.println("Enemy field choice is NULL, unreachable");
                    }
                    else if (displayController.getPlayerBoardController().getObjectStatus(enemyFieldChoice) == BoardObjectCell.WATER) {
                        messageBuilder.append("pudło");
                    }
                    else if (displayController.getPlayerBoardController().getBoardTUIC().getBoard().isAllSunk()) {
                        messageBuilder.append("ostatni zatopiony");
                    }
                    else if (displayController.getPlayerBoardController().getBoardTUIC().getBoard().isSunk(Coordinates.getCellRow(enemyFieldChoice), Coordinates.getCellCol(enemyFieldChoice))) {
                        messageBuilder.append("trafiony zatopiony");
                    }
                    else {
                        messageBuilder.append("trafiony");
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
                                yourFieldChoice = displayController.getEnemyBoardController().getChosenCell();
                                messageBuilder.append(yourFieldChoice);
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

                    enemyAnswer = message.substring(0, message.indexOf(';'));
                    displayController.getEnemyBoardController().markShot(yourFieldChoice);
                    switch (enemyAnswer) {
                        case "pudło":
                            displayController.getEnemyBoardController().markWater(yourFieldChoice);
                            break;
                        case "trafiony":
                            displayController.getEnemyBoardController().markShip(yourFieldChoice);
                            break;
                        case "trafiony zatopiony":
                            displayController.getEnemyBoardController().markShip(yourFieldChoice);
                            break;
                        case "ostatni zatopiony":
                            displayController.getEnemyBoardController().markShip(yourFieldChoice);
                            break;
                    }

                    enemyFieldChoice = message.substring(message.indexOf(';') + 1);
                    displayController.getPlayerBoardController().markShot(enemyFieldChoice);

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
