package com.romil.customer.onboarding.reader;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

@Service
public class CsvReaderService {

    public List<Map<String, String>> read(String filePath) {

        List<Map<String, String>> records = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {

            String headerLine = br.readLine();

            if (headerLine == null) {
                return records;
            }

            String[] headers = headerLine.split(",");

            String line;

            while ((line = br.readLine()) != null) {

                String[] values = line.split(",");

                Map<String, String> row = new HashMap<>();

                for (int i = 0; i < headers.length; i++) {

                    row.put(
                            headers[i].trim(),
                            i < values.length ? values[i].trim() : ""
                    );
                }

                records.add(row);
            }

        } catch (Exception e) {
            throw new RuntimeException(
                    "Error reading file : " + filePath,
                    e
            );
        }

        return records;
    }
}