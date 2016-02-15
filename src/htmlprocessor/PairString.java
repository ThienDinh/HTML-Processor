package htmlprocessor;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * PairString class makes a pair of two things
 *
 * @author ThienDinh
 */
public class PairString {

    private String first;
    private String second;

    /**
     * Constructor with two parameters.
     *
     * @param first the first element
     * @param second the second element
     */
    public PairString(String first, String second) {
        this.first = first;
        this.second = second;
    }

    /**
     * Get the first element.
     *
     * @return the first element
     */
    public String getFirst() {
        return first;
    }

    /**
     * Get the second element.
     *
     * @return the second element
     */
    public String getSecond() {
        return second;
    }

    public String toString() {
        return "(" + this.getFirst() + ", " + this.getSecond() + ")";
    }

    /**
     * Swap the two elements in the pair.
     */
    public void swap() {
        String temp = first;
        first = second;
        second = temp;
    }
}
