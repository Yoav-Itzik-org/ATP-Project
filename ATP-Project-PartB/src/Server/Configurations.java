package Server;
import java.io.*;
import java.util.ArrayList;
import java.util.Properties;

public class Configurations {
    private static Configurations configurations = null;
//    OutputStream output = new FileOutputStream("/Users/usernew/Documents/GitHub/ATP-Project/ATP-Project-PartB/resources/config.properties");
    InputStream input = new FileInputStream("/Users/usernew/Documents/GitHub/ATP-Project/ATP-Project-PartB/resources/config.properties");
    private Configurations() throws FileNotFoundException {}
    public static Configurations getInstance() throws FileNotFoundException {
        if (configurations == null) {
            configurations = new Configurations();
//            writeConfig("5", "EmptyMazeGenerator", "BestFirstSearch");
        }
        return configurations;
    }
//    public void writeConfig(String threadAmount, String generateAlgorithm, String solutionAlgorithm){
//        try
//        {
//            Properties prop = new Properties();
//            prop.setProperty("threadPoolSize", threadAmount);
//            prop.setProperty("mazeGeneratingAlgorithm", generateAlgorithm);
//            prop.setProperty("mazeSearchingAlgorithm", solutionAlgorithm);
//            prop.store(output, null);
//            System.out.println(prop);
//        }
//        catch (IOException io) {
//            io.printStackTrace();
//        }
//    }
    private ArrayList<String> loadConfig() throws IOException {
        ArrayList<String> configValues = new ArrayList<>();
        Properties prop = new Properties();
        prop.load(input);
        System.out.println(prop.getProperty("threadPoolSize"));
        configValues.add(prop.getProperty("threadPoolSize"));
        configValues.add(prop.getProperty("mazeGeneratingAlgorithm"));
        configValues.add(prop.getProperty("mazeSearchingAlgorithm"));
        return configValues;
    }
    public String getThreadAmount() throws IOException {return configurations.loadConfig().get(0);}
    public String getGenerateMaze() throws IOException {return configurations.loadConfig().get(1);}
    public String getSolutionAlgorithm() throws IOException {return configurations.loadConfig().get(2);}
}
