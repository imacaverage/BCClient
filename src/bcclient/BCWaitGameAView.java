/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bcclient;

import bc.BCPlayer;
import bc.BCTable;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * Класс "Представление объекта 'Ожидание игры: игрок А'"
 * Наблюдатель объекта "Модель объекта 'Ожидание игры: игрок А'"
 * @author iMacAverage
 */
public class BCWaitGameAView extends JPanel implements Observer  {
    
    /** 
     * кнопка запуска игры 
     */
    private final JButton jPlay;

    /** 
     * кнопка выгнать игрока со стола
     */
    private final JButton jKick;

    /** 
     * кнопка отмены 
     */
    private final JButton jCancel;
    
    /** 
     * логин игрока Б
     */
    private final JTextField jPlayerB;
    
    /** 
     * рейтинг игрока Б
     */
    private final JTextField jRatingB;
    
    /** 
     * возможность повторения цифр в числе
     */
    private final JCheckBox jWithRepeat;
    
    /**
     * число игры
     */
    private final JTextField jNumber;
    
    /**
     * Создать объект
     * @param bcWaitGameAModel объект "Модель объекта 'Ожидание игры: игрок А'"
     */
    public BCWaitGameAView(BCWaitGameAModel bcWaitGameAModel) {

        BCTable bcTable = bcWaitGameAModel.getBCTable();
        BCPlayer bcPlayerB = bcTable.getBCPlayerB();
        
        this.setLayout(new GridBagLayout());
        
        GridBagConstraints gbCon = new GridBagConstraints();

        JTextField jNumTable, jNumberLength, jCountMin, jMinRating; 

        gbCon.gridx = 0;    // столбец
        gbCon.gridy = 0;    // ряд
        gbCon.anchor = GridBagConstraints.EAST;
        gbCon.fill = GridBagConstraints.NONE;   
        gbCon.weightx = 1;  // для соотношения ячеек
        gbCon.weighty = 1;
        gbCon.insets = new Insets(12, 12, 6, 6);    // отступ сверху, слева, снизу, справа
        this.add(new JLabel("Number table:"), gbCon);

        gbCon.gridx = 1;
        gbCon.gridy = 0;
        gbCon.anchor = GridBagConstraints.WEST;
        gbCon.fill = GridBagConstraints.HORIZONTAL;
        gbCon.weightx = 1;
        gbCon.weighty = 1;
        gbCon.insets = new Insets(12, 6, 6, 12);    // отступ сверху, слева, снизу, справа
        jNumTable = new JTextField(String.valueOf(bcTable.getNumTable()), 10);
        jNumTable.setEditable(false);
        jNumTable.setBackground(null);
        this.add(jNumTable, gbCon);

        gbCon.gridx = 0;    // столбец
        gbCon.gridy = 1;    // ряд
        gbCon.anchor = GridBagConstraints.EAST;
        gbCon.fill = GridBagConstraints.NONE;   
        gbCon.weightx = 1;  // для соотношения ячеек
        gbCon.weighty = 1;
        gbCon.insets = new Insets(6, 12, 6, 6);    // отступ сверху, слева, снизу, справа
        this.add(new JLabel("Player B:"), gbCon);
    
        gbCon.gridx = 1;
        gbCon.gridy = 1;
        gbCon.anchor = GridBagConstraints.WEST;
        gbCon.fill = GridBagConstraints.HORIZONTAL;
        gbCon.weightx = 1;
        gbCon.weighty = 1;
        gbCon.insets = new Insets(6, 6, 6, 12);    // отступ сверху, слева, снизу, справа
        this.jPlayerB = new JTextField();
        this.jPlayerB.setEditable(false);
        this.jPlayerB.setBackground(null);
        this.jPlayerB.setFont(this.jPlayerB.getFont().deriveFont(Font.BOLD)); 
        
        this.add(this.jPlayerB, gbCon);

        gbCon.gridx = 0;    // столбец
        gbCon.gridy = 2;    // ряд
        gbCon.anchor = GridBagConstraints.EAST;
        gbCon.fill = GridBagConstraints.NONE;   
        gbCon.weightx = 1;  // для соотношения ячеек
        gbCon.weighty = 1;
        gbCon.insets = new Insets(6, 12, 6, 6);    // отступ сверху, слева, снизу, справа
        this.add(new JLabel("Rating B:"), gbCon);
    
        gbCon.gridx = 1;
        gbCon.gridy = 2;
        gbCon.anchor = GridBagConstraints.WEST;
        gbCon.fill = GridBagConstraints.HORIZONTAL;
        gbCon.weightx = 1;
        gbCon.weighty = 1;
        gbCon.insets = new Insets(6, 6, 6, 12);    // отступ сверху, слева, снизу, справа
        this.jRatingB = new JTextField();
        this.jRatingB.setEditable(false);
        this.jRatingB.setBackground(null);
        this.jRatingB.setFont(this.jRatingB.getFont().deriveFont(Font.BOLD)); 
        this.add(this.jRatingB, gbCon);

        gbCon.gridx = 0;    // столбец
        gbCon.gridy = 3;    // ряд
        gbCon.anchor = GridBagConstraints.EAST;
        gbCon.fill = GridBagConstraints.NONE;   
        gbCon.weightx = 1;  // для соотношения ячеек
        gbCon.weighty = 1;
        gbCon.insets = new Insets(6, 12, 6, 6);    // отступ сверху, слева, снизу, справа
        this.add(new JLabel("Number length:"), gbCon);
    
        gbCon.gridx = 1;
        gbCon.gridy = 3;
        gbCon.anchor = GridBagConstraints.WEST;
        gbCon.fill = GridBagConstraints.HORIZONTAL;
        gbCon.weightx = 1;
        gbCon.weighty = 1;
        gbCon.insets = new Insets(6, 6, 6, 12);    // отступ сверху, слева, снизу, справа
        jNumberLength = new JTextField(String.valueOf(bcTable.getNumberLength()));
        jNumberLength.setEditable(false);
        jNumberLength.setBackground(null);
        this.add(jNumberLength, gbCon);

        gbCon.gridx = 0;    // столбец
        gbCon.gridy = 4;    // ряд
        gbCon.anchor = GridBagConstraints.EAST;
        gbCon.fill = GridBagConstraints.NONE;   
        gbCon.weightx = 1;  // для соотношения ячеек
        gbCon.weighty = 1;
        gbCon.insets = new Insets(6, 12, 6, 6);    // отступ сверху, слева, снизу, справа
        this.add(new JLabel("Time (minutes):"), gbCon);
    
        gbCon.gridx = 1;
        gbCon.gridy = 4;
        gbCon.anchor = GridBagConstraints.WEST;
        gbCon.fill = GridBagConstraints.HORIZONTAL;
        gbCon.weightx = 1;
        gbCon.weighty = 1;
        gbCon.insets = new Insets(6, 6, 6, 12);    // отступ сверху, слева, снизу, справа
        jCountMin = new JTextField(String.valueOf(bcTable.getCountMin()));
        jCountMin.setEditable(false);
        jCountMin.setBackground(null);
        this.add(jCountMin, gbCon);

        gbCon.gridx = 0;    // столбец
        gbCon.gridy = 5;    // ряд
        gbCon.anchor = GridBagConstraints.EAST;
        gbCon.fill = GridBagConstraints.NONE;   
        gbCon.weightx = 1;  // для соотношения ячеек
        gbCon.weighty = 1;
        gbCon.insets = new Insets(6, 12, 6, 6);    // отступ сверху, слева, снизу, справа
        this.add(new JLabel("Min rating:"), gbCon);
    
        gbCon.gridx = 1;
        gbCon.gridy = 5;
        gbCon.anchor = GridBagConstraints.WEST;
        gbCon.fill = GridBagConstraints.HORIZONTAL;
        gbCon.weightx = 1;
        gbCon.weighty = 1;
        gbCon.insets = new Insets(6, 6, 6, 12);    // отступ сверху, слева, снизу, справа
        jMinRating = new JTextField(String.valueOf(bcTable.getMinRating()));
        jMinRating.setEditable(false);
        jMinRating.setBackground(null);
        this.add(jMinRating, gbCon);

        gbCon.gridx = 0;    // столбец
        gbCon.gridy = 6;    // ряд
        gbCon.anchor = GridBagConstraints.EAST;
        gbCon.fill = GridBagConstraints.NONE;   
        gbCon.weightx = 1;  // для соотношения ячеек
        gbCon.weighty = 1;
        gbCon.insets = new Insets(6, 12, 6, 6);    // отступ сверху, слева, снизу, справа
        this.add(new JLabel("Repeating digits:"), gbCon);
    
        gbCon.gridx = 1;
        gbCon.gridy = 6;
        gbCon.anchor = GridBagConstraints.WEST;
        gbCon.fill = GridBagConstraints.HORIZONTAL;
        gbCon.weightx = 1;
        gbCon.weighty = 1;
        gbCon.insets = new Insets(6, 6, 6, 12);    // отступ сверху, слева, снизу, справа
        this.jWithRepeat = new JCheckBox();
        this.jWithRepeat.setSelected(bcTable.getWithRepeat());
        this.jWithRepeat.setEnabled(false);
        this.add(jWithRepeat, gbCon);

        gbCon.gridx = 0;    // столбец
        gbCon.gridy = 7;    // ряд
        gbCon.anchor = GridBagConstraints.EAST;
        gbCon.fill = GridBagConstraints.NONE;   
        gbCon.weightx = 1;  // для соотношения ячеек
        gbCon.weighty = 1;
        gbCon.insets = new Insets(6, 12, 6, 6);    // отступ сверху, слева, снизу, справа
        this.add(new JLabel("My number:"), gbCon);
    
        gbCon.gridx = 1;
        gbCon.gridy = 7;
        gbCon.anchor = GridBagConstraints.WEST;
        gbCon.fill = GridBagConstraints.HORIZONTAL;
        gbCon.weightx = 1;
        gbCon.weighty = 1;
        gbCon.insets = new Insets(6, 6, 6, 12);    // отступ сверху, слева, снизу, справа
        this.jNumber = new JTextField(bcTable.getNumberLength());
        BCKeyAdapterDigits bcKeyAdapterDigits = new BCKeyAdapterDigits(bcTable.getNumberLength(), bcTable.getWithRepeat());
        this.jNumber.addKeyListener(bcKeyAdapterDigits); 
        this.add(this.jNumber, gbCon);

        this.jPlay = new JButton("Play");
        this.jPlay.setEnabled(false);
        this.jKick = new JButton("Kick");
        this.jKick.setEnabled(false);
        this.jCancel = new JButton("Cancel");
               
        JPanel jGridButton = new JPanel(new GridLayout(1, 3, 5, 0));
        jGridButton.add(this.jPlay);
        jGridButton.add(this.jKick);
        jGridButton.add(this.jCancel);
       
        JPanel jFlowButton = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        jFlowButton.add(jGridButton);

        gbCon.gridx = 0;
        gbCon.gridy = 8;
        gbCon.gridwidth = 2;
        gbCon.anchor = GridBagConstraints.CENTER;
        gbCon.fill = GridBagConstraints.NONE;
        gbCon.weightx = 1;
        gbCon.weighty = 1;
        gbCon.insets = new Insets(6, 12, 12, 12);   // отступ сверху, слева, снизу, справа
        this.add(jFlowButton, gbCon);
        
        if(bcPlayerB != null) {
            this.jPlayerB.setText(bcPlayerB.getLogin());
            this.jRatingB.setText(String.valueOf(bcPlayerB.getRating()));
        }
        
    }

