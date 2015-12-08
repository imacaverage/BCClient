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
 * Класс "Контроллер объекта 'Ожидание игры: игрок Б'"
 * Слушатель объекта "Представление объекта 'Ожидание игры: игрок Б'"
 * @author iMacAverage
 */
public class BCWaitGameBController implements ActionListener, KeyListener {

    /**
     * объект "Модель объекта 'Ожидания игры: игрок Б'"
     */
    private final BCWaitGameBModel bcWaitGameBModel;
    
    /**
     * объект "Представление объекта 'Ожидание игры: игрок Б'"
     */
    private final BCWaitGameBView bcWaitGameBView;
    
    /**
     * Создать объект
     * @param bcWaitGameBModel объект "Модель объекта 'Ожидания игры: игрок Б'"
     * @param bcWaitGameBView объект "Представление объекта 'Ожидание игры: игрок Б'"
     */
    public BCWaitGameBController(BCWaitGameBModel bcWaitGameBModel, BCWaitGameBView bcWaitGameBView) {
        this.bcWaitGameBModel = bcWaitGameBModel;
        this.bcWaitGameBView = bcWaitGameBView;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        switch(e.getActionCommand()) {
            case "Play":
                if (this.bcWaitGameBModel.playWaitGameB(this.bcWaitGameBView.getNumber())) {
                    this.bcWaitGameBView.setNumber(this.bcWaitGameBView.getNumber().replaceAll("\\d", "*"));
                }
                break;
            case "Cancel":
                this.bcWaitGameBModel.cancelWaitGameB();
                break;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int numberLength = this.bcWaitGameBModel.getBCTable().getNumberLength();
        boolean withRepeat = this.bcWaitGameBModel.getBCTable().getWithRepeat();
        char[] seq = new char [numberLength];
        if (e.isAltDown() && e.getKeyCode() == 48) {
            this.bcWaitGameBView.setNumber(String.valueOf(Comb.seqRandom(seq, withRepeat)));
        }
        if (e.getKeyCode() == KeyEvent.VK_ENTER && this.bcWaitGameBModel.getPlayEnabled()) {
            if (this.bcWaitGameBModel.playWaitGameB(this.bcWaitGameBView.getNumber())) {
                this.bcWaitGameBView.setNumber(this.bcWaitGameBView.getNumber().replaceAll("\\d", "*"));
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
    
}
