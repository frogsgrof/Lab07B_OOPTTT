import javax.swing.*;

public class Player {

    static final int ROW = 3, COL = 3;
    boolean[][] ownedTiles = new boolean[ROW][COL];
    ImageIcon icon;

    public Player() {
    }

    public boolean hasWon() {
        return hasRowWin() || hasColWin() || hasDiagWin();
    }

    public void gainTile(Tile tile) {
        ownedTiles[tile.getRow()][tile.getCol()] = true;
    }

    public void resetPlayer() {
        for (int row = 0; row < ROW; row++)
            for (int col = 0; col < COL; col++)
                ownedTiles[row][col] = false;
    }

    public void setIcon(ImageIcon icon) {
        this.icon = icon;
    }

    public ImageIcon getIcon() {
        return icon;
    }

    public boolean[][] getOwnedTiles() {
        return ownedTiles;
    }

    private boolean hasRowWin() {
        for (int row = 0; row < ROW; row++) {
            if (ownedTiles[row][0] && ownedTiles[row][1] && ownedTiles[row][2])
                return true;
        }
        return false;
    }

    private boolean hasColWin() {
        for (int col = 0; col < COL; col++) {
            if (ownedTiles[0][col] && ownedTiles[1][col] && ownedTiles[2][col])
                return true;
        }
        return false;
    }

    private boolean hasDiagWin() {
        return ownedTiles[1][1] && ((ownedTiles[0][0] && ownedTiles[2][2]) || (ownedTiles[0][2] && ownedTiles[2][0]));
    }
}
