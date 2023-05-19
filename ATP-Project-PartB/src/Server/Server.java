package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Server class - opening port for communicate with Client class*/
public class Server {
    private final int port;
    private final int listeningIntervalMS;
    private final IServerStrategy strategy;
    private volatile boolean stop;
    private ExecutorService threadPool;
    /**
    * constructor
     * @param port - the port of the server
     * @param listeningIntervalMS
     * @param strategy - the strategy of the maze action*/
    public Server(int port, int listeningIntervalMS, IServerStrategy strategy){
        this.port = port;
        this.listeningIntervalMS = listeningIntervalMS;
        this.strategy = strategy;
        try {
            Configurations configurations = Configurations.getInstance();
            int threadAmount = Integer.parseInt(configurations.getThreadAmount());
            if(threadAmount <= 0) //if we get invalid thread amount, we will initialize it with 1
                threadAmount = 1;
            this.threadPool = Executors.newFixedThreadPool(threadAmount);
        }
        catch (IOException e){e.printStackTrace();}
    }
    /**
     * start function open the server connection and wait for a signal from the client */
    public void start(){
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            serverSocket.setSoTimeout(listeningIntervalMS);
            new Thread(() -> {
                try {
                    waitForClientRespond(serverSocket); //waiting for a signal from the client
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }).start();
        }
        catch (IOException e){e.printStackTrace();}
    }
    /**
     * the function getting a signal from a client and execute it using a thread from the thread pool
     * @param serverSocket- getting the socket from the server
     * @throws IOException*/
    private void waitForClientRespond(ServerSocket serverSocket) throws IOException {
        while (!stop){
            try {
                Socket client = serverSocket.accept();
                threadPool.submit(() -> clientHandle(client)); //performing the strategy that the client ask for
            }
            catch (IOException ignored){}
        }
        serverSocket.close();
    }
    /**
     * the function initialize the server with the relevant strategy
     * @param clientSocket- getting the socket from the client*/
    private void clientHandle(Socket clientSocket){
        try {
            strategy.serverStrategy(clientSocket.getInputStream(), clientSocket.getOutputStream());
            clientSocket.close();
        }
        catch (IOException e){e.printStackTrace();}
    }
    /**
     the function stop all the active threads in the project and deleting all the solution's path files*/
    public void stop(){
        stop = true;
        if (strategy instanceof ServerStrategySolveSearchProblem)
            ((ServerStrategySolveSearchProblem) strategy).deleteFile();
        threadPool.shutdownNow();
    }
}