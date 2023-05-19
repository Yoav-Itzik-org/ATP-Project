package Server;

import java.io.*;
import java.util.ArrayList;
import java.util.Properties;

public class Configurations {
    private static Configurations configurations = null;
    OutputStream output;
    InputStream input;
    private Configurations() throws FileNotFoundException {
        output = new FileOutputStream("path/to/config.properties");
        input = new FileInputStream("path/to/config.properties");
    }
    public static Configurations getInstance() throws FileNotFoundException {
        if (configurations == null)
            configurations = new Configurations();
        return configurations;
    }
    public void writeConfig(String threadAmount, String generateAlgorithm, String solutionAlgorithm){
        try
        {
            Properties prop = new Properties();
            prop.setProperty("threadPoolSize", threadAmount);
            prop.setProperty("mazeGeneratingAlgorithm", generateAlgorithm);
            prop.setProperty("mazeSearchingAlgorithm", solutionAlgorithm);
            prop.store(output, null);
            System.out.println(prop);
        }
        catch (IOException io) {
            io.printStackTrace();
        }
    }
    public ArrayList<String> loadConfig() throws IOException {
        ArrayList<String> configValues = new ArrayList<>();
        Properties prop = new Properties();
        prop.load(input);
        configValues.add(prop.getProperty("threadPoolSize"));
        configValues.add(prop.getProperty("mazeGeneratingAlgorithm"));
        configValues.add(prop.getProperty("mazeSearchingAlgorithm"));
        return configValues;
    }
}
