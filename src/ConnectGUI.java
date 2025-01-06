import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

/**
 * GUI class for the Connect game, extending JFrame and implementing ActionListener.
 * This class creates and manages the graphical user interface for the Connect game.
 * @author Talha Djibril
 * @author Aidan Graves
 * @author Zachary Norton
 * @author Ethan Perez
 */
public class ConnectGUI extends JFrame implements ActionListener {

    /** Panel for holding column buttons */
    private JPanel buttonPanel = new JPanel();

    /** Panel for holding the grid of labels */
    private JPanel gridPanel = new JPanel();
    
    /** Panel for holding scoreboard */
    private JPanel scoreboardPanel = new JPanel();

    /** Font used for messages in the game */
    public static final Font WORD = new Font("Courier", 1, 30);

    /** Game logic instance */
    private Connect connect;

    /** Grid of cell labels representing the Connect game board */
    private JLabel[][] cellLabels;

    /** Array of column buttons for the user to click */
    private JButton[] colButtons;
    
    /** Array of text containing player info */
    private JLabel[][] playerInfo;

    /** Grid size (rows and columns) */
    private int gridSize;
    
    /** Number of players */
    private int numPlayers;

    /** Constant for the default window size */
    public static final int DEFAULT_WINDOW_SIZE = 720;

    /** Constant for the default grid size */
    public static final int DEFAULT_GRID_SIZE = 8;

    /** Constant for the number of players */
    public static final int DEFAULT_NUM_PLAYERS = 2;

    /** Constant for the number of connected pieces to win */
    public static final int DEFAULT_WIN_CONNECTED_PIECES = 4;

    /** Border for cells */
    private static final Border CELL_BORDER = new LineBorder(Color.BLACK, 2);
    
    /** Color object that contains info for purple */
    public static final Color PURPLE = new Color(128, 0, 128);

    /** Number of pixels for the default height */
    public static final int DEFAULT_WINDOW_HEIGHT = 50;

    /** Number of pixels for the window height */
    public static final int WINDOW_HEIGHT = 500;

    /** Max number size for a grid to be */
    public static final int MAX_GRID_SIZE = 16;

    /** Max number of players */
    public static final int MAX_PLAYERS = 4;


