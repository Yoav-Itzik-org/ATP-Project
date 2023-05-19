package Server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
/**
 * interface of server Strategy - contains input and output stream of the maze / solution */
public interface IServerStrategy {
    void serverStrategy(InputStream in, OutputStream out) throws IOException;
}