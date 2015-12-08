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
 * Класс "Контроллер объекта 'Логирование'"
 * Слушатель объекта "Представление объекта 'Логирование'"
 * @author iMacAverage
 */
public class BCLoginController implements ActionListener, KeyListener {
    
    /**
     * объект "Модель объекта 'Логирование'"
     */
    private final BCLoginModel bcLoginModel;
    
    /**
     * объект "Представление объекта 'Логирование'"
     */
    private final BCLoginView bcLoginView;

    /**
     * Создать объект
     * @param bcLoginModel объект "Модель объекта 'Логирование'"
     * @param bcLoginView объект "Представление объекта 'Логирование'"
     */
    public BCLoginController(BCLoginModel bcLoginModel, BCLoginView bcLoginView) {
        this.bcLoginModel = bcLoginModel;
        this.bcLoginView = bcLoginView;
    }
            
    @Override
    public void actionPerformed(ActionEvent e) {
        if(this.bcLoginView.checkInput())
            this.bcLoginModel.setLogon(this.bcLoginView.getLogin(), this.bcLoginView.getPassword(), e.getActionCommand().equals("New"));
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER && this.bcLoginView.checkInput()) {
            this.bcLoginModel.setLogon(this.bcLoginView.getLogin(), this.bcLoginView.getPassword(), false);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
    
}
