import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RecommenderSystem {
    private Map<String, Movie> movies;
    private List<String> titles;
    public RecommenderSystem() {
        movies = new MovieDatabase().getDatabase();
        titles = movies.keySet().stream().toList();
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
