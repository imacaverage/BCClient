/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bcclient;

import bc.BCMsgGet;
import bc.BCMsgSitTableResponse;
import java.util.Observable;
import java.util.Observer;

/**
 * Класс "Наблюдатель объекта 'Прием сообщений': Сообщение (Сесть за стол: ответ)"
 * Наблюдатель объекта 'Прием сообщений': Сообщение (Сесть за стол: ответ)
 * Наблюдаемый объектами:
 * - "Модель объекта 'Ожидание игры: игрок А'"
 * - "Модель объекта 'Клиент'"
 * @author iMacAverage
 */
public class BCMsgGetObserverSitTableResponse extends Observable implements Observer {

    /**
     * объект "Сообщение: Сесть за стол: ответ"
     */
    private BCMsgSitTableResponse bcMsgSitTableResponse;
    
    /**
     * Создать объект
     */
    public BCMsgGetObserverSitTableResponse() {
        this.bcMsgSitTableResponse = null;
    }

    /**
     * Получить объект "Сообщение: Сесть за стол: ответ"
     * @return объект "Сообщение: Сесть за стол: ответ"
     */
    public synchronized BCMsgSitTableResponse getBCMsgSitTableResponse() {
        return this.bcMsgSitTableResponse;
    }
    
    @Override
    public synchronized void update(Observable o, Object arg) {
        BCMsgGet bcMsgGet = (BCMsgGet) o;
        BCMsgSitTableResponse bcMsgSitTableResponse = (BCMsgSitTableResponse) bcMsgGet.getBCMsg("BCMsgSitTableResponse");
        if(bcMsgSitTableResponse == null)
            return;
        this.bcMsgSitTableResponse = bcMsgSitTableResponse;
        this.setChanged();
        this.notifyObservers();
    }
    
}
