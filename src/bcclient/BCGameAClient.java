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
 * Класс "Игра: Клиент: игрок А"
 * @author iMacAverage
 */
public class BCGameAClient extends JDialog implements Runnable {

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
     * объект "Представление объекта 'Игра: Клиент: игрок А'"
     */
    private final BCGameAClientView bcGameAClientView;

    /**
     * Создать объект
     * @param parent главное окно
     * @param bcGame объект "Игра"
     * @param bcMsgGet объект "Прием сообщений"
     * @param bcMsgSend объект "Отправка сообщений"
     */
    public BCGameAClient(JFrame parent, BCGame bcGame, BCMsgGet bcMsgGet, BCMsgSend bcMsgSend) {
        
        super(parent, "Game: Player A", true);
        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        
        this.bcGame = bcGame;
        this.bcMsgGet = bcMsgGet;

        BCGameAClientModel bcGameAClientModel = new BCGameAClientModel(this, this.bcGame, bcMsgSend);

        this.bcMsgGetObserverTurn = new BCMsgGetObserverTurn();
        this.bcMsgGetObserverTurn.addObserver(bcGameAClientModel);
        this.bcMsgGet.addObserver(this.bcMsgGetObserverTurn);

        this.bcMsgGetObserverGameEnd = new BCMsgGetObserverGameEnd();
        this.bcMsgGetObserverGameEnd.addObserver(bcGameAClientModel);
        this.bcMsgGet.addObserver(this.bcMsgGetObserverGameEnd);

        this.bcMsgGetObserverTime = new BCMsgGetObserverTime();
        this.bcMsgGetObserverTime.addObserver(bcGameAClientModel);
        this.bcMsgGet.addObserver(this.bcMsgGetObserverTime);

        this.bcGameAClientView = new BCGameAClientView(bcGameAClientModel);
        
        BCGameAClientController bcGameAClientController = new BCGameAClientController(bcGameAClientModel, this.bcGameAClientView);
        
        this.bcGameAClientView.addActionListener(bcGameAClientController);
        this.bcGameAClientView.addKeyListener(bcGameAClientController);
        
        bcGameAClientModel.addObserver(this.bcGameAClientView);
        
        this.getContentPane().add(this.bcGameAClientView);
    
        // слушатель закрытия диалога
        BCGameAClientWindowAdapter bcGameAClientWindowAdapter = new BCGameAClientWindowAdapter(bcGameAClientModel);
        this.addWindowListener(bcGameAClientWindowAdapter);

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
        this.bcGameAClientView.setFocus();
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
