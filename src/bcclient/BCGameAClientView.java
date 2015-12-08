/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bcclient;

import bc.BCGame;
import bc.BCPlayer;
import bc.BCTable;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

/**
 * Класс "Представление объекта 'Игра: Клиент: игрок А'"
 * Наблюдатель объекта "Модель объекта 'Игра: Клиент: игрок А'"
 * @author iMacAverage
 */
public class BCGameAClientView extends JPanel implements Observer  {
        
    /** 
     * кнопка хода
     */
    private final JButton jTurn;
    
    /** 
     * кнопка сдаться
     */
    private final JButton jLost;

    /** 
     * число хода
     */
    private final JTextField jNumber;
        
    /** 
     * кнопка показать
     */
    private final JButton jShow;
    
    /** 
     * кнопка скрыть
     */
    private final JButton jHide;

    /** 
     * число игры
     */
    private final JTextField jNumberA;

    /**
     * модель таблицы игрока А
     */
    private final BCPlayerTableModel bcPlayerATableModel;

    /**
     * модель таблицы игрока Б
     */
    private final BCPlayerTableModel bcPlayerBTableModel;

    /**
     * модель таблицы ходов игрока А
     */
    private final BCTurnsTableModel bcTurnsATableModel;

    /**
     * модель таблицы ходов игрока Б
     */
    private final BCTurnsTableModel bcTurnsBTableModel;

