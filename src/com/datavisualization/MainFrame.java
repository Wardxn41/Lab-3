
package com.datavisualization;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class MainFrame extends JFrame {
    private JTable table;
    private DataTableModel tableModel;
    private JTextArea detailsArea;
    private StatsPanel statsPanel;
    private ChartPanel chartPanel;
    private TablePanel tablePanel;

    private JComboBox<String> countryFilter;
    private JComboBox<String> categoryFilter;
    private JComboBox<String> yearFilter;

    private List<DataItem> originalData;
    private List<DataItem> filteredData;

    public MainFrame(List<DataItem> dataList) {
        super("World Heritage Data Visualization Tool");
        this.originalData = dataList;
        this.filteredData = dataList;

        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Top Panel (Filters)
        JPanel filterPanel = new JPanel();
        filterPanel.setLayout(new FlowLayout());

        // Filter by Country
        countryFilter = new JComboBox<>();
        countryFilter.addItem("All Countries");
        Set<String> countries = dataList.stream().map(DataItem::getCountry).collect(Collectors.toSet());
        for (String country : countries) {
            countryFilter.addItem(country);
        }
        countryFilter.addActionListener(e -> updateTable());
        filterPanel.add(new JLabel("Filter by Country:"));
        filterPanel.add(countryFilter);

        // Filter by Category
        categoryFilter = new JComboBox<>();
        categoryFilter.addItem("All Categories");
        Set<String> categories = dataList.stream().map(DataItem::getCategory).collect(Collectors.toSet());
        for (String category : categories) {
            categoryFilter.addItem(category);
        }
        categoryFilter.addActionListener(e -> updateTable());
        filterPanel.add(new JLabel("Filter by Category:"));
        filterPanel.add(categoryFilter);

        // Filter by Year
        yearFilter = new JComboBox<>();
        yearFilter.addItem("All Years");
        Set<String> years = dataList.stream().map(d -> String.valueOf(d.getYear())).collect(Collectors.toSet());
        for (String year : years) {
            yearFilter.addItem(year);
        }
        yearFilter.addActionListener(e -> updateTable());
        filterPanel.add(new JLabel("Filter by Year:"));
        filterPanel.add(yearFilter);

        add(filterPanel, BorderLayout.NORTH);

        // Create main container panel
        JPanel mainPanel = new JPanel(new GridLayout(2, 3)); // Divides into 3 equal panels

        // Table Panel
        tablePanel = new TablePanel(filteredData);
        mainPanel.add(tablePanel);

        // Stats Panel
        statsPanel = new StatsPanel(filteredData);
        mainPanel.add(statsPanel);

        // Chart Panel
        chartPanel = new ChartPanel(filteredData);
        mainPanel.add(chartPanel);

        add(mainPanel, BorderLayout.CENTER);
    }

    private void updateTable() {
        String selectedCountry = (String) countryFilter.getSelectedItem();
        String selectedCategory = (String) categoryFilter.getSelectedItem();
        String selectedYear = (String) yearFilter.getSelectedItem();

        filteredData = originalData.stream()
                .filter(d -> selectedCountry.equals("All Countries") || d.getCountry().equals(selectedCountry))
                .filter(d -> selectedCategory.equals("All Categories") || d.getCategory().equals(selectedCategory))
                .filter(d -> selectedYear.equals("All Years") || String.valueOf(d.getYear()).equals(selectedYear))
                .collect(Collectors.toList());

        // Update the table model with filtered data
        tablePanel.updateTable(filteredData);
        statsPanel.repaint();
        chartPanel.repaint();
    }
}

