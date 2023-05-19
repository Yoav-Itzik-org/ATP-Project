package Server;
import java.io.*;
import java.util.Properties;
/**
 * configuration class - a singleton class that represent a connection to "config.properties" file
 * that will contain types of maze generating and solving and the amount of threads that the user wants
 * to the server*/
public class Configurations {
    private static Configurations configurations = null;
    private String threadPoolSize;
    private String mazeGeneratingAlgorithm;
    private String mazeSearchingAlgorithm;

    /**
     * constructor - getting the file and collecting the values
     */
    private Configurations(){
        InputStream input = this.getClass().getClassLoader().getResourceAsStream("config.properties"); // file path
        if(input == null) { // File not found
            // Inserting default values to attributes
            this.threadPoolSize = "1";
            this.mazeGeneratingAlgorithm = "EmptyMazeGenerator";
            this.mazeSearchingAlgorithm = "BestFirstSearch";
            return;
        }
        Properties prop = new Properties();
        try{
            prop.load(input); //loading the content of the file
        }
        catch (IOException e){
            return;
        }
        //saving in the correspond field the correct values of the user
        this.threadPoolSize = prop.getProperty("threadPoolSize");
        this.mazeGeneratingAlgorithm = prop.getProperty("mazeGeneratingAlgorithm");
        this.mazeSearchingAlgorithm = prop.getProperty("mazeSearchingAlgorithm");
    }
    /**
     * the function return the instance of the class using a singleton method
     * @return Configuration - same instant of all Configuration class
     * @throws FileNotFoundException configuration file not found
     */
    public static Configurations getInstance() throws FileNotFoundException {
        if (configurations == null)
            configurations = new Configurations();
        return configurations;
    }
    /**
     * the function return the thead amount
     * @return String - threadPoolSize*/
    public String getThreadAmount() {return threadPoolSize;}
    /**
     * the function return the generating maze type
     * @return String - generating maze type*/
    public String getGenerateMaze() {return this.mazeGeneratingAlgorithm;}
    /**
     * the function searching algorithm type
     * @return String - searching algorithm type*/
    public String getSolutionAlgorithm(){return this.mazeSearchingAlgorithm;}
}
