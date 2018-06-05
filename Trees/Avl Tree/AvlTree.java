// CONSTRUCTION: with no initializer
//
// ******************PUBLIC OPERATIONS*********************
// void insert( x )       --> Insert x
// void remove( x )       --> Remove x (unimplemented)
// boolean contains( x )  --> Return true if x is present
// boolean remove( x )    --> Return true if x was present
// Comparable findMin( )  --> Return smallest item
// Comparable findMax( )  --> Return largest item
// boolean isEmpty( )     --> Return true if empty; else false
// void makeEmpty( )      --> Remove all items
// void printTree( )      --> Print tree in sorted order
// ******************ERRORS********************************
// Throws UnderflowException as appropriate

/**
 * Implements an AVL tree.
 * Note that all "matching" is based on the compareTo method.
 * @author Mark Allen Weiss
 * Malavika Pande
 * mp3564
 * AvlTree class modifies the AvlNode constructor and adds methods
 */
import java.util.LinkedList; 
import java.util.*; 
import java.util.Arrays; 

public class AvlTree{
    /**
     * Construct the tree.
     */
    
    /** The tree root. */
    private AvlNode root;

    public AvlTree(){
        root = null;
    }
    
    //Adds occurrence of the word in the line 
    //If word already exists, add new line number to existing node
    //If word appears on same line twice, 
    //should only have one entry in list for that line      
    public void indexWord(String word, Integer line){
        root = indexWord(word, line, root); 
    }
    
    private AvlNode indexWord(String word, Integer line, AvlNode t){
        if (t == null){
            return new AvlNode(word, line, null, null); 
        }
        int compareResult = word.compareTo(t.element); 
        if (compareResult > 0){
            t.left = indexWord(word, line, t.left);
        }
        else if (compareResult < 0){
            t.right = indexWord(word, line, t.right); 
        }
        else
            ;  //Duplicate; do nothing 
        //If word exists, add new line to existing node 
        //If word appears on same line twice
        //method should only return one line number 
        if (t.element.equals(word) && !t.lineNumList.contains(line)){
            t.lineNumList.add(line);  
        }                  
        return balance(t); 
    }
    
    //Looks up word and returns the list of lines in which it occurs 
    public List getLinesForWord(String word){
        return findWord(word, root).lineNumList; 
    }
    
    private AvlNode findWord(String word, AvlNode t){
        if (t != null){
            int compareResult = word.compareTo(t.element);
            if(compareResult > 0){
                return findWord(word, t.left); 
            }
            else if (compareResult < 0){
                return findWord(word, t.right); 
            }           
        }
        return t;
    }
    
    //Prints each unique word that is stored in Avl tree 
    //along with a list of lines #s in which it that word appears 
    public void printIndex(){
        if (isEmpty()){
            System.out.println("Error.Empty tree"); 
        }
        else {
            printIndex(root); 
        }
    }
    
    //Modeled after the private printTree() method 
    private void printIndex(AvlNode t){
        if (t != null){
            printIndex(t.right); 
            //System.out.print(t.element + t.lineNumList.toString()); 
            //Prints an Array of line numbers 
            //https://stackoverflow.com/questions/409784/whats-the-simplest-way-to-print-a-java-array
            System.out.println(t.element + ": " 
                               + Arrays.toString(t.lineNumList.toArray())); 
            printIndex(t.left); 
        }        
    }
           
    /**
     * Find an item in the tree.
     * @param x the item to search for.
     * @return true if x is found.
     */
    public boolean contains(String x){    
        return contains(x, root);
    }

    /**
     * Make the tree logically empty.
     */
    public void makeEmpty(){   
        root = null;
    }

    /**
     * Test if the tree is logically empty.
     * @return true if empty, false otherwise.
     */
    public boolean isEmpty(){    
        return root == null;
    }

    /**
     * Print the tree contents in sorted order.
     */
    public void printTree(){    
        if(isEmpty())
            System.out.println( "Empty tree" );
        else
            printTree(root);
    }

    private static final int ALLOWED_IMBALANCE = 1;
    
