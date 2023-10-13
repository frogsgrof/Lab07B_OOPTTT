import javax.swing.*;
import java.awt.*;

public class Board extends JPanel {

    static final int ROW = 3, COL = 3;
    final int WIDTH, HEIGHT;
    Tile[][] tiles = new Tile[ROW][COL];

    public Board(int width, int height) {
        super(new GridLayout(ROW, COL));
        WIDTH = width;
        HEIGHT = height;
        setPreferredSize(new Dimension(WIDTH, HEIGHT));

        for (int row = 0; row < ROW; row++) {
            for (int col = 0; col < COL; col++) {
                tiles[row][col] = new Tile(row, col, width);
                add(tiles[row][col]);
            }
        }
    }

    public void resetBoard() {
        for (Tile[] row : tiles)
            for (Tile tile : row) {
                tile.resetTile();
            }
    }
}
