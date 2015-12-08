/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bcclient;

import bc.BCPlayer;
import java.awt.Component;
import java.awt.Font;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

/**
 * Класс "Представление таблицы объектов 'Игрок'"
 * @author iMacAverage
 */
public class BCPlayersTableView extends JTable {

    /**
     * объект "Модель объекта 'Клиент'"
     */
    private final BCClientModel bcClientModel;
    
    /**
     * Создать объект
     * @param bcClientModel объект "Модель объекта 'Клиент'"
     */
    public BCPlayersTableView(BCClientModel bcClientModel) {
        super();
        this.bcClientModel = bcClientModel;
    }

    @Override
    public Component prepareRenderer(TableCellRenderer renderer, int row, int col) {
        java.awt.Component comp = super.prepareRenderer(renderer, row, col);
        BCPlayer bcPlayer = this.bcClientModel.getPlayer();
        comp.setFont(this.getFont().deriveFont(Font.PLAIN));
        String login = (String) this.getModel().getValueAt(row, 0);
        if(bcPlayer.getLogin().equals(login))
            comp.setFont(this.getFont().deriveFont(Font.BOLD));
        return comp;
    }    
    
}
