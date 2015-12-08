/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bcclient;

import bc.BCMsgGet;
import bc.BCMsgGameEnd;
import java.util.Observable;
import java.util.Observer;

/**
 * Класс "Наблюдатель объекта 'Прием сообщений': 'Сообщение: Игра: конец'"
 * Наблюдатель объекта "Прием сообщений": "Сообщение: Игра: конец"
 * Наблюдаемый объектом "Модель объекта 'Клиент: Игра'"
 * @author iMacAverage
 */
public class BCMsgGetObserverGameEnd extends Observable implements Observer {

    /**
     * объект "Сообщение: Игра: конец"
     */
    private BCMsgGameEnd bcMsgGameEnd;
    
    /**
     * Создать объект
     */
    public BCMsgGetObserverGameEnd() {
        this.bcMsgGameEnd = null;
    }
    
    /**
     * Получить объект "Сообщение: Игра: конец"
     * @return объект "Сообщение: Игра: конец"
     */
    public synchronized BCMsgGameEnd getBCMsgGameEnd() {
        return this.bcMsgGameEnd;
    }
    
    @Override
    public synchronized void update(Observable o, Object arg) {
        BCMsgGet bcMsgGet = (BCMsgGet) o;
        BCMsgGameEnd bcMsgGameEnd = (BCMsgGameEnd) bcMsgGet.getBCMsg("BCMsgGameEnd");
        if(bcMsgGameEnd == null)
            return;
        this.bcMsgGameEnd = bcMsgGameEnd;
        this.setChanged();
        this.notifyObservers();
    }
    
}
