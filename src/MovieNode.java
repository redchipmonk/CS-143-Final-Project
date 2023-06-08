/**
 * This class represents in an individual node within a movie linked list. It stores a movie and pointer to the next
 * movie.
 *
 * @author Alvin Le
 */
public class MovieNode {
    public Movie data;
    public MovieNode next;
    /**
     * Creates a MovieNode with the specified Movie object and sets the next node reference to null.
     * @param movie Movie to store as a node
     */
    public MovieNode(Movie movie) {
        data = movie;
        this.next = null;
    }
}