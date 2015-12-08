/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bcclient;

import bc.BCMsgGet;
import bc.BCMsgRegResponse;
import java.util.Observable;
import java.util.Observer;

/**
 * Класс "Наблюдатель объекта 'Прием сообщений': 'Сообщение: Логирование: ответ'"
 * Наблюдатель объекта "Прием сообщений": "Сообщение: Логирование: ответ"
 * Наблюдаемый объектом "Модель объекта 'Логирование'"
 * @author iMacAverage
 */
public class BCMsgGetObserverRegResponse extends Observable implements Observer {

    /**
     * объект "Сообщение: Логирование: ответ"
     */
    private BCMsgRegResponse bcMsgRegResponse;

    /**
     * Создать объект
     */
    public BCMsgGetObserverRegResponse() {
        this.bcMsgRegResponse = null;
    }
    
    /**
     * Получить объект "Сообщение: Логирование: ответ"
     * @return объект "Сообщение: Логирование: ответ"
     */
    public synchronized BCMsgRegResponse getBCMsgRegResponse() {
        return this.bcMsgRegResponse;
    }
    
    @Override
    public synchronized void update(Observable o, Object arg) {
        BCMsgGet bcMsgGet = (BCMsgGet) o;
        BCMsgRegResponse bcMsgRegResponse = (BCMsgRegResponse) bcMsgGet.getBCMsg("BCMsgRegResponse");
        if(bcMsgRegResponse == null)
            return;
        this.bcMsgRegResponse = bcMsgRegResponse;
        this.setChanged();
        this.notifyObservers();
    }
    
}
