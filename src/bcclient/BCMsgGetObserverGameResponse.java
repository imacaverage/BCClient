/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bcclient;

import bc.BCMsgGameResponse;
import bc.BCMsgGet;
import java.util.Observable;
import java.util.Observer;

/**
 * Класс "Наблюдатель объекта 'Прием сообщений': 'Сообщение: Игра: ответ'"
 * Наблюдатель объекта "Прием сообщений": "Сообщение: Игра: ответ"
 * Наблюдаемый объектами: 
 * - "Модель объекта 'Ожидание игры: игрок А'"
 * - "Модель объекта 'Ожидание игры: игрок Б'"
 * @author iMacAverage
 */
public class BCMsgGetObserverGameResponse extends Observable implements Observer {
    
    /**
     * объект "Сообщение: Игра: ответ"
     */
    private BCMsgGameResponse bcMsgGameResponse;

    /**
     * Создать объект
     */
    public BCMsgGetObserverGameResponse() {
        this.bcMsgGameResponse = null;
    }
    
    /**
     * Получить объект "Сообщение: Игра: ответ"
     * @return объект "Сообщение: Игра: ответ"
     */
    public synchronized BCMsgGameResponse getBCMsgGameResponse() {
        return this.bcMsgGameResponse;
    }

    @Override
    public void update(Observable o, Object arg) {
        BCMsgGet bcMsgGet = (BCMsgGet) o;
        BCMsgGameResponse bcMsgGameResponse = (BCMsgGameResponse) bcMsgGet.getBCMsg("BCMsgGameResponse");
        if(bcMsgGameResponse == null)
            return;
        this.bcMsgGameResponse = bcMsgGameResponse;
        this.setChanged();
        this.notifyObservers();
    }
    
}
