import java.util.List;
/**
 * This class represents a critic who has a name and who has rated movies.
 */
public class User {
    private String name;
    private List<String> ratings;
    /**
     * Constructor to initialize a critic and their ratings
     * @param name of critic
     * @param ratings of movies
     */
    public User(String name, List<String> ratings) {
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
    public List<String> getRatings() {
        return ratings;
    }
}
