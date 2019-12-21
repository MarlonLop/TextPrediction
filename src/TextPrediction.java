import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Marlon Lopez
 * CS4050 - Assignment 2
 *
 * This class contains the main logic to for the Text Prediction program
 * it also contains an inner class used to create the GUI
 */
public class TextPrediction {
    // file name of dictionary to be loaded in Trie
    private final String DICTIONARY = "dictionary.txt";
    //  private final String DICTIONARY = "testdictionary.txt"; // used for debugging
    private File file;      // to open dictionary file
    private BufferedReader fileReader;    //BufferedReader to read from a file
    private Trie trie = new Trie();      // tree to map dictionary
    private static List<String> wordsFound = new ArrayList<>();    //list to hold predicted words

    /**
     * this methods verifies that the dictionary text file exist
     * and is located in the root directory
     * if the dictionary is not found, a message is displayed on the
     * console and the program exits
     */
    private void dictionaryExist() {
        file = new File(DICTIONARY);
        if (file.exists() && !file.isDirectory()) {
            if (file.length() == 0) {
                System.out.println("Dictionary file is empty!!!" +
                        "\nExiting Program.");
                System.exit(0);
            } else {
                System.out.println("Dictionary file found");
            } // nested if
        } // if
        else {
            System.out.println("Dictionary text file not found! " +
                    "\nMake sure Dictionary file is located in the root directory" +
                    "\nMake sure dictionary file is name 'dictionary.txt'" +
                    "\nExiting Program.");
            System.exit(0);
        } //else
    } //dictionaryExist

    /**
     * this method reads each line from the dictionary.txt file
     * and inserts the words into the trie data structure
     * Note: the dictionary.txt file must contain only one word per line
     */
    private void loadTrie() {
        String fileLine;
        try {
            fileReader = new BufferedReader(new FileReader(DICTIONARY));
            while ((fileLine = fileReader.readLine()) != null) {
                trie.insertWord(fileLine.toLowerCase());
            }//end while
            fileReader.close(); //close read file
            System.out.println();
        } //end try
        catch (IOException e) {
            System.out.println("An error occurred while reading the file\n" +
                    DICTIONARY + "\nExiting program\n");
            //e.printStackTrace();	//debugging
            System.exit(0);  //exiting program
        } //catch
    } //loadTrie

    /**
     * validates the text entered by the user to make sure
     * only letters are sent to trie search
     *
     * @param entry text typed by user on gui
     * @return true if text contains only letters, false otherwise
     */
    public boolean validateEntry(String entry) {
        for (int i = 0; i < entry.length(); i++) {
            if (!Character.isLetter(entry.charAt(i))) {
                return false;
            }
        }// for
        return true;
    } //validateEntry

    /**
     * this class creates the gui for the text prediction program
     */
    static class GUI extends JFrame {
        private final int WIDTH = 250; //gui width
        private final int HEIGHT = 270; //gui height
        private TextPrediction app;
        private JLabel searchField;
        private JTextField textEntry;
        private JTextArea textArea;

        /**
         * default constructor of GUI class
         * @param app is a TextPrediction object used to
         *            get a list of possible predicted words
         */
        public GUI(TextPrediction app) {
            this.setTitle("Text Prediction");
            this.app = app;
            this.setSize(WIDTH, HEIGHT);
            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            this.setLayout(new FlowLayout(FlowLayout.CENTER));

            //instantiating components for GUI
            searchField = new JLabel("Start typing: ");
            textEntry = new JTextField();
            textEntry.setPreferredSize(new Dimension(130, 20));
            textArea = new JTextArea(10, 10);
            textArea.setEditable(false);

            //adding components to frame
            add(searchField);
            add(textEntry);
            add(textArea);

            //Key listener to retrieve text typed by user
            textEntry.addKeyListener(new KeyAdapter() {
                public void keyReleased(KeyEvent e) {
                    textArea.setText(null);
                    JTextField textField = (JTextField) e.getSource();
                    String text = textField.getText();
                    if (app.validateEntry(text) && !text.isEmpty()) {
                        wordsFound = app.trie.searchPrefix(text);
                        if (wordsFound.size() > 0) {
                            for (int i = 0; i < wordsFound.size(); i ++) {
//                                displayWords.setText(word);
                                textArea.append(wordsFound.get(i) + "\n");

                            } //for
                        } //if
                    } //if
                } //keyReleased
            }); //keyListener
            } //GUI constructor
        } // GUI class

    /**
     * main method to run program
     */
    public static void main(String[] args) {
        TextPrediction app = new TextPrediction();

        app.dictionaryExist();  //check that dictionary file exists
        app.loadTrie();         //load trie with dictionary
        GUI window = new GUI(app); //create GUI
        window.setLocationRelativeTo(null); //set GUI window to the center of screen
        window.setVisible(true);    //set GUI visible
    } //main method

} //TextPrediction class