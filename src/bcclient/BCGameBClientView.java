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
 * Класс "Представление объекта 'Игра: Клиент: игрок Б'"
 * Наблюдатель объекта "Модель объекта 'Игра: Клиент: игрок Б'"
 * @author iMacAverage
 */
public class BCGameBClientView extends JPanel implements Observer  {
        
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
    private final JTextField jNumberB;

    /**
     * модель таблицы игрока Б
     */
    private final BCPlayerTableModel bcPlayerBTableModel;

    /**
     * модель таблицы игрока А
     */
    private final BCPlayerTableModel bcPlayerATableModel;

    /**
     * модель таблицы ходов игрока Б
     */
    private final BCTurnsTableModel bcTurnsBTableModel;

    /**
     * модель таблицы ходов игрока А
     */
    private final BCTurnsTableModel bcTurnsATableModel;

    /**
     * Создать объект
     * @param bcGameBClientModel объект "Модель объекта 'Игра: Клиент: игрок Б'"
     */
    public BCGameBClientView(BCGameBClientModel bcGameBClientModel) {
                
        this.setLayout(new GridBagLayout());
        
        GridBagConstraints gbCon = new GridBagConstraints();
        
        this.jNumber = new JTextField(6);
        BCKeyAdapterDigits bcKeyAdapterDigits = new BCKeyAdapterDigits(bcGameBClientModel.getBCGame().getBCTable().getNumberLength(), true);
        this.jNumber.addKeyListener(bcKeyAdapterDigits); 

        this.jTurn = new JButton("Turn");  
        this.jLost = new JButton("Lost");  

        this.jNumberB = new JTextField(6);
        this.jNumberB.setText(bcGameBClientModel.getViewNumberB());
        this.jNumberB.setEditable(false);
        this.jNumberB.setBackground(null);

        this.jShow = new JButton("Show");  
        this.jHide = new JButton("Hide");  

        JPanel jPanelLeft, jPanelCenter, jPanelRigth;
        JPanel jPanelCenterNorth, jPanelCenterCenter, jPanelCenterSouth;
        JTable jTurnsB, jTurnsA;
        JTable jPlayerB, jPlayerA;
        JScrollPane jPaneTurnsB, jPaneTurnsA;
        JScrollPane jPanePlayerB, jPanePlayerA;
        
        BCGame bcGame = bcGameBClientModel.getBCGame();
        BCTable bcTable = bcGame.getBCTable();
        
        BCPlayer bcPlayerB = bcTable.getBCPlayerB();
        BCPlayer bcPlayerA = bcTable.getBCPlayerA();

        this.bcPlayerBTableModel = new BCPlayerTableModel(bcPlayerB, bcTable.getCountMin() * 60);
        this.bcPlayerATableModel = new BCPlayerTableModel(bcPlayerA, bcTable.getCountMin() * 60);

        jPanePlayerB = new JScrollPane();
        jPlayerB = new JTable();
        jPlayerB.setFocusable(false);
        jPlayerB.setBorder(null);
        jPlayerB.setFillsViewportHeight(true);
        jPlayerB.setModel(this.bcPlayerBTableModel);
        jPlayerB.setRowSelectionAllowed(false);
        jPanePlayerB.setViewportView(jPlayerB);

        jPanePlayerA = new JScrollPane();
        jPlayerA = new JTable();
        jPlayerA.setFocusable(false);
        jPlayerA.setFillsViewportHeight(true);
        jPlayerA.setModel(this.bcPlayerATableModel);
        jPlayerA.setRowSelectionAllowed(false);
        jPanePlayerA.setViewportView(jPlayerA);

        jPanePlayerB.setPreferredSize(new Dimension(200, 40));
        jPanePlayerA.setPreferredSize(new Dimension(200, 40));

        this.bcTurnsBTableModel = new BCTurnsTableModel();
        this.bcTurnsATableModel = new BCTurnsTableModel();
        
        jPaneTurnsB = new JScrollPane();
        jTurnsB = new JTable();
        jTurnsB.setFocusable(false);
        jTurnsB.setFillsViewportHeight(true);
        jTurnsB.setModel(this.bcTurnsBTableModel);
        jTurnsB.setRowSelectionAllowed(false);
        jPaneTurnsB.setViewportView(jTurnsB);

        jPaneTurnsA = new JScrollPane();
        jTurnsA = new JTable();
        jTurnsA.setFocusable(false);
        jTurnsA.setFillsViewportHeight(true);
        jTurnsA.setModel(this.bcTurnsATableModel);
        jTurnsA.setRowSelectionAllowed(false);
        jPaneTurnsA.setViewportView(jTurnsA);

        jPaneTurnsB.setPreferredSize(new Dimension(200, 300));
        jPaneTurnsA.setPreferredSize(new Dimension(200, 300));
        
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
        jPanelLeft.add(new JLabel("Turns B"), gbCon);

        gbCon.gridx = 0;    // столбец
        gbCon.gridy = 1;    // ряд
        gbCon.anchor = GridBagConstraints.CENTER;
        gbCon.fill = GridBagConstraints.BOTH;   
        gbCon.weightx = 1;  // для соотношения ячеек
        gbCon.weighty = 1;
        gbCon.gridwidth = 1;
        gbCon.gridheight = 1;
        gbCon.insets = new Insets(5, 5, 5, 5);
        jPanelLeft.add(jPaneTurnsB, gbCon);

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
        jPanelRigth.add(new JLabel("Turns A"), gbCon);

        gbCon.gridx = 0;    // столбец
        gbCon.gridy = 1;    // ряд
        gbCon.anchor = GridBagConstraints.CENTER;
        gbCon.fill = GridBagConstraints.BOTH;   
        gbCon.weightx = 1;  // для соотношения ячеек
        gbCon.weighty = 1;
        gbCon.gridwidth = 1;
        gbCon.gridheight = 1;
        gbCon.insets = new Insets(5, 5, 5, 5);
        jPanelRigth.add(jPaneTurnsA, gbCon);
        
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
        jPanelCenterNorth.add(new JLabel("Player A"), gbCon);

        gbCon.gridx = 0;    // столбец
        gbCon.gridy = 1;    // ряд
        gbCon.anchor = GridBagConstraints.NORTH;
        gbCon.fill = GridBagConstraints.HORIZONTAL;   
        gbCon.weightx = 1;  // для соотношения ячеек
        gbCon.weighty = 1;
        gbCon.gridwidth = 1;
        gbCon.gridheight = 1;
        gbCon.insets = new Insets(5, 5, 5, 5);
        jPanelCenterNorth.add(jPanePlayerA, gbCon);

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
        jPanelCenterCenter.add(this.jNumberB, gbCon);

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
        if (bcGameBClientModel.getBCGame().getBCTable().getWithRepeat())
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
        jPanelCenterSouth.add(new JLabel("Player B"), gbCon);

        gbCon.gridx = 0;    // столбец
        gbCon.gridy = 1;    // ряд
        gbCon.anchor = GridBagConstraints.SOUTH;
        gbCon.fill = GridBagConstraints.HORIZONTAL;   
        gbCon.weightx = 1;  // для соотношения ячеек
        gbCon.weighty = 2;
        gbCon.gridwidth = 1;
        gbCon.gridheight = 1;
        gbCon.insets = new Insets(5, 5, 5, 5);
        jPanelCenterSouth.add(jPanePlayerB, gbCon);
        
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
        
        this.jTurn.setEnabled(false);
        
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
        this.jShow.addActionListener(l);
        this.jHide.addActionListener(l);
    }

    @Override
    public void addKeyListener(KeyListener l) {
        this.jNumber.addKeyListener(l);
    }
    
    @Override
    public void update(Observable o, Object arg) {

        BCGameBClientModel bcGameBClientModel = (BCGameBClientModel) o;
        
        this.jNumberB.setText(bcGameBClientModel.getViewNumberB()); 

        if(bcGameBClientModel.isTurn()) {
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

        this.jLost.setEnabled(bcGameBClientModel.getLostEnabled());
        this.bcPlayerBTableModel.setTime(bcGameBClientModel.getTimeB());
        this.bcPlayerATableModel.setTime(bcGameBClientModel.getTimeA());
        this.bcTurnsBTableModel.setBCTurns(bcGameBClientModel.getBCGame().getTurnsB());
        this.bcTurnsATableModel.setBCTurns(bcGameBClientModel.getBCGame().getTurnsA());
    
    }
    
}
