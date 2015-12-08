/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bcclient;

import bc.BCTable;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

/**
 * Класс "Модель таблицы столов"
 * @author iMacAverage
 */
public class BCTablesTableModel extends AbstractTableModel {

    /** 
     * коллекция объектов "Стол"
     */
    protected ArrayList<BCTable> bcTables;

    /**
     * Создать объект
     */
    public BCTablesTableModel() {
        super();
        this.bcTables = null; 
    }

    /**
     * Задать коллекцию объектов "Стол"
     * @param bcTables коллекция объектов "Стол"
     */
    public void setBCTables(ArrayList<BCTable> bcTables) {
        this.bcTables = bcTables;
        fireTableDataChanged();
    }

    @Override
    public int getRowCount() {
        if(bcTables == null)
            return 0;
        return bcTables.size();
    }

    @Override
    public String getColumnName(int column) {
        String columnName = null;
        switch(column) {
            case 0:
                columnName = "Number";
                break;
            case 1:
                columnName = "Player A";
                break;
            case 2:
                columnName = "Rating A";
                break;
            case 3:
                columnName = "Player B";
                break;
            case 4:
                columnName = "Rating B";
                break;
            case 5:
                columnName = "Min rat.";
                break;
            case 6:
                columnName = "Num.Length";
                break;
            case 7:
                columnName = "Time";
                break;
            case 8:
                columnName = "With rep.";
                break;
        }
        return columnName;
    }
    
    @Override
    public int getColumnCount() {
        return 9;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch(columnIndex) {
            case 0:
                return bcTables.get(rowIndex).getNumTable();
            case 1:
                return bcTables.get(rowIndex).getBCPlayerA().getLogin();
            case 2:
                return bcTables.get(rowIndex).getBCPlayerA().getRating();
            case 3:
                if(bcTables.get(rowIndex).getBCPlayerB() != null)
                    return bcTables.get(rowIndex).getBCPlayerB().getLogin();
                return "";
            case 4:
                if(bcTables.get(rowIndex).getBCPlayerB() != null)
                    return bcTables.get(rowIndex).getBCPlayerB().getRating();
                return "";
            case 5:
                return bcTables.get(rowIndex).getMinRating();
            case 6:
                return bcTables.get(rowIndex).getNumberLength();
            case 7:
                return bcTables.get(rowIndex).getCountMin();
            case 8:
                return bcTables.get(rowIndex).getWithRepeat();
        }
        return null;
    }
    
    @Override
    public Class getColumnClass(int col) {
        switch(col) {
            case 0:
                return Integer.class;
            case 2:
                return Integer.class;
            case 4:
                return Integer.class;
            case 5:
                return Integer.class;
            case 6:
                return Integer.class;
            case 7:
                return Integer.class;
            case 8:
                return Boolean.class;
            default:
                return super.getColumnClass(col);    
        }
    }
    
}
