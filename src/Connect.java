/**
 * Defines what a Connect object is to use it as the logic for ConnectGUI.
 * Supplies one constructor method, six getter methods,
 * three mutator methods, a findRightDiagonalConnectedPieces method,
 * a findLeftDiagonalConnectedPieces method, a findDownDiagonalConnectedPieces method,
 * a findHorizontalDiagonalConnectedPieces method, and a findMatchingPieces method.
 *
 * @author Talha Djibril
 * @author Aidan Graves
 * @author Zachary Norton
 * @author Ethan Perez
 */
public class Connect {
    
    /** The minimum possible number of players */
    public static final int MIN_PLAYERS = 2;
    
    /** The maximum possible size of the index */
    public static final int MAX_INDEX = 5;
    
    /** The maximum possible number of players */
    public static final int MAX_PLAYERS = 4;
    
    /** The maximum possible size of the grid */
    public static final int MAX_GRID_SIZE = 16;
    
    /** The number of turns that all players have played combined */
    private int turnNum;
    
    /** The number of players playing in the game */
    private int numPlayers;
    
    /** The the amount of rows and columns for the grid */
    private int gridSize;
    
    /** The maximum number of turns that gae can go to */
    private int maxTurn;
    
    /** The current maximum of connected pieces a player has */
    private int winConnectedPieces;
    
    /** Is the game finished with a winning player */
    private boolean isGameOverWithWinner;
    
    /** Is the game finished with no winning player */
    private boolean isGameOverTie;
    
    /** 2D array of Player objects of int numPlayers for  */
    private Player[] players;
    
    /** Grid logic instance */
    private Grid grid;
    
    /** Player logic instance */
    private Player currentPlayer;
    
    /**
     * Constructor method that creates a Connect object consisting
     * of a array of players
     *
     * @param gridSize the size of the grid
     * @param numPlayers the number of players in the game
     * @param winConnectedPieces the number of pieces connected when player wins
     * @throws IllegalArgumentException when gridSize is greater than MAX_GRID_SIZE or less than 2
     * @throws IllegalArgumentException when numPlayers is greater than MAX_PLAYERS or
     * less than MIN_PLAYERS
     * @throws IllegalArgumentException when winConnectedPieces is greater than gridSize or
     * less than 1
     */
    public Connect (int gridSize, int numPlayers, int winConnectedPieces) {
        
        if (gridSize > MAX_GRID_SIZE || gridSize < 2) {
            
            throw new IllegalArgumentException("Invalid size");
        }

        if (numPlayers > MAX_PLAYERS || numPlayers < MIN_PLAYERS) {
            
            throw new IllegalArgumentException("Invalid players");
        }
        
        if (winConnectedPieces > gridSize || winConnectedPieces < 1) {
            
            throw new IllegalArgumentException("Invalid pieces");
        }

        this.gridSize = gridSize;
        this.winConnectedPieces = winConnectedPieces;
        this.numPlayers = numPlayers;
        this.maxTurn = gridSize * gridSize;
        this.turnNum = 1;
        isGameOverWithWinner = false;
        isGameOverTie = false;
        
        this.grid = new Grid(gridSize);

        this.players = new Player[MAX_PLAYERS];

        for(int i = 0; i < players.length; i++){
            
            players[i] = new Player();
        }
    }

    /**
     * Mutator method that sets integer currTurn to the specific
     * players turn in the game.
     *
     * @param turnNum the turn number the game is on
     */
    public void determineCurrentPlayer(int turnNum) {
        
        int currTurn = turnNum % numPlayers;
            
        if (currTurn == 1) {

            currentPlayer = players[0];
        }
        else if (currTurn == 2) {

            currentPlayer = players[1];
        }
        else if (currTurn == 3) {
            
            currentPlayer = players[2];
        }
        else if (currTurn == 0) {

            if (numPlayers == 2) {
                
                currentPlayer = players[1];
            }
            else if (numPlayers == 3) {
                
                currentPlayer = players[2];
            }
            else if (numPlayers == MAX_PLAYERS) {
                
                currentPlayer = players[3];                
            }
        }
        else {

            currentPlayer = null;
        }
    }

    /**
     * Getter method that returns the status of a player
     * to a piece on the game.
     *
     * @param currentPlayer the player on the specific turn
     * @return pieceStatus the Status of the pieces based on specific player
     */
    public Cell.Status determineStatus(Player currentPlayer){
        Cell.Status pieceStatus;
        if (currentPlayer == players[0]) {
            
            pieceStatus = Cell.Status.PLAYER_1;
        }
        else if (currentPlayer == players[1]) {
            
            pieceStatus = Cell.Status.PLAYER_2;
        }
        else if (currentPlayer == players[2]) {
            
            pieceStatus = Cell.Status.PLAYER_3;
        }
        else if (currentPlayer == players[3]) {
            
            pieceStatus = Cell.Status.PLAYER_4;
        }
        else {

            pieceStatus = null;
        }

        return pieceStatus;
    }
    
