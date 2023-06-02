import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RecommenderSystem {
    private Map<String, Movie> movies;
    public RecommenderSystem() {
        movies = new MovieDatabase().getDatabase();
    }
    public Map<String, Movie> getDatabase() {
        return movies;
    }
    public void sortByTitle() {

    }

    public void sortByRating() {

    }

    public void sortByGenre() {

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
}
