/**
 * Defines what a Cell object is to use it as an Object for Connect.
 * Supplies one constructor method, one getter method,
 * one setter method, a toString method, and an equals method.
 *
 * @author Talha Djibril
 * @author Aidan Graves
 * @author Zachary Norton
 * @author Ethan Perez
 */
public class Cell {
    
    /** Public enum that keeps track of the condition of the Cell */
    public enum Status {
        
        /** When the Cell has not been claimed, initial state */
        EMPTY, 
        
        /** When the Cell has been claimed by Player 1 */
        PLAYER_1, 
        
        /** When the Cell has been claimed by Player 2 */
        PLAYER_2, 
        
        /** When the Cell has been claimed by Player 3 */
        PLAYER_3, 
        
        /** When the Cell has been claimed by Player 4 */
        PLAYER_4;
    }

    /** Status of the Cell, whether it is empty or claimed by a player */
    private Status status;

    /**
     * Constructor method that creates the Cell and sets the Status
     * to EMPTY by default
     */
    public Cell() {
        
        this.status = Status.EMPTY;
    }

    /**
     * Getter method that returns the Status of the Cell
     *
     * @return status the condition of the Cell
     */
    public Status getStatus() {
        
        return status;
    }

    /**
     * Setter method that changes the Status of the Cell to the
     * desired Status provided the current Status is EMPTY
     *
     * @param s the desired Status to change the current Status to
     */
    public void setStatus(Status s) {
        
        if (status == Status.EMPTY) {
            
            status = s;
        }
    }

    /**
     * toString method that puts the info from this Cell
     * into a String
     *
     * @return s the Cell as a String
     */
    public String toString() {
        
        String s = "";
        s += status;
        return s;
    }

    /**
     * Solely for testing if Cell works, can safely take out in final
     * edition
     *
     * @param args command-line args (not used)
     */
    public static void main(String[] args) {
        
        Cell cell = new Cell();
        System.out.print(cell);
    }
}