package com.company;


import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.*;

public class MyTableModel extends DefaultTableModel {
    java.util.List<Color> rowColours = Arrays.asList(Color.RED,Color.GREEN,Color.CYAN);

    public void setRowColour(int row, Color c) {
        rowColours.set(row, c);
        fireTableRowsUpdated(row, row);
    }

    public Color getRowColour(int row) {
        return rowColours.get(row);
    }

    @Override
    public int getRowCount() {
        return 3;
    }

    @Override
    public int getColumnCount() {
        return 0;
    }

    @Override
    public Object getValueAt(int row, int column) {
        return String.format("%d %d", row, column);
    }


}
