package com.datavisualization;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ChartPanel extends JPanel {
    private List<DataItem> dataList;
    private StatsPanel statsPanel;

    public ChartPanel(List<DataItem> dataList) {
        this.dataList = dataList;
        this.statsPanel = statsPanel;
        setPreferredSize(new Dimension(800, 1200));
    }

    public void updateChart() {
        if (statsPanel != null) {
            this.dataList = statsPanel.getFilteredData();
            repaint();
        }
    }

    @Override

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (dataList == null || dataList.isEmpty()) return;

        Graphics2D g2d = (Graphics2D) g;
        int panelWidth = getWidth();
        int panelHeight = getHeight();
        int barWidth = Math.max(40, panelWidth / (dataList.size() + 1)); // Dynamic width
        int x = 50;

        // Find max value dynamically
        int maxValue = dataList.stream()
                .mapToInt(DataItem::getValue) // Assuming getValue() returns the numeric data
                .max()
                .orElse(1); // Avoid division by zero

        // Draw axes
        g2d.setColor(Color.BLACK);
        g2d.drawLine(40, 50, 40, panelHeight - 50);
        g2d.drawLine(40, panelHeight - 50, panelWidth - 10, panelHeight - 50);

        // Draw bars
        for (DataItem item : dataList) {
            int value = 100; // Get the numerical value
            int barHeight = (int) ((double) value / maxValue * (panelHeight - 100)); // Scale height

            g2d.setColor(Color.BLUE);
            g2d.fillRect(x, panelHeight - barHeight - 50, barWidth, barHeight);

            g2d.setColor(Color.BLACK);
            g2d.drawString(String.valueOf(item.getYear()), x, panelHeight - 30);

            x += barWidth + 10;
        }
    }

}