    /**
     * Mutator method that allows the game to place a piece
     * on the board
     *
     * @param col the columns on the board
     * @return availableRow the number at which piece is placed when dropped in column
     * @throws IllegalArgumentException when col is greater than (gridSize - 1) or less than 0
     * @throws IllegalArgumentException when currentPlayer is equal to null
     */
    public int placePiece(int col) {
        
        //Error handling
        if ((col > (gridSize - 1)) || (col < 0)) {
            
            System.out.println("BAD COL: " + col);
            System.out.println("Grid Size: " + gridSize);
            throw new IllegalArgumentException("Invalid column");
        }
        
        //Determine who's turn it is
        determineCurrentPlayer(turnNum);
        
        //Error handling in case currentPlayer is null
        if (currentPlayer == null) {
            
            throw new IllegalArgumentException("Null current player");
        }
        
        //Determine the status of the piece that should be dropped
        Cell.Status pieceStatus = determineStatus(currentPlayer);
        
        //Finding the available row
        int availableRow = -1;
        for (int i = (gridSize - 1); i >= 0; i--) {
            
            if (grid.getStatus(i, col) == Cell.Status.EMPTY) {
                
                availableRow = i;
                break;
            }
        }
        
        //Addressing if column did not work
        //set availableRow equal to unlikely number, if still equal to this number, 
        //means a result was never found
        //There's probably a much better way of doing this
        if (availableRow == -1) { 
            
            return -1;
        }
        
        //Set the Status of the Cell at the availableRow and col to occupied by currentPlayer
        grid.updateStatus(availableRow, col, pieceStatus);
        
        //Find matching pieces and get new numOfMatchingPieces? 
        int playerMaxConnectedPieces = findMatchingPieces(availableRow, col, pieceStatus);
        
        if (currentPlayer.getMaxConnected() < playerMaxConnectedPieces) {
            
            currentPlayer.setMaxConnected(playerMaxConnectedPieces);            
        }

        //Handle win/lose condition
        if (playerMaxConnectedPieces >= winConnectedPieces) {
            isGameOverWithWinner = true;
            currentPlayer.increaseScore();
        }
        
        turnNum++;

        if (turnNum >= (gridSize * gridSize) + 1){
            isGameOverTie = true;
        }
        
        return availableRow;
    }
    
    /**
     * findMatchingPieces method that finds the highest amount
     * of connected pieces on the entire board for that player
     *
     * @param row the row of the piece placed
     * @param col the col of the piece placed
     * @param pieceStatus the status of the cell where piece was placed
     * @return maxConnectedPieces the highest amount of connected pieces on the board
     */
    public int findMatchingPieces(int row, int col, Cell.Status pieceStatus) {
        
        int horiConnectedPieces = findHorizontalConnectedPieces(row, col, pieceStatus);
        
        int downConnectedPieces = findDownConnectedPieces(row, col, pieceStatus);
        
        int leftDiagonalConnectedPieces = findLeftDiagonalConnectedPieces(row, col, pieceStatus);

        int rightDiagonalConnectedPieces = findRightDiagonalConnectedPieces(row, col, pieceStatus);

        int max1 = Math.max(horiConnectedPieces, downConnectedPieces);
        
        int max2 = Math.max(leftDiagonalConnectedPieces, rightDiagonalConnectedPieces);
        
        int maxConnectedPieces = Math.max(max1, max2);

        return maxConnectedPieces;
    }
    
    /**
     * findHorizontalDiagonalConnectedPieces method that finds the amount
     * of connected pieces in a horizontal direction
     *
     * @param row the row of the piece placed
     * @param col the col of the piece placed
     * @param pieceStatus the status of the cell where piece was placed
     * @return connectedPieces the amount of connected pieces in the horizontal direction
     */
    public int findHorizontalConnectedPieces(int row, int col, Cell.Status pieceStatus) {
        
        int connectedPieces = 1;
        
        for (int i = (col - 1); i >= 0; i--) {
            
            if (pieceStatus == grid.getStatus(row, i)) {
                
                connectedPieces++;
            }
            else {
                
                break;
            }

        }

        for (int i = (col + 1); i < gridSize; i++) {
            
            if (pieceStatus == grid.getStatus(row, i)) {
                
                connectedPieces++;
            }
            else {
                break;
            }
        }
        
        return connectedPieces;
    }
    
