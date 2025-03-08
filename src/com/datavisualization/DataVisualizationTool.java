package com.datavisualization;

import javax.swing.*;
import java.util.List;

public class DataVisualizationTool {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainFrame().setVisible(true));
    }
}

// Model
class DataItem {
    private String name;
    private double value;

    public DataItem(String name, double value) {
        this.name = name;
        this.value = value;
    }

    public String getName() { return name; }
    public double getValue() { return value; }
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

// View
class MainFrame extends JFrame {
    public MainFrame() {
        setTitle("Data Visualization Tool");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JTable table = new JTable();
        add(new JScrollPane(table));
    }
}

