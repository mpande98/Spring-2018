/* Following the specification in the README.md file, provide your 
 * Problem1 class.
 * Fill in the class Problem1.java such that it instantiates an expression tree on a hard coded string
 * representing a postfix expression tree, and demonstrate all of the methods listed above.
 * Malavika Pande
 * mp3564 
 */
import java.io.*; 
public class Problem1{
    
    public static void main(String[] args)throws UnderflowException, NullPointerException,
        NumberFormatException, IllegalArgumentException{
        try{
            //Creates new ExpressionTree object
            ExpressionTree someExpressionTree = new ExpressionTree("3 2 - 5");  
    
            System.out.println("The postfix expression is" + " " + someExpressionTree.postfix());         
            System.out.println("The prefix expression is" + " " + someExpressionTree.prefix()); 
            System.out.println("The infix expression is" + " " + someExpressionTree.infix()); 
        
            System.out.println("The value of the expression is " + someExpressionTree.eval()); 
        }
        
        //Catches most specific exceptions first         
        catch(UnderflowException exception){
              System.out.println("Stack underflow."); 
              System.out.println("Invalid Expression Tree."); 
        }        
        catch(NullPointerException exception){
            System.out.println("Null Pointer Exception");
            System.out.println("Invalid Expression Tree."); 
        }
        catch(NumberFormatException exception){
            System.out.println("Number Format Exception."); 
            System.out.println("Invalid Expression Tree."); 
        }
        catch(IllegalArgumentException exception){            
            System.out.println("Illegal Argument Exception."); 
            System.out.println("Invalid Expression Tree."); 
        }
        finally{
            
        }
    }
} 
