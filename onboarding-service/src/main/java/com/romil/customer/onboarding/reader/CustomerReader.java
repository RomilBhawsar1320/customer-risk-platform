package com.romil.customer.onboarding.reader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class CustomerReader {

    @Autowired
    private CsvReaderService csvReaderService;

    public List<Map<String, String>> read(String filePath) {

        return csvReaderService.read(filePath);
    }
}