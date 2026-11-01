
/*
 * DAA LP3 Practical
 * Experiment: Huffman Encoding using Greedy Strategy
 *
 * Aim:
 * To implement Huffman Encoding using a greedy approach.
 *
 * Algorithm:
 * 1. Create a leaf node for each character with its frequency.
 * 2. Insert all nodes into a Min Priority Queue.
 * 3. Remove the two nodes with the smallest frequencies.
 * 4. Create a new node with their combined frequency.
 * 5. Insert the new node back into the queue.
 * 6. Repeat until only one node remains.
 * 7. Traverse the Huffman tree:
 *    Left edge = 0, Right edge = 1.
 *
 * Greedy Strategy:
 * Always combine the two nodes having the minimum frequencies.
 *
 * Time Complexity: O(n log n)
 * Space Complexity: O(n)
 *
 * Sample Input:
 * Number of characters: 4
 * Characters: a b c d
 * Frequencies: 5 9 12 13
 *
 * One possible output:
 * Character    Huffman Code
 * a            110
 * b            111
 * c            10
 * d            0
 *
 * Note: Huffman codes may differ depending on tie-breaking,
 * but the encoding remains valid.
 *
 * Viva:
 * 1. Huffman coding is a lossless data compression technique.
 * 2. It uses a greedy strategy.
 * 3. Frequent characters generally get shorter codes.
 * 4. Prefix property: No code is the prefix of another code.
 */

import java.util.*;

public class HuffmanEncoding {

    // Node of the Huffman tree
    static class Node {
        char ch;
        int freq;
        Node left, right;

        Node(char ch, int freq) {
            this.ch = ch;
            this.freq = freq;
        }

        Node(Node left, Node right) {
            this.ch = '\0';
            this.freq = left.freq + right.freq;
            this.left = left;
            this.right = right;
        }

        // Leaf node represents an actual character
        boolean isLeaf() {
            return left == null && right == null;
        }
    }

    // Generate Huffman codes by traversing the tree
    static void generateCodes(Node root, String code) {
        if (root == null)
            return;

        if (root.isLeaf()) {
            // Handle the special case of a single character
            System.out.println(root.ch + " : "
                    + (code.isEmpty() ? "0" : code));
            return;
        }

        generateCodes(root.left, code + "0");
        generateCodes(root.right, code + "1");
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("\nEnter number of characters: ");
        int n = sc.nextInt();

        if (n <= 0) {
            System.out.println("\nNumber of characters must be positive.");
            sc.close();
            return;
        }

        char[] chars = new char[n];
        int[] freq = new int[n];

        System.out.println("\nEnter characters:");
        for (int i = 0; i < n; i++) {
            chars[i] = sc.next().charAt(0);
        }

        System.out.println("\nEnter frequencies:");
        for (int i = 0; i < n; i++) {
            freq[i] = sc.nextInt();

            if (freq[i] <= 0) {
                System.out.println("\nFrequencies must be positive.");
                sc.close();
                return;
            }
        }

        // Min Priority Queue: lowest frequency comes first
        PriorityQueue<Node> pq = new PriorityQueue<>(
            (a, b) -> Integer.compare(a.freq, b.freq)
        );

        // Insert all characters into the priority queue
        for (int i = 0; i < n; i++) {
            pq.add(new Node(chars[i], freq[i]));
        }

        // Greedy step: combine the two minimum-frequency nodes
        while (pq.size() > 1) {
            Node left = pq.poll();
            Node right = pq.poll();

            Node parent = new Node(left, right);
            pq.add(parent);
        }

        // The remaining node is the root of the Huffman tree
        Node root = pq.poll();

        System.out.println("\nCharacter : Huffman Code");
        generateCodes(root, "");
        
        sc.close();
    }
}
