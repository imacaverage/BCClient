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
import bc.BCMsgSitTableOut;
import bc.BCPlayer;
import bc.BCTable;
import bc.RegEx;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JOptionPane;

/**
 * Класс "Модель объекта 'Ожидания игры: игрок Б'"
 * Наблюдатель объектов:
 * - "Наблюдатель объекта 'Прием сообщений': 'Cообщение: Игра: ответ'"
 * - "Наблюдатель объекта 'Прием сообщений': 'Cообщение: Отмена создания стола'"
 * - "Наблюдатель объекта 'Прием сообщений': 'Cообщение: Выгнать со стола'"
 * Наблюдаемый объектом "Представление объекта 'Ожидание игры: игрок Б'"
 * @author iMacAverage
 */
public class BCWaitGameBModel extends Observable implements Observer {

    /**
     * объект "Ожидания игры: игрок Б"
     */
    private final BCWaitGameB bcWaitGameB;
    
    /**
     * объект "Игрок"
     */
    private final BCPlayer bcPlayer;
    
    /**
     * объект "Стол"     
     */
    private final BCTable bcTable;
                
    /**
     * доступность возможности ввода числа игры
     */
    private boolean numberEnabled;

    /**
     * доступность возможности старта игры
     */
    private boolean playEnabled;

    /**
     * доступность возможности уйти со стола
     */
    private boolean cancelEnabled;

    /**
     * Создать объект
     * @param bcWaitGameB объект "Ожидания игры: игрок Б"
     * @param bcPlayer объект "Игрок"
     * @param bcTable объект "Стол"
     */
    public BCWaitGameBModel(BCWaitGameB bcWaitGameB, BCPlayer bcPlayer, BCTable bcTable) {
        this.bcWaitGameB = bcWaitGameB;
        this.bcPlayer = new BCPlayer(bcPlayer.getLogin(), bcPlayer.getRating(), bcPlayer.getBCMsgSend());
        this.bcTable = bcTable;
        this.numberEnabled = true;
        this.playEnabled = true;
        this.cancelEnabled = true;
    }
    
    /**
     * Получить признак возможности ввода числа игры
     * @return признак возможности ввода числа игры
     */
    public boolean getNumberEnabled() {
        return this.numberEnabled;
    }

    /**
     * Получить признак возможности старта игры
     * @return признак возможности старта игры
     */
    public boolean getPlayEnabled() {
        return this.playEnabled;
    }
    
    /**
     * Получить признак возможности уйти со стола
     * @return признак возможности уйти со стола
     */
    public boolean getCancelEnabled() {
        return this.cancelEnabled;
    }

    /**
     * Задать признак возможности старта игры
     * @param playEnabled признак возможности старта игры
     */
    public void setPlayEnabled(boolean playEnabled) {
        this.playEnabled = playEnabled;
        this.setChanged();
        this.notifyObservers();
    }
    
    /**
     * Задать признак возможности уйти со стола
     * @param cancelEnabled признак возможности уйти со стола
     */
    public void setCancelEnabled(boolean cancelEnabled) {
        this.cancelEnabled = cancelEnabled;
        this.setChanged();
        this.notifyObservers();
    }
    
    /**
     * Получить объект "Игрок"
     * @return объект "Игрок"
     */
    public BCPlayer getBCPlayer() {
        return this.bcPlayer;
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
    public boolean playWaitGameB(String number) {
        if(this.checkInputNumber(number)) {
            BCMsgGameRequest bcMsgGameRequest = new BCMsgGameRequest(this.bcTable, number);
            this.bcPlayer.sendBCMsg(bcMsgGameRequest);
            this.numberEnabled = false;
            this.playEnabled = false;
            this.setChanged();
            this.notifyObservers();
            return true;
        }
        return false;
    }

    /**
     * Подготовить к закрытию окна
     */
    public void prepareClose() {
        BCMsgSitTableOut bcMsgSitTableOut = new BCMsgSitTableOut(this.bcTable);
        this.bcPlayer.sendBCMsg(bcMsgSitTableOut);
        this.bcWaitGameB.prepareClose();
    }

    /**
     * Уйти со стола
     */
    public void cancelWaitGameB() {
        this.prepareClose();
        this.bcWaitGameB.exit();
    }
    
    @Override
    public void update(Observable o, Object arg) {
        
        switch(o.getClass().getSimpleName()) {

            case "BCMsgGetObserverSitTableKick":    // Сообщение: Выгнать со стола

                BCMsgGetObserverSitTableKick bcMsgGetObserverSitTableKick = (BCMsgGetObserverSitTableKick) o;        
                BCMsgSitTableKick bcMsgSitTableKick = bcMsgGetObserverSitTableKick.getBCMsgSitTableKick();
                this.bcPlayer.sendBCMsg(bcMsgSitTableKick);
                this.bcWaitGameB.exit();
                break;

            case "BCMsgGetObserverNewTableCancel":    // Сообщение: Отмена создания стола
                
                BCMsgGetObserverNewTableCancel bcMsgGetObserverNewTableCancel = (BCMsgGetObserverNewTableCancel) o;
                BCMsgNewTableCancel bcMsgNewTableCancel = bcMsgGetObserverNewTableCancel.getBCMsgNewTableCancel();
                this.bcPlayer.sendBCMsg(bcMsgNewTableCancel);
                this.bcWaitGameB.exit();
                break;

            case "BCMsgGetObserverGameResponse":    // Сообщение: Игра: ответ

                BCMsgGetObserverGameResponse bcMsgGetObserverGameResponse = (BCMsgGetObserverGameResponse) o;
                BCMsgGameResponse bcMsgGameResponse = bcMsgGetObserverGameResponse.getBCMsgGameResponse();

                // удаляю сообщения, которые будут обрабатываться следующим объектом
                this.bcWaitGameB.getBCMsgGet().removeBCMsg("BCMsgGameEnd");
                this.bcWaitGameB.getBCMsgGet().removeBCMsg("BCMsgTurn");
                this.bcWaitGameB.getBCMsgGet().removeBCMsg("BCMsgTime");

                this.bcPlayer.sendBCMsg(bcMsgGameResponse);

                BCGame bcGame = new BCGame(this.bcTable, bcMsgGameResponse.getNumberA(), bcMsgGameResponse.getNumberB());
                BCGameBClient bcGameBClient = new BCGameBClient(null, bcGame, this.bcWaitGameB.getBCMsgGet(), this.bcTable.getBCPlayerB().getBCMsgSend());
                Thread thread = new Thread(bcGameBClient);
                thread.start();

                this.bcWaitGameB.prepareClose();
                this.bcWaitGameB.exit();
                break;

        }
    
    }
    
}
