/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bcclient;

import bc.BCMsgGet;
import bc.BCPlayer;
import bc.BCTable;
import javax.swing.JDialog;
import javax.swing.JFrame;

/**
 * Класс "Создать стол"
 * @author iMacAverage
 */
public class BCNewTable extends JDialog implements Runnable {

    /**
     * объект "Прием сообщений"
     */
    private final BCMsgGet bcMsgGet;
    
    /**
     * объект "Наблюдатель объекта 'Прием сообщений': 'Сообщение: Создать стол: ответ'"
     */
    private final BCMsgGetObserverNewTableResponse bcMsgGetObserverNewTableResponse;
    
    /**
     * объект "Модель объекта 'Создать стол'"
     */
    private final BCNewTableModel bcNewTableModel;
    
    /**
     * Создать объект
     * @param parent главное окно
     * @param bcPlayer объект "Игрок"
     * @param bcMsgGet объект "Прием сообщений"
     */
    public BCNewTable(JFrame parent, BCPlayer bcPlayer, BCMsgGet bcMsgGet) {
        
        super(parent, "Table options", true);
        
        this.bcMsgGet = bcMsgGet;
        
        this.bcNewTableModel = new BCNewTableModel(this, bcPlayer, this.bcMsgGet);
        
        this.bcMsgGetObserverNewTableResponse = new BCMsgGetObserverNewTableResponse();
        this.bcMsgGet.addObserver(this.bcMsgGetObserverNewTableResponse);
        this.bcMsgGetObserverNewTableResponse.addObserver(this.bcNewTableModel);
        
        BCNewTableView bcNewTableView = new BCNewTableView();
        
        BCNewTableController bcNewTableController = new BCNewTableController(this.bcNewTableModel, bcNewTableView);
        
        bcNewTableView.addActionListener(bcNewTableController);
        
        this.bcNewTableModel.addObserver(bcNewTableView);
        
        this.getContentPane().add(bcNewTableView);
        
        // слушатель закрытия диалога
        BCNewTableWindowAdapter bcNewTableWindowAdapter = new BCNewTableWindowAdapter(this.bcNewTableModel);
        this.addWindowListener(bcNewTableWindowAdapter);
    
    }
    
    /**
     * Получить объект "Стол"
     * @return объект "Стол"
     */
    public BCTable getBCTable() {
        return this.bcNewTableModel.getBCTable();
    }

    /**
     * Отобразить окно
     */
    public void display() {
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
    
    /**
     * Подготовить к закрытию окна
     */
    public void prepareClose() {
        this.bcMsgGet.deleteObserver(this.bcMsgGetObserverNewTableResponse);
    }
    
    /**
     * Закрыть окно
     */
    public void exit() {
        this.setVisible(false);
        this.dispose();
    }

    @Override
    public void run() {
        this.display();
    }
    
}
