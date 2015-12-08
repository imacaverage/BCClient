/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bcclient;

import bc.BCGame;
import bc.BCMsgGameRequest;
import bc.BCMsgGameResponse;
import bc.BCMsgNewTableCancel;
import bc.BCMsgSitTableKick;
import bc.BCMsgSitTableResponse;
import bc.BCPlayer;
import bc.BCTable;
import bc.RegEx;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JOptionPane;

/**
 * Класс "Модель объекта 'Ожидание игры: игрок А'"
 * Наблюдатель объектов:
 * - "Наблюдатель объекта 'Прием сообщений': 'Cообщение: Сесть за стол: ответ'"
 * - "Наблюдатель объекта 'Прием сообщений': 'Cообщение: Уйти со стола'"
 * - "Наблюдатель объекта 'Прием сообщений': 'Cообщение: Игра: запрос'"
 * - "Наблюдатель объекта 'Прием сообщений': 'Cообщение: Игра: ответ'"
 * Наблюдаемый объектом "Представление 'Ожидание игры: игрок А'"
 * @author iMacAverage
 */
public class BCWaitGameAModel extends Observable implements Observer {
    
    /**
     * объект "Ожидание игры: игрок А"
     */
    private final BCWaitGameA bcWaitGameA;
    
    /**
     * объект "Стол"
     */
    private final BCTable bcTable;
        
    /**
     * доступность возможности старта игры
     */
    private boolean playEnabled;

    /**
     * доступность возможности выгнать игрока со стола
     */
    private boolean kickEnabled;

    /**
     * доступность возможности отказаться от созданного стола
     */
    private final boolean cancelEnabled;
    
    /**
     * загаданное число игрока Б
     */
    private String numberB;

    /**
     * Создать объект
     * @param bcWaitGameA объект "Ожидание игры: игрок А"
     * @param bcTable объект "Стол"
     */
    public BCWaitGameAModel(BCWaitGameA bcWaitGameA, BCTable bcTable) {
        this.bcWaitGameA = bcWaitGameA;
        this.bcTable = bcTable;
        this.playEnabled = false;
        this.kickEnabled = false;
        this.cancelEnabled = true;
        this.numberB = null;
        // если игрок Б уже за столом (повторная игра)
        if (this.bcTable.getBCPlayerB() != null) {
            this.kickEnabled = true;
        }
    }

    /**
     * Получить признак возможности старта игры
     * @return признак возможности старта игры
     */
    public boolean getPlayEnabled() {
        return this.playEnabled;
    }
    
    /**
     * Получить признак возможности выгнать игрока со стола
     * @return признак возможности выгнать игрока со стола
     */
    public boolean getKickEnabled() {
        return this.kickEnabled;
    }

    /**
     * Получить признак возможности отмены создания стола
     * @return признак возможности отмены создания стола
     */
    public boolean getCancelEnabled() {
        return this.cancelEnabled;
    }
    
    /**
     * Получить объект "Стол"
     * @return объект "Стол"
     */
    public BCTable getBCTable() {
        return this.bcTable;
    }
    
