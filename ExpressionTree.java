/* Following the specification in the README.md file, provide your 
 * ExpressionTree class.
 * Malavika Pande
 * mp3564 
 * Skeleton of method is from lecture notes (code shown in class) and 
 * TreeTraversal supplemental notes by Linan Qiu on Canvas
 * Algorithm is based on section 4.2.2 from Weiss textbook
 */

import java.util.NoSuchElementException; 

public class ExpressionTree{
    private ExpressionNode root; 
    private MyStack<ExpressionNode> stack; 
    private final String postfixExpression; 
    
    //Constructor will be taking in a String that contains a postfix expression 
    //Extracts individual characters from the new string without white space     
    public ExpressionTree(String postfixExpression){   
        this.root = root; 
        this.postfixExpression = postfixExpression; 
        MyStack<ExpressionNode> stack = new MyStack<ExpressionNode>(); 
                
        //Create a new String without the whitespace
        //Makes sure characters are alphanumeric 
        String [] postfixNoSpace = postfixExpression.split("\\s");                 
        for (int i = 0; i<postfixNoSpace.length; i++){            
            String xpres = postfixNoSpace[i];  
            
            //If character is operator, pop T1 and T2 from the stack and 
            //form a new tree whose root is the operator and whose left 
            //and right children are T2 and T1 respectively 
            //The new tree is pushed onto the stack 
            if (isOperator(xpres)){                           
                ExpressionNode Tright = stack.pop();
                ExpressionNode Tleft = stack.pop();
                ExpressionNode newNode = new ExpressionNode(xpres, Tleft, Tright); 
              
                //Tleft and Tright become left and right children
                //New tree is pushed onto stack  
                newNode.right = Tright;
                newNode.left = Tleft; 
                stack.push(newNode); 
            }
            
            //If character is operand
            // create one-node tree and push it onto the stack 
            else{ 
                ExpressionNode x = new ExpressionNode(xpres, null, null); 
                stack.push(x); 
            }  
         }
         //This deals with invalid expressions 
         if (stack.isEmpty()){
             throw new NoSuchElementException("Invalid Expression."); 
         }         
         root = stack.pop();                                  
      }
      
    //Inner class ExpressionNode 
    private static class ExpressionNode{
        ExpressionNode left;
        ExpressionNode right; 
        String xpres; 
                        
        //Constructor must be public              
        public ExpressionNode(String xpres, ExpressionNode left, 
                                ExpressionNode right){
           this.xpres = xpres;
           this.left = left;
           this.right = right;            
       }                         
    }
        
    //Boolean method checks if character is operator or not     
    private boolean isOperator(String c){
        if (c.equals("+") || c.equals("-") 
           || c.equals ("*") || c.equals("/")){
           return true; 
        }
        return false; 
    }
    
    //Returns integer value associated with evaluating the expression tree
    public int eval(){
        if (root == null){
            throw new NoSuchElementException("Invalid Expression."); 
        }
        else{
            return helperEval(root);              
        }
    }
    
    //Helper method checks for operand by checking if node is a leaf
    //A leaf node is an operand and will be converted to an integer 
    //Throws NumberFormatException because of Integer.parseInt
    //IllegalArgumentException in case of incorrect input 
    //This is handled in main method 
    private int helperEval(ExpressionNode parent) 
        throws NumberFormatException, IllegalArgumentException,
        NullPointerException{ 
        if (parent == null){
            throw new NoSuchElementException("Invalid Expression."); 
        }
        if (parent.right == null && parent.left == null){
            return Integer.parseInt(parent.xpres);  
           }
        
        else{ 
            int left = helperEval(parent.left); 
            int right = helperEval(parent.right);
            String operator = parent.xpres; 
            
            if (operator.equals("+")){
                    return left + right; 
            }
            if (operator.equals("-")){                           
                    return left - right;
            }
            if (operator.equals("*")){                                                
                    return left*right;
            }
            if (operator.equals("/")){
                  return left / right; 
            }                                    
         } 
            return 0; 
        }                    
    
     
     //Referred to TreeTraversal supplemental notes by Linan Qiu on Canvas     
    //Returns a String that contains corresponding postfix expression 
    public String postfix(){
        if (root == null){
            throw new NoSuchElementException("Invalid Expression."); 
        }
        
        final StringBuilder postfix_sb = new StringBuilder();      
        postfix(root, postfix_sb); 
        return postfix_sb.toString(); 
    }
            
    private void postfix(ExpressionNode node, StringBuilder postfix_sb){
        if (node != null){
            postfix(node.left, postfix_sb); 
            postfix(node.right, postfix_sb);             
            postfix_sb.append(node.xpres + " "); 
        }
        
        else{
            return; 
        }
    }
        
    //Returns a String that contains corresponding prefix expression 
    public String prefix(){
        if (root == null){
            throw new NoSuchElementException("Invalid Expression."); 
        }
        
        final StringBuilder prefix_sb = new StringBuilder(); 
        prefix(root, prefix_sb); 
        return prefix_sb.toString(); 
    }
    
    private void prefix(ExpressionNode node, StringBuilder prefix_sb){
        prefix_sb.append(node.xpres + " "); 
        
        if(node.left != null){
            prefix(node.left, prefix_sb); 
        }
        
        if (node.right != null){
            prefix(node.right, prefix_sb); 
        }
   }
    

    //Retures a String that contains corresponding infix expression 
    //Paranthesis will be needed because of order of operations 
    public String infix(){
        if(root == null){
            throw new NoSuchElementException("Invalid Expression."); 
        }
        
        final StringBuilder infix_sb = new StringBuilder();
        infix(root, infix_sb); 
        return infix_sb.toString(); 
   }
    

    private void infix(ExpressionNode node, StringBuilder infix_sb){  
        if (node != null){
            //Make sure node is not a leaf 
            if (node.left != null && node.right!= null){
                infix_sb.append("("); 
            }
            
            infix(node.left, infix_sb);
            infix_sb.append(node.xpres + " "); 
            infix(node.right, infix_sb); 
            
            //Make sure node is not a leaf 
            if (node.left != null && node.right != null){
                infix_sb.append(")"); 
            }
        }
    }
}
    
   

 
