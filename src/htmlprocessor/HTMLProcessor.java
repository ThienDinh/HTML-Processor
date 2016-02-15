package htmlprocessor;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/**
 * HTMLProcessor helps working with HTML easier.
 *
 * @version 2014.08.13
 * @author ThienDinh
 */
public class HTMLProcessor {

    /**
     * Get the HTML lines that contains phrases in the filter list.[Checked]
     *
     * @param filteringList a list contains specifying sentences
     * @return an array list of selected HTML lines
     * @throws java.io.IOException
     */
    public static LinkedList<String> getFilteredLines(String address, List<String> filteringList) throws IOException {
        URL pageLocation = new URL(address);
        URLConnection connection = pageLocation.openConnection();
        // Check if response code is HTTP_OK (200)
        HttpURLConnection httpConnection = (HttpURLConnection) connection;
        int code = httpConnection.getResponseCode();
        String message = httpConnection.getResponseMessage();
        System.out.println(address + " " + code + " " + message);
        InputStream instream = connection.getInputStream();
        Scanner in = new Scanner(instream, "UTF-8");
        // Filter the wanted lines, using LinkedList to minimize storage allocated.
        LinkedList<String> filteredHTML = new LinkedList<>();
        while (in.hasNextLine()) {
            String line = in.nextLine().trim();
            for (String filter : filteringList) {
                // If the line contains a phrase in the filteringList.
                if (line.contains(filter)) {
                    filteredHTML.addLast(line);
                }
            }
        }
        in.close();
        return filteredHTML;
    }

    /**
     * Get portions of a HTML file filtered by a pair of opening and closing
     * tags.[Checked]
     *
     * @param tagPair a tagPair that specifies head and end tags
     * @return a linked list of lines of the portion
     * @throws IOException
     */
    public static LinkedList<String> getFilteredPortions(String address, PairString tagPair) throws IOException {
        URL pageLocation = new URL(address);
        Scanner in = new Scanner(pageLocation.openStream(), "UTF-8");
        LinkedList<String> filteredPortion = new LinkedList<String>();
        boolean firstTagFound = false;
        String portion = "";
        while (in.hasNextLine()) {
            String line = in.nextLine().trim();
            // If first tag found in a line
            if (line.contains(tagPair.getFirst())) {
                //If this is the opening tag is not found yet
                if (!firstTagFound) {
                    portion += line;
                    firstTagFound = true;
                } else {
                    // else found a new opening tag, reset the portion to begin with the new opening tag
                    portion = line;
                }
                continue;
            }
            // If the line contains closing tag, then add to the list.
            if (line.contains(tagPair.getSecond()) && firstTagFound) {
                portion += "\n" + line;
                filteredPortion.addLast(portion);
                firstTagFound = false;
                portion = "";
                continue;
            }
            // If opening tag is found, then start accumulating
            if (firstTagFound) {
                portion += "\n" + line;
            }
        }
        in.close();
        return filteredPortion;
    }

    /**
     * Remove all tags, but keep the content between tags in a HTML line. [Checked]
     *
     * @param HTMLine a HTML line.
     * @return a line with tags removed.
     */
    public static String emptyTags(String HTMLine) {
        String editedLine = "";
        boolean recordEnable = true;
        for (int i = 0; i < HTMLine.length(); i++) {
            char ch = HTMLine.charAt(i);
            // Record characters that not in between opening and closing marks
            if (ch == '<') {
                recordEnable = false;
            } else if (ch == '>') {
                recordEnable = true;
                continue;
            }
            if (recordEnable) {
                editedLine += ch;
            }
        }
        return editedLine;
    }

    /**
     * Empty tags, but replace some specified tags from a HTML line by specified
     * words using pairs. [Checked]
     *
     * @param HTMLine an original HTML line
     * @param modifyingList a comparing list to replace some tags
     * @return a line without tags and added some parts
     */
    public static String emptyTags(String HTMLine, List<PairString> modifyingList) {
        String editedLine = "";
        boolean recordEnable = true;
        int firstIndex = 0;
        int secondIndex = 0;
        for (int i = 0; i < HTMLine.length(); i++) {
            char ch = HTMLine.charAt(i);
            if (ch == '<') {
                recordEnable = false;
                firstIndex = i;
            } else if (ch == '>') {
                recordEnable = true;
                secondIndex = i;
                // Complete getting a tag
                String tag = HTMLine.substring(firstIndex, secondIndex + 1);
                for (PairString pair : modifyingList) {
                    if (tag.contains(pair.getFirst())) {
                        editedLine += pair.getSecond();
                        break;
                    }
                }
                continue;
            }
            if (recordEnable) {
                editedLine += ch;
            }
        }
        return editedLine;
    }

