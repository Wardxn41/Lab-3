package com.datavisualization;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class DataTableModel extends AbstractTableModel {
    private List<DataItem> dataList;
    private final String[] columnNames = {"Site Name", "Country", "Inscription Date", "In Danger?", "Removal Date", "Latitude", "Longitude"};

    public DataTableModel(List<DataItem> dataList) {
        this.dataList = dataList;
    }

    public void setData(List<DataItem> newData) {
        this.dataList = newData;
        fireTableDataChanged(); // Notify table that data changed
    }

    @Override
    public int getRowCount() {
        return dataList.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        DataItem dataItem = dataList.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> dataItem.getSiteName();
            case 1 -> dataItem.getCountry();
            case 2 -> dataItem.getYear();
            case 3 -> dataItem.getCategory();
            case 4 -> dataItem.getRemovalDate();
            case 5 -> dataItem.getLatitude();
            case 6 -> dataItem.getLongitude();
            default -> null;
        };
    }
}


