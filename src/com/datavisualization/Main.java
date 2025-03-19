package com.datavisualization;

import com.datavisualization.*;

import javax.swing.*;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {
        String filePath = "Images/All_sites.csv"; // Adjust path as needed
        List<DataItem> dataList = CSVReader.readCSV(filePath);

        if (dataList.isEmpty()) {
            System.out.println("No data loaded! Check the CSV file.");
            return;
        }

        System.out.println("Total Entries: " + dataList.size());
        System.out.println("First Entry: " + dataList.get(0));
        System.out.println("Tenth Entry: " + (dataList.size() >= 10 ? dataList.get(9) : "Not enough entries"));


        System.out.println("Loaded Data Size: " + dataList.size()); // Debugging
        SwingUtilities.invokeLater(() -> new MainFrame(dataList));
        SwingUtilities.invokeLater(() -> new MainFrame(dataList).setVisible(true));
    }
}
