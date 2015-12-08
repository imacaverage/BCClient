/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bcclient;

import bc.BCMsgGameRequest;
import bc.BCMsgGet;
import java.util.Observable;
import java.util.Observer;

/**
 * Класс "Наблюдатель объекта 'Прием сообщений': 'Сообщение: Игра: запрос'"
 * Наблюдатель объекта "Прием сообщений": "Сообщение: Игра: запрос"
 * Наблюдаемый объектами: 
 * - "Модель объекта 'Ожидание игры: игрок А'"
 * - "Модель объекта 'Ожидание игры: игрок Б'"
 * @author iMacAverage
 */
public class BCMsgGetObserverGameRequest extends Observable implements Observer {
    
    /**
     * объект "Сообщение: Игра: запрос"
     */
    private BCMsgGameRequest bcMsgGameRequest;

    /**
     * Создать объект
     */
    public BCMsgGetObserverGameRequest() {
        this.bcMsgGameRequest = null;
    }
    
    /**
     * Получить объект "Сообщение: Игра: запрос"
     * @return объект "Сообщение: Игра: запрос"
     */
    public synchronized BCMsgGameRequest getBCMsgGameRequest() {
        return this.bcMsgGameRequest;
    }

    @Override
    public void update(Observable o, Object arg) {
        BCMsgGet bcMsgGet = (BCMsgGet) o;
        BCMsgGameRequest bcMsgGameRequest = (BCMsgGameRequest) bcMsgGet.getBCMsg("BCMsgGameRequest");
        if(bcMsgGameRequest == null)
            return;
        this.bcMsgGameRequest = bcMsgGameRequest;
        this.setChanged();
        this.notifyObservers();
    }
    
}
