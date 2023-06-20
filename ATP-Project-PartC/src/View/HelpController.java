package View;

import javafx.application.Platform;
import javafx.fxml.Initializable;
import javafx.stage.Stage;
import java.net.URL;
import java.util.ResourceBundle;

public class HelpController implements Initializable {
    public javafx.scene.control.Button exit;
    public javafx.scene.control.Label text;
    public void closePane() {
        Stage s = (Stage) exit.getScene().getWindow();
        s.close();
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        text.setWrapText(true);
        text.setText("""
                Hey and Welcome to the information section of the game.                                
                This application allows you to generate and solve mazes. Here's some information to help you get started:
                
                Maze Generation:
                Use the "Generate Maze" feature to create random mazes.
                Choose the desired size of the maze.
                Select the generation algorithm, such as EmptyMaze, SimpleMaze or MyMaze to generate the maze.
                
                Maze Solving:                 
                Challenge yourself by solving the generated mazes.
                Use different solving algorithms, such as DFS, BFS or Best First Search, to find the optimal path from the entrance to the exit.
                Explore different strategies and techniques to improve your maze solving skills.
                
                Navigation:                
                Interact with the maze using keyboard or mouse controls.
                Use the NumPad keys to navigate through the maze.

                Additional Features:                                
                Save or load mazes to continue solving them later.
                Experiment with different settings and options to enhance your maze solving experience.
                Enjoy the challenge of generating and solving mazes using this application. Have fun exploring and discovering the hidden paths within the mazes!
                """
        );
    }
}
