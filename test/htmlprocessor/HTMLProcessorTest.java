/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package htmlprocessor;

import java.util.LinkedList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author ThienDinh
 */
public class HTMLProcessorTest {
    
    public HTMLProcessorTest() {
    }
    
    @Before
    public void setUp() {
    }

    /**
     * Test of getFilteredLines method, of class HTMLProcessor.
     */
    @Test
    public void testGetFilteredLines() throws Exception {
        System.out.println("getFilteredLines");
        String address = "";
        List<String> filteringList = null;
        LinkedList<String> expResult = null;
        LinkedList<String> result = HTMLProcessor.getFilteredLines(address, filteringList);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getFilteredPortions method, of class HTMLProcessor.
     */
    @Test
    public void testGetFilteredPortions() throws Exception {
        System.out.println("getFilteredPortions");
        String address = "";
        PairString tagPair = null;
        LinkedList<String> expResult = null;
        LinkedList<String> result = HTMLProcessor.getFilteredPortions(address, tagPair);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of emptyTags method, of class HTMLProcessor.
     */
    @Test
    public void testEmptyTags_String() {
        System.out.println("emptyTags");
        String HTMLine = "<b><d><c>Example</c></d><r>Ok</r>";
        String expResult = "ExampleOk";
        String result = HTMLProcessor.emptyTags(HTMLine);
        assertEquals(expResult, result);
    }

    /**
     * Test of emptyTags method, of class HTMLProcessor.
     */
    @Test
    public void testEmptyTags_String_List() {
        System.out.println("emptyTags");
        String HTMLine = "";
        List<PairString> modifyingList = null;
        String expResult = "";
        String result = HTMLProcessor.emptyTags(HTMLine, modifyingList);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getTags method, of class HTMLProcessor.
     */
    @Test
    public void testGetTags() {
        System.out.println("getTags");
        String HTMLine = "";
        List<String> filters = null;
        LinkedList<String> expResult = null;
        LinkedList<String> result = HTMLProcessor.getTags(HTMLine, filters);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPortionBetween method, of class HTMLProcessor.
     */
    @Test
    public void testGetPortionBetween() {
        System.out.println("getPortionBetween");
        String HTMLine = "";
        PairString pair = null;
        boolean broad = false;
        String expResult = "";
        String result = HTMLProcessor.getPortionBetween(HTMLine, pair, broad);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getListPortions method, of class HTMLProcessor.
     */
    @Test
    public void testGetListPortions() {
        System.out.println("getListPortions");
        String HTMLine = "";
        PairString pair = null;
        LinkedList<String> expResult = null;
        LinkedList<String> result = HTMLProcessor.getListPortions(HTMLine, pair);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
