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
 * Класс "Ожидание игры: игрок А"
 * @author iMacAverage
 */
public class BCWaitGameA extends JDialog implements Runnable {

    /**
     * объект "Прием сообщений"
     */
    private final BCMsgGet bcMsgGet;
        
    /**
     * объект "Наблюдатель объекта 'Прием сообщений': 'Cообщение: Сесть за стол: ответ'"
     */
    private final BCMsgGetObserverSitTableResponse bcMsgGetObserverSitTableResponse;
    
    /**
     * объект "Наблюдатель объекта 'Прием сообщений': 'Cообщение: Уйти со стола'"
     */
    private final BCMsgGetObserverSitTableOut bcMsgGetObserverSitTableOut;

    /**
     * объект "Наблюдатель объекта 'Прием сообщений': 'Cообщение: Игра: запрос'"
     */
    private final BCMsgGetObserverGameRequest bcMsgGetObserverGameRequest;

    /**
     * объект "Наблюдатель объекта 'Прием сообщений': 'Cообщение: Игра: ответ'"
     */
    private final BCMsgGetObserverGameResponse bcMsgGetObserverGameResponse;

    /**
     * объект "Представление объекта 'Ожидание игры: игрок А'"
     */
    private final BCWaitGameAView bcWaitGameAView;

    /**
     * Создать объект
     * @param parent главное окно
     * @param bcTable объект "Стол"
     * @param bcMsgGet объект "Прием сообщений"
     */
    public BCWaitGameA(JFrame parent, BCTable bcTable, BCMsgGet bcMsgGet) {
        
        super(parent, "Waiting game", true);
        this.bcMsgGet = bcMsgGet;

        BCWaitGameAModel bcWaitGameAModel = new BCWaitGameAModel(this, bcTable);

        this.bcMsgGetObserverSitTableResponse = new BCMsgGetObserverSitTableResponse();
        this.bcMsgGetObserverSitTableResponse.addObserver(bcWaitGameAModel);
        this.bcMsgGet.addObserver(this.bcMsgGetObserverSitTableResponse);

        this.bcMsgGetObserverSitTableOut = new BCMsgGetObserverSitTableOut();
        this.bcMsgGetObserverSitTableOut.addObserver(bcWaitGameAModel);
        this.bcMsgGet.addObserver(this.bcMsgGetObserverSitTableOut);

        this.bcMsgGetObserverGameRequest = new BCMsgGetObserverGameRequest();
        this.bcMsgGetObserverGameRequest.addObserver(bcWaitGameAModel);
        this.bcMsgGet.addObserver(this.bcMsgGetObserverGameRequest);

        this.bcMsgGetObserverGameResponse = new BCMsgGetObserverGameResponse();
        this.bcMsgGetObserverGameResponse.addObserver(bcWaitGameAModel);
        this.bcMsgGet.addObserver(this.bcMsgGetObserverGameResponse);

        this.bcWaitGameAView = new BCWaitGameAView(bcWaitGameAModel);
        
        BCWaitGameAController bcWaitGameAController = new BCWaitGameAController(bcWaitGameAModel, this.bcWaitGameAView);
        
        this.bcWaitGameAView.addActionListener(bcWaitGameAController);
        this.bcWaitGameAView.addKeyListener(bcWaitGameAController);
        
        bcWaitGameAModel.addObserver(this.bcWaitGameAView);
        
        this.getContentPane().add(this.bcWaitGameAView);

        // слушатель закрытия диалога
        BCWaitGameAWindowAdapter bcWaitGameAWindowAdapter = new BCWaitGameAWindowAdapter(bcWaitGameAModel);
        this.addWindowListener(bcWaitGameAWindowAdapter);
    
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
        this.bcWaitGameAView.setFocus();
        this.setVisible(true);
    }

    /**
     * Подготовить к закрытию окна
     */
    public void prepareClose() {
        this.bcMsgGet.deleteObserver(this.bcMsgGetObserverSitTableResponse);
        this.bcMsgGet.deleteObserver(this.bcMsgGetObserverSitTableOut);
        this.bcMsgGet.deleteObserver(this.bcMsgGetObserverGameRequest);
        this.bcMsgGet.deleteObserver(this.bcMsgGetObserverGameResponse);
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
