public class MovieNode {
    public Movie data;
    public MovieNode next;
    public MovieNode(Movie movie, MovieNode next) {
        data = movie;
        this.next = next;
    }
    public MovieNode(Movie movie) {
        data = movie;
        this.next = null;
    }
}