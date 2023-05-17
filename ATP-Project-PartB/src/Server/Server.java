package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class Server {
    private final int port;
    private final int listeningIntervalMS;
    private final IServerStrategy strategy;
    private volatile boolean stop;
    public Server(int port, int listeningIntervalMS, IServerStrategy strategy){
        this.port = port;
        this.listeningIntervalMS = listeningIntervalMS;
        this.strategy = strategy;
    }
    public void start(){
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            serverSocket.setSoTimeout(listeningIntervalMS);
//            System.out.println(String.format("Server started! (port: %s)", port));
            while (!stop){
                try {
                    Socket client = serverSocket.accept();
                    new Thread(() -> clientHandle(client)).start();
                }
                catch (SocketTimeoutException e){
//                    e.printStackTrace();
                }
            }
            serverSocket.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
    private void clientHandle(Socket clientSocket){
        try {
            strategy.serverStrategy(clientSocket.getInputStream(), clientSocket.getOutputStream());
            clientSocket.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
    public void stop(){
        stop = true;
    }
}