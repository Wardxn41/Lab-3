package com.datavisualization;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ChartPanel extends JPanel {
    private List<DataItem> dataList;

    public ChartPanel(List<DataItem> dataList) {
        this.dataList = dataList;
        setPreferredSize(new Dimension(400, 300));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (dataList == null || dataList.isEmpty()) return;

        Graphics2D g2d = (Graphics2D) g;
        Map<String, Long> categoryCount = dataList.stream()
                .collect(Collectors.groupingBy(DataItem::getCategory, Collectors.counting()));

        int panelWidth = getWidth();
        int panelHeight = getHeight();
        int barWidth = panelWidth / categoryCount.size();
        long maxCount = categoryCount.values().stream().max(Long::compare).orElse(1L); // Fix: Ensure Long type

        int scaleFactor = Math.max(1, (int) ((panelHeight - 100) / (double) maxCount)); // Fix: Prevent type mismatch

        int x = 10;
        for (Map.Entry<String, Long> entry : categoryCount.entrySet()) {
            int barHeight = Math.max(5, (int) (entry.getValue() * scaleFactor)); // Fix: Convert to int
            g2d.setColor(Color.BLUE);
            g2d.fillRect(x, panelHeight - barHeight - 50, barWidth - 10, barHeight);
            g2d.setColor(Color.BLACK);
            g2d.drawString(entry.getKey(), x, panelHeight - 5);
            x += barWidth;
        }
    }
}