        /**
     * findDownDiagonalConnectedPieces method that finds the amount
     * of connected pieces in a down direction
     *
     * @param row the row of the piece placed
     * @param col the col of the piece placed
     * @param pieceStatus the status of the cell where piece was placed
     * @return connectedPieces the amount of connected pieces in the down direction
     */
    public int findDownConnectedPieces(int row, int col, Cell.Status pieceStatus) {
        
        int connectedPieces = 1;

        for (int i = (row + 1); i < gridSize; i++){
            if (pieceStatus == grid.getStatus(i, col)){
                connectedPieces++;
            }
            else {
                break;
            }
        }

        return connectedPieces;
    }
    
    /**
     * findLeftDiagonalConnectedPieces method that finds the amount
     * of connected pieces in a left diagonal direction
     *
     * @param row the row of the piece placed
     * @param col the col of the piece placed
     * @param pieceStatus the status of the cell where piece was placed
     * @return connectedPieces the amount of connected pieces in the left diagonal direction
     */
    public int findLeftDiagonalConnectedPieces(int row, int col, Cell.Status pieceStatus) {
        
        int connectedPieces = 1;
        int i = (row - 1);
        int j = (col - 1);

        if (!(i < 0 || j < 0)) {
            
            while (pieceStatus == grid.getStatus(i, j)) {
                connectedPieces++;
                i--;
                j--;
                if (i < 0 || j < 0){
                    break;
                }
            }       
        }

        i = (row + 1);
        j = (col + 1);
        if (!(i >= gridSize || j >= gridSize)) {
            
            while (pieceStatus == grid.getStatus(i, j)) {
                
                connectedPieces++;
                i++;
                j++;
                
                if (i >= gridSize || j >= gridSize) {
                    
                    break;
                }
            }            
        }

        return connectedPieces;
    }
    
    /**
     * findRightDiagonalConnectedPieces method that finds the amount
     * of connected pieces in a right diagonal direction
     *
     * @param row the row of the piece placed
     * @param col the col of the piece placed
     * @param pieceStatus the status of the cell where piece was placed
     * @return connectedPieces the amount of connected pieces in the right diagonal direction
     */
    public int findRightDiagonalConnectedPieces(int row, int col, Cell.Status pieceStatus) {
        
        int connectedPieces = 1;

        int i = (row + 1);
        int j = (col - 1);
        
        if (!(i >= gridSize || j < 0)) {
            
            while (pieceStatus == grid.getStatus(i, j)) {
                
                connectedPieces++;
                i++;
                j--;
                
                if (i >= gridSize || j < 0) {
                    
                    break;
                }
            }            
        }

        i = (row - 1);
        j = (col + 1);
        
        if (!(i < 0 || j >= gridSize)) {
            
            while (pieceStatus == grid.getStatus(i, j)) {
                
                connectedPieces++;
                i--;
                j++;
                
                if (i < 0 || j >= gridSize) {
                    
                    break;
                }
            }            
        }

        return connectedPieces;
    }
    
    /**
     * Getter method that returns the isGameOverWithWinnerwhen game ends with winner
     *
     * @return isGameOverWithWinner boolean determining result of the game
     */
    public boolean getIsGameOverWithWinner() {
        
        return isGameOverWithWinner;
    }
    
    /**
     * Getter method that returns the isGameOverTie when game ends with no winner
     *
     * @return isGameOverTie boolean determining result of the game
     */
    public boolean getIsGameOverTie() {
        
        return isGameOverTie;
    }
    
    /**
     * Getter method that returns the grid
     *
     * @return grid object that makes board size
     */
    public Grid getGrid() {
        
        return grid;
    }
    
    /**
     * Getter method that returns the current player
     *
     * @return currentPlayer the currect player based on what turn
     */
    public Player getCurrentPlayer() {
        
        return currentPlayer;
    }
    
    /**
     * Getter method that returns the player at the index
     *
     * @param index the index of the player
     * @return players[index] the player at the index
     * @throws IllegalArgumentException when index is less than 0 or greater than MAX_INDEX
     */
    public Player getPlayerAtIndex(int index) {
        
        if (index < 0 || index > MAX_INDEX) {
            
            throw new IllegalArgumentException("Invalid index");
        }
        
        return players[index];
    }
    
    /**
     * Mutator method that resets the game for the players
     */
    public void replay() {

        grid = new Grid(gridSize);
        turnNum = 1;

        for (int i = 0; i < players.length; i++) {
            players[i].setMaxConnected(0);
        }
        
        isGameOverTie = false;
        isGameOverWithWinner = false;
    }
}