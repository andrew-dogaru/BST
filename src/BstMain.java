import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

/**
 * Main program for testing binary search trees. 
 */
public class BstMain {

    private BST<Integer> tree;
    private SortableList<Integer> list;
    private static boolean canceled = false;
    
    public static void main(String[] args) throws IOException {
        BufferedReader input = null;
        BufferedWriter output = null;
        BufferedReader inputRemove = null;

        BstMain program = new BstMain();
        
        try {
            // Get the input file
            input = getInput();

            // Get the output file
            output = getOutput();

            // Get the data structure and sort the data
            String dataType = getDataStructure();
            program.writeData(dataType, input, output);
            
            // Remove data from tree
            if (dataType.equals("bst")) {
                // Get the file containing numbers to remove
                inputRemove = getNumbersToRemove();
                program.removeData(inputRemove, output);
            }
        } catch (Exception e) {
            if (!canceled)
                throw e; // the program prints the exception message
            else {
                System.out.println("Program terminated");
            }
        } finally {
            // I think we need to close the files like this?
            // If the user cancels then this closes all the files
            if (input != null)
                input.close();
            if (inputRemove != null)
                inputRemove.close();
            if (output != null)
                output.close();
        }
    }

    /**
     * Reads data from input, stores it in a data structure specified by
     * dataType, then reads from the data structure and writes to the output
     * writer.
     * 
     * @param dataType the data structure type, either "bst" or "list"
     * @param input the input reader
     * @param output the output writer
     * @throws IOException if the data read or write is failing
     */
    void writeData(String dataType, BufferedReader input, BufferedWriter output) throws IOException {
        if (dataType.equals("bst")) {
            // sort data using BST
            tree = new BST<Integer>();

            // Read from file
            read(input, tree);

            // System.out.println("Tree is:");
            // System.out.println(tree.toString());

            // Write to output in the traversal orders
            output.write("Pre-order:");
            output.newLine();
            write(output, tree, BST.Traversal.PRE_ORDER);

            output.newLine();
            output.newLine();
            output.write("In-order:");
            output.newLine();
            write(output, tree, BST.Traversal.IN_ORDER);

            output.newLine();
            output.newLine();
            output.write("Post-order:");
            output.newLine();
            write(output, tree, BST.Traversal.POST_ORDER);
        } else if (dataType.equals("list")) {
            // sort data using list
            list = new SortableList<Integer>();

            // Read from file, sort list, then write to output
            read(input, list);

            output.write("Sorted list:");
            output.newLine();
            write(output, list.getSortedList());
        }
    }

    /**
     * Reads from input the elements to be removed from the tree. After removal
     * writes the tree to the output.
     * 
     * @param input where data to be deleted is read from
     * @param output where the tree contents after removal is written to
     * @throws IOException
     */
    void removeData(BufferedReader input, BufferedWriter output) throws IOException {
        // Read numbers to delete from tree
        LinkedList<Integer> numbersToDelete = new LinkedList<Integer>();
        read(input, numbersToDelete);

        for (Integer value : numbersToDelete) {
            tree.remove(value);
        }

        // System.out.println("Tree after remove is:");
        // System.out.println(tree.toString());

        // Write to output in in-order
        output.newLine();
        output.newLine();
        output.write("After delete:");
        output.newLine();
        write(output, tree, BST.Traversal.IN_ORDER);
    }

    /**
     * Prompts user for a file name and returns a reader from the file.
     * Repeats the prompts if the file does not exist or it cannot open 
     * the file.
     * 
     * @return BufferedReader used for reading the user's input
     * @throws IOException
     */
    static BufferedReader getInput() throws IOException {
        while (true) {
            // Prompt user for input file name
            String fileName = getLineFromUser("Enter the name of the input file:");
            try {
                return new BufferedReader(new FileReader(fileName));
            } catch (IOException e) {
                System.err.println("File " + fileName + " does not exist or cannot be opened.");
            }
        }
    }

