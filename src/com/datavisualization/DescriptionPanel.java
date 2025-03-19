package com.datavisualization;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class DescriptionPanel extends JPanel {
    private JTextArea descriptionArea;

    public DescriptionPanel() {
        setLayout(new BorderLayout());
        descriptionArea = new JTextArea(8, 30);
        descriptionArea.setEditable(false);
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
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

