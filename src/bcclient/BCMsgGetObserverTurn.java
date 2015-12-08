/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bcclient;

import bc.BCMsgGet;
import bc.BCMsgTurn;
import java.util.Observable;
import java.util.Observer;

/**
 * Класс "Наблюдатель объекта 'Прием сообщений': 'Сообщение: Ход'"
 * Наблюдатель объекта "Прием сообщений": "Сообщение: Ход"
 * Наблюдаемый объектом "Модель объекта 'Клиент: Игра'"
 * @author iMacAverage
 */
public class BCMsgGetObserverTurn extends Observable implements Observer {

    /**
     * объект "Сообщение: Ход"
     */
    private BCMsgTurn bcMsgTurn;
    
    /**
     * Создать объект
     */
    public BCMsgGetObserverTurn() {
        this.bcMsgTurn = null;
    }
    
    /**
     * Получить объект "Сообщение: Ход"
     * @return объект "Сообщение: Ход"
     */
    public synchronized BCMsgTurn getBCMsgTurn() {
        return this.bcMsgTurn;
    }
    
    @Override
    public synchronized void update(Observable o, Object arg) {
        BCMsgGet bcMsgGet = (BCMsgGet) o;
        BCMsgTurn bcMsgTurn = (BCMsgTurn) bcMsgGet.getBCMsg("BCMsgTurn");
        if(bcMsgTurn == null)
            return;
        this.bcMsgTurn = bcMsgTurn;
        this.setChanged();
        this.notifyObservers();
    }
    
}
