/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bcclient;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Слушатель объекта "Игра: Клиент: игрок А"
 * @author iMacAverage
 */
public class BCGameAClientWindowAdapter extends WindowAdapter {
    
    /**
     * объект "Модель объекта 'Игра: Клиент: игрок А'"
     */
    private final BCGameAClientModel bcGameAClientModel;
    
    /**
     * Создать объект
     * @param bcGameAClientModel объект "Модель объекта 'Игра: Клиент: игрок А'"
     */
    public BCGameAClientWindowAdapter(BCGameAClientModel bcGameAClientModel) {
        this.bcGameAClientModel = bcGameAClientModel;
    }
    
    @Override
    public void windowClosing(WindowEvent e) {
        if (this.bcGameAClientModel.prepareClose())
            ((BCGameAClient) e.getSource()).dispose();
    }
    
}
