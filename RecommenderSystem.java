import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
            String temp = genre.get(i).getGenre();
            if (!result.contains(temp)) {
                result.add(temp);
            }

        }
        

        return result;
    }
    
}
