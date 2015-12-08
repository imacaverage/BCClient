/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bcclient;

import bc.BCMsgGet;
import bc.BCMsgSitTableRequest;
import bc.BCMsgSitTableResponse;
import bc.BCPlayer;
import bc.BCRoom;
import bc.BCTable;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JOptionPane;

/**
 * Класс "Модель объекта 'Клиент'"
 * наблюдатель:
 * - объекта "Прием сообщений": "Игровая комната"
 * - объекта "Прием сообщений": "Сесть за стол: ответ"
 * наблюдаемый объектом "Представление объекта 'Клиент'"
 * @author iMacAverage
 */
public class BCClientModel extends Observable implements Observer {
    
    /**
     * объект "Игрок"
     */
    private final BCPlayer bcPlayer;

    /**
     * объект "Прием сообщений"
     */
    private final BCMsgGet bcMsgGet;

    /**
     * доступность создания нового стола
     */
    private boolean NewTableEnabled;
    
    /**
     * доступность возможности сесть за стол
     */
    private boolean SitTableEnabled;
    
    /**
     * коллекция объектов "Игрок"
     */
    private ArrayList<BCPlayer> bcPlayers;
    
    /**
     * коллекция объектов "Стол"
     */
    private ArrayList<BCTable> bcTables;
    
    /**
     * индекс выбранного стола
     */
    private int indexSelectedTable;

    /**
     * Создать объект
     * @param bcPlayer объект "Игрок"
     * @param bcMsgGet объект "Прием сообщений"
     */
    public BCClientModel(BCPlayer bcPlayer, BCMsgGet bcMsgGet) { 
        this.bcPlayer = bcPlayer;
        this.bcMsgGet = bcMsgGet;
        this.NewTableEnabled = true;
        this.SitTableEnabled = false;
        this.bcPlayers = null;
        this.bcTables = null;
    }

    /**
     * Получить объект "Игрок"
     * @return объект "Игрок"
     */
    public BCPlayer getPlayer() {
        return this.bcPlayer;
    }
        
    /**
     * Получить коллекцию объектов "Игрок"
     * @return коллекцию объектов "Игрок"
     */
    public ArrayList<BCPlayer> getBCPlayers() {
        return this.bcPlayers;
    }
            
    /**
     * Получить коллекцию объектов "Стол"
     * @return коллекцию объектов "Стол"
     */
    public ArrayList<BCTable> getBCTables() {
        return this.bcTables;
    }
    
    /**
     * Задать доступность создания нового стола
     * @param b доступность создания нового стола
     */
    public void setNewTableEnabled(boolean b) {
        this.NewTableEnabled = b;
        this.setChanged();
        this.notifyObservers("NewTable");
    }
    
    /**
     * Задать доступность возможности сесть за стол
     * @param b доступность возможности сесть за стол
     */
    public void setSitTableEnabled(boolean b) {
        this.SitTableEnabled = b;
        this.setChanged();
        this.notifyObservers("SitTable");
    }
    
    /**
     * Получить доступность создания нового стола
     * @return доступность создания нового стола
     */
    public boolean getNewTableEnabled() {
        return this.NewTableEnabled;
    }
    
    /**
     * Получить доступность возможности сесть за стол
     * @return доступность возможности сесть за стол
     */
    public boolean getSitTableEnabled() {
        return this.SitTableEnabled;
    }
    
    /**
     * Задать индекс выбранного стола
     * @param indexSelectedTable индекс выбранного стола
     */
    public void setIndexSelectedTable(int indexSelectedTable) {
        this.indexSelectedTable = indexSelectedTable;        
    }

    /**
     * Создать стол
     */
    public void newTable() {
        BCNewTable bcNewTable = new BCNewTable(null, this.bcPlayer, this.bcMsgGet);
        Thread thread = new Thread(bcNewTable);
        thread.start();
    }

    /**
     * Отправить объект "Сообщение: Сесть за стол: запрос"
     */
    public void sitTable() {
        BCMsgSitTableRequest bcMsgSitTableRequest = new BCMsgSitTableRequest(this.bcPlayer, this.bcTables.get(indexSelectedTable));
        this.bcPlayer.sendBCMsg(bcMsgSitTableRequest);
    }
    
    @Override
    public void update(Observable o, Object arg) {

        switch(o.getClass().getSimpleName()) {

            case "BCMsgGetObserverRoom":    // Сообщение: Игровая комната

                BCMsgGetObserverRoom bcMsgGetObserverRoom = (BCMsgGetObserverRoom) o;
                BCRoom bcRoom = bcMsgGetObserverRoom.getBCMsgRoom().getBCRoom();
                this.bcPlayers = bcRoom.getBCPlayers();
                this.bcTables = bcRoom.getBCTables();
                // обновляю рейтинг игрока
                BCPlayer bcPlayer = bcRoom.findPlayer(this.bcPlayer.getLogin());
                if (bcPlayer != null)
                    this.bcPlayer.setRating(bcPlayer.getRating());
                this.setChanged();
                this.notifyObservers("Room");
                break;

            case "BCMsgGetObserverSitTableResponse":    // Сообщение: Сесть за стол: ответ
                
                BCMsgGetObserverSitTableResponse bcMsgGetObserverSitTableResponse = (BCMsgGetObserverSitTableResponse) o;
                BCMsgSitTableResponse bcMsgSitTableResponse = bcMsgGetObserverSitTableResponse.getBCMsgSitTableResponse();
                String error = bcMsgSitTableResponse.getError();
                if(error != null) {
                    Thread thread = new Thread(new Runnable(){
                        public void run(){
                            JOptionPane.showMessageDialog(null, error, "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    });
                    thread.start();    
                    break;                    
                } 
                
                BCTable t = bcMsgSitTableResponse.getBCTable();
                BCTable bcTable = new BCTable(t.getNumTable(), t.getBCPlayerA(), t.getMinRating(), t.getNumberLength(), t.getWithRepeat(), t.getCountMin());
                bcTable.setBCPlayerB(new BCPlayer(this.bcPlayer.getLogin(), this.bcPlayer.getRating(), this.bcPlayer.getBCMsgSend()));

                // удаляю сообщения, которые будут обрабатываться следующим объектом
                this.bcMsgGet.removeBCMsg("BCMsgGameResponse");
                this.bcMsgGet.removeBCMsg("BCMsgNewTableCancel");
                this.bcMsgGet.removeBCMsg("BCMsgSitTableKick");

                BCWaitGameB bcWaitGameB = new BCWaitGameB(null, this.bcPlayer, bcTable, this.bcMsgGet);
                Thread thread = new Thread(bcWaitGameB);
                thread.start();
                break;

        }
        
    }

}
