import java.io.*;
import java.util.*;
/**
 * The MovieList class is responsible for reading movie data from a file and creating a linked list of MovieNode
 * objects, storing a database.
 *
 * @author Alvin Le
 */
public class MovieList {
    private MovieNode outputRoot;
    /**
     * The constructor reads from a file and creates Movie objects from the data as nodes and constructs a linked list
     * of movies.
     * @param fileName String name of file
     */
    public MovieList(String fileName) {
        try {
            Scanner scan = new Scanner(new File(fileName));
            String line;
            Movie movie = new Movie();
            outputRoot = new MovieNode(movie);
            MovieNode current = outputRoot;
            StringTokenizer token;
            while (scan.hasNextLine()) {
                line = scan.nextLine();
                //Data separation
                token = new StringTokenizer(line, "//");
                movie = new Movie();
                while (token.hasMoreElements()) {
                    movie.setTitle(token.nextToken());
                    movie.setDescription(token.nextToken());
                    movie.setYear(Integer.parseInt(token.nextToken()));
                    movie.setGenre(token.nextToken());
                    movie.setMinutes(Integer.parseInt(token.nextToken()));
                }
                current.next = new MovieNode(movie);
                current = current.next;
            }
            scan.close();
            outputRoot = outputRoot.next;
        }
        catch(FileNotFoundException e) {
            System.out.println("Invalid file");
        }
    }
    /**
     * Getter for root of movie linked list
     * @return overall root of movie linked list
     */
    public MovieNode getOutputRoot() {
        return outputRoot;
    }
}