
package csci232_outlab_5_huffman;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.PriorityQueue;
import java.util.Scanner;

/**
 *
 * @author Roy Smart
 * CSCI 232 Data Structures & Algorithms
 * Reads file, compresses it, and then decompresses
 */
public class Driver {
    static String inputPath = "input.txt";
    static String encodedPath = "encoded.txt";
    static int startChar = (int)' ';
    static int endChar = (int)'~';
    static int treeSize =  endChar - startChar;
    
    public static void main(String[] args) {
       char[] uncrypt = readFile(inputPath);
       /*print file*/
       
       for(int i = 0; i < uncrypt.length; i++){
           if(i % 80 == 0) System.out.println();
           System.out.print(uncrypt[i]);
       }
       System.out.println();
       
       /*find frequencies and show in a histogram*/
       int[] charFreq = calculateFrequency(uncrypt);
       printHistogram(charFreq, uncrypt.length);
       HuffmanTree huffman = new HuffmanTree(charFreq);
       
       /*compress string to file*/
       FileOut encodedWriter = new FileOut(encodedPath);
       huffman.encodeString(uncrypt, encodedWriter);      
       
    }
    
    public static char[] readFile(String path){
        String sequence = "";
        try{
            Scanner scanner = new Scanner(new File(path));
            
            while(scanner.hasNext()){
                sequence = sequence + scanner.nextLine();
            }                        
        }
        catch(FileNotFoundException e){
            System.out.print("Input file not found...");
        }
        return sequence.toCharArray();
    }
    
    public static int[] calculateFrequency(char[] string){
        int[] frequency = new int[treeSize];
        for(int i = 0; i < string.length; i++){
            int charIndex = string[i] - startChar;
            frequency[charIndex]++; 
        }
        return frequency;
    }
    
    public static void printHistogram(int[] frequency, int size){
        for(int i = startChar; i <= endChar; i++){
            System.out.print((char)i + " ");
        }
        System.out.println();
        int increment = size / 100;
        for(int i = 1; i < 11; i++){
            int level = increment * i;
            for(int j = 0; j < treeSize; j++){
                if(frequency[j] > level){
                    System.out.print("# ");
                }
                else{
                    System.out.print("  ");
                }
            }
            System.out.println();
        }
    }
}
