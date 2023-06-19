package View;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("MyView.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 1000);
        stage.setTitle("Spongebob Maze");
        stage.setScene(scene);
        stage.show();

        stage.setOnCloseRequest((event) -> {
            Platform.exit();
            System.exit(0);
        });
    }

    public static void main(String[] args) {
//        Music music = new Music("backgroundMusic"); //TODO release
        launch();
    }
}