    // Assume t is either balanced or within one of being balanced
    private AvlNode balance(AvlNode t){
    
        if(t == null)
            return t;
        
        if(height(t.left) - height(t.right) > ALLOWED_IMBALANCE)
            if(height(t.left.left) >= height(t.left.right) )
                t = rotateWithLeftChild( t );
            else
                t = doubleWithLeftChild( t );
        else
        if( height( t.right ) - height( t.left ) > ALLOWED_IMBALANCE )
            if( height( t.right.right ) >= height( t.right.left ) )
                t = rotateWithRightChild( t );
            else
                t = doubleWithRightChild( t );

        t.height = Math.max( height( t.left ), height( t.right ) ) + 1;
        return t;
    }
    
    public void checkBalance(){    
        checkBalance(root);
    }
    
    private int checkBalance(AvlNode t){    
        if(t == null)
            return -1;        
        if(t != null){        
            int hl = checkBalance(t.left);
            int hr = checkBalance(t.right);
            if( Math.abs(height(t.left) - height(t.right)) > 1 ||
                    height(t.left) != hl || height(t.right) != hr )
                System.out.println( "OOPS!!" );
        }        
        return height(t);
    }
       
    /**
     * Internal method to find an item in a subtree.
     * @param x is item to search for.
     * @param t the node that roots the tree.
     * @return true if x is found in subtree.
     */
    private boolean contains(String x, AvlNode t){    
        while(t != null){       
            int compareResult = x.compareTo(t.element);            
            if(compareResult < 0)
                t = t.left;
            else if(compareResult > 0)
                t = t.right;
            else
                return true;    // Match
        }
        return false;   // No match
    }

    /**
     * Internal method to print a subtree in sorted order.
     * @param t the node that roots the tree.
     */
    private void printTree( AvlNode t){    
        if(t != null){        
            printTree(t.left);
            System.out.println(t.element);
            printTree(t.right);
        }
    }

    /**
     * Return the height of node t, or -1, if null.
     */
    private int height(AvlNode t){    
        return t == null ? -1 : t.height;
    }

    /**
     * Rotate binary tree node with left child.
     * For AVL trees, this is a single rotation for case 1.
     * Update heights, then return new root.
     */
    private AvlNode rotateWithLeftChild(AvlNode k2){
    
        AvlNode k1 = k2.left;
        k2.left = k1.right;
        k1.right = k2;
        k2.height = Math.max( height( k2.left ), height( k2.right ) ) + 1;
        k1.height = Math.max( height( k1.left ), k2.height ) + 1;
        return k1;
    }

    /**
     * Rotate binary tree node with right child.
     * For AVL trees, this is a single rotation for case 4.
     * Update heights, then return new root.
     */
    private AvlNode rotateWithRightChild(AvlNode k1){
    
        AvlNode k2 = k1.right;
        k1.right = k2.left;
        k2.left = k1;
        k1.height = Math.max( height( k1.left ), height( k1.right ) ) + 1;
        k2.height = Math.max( height( k2.right ), k1.height ) + 1;
        return k2;
    }

    /**
     * Double rotate binary tree node: first left child
     * with its right child; then node k3 with new left child.
     * For AVL trees, this is a double rotation for case 2.
     * Update heights, then return new root.
     * LEFT-RIGHT double rotation 
     */
    private AvlNode doubleWithLeftChild(AvlNode k3){    
        k3.left = rotateWithRightChild( k3.left );
        return rotateWithLeftChild( k3 );
    }

    /**
     * Double rotate binary tree node: first right child
     * with its left child; then node k1 with new right child.
     * For AVL trees, this is a double rotation for case 3.
     * Update heights, then return new root.
     * RIGHT-LEFT double rotation
     */
    private AvlNode doubleWithRightChild(AvlNode k1){    
        k1.right = rotateWithLeftChild(k1.right);
        return rotateWithRightChild(k1);
    }
    
    //Renative order of elements in data depend on word only 
    //Add linkedList object 
    private static class AvlNode{          
        String element; // The data in the node
        LinkedList<Integer>lineNumList = new LinkedList<Integer>();
        AvlNode          left;         // Left child
        AvlNode          right;        // Right child
        int              height;       // Height
               
        //AvlNode(String theElement, int lineNum){  
        AvlNode(String theElement, Integer lineNum){  
            this(theElement, lineNum, null, null); 
        }
        
        AvlNode(String theElement, Integer lineNum, AvlNode lt,  
                AvlNode rt){ 
            
            height = 0; 
            lineNumList.add(lineNum); 
            element  = theElement;           
            left     = lt;
            right    = rt;
        }                                    
    }
}
