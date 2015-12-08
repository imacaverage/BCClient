/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bcclient;

import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 * Класс "Представление объекта 'Логирование'"
 * Наблюдатель объекта "Модель объекта 'Логирование'"
 * @author iMacAverage
 */
public class BCLoginView extends JPanel implements Observer {
    
    /**
     * объект "Модель объекта 'Логирование'"
     */
    private final BCLoginModel bcLoginModel;

    /**
     * кнопка для входа
     */
    private final JButton jEnter;
    
    /**
     * кнопка для создания игрока
     */
    private final JButton jNew;
    
    /** 
     * логин игрока 
     */
    private final JTextField jLogin;

    /** 
     * пароль игрока
     */
    private final JPasswordField jPassword;
    
    /**
     * Создать объект
     * @param bcLoginModel объект "Модель объекта 'Логирование'"
     */
    public BCLoginView(BCLoginModel bcLoginModel) {
        super();
        this.bcLoginModel = bcLoginModel;
        this.setLayout(new GridBagLayout());
        
        GridBagConstraints gbCon = new GridBagConstraints();

        gbCon.gridx = 0;    // первый столбец
        gbCon.gridy = 0;    // первый ряд
        gbCon.anchor = GridBagConstraints.WEST;
        gbCon.fill = GridBagConstraints.NONE;   
        gbCon.weightx = 0.3;  // для соотношения ячеек
        gbCon.weighty = 1;
        gbCon.insets = new Insets(12, 12, 6, 6);    // отступ сверху, слева, снизу, справа
        //gbCon.ipadx = 6;
        //gbCon.ipady = 6;
        this.add(new JLabel("Player:"), gbCon);
        
        gbCon.gridx = 0;
        gbCon.gridy = 1;
        gbCon.anchor = GridBagConstraints.WEST;
        gbCon.fill = GridBagConstraints.NONE;
        gbCon.weightx = 0.3;
        gbCon.weighty = 1;
        gbCon.insets = new Insets(6, 12, 12, 6);    // отступ сверху, слева, снизу, справа
        this.add(new JLabel("Password:"), gbCon);

        gbCon.gridx = 1;
        gbCon.gridy = 0;
        gbCon.anchor = GridBagConstraints.WEST;
        gbCon.fill = GridBagConstraints.HORIZONTAL;
        gbCon.weightx = 1;
        gbCon.weighty = 1;
        gbCon.insets = new Insets(12, 6, 6, 12);    // отступ сверху, слева, снизу, справа
        jLogin = new JTextField(10);
        this.add(jLogin, gbCon);

        gbCon.gridx = 1;
        gbCon.gridy = 1;
        gbCon.anchor = GridBagConstraints.WEST;
        gbCon.fill = GridBagConstraints.HORIZONTAL;
        gbCon.weightx = 1;
        gbCon.weighty = 1;
        gbCon.insets = new Insets(6, 6, 6, 12); // отступ сверху, слева, снизу, справа
        jPassword = new JPasswordField(10);
        this.add(jPassword, gbCon);
        
        jEnter = new JButton("Login");
        jNew = new JButton("New");
        
        JPanel jPGridButton = new JPanel(new GridLayout(1, 2, 5, 0));
        jPGridButton.add(jEnter);
        jPGridButton.add(jNew);
       
        JPanel jPFlowButton = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        jPFlowButton.add(jPGridButton);

        gbCon.gridx = 0;
        gbCon.gridy = 2;
        gbCon.gridwidth = 2;
        gbCon.anchor = GridBagConstraints.CENTER;
        gbCon.fill = GridBagConstraints.NONE;
        gbCon.weightx = 1;
        gbCon.weighty = 1;
        gbCon.insets = new Insets(6, 12, 12, 12);   // отступ сверху, слева, снизу, справа
        this.add(jPFlowButton, gbCon);

    }

    /**
     * Получить логин
     * @return логин
     */
    public String getLogin() {
        return this.jLogin.getText();
    }
    
    /**
     * Получить пароль
     * @return пароль
     */
    public String getPassword() {
        return String.valueOf(this.jPassword.getPassword());
    }

    /**
     * Контроль ввода значений
     * @return true если успешно, иначе false
     */
    public boolean checkInput() {
        if(this.jLogin.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Enter Player!", "Error", JOptionPane.ERROR_MESSAGE);
            this.jLogin.requestFocus();
            return false;
        }
        if(String.valueOf(this.jPassword.getPassword()).isEmpty()) {
            JOptionPane.showMessageDialog(null, "Enter Password!", "Error", JOptionPane.ERROR_MESSAGE);
            this.jPassword.requestFocus();
            return false;
        }
        return true;
    }
    
    /**
     * Добавить слушателя
     * @param l слушатель
     */
    public void addActionListener(ActionListener l) {
        this.jEnter.addActionListener(l);
        this.jNew.addActionListener(l);
    }
    
    @Override
    public void addKeyListener(KeyListener l) {
        this.jLogin.addKeyListener(l);
        this.jPassword.addKeyListener(l);
    }
        
    @Override
    public void update(Observable o, Object arg) {
        this.jEnter.setEnabled(this.bcLoginModel.getEnterEnabled());
        this.jNew.setEnabled(this.bcLoginModel.getNewEnabled());
    }
    
}
