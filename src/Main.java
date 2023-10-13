import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;

public class Main {

    public enum Turn {X, O}


    static final int ROW = 3, COL = 3;
    public static Turn turn = Turn.X;
    static final int MOVES_FOR_WIN = 5,
            MOVES_FOR_TIE = 7;
    static int moveCount = 0;
    static Board gameBoard;
    static final Player xPlayer = new Player(),
            oPlayer = new Player();
    static ImageIcon ticTacToeIcon, xIcon, oIcon, trophyIcon, squiggleIcon;
    static Font hackRegular;
    static final Toolkit toolkit = Toolkit.getDefaultToolkit();
    static final JFrame frame = new JFrame("Tic Tac Toe");

    public static void main(String[] args) {

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        int screenWidth = screenSize.width,
                screenHeight = screenSize.height;
        int gamePanelLen = screenHeight * 3 / 4,
                frameHeight = screenHeight * 7 / 8;

        getImages(gamePanelLen);
        getFonts();

        xPlayer.setIcon(xIcon);
        oPlayer.setIcon(oIcon);

        frame.setSize(gamePanelLen, frameHeight);
        frame.setLocation((screenWidth - gamePanelLen) / 2, (screenHeight - gamePanelLen) / 4);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setIconImage(ticTacToeIcon.getImage());
        frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));

        gameBoard = new Board(gamePanelLen, gamePanelLen);
        frame.add(gameBoard);
        frame.add(Box.createVerticalGlue());

        Box btnBox = Box.createHorizontalBox();
        JButton quitBtn = new JButton("Quit");
        quitBtn.setFocusPainted(false);
        quitBtn.setBackground(Color.WHITE);
        quitBtn.setFont(hackRegular);
        btnBox.add(Box.createHorizontalStrut(gamePanelLen * 3 / 4));
        btnBox.add(quitBtn);
        frame.add(btnBox);
        frame.add(Box.createVerticalGlue());

        frame.setVisible(true);
    }

    public static void checkForGameOver(Player player) {
        if (moveCount >= MOVES_FOR_WIN) {
            if (player.hasWon()) {
                showWinDialog();
            } else if (moveCount >= MOVES_FOR_TIE && isTie()) {
                showTieDialog();
            }
        }
    }

    public static void incrementMoveCount() {
        moveCount++;
    }

    private static boolean isTie() {
        if (Main.moveCount == ROW * COL) return true;
        boolean[][] x = xPlayer.getOwnedTiles();
        boolean[][] o = oPlayer.getOwnedTiles();

        for (int row = 0; row < ROW; row++) {
            if (!(x[row][0] || x[row][1] || x[row][2])) return false;
            if (!(o[row][0] || o[row][1] || o[row][2])) return false;
        }

        for (int col = 0; col < COL; col++) {
            if (!(x[0][col] || x[1][col] || x[2][col])) return false;
            if (!(o[0][col] || o[1][col] || o[2][col])) return false;
        }

        if (!(x[0][0] || x[1][1] || x[2][2]) || !(o[0][0] || o[1][1] || o[2][2])) return false;
        return (x[0][2] || x[1][1] || x[2][0]) && (o[0][2] || o[1][1] || o[2][0]);
    }

    private static void resetGame() {
        turn = Turn.X;
        moveCount = 0;
        gameBoard.resetBoard();
        xPlayer.resetPlayer();
        oPlayer.resetPlayer();
    }

    private static void showWinDialog() {
        if (JOptionPane.showConfirmDialog(null, switch (turn) {
            case X -> "O won! Play again?";
            case O -> "X won! Play again?";
        }, "Win", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, trophyIcon)
                == JOptionPane.YES_OPTION) {
            resetGame();
        } else {
            frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
        }
    }

    private static void showTieDialog() {
        if (JOptionPane.showConfirmDialog(null, "You tied! Play again?", "Tie",
                JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, squiggleIcon)
                == JOptionPane.YES_OPTION) {
            resetGame();
        } else {
            frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
        }
    }

    private static void getImages(int frameWidth) {
        String directory = System.getProperty("user.dir") + "//images//";
        ticTacToeIcon = getSmoothIcon(directory + "//tictactoe.png", frameWidth / 3);
        xIcon = getSmoothIcon(directory + "//x.png", frameWidth / 3);
        oIcon = getSmoothIcon(directory + "//o.png", frameWidth / 3);
        trophyIcon = getSmoothIcon(directory + "//trophy.png", frameWidth / 11);
        squiggleIcon = getSmoothIcon(directory + "//squiggle.png", frameWidth / 11);
    }

    private static ImageIcon getSmoothIcon(String fileName, int sideLen) {
        return new ImageIcon(toolkit.getImage(fileName)) {
            @Override
            public int getIconWidth() {
                return sideLen;
            }
            @Override
            public int getIconHeight() {
                return sideLen;
            }
            @Override
            public synchronized void paintIcon(Component c, Graphics g, int x, int y) {
                g.drawImage(getImage(), x, y, sideLen, sideLen, null);
            }
        };
    }

    private static void getFonts() {
        try {
            hackRegular = Font.createFont(Font.TRUETYPE_FONT,
                    new File(System.getProperty("user.dir") + "//fonts//hack_regular.ttf"))
                    .deriveFont(Font.PLAIN, 20f);
        } catch (FontFormatException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}