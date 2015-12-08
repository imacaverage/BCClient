/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bcclient;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Слушатель объекта "Игра: Клиент: игрок Б"
 * @author iMacAverage
 */
public class BCGameBClientWindowAdapter extends WindowAdapter {
    
    /**
     * объект "Модель объекта 'Игра: Клиент: игрок Б'"
     */
    private final BCGameBClientModel bcGameBClientModel;
    
    /**
     * Создать объект
     * @param bcGameBClientModel объект "Модель объекта 'Игра: Клиент: игрок Б'"
     */
    public BCGameBClientWindowAdapter(BCGameBClientModel bcGameBClientModel) {
        this.bcGameBClientModel = bcGameBClientModel;
    }
    
    @Override
    public void windowClosing(WindowEvent e) {
        if (this.bcGameBClientModel.prepareClose())
            ((BCGameBClient) e.getSource()).dispose();
    }
    
}
