public class SurveyNode {
    public String question;
    public SurveyNode left;
    public SurveyNode right;

    public SurveyNode(String question) {
        this.question = question;
    }

    public String getQuestion() {
        return question;
    }
    public boolean isLeaf() {
        return right == null && left == null;
    }
}