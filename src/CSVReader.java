import java.io.*;
import java.util.*;

public class CSVReader {
    public static List<DataItem> readCSV(String filePath) {
        List<DataItem> dataList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean headerSkipped = false;
            while ((line = br.readLine()) != null) {
                if (!headerSkipped) {  // Skip header row
                    headerSkipped = true;
                    continue;
                }
                String[] values = line.split(",");  // Adjust if the delimiter is different

                // Adjust indexes based on actual CSV structure
                String siteName = values[0].trim();
                String country = values[1].trim();
                int year = Integer.parseInt(values[2].trim());
                String category = values[3].trim();

                dataList.add(new DataItem(siteName, country, year, category));
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
        return dataList;
    }
}

