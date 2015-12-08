/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bcclient;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JTextField;

/**
 * Слушатель ввода в текстовое поле: только заданное количество цифр
 * @author iMacAverage
 */
public class BCKeyAdapterDigits extends KeyAdapter {
    
    /**
     * максимальное количество цифр
     */    
    private final int maxCountDigits;
    
    /**
     * возможность повторения цифр
     */
    private final boolean withRepeat;
    
    /**
     * Создать объект
     * @param maxCountDigits максимальное количество цифр
     * @param withRepeat возможность повторения цифр
     */
    public BCKeyAdapterDigits(int maxCountDigits, boolean withRepeat) {
        this.maxCountDigits = maxCountDigits;
        this.withRepeat = withRepeat;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        char c = e.getKeyChar();
        JTextField field = (JTextField) e.getSource();
        if(c != KeyEvent.VK_BACK_SPACE && c != KeyEvent.VK_DELETE)
            if(!Character.isDigit(c) || (field.getText().indexOf(c) != -1 && !this.withRepeat))
                e.consume();
    }			
    
}
