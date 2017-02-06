/**
 * An extension of LinkedList based on a BST.  The elements are returned 
 * in the order provided by Comparable.
 *
 * @param <T> list element type
 */
public class SortableList<T extends Comparable<T>> extends LinkedList<T> {

    /**
     * Returns the contents of the list in a sorted list.
     * @return the sorted list
     */
    public LinkedList<T> getSortedList() {
        BST<T> tree = new BST<T>();
        
        for (T data : this) {
            tree.add(data);
        }
        return tree.elements(BST.Traversal.IN_ORDER);
    }
}
