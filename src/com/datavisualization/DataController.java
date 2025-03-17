package com.datavisualization;

import java.util.List;

public class DataController {
    private List<DataItem> dataList;

    public DataController(List<DataItem> dataList) {
        this.dataList = dataList;
    }

    public List<DataItem> getData() {
        return dataList;
    }
}

