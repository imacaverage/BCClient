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
import java.util.Observable;
import java.util.Observer;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Класс "Представление объекта 'Создать стол'"
 * Наблюдатель объекта "Модель объекта 'Создать стол'"
 * @author iMacAverage
 */
public class BCNewTableView extends JPanel implements Observer {

    /** 
     * кнопка OK 
     */
    private final JButton jOK;
    
    /** 
     * кнопка отмены 
     */
    private final JButton jCancel;
        
    /** 
     * минимальный рейтинг для соперника 
     */
    private final JComboBox jMinRating;

    /** 
     * длина числа 
     */
    private final JComboBox jNumberLength;

    /** 
     * количество минут игры 
     */
    private final JComboBox jCountMin;
    
    /** 
     * признак возможности повторений цифр в числе 
     */
    private final JCheckBox jWithRepeat;
    
    /** 
     * массив значений для минимального рейтинга 
     */
    private final String[] minRatingValues;
    
    /** 
     * массив значений длины числа 
     */
    private final String[] numberLengthValues;

    /** 
     * массив значений для времени игры 
     */
    private final String[] countMinValues;
    
    /**
     * Создать объект
     */
    public BCNewTableView() {

        this.minRatingValues = new String[] {"0", "500", "1000", "1500", "2000"};
        this.numberLengthValues = new String[] {"2", "3", "4", "5", "6"};
        this.countMinValues = new String[] {"3", "4", "5", "10", "20"};

        this.setLayout(new GridBagLayout());

        GridBagConstraints gbCon = new GridBagConstraints();

        gbCon.gridx = 0;    // первый столбец
        gbCon.gridy = 0;    // первый ряд
        gbCon.anchor = GridBagConstraints.WEST;
        gbCon.fill = GridBagConstraints.NONE;   
        gbCon.weightx = 3;  // для соотношения ячеек
        gbCon.weighty = 1;
        gbCon.insets = new Insets(12, 12, 6, 6);    // отступ сверху, слева, снизу, справа
        this.add(new JLabel("Min rating:"), gbCon);
        
        gbCon.gridx = 1;
        gbCon.gridy = 0;
        gbCon.anchor = GridBagConstraints.WEST;
        gbCon.fill = GridBagConstraints.HORIZONTAL;
        gbCon.weightx = 1;
        gbCon.weighty = 1;
        gbCon.insets = new Insets(12, 6, 6, 12);    // отступ сверху, слева, снизу, справа
        this.jMinRating = new JComboBox(this.minRatingValues);
        this.add(this.jMinRating, gbCon);

        gbCon.gridx = 0;
        gbCon.gridy = 1;
        gbCon.anchor = GridBagConstraints.WEST;
        gbCon.fill = GridBagConstraints.NONE;
        gbCon.weightx = 3;
        gbCon.weighty = 1;
        gbCon.insets = new Insets(6, 12, 12, 6);    // отступ сверху, слева, снизу, справа
        this.add(new JLabel("Number length:"), gbCon);

        gbCon.gridx = 1;
        gbCon.gridy = 1;
        gbCon.anchor = GridBagConstraints.WEST;
        gbCon.fill = GridBagConstraints.HORIZONTAL;
        gbCon.weightx = 1;
        gbCon.weighty = 1;
        gbCon.insets = new Insets(6, 6, 6, 12); // отступ сверху, слева, снизу, справа
        this.jNumberLength = new JComboBox(this.numberLengthValues);
        this.add(this.jNumberLength, gbCon);

        gbCon.gridx = 0;
        gbCon.gridy = 2;
        gbCon.anchor = GridBagConstraints.WEST;
        gbCon.fill = GridBagConstraints.NONE;
        gbCon.weightx = 3;
        gbCon.weighty = 1;
        gbCon.insets = new Insets(6, 12, 12, 6);    // отступ сверху, слева, снизу, справа
        this.add(new JLabel("Time (minutes):"), gbCon);
        
        gbCon.gridx = 1;
        gbCon.gridy = 2;
        gbCon.anchor = GridBagConstraints.WEST;
        gbCon.fill = GridBagConstraints.HORIZONTAL;
        gbCon.weightx = 1;
        gbCon.weighty = 1;
        gbCon.insets = new Insets(6, 6, 6, 12); // отступ сверху, слева, снизу, справа
        this.jCountMin = new JComboBox(this.countMinValues);
        this.add(this.jCountMin, gbCon);
        
        gbCon.gridx = 0;    // столбец
        gbCon.gridy = 3;    // ряд
        gbCon.anchor = GridBagConstraints.EAST;
        gbCon.fill = GridBagConstraints.NONE;   
        gbCon.weightx = 1;  // для соотношения ячеек
        gbCon.weighty = 1;
        gbCon.insets = new Insets(6, 12, 12, 6);    // отступ сверху, слева, снизу, справа
        this.add(new JLabel("Repeating digits:"), gbCon);
    
        gbCon.gridx = 1;
        gbCon.gridy = 3;
        gbCon.anchor = GridBagConstraints.WEST;
        gbCon.fill = GridBagConstraints.HORIZONTAL;
        gbCon.weightx = 1;
        gbCon.weighty = 1;
        gbCon.insets = new Insets(6, 6, 6, 12);    // отступ сверху, слева, снизу, справа
        this.jWithRepeat = new JCheckBox();
        this.add(jWithRepeat, gbCon);

        this.jOK = new JButton("OK");
        this.jCancel = new JButton("Cancel");

        JPanel jGridButton = new JPanel(new GridLayout(1, 2, 5, 0));
        jGridButton.add(this.jOK);
        jGridButton.add(this.jCancel);
       
        JPanel jFlowButton = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        jFlowButton.add(jGridButton);

        gbCon.gridx = 0;
        gbCon.gridy = 4;
        gbCon.gridwidth = 2;
        gbCon.anchor = GridBagConstraints.EAST;
        gbCon.fill = GridBagConstraints.NONE;
        gbCon.weightx = 1;
        gbCon.weighty = 1;
        gbCon.insets = new Insets(6, 12, 12, 12);   // отступ сверху, слева, снизу, справа
        this.add(jFlowButton, gbCon);

    }

    /**
     * Получить минимальный рейтинг соперника
     * @return минимальный рейтинг соперника
     */
    public int getMinRating() {
        return Integer.valueOf(this.minRatingValues[this.jMinRating.getSelectedIndex()]);
    }

    /**
     * Получить количество цифр в числе
     * @return количество цифр в числе
     */
    public int getNumberLength() {
        return Integer.valueOf(this.numberLengthValues[this.jNumberLength.getSelectedIndex()]);
    }
    
    /**
     * Получить количество минут на игру
     * @return количество минут на игру
     */
    public int getCountMin() {
        return Integer.valueOf(this.countMinValues[this.jCountMin.getSelectedIndex()]);
    }
    
    /**
     * Получить возможность повторения цифр в числе
     * @return возможность повторения цифр в числе
     */
    public boolean getWithRepeat() {
        return jWithRepeat.isSelected();
    }
    
    /**
     * Добавить слушателя
     * @param l слушатель
     */
    public void addActionListener(ActionListener l) {
        this.jOK.addActionListener(l);
        this.jCancel.addActionListener(l);
    }        
    
    @Override
    public void update(Observable o, Object arg) {
        BCNewTableModel bcNewTableModel = (BCNewTableModel) o;
        this.jOK.setEnabled(bcNewTableModel.getOKEnabled());
        this.jCancel.setEnabled(bcNewTableModel.getCancelEnabled());
    }
    
}
