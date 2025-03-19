package com.datavisualization;

import javax.swing.*;
import java.awt.*;
import java.util.List;
public class TablePanel extends JPanel {
    private JTable table;
    private DataTableModel tableModel;

    public TablePanel(List<DataItem> dataList) {
        setLayout(new BorderLayout());
        tableModel = new DataTableModel(dataList);
        table = new JTable(tableModel);
        table.setAutoCreateRowSorter(true);
        add(new JScrollPane(table), BorderLayout.CENTER);
    }

    public void updateTable(List<DataItem> newData) {
        tableModel.setData(newData);
        tableModel.fireTableDataChanged();
    }

    public void highlightRow(DataItem item) {
        for (int i = 0; i < table.getRowCount(); i++) {
            if (table.getValueAt(i, 0).equals(item.getSiteName())) {
                table.setRowSelectionInterval(i, i);
                break;
            }
        }
    }


    // Get the JTable for selection listener integration
    public JTable getTable() {
        return table;
    }
}