    /**
     * Получить число игры
     * @return число игры
     */
    public String getNumber() {
        return this.jNumber.getText();
    }
    
    /**
     * Задать число игры
     * @param number число игры
     */
    public void setNumber(String number) {
        this.jNumber.setText(number);
    }
    
    /**
     * Очистить число игры
     */
    public void clearNumber() {
        this.jNumber.setText("");
    }

    /**
     * Установить фокус
     */
    public void setFocus() {
        this.jNumber.requestFocus();
    }

    /**
     * Добавляет слушателя
     * @param l объект слушателя
     */
    public void addActionListener(ActionListener l) {
        this.jPlay.addActionListener(l);
        this.jKick.addActionListener(l);
        this.jCancel.addActionListener(l);
    }

    @Override
    public void addKeyListener(KeyListener l) {
        this.jNumber.addKeyListener(l);
    }

    @Override
    public void update(Observable o, Object arg) {

        BCWaitGameAModel bcWaitGameAModel = (BCWaitGameAModel) o;
        
        if (bcWaitGameAModel.getPlayEnabled()) {
            if (!this.jPlay.isEnabled()) {
                this.jPlay.setEnabled(true);
                this.jNumber.requestFocus();
            }
        }
        else {
            this.jPlay.setEnabled(false);
        }
        
        this.jKick.setEnabled(bcWaitGameAModel.getKickEnabled());
        this.jCancel.setEnabled(bcWaitGameAModel.getCancelEnabled());
        
        BCTable bcTable = bcWaitGameAModel.getBCTable();
        BCPlayer bcPlayerB = bcTable.getBCPlayerB();
        
        if(bcPlayerB != null) {
            this.jPlayerB.setText(bcPlayerB.getLogin());
            this.jRatingB.setText(String.valueOf(bcPlayerB.getRating()));
        }
        else {
            this.jPlayerB.setText("");
            this.jRatingB.setText("");
        }
            
    }
    
}
