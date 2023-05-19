package Server;
import java.io.*;
import java.util.Properties;

public class Configurations {
    private static Configurations configurations = null;
    private String threadPoolSize;
    private String mazeGeneratingAlgorithm;
    private String mazeSearchingAlgorithm;

    private Configurations() throws FileNotFoundException {
        InputStream input = new FileInputStream("ATP-Project-PartB/resources/config.properties");
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
        }
        return configurations;
    }
    public String getThreadAmount() {return threadPoolSize;}
    public String getGenerateMaze() {return this.mazeGeneratingAlgorithm;}
    public String getSolutionAlgorithm(){return this.mazeSearchingAlgorithm;}
}
