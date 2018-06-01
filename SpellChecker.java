/*
 * This class checks to see if a word is misspelled or not. If it is, suggestions are provided
 * based on adding a character, removing a character, and exchanging adjacent characters
 * Note: Some print statements have been commented out  
 * mp3564
 * Malavika Pande 
 */

import java.util.Scanner; 
import java.io.FileReader; 
import java.io.FileNotFoundException; 
import java.util.*; 
import java.util.HashSet; 
import java.util.LinkedList; 
import java.io.File; 

public class SpellChecker{
    
    public static void main(String[] args) throws FileNotFoundException{
        //Program takes in two command line arguments 
        File words = new File(args[0]);
        File testFile = new File(args[1]); 
        
        //Creates HashSet for dictionary and misspelled words
        HashSet<String>dictionary = new HashSet<String>(); 
        //HashSet<String> misspelled = new HashSet<String>(); 
        
        try{            
            //Scanner for dictionary 
            Scanner dictionaryScan = new Scanner(words);
                                     
            while (dictionaryScan.hasNext()){
                String word = dictionaryScan.next();               
                dictionary.add(word.toLowerCase());                     
            }
                                                                               
            //Scanner for test File         
             Scanner testScan = new Scanner(testFile); 
                 
                 //Line numbers where misspelled words are located
                 int lineNumber = 1; 
                 
                 while (testScan.hasNextLine()){
                     String thisTestLine = testScan.nextLine(); 
                       
                     //Lower case and gets rid of whitespace
                     String [] testWordsLine = thisTestLine.toLowerCase().split("\\s+");
                                             
                     //Checks if reformatted word is in dictionary
                     for (String wordTest: testWordsLine){                         
                         //Gets rid of leading and trailing punctuation 
                         //https://stackoverflow.com/questions/15818970/remove-begining-punctuation-from-a-word 
                         //http://www.wellho.net/regex/javare.html                                                                        
                         wordTest = wordTest.replaceAll("^\\p{Punct}|\\p{Punct}$", "");  
                         //System.out.println("After removing leading and trailing punctuation:" +
                                            //" " + wordTest);
                                                  
                         //Notifies user of an empty string 
                         //Example: @! --> ""  
                         if (wordTest.equals("") && !dictionary.contains(wordTest)){
                             System.out.println("*Empty string*"); 
                         }                       
                         
                         //If dictionary does not contain word, it is misspelled 
                         //Calls suggestWord() method 
                         if (!dictionary.contains(wordTest)){                       
                             //System.out.println("This word is not in the dictionary."); 
                             System.out.println("Misspelled Word:" + " " 
                             + wordTest + " " + "Line:" + " " + lineNumber);                                                    
                             System.out.println(suggestWord(wordTest, dictionary));  
                         }
                         
                         //Can comment this out
                         //if (dictionary.contains(wordTest)){                             
                             //System.out.println("The word" + " " + wordTest
                                            //+ " " + "is in the dictionary."); 
                         //}                                                 
                   }
                   lineNumber++; 
              }       
              dictionaryScan.close(); 
              testScan.close(); 
       }            
       catch(FileNotFoundException e){

       }
        
       finally{            
       }               
   }    
               
    //3 for loops     
    //Return a list of ALL/any possible correct words 
    //Add character, remove character, exchange character
    //Make sure this works independently, adding only characters a-z and ' 
    public static List suggestWord(String misspelledWord, HashSet dictionary){
                                  
        //LinkedList will store suggestions 
        LinkedList<String>suggestions = new LinkedList<String>(); 
        
        //Removes character 
        for (int i = 0; i<misspelledWord.length(); i++){
            
            String removeReplacement= misspelledWord.substring(0,i)+ misspelledWord.substring(i+1);
             
            if (dictionary.contains(removeReplacement) && 
                !suggestions.contains(removeReplacement)){
                suggestions.add(removeReplacement); 
                //System.out.println("Suggestion: REMOVE"); 
            }            
        }
        
        //Switches adjacent characters 
        for (int i = 1; i<misspelledWord.length(); i++){
             //int i = 0; misspelledWord.length()+1          
         
            char charswitch1 = misspelledWord.charAt(i-1);            
            char charswitch2 = misspelledWord.charAt(i);
            
            String switch1 = Character.toString(charswitch1); 
            String switch2 = Character.toString(charswitch2);
            
            String switchReplacement = misspelledWord.substring(0, i-1) 
            + switch2 + switch1+ misspelledWord.substring(i+1); 
                                                       
            if (dictionary.contains(switchReplacement) && 
                !suggestions.contains(switchReplacement)){
                suggestions.add(switchReplacement); 
                //System.out.println("Suggestion: SWITCH"); 
            }                                                
        }               
        
        //Adds/insert character at any index 
        //Must add only characters a-z, do not deal with ' as stated on Piazza 
        //Do not have to add numbers 
        for (int i = 0; i<misspelledWord.length(); i++){
            for (char ch = 'a'; ch <= 'z'; ch++){
                String addChar = Character.toString(ch); 
                String addReplacement = misspelledWord.substring(0,i) + 
                    addChar + misspelledWord.substring(i);                      
                        
                if (dictionary.contains(addReplacement) &&
                   !suggestions.contains(addReplacement)){
                    suggestions.add(addReplacement); 
                    //System.out.println("Suggestion: ADD"); 
                }
            }
        }
        
        //Handles the case of an empty string and a single number 
        //Suggests inserting letters, which may turn out to be valid words 
        if (misspelledWord.equals("") || misspelledWord.length()<2){
            for (char ch = 'a'; ch <= 'z'; ch++){
                String addChar = Character.toString(ch); 
                String addReplacement = addChar; 
                
                if (dictionary.contains(addReplacement) &&
                   !suggestions.contains(addReplacement)){
                    suggestions.add(addReplacement); 
                    //System.out.println("Suggestion: ADD"); 
                }
            }
        }
               
        //Note: to replace character would just change variable String replacement
        //to misspelledWord.substring(0,i) + addChar + misspelledWord.substring(i+1)
                        
        //Checks if there are no suggestions to output 
        if (suggestions.size() == 0){
            System.out.println("Sorry. No suggestions."); 
        }
        
        else{
            System.out.println("Suggestions:");
        }
               
     return suggestions; 
    }
}
    