    /**
     * Контроль корректности ввода числа игры
     * @param number число игры
     * @return true в случае успеха, иначе false
     */
    public boolean checkInputNumber(String number) {
        int n = this.bcTable.getNumberLength();
        String regex = RegEx.digitsWithRepetition(n);
        String message = "Incorrect input, enter " + n + " digits";
        if (!this.bcTable.getWithRepeat()) {
            message += " without repetition";
            regex = RegEx.digitsWithOutRepetition(n);
        }
        if(!number.matches(regex)) {
            JOptionPane.showMessageDialog(null, message , "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }
    
    /**
     * Отправить запрос игры
     * @param number число игры
     * @return true в случае успеха, иначе false
     */
    public boolean playWaitGameA(String number) {
        if(this.checkInputNumber(number)) {
            BCMsgGameRequest bcMsgGameRequest = new BCMsgGameRequest(this.bcTable, number, this.numberB);
            this.bcTable.getBCPlayerA().sendBCMsg(bcMsgGameRequest);
            this.playEnabled = false;
            this.kickEnabled = false;
            this.setChanged();
            this.notifyObservers();
            return true;
        }
        return false;
    }
    /**
     * Выгнать игрока Б со стола
     */
    public void kickPlayerB() {
        this.playEnabled = false;
        this.kickEnabled = false;
        BCMsgSitTableKick bcMsgSitTableKick = new BCMsgSitTableKick(this.bcTable);
        this.bcTable.getBCPlayerA().sendBCMsg(bcMsgSitTableKick);
        this.bcTable.kickBCPlayerB();   
        this.setChanged();
        this.notifyObservers();
    }

    /**
     * Подготовить к закрытию окна
     */
    public void prepareClose() {
        BCMsgNewTableCancel bcMsgNewTableCancel = new BCMsgNewTableCancel(this.bcTable);
        this.bcTable.getBCPlayerA().sendBCMsg(bcMsgNewTableCancel);
        this.bcWaitGameA.prepareClose();
    }
    
    /**
     * Отменить создание стола
     */
    public void cancelNewTable() {
        this.prepareClose();
        this.bcWaitGameA.exit();
    }
    
    @Override
    public void update(Observable o, Object arg) {

        switch(o.getClass().getSimpleName()) {

            case "BCMsgGetObserverSitTableResponse":    // Сообщение: Сесть за стол: ответ
        
                BCMsgGetObserverSitTableResponse bcMsgGetObserverSitTableResponse = (BCMsgGetObserverSitTableResponse) o;        
                BCMsgSitTableResponse bcMsgSitTableResponse = bcMsgGetObserverSitTableResponse.getBCMsgSitTableResponse();
                
                BCPlayer bcPlayer = bcMsgSitTableResponse.getBCPlayer();
                this.bcTable.setBCPlayerB(bcPlayer);
                
                this.kickEnabled = true;
                this.setChanged();
                this.notifyObservers();
                break;
        
            case "BCMsgGetObserverSitTableOut":    // Сообщение: Уйти со стола

                this.bcTable.setBCPlayerB(null);
                this.playEnabled = false;
                this.kickEnabled = false;
                this.setChanged();
                this.notifyObservers();
                break;

            case "BCMsgGetObserverGameRequest":    // Сообщение: Игра: запрос

                BCMsgGetObserverGameRequest bcMsgGetObserverGameRequest = (BCMsgGetObserverGameRequest) o;
                BCMsgGameRequest bcMsgGameRequest = bcMsgGetObserverGameRequest.getBCMsgGameRequest();

                this.numberB = bcMsgGameRequest.getNumberB();
                this.playEnabled = true;
                this.setChanged();
                this.notifyObservers();
                break;

            case "BCMsgGetObserverGameResponse":    // Сообщение: Игра: ответ

                BCMsgGetObserverGameResponse bcMsgGetObserverGameResponse = (BCMsgGetObserverGameResponse) o;
                BCMsgGameResponse bcMsgGameResponse = bcMsgGetObserverGameResponse.getBCMsgGameResponse();

                // удаляю сообщения, которые будут обрабатываться следующим объектом
                this.bcWaitGameA.getBCMsgGet().removeBCMsg("BCMsgGameEnd");
                this.bcWaitGameA.getBCMsgGet().removeBCMsg("BCMsgTurn");
                this.bcWaitGameA.getBCMsgGet().removeBCMsg("BCMsgTime");

                BCGame bcGame = new BCGame(this.bcTable, bcMsgGameResponse.getNumberA(), bcMsgGameResponse.getNumberB());
                BCGameAClient bcGameAClient = new BCGameAClient(null, bcGame, this.bcWaitGameA.getBCMsgGet(), this.bcTable.getBCPlayerA().getBCMsgSend());
                Thread thread = new Thread(bcGameAClient);
                thread.start();

                this.bcWaitGameA.prepareClose();
                this.bcWaitGameA.exit();
                break;
        
        }        
    
    }
    
}
