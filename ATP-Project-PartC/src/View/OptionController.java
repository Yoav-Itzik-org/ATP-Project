package View;

import javafx.application.Platform;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.util.Properties;
import java.util.ResourceBundle;

public class OptionController implements Initializable {
    public javafx.scene.control.Button exit;
    public javafx.scene.control.Button save;
    public ChoiceBox algo;
    public ChoiceBox maze;
    public ChoiceBox thread;
    String algorithm;
    String mazeType;
    String threadNum;

    public void close() {
        Stage s = (Stage) exit.getScene().getWindow();
        s.close();
    }

    public void save() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(String.format("Settings Saved \n "+ "\n Algorithem is: "+ algorithm + "\n MazeType is: "+ mazeType + "\n Number of cores: "+ threadNum));
        alert.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        algo.getItems().addAll("BFS", "DFS" , "Best First Search");
        maze.getItems().addAll("EmptyMaze", "SimpleMaze", "MyMaze");
        thread.getItems().addAll("1", "2", "3", "4", "5");
    }

    public void SetConf() throws IOException {
        OutputStream output = null;
        InputStream input = null;
        String text = null;
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("ATP-Project-PartC/resources/config.properties"));
            StringBuilder stringBuilder = new StringBuilder();
            String line = bufferedReader.readLine();
            line = bufferedReader.readLine();
            while (line != null) {
                stringBuilder.append(line + ",");
                stringBuilder.append(System.lineSeparator());
                line = bufferedReader.readLine();
            }
            text = stringBuilder.toString();
            bufferedReader.close();
        }
        catch (IOException e) {
        }
        if (input == null) {//check if file exist
            output = new FileOutputStream("ATP-Project-PartC/resources/config.properties");
            Properties prop = new Properties(); //create new prop file
            if (algo.getValue()==(String)"BFS")
                algorithm ="BreadthFirstSearch";
            if (algo.getValue()==(String)"DFS")
                algorithm ="DepthFirstSearch";
            if (algo.getValue()==(String)"Best First Search")
                algorithm ="BestFirstSearch";
            if (maze.getValue()==(String)"SimpleMaze")
                mazeType ="SimpleMazeGenerator";
            if (maze.getValue()==(String)"MyMaze")
                mazeType ="MyMazeGenerator";
            if (maze.getValue()==(String)"EmptyMaze")
                mazeType ="EmptyMazeGenerator";
            if (thread.getValue()==(String)"1")
                threadNum ="1";
            if (thread.getValue()==(String)"2")
                threadNum ="2";
            if (thread.getValue()==(String)"3")
                threadNum ="3";
            if (thread.getValue()==(String)"4")
                threadNum ="4";
            if (thread.getValue()==(String)"5")
                threadNum ="5";

            prop.setProperty("threadPoolSize", threadNum);
            prop.setProperty("mazeSearchingAlgorithm", algorithm);
            prop.setProperty("mazeGeneratingAlgorithm", mazeType);
            prop.store(output, null);
        }
        if (output != null) {
            try {
                output.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        save();
    }
}
