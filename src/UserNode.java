/**
 * This clss represents an individual node in a binary tree of users
 *
 * @author Alvin
 */
public class UserNode {
    private User data;
    private UserNode left;
    private UserNode right;
    /**
     * Constructs new node with left and right children
     * @param data
     * @param left
     * @param right
     */
    public UserNode(User data, UserNode left, UserNode right) {
        this.data = data;
        this.left = left;
        this.right = right;
    }
    /**
     * Constructs new leaf node
     * @param data
     */
    public UserNode(User data) {
        this.data = data;
        left = null;
        right = null;
    }
    /**
     * Checks if current node is a leaf
     * @return true if both children are null, false if not
     */
    public boolean isLeaf() {
        return left == null && right == null;
    }
}