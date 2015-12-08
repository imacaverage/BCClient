/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bcclient;

import bc.BCMsgGet;
import bc.BCMsgSend;
import bc.BCPlayer;
import javax.swing.JDialog;
import javax.swing.JFrame;

/**
 * Класс "Логирование"
 * @author iMacAverage
 */
public class BCLogin extends JDialog {
    
    /**
     * объект "Модель объекта 'Логирование'"
     */
    private final BCLoginModel bcLoginModel;
    
    /**
     * объект "Представление объекта 'Логирование'"
     */
    private final BCLoginView bcLoginView;
    
    /**
     * объект "Контроллер объекта 'Логирование'"
     */
    private final BCLoginController bcLoginController;

    /**
     * объект "Прием сообщений"
     */
    private final BCMsgGet bcMsgGet;
    
    /**
     * объект "Отправка сообщений"
     */
    private final BCMsgSend bcMsgSend;
    
    /**
     * версия клиента
     */
    private final int version;
    
    /**
     * объект "Наблюдатель объекта 'Прием сообщений': 'Сообщение: Логирование: ответ'"
     */
    private final BCMsgGetObserverRegResponse bcMsgGetObserverRegResponse;
    
    /**
     * Создать объект
     * @param parent родительское окно
     * @param bcMsgGet объект "Прием сообщений"
     * @param bcMsgSend объект "Отправка сообщений"
     * @param version версия клиента
     */
    public BCLogin(JFrame parent, BCMsgGet bcMsgGet, BCMsgSend bcMsgSend, int version) {
        super(parent, "Bulls & Cows" + " ver." + version/10 + "." + version%10, true);
        this.version = version;
        this.bcMsgGet = bcMsgGet;
        this.bcMsgSend = bcMsgSend;
        this.bcMsgGetObserverRegResponse = new BCMsgGetObserverRegResponse();
        this.bcMsgGet.addObserver(this.bcMsgGetObserverRegResponse);
        this.bcLoginModel = new BCLoginModel(this);
        this.bcMsgGetObserverRegResponse.addObserver(this.bcLoginModel);
        this.bcLoginView = new BCLoginView(this.bcLoginModel);
        this.bcLoginController = new BCLoginController(this.bcLoginModel, this.bcLoginView);
        this.bcLoginView.addActionListener(this.bcLoginController);
        this.bcLoginView.addKeyListener(bcLoginController);
        this.bcLoginModel.addObserver(this.bcLoginView);
        this.getContentPane().add(this.bcLoginView);        
    }
        
    /**
     * Получить объект "Отправка сообщений"
     * @return объект "Отправка сообщений"
     */
    public BCMsgSend getBCMsgSend() {
        return this.bcMsgSend;
    }
    
    /**
     * Получить версию клиента
     * @return версия клиента
     */
    public int getVersion() {
        return this.version;
    }
    
    /**
     * Получить признак успешного логирования
     * @return признак успешного логирования
     */
    public boolean getLogon() {
        return this.bcLoginModel.getLogon();
    }
    
    /**
     * Получить объект "Игрок"
     * @return объект "Игрок"
     */
    public BCPlayer getBCPlayer() {
        return this.bcLoginModel.getBCPlayer();
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
     * Закрыть окно
     */
    public void exit() {
        this.bcMsgGet.deleteObserver(this.bcMsgGetObserverRegResponse);
        this.setEnabled(false);
        this.dispose();
    }
    
}
