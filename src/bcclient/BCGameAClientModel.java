/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bcclient;

import bc.BCGame;
import bc.BCGameState;
import bc.BCMsgGameEnd;
import bc.BCMsgLost;
import bc.BCMsgSend;
import bc.BCMsgTime;
import bc.BCMsgTurn;
import bc.BCTurn;
import bc.RegEx;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JOptionPane;

/**
 * Класс "Модель объекта 'Игра: Клиент: игрок А'"
 * Наблюдатель объектов:
 * - "Наблюдатель объекта 'Прием сообщений': 'Cообщение: Ход'"
 * - "Наблюдатель объекта 'Прием сообщений': 'Cообщение: Время'"
 * - "Наблюдатель объекта 'Прием сообщений': 'Cообщение: Игра: конец'"
 * Наблюдаемый объектом "Представление 'Игра: Клиент: игрок А'"
 * @author iMacAverage
 */
public class BCGameAClientModel extends Observable implements Observer {
    
    /**
     * объект "Игра: Клиент: игрок А"
     */
    private final BCGameAClient bcGameAClient;
    
    /**
     * объект "Игра"
     */
    private final BCGame bcGame;
    
    /**
     * объект "Отправка сообщений"
     */
    private final BCMsgSend bcMsgSend;

    /**
     * состояние хода
     */
    private boolean isTurn;
    
    /**
     * доступность сдаться
     */
    private boolean lostEnabled;
    
    /**
     * время игрока А (секунд)
     */
    private int timeA;
    
    /**
     * время игрока Б (секунд)
     */
    private int timeB;
    
    /**
     * представление числа игрока А
     */
    private String viewNumberA;

    /**
     * Создать объект
     * @param bcGameAClient объект "Игра: Клиент: игрок А"
     * @param bcGame объект "Игра"
     * @param bcMsgSend объект "Отправка сообщений"
     */
    public BCGameAClientModel(BCGameAClient bcGameAClient, BCGame bcGame, BCMsgSend bcMsgSend) {
        this.bcGameAClient = bcGameAClient;
        this.bcGame = bcGame;
        this.bcMsgSend = bcMsgSend;
        this.isTurn = true;
        this.lostEnabled = true;
        this.timeA = this.bcGame.getBCTable().getCountMin() * 60;
        this.timeB = this.bcGame.getBCTable().getCountMin() * 60;
        this.viewNumberA = String.format("%0" + this.bcGame.getNumberA().length() + "d", 0).replace("0", "*"); 
    }

    /**
     * Получить состояние хода
     * @return состояние хода
     */
    public boolean isTurn() {
        return this.isTurn;
    }
    
    /**
     * Получить доступность сдаться
     * @return доступность сдаться
     */
    public boolean getLostEnabled() {
        return this.lostEnabled;
    }

    /**
     * Получить объект "Игра"
     * @return объект "Игра"
     */
    public BCGame getBCGame() {
        return this.bcGame;
    }
    
    /**
     * Получить время игрока А (секунд)
     * @return время игрока А (секунд)
     */
    public synchronized int getTimeA() {
        return this.timeA;
    }
    
    /**
     * Задать время игрока А (секунд)
     * @param timeA время игрока А (секунд)
     */
    public synchronized void setTimeA(int timeA) {
        this.timeA = timeA;
    }
    
    /**
     * Получить время игрока Б (секунд)
     * @return время игрока Б (секунд)
     */
    public synchronized int getTimeB() {
        return this.timeB;
    }
    
    /**
     * Задать время игрока Б (секунд)
     * @param timeB время игрока Б (секунд)
     */
    public synchronized void setTimeB(int timeB) {
        this.timeB = timeB;
    }

