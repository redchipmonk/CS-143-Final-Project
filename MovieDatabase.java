import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * This class represents the storage of a list of movies.
 *
 * @author Alvin Le
 */
public class MovieDatabase {
    private static Map<String, Movie> database;
    private SurveyTree survey;
    /**
     * Default constructor to load example txt
     */
    public MovieDatabase() {
        this("movies.txt");
    }
    /**
     * Constructor to load movies from file
     * @param fileName to read from
     */
    public MovieDatabase(String fileName) {
        database = new HashMap<String, Movie>();
        readFromFile(fileName);
    }
    /**
     * Reads from a txt file to initialize a movie and to put in map
     * @param fileName to read from
     */
    public void readFromFile(String fileName) {
        try {
            Scanner scan = new Scanner(new File(fileName));
            String line;
            Movie movie;
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
                database.put(movie.getTitle(), movie);
            }
            scan.close();
        }
        catch(FileNotFoundException e) {
            System.out.println("Invalid file");
        }
    }
    public Map<String, Movie> getDatabase() {
        if (database != null) {
            return database;
        }
        return null;
    }
    public List<String> generate(SurveyNode node) {
        try {
            List<String> list = new LinkedList<>();
            Scanner scan = new Scanner(System.in);
            survey = new SurveyTree("survey.txt");
            String line = survey.takeSurvey(scan);
            StringTokenizer token = new StringTokenizer(line, "//");
            String genre1 = "";
            String genre2 = "";
            String duration = "";
            String age = "";
            while (token.hasMoreElements()) {
                genre1 = token.nextToken();
                genre2 = token.nextToken();
                duration = token.nextToken();
                age = token.nextToken();
            }
            for (Movie movie : database.values()) {
                String durations = "short";
                if (movie.getMinutes() >= 90) {
                    durations = "long";
                }
                String ages = "new";
                if (movie.getYear() <= 2000) {
                    ages = "old";
                }
                if ((movie.getGenre().equalsIgnoreCase(genre1) || movie.getGenre().equalsIgnoreCase(genre2)) &&
                        durations.equals(duration) && ages.equals(age)) {
                    list.add(movie.getTitle());
                }
            }
            return list;
        }
        catch(FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
    public List<Movie> findRecommendation(Movie movie) {
        Map<Movie, Integer> results = new HashMap<>();
        for (Movie temp : database.values()) {
            int connections = 0;
            if (temp.getGenre().equals(movie.getGenre())) {
                connections++;
            }
            
            if (temp.getYear() == movie.getYear()) {
                connections++;
            }
            
            if (temp.getMinutes() == movie.getMinutes()) {
                connections++;
            }
            results.put(temp, connections);
        }
        List<Movie> recommendations = new ArrayList<>();
    
        // Sort the movies based on the number of connections
        List<Map.Entry<Movie, Integer>> sortedResults = new ArrayList<>(results.entrySet());
        sortedResults.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));
        
        // Add the movies with the highest connections to the recommendations list
        for (Map.Entry<Movie, Integer> entry : sortedResults) {
            recommendations.add(entry.getKey());
        }
        
        return recommendations;
    }
}