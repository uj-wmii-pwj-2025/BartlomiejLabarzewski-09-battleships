import controllers.DisplayController;
import map.Board;
import map.BoardObjectCell;
import misc.Coordinates;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import org.jline.utils.NonBlockingReader;

import java.io.*;
import java.net.Socket;
import java.net.SocketTimeoutException;
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
            int readTimeout = 3000;
            clientSocket.setSoTimeout(readTimeout);
            System.out.println("Connected to server!");

            Terminal terminal = TerminalBuilder.builder().system(true).build();
            terminal.enterRawMode();
            NonBlockingReader terminalReader = terminal.reader();

            PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);
            InputStream socketIn = clientSocket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(socketIn));

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

            String yourMessage = null;

            boolean shouldEndGame = false;

            while (!shouldEndGame) {
                if (yourTurn) {

                    StringBuilder messageBuilder = new StringBuilder();

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
                        shouldEndGame = true;
                    }
                    else if (displayController.getPlayerBoardController().getBoardTUIC().getBoard().isSunk(Coordinates.getCellRow(enemyFieldChoice), Coordinates.getCellCol(enemyFieldChoice))) {
                        messageBuilder.append("trafiony zatopiony");
                    }
                    else {
                        messageBuilder.append("trafiony");
                    }

                    if (!shouldEndGame) {

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
                    }

                    yourMessage = messageBuilder.toString();

                    displayController.addHistoryMessage("YOU: " + yourMessage);

                    if (shouldEndGame) displayController.setStatusLine("Przegrana");
                    else displayController.setStatusLine("Enemy turn");

                    displayController.draw();

                    writer.println(yourMessage);
                    yourTurn = false;
                }
                else {

                    boolean correctMessage = false;

                    for (int i = 0; i < 3 && !correctMessage; i++) {
                        String enemyMessage;
                        try {
                            while (socketIn.available() > 0) {
                                reader.readLine();
                            }
                            enemyMessage = reader.readLine();
                            displayController.setStatusLine(String.valueOf(i));

                            displayController.addHistoryMessage("ENM: " + enemyMessage);

                            if (enemyMessage.indexOf(';') == -1 && enemyMessage.equals("ostatni zatopiony")) {
                                displayController.getEnemyBoardController().markShip(yourFieldChoice);
                                shouldEndGame = true;
                                correctMessage = true;
                            }
                            else if (enemyMessage.indexOf(';') != -1) {

                                boolean correctAnswer = false;
                                boolean correctCell = false;

                                enemyAnswer = enemyMessage.substring(0, enemyMessage.indexOf(';'));

                                switch (enemyAnswer) {
                                    case "start":
                                        correctAnswer = true;
                                        break;
                                    case "pudło":
                                        correctAnswer = true;
                                        displayController.getEnemyBoardController().markWater(yourFieldChoice);
                                        break;
                                    case "trafiony":
                                        correctAnswer = true;
                                        displayController.getEnemyBoardController().markShip(yourFieldChoice);
                                        break;
                                    case "trafiony zatopiony":
                                        correctAnswer = true;
                                        displayController.getEnemyBoardController().markShip(yourFieldChoice);
                                        break;
                                }

                                enemyFieldChoice = enemyMessage.substring(enemyMessage.indexOf(';') + 1);

                                if (enemyFieldChoice.length() >= 2 && enemyFieldChoice.charAt(0) >= 'A' && enemyFieldChoice.charAt(0) <= 'J') try {
                                    int col = Integer.parseInt(enemyFieldChoice.substring(1));
                                    if (col >= 1 && col <= 10) correctCell = true;
                                }
                                catch (NumberFormatException ignore) {}

                                correctMessage = correctCell && correctAnswer;
                            }
                        }
                        catch (SocketTimeoutException ignore) {}

                        if (!correctMessage) {
                            writer.println(yourMessage);
                            displayController.addHistoryMessage("YOU: " + yourMessage);
                            displayController.draw();
                        }

                    }

                    if (!correctMessage) {
                        displayController.setStatusLine("Błąd komunikacji");
                        shouldEndGame = true;
                    }
                    else if (shouldEndGame) displayController.setStatusLine("Wygrana");
                    else {
                        displayController.getPlayerBoardController().markShot(enemyFieldChoice);
                        displayController.setStatusLine("Your turn");
                    }

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