    /**
     * Создать объект
     * @param bcGameAClientModel объект "Модель объекта 'Игра: Клиент: игрок А'"
     */
    public BCGameAClientView(BCGameAClientModel bcGameAClientModel) {
                
        this.setLayout(new GridBagLayout());
        
        GridBagConstraints gbCon = new GridBagConstraints();
        
        this.jNumber = new JTextField(6);
        BCKeyAdapterDigits bcKeyAdapterDigits = new BCKeyAdapterDigits(bcGameAClientModel.getBCGame().getBCTable().getNumberLength(), true);
        this.jNumber.addKeyListener(bcKeyAdapterDigits); 

        this.jTurn = new JButton("Turn");  
        this.jLost = new JButton("Lost");  
        
        this.jNumberA = new JTextField(6);
        this.jNumberA.setText(bcGameAClientModel.getViewNumberA()); 
        this.jNumberA.setEditable(false);
        this.jNumberA.setBackground(null);

        this.jShow = new JButton("Show");  
        this.jHide = new JButton("Hide");  
        
        JPanel jPanelLeft, jPanelCenter, jPanelRigth;
        JPanel jPanelCenterNorth, jPanelCenterCenter, jPanelCenterSouth;
        JTable jTurnsA, jTurnsB;
        JTable jPlayerA, jPlayerB;
        JScrollPane jPaneTurnsA, jPaneTurnsB;
        JScrollPane jPanePlayerA, jPanePlayerB;
        
        BCGame bcGame = bcGameAClientModel.getBCGame();
        BCTable bcTable = bcGame.getBCTable();
        
        BCPlayer bcPlayerA = bcTable.getBCPlayerA();
        BCPlayer bcPlayerB = bcTable.getBCPlayerB();

        this.bcPlayerATableModel = new BCPlayerTableModel(bcPlayerA, bcTable.getCountMin() * 60);
        this.bcPlayerBTableModel = new BCPlayerTableModel(bcPlayerB, bcTable.getCountMin() * 60);

        jPanePlayerA = new JScrollPane();
        jPlayerA = new JTable();
        jPlayerA.setFocusable(false);
        jPlayerA.setBorder(null);
        jPlayerA.setFillsViewportHeight(true);
        jPlayerA.setModel(this.bcPlayerATableModel);
        jPlayerA.setRowSelectionAllowed(false);
        jPanePlayerA.setViewportView(jPlayerA);

        jPanePlayerB = new JScrollPane();
        jPlayerB = new JTable();
        jPlayerB.setFocusable(false);
        jPlayerB.setFillsViewportHeight(true);
        jPlayerB.setModel(this.bcPlayerBTableModel);
        jPlayerB.setRowSelectionAllowed(false);
        jPanePlayerB.setViewportView(jPlayerB);

        jPanePlayerA.setPreferredSize(new Dimension(200, 40));
        jPanePlayerB.setPreferredSize(new Dimension(200, 40));

        this.bcTurnsATableModel = new BCTurnsTableModel();
        this.bcTurnsBTableModel = new BCTurnsTableModel();
        
        jPaneTurnsA = new JScrollPane();
        jTurnsA = new JTable();
        jTurnsA.setFocusable(false);
        jTurnsA.setFillsViewportHeight(true);
        jTurnsA.setModel(this.bcTurnsATableModel);
        jTurnsA.setRowSelectionAllowed(false);
        jPaneTurnsA.setViewportView(jTurnsA);

        jPaneTurnsB = new JScrollPane();
        jTurnsB = new JTable();
        jTurnsB.setFocusable(false);
        jTurnsB.setFillsViewportHeight(true);
        jTurnsB.setModel(this.bcTurnsBTableModel);
        jTurnsB.setRowSelectionAllowed(false);
        jPaneTurnsB.setViewportView(jTurnsB);

        jPaneTurnsA.setPreferredSize(new Dimension(200, 300));
        jPaneTurnsB.setPreferredSize(new Dimension(200, 300));
        
        jPanelLeft = new JPanel();
        jPanelLeft.setLayout(new GridBagLayout());

        gbCon.gridx = 0;    // столбец
        gbCon.gridy = 0;    // ряд
        gbCon.anchor = GridBagConstraints.CENTER;
        gbCon.fill = GridBagConstraints.NONE;   
        gbCon.weightx = 1;  // для соотношения ячеек
        gbCon.weighty = 0;
        gbCon.gridwidth = 1;
        gbCon.gridheight = 1;
        gbCon.insets = new Insets(5, 5, 5, 5);
        jPanelLeft.add(new JLabel("Turns A"), gbCon);

        gbCon.gridx = 0;    // столбец
        gbCon.gridy = 1;    // ряд
        gbCon.anchor = GridBagConstraints.CENTER;
        gbCon.fill = GridBagConstraints.BOTH;   
        gbCon.weightx = 1;  // для соотношения ячеек
        gbCon.weighty = 1;
        gbCon.gridwidth = 1;
        gbCon.gridheight = 1;
        gbCon.insets = new Insets(5, 5, 5, 5);
        jPanelLeft.add(jPaneTurnsA, gbCon);

        jPanelRigth = new JPanel();
        jPanelRigth.setLayout(new GridBagLayout());

        gbCon.gridx = 0;    // столбец
        gbCon.gridy = 0;    // ряд
        gbCon.anchor = GridBagConstraints.CENTER;
        gbCon.fill = GridBagConstraints.NONE;   
        gbCon.weightx = 1;  // для соотношения ячеек
        gbCon.weighty = 0;
        gbCon.gridwidth = 1;
        gbCon.gridheight = 1;
        gbCon.insets = new Insets(5, 5, 5, 5);
        jPanelRigth.add(new JLabel("Turns B"), gbCon);

        gbCon.gridx = 0;    // столбец
        gbCon.gridy = 1;    // ряд
        gbCon.anchor = GridBagConstraints.CENTER;
        gbCon.fill = GridBagConstraints.BOTH;   
        gbCon.weightx = 1;  // для соотношения ячеек
        gbCon.weighty = 1;
        gbCon.gridwidth = 1;
        gbCon.gridheight = 1;
        gbCon.insets = new Insets(5, 5, 5, 5);
        jPanelRigth.add(jPaneTurnsB, gbCon);
        
        jPanelCenterNorth =  new JPanel();
        jPanelCenterNorth.setLayout(new GridBagLayout());
        
        gbCon.gridx = 0;    // столбец
        gbCon.gridy = 0;    // ряд
        gbCon.anchor = GridBagConstraints.CENTER;
        gbCon.fill = GridBagConstraints.NONE;   
        gbCon.weightx = 1;  // для соотношения ячеек
        gbCon.weighty = 1;
        gbCon.gridwidth = 1;
        gbCon.gridheight = 1;
        gbCon.insets = new Insets(5, 5, 5, 5);
        jPanelCenterNorth.add(new JLabel("Player B"), gbCon);

        gbCon.gridx = 0;    // столбец
        gbCon.gridy = 1;    // ряд
        gbCon.anchor = GridBagConstraints.NORTH;
        gbCon.fill = GridBagConstraints.HORIZONTAL;   
        gbCon.weightx = 1;  // для соотношения ячеек
        gbCon.weighty = 1;
        gbCon.gridwidth = 1;
        gbCon.gridheight = 1;
        gbCon.insets = new Insets(5, 5, 5, 5);
        jPanelCenterNorth.add(jPanePlayerB, gbCon);

        jPanelCenterCenter = new JPanel(); 
        jPanelCenterCenter.setLayout(new GridBagLayout());

        gbCon.gridx = 0;    // столбец
        gbCon.gridy = 0;    // ряд
        gbCon.anchor = GridBagConstraints.SOUTHEAST;
        gbCon.fill = GridBagConstraints.NONE;   
        gbCon.weightx = 1;  // для соотношения ячеек
        gbCon.weighty = 1;
        gbCon.gridwidth = 1;
        gbCon.gridheight = 1;
        gbCon.insets = new Insets(5, 5, 10, 10);
        jPanelCenterCenter.add(new JLabel("My number:"), gbCon);
        
        gbCon.gridx = 1;    // столбец
        gbCon.gridy = 0;    // ряд
        gbCon.anchor = GridBagConstraints.SOUTHWEST;
        gbCon.fill = GridBagConstraints.NONE;   
        gbCon.weightx = 1;  // для соотношения ячеек
        gbCon.weighty = 1;
        gbCon.gridwidth = 1;
        gbCon.gridheight = 1;
        gbCon.insets = new Insets(5, 10, 5, 5);
        jPanelCenterCenter.add(this.jNumberA, gbCon);

        gbCon.gridx = 0;    // столбец
        gbCon.gridy = 1;    // ряд
        gbCon.anchor = GridBagConstraints.NORTHEAST;
        gbCon.fill = GridBagConstraints.NONE;   
        gbCon.weightx = 1;  // для соотношения ячеек
        gbCon.weighty = 1;
        gbCon.gridwidth = 1;
        gbCon.gridheight = 1;
        gbCon.insets = new Insets(5, 5, 5, 5);
        jPanelCenterCenter.add(this.jShow, gbCon);

        gbCon.gridx = 1;    // столбец
        gbCon.gridy = 1;    // ряд
        gbCon.anchor = GridBagConstraints.NORTHWEST;
        gbCon.fill = GridBagConstraints.NONE;   
        gbCon.weightx = 1;  // для соотношения ячеек
        gbCon.weighty = 1;
        gbCon.gridwidth = 1;
        gbCon.gridheight = 1;
        gbCon.insets = new Insets(5, 5, 5, 5);
        jPanelCenterCenter.add(this.jHide, gbCon);
        
        JLabel jRepeat = new JLabel();
        if (bcGameAClientModel.getBCGame().getBCTable().getWithRepeat())
            jRepeat.setText("With repeating digits");
        else
            jRepeat.setText("Without repeating digits");
        jRepeat.setEnabled(false);
            
        gbCon.gridx = 0;    // столбец
        gbCon.gridy = 2;    // ряд
        gbCon.anchor = GridBagConstraints.CENTER;
        gbCon.fill = GridBagConstraints.NONE;   
        gbCon.weightx = 1;  // для соотношения ячеек
        gbCon.weighty = 1;
        gbCon.gridwidth = 2;
        gbCon.gridheight = 1;
        gbCon.insets = new Insets(5, 5, 5, 5);
        jPanelCenterCenter.add(jRepeat, gbCon);

        gbCon.gridx = 0;    // столбец
        gbCon.gridy = 3;    // ряд
        gbCon.anchor = GridBagConstraints.SOUTHEAST;
        gbCon.fill = GridBagConstraints.NONE;   
        gbCon.weightx = 1;  // для соотношения ячеек
        gbCon.weighty = 1;
        gbCon.gridwidth = 1;
        gbCon.gridheight = 1;
        gbCon.insets = new Insets(5, 5, 10, 10);
        jPanelCenterCenter.add(new JLabel("Number turn:"), gbCon);
        
        gbCon.gridx = 1;    // столбец
        gbCon.gridy = 3;    // ряд
        gbCon.anchor = GridBagConstraints.SOUTHWEST;
        gbCon.fill = GridBagConstraints.NONE;   
        gbCon.weightx = 1;  // для соотношения ячеек
        gbCon.weighty = 1;
        gbCon.gridwidth = 1;
        gbCon.gridheight = 1;
        gbCon.insets = new Insets(5, 10, 5, 5);
        jPanelCenterCenter.add(this.jNumber, gbCon);
        
        gbCon.gridx = 0;    // столбец
        gbCon.gridy = 4;    // ряд
        gbCon.anchor = GridBagConstraints.NORTHEAST;
        gbCon.fill = GridBagConstraints.NONE;   
        gbCon.weightx = 1;  // для соотношения ячеек
        gbCon.weighty = 1;
        gbCon.gridwidth = 1;
        gbCon.gridheight = 1;
        gbCon.insets = new Insets(5, 5, 5, 5);
        jPanelCenterCenter.add(this.jTurn, gbCon);

        gbCon.gridx = 1;    // столбец
        gbCon.gridy = 4;    // ряд
        gbCon.anchor = GridBagConstraints.NORTHWEST;
        gbCon.fill = GridBagConstraints.NONE;   
        gbCon.weightx = 1;  // для соотношения ячеек
        gbCon.weighty = 1;
        gbCon.gridwidth = 1;
        gbCon.gridheight = 1;
        gbCon.insets = new Insets(5, 5, 5, 5);
        jPanelCenterCenter.add(this.jLost, gbCon);

        jPanelCenterSouth = new JPanel();
        jPanelCenterSouth.setLayout(new GridBagLayout());

        gbCon.gridx = 0;    // столбец
        gbCon.gridy = 0;    // ряд
        gbCon.anchor = GridBagConstraints.SOUTH;
        gbCon.fill = GridBagConstraints.NONE;   
        gbCon.weightx = 1;  // для соотношения ячеек
        gbCon.weighty = 1;
        gbCon.gridwidth = 1;
        gbCon.gridheight = 1;
        gbCon.insets = new Insets(5, 5, 5, 5);
        jPanelCenterSouth.add(new JLabel("Player A"), gbCon);

        gbCon.gridx = 0;    // столбец
        gbCon.gridy = 1;    // ряд
        gbCon.anchor = GridBagConstraints.SOUTH;
        gbCon.fill = GridBagConstraints.HORIZONTAL;   
        gbCon.weightx = 1;  // для соотношения ячеек
        gbCon.weighty = 2;
        gbCon.gridwidth = 1;
        gbCon.gridheight = 1;
        gbCon.insets = new Insets(5, 5, 5, 5);
        jPanelCenterSouth.add(jPanePlayerA, gbCon);
        
        jPanelCenter = new JPanel();
        jPanelCenter.setLayout(new GridBagLayout());

        gbCon.gridx = 0;    // столбец
        gbCon.gridy = 0;    // ряд
        gbCon.anchor = GridBagConstraints.NORTH;
        gbCon.fill = GridBagConstraints.HORIZONTAL;   
        gbCon.weightx = 1;  // для соотношения ячеек
        gbCon.weighty = 1;
        gbCon.gridwidth = 1;
        gbCon.gridheight = 1;
        gbCon.insets = new Insets(0, 0, 0, 0);
        jPanelCenter.add(jPanelCenterNorth, gbCon);
        
        gbCon.gridx = 0;    // столбец
        gbCon.gridy = 1;    // ряд
        gbCon.anchor = GridBagConstraints.CENTER;
        gbCon.fill = GridBagConstraints.BOTH;   
        gbCon.weightx = 1;  // для соотношения ячеек
        gbCon.weighty = 3;
        gbCon.gridwidth = 1;
        gbCon.gridheight = 1;
        gbCon.insets = new Insets(0, 0, 0, 0);
        jPanelCenter.add(jPanelCenterCenter, gbCon);
        
        gbCon.gridx = 0;    // столбец
        gbCon.gridy = 2;    // ряд
        gbCon.anchor = GridBagConstraints.SOUTH;
        gbCon.fill = GridBagConstraints.HORIZONTAL;   
        gbCon.weightx = 1;  // для соотношения ячеек
        gbCon.weighty = 1;
        gbCon.gridwidth = 1;
        gbCon.gridheight = 1;
        gbCon.insets = new Insets(0, 0, 0, 0);
        jPanelCenter.add(jPanelCenterSouth, gbCon);

        // добавляю панели на главную
        gbCon.gridx = 0;    // столбец
        gbCon.gridy = 0;    // ряд
        gbCon.anchor = GridBagConstraints.NORTH;
        gbCon.fill = GridBagConstraints.BOTH;   
        gbCon.weightx = 1;  // для соотношения ячеек
        gbCon.weighty = 1;
        gbCon.gridwidth = 1;
        gbCon.gridheight = 1;
        gbCon.insets = new Insets(5, 5, 5, 5);
        this.add(jPanelLeft, gbCon);

        gbCon.gridx = 1;
        gbCon.gridy = 0;
        gbCon.anchor = GridBagConstraints.NORTH;
        gbCon.fill = GridBagConstraints.BOTH;
        gbCon.weightx = 1;
        gbCon.weighty = 1;
        gbCon.gridwidth = 1;
        gbCon.gridheight = 1;
        gbCon.insets = new Insets(5, 0, 5, 0); // сверху, слева, снизу, справа
        this.add(jPanelCenter, gbCon);
                
        gbCon.gridx = 2;    // столбец
        gbCon.gridy = 0;    // ряд
        gbCon.anchor = GridBagConstraints.NORTH;
        gbCon.fill = GridBagConstraints.BOTH;   
        gbCon.weightx = 1;  // для соотношения ячеек
        gbCon.weighty = 1;
        gbCon.gridwidth = 1;
        gbCon.gridheight = 1;
        gbCon.insets = new Insets(5, 5, 5, 5);
        this.add(jPanelRigth, gbCon);
        
    }

