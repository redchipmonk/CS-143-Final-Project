import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.StringTokenizer;

public class MovieList {
    private MovieNode outputRoot;
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
    public MovieNode getOutputRoot() {
        return outputRoot;
    }
}
