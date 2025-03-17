package com.datavisualization;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class MainFrame extends JFrame {
    private JTable table;
    private JTextArea detailsArea;
    private JLabel statsLabel;
    private ChartPanel chartPanel;
    private List<DataItem> dataList;

    public MainFrame(List<DataItem> dataList) {
        super("World Heritage Data Visualization Tool");
        this.dataList = dataList;

        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        table = new JTable(new DataTableModel(dataList));
        table.setAutoCreateRowSorter(true);
        add(new JScrollPane(table), BorderLayout.CENTER);

        detailsArea = new JTextArea(5, 30);
        detailsArea.setEditable(false);
        add(new JScrollPane(detailsArea), BorderLayout.SOUTH);

        statsLabel = new JLabel("Statistics will appear here");
        add(statsLabel, BorderLayout.NORTH);

        chartPanel = new ChartPanel(dataList);
        add(chartPanel, BorderLayout.EAST);
    }
}

