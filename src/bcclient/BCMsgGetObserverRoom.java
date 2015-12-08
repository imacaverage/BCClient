/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bcclient;

import bc.BCMsgGet;
import bc.BCMsgRoom;
import java.util.Observable;
import java.util.Observer;

/**
 * Класс "Наблюдатель объекта 'Прием сообщений': Игровая комната"
 * Наблюдатель объекта 'Прием сообщений': Игровая комната
 * Наблюдаемый объктом "Модель объекта 'Клиент'"
 * @author iMacAverage
 */
public class BCMsgGetObserverRoom extends Observable implements Observer {
    
    /**
     * объект "Сообщение: Игровая комната"
     */
    private BCMsgRoom bcMsgRoom;

    /**
     * Создать объект
     */
    public BCMsgGetObserverRoom() {
        this.bcMsgRoom = null;
    }
    
    /**
     * Получить уведомление
     */
    public void getNotify() {
        if(this.bcMsgRoom != null) {
            this.setChanged();
            this.notifyObservers();
        }
    }

    /**
     * Получить объект "Сообщение: Игровая комната"
     * @return объект "Сообщение: Игровая комната"
     */
    public synchronized BCMsgRoom getBCMsgRoom() {
        return this.bcMsgRoom;
    }
    
    @Override
    public synchronized void update(Observable o, Object arg) {
        BCMsgGet bcMsgGet = (BCMsgGet) o;
        BCMsgRoom bcMsgRoom = (BCMsgRoom) bcMsgGet.getBCMsg("BCMsgRoom");
        if(bcMsgRoom == null)
            return;
        this.bcMsgRoom = bcMsgRoom;
        this.setChanged();
        this.notifyObservers();
    }
        
}
