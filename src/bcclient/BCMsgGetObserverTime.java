/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bcclient;

import bc.BCMsgGet;
import bc.BCMsgTime;
import java.util.Observable;
import java.util.Observer;

/**
 * Класс "Наблюдатель объекта 'Прием сообщений': 'Сообщение: Время'"
 * Наблюдатель объекта "Прием сообщений": "Сообщение: Время"
 * Наблюдаемый объектом "Модель объекта 'Игра: Клиент: игрок А'"
 * Наблюдаемый объектом "Модель объекта 'Игра: Клиент: игрок Б'"
 * @author iMacAverage
 */
public class BCMsgGetObserverTime extends Observable implements Observer {

    /**
     * объект "Сообщение: Время"
     */
    private BCMsgTime bcMsgTime;
    
    /**
     * Создать объект
     */
    public BCMsgGetObserverTime() {
        this.bcMsgTime = null;
    }
    
    /**
     * Получить объект "Сообщение: Время"
     * @return объект "Сообщение: Время"
     */
    public synchronized BCMsgTime getBCMsgTime() {
        return this.bcMsgTime;
    }
    
    @Override
    public synchronized void update(Observable o, Object arg) {
        BCMsgGet bcMsgGet = (BCMsgGet) o;
        BCMsgTime bcMsgTime = (BCMsgTime) bcMsgGet.getBCMsg("BCMsgTime");
        if(bcMsgTime == null)
            return;
        this.bcMsgTime = bcMsgTime;
        this.setChanged();
        this.notifyObservers();
    }
    
}
