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
 * Класс "Ожидание игры: игрок Б"
 * @author iMacAverage
 */
public class BCWaitGameB extends JDialog implements Runnable {

    /**
     * объект "Прием сообщений"
     */
    private final BCMsgGet bcMsgGet;
    
    /**
     * объект "Наблюдатель объекта 'Прием сообщений': 'Сообщение: Игра: ответ'"
     */
    private final BCMsgGetObserverGameResponse bcMsgGetObserverGameResponse;
    
    /**
     * объект "Наблюдатель объекта 'Прием сообщений': 'Сообщение: Отмена создания стола'"
     */
    private final BCMsgGetObserverNewTableCancel bcMsgGetObserverNewTableCancel;

    /**
     * объект "Наблюдатель объекта 'Прием сообщений': 'Сообщение: Выгнать со стола'"
     */
    private final BCMsgGetObserverSitTableKick bcMsgGetObserverSitTableKick;
    
    /**
     * объект "Представление объекта 'Ожидание игры: игрок Б'"
     */
    private final BCWaitGameBView bcWaitGameBView;

    /**
     * Создать объект
     * @param parent родительское окно
     * @param bcPlayer объект "Игрок"
     * @param bcTable объект "Стол"
     * @param bcMsgGet объект "Прием сообщений"
     */
    public BCWaitGameB(JFrame parent, BCPlayer bcPlayer, BCTable bcTable, BCMsgGet bcMsgGet) {
        
        super(parent, "Table options", true);
        
        this.bcMsgGet = bcMsgGet;
        
        BCWaitGameBModel bcWaitGameBModel = new BCWaitGameBModel(this, bcPlayer, bcTable);
        
        this.bcMsgGetObserverGameResponse = new BCMsgGetObserverGameResponse();
        this.bcMsgGetObserverGameResponse.addObserver(bcWaitGameBModel);
        this.bcMsgGet.addObserver(this.bcMsgGetObserverGameResponse);
                
        this.bcMsgGetObserverNewTableCancel = new BCMsgGetObserverNewTableCancel();
        this.bcMsgGetObserverNewTableCancel.addObserver(bcWaitGameBModel);
        this.bcMsgGet.addObserver(this.bcMsgGetObserverNewTableCancel);

        this.bcMsgGetObserverSitTableKick = new BCMsgGetObserverSitTableKick();
        this.bcMsgGetObserverSitTableKick.addObserver(bcWaitGameBModel);
        this.bcMsgGet.addObserver(this.bcMsgGetObserverSitTableKick);
        
        this.bcWaitGameBView = new BCWaitGameBView(bcWaitGameBModel);
        
        BCWaitGameBController bcWaitGameBController = new BCWaitGameBController(bcWaitGameBModel, bcWaitGameBView);
        this.bcWaitGameBView.addActionListener(bcWaitGameBController);
        this.bcWaitGameBView.addKeyListener(bcWaitGameBController);
        
        bcWaitGameBModel.addObserver(this.bcWaitGameBView);
    
        this.getContentPane().add(this.bcWaitGameBView);
    
        // слушатель закрытия диалога
        BCWaitGameBWindowAdapter bcWaitGameBWindowAdapter = new BCWaitGameBWindowAdapter(bcWaitGameBModel);
        this.addWindowListener(bcWaitGameBWindowAdapter);

    }
    
    /**
     * Получить объект "Прием сообщений"
     * @return объект "Прием сообщений"
     */
    public BCMsgGet getBCMsgGet() {
        return this.bcMsgGet;
    }
    
    /**
     * Отобразить окно
     */
    public void display() {
        this.pack();
        this.setLocationRelativeTo(null);
        this.bcWaitGameBView.setFocus();
        this.setVisible(true);
    }
    
    /**
     * Подготовить к закрытию окна
     */
    public void prepareClose() {
        this.bcMsgGet.deleteObserver(this.bcMsgGetObserverGameResponse);
        this.bcMsgGet.deleteObserver(this.bcMsgGetObserverNewTableCancel);
        this.bcMsgGet.deleteObserver(this.bcMsgGetObserverSitTableKick);
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
