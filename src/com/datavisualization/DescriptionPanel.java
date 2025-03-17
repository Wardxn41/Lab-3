package com.datavisualization;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DescriptionPanel extends JPanel {
    private JTextArea descriptionArea;

    public DescriptionPanel() {
        setLayout(new BorderLayout());
        descriptionArea = new JTextArea(8, 30);
        descriptionArea.setEditable(false);
        add(new JScrollPane(descriptionArea), BorderLayout.CENTER);
    }

    public void updateDescription(DataItem item) {
        descriptionArea.setText("Site Name: " + item.getSiteName() +
                "\nCountry: " + item.getCountry() +
                "\nYear: " + item.getYear() +
                "\nCategory: " + item.getCategory() +
                "\nRemoval Date: " + item.getRemovalDate() +
                "\nLatitude: " + item.getLatitude() +
                "\nLongitude: " + item.getLongitude());
    }
}
