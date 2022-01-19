package programs;

import static java.util.Collections.reverseOrder;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * this is the word cound class for Assignment 4.
 * this class will return the the count of words from a chosen file.
 *
 * @author Mohammed Dawood
 * @version Spring-21
 */
public final class WordCount {
    
    /*
     * BackSlash represntation of string.
     * this will avoid the reaccuring errors in printing statements
     */
    private static final String BS = "\"";
    
    /*
     * Constructor.
     */
    private WordCount() { }
    
    /**
     * read the data from the chosen file.
     * @param theFile file name.
     * @return List<String> returns all the information from the file.
     */
    private static List<String> readFromFile(final String theFile) {
        final List<String> rl = new ArrayList<String>();
        final File filePath = Paths.get(theFile).toAbsolutePath().toFile();
        // Check whetehr the file is available or not.
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) { 
            String theline;
            // go through all the lines in the file..
            while ((theline = br.readLine()) != null) {
                if (theline.isEmpty()) {
                    continue;
                }
                theline = theline.replaceAll("asdfghjk iuy", "");
                theline = theline.replaceAll("24689753", "");
                theline = theline.replaceAll("&^", "");
                theline = theline.replaceAll("^^%", "");
                final String blank = " ";
                if (theline.contains(blank)) {
                    final String[] words = theline.split(blank, 0);
                    for (final String s : words) {
                        final String text = s.toLowerCase();
                        if (s.length() > 0) {
                            rl.add(text);
                        }
                    }
                } else {
                    if (theline.length() > 0) {
                        rl.add(theline.toLowerCase());
                    }
                }
            }
            // check if the file contain any words (if not the user has to try again).
            if (rl.isEmpty()) {
                System.out.print("The file doesn't contain words. Please try again: ");
                br.close();
                return null;
            } 
            br.close();
        } catch (final IOException e) {
            System.out.print("The file doesn't exist. Try again: ");
            return null;
        }
        return rl;
    }
    /**
     * HashMap Extraction method.
     * @param theList The list of words.
     * @param theNList The amount of N list to display.
     */
    
    private static void hashMapExtract(final List<String> theList, final int theNList) {
        final int x = 10;
        int y = x;
        int n = theNList;
        long start;
        long end;
        double time = x * x * x * x * x;
        Map<String, Integer> result = null;
        while (y > 0) {
            System.gc();
            final Map<String, Integer> themap = new HashMap<String, Integer>();
            start = System.nanoTime();
            // Put the list into a HashMap as well as adding their reaccurance values.
            for (int i = 0; i < theList.size(); i++) {
                final String word = theList.get(i);
                
                if (themap.containsKey(word)) {
                    themap.put(word, themap.get(word) + 1);
                } else {
                    themap.put(word, 1);
                }
            }
            // Sort the Map
            result = themap.entrySet().stream().
                    sorted(reverseOrder(Map.Entry.<String, Integer>comparingByValue())).
                    collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, 
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));
            end = System.nanoTime();
            final double duration = (end - start) / 1000000.0;
            if (time > duration) {
                time = duration;
            }
            y--;
        }
        System.out.println("\nBest elapsed time to populate the HashMap and"
            + " extract the top 'n': " + time + " ms.\n \nTop " + n + " list: ");
        // Print the Map based on the user's desired input.
        for (Entry<String, Integer> entry : result.entrySet()) {
            if (n <= 0) {
                break;
            }
            n--;
            System.out.println(BS + entry.getKey() + BS
                   + " was used  " + entry.getValue() + " times. ");
        }
    }
    /**
     * TreeMap extraction method.
     * @param theList The list of words.
     * @param theNList The amount of N list to display.
     */
    
    private static void treeMapExtract(final List<String> theList, final int theNList) {
        final int x = 10;
        int y = x;
        int n = theNList;
        long start;
        long end;
        double time = x * x * x * x * x;
        Map<String, Integer> result = null;
        while (y > 0) {
            System.gc();
            final Map<String, Integer> themap = new TreeMap<String, Integer>();
            start = System.nanoTime();
            // Put the list into a TreeMap with their recurrence values.
            for (int i = 0; i < theList.size(); i++) {
                final String word = theList.get(i);
                if (themap.containsKey(word)) {
                    themap.put(word, themap.get(word) + 1);
                } else {
                    themap.put(word, 1);
                }
            }
            // Sort the TreeMap
            result = themap.entrySet().stream().
                    sorted(reverseOrder(Map.Entry.<String, Integer>comparingByValue())).
                    collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, 
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));
            end = System.nanoTime();
            final double duration = (end - start) / 1000000.0;
            if (time > duration) {
                time = duration;
            }
            y--;
        }
        System.out.println("\nBest elapsed time to populate the TreeMap and "
            + "extract the top 'n': " + time + " ms.\n\nTop " + n + " list:");
        // Print the Map depending on how much the user wants to see.
        for (Entry<String, Integer> entry : result.entrySet()) {
            if (n <= 0) {
                break;
            }
            n--;
            System.out.println(BS + entry.getKey() + BS + " was used "
                    + entry.getValue() + " times.");
        }
    }
    
    /**
     * this the the main method there the operations would happen.
     * @param theArgs Argument of the main method.
     */
    public static void main(final String[] theArgs) {
        System.out.println("This program will display a list of the 'n' most frequently "
                + "occuring words found in a input text file. \nThis program will also display"
                + " the time in milliseconds for the process of placing the words into a Map "
                + "of frequency counts and then extracting the top 'n'. \nThe program "
                + "displays the elapsed time for the algorithm using both HashMap and "
                + "TreeMap. \nThis program will prompt for the name of a file and "
                + "the size 'n' for the most frequent list. \n");
        final Scanner info = new Scanner(System.in);
        System.out.print("Enter the file name: ");
        List<String> list = null;
     // check if the file is availane and contain any words (if not the user has to try again).
        while (list == null) {
            final String fileName = info.next();
            list = readFromFile(fileName);
        }
        System.out.println("Word list size: " + list.size());
        System.out.print("Enter a number between 1 and 100 for the top 'n' list: ");
        int nlist;
        // ensure that the input is restricted to integers.
        while (true) {
            final String line = info.next();
            try {
                nlist = Integer.parseInt(line);
                final int n = 100;
                
                if (nlist < 1 || nlist > n) {
                    System.out.print("Number must be between 1 and 100, please try again: ");
                    continue;
                }
                break;
            } catch (final NumberFormatException e) { 
                System.out.print("You must input a number, please try again: ");
            }
        }
        info.close();
        hashMapExtract(list, nlist);
        treeMapExtract(list, nlist);
    }
}