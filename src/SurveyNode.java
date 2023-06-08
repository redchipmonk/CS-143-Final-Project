/**
 * This class represents a node in a binary tree used for conducting a survey or questionnaire. Each node contains a
 * combined question that is then split in two.
 *
 * @author Eric Im
 */
public class SurveyNode {
    public String question;
    public SurveyNode left;
    public SurveyNode right;
    /**
     * This constructor initialize the survey node with the given question.
     * @param question String question to initialize
     */
    public SurveyNode(String question) {
        this.question = question;
    }
    /**
     * Getter for question or answer associated with the current node.
     * @return String question/answer
     */
    public String getQuestion() {
        return question;
    }
    /**
     * Checks if current node is an answer
     * @return true if left and right are null, false otherwise
     */
    public boolean isLeaf() {
        return right == null && left == null;
    }
}