    /**
     * Get tags that contains specifics words inside it.[Checked]
     *
     * @param HTMLine
     * @param filteringList
     * @return
     */
    public static LinkedList<String> getTags(String HTMLine, List<String> filters) {
        LinkedList<String> tagsList = new LinkedList<>();
        int firstIndex = 0;
        int secondIndex = 0;
        for (int i = 0; i < HTMLine.length(); i++) {
            char ch = HTMLine.charAt(i);
            if (ch == '<') {
                firstIndex = i;
            } else if (ch == '>') {
                secondIndex = i;
                // Complete getting a tag
                String tag = HTMLine.substring(firstIndex, secondIndex + 1);
                for (String filter : filters) {
                    // If a tag contains some specific phrases of the filters
                    if (tag.contains(filter)) {
                        tagsList.addLast(tag);
                        break;
                    }
                }
            }
        }
        return tagsList;
    }

    /**
     * Get a line between a pair of tags in a long HTML line.[Checked]
     *
     * @param pair
     * @param broad
     * @param HTMLine an original HTML line
     * @return a portion from the first check to the last check
     */
    public static String getPortionBetween(String HTMLine, PairString pair, boolean broad) {
        String portionedPart;
        boolean firstMarkSet = false;
        boolean openSignSet = false;
        int firstMark = 0;
        int lastMark = 0;
        int firstIndex = 0;
        int secondIndex = 0;
        for (int i = 0; i < HTMLine.length(); i++) {
            char ch = HTMLine.charAt(i);
            if (ch == '<') {
                firstIndex = i;
                openSignSet = true;
            } else if (ch == '>' && openSignSet) {
                secondIndex = i;
                // Complete getting a tag with <>
                String tag = HTMLine.substring(firstIndex, secondIndex + 1);
                if (broad) {
                    if (tag.contains(pair.getFirst()) && !firstMarkSet) {
                        firstMark = firstIndex;
                        firstMarkSet = true;
                    }
                    if (tag.contains(pair.getSecond())) {
                        lastMark = secondIndex;
                    }
                } else {
                    if (tag.contains(pair.getFirst())) {
                        firstMark = firstIndex;
                        firstMarkSet = true;
                        // Need to flag when it can first tag
                    }
                    // Need to check the first tag before break
                    if (tag.contains(pair.getSecond()) && firstMarkSet) {
                        lastMark = secondIndex;
                        break;//reset
                    }
                }
            }
        }
        portionedPart = HTMLine.substring(firstMark, lastMark + 1);
        return portionedPart;
    }

    /**
     * Extract parts that have the beginning tag and closing tags within a HTML
     * line.[Checked]
     *
     * @param HTMLine
     * @param pair
     * @return
     */
    public static LinkedList<String> getListPortions(String HTMLine, PairString pair) {
        LinkedList<String> portions = new LinkedList<>();
        int openningIndex = 0;
        int closingIndex = 0;
        boolean openningTagFound = false;
        int firstMark = 0;
        int lastMark = 0;
        for (int i = 0; i < HTMLine.length(); i++) {
            char ch = HTMLine.charAt(i);
            // This if else statement checks for character < or >
            if (ch == '<') {
                openningIndex = i;
            } else if (ch == '>') { // When complete getting a tag
                closingIndex = i;
                // Get the string tag
                String tag = HTMLine.substring(openningIndex, closingIndex + 1);
                // If this tag is the beginning tag of the pair,
                // then save the index of the first character of the tag.
                if (tag.contains(pair.getFirst()) && !openningTagFound) {
                    firstMark = openningIndex;
                    openningTagFound = true;
                }
                // If this tag is the ending tag of the pair,
                // save the index of the last character of the tag.
                if (tag.contains(pair.getSecond()) && openningTagFound) {
                    lastMark = closingIndex;
                    // Add the substring into the portion list
                    portions.addLast(HTMLine.substring(firstMark, lastMark + 1));
                    // Reset for the next extraction
                    openningTagFound = false;
                }
            }
        }
        return portions;
    }
}
