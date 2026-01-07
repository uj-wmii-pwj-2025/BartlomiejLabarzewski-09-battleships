import controllers.DisplayController;
import map.Board;
import map.BoardObjectCell;
import misc.Coordinates;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import org.jline.utils.NonBlockingReader;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.regex.Pattern;

public class Game {

    ApplicationMode mode;
    DisplayController displayController;
    char[][] map;
    String hostName;
    int port;
    int READ_TIMEOUT = 3000;

    public Game(DisplayController displayController, ApplicationMode mode, char[][] map, String hostName, int port) {
        this.mode = mode;
        this.displayController = displayController;
        this.map = map;
        this.hostName = hostName;
        this.port = port;
    }

    public void start() {
        switch (mode) {
            case ApplicationMode.CLIENT:
                startClient(true);
                break;
            case  ApplicationMode.SERVER:
                startServer(false);
                break;
        }
    }

    public void initializeDisplay() {
        displayController.setPlayerBoard(new Board(map, 10, 10));
        displayController.setEnemyBoard(new Board(10, 10));
        displayController.setChosenField("A1");
    }

    public String generateAnswer(boolean firstTurn, String enemyCellChoice) {

        if (firstTurn) {
            return "start";
        }
        if (enemyCellChoice == null) {
            System.err.println("Enemy field choice is NULL, unreachable");
            return "UNREACHABLE";
        }
        if (displayController.getPlayerBoardController().getObjectStatus(enemyCellChoice) == BoardObjectCell.WATER) {
            return "pudło";
        }
        if (displayController.getPlayerBoardController().getBoardTUIC().getBoard().isAllSunk()) {
            return "ostatni zatopiony";
        }
        if (displayController.getPlayerBoardController().getBoardTUIC().getBoard().isSunk(Coordinates.getCellRow(enemyCellChoice), Coordinates.getCellCol(enemyCellChoice))) {
            return "trafiony zatopiony";
        }
        else {
            return "trafiony";
        }

    }

    public boolean processIfMovement(char input) {
        return switch (input) {
            case 'a' -> {
                displayController.getEnemyBoardController().moveLeft();
                yield true;
            }
            case 's' -> {
                displayController.getEnemyBoardController().moveDown();
                yield true;
            }
            case 'd' -> {
                displayController.getEnemyBoardController().moveRight();
                yield true;
            }
            case 'w' -> {
                displayController.getEnemyBoardController().moveUp();
                yield true;
            }
            default -> false;
        };
    }

    public String getCharName(char ch) {
        return switch (ch) {
            case 'w' -> "UP";
            case 'a' -> "LEFT";
            case 's' -> "DOWN";
            case 'd' -> "RIGHT";
            case '\r' -> "ENTER";
            default -> "???";
        };
    }

    public boolean validateEnemyMessage(String message) {
        if (message == null) return false;
        if (message.indexOf(';') == -1) {
            return message.equals("ostatni zatopiony");
        }
        String answerPart = message.substring(0, message.indexOf(';'));
        String choicePart = message.substring(message.indexOf(';') + 1);
        switch (answerPart) {
            case "start":
            case "pudło":
            case "trafiony":
            case "trafiony zatopiony":
                break;
            default:
                return false;
        }
        return (Pattern.matches("[A-J][1-9]", choicePart) || Pattern.matches("[A-J]10", choicePart));
    }

