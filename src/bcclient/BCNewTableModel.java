/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bcclient;

import bc.BCMsgGet;
import bc.BCMsgNewTableRequest;
import bc.BCMsgNewTableResponse;
import bc.BCPlayer;
import bc.BCTable;
import java.util.Observable;
import java.util.Observer;

/**
 * Класс "Модель объекта 'Создать стол'"
 * Наблюдатель объекта "Прием сообщений": "Сообщение: Создать стол: ответ"
 * Наблюдаемый объектом "Представление объекта 'Cоздать стол'"
 * @author iMacAverage
 */
public class BCNewTableModel extends Observable implements Observer {

    /**
     * объект "Создать стол"
     */
    private final BCNewTable bcNewTable;
    
    /**
     * объект "Игрок"
     */
    private final BCPlayer bcPlayer;
    
    /**
     * объект "Прием сообщений"
     */
    private final BCMsgGet bcMsgGet;
    
    /**
     * объект "Стол"
     */
    private BCTable bcTable;
                
    /**
     * доступность возможности отправки на сервер данных по созданию нового стола
     */
    private boolean okEnabled;

    /**
     * доступность возможности отказаться от создания нового стола
     */
    private boolean cancelEnabled;
        
    /**
     * Создать объект
     * @param bcNewTable объект "Создать стол"
     * @param bcPlayer объект "Игрок"
     * @param bcMsgGet объект "Прием сообщений"
     */
    public BCNewTableModel(BCNewTable bcNewTable, BCPlayer bcPlayer, BCMsgGet bcMsgGet) {
        this.bcNewTable = bcNewTable;
        this.bcPlayer = new BCPlayer(bcPlayer.getLogin(), bcPlayer.getRating(), bcPlayer.getBCMsgSend());
        this.bcMsgGet = bcMsgGet;
        this.bcTable = null;
        this.okEnabled = true;
        this.cancelEnabled = true;
    }
    
    /**
     * Получить признак возможности отправки на сервер данных по созданию нового стола
     * @return признак возможности отправки на сервер данных по созданию нового стола
     */
    public boolean getOKEnabled() {
        return this.okEnabled;
    }
    
    /**
     * Получить признак возможности отказаться от создания нового стола
     * @return признак возможности отказаться от создания нового стола
     */
    public boolean getCancelEnabled() {
        return this.cancelEnabled;
    }
    
    /**
     * Задать признак возможности отправки на сервер данных по созданию нового стола
     * @param okEnabled признак возможности отправки на сервер данных по созданию нового стола
     */
    public void setOKEnabled(boolean okEnabled) {
        this.okEnabled = okEnabled;
        this.setChanged();
        this.notifyObservers();
    }
    
    /**
     * Задать признак возможности отказаться от создания нового стола
     * @param cancelEnabled признак возможности отказаться от создания нового стола
     */
    public void setCancelEnabled(boolean cancelEnabled) {
        this.cancelEnabled = cancelEnabled;
        this.setChanged();
        this.notifyObservers();
    }
    
    /**
     * Получить объект "Стол"
     * @return объект "Стол"
     */
    public BCTable getBCTable() {
        return this.bcTable;
    }
    
    /**
     * Отправить объект "Сообщение: Создать стол: запрос"
     * @param minRating минимальный рейтинг соперника
     * @param numberLength количество цифр в числе
     * @param countMin количество минут на игру
     * @param withRepeat возможность повторения цифр в числе
     * @return true в случае успеха, иначе false
     */
    public boolean sendNewTable(int minRating, int numberLength, int countMin, boolean withRepeat) {
        this.setOKEnabled(false);
        this.bcTable = new BCTable(this.bcPlayer, minRating, numberLength, withRepeat, countMin);
        BCMsgNewTableRequest bcMsgNewTableRequest = new BCMsgNewTableRequest(bcTable);
        return this.bcPlayer.sendBCMsg(bcMsgNewTableRequest);
    }

    /**
     * Подготовить данные перед закрытием диалога
     */
    public void prepareClose() {
        this.bcTable = null;
        this.bcNewTable.prepareClose();
    }
    
    /**
     * Отменить создание стола
     */
    public void cancelNewTable() {
        this.prepareClose();
        this.bcNewTable.exit();
    }

    @Override
    public void update(Observable o, Object arg) {        

        BCMsgGetObserverNewTableResponse bcMsgGetObserverNewTableResponse = (BCMsgGetObserverNewTableResponse) o;
        BCMsgNewTableResponse bcMsgNewTableResponse = bcMsgGetObserverNewTableResponse.getBCNewTableResponse();

        // удаляю сообщения, которые будут обрабатываться следующим объектом
        this.bcMsgGet.removeBCMsg("BCMsgSitTableResponse");
        this.bcMsgGet.removeBCMsg("BCMsgSitTableOut");
        this.bcMsgGet.removeBCMsg("BCMsgGameRequest");
        this.bcMsgGet.removeBCMsg("BCMsgGameResponse");

        this.bcTable.setNumTable(bcMsgNewTableResponse.getNumTable());
        BCWaitGameA bcWaitGameA = new BCWaitGameA(null, bcTable, this.bcMsgGet);
        Thread thread = new Thread(bcWaitGameA);
        thread.start();

        this.bcNewTable.exit();
    }
        
}
