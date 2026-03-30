package com.grapes;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class GiveBonus {

    // --- Data Models ---
    static class Data {
        int nA;
        int nV;
        Angajat[] sA;
        Vanzare[] sV;
    }

    static class Angajat {
        String name;
        String department;
        String function;
        int salary;
    }

    static class Vanzare {
        String department;
        int saleValue;
    }

    private static Data dataRead(String filename) throws IOException {
        Data data = new Data();
        
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            
            String line = reader.readLine();
            data.nA = (line == null) ? 0 : Integer.parseInt(line.trim());
            data.sA = new Angajat[Math.max(data.nA, 10)]; 

            for (int i = 0; i < data.nA; i++) {
                Angajat emp = new Angajat();
                emp.name = reader.readLine();
                emp.department = reader.readLine();
                emp.function = reader.readLine();
                emp.salary = Integer.parseInt(reader.readLine().trim());
                data.sA[i] = emp;
            }

            line = reader.readLine();
            data.nV = (line == null) ? 0 : Integer.parseInt(line.trim());
            data.sV = new Vanzare[Math.max(data.nV, 10)];

            for (int i = 0; i < data.nV; i++) {
                Vanzare sale = new Vanzare();
                sale.department = reader.readLine();
                sale.saleValue = Integer.parseInt(reader.readLine().trim());
                data.sV[i] = sale;
            }
        }
        return data;
    }

    private static void afisareDate(String filename, int resultCode, int numEmployees, Angajat[] employees) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write("code=" + resultCode);
            writer.newLine();
            
            if (numEmployees > 0) {
                writer.write("nE=" + numEmployees);
                writer.newLine();
                for (int i = 0; i < numEmployees; i++) {
                    Angajat emp = employees[i];
                    writer.write(emp.name + " " + emp.department + " " + emp.function + " " + emp.salary);
                    writer.newLine();
                }
            }
        }
    }
    private static int giveBonus(int numEmployees, Angajat[] employees, int numSales, Vanzare[] sales) {
        if (numEmployees == 0 || numSales == 0) {
            return 1;
        }
        int maxSale = Integer.MIN_VALUE;
        String topDept = null;

        for (int i = 0; i < numSales; i++) {
            if (sales[i].saleValue > maxSale) {
                maxSale = sales[i].saleValue;
                topDept = sales[i].department;
            }
        }

        boolean foundEmployeeInTopDept = false;
        for (int i = 0; i < numEmployees; i++) {
            if (employees[i].department.equals(topDept)) {
                foundEmployeeInTopDept = true;
                break;
            }
        }

        if (!foundEmployeeInTopDept) {
            return 2;
        }

        for (int i = 0; i < numEmployees; i++) {
            if (employees[i].department.equals(topDept)) {
                if (employees[i].salary > 5000 || "manager".equalsIgnoreCase(employees[i].function)) {
                    employees[i].salary += 500;
                } else {
                    employees[i].salary += 1000;
                }
            }
        }

        return 0;
    }

    public static void main(String[] args) throws Exception {
        Data data = dataRead("IN.TXT");
        int resultCode = giveBonus(data.nA, data.sA, data.nV, data.sV);
        afisareDate("OUT.TXT", resultCode, data.nA, data.sA);
        System.out.println("GiveBonus ... done!");
    }
}