    /**
     * Контроль корректности ввода числа хода
     * @param number число хода
     * @return true в случае успеха, иначе false
     */
    public boolean checkInputNumber(String number) {
        int n = this.bcGame.getBCTable().getNumberLength();
        String regex = RegEx.digitsWithRepetition(n);
        String message = "Incorrect input, enter " + n + " digits";
        if(!number.matches(regex)) {
            JOptionPane.showMessageDialog(null, message , "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }
    
    /**
     * Выполнить ход
     * @param number число хода
     * @return true в случае успеха, иначе false
     */
    public boolean setTurn(String number) {
        if(this.bcGame.getState() == BCGameState.PLAY && this.checkInputNumber(number)) {
            BCTurn bcTurn = new BCTurn(number, this.bcGame.getNumberB());
            BCMsgTurn bcMsgTurn = new BCMsgTurn(bcTurn);
            this.bcMsgSend.sendBCMsg(bcMsgTurn);
            this.bcGame.turnPlayerA(number);
            this.isTurn = false;
            this.setChanged();
            this.notifyObservers();
            return true;
        }
        return false;
    }    
    
    /**
     * Сдаться
     * @return true в случае успеха, иначе false
     */
    public boolean lost() {
        if (JOptionPane.showConfirmDialog(null, "Are you sure you want to lost?") == JOptionPane.YES_OPTION) {
            this.lostEnabled = false;
            this.setChanged();
            this.notifyObservers();
            BCMsgLost bcMsgLost = new BCMsgLost();
            this.bcMsgSend.sendBCMsg(bcMsgLost);
            return true;
        }
        return false;
    }
    
    /**
     * Подготовить данные перед закрытием диалога
     * @return true в случае успеха, иначе false
     */
    public boolean prepareClose() {
        if (this.bcGame.getState() == BCGameState.PLAY)
            if (!this.lost())
                return false;
        this.bcGameAClient.prepareClose();
        return true;
    }

    /**
     * Показать число игрока А
     */
    public void showNumberA() {
        this.viewNumberA = this.bcGame.getNumberA(); 
        this.setChanged();
        this.notifyObservers();
    }
    
    /**
     * Скрыть число игрока А
     */
    public void hideNumberA() {
        this.viewNumberA = String.format("%0" + this.bcGame.getNumberA().length() + "d", 0).replace("0", "*"); 
        this.setChanged();
        this.notifyObservers();
    }
    
    /**
     * Получить представление числа игрока А
     * @return 
     */
    public String getViewNumberA() {
        return this.viewNumberA;
        
    }
    
    /**
     * Получить число следующего хода
     * @return число следующего хода
     */
    public String getNextTurn() {
        return this.bcGame.getNextTurn(this.bcGame.getTurnsA());
    }
        
    @Override
    public void update(Observable o, Object arg) {

        switch(o.getClass().getSimpleName()) {

            case "BCMsgGetObserverTurn":    // Сообщение: Ход
        
                BCMsgGetObserverTurn bcMsgGetObserverTurn = (BCMsgGetObserverTurn) o;        
                BCMsgTurn bcMsgTurn = bcMsgGetObserverTurn.getBCMsgTurn();
                BCTurn bcTurn = bcMsgTurn.getBCTurn();
                this.bcGame.turnPlayerB(bcTurn.getNumber());
                this.isTurn = true;
                this.setChanged();
                this.notifyObservers();
                break;
        
            case "BCMsgGetObserverGameEnd":    // Сообщение: Игра: конец

                BCMsgGetObserverGameEnd bcMsgGetObserverGameEnd = (BCMsgGetObserverGameEnd) o;        
                BCMsgGameEnd bcMsgGameEnd = bcMsgGetObserverGameEnd.getBCMsgGameEnd();
                
                this.bcGame.setState(bcMsgGameEnd.getBCGameState());

                String message;
                
                switch(bcMsgGameEnd.getBCGameState()) {
                    case WON:
                        message = "You won!";
                        break;
                    case WON_TIME:    
                        message = "You won time!";
                        break;
                    case WON_LOST:    
                        message = "You won! PlayerB lost! PlayerB number: " + this.bcGame.getNumberB();
                        break;
                    case LOST:
                        message = "You lost! PlayerB number: " + this.bcGame.getNumberB();
                        break;
                    case LOST_TIME:    
                        message = "You lost time! PlayerB number: " + this.bcGame.getNumberB();
                        break;
                    case LOST_LOST:    
                        message = "You lost! PlayerB number: " + this.bcGame.getNumberB();
                        break;
                    case DRAW:
                        message = "Draw!";
                        break;
                    default: 
                        message = "";
                        break;
                }
                
                Thread t = new Thread(new Runnable(){
                    public void run(){
                        JOptionPane.showMessageDialog(null, message, "Game Over", JOptionPane.INFORMATION_MESSAGE);
                    }
                });
                t.start();            

                this.isTurn = false;
                this.lostEnabled = false;
                this.setChanged();
                this.notifyObservers();

                break;
                
            case "BCMsgGetObserverTime":    // Сообщение: Время
                
                BCMsgGetObserverTime bcMsgGetObserverTime = (BCMsgGetObserverTime) o;
                BCMsgTime bcMsgTime = bcMsgGetObserverTime.getBCMsgTime();
                this.setTimeA(bcMsgTime.getTimeA());
                this.setTimeB(bcMsgTime.getTimeB());
                this.setChanged();
                this.notifyObservers();
                break;
        
        }        
    
    }
    
}
