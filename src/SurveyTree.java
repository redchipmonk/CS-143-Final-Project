import java.io.*;
import java.util.*;
/**
 * This class represents a binary tree of survey questions and answers. It utilizes the SurveyNode class to construct
 * the tree and perform survey operations.
 *
 * @author Eric Im
 */
public class SurveyTree {
    private SurveyNode rootNode;
    /**
     * The constructor initializes a SurveyTree object by reading survey questions and answers from a "survey.txt" file.
     * The file should contain the questions and answers in a specific format, with "A:" indicating an answer/leaf node
     * and each line representing a node in the tree.
     * @param filePath name of file
     * @throws FileNotFoundException if file does not exist
     */
    public SurveyTree(String filePath) throws FileNotFoundException {
        File file = new File(filePath);
        Scanner input;
        try {
            input = new Scanner(file);
        }
        catch (FileNotFoundException e) {
            throw new FileNotFoundException();
        }
        rootNode = read(input, rootNode);
    }
    /**
     * A recursive method that reads and constructs the survey tree based on the "survey.txt" file.
     * @param input Scanner to read from
     * @param node current node to add to tree
     * @return overall root of tree
     */
    public SurveyNode read(Scanner input, SurveyNode node) {
        //done reading
        if (!input.hasNext()) {
            return node;
        }
        String line = input.nextLine();
        node = new SurveyNode(line);
        //if node is an answer
        if (node.isLeaf()) {
            return node;
        }
        else {
            node.left = read(input, node.left);
            node.right = read(input, node.right);
        }
        return node;
    }
    /**
     * Getter for the root of the binary tree
     * @return root of tree
     */
    public SurveyNode getRootNode() {
        return rootNode;
    }
}