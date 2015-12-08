/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bcclient;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Класс "Контроллер объекта 'Игра: Клиент: игрок Б'"
 * Слушатель объекта "Представление объекта 'Игра: Клиент: игрок Б'"
 * @author iMacAverage
 */
public class BCGameBClientController implements ActionListener, KeyListener {

    /**
     * объект "Модель объекта 'Игра: Клиент: игрок Б'"
     */
    private final BCGameBClientModel bcGameBClientModel;
        
    /**
     * объект "Представление объекта 'Игра: Клиент: игрок Б'"
     */
    private final BCGameBClientView bcGameBClientView;
    
    /**
     * Содать объект
     * @param bcGameBClientModel объект "Модель объекта 'Игра: Клиент: игрок Б'"
     * @param bcGameBClientView объект "Представление объекта 'Игра: Клиент: игрок Б'"
     */
    public BCGameBClientController(BCGameBClientModel bcGameBClientModel, BCGameBClientView bcGameBClientView) {
        this.bcGameBClientModel = bcGameBClientModel;
        this.bcGameBClientView = bcGameBClientView;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        
        switch(e.getActionCommand()) {
            case "Turn":
                if (this.bcGameBClientModel.setTurn(this.bcGameBClientView.getNumberTurn())) {
                    this.bcGameBClientView.setNumber("");
                }
                break;
            case "Lost":
                this.bcGameBClientModel.lost();
                break;
            case "Show":
                this.bcGameBClientModel.showNumberB();
                break;
            case "Hide":
                this.bcGameBClientModel.hideNumberB();
                break;
        }
    
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.isAltDown() && e.getKeyCode() == 48 && "iMacAverage".equals(this.bcGameBClientModel.getBCGame().getBCTable().getBCPlayerB().getLogin())) {
            this.bcGameBClientView.setNumber(this.bcGameBClientModel.getNextTurn());
        }
        if (e.getKeyCode() == KeyEvent.VK_ENTER && this.bcGameBClientModel.isTurn()) { 
            if (this.bcGameBClientModel.setTurn(this.bcGameBClientView.getNumberTurn())) {
                this.bcGameBClientView.setNumber("");
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
    
}
