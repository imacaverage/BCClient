/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bcclient;

import bc.BCTurn;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

/**
 * Класс "Модель таблицы ходов"
 * @author iMacAverage
 */
public class BCTurnsTableModel extends AbstractTableModel {
    
    /** 
     * коллекция объектов "Ход" 
     */
    private ArrayList<BCTurn> bcTurns;

    /**
     * Создать объект
     */
    public BCTurnsTableModel() {
        super();
        this.bcTurns = null; 
    }
    
    /**
     * Задать коллекцию объектов "Ход"
     * @param bcTurns коллекция объектов "Ход"
     */
    public void setBCTurns(ArrayList<BCTurn> bcTurns) {
        this.bcTurns = bcTurns;
        fireTableDataChanged();
    }
    
    @Override
    public int getRowCount() {
        if(bcTurns == null)
            return 0;
        return bcTurns.size();
    }

    @Override
    public String getColumnName(int column) {
        String columnName = null;
        switch(column) {
            case 0:
                columnName = "Number";
                break;
            case 1:
                columnName = "Bulls";
                break;
            case 2:
                columnName = "Cows";
                break;
        }
        return columnName;
    }
    
    @Override
    public int getColumnCount() {
        return 3;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch(columnIndex) {
            case 0:
                return bcTurns.get(rowIndex).getNumber();
            case 1:
                return bcTurns.get(rowIndex).getB();
            case 2:
                return bcTurns.get(rowIndex).getC();
        }
        return null;
    }

    @Override
    public Class getColumnClass(int col) {
        switch(col) {
            case 0:
                return String.class;
            case 1:
                return Integer.class;
            case 2:
                return Integer.class;
            default:
                return super.getColumnClass(col);    
        }
    }

}
