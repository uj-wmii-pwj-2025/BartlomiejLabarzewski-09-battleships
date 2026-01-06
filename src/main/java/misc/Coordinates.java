package misc;

public class Coordinates {

    public static int getCellRow(String cell) {
        return cell.charAt(0) - 'A';
    }

    public static int getCellCol(String cell) {
        String colPart = cell.substring(1);
        return Integer.parseInt(colPart) - 1;
    }

    public static String getCellFromCoordinates(int row, int col) {
        String rowPart = String.valueOf((char) ('A' + row));
        String colPart = String.valueOf(col + 1);
        return rowPart + colPart;
    }
}
