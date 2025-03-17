package com.datavisualization;
import javax.swing.table.AbstractTableModel;
import javax.swing.*;
import javax.swing.table.TableModel;
import java.io.*;
import java.util.*;

public class DataVisualizationTool {
    public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {
        String filePath = "Images/All_sites.csv"; // Adjust the path
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), "UTF-8"));
        List<DataItem> dataList = CSVReader.readCSV(filePath);

        if (dataList.isEmpty()) {
            System.out.println("No data loaded! Check the CSV file.");
            return;
        }

        System.out.println("Total Entries: " + dataList.size());
        System.out.println("First Entry: " + dataList.get(0));
        System.out.println("Tenth Entry: " + (dataList.size() >= 10 ? dataList.get(9) : "Not enough entries"));

        SwingUtilities.invokeLater(() -> new MainFrame(dataList).setVisible(true));
    }
}

// Model
class DataItem {
    private String siteName;
    private String country;
    private int year;
    private String category;
    private String removalDate;
    private double latitude;
    private double longitude;

    public DataItem(String siteName, String country, int year, String category, String removalDate, double latitude, double longitude) {
        this.siteName = siteName;
        this.country = country;
        this.year = year;
        this.category = category;
        this.removalDate = removalDate;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getSiteName() { return siteName; }
    public String getCountry() { return country; }
    public int getYear() { return year; }
    public String getCategory() { return category; }
    public String getRemovalDate() { return removalDate; }
    public double getLatitude() { return latitude; }
    public double getLongitude() { return longitude; }

    @Override
    public String toString() {
        return siteName + " | " + country + " | " + year + " | " + category +
                " | Inscription: " + removalDate +
                " | Lat: " + latitude + " | Lon: " + longitude;
    }
}

// Controller
class DataController {
    private List<DataItem> dataList;

    public DataController(List<DataItem> dataList) {
        this.dataList = dataList;
    }

    public List<DataItem> getData() {
        return dataList;
    }
}

// CSV Reader
class CSVReader {
    public static List<DataItem> readCSV(String filePath) {
        List<DataItem> dataList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean headerSkipped = false;
            while ((line = br.readLine()) != null) {
                if (!headerSkipped) { // Skip header row
                    headerSkipped = true;
                    continue;
                }
                String[] values = line.split(","); // Adjust if delimiter is different

                // Ensure all required columns exist
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

// View
class MainFrame extends JFrame {
    public MainFrame(List<DataItem> dataList) {
        super("World Heritage Data Visualization Tool"); // 🆕 Set the window title here

        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JTable table = new JTable((TableModel) new DataTableModel(dataList));
        add(new JScrollPane(table));
    }
}

// Table Model for JTable
class DataTableModel extends AbstractTableModel {
    private final List<DataItem> dataList;
    private final String[] columnNames = {"Site Name", "Country", "Inscription Date", "In Danger?", "Removal Date", "Latitude", "Longitude"};

    public DataTableModel(List<DataItem> dataList) {
        this.dataList = dataList;
    }

    @Override
    public int getRowCount() {
        return dataList.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        DataItem dataItem = dataList.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> dataItem.getSiteName();
            case 1 -> dataItem.getCountry();
            case 2 -> dataItem.getYear();
            case 3 -> dataItem.getCategory();
            case 4 -> dataItem.getRemovalDate();
            case 5 -> dataItem.getLatitude();
            case 6 -> dataItem.getLongitude();
            default -> null;
        };
    }
}

