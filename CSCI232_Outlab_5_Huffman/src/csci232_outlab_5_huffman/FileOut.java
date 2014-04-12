/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package csci232_outlab_5_huffman;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;


/**
 *
 * @author byrdie
 */
public class FileOut{
   
   PrintStream fout; 
   BufferedReader fin;

 
   FileOut(String filename)
   {
     try{
         fout= new PrintStream(new FileOutputStream(filename));
     }catch(IOException fo){
         System.out.println(fo); 
      }
    }
   FileOut(String filename, String in)
   {
       try{
       fin = new BufferedReader(new FileReader(filename));

     }catch(IOException fo){
         System.out.println(fo); 
      }
    }
   public void writer(String out)
   {
     
         fout.print(out);
      
   }
   public void writer(int out)
   {
     
         fout.println(out);
      
   }
   public void writer(char out)
   {
     
         fout.print(out);
      
   }
   public void writer(double out)
   {
     
         fout.println(out);
      
   }
   public void writer(float out)
   {
     
         fout.println(out);
      
   }
   public void newln(){
       fout.println();
   }
   public String reader(){
      try{
       return fin.readLine();     
       }catch(IOException e){
         System.out.println("error reading from file " + e);
         return null;
       }
   }
}
