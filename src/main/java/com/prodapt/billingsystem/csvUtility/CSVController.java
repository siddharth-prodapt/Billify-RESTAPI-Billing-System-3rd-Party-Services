package com.prodapt.billingsystem.csvUtility;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CSVController {
    @GetMapping("/api/v1/user/csv")
    public String csvReader(){
        System.out.println("Hit endpoitn");
        CSVReader.readCSVFile();
        return "It runs successfully";
    }
}
