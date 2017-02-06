# Binary search trees and lists

Run BstMain to test the binary search tree.  You can use the integers.txt file for input and the delete.txt for a set of data to delete from the tree.

**The BstMain program**

* Prompts the user for an input file. If the program cannot open the file, it prompts the user again.
* Prompts the user for an output file. If the program cannot open the file, it prompts the user again.  The file is overwritten if it already exists.
* Prompts the user for a data structure type - should be "bst" or "list".
* Reads a list of integers from the input file.
* Inserts the data into a binary search tree or a list, depending on the data structure type chosen by the user.
* If the data was inserted into a tree, writes the tree contents to the output file in pre-order, in-order, 
  and post-order traversals.
* If the data was inserted into a list, write the list sorted in ascending order to the output file.
* If the data structure was a tree, prompts the user for the name of a file containing numbers to delete from the tree.
* Removes the elements from the tree and writes the remaining tree contents to the output file in in-order traversal.
* If the user types at any prompt "cancel" in any combination of uppercase and lowercase letters and with any surrounding spaces, then the program terminates.

**BST**

Implementation of a binary search tree.
 
**LinkedList**

A linked list used for the list data structure.

**SortableList**

Extends LinkedList with a sort method. The implementation inserts all the list elements into a BST then returns the tree contents when traversed in-order.
