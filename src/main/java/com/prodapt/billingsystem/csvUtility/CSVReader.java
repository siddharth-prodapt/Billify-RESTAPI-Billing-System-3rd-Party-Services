package com.prodapt.billingsystem.csvUtility;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.Reader;

public class CSVReader {
    public static void readCSVFile() {
        System.out.println("Read CSV File");
        try {
            Reader in = new FileReader("C:\\Users\\siddharth.sp\\Downloads\\Book.csv");
            Iterable<CSVRecord> records = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(in);
            for (CSVRecord record : records) {
                System.out.println("Record: "+record);

                String lastName = record.get("Last_Name");
                String firstName = record.get("First_Name");

                System.out.println("FirstName: "+firstName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
