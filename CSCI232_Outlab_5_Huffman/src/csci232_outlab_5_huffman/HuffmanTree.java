package csci232_outlab_5_huffman;

import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Stack;

/**
 *
 * @author Roy Smart
 */
public class HuffmanTree {

    public Node root;
    int startChar = (int) ' ';
    int endChar = (int) '~';
    int treeSize = endChar - startChar;
    Node[] nodes = new Node[treeSize];

    public HuffmanTree(int[] freq) {
        createNodes(freq);
        root = buildTree();
    }

    public void createNodes(int[] freq) {
        for (int i = 0; i < treeSize; i++) {
            String data = "" + (char) (i + startChar);
            if (freq[i] > 0) {
                nodes[i] = new Node(data, freq[i]);
                nodes[i].isLeaf = true;
            }
        }
    }

    public Node buildTree() {
        PriorityQueue<Node> priority = new PriorityQueue<>();
        /*add items into priority Queue*/
        for (int i = 0; i < nodes.length; i++) {
            if (nodes[i] != null) {
                priority.add(nodes[i]);
            }
        }

        Node nextRoot = null;
        while (!priority.isEmpty()) {
            Node a = priority.remove();
            if (priority.isEmpty()) {
                break;
            }
            Node b = priority.remove();
            nextRoot = buildBranch(a, b);
            priority.add(nextRoot);
        }
        return nextRoot;
    }

    public Node buildBranch(Node a, Node b) {
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

    public void buildLookupTable() {
        Node node;
        for (int i = 0; i < treeSize; i++) {
            node = nodes[i];
            if (node != null) {
                System.out.print(node.data + " ");
                while (node != root) {
                    System.out.print((int) node.code);
                    node = node.parent;
                }
                System.out.println();
            }
        }
    }

    public void encodeFile(char[] string, FileOut writer) {
        byte nextByte = 0x00;
        int bitShift = 0;
        Stack<Node> stack = new Stack();
        for (int i = 0; i < string.length; i++) {
            Node node = nodes[(int) string[i] - startChar];
            while (node != root) {
                stack.push(node);
                node = node.parent;
            }
            while (!stack.empty()) {
                node = stack.pop();
                byte nextBit = (byte) (node.code);
                nextByte = (byte) (nextByte << 1);
                nextByte = (byte) (nextByte | nextBit);
                if (bitShift == 7) {  
                    writer.writer((char) (nextByte));
                    nextByte = 0x00;
                }
                bitShift = (bitShift + 1) % 8;
            }
        }
    }

    @SuppressWarnings("empty-statement")
    public void decodeFile(FileReader scanner, FileOut writer) {
        byte nextByte;
        byte mask;
        Node node = root;
        int carriageReturn = 1;

        try {
            while (scanner.ready()) {
                nextByte = (byte) scanner.read();

                mask = (byte) 0x80;;
                for (int i = 0; i < 8; i++) {
                    if (node.isLeaf) {
                        writer.writer(node.data);
                        node = root;
                        if (carriageReturn % 100 == 0) {
                            writer.newln();                            
                        }
                        carriageReturn++;
                    }
                    byte nextBit = (byte) (mask & nextByte);
                    mask = (byte) (mask >> 1);
                    mask = (byte) (mask & 0x7F);
                    if (nextBit == 0) {
                        node = node.left;
                    } else {
                        node = node.right;
                    }
                }
            }
        } catch (IOException e) {

        }

    }
}
