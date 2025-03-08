import java.util.List;

public class DataVisualizationRunnable {
    public static void main(String[] args) {
        String filePath = "src/resources/world_heritage.csv";  // Adjust the path
        List<DataItem> dataList = CSVReader.readCSV(filePath);

        System.out.println("Total Entries: " + dataList.size());
        System.out.println("First Entry: " + dataList.get(0));
        System.out.println("Tenth Entry: " + (dataList.size() >= 10 ? dataList.get(9) : "Not enough entries"));
    }
}