    /**
     * Prompts user for file name and returns a writer to the file.
     * 
     * @return Buffered writer used for writing into the output file
     * @throws IOException
     */
    static BufferedWriter getOutput() throws IOException {
        while (true) {
            String fileName = getLineFromUser("Enter the name of the output file:");
            try {
                return new BufferedWriter(new FileWriter(fileName));
            } catch (IOException e) {
                System.err.println("File " + fileName + " cannot be opened to write in it.");
            }
        }
    }

    /**
     * Prompts the user for an input file name and returns the open file.
     * Prompts the user again if it cannot open the file.
     * 
     * @return
     * @throws IOException
     */
    static BufferedReader getNumbersToRemove() throws IOException {
        while (true) {
            // Prompt user for input file name
            String fileName = getLineFromUser("Enter the name of the file with numbers to remove:");
            try {
                return new BufferedReader(new FileReader(fileName));
            } catch (IOException e) {
                System.err.println("File " + fileName + " does not exist or cannot be opened.");
            }
        }
    }

    /**
     * Prompts the user for a the data structure type until the user types 
     * either "bst" or "list".
     * 
     * @return the data structure type
     * @throws IOException
     */
    static String getDataStructure() throws IOException {
        while (true) {
            // Prompt user for input file name
            String line = getLineFromUser("Enter the data structure (should be list or bst):");
            if (line.equals("list") || line.equals("bst"))
                return line;
        }
    }

    /**
     * Prompts the user then returns his input. If the user types "cancel"
     * then it throws a RuntimeException.
     * 
     * @param prompt
     * @return
     * @throws IOException
     */
    static String getLineFromUser(String prompt) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println(prompt);
        String line = br.readLine();
        line = line.trim();
        if (line.equalsIgnoreCase("cancel")) {
            canceled = true;
            throw new RuntimeException(); // user canceled program
        }
        return line;
    }

    /**
     * Read from the input, parse it as integer and insert the values into
     * the given tree.
     * 
     * @param input the input reader
     * @param tree the tree where values are inserted
     */
    static void read(BufferedReader input, BST<Integer> tree) {
        Scanner sc = new Scanner(input);
        try {
            while (sc.hasNextInt()) {
                Integer value = new Integer(sc.nextInt());
                tree.add(value);
            }
        } finally {
            sc.close();
        }
    }
    
    /**
     * Read from the input, parse it as integers, and insert the values into
     * the given list.
     * 
     * @param input the input reader
     * @param list where values are inserted
     */
    static void read(BufferedReader input, LinkedList<Integer> list) {
        Scanner sc = new Scanner(input);
        try {
            while (sc.hasNextInt()) {
                Integer value = new Integer(sc.nextInt());
                list.add(value);
            }
        } finally {
            sc.close();
        }
    }

    /**
     * Traverses the tree in the specified order and write values to the 
     * given writer.
     * 
     * @param output the output writer
     * @param tree the tree
     * @param order the tree traversal order
     * @throws IOException if error while writing
     */
    static void write(BufferedWriter output, BST<Integer> tree, BST.Traversal order) throws IOException {
    	int count = 0;
    	LinkedList<Integer> list = tree.elements(order);
    	for(Integer i : list){
    		output.write(i.toString());
    		count++;
    		
    		if(count%5 == 0){
    			output.newLine();
    		}
    		else{
    			output.write(' ');
    		}
    	}
    }
    
    /**
     * Writes values from the list to the given writer.
     * 
     * @param output the output writer
     * @param list the list
     * @throws IOException if error while writing
     */
    static void write(BufferedWriter output, LinkedList<Integer> list) throws IOException {
        int count = 0;
        for (Integer value : list) {
            output.write(value.toString());
            
            // start a new line after five integers
            count++;
            if (count % 5 == 0)
                output.newLine();
            else
                output.write(' ');
        }
    }
}
