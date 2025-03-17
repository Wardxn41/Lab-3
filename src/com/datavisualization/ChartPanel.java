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
        this.dataList = statsPanel.getFilteredData();
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (dataList == null || dataList.isEmpty()) return;

        Graphics2D g2d = (Graphics2D) g;
        int panelWidth = getWidth();
        int panelHeight = getHeight();
        int barWidth = 40;
        int x = 50;

        g2d.setColor(Color.BLACK);
        g2d.drawLine(40, 50, 40, panelHeight - 50);
        g2d.drawLine(40, panelHeight - 50, panelWidth - 10, panelHeight - 50);

        for (DataItem item : dataList) {
            int barHeight = 100; // Placeholder height, should be calculated dynamically
            g2d.setColor(Color.BLUE);
            g2d.fillRect(x, panelHeight - barHeight - 50, barWidth, barHeight);
            g2d.setColor(Color.BLACK);
            g2d.drawString(String.valueOf(item.getYear()), x, panelHeight - 30);
            x += barWidth + 10;
        }
    }
}


