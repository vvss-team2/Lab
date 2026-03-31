package com.starfruit;

import static org.junit.Assert.*;
import org.junit.Test;

public class SetTest {

    @Test
    public void testConstructor() {
        Set set = new Set(10);
        assertEquals(0, set.nVS);
        assertEquals(10, set.vS.length);
    }

    @Test
    public void testAddAValue_NewValue() {
        Set set = new Set(5);
        boolean result = set.AddAValue(1);
        assertTrue(result);
        assertEquals(1, set.nVS);
        assertEquals(1, set.vS[0]);
    }

    @Test
    public void testAddAValue_DuplicateValue() {
        Set set = new Set(5);
        set.AddAValue(1);
        boolean result = set.AddAValue(1);
        assertFalse(result);
        assertEquals(1, set.nVS); // should not increase
    }

    @Test
    public void testIsInTheSet_ValuePresent() {
        Set set = new Set(5);
        set.AddAValue(1);
        set.AddAValue(2);
        assertTrue(set.IsInTheSet(1));
        assertTrue(set.IsInTheSet(2));
    }

    @Test
    public void testIsInTheSet_ValueNotPresent() {
        Set set = new Set(5);
        set.AddAValue(1);
        assertFalse(set.IsInTheSet(2));
    }

    @Test
    public void testMultipleAdds() {
        Set set = new Set(5);
        set.AddAValue(1);
        set.AddAValue(2);
        set.AddAValue(3);
        assertEquals(3, set.nVS);
        assertTrue(set.IsInTheSet(1));
        assertTrue(set.IsInTheSet(2));
        assertTrue(set.IsInTheSet(3));
    }
}