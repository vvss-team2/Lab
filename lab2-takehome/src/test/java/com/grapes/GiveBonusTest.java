package com.grapes;
import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class GiveBonusTest {

    private final String IN_FILE = "IN.TXT";
    private final String OUT_FILE = "OUT.TXT";
    private List<String> runGiveBonusAndGetOutput(List<String> inputLines) throws Exception {
        Files.write(Paths.get(IN_FILE), inputLines);
        GiveBonus.main(new String[]{});
        return Files.readAllLines(Paths.get(OUT_FILE));
    }
    @After
    public void tearDown() throws Exception {
        Files.deleteIfExists(Paths.get(IN_FILE));
        Files.deleteIfExists(Paths.get(OUT_FILE));
    }

    @Test
    public void testTC1_ZeroEmployees_ReturnsCode1() throws Exception {

        List<String> input = Arrays.asList("0", "1", "IT", "100");
        
        List<String> output = runGiveBonusAndGetOutput(input);
        
        assertEquals("code=1", output.get(0));
        assertEquals(1, output.size()); 
    }

    @Test
    public void testTC2_ZeroSales_ReturnsCode1() throws Exception {
        List<String> input = Arrays.asList("1", "Alice", "IT", "dev", "4000", "0");
        
        List<String> output = runGiveBonusAndGetOutput(input);
        
        assertEquals("code=1", output.get(0));
        assertEquals("nE=1", output.get(1));
        assertEquals("Alice IT dev 4000", output.get(2)); 
    }

    @Test
    public void testTC3_NoEmployeesInWinningDept_ReturnsCode2() throws Exception {
        List<String> input = Arrays.asList("1", "Alice", "HR", "dev", "4000", "1", "IT", "100");
        
        List<String> output = runGiveBonusAndGetOutput(input);
        
        assertEquals("code=2", output.get(0));
        assertEquals("nE=1", output.get(1));
        assertEquals("Alice HR dev 4000", output.get(2));
    }

    @Test
    public void testTC4_BVA_Salary4999_Gets1000Bonus() throws Exception {
        List<String> input = Arrays.asList("1", "Alice", "IT", "dev", "4999", "1", "IT", "100");
        
        List<String> output = runGiveBonusAndGetOutput(input);
        
        assertEquals("code=0", output.get(0));
        assertEquals("nE=1", output.get(1));
        assertEquals("Alice IT dev 5999", output.get(2)); 
    }

    @Test
    public void testTC5_BVA_Salary5000_Gets1000Bonus() throws Exception {
        List<String> input = Arrays.asList("1", "Alice", "IT", "dev", "5000", "1", "IT", "100");
        
        List<String> output = runGiveBonusAndGetOutput(input);
        
        assertEquals("code=0", output.get(0));
        assertEquals("nE=1", output.get(1));
        assertEquals("Alice IT dev 6000", output.get(2));
    }

    @Test
    public void testTC6_BVA_Salary5001_Gets500Bonus() throws Exception {
        List<String> input = Arrays.asList("1", "Alice", "IT", "dev", "5001", "1", "IT", "100");
        
        List<String> output = runGiveBonusAndGetOutput(input);
        
        assertEquals("code=0", output.get(0));
        assertEquals("nE=1", output.get(1));
        assertEquals("Alice IT dev 5501", output.get(2)); 
    }

    @Test
    public void testTC7_EC_ManagerFunction_Gets500Bonus() throws Exception {
        List<String> input = Arrays.asList("1", "Alice", "IT", "manager", "4000", "1", "IT", "100");
        
        List<String> output = runGiveBonusAndGetOutput(input);
        
        assertEquals("code=0", output.get(0));
        assertEquals("nE=1", output.get(1));
        assertEquals("Alice IT manager 4500", output.get(2)); 
    }
}