/* Following the specification in the README.md file, provide your 
 * Problem2 class.
 * Ignore use in the input text - convert it to lower case 
 * Ignore punctuation 
 * At the end, call printIndex method to display list of unique words
 * in text file and line numbers in which that word occurs 
 * Malavika Pande
 * mp3564 
 */
import java.util.Scanner; 
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException; 
import java.io.*;  
import java.util.Arrays; 

public class Problem2{
        public static void main(String[] args) throws IOException, FileNotFoundException{
            try{
                String thisLine;                   
                //Creates new AvlTree object                 
                Integer lineNum = 1; 
                
                //Note: I also could have used BufferedReader for this
                Scanner myScanner = new Scanner(new File(args[0])); 
                AvlTree myAvlTree = new AvlTree(); 
                //while ((thisLine = reader.readLine()) != null)
                while (myScanner.hasNextLine()){
                    thisLine = myScanner.nextLine();  
                    
                    //This ignores punctuation and converts to lower case
                    //Similar methodology as ExpressionTree.java
                    //Used String regular expressions to ignore punctuation but 
                    //also account for numbers   
                    //www.vogella.com/tutorials/JavaRegularExpressions/article.html                                         
                    String[] wordsLine = thisLine.replaceAll("[^a-zA-Z0-9 ]", "").toLowerCase().split("\\s+");
                    
                    //Prints the array of strings
                    //I did this to help debug a previous error 
                    System.out.println(Arrays.toString(wordsLine)); 
                    
                    //Enhanced for loop, could have also used regular for loop 
                    //Finds line number corresponding to unique word
                    //Calls indexWord() method from AvlTree class 
                    for (String word : wordsLine){
                        myAvlTree.indexWord(word, lineNum);
                    }                      
                    lineNum++; 
                }
                //Prints line number corresponding to unique word  
                myAvlTree.printIndex();
                //To test getLinesForWord: 
                //System.out.println(myAvlTree.getLinesForWord("andrey));
                //Should return 4, 7 
                myScanner.close();                                                              
            }   
              catch(IOException exception){
             System.out.println("File not found."); 
        } 
        
        finally{
            
        }
     }    
}
    
