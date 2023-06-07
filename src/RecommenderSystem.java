import java.util.*;

public class RecommenderSystem {
    private Map<String, Movie> movies;
    private List<String> titles;
    private List<Movie> genre;
    public RecommenderSystem() {
        movies = new MovieDatabase().getDatabase();
        titles = movies.keySet().stream().toList();
        genre = new ArrayList<>();
    }
    public Map<String, Movie> getDatabase() {
        return movies;
    }
    public List<String> sortByTitle() {
        int j;
        for (int i = 0; i < titles.size(); i++) {
            j = i - 1;
            String temp = titles.get(i);
            while (j >= 0 && titles.get(j).compareToIgnoreCase(temp) > 0) {
                titles.set(j + 1, titles.get(j));
                j--;
            }
            titles.set(j + 1, temp);
        }
        return titles;
    }

    public List<Movie> sortByGenre(String input) {
        for (int i = 0; i < movies.size(); i++) {
            Movie temp = movies.get(titles.get(i));
            if (temp.getGenre() == input) {
                genre.add(temp);
            }
         
        }
        return genre;    
    }

    public List<String> search(String key) {
        List<String> result = new ArrayList<>();
        for(String title : movies.keySet()) {
            if (title.toLowerCase().contains(key.toLowerCase())) {
                result.add(title);
            }
        }
        return result;
    }
    public List<String> searchByGenre(String key) {
        List<String> result = new ArrayList<>();
        for (int i = 0; i < genre.size(); i++) {
            Movie temp = genre.get(i);
            if (temp.getTitle().toLowerCase().contains(key.toLowerCase())) {
                result.add(temp.getTitle());
            }

        }
        
        return result;
    }
    public List<String> generate(String answer) {
        List<String> list = new LinkedList<String>();
        StringTokenizer token = new StringTokenizer(answer, "//");
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
        for (Movie movie : movies.values()) {
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
        }
        return list;
    }
    public List<Movie> findRecommendation(Movie movie) {
        Map<Movie, Integer> results = new HashMap<>();
        for (Movie temp : movies.values()) {
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