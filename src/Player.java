/**
 * Defines what a Player object is to use it as an Object for Connect.
 * Supplies one constructor method, two getter methods, one setter methods,
 * a mutator method, a toString method, and an equals method.
 *
 * @author Talha Djibril
 * @author Aidan Graves
 * @author Zachary Norton
 * @author Ethan Perez
 */
public class Player {
    
    /** The current maximum of connected pieces the player has */
    private int maxConnected;
    
    /** The current score of the player */
    private int score;
    
    
    /** 
     * Constructor class for the Player object. Sets the score and maxConnected to zero
     * by default
     */
    public Player() {
        
        this.score = 0;
        this.maxConnected = 0;
    }
    
    /**
     * Getter method that gets the current maximum connected pieces of the player
     *
     * @return maxConnected the current maximum amount of connected pieces
     */
    public int getMaxConnected() {
        
        return maxConnected;
    }
    
    /**
     * Getter method that gets the current score of the player
     *
     * @return score the current score of the player
     */
    public int getScore() {
        
        return score;
    }
    
    /**
     * Setter method that sets the maxConnected equal to their new maximum
     * connected pieces.
     *
     * @param numPieces new maximum connected pieces of the player
     * @throws IllegalArgumentException when numPieces is less than 0
     */
    public void setMaxConnected(int numPieces) {
        
        if (numPieces < 0) {
            
            throw new IllegalArgumentException("Invalid number of connected pieces");
        }
        
        maxConnected = numPieces;
    }
    
    /**
     * Mutator method that increments the score. Called if the player wins a round
     */
    public void increaseScore() {
        
        score++;
    }
    
    /**
     * Equals method that checks to see if another object is
     * a Player object that shares the same maxConnected
     * and score values
     *
     * @param o another Object to be passed in
     * @return true, false based on criteria above
     */
    public boolean equals(Object o) {
        
        if (o instanceof Player) {
            
            Player other = (Player)o;
            
            if ((other.maxConnected == maxConnected) &&
                (other.score == score)) {
                
                return true;
            }
        }
        
        return false;
    }
    
    /**
     * toString method that returns a String containing
     * the maxConnected, score, and piece of a Player object
     *
     * @return s String of information
     */
    public String toString() {
        
        String s = "";
        s += "MaxConnected: " + maxConnected;
        s += " Score: " + score;
        return s;
    }
}