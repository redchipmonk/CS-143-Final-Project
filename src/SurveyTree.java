import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class SurveyTree {
    private SurveyNode rootNode;

    public SurveyTree(String filePath) throws FileNotFoundException {
        File file = new File(filePath);
        Scanner input = new Scanner(file);
        rootNode = read(input, rootNode);
    }

    public SurveyNode read(Scanner input, SurveyNode node) {
        if (!input.hasNext()) {
            return node;
        }
            String line = input.nextLine();
            node = new SurveyNode(line);
            if (line.startsWith("A:")) {
                return node;
            }
            else {
                node.left = read(input, node.left);
                node.right = read(input, node.right);
            }
            return node;
        }
    public String takeSurvey(Scanner console) {
         return takeSurvey(console, rootNode);
    }
    private String takeSurvey(Scanner console, SurveyNode node) {
        if (node.isLeaf()) {
            return node.getQuestion();
        }
        String[] parts = node.getQuestion().split("//");
        String left = parts[0];
        String right = parts[1];
        String input = console.nextLine();
        if (input.equalsIgnoreCase(left)) {
            takeSurvey(console, node.left);
        }
        else if (input.equalsIgnoreCase(right)) {
            takeSurvey(console, node.right);
        }
        else {
            return "Invalid response";
        }
        return "Invalid response";
    }

    public SurveyNode getRootNode() {
        return rootNode;
    }
}