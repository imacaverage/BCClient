/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bcclient;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

/**
 * Класс "Представление объекта 'Клиент'"
 * наблюдатель объекта "Модель объекта 'Клиент'"
 * @author iMacAverage
 */
public class BCClientView extends JPanel implements Observer {
    
    /**
     * объект "Модель объекта 'Клиент'"
     */
    private final BCClientModel bcClientModel;
    
    /**
     * объект "Модель таблицы игроков"
     */
    private final BCPlayersTableModel bcPlayersTableModel;

    /**
     * объект "Модель таблицы столов"
     */
    private final BCTablesTableModel bcTablesTableModel;
        
    /**
     * таблица игроков
     */
    private final BCPlayersTableView jPlayers;

    /**
     * таблица столов
     */
    private final BCTablesTableView jTables;

    /**
     * контекстное меню таблицы столов
     */
    private final JPopupMenu jMenuTables;

    /**
     * пункт контекстного меню таблицы столов - Создать стол
     */
    private final JMenuItem jNew;

    /**
     * пункт контекстного меню таблицы столов - Сесть за стол
     */
    private final JMenuItem jSit;
    
    /**
     * Создать объект
     * @param bcClientModel объект "Модель объекта 'Клиент'"
     */
    public BCClientView(BCClientModel bcClientModel) {
        
        this.bcClientModel = bcClientModel;
        this.bcPlayersTableModel = new BCPlayersTableModel();
        this.bcTablesTableModel = new BCTablesTableModel();

        JScrollPane jPanePlayers;
        JScrollPane jPaneTables;

        this.setLayout(new GridBagLayout());
                
        jPanePlayers = new JScrollPane();
        this.jPlayers = new BCPlayersTableView(this.bcClientModel);
        this.jPlayers.setFocusable(false);
        this.jPlayers.setFillsViewportHeight(true);
        this.jPlayers.setModel(this.bcPlayersTableModel);
        jPanePlayers.setViewportView(this.jPlayers);
        
        jPaneTables = new JScrollPane();
        this.jTables = new BCTablesTableView(this.bcClientModel);
        this.jTables.setFocusable(false);
        this.jTables.setFillsViewportHeight(true);
        this.jTables.setModel(this.bcTablesTableModel);
        ListSelectionModel selModel = jTables.getSelectionModel();
        selModel.addListSelectionListener(new BCTablesSelectionListener(this, this.bcClientModel));
        jPaneTables.setViewportView(this.jTables);
        
        this.jMenuTables = new JPopupMenu();
        this.jNew = new JMenuItem("New Table");
        this.jMenuTables.add(this.jNew);
        this.jSit = new JMenuItem("Sit Table");
        this.jSit.setEnabled(false);
        this.jMenuTables.add(this.jSit);
        this.jTables.setComponentPopupMenu(this.jMenuTables);
        
        jPanePlayers.setPreferredSize(new Dimension(160, 400));
        jPaneTables.setPreferredSize(new Dimension(620, 400));

        GridBagConstraints gbCon = new GridBagConstraints();
        gbCon.anchor = GridBagConstraints.NORTH;
        gbCon.gridx = 0;
        gbCon.gridy = 0;
        gbCon.fill = GridBagConstraints.NONE;
        gbCon.weightx = 1;
        gbCon.weighty = 0;
        gbCon.insets = new Insets(5, 5, 5, 5);
        this.add(new JLabel("Players"), gbCon);

        gbCon.anchor = GridBagConstraints.NORTH;
        gbCon.gridx = 1;
        gbCon.gridy = 0;
        gbCon.fill = GridBagConstraints.NONE;
        gbCon.weightx = 3.875;
        gbCon.weighty = 0;
        gbCon.insets = new Insets(5, 5, 5, 5); // отступ сверху, слева, снизу, справа
        this.add(new JLabel("Tables"), gbCon);

        gbCon.anchor = GridBagConstraints.CENTER;
        gbCon.gridx = 0;
        gbCon.gridy = 1;
        gbCon.fill = GridBagConstraints.BOTH;   // растянуть на всю ячейку по горизонтали и вертикали
        gbCon.weightx = 1;  // для соотношения ячеек
        gbCon.weighty = 1;
        gbCon.insets = new Insets(5, 5, 5, 5); // отступ сверху, слева, снизу, справа
        this.add(jPanePlayers, gbCon);

        gbCon.gridx = 1;  // следующий столбец, т.е 1
        gbCon.gridy = 1;  // тот же ряд, т.е. 0
        gbCon.fill = GridBagConstraints.BOTH;
        gbCon.weightx = 3.875;  // в 3.875 раза шире первой, т.к. в первой - 1
        gbCon.weighty = 1;  // одинаковой высоты, т.к. в первой тоже 1
        gbCon.insets = new Insets(5, 0, 5, 5);
        this.add(jPaneTables, gbCon);
        
    }

    /**
     * Получить индекс выбранного стола
     * @return индекс выбранного стола
     */
    public int getSelectedTable() {
        return this.jTables.getSelectedRow();
    }
    
    /**
     * Добавляет слушателя
     * @param l объект слушателя
     */
    public void addActionListener(ActionListener l) {
        this.jNew.addActionListener(l);
        this.jSit.addActionListener(l);
    }
    
    @Override
    public void addMouseListener(MouseListener l) {
        this.jTables.addMouseListener(l);
    }

    @Override
    public void update(Observable o, Object arg) {
        switch((String) arg) {
            case "Room":
                this.bcPlayersTableModel.setBCPlayers(this.bcClientModel.getBCPlayers());
                this.bcTablesTableModel.setBCTables(this.bcClientModel.getBCTables());
                break;
            case "NewTable":    
                this.jNew.setEnabled(this.bcClientModel.getNewTableEnabled());
                break;
            case "SitTable":    
                this.jSit.setEnabled(this.bcClientModel.getSitTableEnabled());
                break;
        }
    }

}
