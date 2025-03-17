package com.datavisualization;

import java.io.*;
import java.util.*;

public class CSVReader {
    public static List<DataItem> readCSV(String filePath) {
        List<DataItem> dataList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean headerSkipped = false;
            while ((line = br.readLine()) != null) {
                if (!headerSkipped) {
                    headerSkipped = true;
                    continue;
                }
                String[] values = line.split(",");

                if (values.length < 7 || values[5].trim().isEmpty() || values[6].trim().isEmpty()) {
                    System.out.println("Skipping invalid row: " + line);
                    continue;
                }

                try {
                    String siteName = values[0].trim();
                    String country = values[1].trim();
                    int year = Integer.parseInt(values[2].trim());
                    String category = values[3].trim();
                    String removalDate = values[4].trim();
                    double latitude = Double.parseDouble(values[5].trim());
                    double longitude = Double.parseDouble(values[6].trim());

                    dataList.add(new DataItem(siteName, country, year, category, removalDate, latitude, longitude));
                } catch (NumberFormatException e) {
                    System.out.println("Skipping row due to parsing error: " + line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dataList;
    }
}

