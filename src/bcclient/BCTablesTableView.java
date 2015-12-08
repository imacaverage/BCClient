/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bcclient;

import bc.BCPlayer;
import bc.BCTable;
import java.awt.Component;
import java.util.ArrayList;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

/**
 * "Представление таблицы объектов 'Стол'"
 * @author iMacAverage
 */
public class BCTablesTableView extends JTable {

    /**
     * объект "Модель объекта 'Клиент'"
     */
    private final BCClientModel bcClientModel;
    
    /**
     * Создать объект
     * @param bcClientModel объект "Модель объекта 'Клиент'"
     */
    public BCTablesTableView(BCClientModel bcClientModel) {
        super();
        this.bcClientModel = bcClientModel;
    }

    @Override
    public Component prepareRenderer(TableCellRenderer renderer, int row, int col) {
        java.awt.Component comp = super.prepareRenderer(renderer, row, col);
        ArrayList<BCTable> bcTables = this.bcClientModel.getBCTables();
        if(bcTables == null)
            return comp;
        BCPlayer bcPlayer = this.bcClientModel.getPlayer();
        comp.setEnabled(true);
        int numTable = (int) this.getModel().getValueAt(row, 0);
        for(BCTable bcTable : bcTables) {
            if(bcTable.getNumTable() == numTable) {
                if(bcTable.checkBanned(bcPlayer) || bcTable.getMinRating() > bcPlayer.getRating())
                    comp.setEnabled(false);
                return comp;
            }
        }
        return comp;
    }    
    
}
