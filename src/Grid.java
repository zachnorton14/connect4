/**
 * Defines what a Grid object is to use it as an Object for Connect.
 * Supplies one constructor method, three getter methods, a mutator method, 
 * a toString method, and an equals method.
 *
 * @author Talha Djibril
 * @author Aidan Graves
 * @author Zachary Norton
 * @author Ethan Perez
 */
public class Grid {
    
    /** The size of the grid (example 5x5 would have size 5 */
    private int size;
    
    /** 2D array of Cell objects of int size for playing grid */
    private Cell[][] grid;
    
    /** The maximum possible size of the grid */
    private static final int MAX_GRID_SIZE = 16;

    /**
     * Constructor method that creates a Grid object consisting
     * of a 2D array of Cells
     *
     * @param size the length and height of the grid
     * @throws IllegalArgumentException when size is less than 1
     */
    public Grid(int size) {
        
        if (size < 1) {
            
            throw new IllegalArgumentException("Invalid size");
        }

        this.size = size;
        grid = new Cell[this.size][this.size];

        for (int i = 0; i < grid.length; i++) {
            
            for (int j = 0; j < grid[i].length; j++) {
                
                grid[i][j] = new Cell();
            }
        }
    }

    /**
     * Getter method that returns the size of the grid
     *
     * @return size the length and height of the grid
     */
    public int getSize() {
        
        return size;
    }

    /**
     * Getter method that returns the 2D array grid
     *
     * @return grid 2D array of Cell objects of int size for playing grid 
     */
    public Cell[][] getGrid() {
        
        return grid;
    }

    /**
     * Getter method that returns the status of a Cell
     * at a certain position
     *
     * @param row the row of the desired Cell
     * @param col the col of the desired Cell
     * @return grid[row][col].getStatus the Status of desired cell
     * @throws IllegalArgumentException when row is invalid (< 0 || >= 16)
     * @throws IllegalArgumentException when col is invalid (< 0 || >= 16)
     */
    public Cell.Status getStatus(int row, int col) {
        if ((row < 0) || (row >= MAX_GRID_SIZE)) {
            
            System.out.println("BAD ROW: " + row);
            throw new IllegalArgumentException("Invalid row");
        }
        if ((col < 0) || (col >= MAX_GRID_SIZE)) {
            
            throw new IllegalArgumentException("Invalid col");
        }

        return grid[row][col].getStatus();
    }

    /**
     * Mutator method that changes the Status of a desired Cell
     *
     * @param row the row of the desired Cell
     * @param col the col of the desired Cell
     * @param s desired Status of specified Cell
     * @throws IllegalArgumentException when row is invalid (< 0 || >= 16)
     * @throws IllegalArgumentException when col is invalid (< 0 || >= 16)
     */
    public void updateStatus(int row, int col, Cell.Status s) {
        
        if ((row < 0) || (row >= MAX_GRID_SIZE)) {
            
            throw new IllegalArgumentException("Invalid row");
        }
        if ((col < 0) || (col >= MAX_GRID_SIZE)) {
            
            throw new IllegalArgumentException("Invalid col");
        }

        grid[row][col].setStatus(s);
    }

    /**
     * Equals method that compares another Object with this object
     * Returns true if the size and elements of the 2D array grid
     * of the supposed Grid objects are the same
     *
     * @param o another object
     * @return true, false based on criteria above
     */
    public boolean equals(Object o) {
        
        if (o instanceof Grid) {
            
            Grid other = (Grid)o;

            boolean gridsAreEqual = true;
            
            if (grid.length == other.grid.length) {

                for (int i = 0; i < grid.length; i++) {
                    
                    if (grid[i].length == other.grid[i].length) {
                        
                        for (int j = 0; j < grid[i].length; j++) {
                            
                            if (!grid[i][j].equals(other.grid[i][j])) {
                                
                                gridsAreEqual = false;
                                break;
                            }
                        }
                    }
                    else {
                        
                        gridsAreEqual = false;
                        break;
                    }
                }
            }
            else {
                
                gridsAreEqual = false;
            }
            
            if ((size == other.size) && (gridsAreEqual)) {
                
                return true;
            }
            else {
                
                return false;
            }
        }
        else {
            
            return false;
        }
    }

    /**
     * toString method that prints the Grid as a string by
     * printing each element in each row and column, organized
     * by '|' and a new line for each row.
     *
     * @return s the Grid object content as a String
     */
    public String toString() {
        
        String s = "";

        for (int i = 0; i < grid.length; i++) {
            
            s += "|";
            for (int j = 0; j < grid[i].length; j++) {
                
                s += grid[i][j] + "|";
            }
            
            s += "\n";
        }

        return s;
    }
}