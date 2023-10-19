package com.prodapt.billingsystem.csvUtility;

import com.prodapt.billingsystem.csvUtility.services.CSVReaderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
public class CSVController {

    @Autowired
    private CSVReaderService csvService;
    @GetMapping("/api/v1/admin/csv-email")
    public String csvReader(){
        log.info("CSV Reader controller");

        csvService.readCSVFileAndSendEmail();
        return "It runs successfully";
    }


//    Give invoice details of CSV file
    @GetMapping("/api/v1/admin/csv")
    public ResponseEntity<List<AdminInvoiceList>> getCsvInvoiceDetails(){
        return new ResponseEntity<>(csvService.getDataFromCSV(), HttpStatus.OK);
    }


}
