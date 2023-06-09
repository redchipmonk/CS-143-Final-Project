import java.io.FileNotFoundException;
/**
 * This class is the main runner class for the program.
 *
 * @author Eric Im
 */
public class RecommendationMain {
    /**
     * Main method to run
     * @param args command line arguments
     */
    public static void main(String[] args) {
        try {
            RecommendationSwing recommendationSwing = new RecommendationSwing();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}