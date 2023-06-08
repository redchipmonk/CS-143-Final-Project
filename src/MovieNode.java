/*
 * This class represents a node in a linked list for storing Movie objects.
 */
public class MovieNode {

    public Movie data;
    public MovieNode next;
    public MovieNode(Movie movie, MovieNode next) {
        data = movie;
        this.next = next;
    }
    /*
     * This constructor creates a MovieNode with the specified Movie object 
     * and sets the next node reference to null.
     */
    public MovieNode(Movie movie) {
        data = movie;
        this.next = null;
    }
}