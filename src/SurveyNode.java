/*
 * This class represents a node in a binary tree used for conducting a survey or questionnaire.
 * Each node has a question, and it can have a left child node and a right child node.
 */
public class SurveyNode {
    public String question;
    public SurveyNode left;
    public SurveyNode right;
/*
 * This constructor initialize the survey node with the given question.
 */
    public SurveyNode(String question) {
        this.question = question;
    }
/*
 * returns the question associated with the current node.
 */
    public String getQuestion() {
        return question;
    }
/*
 * returns the left child node of the current node.
 */
    public SurveyNode getLeftNode() {
        return left;
    }
    /*
     * returns the right child node of the current node.
     */
    public SurveyNode getRightNode() {
        return right;
    }
    /*
     * returns true or false depending on if the node is a leaf or not.
     */
    public boolean isLeaf() {
        return right == null && left == null;
    }
}