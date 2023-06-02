import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.StringTokenizer;

public class UsersDatabase {
    private static Map<String, User> users;
    private Map<String, Movie> movies;

    /**
     * Default constructor to load example txt
     */
    public UsersDatabase() {
        this("users.txt");
    }
    /**
     * Constructor to load users from file
     * @param fileName to read from
     */
    public UsersDatabase(String fileName) {
        users = new HashMap<String, User>();
        movies = new MovieDatabase().getDatabase();
        readFromFile(fileName);
    }
    /**
     * Reads from a txt file to initialize a user and to put in map
     * @param fileName to read from
     */
    public void readFromFile(String fileName) {
        try {
            Scanner scan = new Scanner(new File(fileName));
            String line;
            User user;
            StringTokenizer token;
            while (scan.hasNextLine()) {
                line = scan.nextLine();
                token = new StringTokenizer(line, "//");
                user = new User();
                user.setName(token.nextToken());
                Map<Movie, Integer> rating = new HashMap<>();
                while (token.hasMoreElements()) {
                    Movie movie = movies.get(token.nextToken());
                    rating.put(movie, Integer.parseInt(token.nextToken()));
                }
                user.setRatings(rating);
                users.put(user.getName(), user);
            }
            scan.close();
        }
        catch(FileNotFoundException e) {
            System.out.println("Invalid file");
        }
    }
    public Map<String, User> getDatabase() {
        if (users != null) {
            return users;
        }
        return null;
    }
}
