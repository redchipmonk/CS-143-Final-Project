import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.*;
/*
 * This class represents the movie recommender system. It handles all interactions with the 
 * collection of movies.
 */
public class RecommenderSystem {
    private List<String> titles;
    private MovieNode overallRoot;
    /*
     * The constructor initializes the recommender system by loading movie data from a file. 
     * It creates a linked structure of MovieNode objects based on the movie data.
     */
    public RecommenderSystem() {
        overallRoot = new MovieList("movies.txt").getOutputRoot();
        titles = new ArrayList<String>();
        MovieNode current = overallRoot;
        while (current.next != null) {
            titles.add(current.data.getTitle());
            current = current.next;
        }
    }
    /*
     * This method returns the list of titles of the movies.
     */
    public List<String> getTitles() {
        return titles;
    }
    /*
     * This method returns the root.
     */
    public MovieNode getOverallRoot() {
        return overallRoot;
    }
    /*
     * This method iterates through the linkedlist to find the title that matches
     * the title passed in from the parameter.
     * returns the node if found, else null.
     */
    public MovieNode find(String key) {
        MovieNode current = overallRoot;
        while (current != null) {
            if (current.data.getTitle().equalsIgnoreCase(key)) {
                return current;
            }
            current = current.next;
        }
        return null;
    }
    /*
     * This method adds a movie to the end of the linkedlist. It also adds to the last
     * line of the "movies.txt" file in the same format as the other movies.
     */
    public void add(Movie movie) {
        MovieNode current = overallRoot;
        while (current.next != null) {
            current = current.next;
        }
        current.next = new MovieNode(movie);
        titles.add(movie.getTitle());
        try {
            PrintStream out = new PrintStream(new FileOutputStream("movies.txt", true));
            out.println();
            out.print(movie.toString());
            out.close();
        }
        catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
    /*
     * This method removes the movie title that matches the title passed in as parameter.
     * 
     */
    public void remove(String title) {
        if (overallRoot.data.getTitle().equalsIgnoreCase(title)) {
            overallRoot = overallRoot.next;
            titles.remove(0);
        }
        MovieNode current = find(title);
        MovieNode previous = overallRoot;
        while (previous.next != current) {
            previous = previous.next;
        }
        previous.next = current.next;
        titles.remove(title);
    }
    /*
     * This method iterates through the linkedlist and adds the movies that match the 
     * title passed in the parameter. returns a list of movies.
     */
    public List<String> search(String key) {
        List<String> result = new ArrayList<>();
        MovieNode current = overallRoot;
        while (current.next != null) {
            if (current.data.getTitle().toLowerCase().contains(key.toLowerCase())) {
                result.add(current.data.getTitle());
            }
            current = current.next;
        }
        return result;
    }
    /*
     * This method handles the algorithm behind the filter function. it iterates through the
     * movie, comparing it's genre. returns the list of movies that match the genre.
     */
    public List<String> filter(String answer) {
        List<String> list = new LinkedList<String>();
        if (answer.equalsIgnoreCase("All")) {
            return titles;
        }
        MovieNode current = overallRoot;
        while (current.next != null) {
            if (current.data.getGenre().equalsIgnoreCase(answer)) {
                list.add(current.data.getTitle());
            }
            current = current.next;
        }
        return list;
    }
    /*
     * Generates a list of movie titles based on multiple criteria, 
     * including genre, duration, and age. The criteria are provided as a string with delimiter "//". 
     * It returns a list of movie titles that match the criteria.
     */
    public List<String> generate(String answer) {
        List<String> list = new LinkedList<String>();
        StringTokenizer token = new StringTokenizer(answer, "//");
        String genre1 = "";
        String genre2 = "";
        String duration = "";
        String age = "";
        MovieNode current = overallRoot;
        while (token.hasMoreElements()) {
            genre1 = token.nextToken();
            genre2 = token.nextToken();
            duration = token.nextToken();
            age = token.nextToken();
        }
        while (current.next != null) {
            Movie movie = current.data;
            String durations = "short";
            if (movie.getMinutes() >= 90) {
                durations = "long";
            }
            String ages = "new";
            if (movie.getYear() <= 2000) {
                ages = "old";
            }
            if ((movie.getGenre().equalsIgnoreCase(genre1) || movie.getGenre().equalsIgnoreCase(genre2)) &&
                    durations.equalsIgnoreCase(duration) && ages.equalsIgnoreCase(age)) {
                list.add(movie.getTitle());
            }
            current = current.next;
        }
        return list;
    }
    /*
     * Finds movie recommendations based on the similarity between the given movie
     * and other movies in the recommender system. 
     * It calculates the number of connections (similar attributes) between movies and 
     * returns a list of recommended movies sorted by the number of connections.
     */
    public List<Movie> findRecommendation(Movie movie) {
        Map<Movie, Integer> results = new HashMap<>();
        MovieNode current = overallRoot;
        while (current.next != null) {
            Movie temp = current.data;
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
            current = current.next;
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