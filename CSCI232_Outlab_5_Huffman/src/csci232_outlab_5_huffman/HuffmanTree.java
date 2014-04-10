
package csci232_outlab_5_huffman;

import java.io.FileReader;
import java.io.IOException;
import java.util.PriorityQueue;

/**
 *
 * @author Roy Smart
 */
public class HuffmanTree {
    public Node root;
    int startChar = (int)' ';
    int endChar = (int)'~';
    int treeSize =  endChar - startChar;
    Node[] nodes = new Node[treeSize];
    
    public HuffmanTree(int[] freq){
        createNodes(freq);
        root = buildTree();
    }
    
    public void createNodes(int[] freq){
        for(int i = 0; i < treeSize; i++){
            String data = "" + (char) (i + startChar);
            if(freq[i] > 0){
                nodes[i] = new Node(data, freq[i]);
                nodes[i].isLeaf = true;
            } 
        }
    }
    
    public Node buildTree(){
        PriorityQueue<Node> priority = new PriorityQueue<>();
        /*add items into priority Queue*/
        for(int i = 0; i < nodes.length; i++){
            if(nodes[i] != null) priority.add(nodes[i]);
        }
        
        Node nextRoot = null;
        while(!priority.isEmpty()){
            Node a = priority.remove();
            if(priority.isEmpty()) break;
            Node b = priority.remove();
            nextRoot = buildBranch(a,b);
            priority.add(nextRoot);
        }
        return nextRoot;
    }
    
    public Node buildBranch(Node a, Node b){
        int comboFreq = a.freq + b.freq;
        String comboData = "" + a.data + b.data;
        Node comboNode = new Node(comboData, comboFreq);
        a.code = 0x00;
        a.parent = comboNode;
        comboNode.left = a;
        b.code = 0x01;
        b.parent = comboNode;
        comboNode.right = b;
        return comboNode;
    }
    
    public void buildLookupTable(){
        int[] lookup = 10  ;
        Node nodes;
        int bitShift;
        for(int i = 0; i < treeSize; i++){
            Node startNode = nodes[i];
            node = startNode;
            while(node != root){
                
            }
        }
    }
    
    public void encodeFile(char[] string, FileOut writer){
        byte nextByte = 0x00;
        int bitShift = 0;
        int startBit;
        for(int i = 0; i <  string.length; i++){                                   
            Node node = nodes[(int)string[i] - startChar];
            startBit = bitShift;
            while(node != root){                
                byte nextBit= (byte)(node.code << bitShift);
                nextByte = (byte)(nextByte | nextBit);
                if(bitShift == 7) {
                    writer.writer((char)(nextByte));
                    nextByte = 0x00;
                }
                node = node.parent;
                bitShift = (bitShift + 1)%8;
            }
        }
    }
    
    @SuppressWarnings("empty-statement")
    public void decodeFile(FileReader scanner){
        byte nextByte;
        byte mask;
        Node node = root;
        
        try{
        while(scanner.ready()){
            nextByte = (byte)scanner.read();
            nextByte = nextByte;
            mask = (byte)0x80;
            for(int i = 0; i < 8; i++){
                if(node.isLeaf){
                    System.out.print(node.data);
                    node = root;
                }
                byte nextBit = (byte)(mask & nextByte);
                mask = (byte)(mask >> 1);
                mask = (byte)(mask & 0x7F);
                if(nextBit == 0){
                    node = node.left;
                }
                else{
                    node = node.right;
                }
            }
        }
        }
        catch(IOException e){
            
        }
        
    }
}
