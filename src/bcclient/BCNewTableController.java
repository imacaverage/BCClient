/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bcclient;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Класс "Контроллер объекта 'Создать стол'"
 * @author iMacAverage
 */
public class BCNewTableController implements ActionListener {
    
    /**
     * объект "Модель объекта 'Создать стол'"
     */
    private final BCNewTableModel bcNewTableModel;
    
    /**
     * объект "Представление объекта 'Создать стол'"
     */
    private final BCNewTableView bcNewTableView;

    public BCNewTableController(BCNewTableModel bcNewTableModel, BCNewTableView bcNewTableView) {
        this.bcNewTableModel = bcNewTableModel;
        this.bcNewTableView = bcNewTableView;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
                
        if(e.getActionCommand().equals("OK")) {
            
            int minRating = this.bcNewTableView.getMinRating();
            int numberLength = this.bcNewTableView.getNumberLength();
            int countMin = this.bcNewTableView.getCountMin();
            boolean withRepeat = this.bcNewTableView.getWithRepeat();

            this.bcNewTableModel.sendNewTable(minRating, numberLength, countMin, withRepeat);
       
        }
        else {
            this.bcNewTableModel.cancelNewTable();
        }
        
    }
    
}
