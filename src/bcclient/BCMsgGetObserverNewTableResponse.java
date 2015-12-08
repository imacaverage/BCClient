/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bcclient;

import bc.BCMsgGet;
import bc.BCMsgNewTableResponse;
import java.util.Observable;
import java.util.Observer;

/**
 * Класс "Наблюдатель объекта 'Прием сообщений': 'Сообщение: Создать стол: ответ'"
 * Наблюдатель объекта "Прием сообщений": "Сообщение: Создать стол: ответ"
 * Наблюдаемый объектом "Модель объекта 'Создать стол'"
 * @author iMacAverage
 */
public class BCMsgGetObserverNewTableResponse extends Observable implements Observer {

    /**
     * объект "Сообщение: Создать стол: ответ"
     */
    private BCMsgNewTableResponse bcMsgNewTableResponse;
    
    /**
     * Создать объект
     */
    public BCMsgGetObserverNewTableResponse() {
        this.bcMsgNewTableResponse = null;
    }
    
    /**
     * Получить объект "Сообщение: Создать стол: ответ"
     * @return объект "Сообщение: Создать стол: ответ"
     */
    public synchronized BCMsgNewTableResponse getBCNewTableResponse() {
        return this.bcMsgNewTableResponse;
    }
    
    @Override
    public synchronized void update(Observable o, Object arg) {
        BCMsgGet bcMsgGet = (BCMsgGet) o;
        BCMsgNewTableResponse bcMsgNewTableResponse = (BCMsgNewTableResponse) bcMsgGet.getBCMsg("BCMsgNewTableResponse");
        if(bcMsgNewTableResponse == null)
            return;
        this.bcMsgNewTableResponse = bcMsgNewTableResponse;
        this.setChanged();
        this.notifyObservers();
    }
    
}
