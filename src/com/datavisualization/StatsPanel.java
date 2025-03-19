package com.datavisualization;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class StatsPanel extends JPanel {
    private List<DataItem> dataList;
    private JComboBox<String> categoryFilter;
    private JComboBox<String> yearFilter;
    private JComboBox<String> countryFilter;
    private Runnable updateCallback;

    public StatsPanel(List<DataItem> dataList, Runnable updateCallback) {
        this.dataList = dataList;
        this.updateCallback = updateCallback;
        setPreferredSize(new Dimension(400, 300));
        setLayout(new BorderLayout());

        JPanel filterPanel = new JPanel(new FlowLayout());
        categoryFilter = new JComboBox<>();
        categoryFilter.addItem("All Categories");
        Set<String> categories = dataList.stream().map(DataItem::getCategory).collect(Collectors.toSet());
        for (String category : categories) {
            categoryFilter.addItem(category);
        }
        categoryFilter.addActionListener(e -> updateCallback.run());
        filterPanel.add(new JLabel("Category:"));
        filterPanel.add(categoryFilter);

        yearFilter = new JComboBox<>();
        yearFilter.addItem("All Years");
        Set<String> years = dataList.stream().map(d -> String.valueOf(d.getYear())).collect(Collectors.toSet());
        for (String year : years) {
            yearFilter.addItem(year);
        }
        yearFilter.addActionListener(e -> updateCallback.run());
        filterPanel.add(new JLabel("Year:"));
        filterPanel.add(yearFilter);

        countryFilter = new JComboBox<>();
        countryFilter.addItem("All Countries");
        Set<String> countries = dataList.stream().map(DataItem::getCountry).collect(Collectors.toSet());
        for (String country : countries) {
            countryFilter.addItem(country);
        }
        countryFilter.addActionListener(e -> updateCallback.run());
        filterPanel.add(new JLabel("Country:"));
        filterPanel.add(countryFilter);

        add(filterPanel, BorderLayout.NORTH);
    }

    public List<DataItem> getFilteredData() {
        String selectedCategory = (String) categoryFilter.getSelectedItem();
        String selectedYear = (String) yearFilter.getSelectedItem();
        String selectedCountry = (String) countryFilter.getSelectedItem();

        return dataList.stream()
                .filter(d -> selectedCategory.equals("All Categories") || d.getCategory().equals(selectedCategory))
                .filter(d -> selectedYear.equals("All Years") || String.valueOf(d.getYear()).equals(selectedYear))
                .filter(d -> selectedCountry.equals("All Countries") || d.getCountry().equals(selectedCountry))
                .collect(Collectors.toList());
    }
}
