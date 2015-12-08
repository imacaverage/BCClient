/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bcclient;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Слушатель объекта "Ожидание игры: игрок Б"
 * @author iMacAverage
 */
public class BCWaitGameBWindowAdapter extends WindowAdapter {
    
    /**
     * объект "Модель объекта 'Ожидание игры: игрок Б'"
     */
    private final BCWaitGameBModel bcWaitGameBModel;
    
    /**
     * Создать объект
     * @param bcWaitGameBModel объект "Модель объекта 'Ожидание игры: игрок Б'"
     */
    public BCWaitGameBWindowAdapter(BCWaitGameBModel bcWaitGameBModel) {
        this.bcWaitGameBModel = bcWaitGameBModel;
    }
    
    @Override
    public void windowClosing(WindowEvent e) {
        this.bcWaitGameBModel.prepareClose();
    }
    
}
