package com.grapes;

import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

import java.io.IOException;
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
        if (!Files.exists(Paths.get(OUT_FILE))) {
            throw new RuntimeException("OUT.TXT was not created!");
        }
        return Files.readAllLines(Paths.get(OUT_FILE));
    }

    @After
    public void tearDown() throws IOException {
        Files.deleteIfExists(Paths.get(IN_FILE));
        Files.deleteIfExists(Paths.get(OUT_FILE));
    }

    @Test
    public void testBVA_TC1_SalaryExactly5000() throws Exception {
        List<String> input = Arrays.asList("1", "Alice", "d1", "f1", "5000", "1", "d1", "2000");
        List<String> output = runGiveBonusAndGetOutput(input);
        
        assertEquals("code=0", output.get(0));
        assertEquals("nE=1", output.get(1));
        assertEquals("Alice d1 f1 6000", output.get(2));
    }

    @Test
    public void testBVA_TC2_SalaryJustOver5000() throws Exception {
        List<String> input = Arrays.asList("1", "Alice", "d1", "f1", "5001", "1", "d1", "2000");
        List<String> output = runGiveBonusAndGetOutput(input);
        
        assertEquals("code=0", output.get(0));
        assertEquals("Alice d1 f1 5501", output.get(2));
    }

    @Test
    public void testBVA_TC3_SalaryUnder5000_ResultCrosses5000() throws Exception {
        List<String> input = Arrays.asList("1", "Alice", "d1", "f2", "4001", "1", "d1", "2000");
        List<String> output = runGiveBonusAndGetOutput(input);
        
        assertEquals("code=0", output.get(0));
        assertEquals("Alice d1 f2 5001", output.get(2));
    }

    @Test
    public void testBVA_TC4_ManagerSalaryHits5000() throws Exception {
        List<String> input = Arrays.asList("1", "Alice", "d1", "manager", "4500", "1", "d1", "2000");
        List<String> output = runGiveBonusAndGetOutput(input);
        
        assertEquals("code=0", output.get(0));
        assertEquals("Alice d1 manager 5000", output.get(2));
    }

    @Test
    public void testBVA_TC5_ManagerSalaryCrosses5000() throws Exception {
        List<String> input = Arrays.asList("1", "Alice", "d1", "manager", "4501", "1", "d1", "2000");
        List<String> output = runGiveBonusAndGetOutput(input);
        
        assertEquals("code=0", output.get(0));
        assertEquals("Alice d1 manager 5001", output.get(2));
    }

    @Test
    public void testBVA_TC6_ManagerSalaryExactly5000() throws Exception {
        List<String> input = Arrays.asList("1", "Alice", "d1", "manager", "5000", "1", "d1", "2000");
        List<String> output = runGiveBonusAndGetOutput(input);
        
        assertEquals("code=0", output.get(0));
        assertEquals("Alice d1 manager 5500", output.get(2));
    }

    @Test
    public void testBVA_TC7_ZeroEmployees() throws Exception {
        List<String> input = Arrays.asList("0", "1", "d1", "1000");
        List<String> output = runGiveBonusAndGetOutput(input);
        
        assertEquals("code=1", output.get(0));
    }

    @Test
    public void testBVA_TC8_OneEmployeeValid() throws Exception {
        List<String> input = Arrays.asList("1", "Alice", "d1", "f1", "2000", "1", "d1", "500");
        List<String> output = runGiveBonusAndGetOutput(input);
        
        assertEquals("code=0", output.get(0));
        assertEquals("Alice d1 f1 3000", output.get(2));
    }

    @Test
    public void testBVA_TC9_ZeroSales() throws Exception {
        List<String> input = Arrays.asList("1", "Alice", "d1", "f1", "3000", "0");
        List<String> output = runGiveBonusAndGetOutput(input);
        
        assertEquals("code=1", output.get(0));
        assertEquals("nE=1", output.get(1));
        assertEquals("Alice d1 f1 3000", output.get(2));
    }

    @Test
    public void testBVA_TC10_ZeroSalary() throws Exception {
        List<String> input = Arrays.asList("1", "Alice", "d1", "f1", "0", "1", "d1", "1000");
        List<String> output = runGiveBonusAndGetOutput(input);
        
        assertEquals("code=0", output.get(0));
        assertEquals("Alice d1 f1 1000", output.get(2));
    }

    @Test
    public void testBVA_TC11_NegativeSalary() throws Exception {
        List<String> input = Arrays.asList("1", "Alice", "d1", "f1", "-100", "1", "d1", "1000");
        List<String> output = runGiveBonusAndGetOutput(input);
        
        assertEquals("code=0", output.get(0));
        assertEquals("Alice d1 f1 900", output.get(2));
    }
}