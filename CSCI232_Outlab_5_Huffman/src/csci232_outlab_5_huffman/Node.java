/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package csci232_outlab_5_huffman;

/**
 *
 * @author Roy Smart
 */
public class Node implements Comparable<Node>{
    public String data;
    public int freq;
    public byte code;
    public int[] lookup;
    public Node left;
    public Node right;
    public Node parent;
    public boolean isLeaf = false;
    
    public Node(String d, int f){
        data = d;
        freq = f;
        left = null;
        right = null;
        parent = null;
    }
    
    @Override
    public int compareTo(Node node){
        return Integer.compare(this.freq, node.freq);
    }
}
