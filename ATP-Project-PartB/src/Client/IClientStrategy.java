package Client;

import java.io.InputStream;
import java.io.OutputStream;
/**
 * interface of client Strategy - contains input and output stream of the maze / solution */
public interface IClientStrategy {
    void clientStrategy(InputStream inFromServer, OutputStream outToServer);
}