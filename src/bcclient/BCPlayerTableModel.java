/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bcclient;

import bc.BCPlayer;
import javax.swing.table.AbstractTableModel;

/**
 * Класс "Модель таблицы игрока"
 * @author iMacAverage
 */
public class BCPlayerTableModel extends AbstractTableModel {
    
    /**
     * объект "Игрок"
     */
    private final BCPlayer bcPlayer;
    
    /**
     * время игрока (секунд)
     */
    private int time;

    /**
     * Создать объект
     * @param bcPlayer объект "Игрок"
     * @param time время игрока (секунд)
     */
    public BCPlayerTableModel(BCPlayer bcPlayer, int time) {
        super();
        this.bcPlayer = bcPlayer; 
        this.time = time;
    }
    
    /**
     * Задать время игрока (секунд)
     * @param time время игрока (секунд)
     */
    public void setTime(int time) {
        this.time = time;
        fireTableDataChanged();
    }
    
    @Override
    public int getRowCount() {
        return 1;
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
            case 2:
                columnName = "Time";
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
                return bcPlayer.getLogin();
            case 1:
                return bcPlayer.getRating();
            case 2:
                return this.viewTime(this.time);
        }
        return null;
    }

    /**
     * Преобразовать время (секунд) в строку для вывода на экран
     * @param time время (секунд)
     * @return строка для вывода на экран
     */
    private String viewTime(int time) {
        return String.format("%1d", time / 60) + ":" + String.format("%02d", time % 60);
    }
    
    @Override
    public Class getColumnClass(int col) {
        switch(col) {
            case 0:
                return String.class;
            case 1:
                return Integer.class;
            case 2:
                return String.class;
            default:
                return super.getColumnClass(col);    
        }
    }

}
