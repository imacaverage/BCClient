/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bcclient;

import bc.BCMsgGet;
import bc.BCMsgSitTableKick;
import java.util.Observable;
import java.util.Observer;

/**
 * Класс "Наблюдатель объекта 'Прием сообщений': Выгнать со стола"
 * Наблюдатель объекта 'Прием сообщений': Выгнать со стола
 * Наблюдаемый объектом "Модель объекта 'Ожидания игры: игрок Б'"
 * @author iMacAverage
 */
public class BCMsgGetObserverSitTableKick extends Observable implements Observer {

    /**
     * объект "Сообщение: Выгнать со стола"
     */
    private BCMsgSitTableKick bcMsgSitTableKick;
    
    /**
     * Создать объект
     */
    public BCMsgGetObserverSitTableKick() {
        this.bcMsgSitTableKick = null;
    }
    
    /**
     * Получить объект "Сообщение: Выгнать со стола"
     * @return объект "Сообщение: Выгнать со стола"
     */
    public synchronized BCMsgSitTableKick getBCMsgSitTableKick() {
        return this.bcMsgSitTableKick;
    }
    
    @Override
    public synchronized void update(Observable o, Object arg) {
        BCMsgGet bcMsgGet = (BCMsgGet) o;
        BCMsgSitTableKick bcMsgSitTableKick = (BCMsgSitTableKick) bcMsgGet.getBCMsg("BCMsgSitTableKick");
        if(bcMsgSitTableKick == null)
            return;
        this.bcMsgSitTableKick = bcMsgSitTableKick;
        this.setChanged();
        this.notifyObservers();
    }
    
}
