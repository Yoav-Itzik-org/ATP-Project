package Client;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
/**
 * client class - creating a connection between a user and the server*/

public class Client {
    private final InetAddress serverIP;
    private final int serverPort;
    private final IClientStrategy strategy;
    /**
     * constructor - initialize the field with the relevant port, IP and strategy
     * @param serverIP - the local IP
     * @param serverPort - port of the server
     * @param  strategy - the strategy type*/
    public Client(InetAddress serverIP, int serverPort, IClientStrategy strategy){
        this.serverIP = serverIP;
        this.serverPort = serverPort;
        this.strategy = strategy;
    }
    /**
     * the function creates the connection between the user and the server*/
    public void communicateWithServer() {
        try{
            Socket serverSocket = new Socket(serverIP, serverPort);
            strategy.clientStrategy(serverSocket.getInputStream(), serverSocket.getOutputStream());
            serverSocket.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}