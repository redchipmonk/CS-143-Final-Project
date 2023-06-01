/**
 * This class is an immutable object representing the information of a movie.
 *
 * @author Alvin Le
 */
public class Movie {
    private final String title;
    private final String description;
    private final int year;
    private final String genre;
    private final int minutes;
    /**
     * Constructor to initialize the information of a movie
     * @param title what the movie is called
     * @param description what the movie is about
     * @param year when the movie was released
     * @param genre what categories the movie belongs to
     * @param minutes duration of movie
     */
    public Movie(String title, String description, int year, String genre, int minutes) {
        this.title = title;
        this.description = description;
        this.year = year;
        this.genre = genre;
        this.minutes = minutes;
    }
    /**
     * Getter for movie title
     * @return movie title
     */
    public String getTitle() {
        return title;
    }
    /**
     * Getter for movie description
     * @return movie description
     */
    public String getDescription() {
        return description;
    }
    /**
     * Getter for movie release date
     * @return release date
     */
    public int getYear() {
        return year;
    }
    /**
     * Getter for movie genre
     * @return movie genre
     */
    public String getgenre() {
        return genre;
    }
    /**
     * Getter for movie duration
     * @return movie duration
     */
    public int getMinutes() {
        return minutes;
    }
}