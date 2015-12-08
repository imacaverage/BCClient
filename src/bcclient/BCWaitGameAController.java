/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bcclient;

import bc.Comb;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Класс "Контроллер объекта 'Ожидание игры: игрок А'"
 * Слушатель объекта "Представление объекта 'Ожидание игры: игрок А'"
 * @author iMacAverage
 */
public class BCWaitGameAController implements ActionListener, KeyListener {

    /**
     * объект "Модель объекта 'Ожидание игры: игрок А'"
     */
    private final BCWaitGameAModel bcWaitGameAModel;
        
    /**
     * объект "Представление объекта 'Ожидание игры: игрок А'"
     */
    private final BCWaitGameAView bcWaitGameAView;
    
    /**
     * Содать объект
     * @param bcWaitGameAModel объект "Модель объекта 'Ожидание игры: игрок А'"
     * @param bcWaitGameAView объект "Представление объекта 'Ожидание игры: игрок А'"
     */
    public BCWaitGameAController(BCWaitGameAModel bcWaitGameAModel, BCWaitGameAView bcWaitGameAView) {
        this.bcWaitGameAModel = bcWaitGameAModel;
        this.bcWaitGameAView = bcWaitGameAView;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        
        switch(e.getActionCommand()) {
            case "Play":
                if (this.bcWaitGameAModel.playWaitGameA(this.bcWaitGameAView.getNumber())) {
                    this.bcWaitGameAView.setNumber(this.bcWaitGameAView.getNumber().replaceAll("\\d", "*"));
                }
                break;
            case "Kick":
                this.bcWaitGameAModel.kickPlayerB();
                break;
            case "Cancel":
                this.bcWaitGameAModel.cancelNewTable();
                break;
        }
    
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int numberLength = this.bcWaitGameAModel.getBCTable().getNumberLength();
        boolean withRepeat = this.bcWaitGameAModel.getBCTable().getWithRepeat();
        char[] seq = new char [numberLength];
        if (e.isAltDown() && e.getKeyCode() == 48) {
            this.bcWaitGameAView.setNumber(String.valueOf(Comb.seqRandom(seq, withRepeat)));
        }
        if (e.getKeyCode() == KeyEvent.VK_ENTER && this.bcWaitGameAModel.getPlayEnabled()) {
            if (this.bcWaitGameAModel.playWaitGameA(this.bcWaitGameAView.getNumber())) {
                this.bcWaitGameAView.setNumber(this.bcWaitGameAView.getNumber().replaceAll("\\d", "*"));
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
    
}
