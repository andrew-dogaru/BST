/**
 * Binary search tree implementation.
 */
public class BST<T extends Comparable<T>> {
    private BSTNode<T> root;
    private LinkedList<T> inOrderList;
    private LinkedList<T> preOrderList;
    private LinkedList<T> postOrderList;

    /**
     * Tree node.
     *
     * @param <T> the data type held by the node
     */
    public static class BSTNode<T extends Comparable<T>> implements Comparable<BSTNode<T>> {
        public BSTNode<T> left;
        public BSTNode<T> right;
        public T data;

        public BSTNode(BSTNode<T> leftNode, BSTNode<T> rightNode, T value) {
            left = leftNode;
            right = rightNode;
            data = value;
        }

        public int compareTo(BSTNode<T> other) {
            if (data.compareTo(other.data) > 0) {
                return 1;
            } else if (data.compareTo(other.data) < 0) {
                return -1;
            }
            return 0;
        }

        @Override
        public String toString() {
            return Integer.toString(this.hashCode(), 16) + ": " +
                    ((left == null) ? "null" : Integer.toString(left.hashCode(), 16)) + 
                    " " + data.toString() + " " + 
                    ((right == null) ? "null" : Integer.toString(right.hashCode(), 16));
        }
    }

    /**
     * The orders of tree traversal. 
     */
    public enum Traversal { PRE_ORDER, IN_ORDER, POST_ORDER };
    
    /**
     * Returns a list containing the values stored in this tree in the 
     * specified traversal order.
     * 
     * @param order the traversal order of the tree
     * @return a list of nodes
     */
    public LinkedList<T> elements(Traversal order) {
        switch (order) {
        case IN_ORDER:
            inOrderList = new LinkedList<T>();
            inOrder(root);
            return inOrderList;
        case POST_ORDER:
            postOrderList = new LinkedList<T>();
            postOrder(root);
            return postOrderList;
        case PRE_ORDER:
            preOrderList = new LinkedList<T>();
            preOrder(root);
            return preOrderList;
        default:
            throw new IllegalArgumentException();

        }
    }

    /**
     * Traverses the tree in-order and inserts the tree data into a list.
     * 
     * @param current the root node of the tree
     */
    private void inOrder(BSTNode<T> current) {
        if (current.left != null) {
            inOrder(current.left);
        }
        inOrderList.add(current.data);
        if (current.right != null) {
            inOrder(current.right);
        }
    }

    /**
     * Traverses the tree in pre-order and inserts the tree data into a list.
     * 
     * @param current the root node of the tree
     */
    private void preOrder(BSTNode<T> current) {
        preOrderList.add(current.data);
        if (current.left != null) {
            preOrder(current.left);
        }
        if (current.right != null) {
            preOrder(current.right);
        }
    }

    /**
     * Traverses the tree in post-order and inserts the tree data into a list.
     * 
     * @param current the root node of the tree
     */
    private void postOrder(BSTNode<T> current) {
        if (current.left != null) {
            postOrder(current.left);
        }
        if (current.right != null) {
            postOrder(current.right);
        }
        postOrderList.add(current.data);
    }

    /**
     * Adds the specified data to the tree.  If the tree already contains
     * the data then it replaces it with the new data element. 
     * 
     * @param data the data element to add to the tree
     */
    public void add(T data) {
        if (data != null) {
            // this works even when root is null, add() returns a new BSTNode
            root = addToTree(data, root);
        } else {
            throw new IllegalArgumentException("Cannot add nulls");
        }
    }

    /**
     * Recursive helper method adds data into the subtree with root given by 
     * the here node.
     * 
     * @param data data to add
     * @param here the root node of a subtree
     * @return the root node of the subtree where data was added
     */
    private BSTNode<T> addToTree(T data, BSTNode<T> here) {
        if (here != null) {
            // if data smaller than here node insert into the left subtree
            if (data.compareTo(here.data) < 0) {
                here.left = addToTree(data, here.left);
            } 
            // if data greater than here node insert into the right subtree
            else if (data.compareTo(here.data) > 0) {
                here.right = addToTree(data, here.right);
            }
            // replace in current node
            else {
                here.data = data;
            }
            return here;
        }
        else {
            // just create new node, caller will link it into the tree
            return new BSTNode<T>(null, null, data);
        }
    }

    /**
     * Remove the specified data from the tree, if it exists.
     * @param data the data element to remove
     */
    public void remove(T data) {
        if (data == null)
            throw new IllegalArgumentException("Cannot remove nulls");

        root = removeFromTree(data, root);
    }
    
    /**
     * Recursive helper method removes data from the subtree which has node
     * 'node' as root.
     * 
     * @param data data from node to remove
     * @param here the tree root
     * @return the tree without the node containing the data 
     */
    private BSTNode<T> removeFromTree(T data, BSTNode<T> here) {

        if (here != null) {
//            System.out.println("removeFromTree(): data = " + data + "node = " + here.toString()); 

            // if data smaller than here node remove from the left
            if (data.compareTo(here.data) < 0) {
                here.left = removeFromTree(data, here.left);
            }
            // if data greater than here node remove from the right
            else if (data.compareTo(here.data) > 0) {
                here.right = removeFromTree(data, here.right);
            } else {
                // remove here node, return left or right subtree
                if (here.right == null)
                    return here.left;
                if (here.left == null)
                    return here.right;

                // remove the here node and join the left and right subtrees
                here = joinSubtrees(here.left, here.right);
            }
            return here;
        } else
            return null;
    }

    /**
     * Join the leftRoot and the rightRoot trees.
     * 
     * Find the leftmost leaf node from the rightRoot tree. Remove that node 
     * and make it the root of the joined tree. Under the new root put the 
     * left and the right subtrees.
     * 
     * @param leftRoot the root node of the left tree
     * @param oldRoot the root node to be removed
     * @return the new root of the joined trees
     */
    private BSTNode<T> joinSubtrees(BSTNode<T> leftRoot, BSTNode<T> rightRoot) {
        // newRoot is the leftmost of the right subtree
        BSTNode<T> newRoot = leftMost(rightRoot);
        newRoot.right = removeLeftMost(rightRoot);
        newRoot.left = leftRoot;
        return newRoot;
    }

    /** 
     * Go onto the left subtree until we find the leaf node.
     * 
     * @param node the tree root
     * @return the leftmost leaf node
     */
    private BSTNode<T> leftMost(BSTNode<T> node) {
        if (node.left == null)
            return node; // this is the leftmost leaf
        else {
//            System.out.println("leftMost(): " + node.toString()); 
            return leftMost(node.left);
        }
    }
    
    /**
     * From the tree starting with node remove the leftmost leaf
     * and return the remaining tree.
     * 
     * @param node the tree root
     * @return the tree after removing the leftmost node
     */
    private BSTNode<T> removeLeftMost(BSTNode<T> node) {
        if (node.left == null) {
            // nothing to remove on the left, end recursion
            return node.right;
        } else {
//            System.out.println("removeLeftMost(): " + node.toString()); 
            // recurse on the left subtree
            node.left = removeLeftMost(node.left);
            return node;
        }
    }
    
    @Override
    public String toString() {
        return toString(root);
    }
    
    private String toString(BSTNode<T> node) {
        if (node == null)
            return "";
        
        String s = toString(node.left) + "\n";
        if (node == root)
            s += "root ";
        s += node.toString() + "\n";
        s += toString(node.right);
        return s;
    }
}
