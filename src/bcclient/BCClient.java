/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bcclient;

import bc.BCMsgGet;
import bc.BCMsgGetObserverPingRequest;
import bc.BCMsgSend;
import bc.BCPlayer;
import bc.BCTimerPing;
import java.awt.EventQueue;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Timer;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JOptionPane;

/**
 * Класс "Клиент"
 * @author iMacAverage
 */
public class BCClient extends JFrame {

    /**
     * версия клиента
     */
    private final static int VERSION = 18;
    
    /** 
     * задержка для пинга 
     */
    private final static int PING_DELAY = 3000;

    /** 
     * таймаут для сокета 
     */
    private final static int TIME_OUT = 10000;

    /**
     * ip сервера
     */
    private final static String SERVER = "91.234.33.251";
            
    /** 
     * порт сервера 
     */
    private final static int PORT = 4444;

    /**
     * объект "Модель объекта 'Клиент'"
     */
    private final BCClientModel bcClientModel;
    
    /**
     * объект "Представление объекта 'Клиент'"
     */
    private final BCClientView bcClientView;
    
    /**
     * объект "Контроллер объекта 'Клиент'"
     */
    private final BCClientController bcClientController;
    
    /**
     * Создать объект
     * @param bcPlayer объект "Игрок"
     * @param bcMsgGet объект "Прием сообщений"
     * @param bcMsgGetObserverRoom "Наблюдатель объекта 'Прием сообщений': Игровая комната"
     */ 
    public BCClient(BCPlayer bcPlayer, BCMsgGet bcMsgGet, BCMsgGetObserverRoom bcMsgGetObserverRoom) {

        super("Bulls & Cows" + " ver." + BCClient.VERSION/10 + "." + BCClient.VERSION%10);

        this.bcClientModel = new BCClientModel(bcPlayer, bcMsgGet);

        this.bcClientView = new BCClientView(this.bcClientModel);

        this.bcClientController = new BCClientController(this.bcClientModel);

        this.bcClientView.addActionListener(this.bcClientController);
        this.bcClientView.addMouseListener(this.bcClientController);

        this.bcClientModel.addObserver(this.bcClientView);

        this.getContentPane().add(this.bcClientView);

        bcMsgGetObserverRoom.addObserver(this.bcClientModel);
        bcMsgGetObserverRoom.getNotify();

        BCMsgGetObserverSitTableResponse bcMsgGetObserverSitTableResponse = new BCMsgGetObserverSitTableResponse();
        bcMsgGetObserverSitTableResponse.addObserver(this.bcClientModel);
        
        bcMsgGet.addObserver(bcMsgGetObserverSitTableResponse);
                
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);   
    
    }
    
    /**
     * Отобразить окно
     */
    public void display() {
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        EventQueue.invokeLater(
            
                new Runnable() {

                @Override
                public void run() {
                    
                    Socket socket = null;
                    InetAddress addrServer = null;
                    ObjectInputStream inStream = null;
                    ObjectOutputStream outStream = null;

                    // получить объект адреса сервера
                    try {
                        addrServer = InetAddress.getByName(BCClient.SERVER);
                    } 
                    catch (UnknownHostException ex) {
                        JOptionPane.showMessageDialog(null, "Failed get ip address from BCClient.ini!", "Error", JOptionPane.ERROR_MESSAGE);
                        System.exit(1);
                    }      

                    // подключиться к серверу
                    try {
                        socket = new Socket(addrServer, BCClient.PORT);
                    } 
                    catch (IOException ex) {
                        JOptionPane.showMessageDialog(null, "No connection to the server!", "Error", JOptionPane.ERROR_MESSAGE);
                        System.exit(1);
                    }

                    // создать потоки для обмена объектами сообщений
                    try {
                        inStream   = new ObjectInputStream(socket.getInputStream());
                        outStream = new ObjectOutputStream(socket.getOutputStream());
                    } 
                    catch (IOException ex) {
                        JOptionPane.showMessageDialog(null, "Error creating thread messaging!", "Error", JOptionPane.ERROR_MESSAGE);
                        System.exit(1);
                    }

                    // задать таймаут для сокета
                    try {
                        socket.setSoTimeout(BCClient.TIME_OUT);
                    } 
                    catch (SocketException ex) {
                        JOptionPane.showMessageDialog(null, "Error setting the timeout for the socket!", "Error", JOptionPane.ERROR_MESSAGE);
                        System.exit(1);
                    }
                    
                    // объект прием сообщений
                    BCMsgGet bcMsgGet = new BCMsgGet(inStream);
                    
                    // объект отправка сообщений
                    BCMsgSend bcMsgSend = new BCMsgSend(outStream);
                    
                    // объект наблюдатель за пинг запросами
                    BCMsgGetObserverPingRequest bcMsgGetObserverPingRequest = new BCMsgGetObserverPingRequest(bcMsgSend);
                    bcMsgGet.addObserver(bcMsgGetObserverPingRequest);
                            
                    // объект наблюдатель - пинг на таймере
                    BCTimerPing bcTimerPing = new BCTimerPing(bcMsgGet, bcMsgSend);
                    bcMsgGet.addObserver(bcTimerPing);
                    
                    // объект наблюдатель объекта прием сообщений (сообщение: игровая комната)
                    BCMsgGetObserverRoom bcMsgGetObserverRoom = new BCMsgGetObserverRoom();
                    bcMsgGet.addObserver(bcMsgGetObserverRoom);

                    // объект наблюдатель объекта прием сообщений (сообщение: ошибка)
                    BCMsgGetObserverError bcMsgGetObserverError = new BCMsgGetObserverError();
                    bcMsgGet.addObserver(bcMsgGetObserverError);

                    // запуск получения сообщений в отдельном потоке
                    Thread thread = new Thread(bcMsgGet);
                    thread.start();
                    
                    // запуск пинга на таймере
                    Timer timer = new Timer();
                    timer.schedule(bcTimerPing, 0, BCClient.PING_DELAY);
                    
                    // окно логирования
                    BCLogin bcLogin = new BCLogin(null, bcMsgGet, bcMsgSend, BCClient.VERSION);
                    bcLogin.display();
                    
                    if(!bcLogin.getLogon())
                        System.exit(0);

                    // основное окно клиента
                    BCClient bcClient = new BCClient(bcLogin.getBCPlayer(), bcMsgGet, bcMsgGetObserverRoom);
                    bcClient.display();
                }
            
            });
    }
    
}
