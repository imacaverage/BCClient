/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bcclient;

import bc.BCMsgGet;
import bc.BCMsgSitTableOut;
import java.util.Observable;
import java.util.Observer;

/**
 * Класс "Наблюдатель объекта 'Прием сообщений': Уйти со стола"
 * Наблюдатель объекта 'Прием сообщений': Уйти со стола
 * Наблюдаемый объектом "Модель объекта 'Ожидания игры: игрок А'"
 * @author iMacAverage
 */
public class BCMsgGetObserverSitTableOut extends Observable implements Observer {

    /**
     * объект "Сообщение: Уйти со стола"
     */
    private BCMsgSitTableOut bcMsgSitTableOut;
    
    /**
     * Создать объект
     */
    public BCMsgGetObserverSitTableOut() {
        this.bcMsgSitTableOut = null;
    }
    
    /**
     * Получить объект "Сообщение: Уйти со стола"
     * @return объект "Сообщение: Уйти со стола"
     */
    public synchronized BCMsgSitTableOut getBCMsgSitTableOut() {
        return this.bcMsgSitTableOut;
    }
    
    @Override
    public synchronized void update(Observable o, Object arg) {
        BCMsgGet bcMsgGet = (BCMsgGet) o;
        BCMsgSitTableOut bcMsgSitTableOut = (BCMsgSitTableOut) bcMsgGet.getBCMsg("BCMsgSitTableOut");
        if(bcMsgSitTableOut == null)
            return;
        this.bcMsgSitTableOut = bcMsgSitTableOut;
        this.setChanged();
        this.notifyObservers();
    }
    
}
