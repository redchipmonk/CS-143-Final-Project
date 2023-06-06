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

    public SurveyNode getYesNode() {
        return left;
    }

    public void setYesNode(SurveyNode yesNode) {
        this.left = yesNode;
    }

    public SurveyNode getNoNode() {
        return right;
    }

    public void setNoNode(SurveyNode noNode) {
        this.right = noNode;
    }
    public void setChild(String s, SurveyNode node) {

    }
    public boolean isLeaf() {
        return right == null && left == null;
    }
}
