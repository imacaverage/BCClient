/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bcclient;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Слушатель объекта "Ожидание игры: игрок А"
 * @author iMacAverage
 */
public class BCWaitGameAWindowAdapter extends WindowAdapter {
    
    /**
     * объект "Модель объекта 'Ожидание игры: игрок А'"
     */
    private final BCWaitGameAModel bcWaitGameAModel;
    
    /**
     * Создать объект
     * @param bcWaitGameAModel объект "Модель объекта 'Ожидание игры: игрок А'"
     */
    public BCWaitGameAWindowAdapter(BCWaitGameAModel bcWaitGameAModel) {
        this.bcWaitGameAModel = bcWaitGameAModel;
    }
    
    @Override
    public void windowClosing(WindowEvent e) {
        this.bcWaitGameAModel.prepareClose();
    }
    
}
