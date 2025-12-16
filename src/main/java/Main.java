import controllers.DisplayController;
import tuic.*;

import java.io.File;
import java.nio.file.Paths;

public class Main {

    private static ApplicationMode mode;
    private static int port;
    private static File mapFile;
    private static String hostName;

    private static boolean modeSet = false;
    private static boolean portSet = false;
    private static boolean mapSet = false;
    private static boolean hostSet = false;

    public static void main(String[] args) {

        System.out.println("Hello World!\n");

        if (!tryParsing(args)) {
            System.err.println("Invalid arguments!");
            return;
        }

        if (mode == ApplicationMode.SERVER && hostSet) {
            System.err.println("-host argument is only for the client mode!");
            return;
        }

        System.out.println("Mode: " + mode);
        System.out.println("Port: " + port);
        System.out.println("Host: " + hostName);
        System.out.println("Map: " + mapFile.toPath().toAbsolutePath());

        System.out.println("Loading Map...");

        if (!mapFile.exists()) {
            throw new RuntimeException("Map file not found: " + mapFile.getAbsolutePath());
        }

        if (!mapFile.isFile()) {
            throw new RuntimeException("Map file is not a normal file!");
        }

        if (!mapFile.canRead()) {
            throw new RuntimeException("Map file is not readable!");
        }

        DisplayLoader displayLoader = new DisplayLoader();
        TUIC display = displayLoader.load();
        DisplayController displayController = displayLoader.getController();

        if (mode == ApplicationMode.SERVER) {
            ServerGame game = new ServerGame(displayController, port);
            game.process();
        }
        else if (mode == ApplicationMode.CLIENT) {
            ClientGame game = new ClientGame(displayController, hostName, port);
            game.process();
        }

    }

    private static boolean tryParsing(String[] args) {

        int currentArgument = 0;

        while (currentArgument < args.length) {

            switch (args[currentArgument]) {
                case "-mode":
                    if (tryParsingMode(args, currentArgument)) {
                        currentArgument += 2;
                        modeSet = true;
                        break;
                    }
                    else return false;
                case "-port":
                    if (tryParsingPort(args, currentArgument)) {
                        currentArgument += 2;
                        portSet = true;
                        break;
                    }
                    else return false;
                case "-host":
                    if (tryParsingHost(args, currentArgument)) {
                        currentArgument += 2;
                        hostSet = true;
                        break;
                    }
                    else return false;
                case "-map":
                    if (tryParsingMap(args, currentArgument)) {
                        currentArgument += 2;
                        mapSet = true;
                        break;
                    }
                    else return false;
                default:
                    System.err.println("Unknown parameter type: " + args[currentArgument]);
                    return false;
            }
        }

        return true;

    }

    private static boolean tryParsingMode(String[] args, int currentArgument) {

        if (currentArgument + 1 >= args.length) {
            System.err.println("Invalid arguments for parameter: " + args[currentArgument]);
            return false;
        }

        if (modeSet) {
            System.err.println("Mode already specified!");
            return false;
        }

        if (args[currentArgument + 1].equals("server")) {
            mode = ApplicationMode.SERVER;

            return true;
        }

        else if (args[currentArgument + 1].equals("client")) {
            mode = ApplicationMode.CLIENT;

            return true;
        }

        System.err.println("Invalid mode: " + args[currentArgument + 1]);
        return false;

    }

    private static boolean tryParsingPort(String[] args, int currentArgument) {

        if (currentArgument + 1 >= args.length) {
            System.err.println("Invalid arguments for parameter: " + args[currentArgument]);
            return false;
        }

        if (portSet) {
            System.err.println("Port already specified!");
            return false;
        }

        try {
            int portArg = Integer.parseInt(args[currentArgument + 1]);

            if (portArg < 0 || portArg > 65535) {
                System.err.println("Invalid port number (has to be in [0, 65535]: " + portArg);
                return false;
            }

            port = portArg;

            return true;
        }
        catch (NumberFormatException e) {
            System.err.println("Invalid port number: " + args[currentArgument + 1]);
            return false;
        }

    }

    private static boolean tryParsingMap(String[] args, int currentArgument) {

        if (currentArgument + 1 >= args.length) {
            System.err.println("Invalid arguments for parameter: " + args[currentArgument]);
            return false;
        }

        if (mapSet) {
            System.err.println("Map already specified!");
            return false;
        }

        File mapFileArg = Paths.get(args[currentArgument + 1]).toFile();

        if (!mapFileArg.exists()) {
            System.err.println("Map file does not exist: " + mapFileArg.getAbsolutePath());
            return false;
        }

        if (!mapFileArg.isFile()) {
            System.err.println("Map file is not a normal file: " + mapFileArg.getAbsolutePath());
            return false;
        }

        if (!mapFileArg.canRead()) {
            System.err.println("Can't read map file: " + mapFileArg.getAbsolutePath());
            return false;
        }

        mapFile = mapFileArg;

        return true;

    }

    private static boolean tryParsingHost(String[] args, int currentArgument) {

        if (currentArgument + 1 >= args.length) {
            System.err.println("Invalid arguments for parameter: " + args[currentArgument]);
            return false;
        }

        if (hostSet) {
            System.err.println("Host already specified!");
            return false;
        }

        hostName = args[currentArgument + 1];

        return true;

    }

}
