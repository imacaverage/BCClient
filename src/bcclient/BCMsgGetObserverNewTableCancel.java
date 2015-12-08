/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bcclient;

import bc.BCMsgGet;
import bc.BCMsgNewTableCancel;
import java.util.Observable;
import java.util.Observer;

/**
 * Класс "Наблюдатель объекта 'Прием сообщений': 'Сообщение: Отмена создания стола'"
 * Наблюдатель объекта "Прием сообщений": "Сообщение: Отмена создания стола"
 * Наблюдаемый объектом "Модель объекта 'Ожидания игры: игрок Б'"
 * @author iMacAverage
 */
public class BCMsgGetObserverNewTableCancel extends Observable implements Observer {

    /**
     * объект "Сообщение: Отмена создания стола"
     */
    private BCMsgNewTableCancel bcMsgNewTableCancel;
    
    /**
     * Создать объект
     */
    public BCMsgGetObserverNewTableCancel() {
        this.bcMsgNewTableCancel = null;
    }
    
    /**
     * Получить объект "Сообщение: Отмена создания стола"
     * @return объект "Сообщение: Отмена создания стола"
     */
    public synchronized BCMsgNewTableCancel getBCMsgNewTableCancel() {
        return this.bcMsgNewTableCancel;
    }
    
    @Override
    public synchronized void update(Observable o, Object arg) {
        BCMsgGet bcMsgGet = (BCMsgGet) o;
        BCMsgNewTableCancel bcMsgNewTableCancel = (BCMsgNewTableCancel) bcMsgGet.getBCMsg("BCMsgNewTableCancel");
        if(bcMsgNewTableCancel == null)
            return;
        this.bcMsgNewTableCancel = bcMsgNewTableCancel;
        this.setChanged();
        this.notifyObservers();
    }
    
}
