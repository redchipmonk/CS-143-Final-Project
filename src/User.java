import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * This class represents a critic who has a name and who has rated movies.
 *
 * @author Alvin
 */
public class User {
    private String name;
    private Map<Movie, Integer> ratings;
    public User() {
        name = null;
        ratings = null;
    }
    /**
     * Constructor to initialize a critic and their ratings
     * @param name of critic
     * @param ratings of movies
     */
    public User(String name, Map<Movie, Integer> ratings) {
        this.name = name;
        this.ratings = ratings;
    }
    /**
     * Getter for critic's name
     * @return critic's name
     */
    public String getName() {
        return name;
    }
    /**
     * Getter for critic's reviews
     * @return critic's ratings
     */
    public Map<Movie, Integer> getRatings() {
        return ratings;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRatings(Map<Movie, Integer> ratings) {
        this.ratings = ratings;
    }
}