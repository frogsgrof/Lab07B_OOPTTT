import javax.swing.*;
import java.awt.*;

public class Tile extends JButton {

    Player owner;
    final int row, col;

    public Tile(int row, int col, int tileLen) {
        super();
        this.row = row;
        this.col = col;

        addActionListener(e -> beChosen());
        setFocusPainted(false);
        setBackground(Color.WHITE);
        setMaximumSize(new Dimension(tileLen, tileLen));
    }

    public void resetTile() {
        owner = null;
        setIcon(null);
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    private void beChosen() {
        if (owner == null) {
            owner = switch (Main.turn) {
                case X -> {
                    Main.turn = Main.Turn.O;
                    yield Main.xPlayer;
                }
                case O -> {
                    Main.turn = Main.Turn.X;
                    yield Main.oPlayer;
                }
            };
            owner.gainTile(this);
            setIcon(owner.getIcon());
            Main.incrementMoveCount();
            Main.checkForGameOver(owner);
        }
    }
}
