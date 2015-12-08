/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bcclient;

import bc.BCTable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JTable;

/**
 * Класс "Контроллер объекта 'Клиент'"
 * слушатель объекта "Представление объекта 'Клиент'"
 * @author iMacAverage
 */
public class BCClientController implements ActionListener, MouseListener {
    
    /**
     * объект "Модель объекта 'Клиент'"
     */
    BCClientModel bcClientModel;
    
    /**
     * Создать объект
     * @param bcClientModel объект "Модель объекта 'Клиент'"
     */
    public BCClientController(BCClientModel bcClientModel) {
        this.bcClientModel = bcClientModel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch(e.getActionCommand()) {
            case "New Table":
                this.bcClientModel.newTable();
                break;
            case "Sit Table":
                this.bcClientModel.sitTable();
                break;
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        JTable table = (JTable) e.getSource();
        int row = table.rowAtPoint(e.getPoint());
        if(e.getClickCount() == 2) {
            int numTable = this.bcClientModel.getBCTables().get(row).getNumTable();
            for(BCTable bcTable : this.bcClientModel.getBCTables()) {
                if(bcTable.getNumTable() == numTable) {
                    if(bcTable.checkBanned(this.bcClientModel.getPlayer()) || bcTable.getMinRating() > this.bcClientModel.getPlayer().getRating()) {
                        return;
                    }
                }
            }
            this.bcClientModel.sitTable();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
    
}
