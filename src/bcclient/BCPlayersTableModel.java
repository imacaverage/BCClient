/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bcclient;

import bc.BCPlayer;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

/**
 * Класс "Модель таблицы игроков"
 * @author iMacAverage
 */
public class BCPlayersTableModel extends AbstractTableModel {
    
    /** 
     * коллекция объектов "Игрок" 
     */
    protected ArrayList<BCPlayer> bcPlayers;

    /**
     * Создать объект
     */
    public BCPlayersTableModel() {
        super();
        this.bcPlayers = null; 
    }
    /**
     * Задать коллекцию объектов "Игрок"
     * @param bcPlayers коллекция объектов "Игрок"
     */
    public void setBCPlayers(ArrayList<BCPlayer> bcPlayers) {
        this.bcPlayers = bcPlayers;
        fireTableDataChanged();
    }
    
    @Override
    public int getRowCount() {
        if(bcPlayers == null)
            return 0;
        return bcPlayers.size();
    }

    @Override
    public String getColumnName(int column) {
        String columnName = null;
        switch(column) {
            case 0:
                columnName = "Player";
                break;
            case 1:
                columnName = "Rating";
                break;
        }
        return columnName;
    }
    
    @Override
    public int getColumnCount() {
        return 2;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch(columnIndex) {
            case 0:
                return bcPlayers.get(rowIndex).getLogin();
            case 1:
                return bcPlayers.get(rowIndex).getRating();
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
            default:
                return super.getColumnClass(col);    
        }
    }

}
