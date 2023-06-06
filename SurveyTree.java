import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
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
            return null;
        }
            String line = input.nextLine();
            node = new SurveyNode(line);
            if (line.startsWith("A:")) {
                return node;
            } else {
                node.left = read(input, node.left);
            }
            node.right = read(input, node.right);
            return node;
        }
    public void takeSurvey(Scanner console) {
        takeSurvey(console, rootNode);
    }
    private SurveyNode takeSurvey(Scanner console, SurveyNode node) {
        if (node.isLeaf()) {
            return node.getRecommendation();
        }
        return node;
        
    }

    public SurveyNode getRootNode() {
        return rootNode;
    }
}
