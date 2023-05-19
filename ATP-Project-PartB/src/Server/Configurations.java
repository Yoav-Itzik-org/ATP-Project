package Server;
import java.io.*;
import java.util.ArrayList;
import java.util.Properties;

public class Configurations {
    private static Configurations configurations = null;
    private String threadPoolSize;
    private String mazeGeneratingAlgorithm;
    private String mazeSearchingAlgorithm;

    private Configurations() throws FileNotFoundException {
        InputStream input = new FileInputStream("/Users/usernew/Documents/GitHub/ATP-Project/ATP-Project-PartB/resources/config.properties");
        Properties prop = new Properties();
        try{
            prop.load(input);
        }
        catch (IOException e){
            return;
        }
        this.threadPoolSize = prop.getProperty("threadPoolSize");
        this.mazeGeneratingAlgorithm = prop.getProperty("mazeGeneratingAlgorithm");
        this.mazeSearchingAlgorithm = prop.getProperty("mazeSearchingAlgorithm");
    }
    public static Configurations getInstance() throws FileNotFoundException {
        if (configurations == null) {
            configurations = new Configurations();
//            writeConfig("5", "EmptyMazeGenerator", "BestFirstSearch");
        }
        return configurations;
    }
    public String getThreadAmount() throws IOException {return threadPoolSize;}
    public String getGenerateMaze() throws IOException {return this.mazeGeneratingAlgorithm;}
    public String getSolutionAlgorithm() throws IOException {return this.mazeSearchingAlgorithm;}
}
