/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bcclient;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Слушатель объекта "Создать стол"
 * @author iMacAverage
 */
public class BCNewTableWindowAdapter extends WindowAdapter {
    
    /**
     * объект "Модель объекта 'Создать стол'"
     */
    private final BCNewTableModel bcNewTableModel;
    
    /**
     * Создать объект
     * @param bcNewTableModel объект "Модель объекта 'Создать стол'"
     */
    public BCNewTableWindowAdapter(BCNewTableModel bcNewTableModel) {
        this.bcNewTableModel = bcNewTableModel;
    }
    
    @Override
    public void windowClosing(WindowEvent e) {
        this.bcNewTableModel.prepareClose();
    }
    
}
