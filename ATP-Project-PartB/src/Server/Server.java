package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private final int port;
    private final int listeningIntervalMS;
    private final IServerStrategy strategy;
    private volatile boolean stop;
    private ExecutorService threadPool;
    private Configurations configurations;
    public Server(int port, int listeningIntervalMS, IServerStrategy strategy){
        this.port = port;
        this.listeningIntervalMS = listeningIntervalMS;
        this.strategy = strategy;

        try {
            this.threadPool = Executors.newFixedThreadPool(Integer.parseInt(getThreadAmount()));
            this.configurations = Configurations.getInstance();
        }
        catch (IOException e){
            e.printStackTrace();
        }

    }
    public void start(){
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            serverSocket.setSoTimeout(listeningIntervalMS);
            new Thread(() -> {
                try {
                    waitForClientRespond(serverSocket);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }).start();
        }
        catch (IOException e){e.printStackTrace();}
    }
    private void waitForClientRespond(ServerSocket serverSocket) throws IOException {
        while (!stop){
            try {
                Socket client = serverSocket.accept();
                threadPool.submit(() -> clientHandle(client));
            }
            catch (IOException ignored){}
        }
        serverSocket.close();
    }
    private void clientHandle(Socket clientSocket){
        try {
            strategy.serverStrategy(clientSocket.getInputStream(), clientSocket.getOutputStream());
            clientSocket.close();
        }
        catch (IOException e){e.printStackTrace();}
    }
    public void stop(){
        stop = true;
        if (strategy instanceof ServerStrategySolveSearchProblem)
            ((ServerStrategySolveSearchProblem) strategy).deleteFile();
        threadPool.shutdown();
    }
    public String getThreadAmount() throws IOException {return configurations.loadConfig().get(0);}
    private String getGenerateMaze() throws IOException {return configurations.loadConfig().get(1);}
    private String getSolutionAlgorithm() throws IOException {return configurations.loadConfig().get(2);}
}