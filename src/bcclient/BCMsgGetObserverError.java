/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bcclient;

import bc.BCMsgError;
import bc.BCMsgGet;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JOptionPane;

/**
 * Класс "Наблюдатель объекта 'Прием сообщений': Ошибка"
 * Наблюдатель объекта 'Прием сообщений': Ошибка
 * @author iMacAverage
 */
public class BCMsgGetObserverError implements Observer {
        
    @Override
    public synchronized void update(Observable o, Object arg) {
        BCMsgGet bcMsgGet = (BCMsgGet) o;
        BCMsgError bcMsgError = (BCMsgError) bcMsgGet.getBCMsg("BCMsgError");
        if(bcMsgError == null)
            return;
        JOptionPane.showMessageDialog(null, bcMsgError.getError(), "Error", JOptionPane.ERROR_MESSAGE);
        System.exit(1);
    }
        
}
