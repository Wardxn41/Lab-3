package com.datavisualization;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.ArrayList;

public class MainFrame extends JFrame {
    private List<DataItem> originalData;  // Stores the full dataset
    private List<DataItem> filteredData;  // Stores the currently filtered dataset

    private JTable table;
    private TablePanel tablePanel;
    private ChartPanel chartPanel;
    private DescriptionPanel descriptionPanel;

    private JComboBox<String> countryFilter;
    private JComboBox<String> categoryFilter;
    private JComboBox<String> yearFilter;

    public MainFrame(List<DataItem> dataList) {
        super("World Heritage Data Visualization Tool");

        this.originalData = dataList;
        this.filteredData = new ArrayList<>(dataList);

        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Top Panel (Filters)
        JPanel filterPanel = new JPanel(new FlowLayout());
        countryFilter = createFilterBox("Country", dataList.stream().map(DataItem::getCountry).collect(Collectors.toSet()));
        categoryFilter = createFilterBox("Category", dataList.stream().map(DataItem::getCategory).collect(Collectors.toSet()));
        yearFilter = createFilterBox("Year", dataList.stream().map(d -> String.valueOf(d.getYear())).collect(Collectors.toSet()));

        filterPanel.add(new JLabel("Filter by Country:"));
        filterPanel.add(countryFilter);
        filterPanel.add(new JLabel("Filter by Category:"));
        filterPanel.add(categoryFilter);
        filterPanel.add(new JLabel("Filter by Year:"));
        filterPanel.add(yearFilter);
        add(filterPanel, BorderLayout.NORTH);

        // Table Panel (Left Side)
        tablePanel = new TablePanel(filteredData);
        JScrollPane tableScrollPane = new JScrollPane(tablePanel);

        // Description Panel (Bottom Left)
        descriptionPanel = new DescriptionPanel();

        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.add(tableScrollPane, BorderLayout.CENTER);
        leftPanel.add(descriptionPanel, BorderLayout.SOUTH); // Place description below table

        // Right Side - Chart Panel
        chartPanel = new ChartPanel(filteredData);
        JPanel chartContainer = new JPanel(new BorderLayout());
        chartContainer.setPreferredSize(new Dimension(400, getHeight()));
        chartContainer.add(chartPanel, BorderLayout.CENTER);

        // Create Main Center Panel (Table & Description on Left, Chart on Right)
        JPanel mainCenterPanel = new JPanel(new BorderLayout());
        mainCenterPanel.add(leftPanel, BorderLayout.CENTER);
        mainCenterPanel.add(chartContainer, BorderLayout.EAST);

        // Add Panels to Frame
        add(mainCenterPanel, BorderLayout.CENTER);

        // Add Table Selection Listener for Description Updates
        addTableSelectionListener();
    }


    private void addTableSelectionListener() {
        tablePanel.getTable().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRow = tablePanel.getTable().getSelectedRow();
                if (selectedRow != -1 && selectedRow < filteredData.size()) {
                    DataItem selectedItem = filteredData.get(selectedRow);
                    descriptionPanel.updateDescription(selectedItem);
                }
            }
        });
    }

    private void updateTable() {
        if (originalData == null || originalData.isEmpty()) {
            System.out.println("ERROR: originalData is EMPTY.");
            return;
        }

        System.out.println("Original Data Size: " + originalData.size());

        // Get selected filter values
        final String selectedCountry = (String) countryFilter.getSelectedItem();
        final String selectedCategory = (String) categoryFilter.getSelectedItem();
        final String selectedYear = (String) yearFilter.getSelectedItem();

        // Ensure selections are not null
        final String finalSelectedCountry = (selectedCountry == null || selectedCountry.isEmpty()) ? "All Countries" : selectedCountry;
        final String finalSelectedCategory = (selectedCategory == null || selectedCategory.isEmpty()) ? "All Categories" : selectedCategory;
        final String finalSelectedYear = (selectedYear == null || selectedYear.isEmpty()) ? "All Years" : selectedYear;

        System.out.println("Applying Filters:");
        System.out.println("Country: " + finalSelectedCountry);
        System.out.println("Category: " + finalSelectedCategory);
        System.out.println("Year: " + finalSelectedYear);

        // Convert Year to Integer for Proper Comparison
        final int yearFilterInt;
        if (!finalSelectedYear.equals("All Years")) {
            try {
                yearFilterInt = Integer.parseInt(finalSelectedYear);
            } catch (NumberFormatException e) {
                System.out.println("WARNING: Invalid year format in filter: " + finalSelectedYear);
                return; // Exit early if year is invalid
            }
        } else {
            yearFilterInt = -1; // Special value to indicate "All Years"
        }

        // Apply Filtering & Print Debugging
        filteredData = originalData.stream()
                .peek(d -> System.out.println("Checking - " + d.getCountry() + ", " + d.getCategory() + ", " + d.getYear()))
                .filter(d -> finalSelectedCountry.equals("All Countries") ||
                        (d.getCountry() != null && d.getCountry().trim().equalsIgnoreCase(finalSelectedCountry)))
                .peek(d -> System.out.println("✔ Country Passed: " + d.getCountry()))
                .filter(d -> finalSelectedCategory.equals("All Categories") ||
                        (d.getCategory() != null && d.getCategory().trim().equalsIgnoreCase(finalSelectedCategory)))
                .peek(d -> System.out.println("✔ Category Passed: " + d.getCategory()))
                .filter(d -> finalSelectedYear.equals("All Years") ||
                        (d.getYear() == yearFilterInt))  // Compare Integer Year
                .peek(d -> System.out.println("✔ Year Passed: " + d.getYear()))
                .collect(Collectors.toList());

        System.out.println("Filtered Data Size: " + filteredData.size());

        // Restore all data if filtering removes everything
        if (filteredData.isEmpty()) {
            System.out.println("No results found. Resetting to full dataset.");
            filteredData = new ArrayList<>(originalData);
        }

        // Update UI
        if (tablePanel != null) {
            tablePanel.updateTable(filteredData);
        }
        if (chartPanel != null) {
            chartPanel.repaint();
        }
    }

    private JComboBox<String> createFilterBox(String label, Set<String> options) {
        JComboBox<String> filterBox = new JComboBox<>();
        filterBox.addItem("All " + label + "s"); // Default option
        options.forEach(filterBox::addItem); // Populate with available data

        // Ensure updateTable() is only called when UI is ready
        filterBox.addActionListener(e -> {
            System.out.println("Filter changed: " + label + " -> " + filterBox.getSelectedItem());
            updateTable(); // Call updateTable whenever a filter is changed
        });

        return filterBox;
    }
}
