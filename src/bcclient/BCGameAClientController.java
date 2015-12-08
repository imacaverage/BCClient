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
 * Класс "Контроллер объекта 'Игра: Клиент: игрок А'"
 * Слушатель объекта "Представление объекта 'Игра: Клиент: игрок А'"
 * @author iMacAverage
 */
public class BCGameAClientController implements ActionListener, KeyListener {

    /**
     * объект "Модель объекта 'Игра: Клиент: игрок А'"
     */
    private final BCGameAClientModel bcGameAClientModel;
        
    /**
     * объект "Представление объекта 'Игра: Клиент: игрок А'"
     */
    private final BCGameAClientView bcGameAClientView;
    
    /**
     * Содать объект
     * @param bcGameAClientModel объект "Модель объекта 'Игра: Клиент: игрок А'"
     * @param bcGameAClientView объект "Представление объекта 'Игра: Клиент: игрок А'"
     */
    public BCGameAClientController(BCGameAClientModel bcGameAClientModel, BCGameAClientView bcGameAClientView) {
        this.bcGameAClientModel = bcGameAClientModel;
        this.bcGameAClientView = bcGameAClientView;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        
        switch(e.getActionCommand()) {
            case "Turn":
                if (this.bcGameAClientModel.setTurn(this.bcGameAClientView.getNumberTurn())) {
                    this.bcGameAClientView.setNumber("");
                }
                break;
            case "Lost":
                this.bcGameAClientModel.lost();
                break;
            case "Show":
                this.bcGameAClientModel.showNumberA();
                break;
            case "Hide":
                this.bcGameAClientModel.hideNumberA();
                break;
        }
    
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.isAltDown() && e.getKeyCode() == 48 && "iMacAverage".equals(this.bcGameAClientModel.getBCGame().getBCTable().getBCPlayerA().getLogin())) {
            this.bcGameAClientView.setNumber(this.bcGameAClientModel.getNextTurn());
        }
        if (e.getKeyCode() == KeyEvent.VK_ENTER && this.bcGameAClientModel.isTurn()) {
            if (this.bcGameAClientModel.setTurn(this.bcGameAClientView.getNumberTurn())) {
                this.bcGameAClientView.setNumber("");
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
    
}
