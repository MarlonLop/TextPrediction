import java.util.ArrayList;
import java.util.List;

/**
 * Class to create a Trie - a Tree-type data structure
 * @author Marlon Lopez
 * CS4050 - Assignment 2
 *
 */
public class Trie {
    Node root;  //root of Trie
    private final char A = 'a';     // reference to determine index of children


    /**
     * nested class to define a node
     */
    class Node {
        private final int N = 26;       // size of array for children
        Node[] children;        // node array that maps to each char in alphabet
        String letter;         // to store word if node forms a word
        boolean isWord;     // flag to check if node is a word

        /**
         * Node constructor
         * by default sets isWord to false
         */
        public Node() {
            this.children = new Node[N];
            this.isWord = false;
        }

        /**
         * returns the specific child node index
         * it assumes the char param is in lower case
         *
         * @param ch the character to find a specific child node index
         * @return the index of the child node given by its character
         */
        public int getIndex(char ch) {
            return ch - A;
        } //getIndex
    } //Node inner class

    /**
     * Trie constructor
     */
    public Trie() { root = new Node(); }

    /**
     * this methods inserts a word into the trie
     * once is done iterating through all characters the isWord flag
     * is set to true
     * @param word to be map to the trie
     */
    public void insertWord(String word) {
        Node currentNode = root;

        for(int i = 0; i < word.length(); i++) {
            char ch = word.charAt(i);
            int index = ch - A;  //to hold letter index

            if(currentNode.children[index] == null){
                Node node = new Node();
                node.letter = String.valueOf(ch);
                currentNode.children[index] = node;
                currentNode = node;
//                System.out.print(word.charAt(i)); //debugging
            }  //if
            else {
                currentNode = currentNode.children[index];
            } //else
        } // for
        currentNode.isWord = true;   // sets last node of the word to true
    }

    /**
     * This method searches the trie for words that match
     * the given string prefix.
     * this method uses the BreadthFS method to search the trie
     * a console message is displayed if the trie has no words
     * that contain the given prefix
     * @param prefix the string prefix to search in the trie
     * @return a List of words found with the prefix parameter
     */
    public List<String> searchPrefix(String prefix) {
        Node curNode = root; //reference to current node
        List<String> wordsFound = new ArrayList<>(); //to hold list of words found

        for(int i = 0;i<prefix.length();++i) {
            char ch = prefix.charAt(i);
            int index = ch - A;
            try {
                curNode = curNode.children[index];
            } catch (NullPointerException e){
                System.out.println("no words found with such prefix");
            } // try
        } //for
        breadthFS(prefix, curNode, wordsFound);
        return wordsFound;
    } //searchPrefix

    /**
     * this method uses recursion to implement a breadth first search
     * in the trie with the given prefix parameter
     * this method is set to return a List containing a maximum of ten words
     * that contain the given prefix parameter
     * @param prefix string to search words in the trie
     * @param node current node to search
     * @param wordsList List of words found in the trie with the given prefix
     */
    private void breadthFS(String prefix, Node node, List<String> wordsList) {
        final int SIZE = 10; // size of words list to return
        if (node == null) return;
        if (node.isWord) {wordsList.add(prefix);}

        for (Node child : node.children) {
            if ( child == null ) {continue;}
            String childNodeLetter = child.letter;
            if (wordsList.size() == SIZE) {return;}
            breadthFS(prefix + childNodeLetter, child, wordsList);  //recursive call
        } //for
    } // breadthFS

} //Trie class
