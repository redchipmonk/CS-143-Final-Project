/**
 * This class represents the details of a movie.
 *
 * @author Alvin Le
 */
public class Movie {
    private String title;
    private String description;
    private int year;
    private String genre;
    private int minutes;

    /**
     * Default constructor creates an empty movie that contains no useful information
     */
    public Movie() {
        title = null;
        description = null;
        year = -1;
        genre = null;
        minutes = -1;
    }
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
    public String getGenre() {
        return genre;
    }
    /**
     * Getter for movie duration
     * @return movie duration
     */
    public int getMinutes() {
        return minutes;
    }

    /**
     * Setter for movie title
     * @param title movie title
     */
    public void setTitle(String title) {
        this.title = title;
    }
    /**
     * Setter for movie description
     * @param description of movie
     */
    public void setDescription(String description) {
        this.description = description;
    }
    /**
     * Setter for movie release year
     * @param year when movie was released
     */
    public void setYear(int year) {
        this.year = year;
    }
    /**
     * Setter for movie genre
     * @param genre movie
     */
    public void setGenre(String genre) {
        this.genre = genre;
    }
    /**
     * Setter for movie duration
     * @param minutes how long movie is
     */
    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }
}