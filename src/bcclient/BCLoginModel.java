/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bcclient;

import bc.BCMsgRegRequest;
import bc.BCMsgRegResponse;
import bc.BCPlayer;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JOptionPane;

/**
 * Класс "Модель объекта 'Логирование'"
 * Наблюдатель объекта "Наблюдатель объекта 'Прием сообщений': 'Сообщение: Логирование: ответ'"
 * Наблюдаемый объектом "Представление объекта 'Логирование'"
 * @author iMacAverage
 */
public class BCLoginModel extends Observable implements Observer {

    /**
     * объект "Логирование"
     */
    private final BCLogin bcLogin;

    /**
     * доступность возможности отправки на сервер данных логирования (режим: существующий игрок)
     */
    private boolean EnterEnabled;

    /**
     * доступность возможности отправки на сервер данных логирования (режим: новый игрок)
     */
    private boolean NewEnabled;
    
    /**
     * признак успешного логирования
     */
    private boolean logon;
    
    /**
     * объект "Игрок"
     */
    private BCPlayer bcPlayer;
    
    /**
     * Создать объект
     * @param bcLogin объект "Логирование"
     */
    public BCLoginModel(BCLogin bcLogin) {
        super();
        this.bcLogin = bcLogin;
        this.EnterEnabled = true;
        this.NewEnabled = true;
        this.bcPlayer = null;
        this.logon = false;
    }
    
    /**
     * Задать доступность возможности отправки на сервер данных логирования (режим: существующий игрок)
     * @param b доступность возможности отправки на сервер данных логирования
     */
    public void setEnterEnabled(boolean b) {
        this.EnterEnabled = b;
        this.setChanged();
        this.notifyObservers();
    }
    
    /**
     * Задать доступность возможности отправки на сервер данных логирования (режим: новый игрок)
     * @param b доступность возможности отправки на сервер данных логирования
     */
    public void setNewEnabled(boolean b) {
        this.NewEnabled = b;
        this.setChanged();
        this.notifyObservers();
    }

    /**
     * Получить доступность возможности отправки на сервер данных логирования (режим: существующий игрок)
     * @return доступность возможности отправки на сервер данных логирования
     */
    public boolean getEnterEnabled() {
        return this.EnterEnabled;
    }
    
    /**
     * Получить доступность возможности отправки на сервер данных логирования (режим: новый игрок)
     * @return доступность возможности отправки на сервер данных логирования
     */
    public boolean getNewEnabled() {
        return this.NewEnabled;
    }
    
    /**
     * Получить признак успешного логирования
     * @return признак успешного логирования
     */
    public boolean getLogon() {
        return this.logon;
    }
    
    /**
     * Залогиниться
     * @param login логин
     * @param password пароль
     * @param newPlayer признак создания нового игрока
     */
    public void setLogon(String login, String password, boolean newPlayer) {
        this.EnterEnabled = false;
        this.NewEnabled = false;
        this.setChanged();
        this.notifyObservers();
        BCMsgRegRequest bcMsgRegRequest = new BCMsgRegRequest(login, password, newPlayer);
        this.bcLogin.getBCMsgSend().sendBCMsg(bcMsgRegRequest);        
    }
    
    /**
     * Получить объект "Игрок"
     * @return объект "Игрок"
     */
    public BCPlayer getBCPlayer() {
        return this.bcPlayer;
    }
    
    @Override
    public void update(Observable o, Object arg) {
       
        BCMsgGetObserverRegResponse bcMsgGetObserverRegResponse = (BCMsgGetObserverRegResponse) o;
        BCMsgRegResponse bcMsgRegResponse = bcMsgGetObserverRegResponse.getBCMsgRegResponse();
        
        // ошибка логирования
        if(!bcMsgRegResponse.getRegistration()) {
            Thread t = new Thread(new Runnable(){
                public void run(){
                    JOptionPane.showMessageDialog(null, bcMsgRegResponse.getError(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            });
            t.start();            
            this.EnterEnabled = true;
            this.NewEnabled = true;
            this.setChanged();
            this.notifyObservers();
            return;
        }
        
        // если у клиента более ранняя версия
        if (this.bcLogin.getVersion() < bcMsgRegResponse.getVersion()) {
            JOptionPane.showMessageDialog(null, "Your version is outdated, download version " + bcMsgRegResponse.getVersion()/10 + "." + bcMsgRegResponse.getVersion()%10 + " from site!", "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
        
        // успешное логирование
        this.logon = true;
        this.bcPlayer = new BCPlayer(bcMsgRegResponse.getLogin(), bcMsgRegResponse.getRating(), this.bcLogin.getBCMsgSend());
        this.bcLogin.exit();

    }
    
}
