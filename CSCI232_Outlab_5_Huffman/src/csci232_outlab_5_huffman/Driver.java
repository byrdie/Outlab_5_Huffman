
package csci232_outlab_5_huffman;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
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
    static String outputPath = "output.txt";
    static int startChar = (int)' ';
    static int endChar = (int)'~';
    static int treeSize =  endChar - startChar;
    
    public static void main(String[] args) {
       System.out.println("Reading file...");
       char[] uncrypt = readFile(inputPath);
       /*print file*/
       
       for(int i = 0; i < uncrypt.length; i++){
           if(i % 190 == 0) System.out.println();
           System.out.print(uncrypt[i]);
       }
       System.out.println();
       
       /*find frequencies and show in a histogram*/
       System.out.print("Calculating letter frequencies...");
       int[] charFreq = calculateFrequency(uncrypt);
       System.out.println("\nHistorgram of relative letter frequencies");
       printHistogram(charFreq, uncrypt.length);
       HuffmanTree huffman = new HuffmanTree(charFreq);
       
       huffman.buildLookupTable();
       
       /*compress string to file*/
       FileOut encodedWriter = new FileOut(encodedPath);
       FileOut outputWriter = new FileOut(outputPath);
       huffman.encodeFile(uncrypt, encodedWriter); 
       try{
            FileReader encodedReader = new FileReader(new File(encodedPath));
            huffman.decodeFile(encodedReader, outputWriter);
       }
       catch(FileNotFoundException e){
           
       }
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
        sequence = sequence + " ";  //add extra so it prints out the last character
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
        for(int i = 0; i < 11; i++){
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