    public void startClient(boolean first) {
        try (Socket socket = new Socket(hostName, port)) {
            System.out.println("Connected to server!");

            gameLoop(first, socket);
        } catch (UnknownHostException e) {
            throw new RuntimeException("Host \"" + hostName + "\" is unknown...", e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void startServer(boolean first) {

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            Socket socket = serverSocket.accept();
            System.out.println("A client connected to you!");

            gameLoop(first, socket);
        }
        catch (IOException e) {
            throw new RuntimeException("Could not bind to port " + port, e);
        }
    }

    private void gameLoop(boolean first, Socket socket) throws IOException {
        Terminal terminal = TerminalBuilder.builder().system(true).build();
        terminal.enterRawMode();
        NonBlockingReader terminalReader = terminal.reader();

        PrintWriter networkWriter = new PrintWriter(socket.getOutputStream(), true);
        InputStream socketIn = socket.getInputStream();
        BufferedReader networkReader = new BufferedReader(new InputStreamReader(socketIn));

        initializeDisplay();
        displayController.draw();

        boolean yourTurn = first;
        boolean firstTurn = true;

        String enemyFieldChoice = null;
        String yourFieldChoice = null;
        String enemyAnswer = null;

        String yourMessage = null;

        boolean shouldEndGame = false;

        while (!shouldEndGame) {

            if (!firstTurn) socket.setSoTimeout(READ_TIMEOUT);

            if (yourTurn) {

                StringBuilder messageBuilder = new StringBuilder();

                String answer = generateAnswer(firstTurn, enemyFieldChoice);
                messageBuilder.append(answer);

                if (answer.equals("ostatni zatopiony")) {
                    displayController.setStatusLine("Przegrana");
                    shouldEndGame = true;
                }

                else {
                    messageBuilder.append(';');

                    boolean shouldEndTurn = false;

                    while (!shouldEndTurn) {
                        char ch = (char) terminalReader.read();
                        displayController.setStatusLine(getCharName(ch));
                        processIfMovement(ch);
                        if (ch == '\r') shouldEndTurn = true;
                        displayController.draw();
                    }

                    yourFieldChoice = displayController.getEnemyBoardController().getChosenCell();
                    displayController.getEnemyBoardController().markShot(yourFieldChoice);
                    messageBuilder.append(yourFieldChoice);

                    displayController.setStatusLine("Enemy turn");
                }

                yourMessage = messageBuilder.toString();
                displayController.addHistoryMessage("YOU: " + yourMessage);
                displayController.draw();
                networkWriter.println(yourMessage);
                yourTurn = false;
            }
            else {

                String enemyMessage = null;
                boolean received = false;

                // Flushing the stream, perhaps getting the message
                while (networkReader.ready()) {
                    String receivedMessage = networkReader.readLine();
                    if (receivedMessage == null) /* The enemy closed the connection */ {
                        break;
                    }
                }

                for (int i = 0; i < 3 && !received; i++) {
                    try {
                        enemyMessage = networkReader.readLine();
                    }
                    catch (SocketTimeoutException ignore) {}

                    if (validateEnemyMessage(enemyMessage)) {
                        received = true;
                    }
                    else {
                        networkWriter.println(yourMessage);
                        displayController.addHistoryMessage("YOU: " + yourMessage);
                        displayController.draw();
                    }
                }

                if (!received) {
                    displayController.setStatusLine("Błąd komunikacji");
                    shouldEndGame = true;
                }

                else {
                    displayController.addHistoryMessage("ENM: " + enemyMessage);

                    if (enemyMessage.equals("ostatni zatopiony")) {
                        displayController.getEnemyBoardController().markShip(yourFieldChoice);
                        displayController.setStatusLine("Wygrana");
                        shouldEndGame = true;
                    }
                    else if (enemyMessage.indexOf(';') != -1) {
                        enemyAnswer = enemyMessage.substring(0, enemyMessage.indexOf(';'));

                        switch (enemyAnswer) {
                            case "start":
                                break;
                            case "pudło":
                                displayController.getEnemyBoardController().markWater(yourFieldChoice);
                                break;
                            case "trafiony":
                                displayController.getEnemyBoardController().markShip(yourFieldChoice);
                                break;
                            case "trafiony zatopiony":
                                displayController.getEnemyBoardController().markShip(yourFieldChoice);
                                break;
                        }

                        enemyFieldChoice = enemyMessage.substring(enemyMessage.indexOf(';') + 1);
                        displayController.getPlayerBoardController().markShot(enemyFieldChoice);
                        displayController.setStatusLine("Your turn");
                        yourTurn = true;

                    }
                }
                displayController.draw();
            }
            firstTurn = false;
        }
    }
}
