import java.io.*;
import java.nio.CharBuffer;
import java.nio.file.Files;
import java.nio.file.Path;

public class MapLoader {

    InputStreamReader mapInput;
    int width;
    int height;

    public MapLoader(InputStreamReader mapInput) {
        this.mapInput = mapInput;
        width = 10;
        height = 10;
    }

    public MapLoader(InputStreamReader mapInput, int width, int height) {
        this.mapInput = mapInput;
        this.width = width;
        this.height = height;
    }

    public char[][] load() throws IOException {

        char[][] retVal = new char[height][width];

        int currentChar;

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                currentChar = mapInput.read();
                if (currentChar == -1) {
                    throw new RuntimeException("The map input is too short!");
                }
                retVal[y][x] = (char) currentChar;
            }
        }

        return retVal;
    }
}
