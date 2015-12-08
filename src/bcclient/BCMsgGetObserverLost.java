/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bcclient;

import bc.BCMsgGet;
import bc.BCMsgLost;
import java.util.Observable;
import java.util.Observer;

/**
 * Класс "Наблюдатель объекта 'Прием сообщений': 'Сообщение: Сдаться'"
 * Наблюдатель объекта "Прием сообщений": "Сообщение: Сдаться"
 * Наблюдаемый объектом "Модель объекта 'Клиент: Игра'"
 * @author iMacAverage
 */
public class BCMsgGetObserverLost extends Observable implements Observer {

    /**
     * объект "Сообщение: Сдаться"
     */
    private BCMsgLost bcMsgLost;
    
    /**
     * Создать объект
     */
    public BCMsgGetObserverLost() {
        this.bcMsgLost = null;
    }
    
    /**
     * Получить объект "Сообщение: Сдаться"
     * @return объект "Сообщение: Сдаться"
     */
    public synchronized BCMsgLost getBCMsgLost() {
        return this.bcMsgLost;
    }
    
    @Override
    public synchronized void update(Observable o, Object arg) {
        BCMsgGet bcMsgGet = (BCMsgGet) o;
        BCMsgLost bcMsgLost = (BCMsgLost) bcMsgGet.getBCMsg("BCMsgLost");
        if(bcMsgLost == null)
            return;
        this.bcMsgLost = bcMsgLost;
        this.setChanged();
        this.notifyObservers();
    }
    
}