    /**
     * Установить фокус
     */
    public void setFocus() {
        this.jNumber.requestFocus();
    }
    
    /**
     * Получить число хода
     * @return число хода
     */
    public String getNumberTurn() {
        return this.jNumber.getText();
    }

    /**
     * Задать число хода
     * @param number число хода
     */
    public void setNumber(String number) {
        this.jNumber.setText(number);
    }

    /**
     * Добавляет слушателя
     * @param l объект слушателя
     */
    public void addActionListener(ActionListener l) {
        this.jTurn.addActionListener(l);
        this.jLost.addActionListener(l);
        this.jHide.addActionListener(l);
        this.jShow.addActionListener(l);
    }
    
    @Override
    public void addKeyListener(KeyListener l) {
        this.jNumber.addKeyListener(l);
    }
    
    @Override
    public void update(Observable o, Object arg) {

        BCGameAClientModel bcGameAClientModel = (BCGameAClientModel) o;
        
        this.jNumberA.setText(bcGameAClientModel.getViewNumberA()); 

        if(bcGameAClientModel.isTurn()) {
            if (!this.jTurn.isEnabled()) {
                this.jTurn.setEnabled(true);
                this.jNumber.requestFocus();
            }
        }
        else {
            if (this.jTurn.isEnabled()) {
                this.jTurn.setEnabled(false);
                this.jLost.requestFocus();
            }
        }

        this.jLost.setEnabled(bcGameAClientModel.getLostEnabled());
        this.bcPlayerATableModel.setTime(bcGameAClientModel.getTimeA());
        this.bcPlayerBTableModel.setTime(bcGameAClientModel.getTimeB());
        this.bcTurnsATableModel.setBCTurns(bcGameAClientModel.getBCGame().getTurnsA());
        this.bcTurnsBTableModel.setBCTurns(bcGameAClientModel.getBCGame().getTurnsB());
    
    }
    
}
