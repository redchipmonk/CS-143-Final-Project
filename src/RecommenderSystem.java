import java.util.*;

public class RecommenderSystem {
    //private Map<String, Movie> movies;
    private List<String> titles;
    //private List<Movie> genre;
    private MovieNode overallRoot;
    public RecommenderSystem() {
//        movies = new MovieDatabase().getDatabase();
//        titles = movies.keySet().stream().toList();
//        genre = new ArrayList<>();
        overallRoot = new MovieList("movies.txt").getOutputRoot();
        titles = new LinkedList<String>();
        MovieNode current = overallRoot;
        while (current.next != null) {
            titles.add(current.data.getTitle());
            current = current.next;
        }
    }
    public List<String> getTitles() {
        return titles;
    }
//    public Map<String, Movie> getDatabase() {
//        return movies;
//    }
    public MovieNode getOverallRoot() {
        return overallRoot;
    }
//    public List<String> sortByTitle() {
//        int j;
//        for (int i = 0; i < titles.size(); i++) {
//            j = i - 1;
//            String temp = titles.get(i);
//            while (j >= 0 && titles.get(j).compareToIgnoreCase(temp) > 0) {
//                titles.set(j + 1, titles.get(j));
//                j--;
//            }
//            titles.set(j + 1, temp);
//        }
//        return titles;
//    }
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
    public Movie find(String key) {
        MovieNode current = overallRoot;
        while (current.next != null) {
            if (current.data.getTitle().equalsIgnoreCase(key)) {
                return current.data;
            }
            current = current.next;
        }
        return null;
    }
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