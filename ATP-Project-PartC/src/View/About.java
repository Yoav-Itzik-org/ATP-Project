package View;

import javafx.application.Platform;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;

import static javax.swing.text.html.CSS.Attribute.PADDING;

public class About implements Initializable {
    public javafx.scene.control.Button exit;
    public javafx.scene.control.Label text;
    public javafx.scene.image.ImageView exitTop;
    public void close() {
        Platform.exit();
    }

    public void closePane() {
        Stage s = (Stage) exit.getScene().getWindow();
        s.close();
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        text.setWrapText(true);
        text.setText("Hello and Welcome to Itzik and Yoav's project!\n" +
                "This application allows you to generate and solve mazes using various algorithms. " +
                "Create random mazes of different sizes and complexities, and challenge yourself by finding the optimal " +
                "path from the entrance to the exit. Enjoy the visually appealing representation of mazes and interact " +
                "with them using keyboard or mouse controls. Whether you're a puzzle enthusiast, algorithm lover, " +
                "or simply looking for a fun activity, this application provides an immersive experience in the intriguing " +
                "world of mazes. Get ready to explore and solve mazes like never before!\n"
        );

        Image SmallImageNearExit = null;
        try {
            SmallImageNearExit = new Image(new File("ATP-Project-PartC/resources/final.png").toURL().toExternalForm());
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
        }
        exitTop.setImage(SmallImageNearExit);
    }
}