    /**
     * Constructor to initialize the ConnectGUI with specified parameters.
     * 
     * @param gridSize the size of the grid (number of rows and columns)
     * @param numPlayers the number of players in the game
     * @param winConnectedPieces the number of connected pieces required to win
     */
    public ConnectGUI(int gridSize, int numPlayers, int winConnectedPieces) {

        this.gridSize = gridSize;
        this.numPlayers = numPlayers;

        try {
            
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {
            
            e.printStackTrace();
        }

        connect = new Connect(gridSize, numPlayers, winConnectedPieces);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(DEFAULT_WINDOW_SIZE, DEFAULT_WINDOW_SIZE);
        setTitle("4 Connect Four");

        colButtons = new JButton[gridSize];

        buttonPanel.setLayout(new GridLayout(1, gridSize));
        buttonPanel.setPreferredSize(new Dimension(DEFAULT_WINDOW_SIZE, DEFAULT_WINDOW_HEIGHT));

        for (int i = 0; i < gridSize; i++) {
            
            colButtons[i] = new JButton(" V ");
            colButtons[i].setHorizontalAlignment(SwingConstants.CENTER);
            colButtons[i].addActionListener(this);
            buttonPanel.add(colButtons[i]);
        }

        add(buttonPanel, BorderLayout.NORTH);

        cellLabels = new JLabel[gridSize][gridSize];

        for (int i = 0; i < gridSize; i++) {
            
            for (int j = 0; j < gridSize; j++) {
                
                cellLabels[i][j] = new JLabel(" ");
                cellLabels[i][j].setHorizontalAlignment(JLabel.CENTER);
                cellLabels[i][j].setFont(WORD);
                cellLabels[i][j].setBorder(CELL_BORDER);
                gridPanel.add(cellLabels[i][j]);
            }
        }

        gridPanel.setLayout(new GridLayout(gridSize, gridSize));
        add(gridPanel, BorderLayout.CENTER);
        
        scoreboardPanel.setSize(DEFAULT_WINDOW_SIZE, WINDOW_HEIGHT);
        scoreboardPanel.setLayout(new GridLayout(numPlayers, 3));
        add(scoreboardPanel, BorderLayout.SOUTH);

        playerInfo = new JLabel[numPlayers][3];
        
        for (int i = 0; i < numPlayers; i++) {
            
            playerInfo[i][0] = new JLabel("Player " + (i + 1));
            playerInfo[i][1] = new JLabel("Score: 0");
            playerInfo[i][2] = new JLabel("Max Connected: 0");
            scoreboardPanel.add(playerInfo[i][0]);
            scoreboardPanel.add(playerInfo[i][1]);
            scoreboardPanel.add(playerInfo[i][2]);
        }
        
        setVisible(true);
    }

    /**
     * Main method to run the Connect game GUI with default or command-line settings.
     * 
     * @param args command-line arguments (optional)
     */
    public static void main(String[] args) {
        
        int gridSize = DEFAULT_GRID_SIZE;
        int winConnectedPieces = DEFAULT_WIN_CONNECTED_PIECES;
        int numPlayers = DEFAULT_NUM_PLAYERS;
        boolean validArgs = true;

        if (args.length != 3) {
            
            System.out.println("Usage: java -cp bin ConnectGUI gridSize " + 
                    "winConnectedPieces numPlayers");
            validArgs = false;
        }
        else {
            
            try {
                
                gridSize = Integer.parseInt(args[0]);
                winConnectedPieces = Integer.parseInt(args[1]);
                numPlayers = Integer.parseInt(args[2]);
            } catch (NumberFormatException e) {
                
                System.out.println("gridSize, winConnectedPieces, and numPlayers must be integers");
                validArgs = false;
            }
        }
        
        if ((gridSize < 2 ) || (gridSize > MAX_GRID_SIZE)) {
            
            System.out.println("gridSize must be between 2 and 16");
            validArgs = false;
        }
        
        if ((winConnectedPieces < 1) || (winConnectedPieces > gridSize)) {
            
            System.out.println("winConnectedPieces must be between 1 and gridSize");
            validArgs = false;
        }
        
        if ((numPlayers < 2) || (numPlayers > MAX_PLAYERS)) {
            
            System.out.println("numPlayers must be between 2 and 4");
            validArgs = false;
        }

        if (validArgs) {
            
            new ConnectGUI(gridSize, numPlayers, winConnectedPieces);            
        }
    }

    /**
     * ActionListener method to handle column button clicks.
     * 
     * @param e the ActionEvent triggered by a button click
     */
    public void actionPerformed(ActionEvent e) {

        for (int i = 0; i < colButtons.length; i++) {
            
            if (e.getSource() == colButtons[i]) {
                
                int row = connect.placePiece(i);
    
                if (row == -1) {
                    
                    System.out.println("Column full, try again");
                    return;
                }
    
                Cell.Status status = connect.getGrid().getStatus(row, i);
    
                if (status == Cell.Status.PLAYER_1) {
                    
                    cellLabels[row][i].setText(" O ");
                    cellLabels[row][i].setForeground(Color.RED);
                    playerInfo[0][2].setText("Max Connected: " + 
                            connect.getCurrentPlayer().getMaxConnected());
                } 
                else if (status == Cell.Status.PLAYER_2) {
                    
                    cellLabels[row][i].setText(" X ");
                    cellLabels[row][i].setForeground(Color.BLUE);
                    playerInfo[1][2].setText("Max Connected: " + 
                            connect.getCurrentPlayer().getMaxConnected());
                } 
                else if (status == Cell.Status.PLAYER_3) {
                    
                    cellLabels[row][i].setText(" Y ");
                    cellLabels[row][i].setForeground(Color.ORANGE);
                    playerInfo[2][2].setText("Max Connected: " + 
                            connect.getCurrentPlayer().getMaxConnected());                    
                } 
                else if (status == Cell.Status.PLAYER_4) {
                    
                    cellLabels[row][i].setText(" Z ");
                    cellLabels[row][i].setForeground(PURPLE);
                    playerInfo[3][2].setText("Max Connected: " + 
                            connect.getCurrentPlayer().getMaxConnected());
                }
    
                boolean isColumnFull = true;
                for (int rowCheck = 0; rowCheck < gridSize; rowCheck++) {
                    
                    if (connect.getGrid().getStatus(rowCheck, i) == Cell.Status.EMPTY) {
                        
                        isColumnFull = false;
                        break;
                    }
                }
                
                if (isColumnFull) {
                    
                    colButtons[i].setEnabled(false);
                }
                
                for (int j = 0; j < numPlayers; j++) {

                    playerInfo[j][0].setText("Player " + (j + 1));
                    playerInfo[j][1].setText("Score: " + 
                            connect.getPlayerAtIndex(j).getScore());
                    playerInfo[j][2].setText("Max Connected: " + 
                            connect.getPlayerAtIndex(j).getMaxConnected());
                }
    
                if (connect.getIsGameOverWithWinner() || connect.getIsGameOverTie()) {
                    
                    for (int j = 0; j < colButtons.length; j++) {
                        
                        colButtons[j].setEnabled(false);
                    }
    
                    int dialogOption = 0;
                    if (connect.getIsGameOverWithWinner()) {
                        
                        dialogOption = JOptionPane.showConfirmDialog(null,
                        "Game has been won! Press Yes to replay!");                    
                    }
                    
                    if (connect.getIsGameOverTie()) {
                        
                        dialogOption = JOptionPane.showConfirmDialog(null,
                        "Game ended with tie! Press Yes to replay!");
                    }
                        
                    if (dialogOption == JOptionPane.YES_OPTION) {
                        
                        connect.replay();
    
                        for (int j = 0; j < cellLabels.length; j++) {
                            
                            for (int k = 0; k < cellLabels[j].length; k++) {
                                
                                cellLabels[j][k].setText(" ");
                            }
                        }
                        
                        for (int j = 0; j < numPlayers; j++) {
                            
                            playerInfo[j][0].setText("Player " + (j + 1));
                            playerInfo[j][1].setText("Score: " + 
                                    connect.getPlayerAtIndex(j).getScore());
                            playerInfo[j][2].setText("Max Connected: 0");
                        }
    
                        for (int j = 0; j < colButtons.length; j++) {
                            
                            colButtons[j].setEnabled(true);
                        }
                    }
                    else {
                        
                        int highestScore = 0;
                        int playerNumber = 0;
                        
                        for (int j = 0; j < numPlayers; j++) {
                            
                            if (highestScore < connect.getPlayerAtIndex(j).getScore()) {
                                
                                highestScore = connect.getPlayerAtIndex(j).getScore();
                                playerNumber = j + 1;
                            }
                        }
                        
                        if (highestScore != 0) {
                            
                            JOptionPane.showMessageDialog(null, "Player " + playerNumber + 
                                             " wins with a score of " + highestScore + "!");
                        }
                        else {
                            
                            JOptionPane.showMessageDialog(null, "Everyone tied!");
                        }
                    }
                }
            }
        }
    }
}    