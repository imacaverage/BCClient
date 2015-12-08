/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bcclient;

import bc.BCTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * Класс "Слушатель выделений строк в таблице"
 * слушает таблицу столов
 * @author iMacAverage
 */
public class BCTablesSelectionListener implements ListSelectionListener {

    /**
     * объект "Представление объекта 'Клиент'"
     */
    private final BCClientView bcClientView;

    /**
     * объект "Модель объекта 'Клиент'"
     */
    private final BCClientModel bcClientModel;
    
    /**
     * Создать объект
     * @param bcClientView объект "Представление объекта 'Клиент'"
     * @param bcClientModel объект "Модель объекта 'Клиент'"
     */
    public BCTablesSelectionListener(BCClientView bcClientView, BCClientModel bcClientModel) {
        this.bcClientView = bcClientView;
        this.bcClientModel = bcClientModel;
    }
    
    @Override
    public void valueChanged(ListSelectionEvent e) {
        ListSelectionModel lsm = (ListSelectionModel) e.getSource();
        if(this.bcClientView.getSelectedTable() == -1) {
            this.bcClientModel.setSitTableEnabled(false);
            return;
        }
        int numTable = this.bcClientModel.getBCTables().get(this.bcClientView.getSelectedTable()).getNumTable();
        for(BCTable bcTable : this.bcClientModel.getBCTables()) {
            if(bcTable.getNumTable() == numTable) {
                if(bcTable.checkBanned(this.bcClientModel.getPlayer()) || bcTable.getMinRating() > this.bcClientModel.getPlayer().getRating()) {
                    lsm.clearSelection();
                    return;
                }
            }
        }
        this.bcClientModel.setIndexSelectedTable(this.bcClientView.getSelectedTable());
        this.bcClientModel.setSitTableEnabled(true);
    }
    
}
