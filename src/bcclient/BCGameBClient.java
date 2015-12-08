/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bcclient;

import bc.BCGame;
import bc.BCMsgGet;
import bc.BCMsgSend;
import javax.swing.JDialog;
import javax.swing.JFrame;

/**
 * Класс "Игра: Клиент: игрок Б"
 * @author iMacAverage
 */
public class BCGameBClient extends JDialog implements Runnable {

    /**
     * объект "Игра"
     */
    private final BCGame bcGame;
    
    /**
     * объект "Прием сообщений"
     */
    private final BCMsgGet bcMsgGet;
    
    /**
     * объект "Наблюдатель объекта 'Прием сообщений': 'Cообщение: Ход'"
     */
    private final BCMsgGetObserverTurn bcMsgGetObserverTurn;
    
    /**
     * объект "Наблюдатель объекта 'Прием сообщений': 'Cообщение: Игра: конец'"
     */
    private final BCMsgGetObserverGameEnd bcMsgGetObserverGameEnd;

    /**
     * объект "Наблюдатель объекта 'Прием сообщений': 'Cообщение: Время'"
     */
    private final BCMsgGetObserverTime bcMsgGetObserverTime;

    /**
     * объект "Представление объекта 'Игра: Клиент: игрок Б'"
     */
    private final BCGameBClientView bcGameBClientView;

    /**
     * Создать объект
     * @param parent главное окно
     * @param bcGame объект "Игра"
     * @param bcMsgGet объект "Прием сообщений"
     * @param bcMsgSend объект "Отправка сообщений"
     */
    public BCGameBClient(JFrame parent, BCGame bcGame, BCMsgGet bcMsgGet, BCMsgSend bcMsgSend) {
        
        super(parent, "Game: Player B", true);
        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        this.bcGame = bcGame;
        this.bcMsgGet = bcMsgGet;

        BCGameBClientModel bcGameBClientModel = new BCGameBClientModel(this, this.bcGame, bcMsgSend);

        this.bcMsgGetObserverTurn = new BCMsgGetObserverTurn();
        this.bcMsgGetObserverTurn.addObserver(bcGameBClientModel);
        this.bcMsgGet.addObserver(this.bcMsgGetObserverTurn);

        this.bcMsgGetObserverGameEnd = new BCMsgGetObserverGameEnd();
        this.bcMsgGetObserverGameEnd.addObserver(bcGameBClientModel);
        this.bcMsgGet.addObserver(this.bcMsgGetObserverGameEnd);

        this.bcMsgGetObserverTime = new BCMsgGetObserverTime();
        this.bcMsgGetObserverTime.addObserver(bcGameBClientModel);
        this.bcMsgGet.addObserver(this.bcMsgGetObserverTime);

        this.bcGameBClientView = new BCGameBClientView(bcGameBClientModel);
        
        BCGameBClientController bcGameBClientController = new BCGameBClientController(bcGameBClientModel, this.bcGameBClientView);
        
        this.bcGameBClientView.addActionListener(bcGameBClientController);
        this.bcGameBClientView.addKeyListener(bcGameBClientController);
        
        bcGameBClientModel.addObserver(this.bcGameBClientView);
        
        this.getContentPane().add(this.bcGameBClientView);
    
        // слушатель закрытия диалога
        BCGameBClientWindowAdapter bcGameBClientWindowAdapter = new BCGameBClientWindowAdapter(bcGameBClientModel);
        this.addWindowListener(bcGameBClientWindowAdapter);

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
        this.bcGameBClientView.setFocus();
        this.setVisible(true);
    }
    
    /**
     * Подготовить к закрытию
     */
    public void prepareClose() {
        this.bcMsgGet.deleteObserver(this.bcMsgGetObserverTurn);
        this.bcMsgGet.deleteObserver(this.bcMsgGetObserverGameEnd);
        this.bcMsgGet.deleteObserver(this.bcMsgGetObserverTime);
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
