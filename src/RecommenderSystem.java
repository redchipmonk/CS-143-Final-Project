import java.io.*;
import java.util.*;
/**
 * This class represents the movie recommender system. It handles all interactions with the collection of movies and
 * most of the logic.
 *
 * @author Alvin Le, Eric Im
 */
public class RecommenderSystem {
    private final List<String> titles;
    private MovieNode overallRoot;
    /**
     * Default constructor that initializes the recommender system by loading movie data from a file.
     * It creates a linked structure of MovieNode objects based on the movie data and list of titles to display.
     *
     * @param fileName name of file to read from
     */
    public RecommenderSystem(String fileName) {
        overallRoot = new MovieList(fileName).getOutputRoot();
        titles = new ArrayList<String>();
        MovieNode current = overallRoot;
        while (current != null) {
            titles.add(current.data.getTitle());
            current = current.next;
        }
    }
    /**
     * This method returns the list of titles of the movies.
     * @return list of movie titles
     */
    public List<String> getTitles() {
        return titles;
    }
    /**
     * Search function of the movie node that matches the title of the key.
     * @param key movie title to search for
     * @return movie node that contains the movie containing the title
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
    /**
     * This method adds a movie to the end of the titles list and linked movies list. It also adds to the last line of
     * the "movies.txt" file in the same format as the other movies.
     * @param movie to add to display
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
    /**
     * This method removes the given movie from titles list and movies linked list.
     * @param title of movie to remove
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
    /**
     * Searches for movies with titles that contain a keyword.
     * @param key entered in search bar to look for certain movies
     * @return list of movies that contain the key in their title
     */
    public List<String> search(String key) {
        List<String> result = new ArrayList<>();
        MovieNode current = overallRoot;
        while (current != null) {
            if (current.data.getTitle().toLowerCase().contains(key.toLowerCase())) {
                result.add(current.data.getTitle());
            }
            current = current.next;
        }
        return result;
    }
    /**
     * Searches for movies that match the given genre
     * @param answer genre to search for
     * @return List of movies that have the given genre
     */
    public List<String> filter(String answer, String query) {
        List<String> list = new LinkedList<String>();
        //if selected genre is all genres
        if (answer.equalsIgnoreCase("All")) {
            return search(query);
        }
        MovieNode current = overallRoot;
        while (current != null) {
            String title = current.data.getTitle().toLowerCase();
            if (current.data.getGenre().equalsIgnoreCase(answer) && title.contains(query)) {
                list.add(current.data.getTitle());
            }
            current = current.next;
        }
        return list;
    }
    /**
     * Generates a list of movie titles based on survey answer. The criteria are provided as a string with delimiter
     * "//". It returns a list of movie titles that match the criteria.
     * @param answer of survey
     * @return list of movies that match the survey results
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
        while (current != null) {
            Movie movie = current.data;
            String durations = "short";
            //movie is considered long if it is over 2 hours
            if (movie.getMinutes() >= 120) {
                durations = "long";
            }
            String ages = "new";
            //a movie is considered old if it is before the year 2000
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
    /**
     * Finds movie recommendations based on the similarity between the given movie and other movies in the recommender
     * system. It calculates the number of connections (similar attributes) between movies.
     * @param movie movie to generate recommendations for
     * @return list of movies sorted by most similar
     */
    public List<Movie> findRecommendation(Movie movie) {
        Map<Movie, Integer> results = new HashMap<>();
        MovieNode current = overallRoot;
        //decade of movie -ex 2002 = 2000
        int decade = movie.getYear() - (movie.getYear()) % 10;

        while (current != null) {
            Movie temp = current.data;
            int connections = 0;
            if (temp.getGenre().equals(movie.getGenre())) {
                connections += 4;
            }
            if (temp.getYear() >= decade && temp.getYear() <= decade + 10) {
                connections += 2;
            }
            //if temp is close to duration of movie
            if (temp.getMinutes() >= movie.getMinutes() - 30 && temp.getMinutes() <= movie.getMinutes() + 30) {
                connections++;
            }
            connections += computeSimilarity(movie.getTitle(), temp.getTitle()) / 4;
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
    /**
     * Calculates the similarity between 2 movie titles by how many similar characters in order
     * @param title1 Movie title
     * @param title2 Movie title
     * @return count of similar characters, 100 if comparing same movie
     */
    private int computeSimilarity(String title1, String title2) {
        if (title1.equalsIgnoreCase(title2)) {
            //arbitrary large number
            return 100;
        }
        char[] arr1 = title1.toCharArray();
        char[] arr2 = title2.toCharArray();
        int length = Math.min(arr1.length, arr2.length);
        int count = 0;
        for (int i = 0; i < length; i++) {
            if (arr1[i] == arr2[i]) {
                count++;
            }
        }
        return count;
    }
}