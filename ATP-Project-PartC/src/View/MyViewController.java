package View;

import Model.MyModel;
import ViewModel.MyViewModel;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.AudioClip;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.Observable;
import java.util.ResourceBundle;

public class MyViewController implements IView{
    public MyViewModel viewModel;
    @FXML
    private Button solve;
    public Thread t1;
    private AudioClip audio ;

    public boolean playerDragging = true;
    double playerX;
    double playerY;

    public MyViewController(){
        setViewModel(new MyViewModel(new MyModel()));
    }
    public void setViewModel(MyViewModel viewModel) {
        this.viewModel = viewModel;
        this.viewModel.addObserver(this);
    }
    public TextField textField_mazeRows;
    public TextField textField_mazeColumns;
    public MazeDisplayer mazeDisplayer;
    public Label playerRow;
    public Label playerCol;

    StringProperty updatePlayerRow = new SimpleStringProperty();
    StringProperty updatePlayerCol = new SimpleStringProperty();


    public String getUpdatePlayerRow() {
        return updatePlayerRow.get();
    }

    public void setUpdatePlayerRow(int updatePlayerRow) {
        this.updatePlayerRow.set(updatePlayerRow + "");
    }

    public String getUpdatePlayerCol() {
        return updatePlayerCol.get();
    }

    public void setUpdatePlayerCol(int updatePlayerCol) {
        this.updatePlayerCol.set(updatePlayerCol + "");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        playerRow.textProperty().bind(updatePlayerRow);
        playerCol.textProperty().bind(updatePlayerCol);
        mazeDisplayer.setOnMouseDragged(event -> {
            double displayWidth = mazeDisplayer.getWidth();
            double displayHeight = mazeDisplayer.getHeight();
            double rowWidth = displayWidth / viewModel.getMaze()[0].length;
            double colWidth = displayHeight / viewModel.getMaze().length;
            double clickX = event.getSceneX() - mazeDisplayer.getTranslateX();
            double clickY = event.getSceneY() - mazeDisplayer.getTranslateY();
            int playerRow = (int) (clickY / rowWidth);
            int playerCol = (int) (clickX / colWidth) - 2;
            if(viewModel.containsPath(playerRow, playerCol))
                viewModel.setPlayerLocation(playerRow, playerCol);
        });
    }

    public void generateMaze(ActionEvent actionEvent) {
        int rows = Integer.parseInt(textField_mazeRows.getText());
        int cols = Integer.parseInt(textField_mazeColumns.getText());
        viewModel.generateMaze(rows, cols);
        solve.setVisible(true);
    }

    public void solveMaze(ActionEvent actionEvent) {
        viewModel.solveMaze();
    }

    public void newFile(ActionEvent actionEvent) {
        viewModel.setMaze(null);
    }
    public void openFile(ActionEvent actionEvent) {
        FileChooser fc = new FileChooser();
        fc.setTitle("Open maze");
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Maze files (*.maze)", "*.maze"));
        fc.setInitialDirectory(new File("ATP-Project-PartC/resources"));
        File chosen = fc.showOpenDialog(null);
        try{
            chosen.createNewFile();
            viewModel.openMaze(chosen);
        }
        catch (IOException ignored){
            System.out.println("Cannot load maze");
        }
    }
    public void saveFile(ActionEvent actionEvent) {
        FileChooser fc = new FileChooser();
        fc.setTitle("Save maze");
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Maze files (*.maze)", "*.maze"));
        fc.setInitialDirectory(new File("ATP-Project-PartC/resources"));
        File chosen = fc.showSaveDialog(null);
        try{
            chosen.createNewFile();
            viewModel.saveMaze(chosen);
        }
        catch (IOException ignored){
            System.out.println("Cannot save maze");
        }
    }
    public void exitProject(ActionEvent actionEvent) {
        Platform.exit();
        System.exit(0);
    }
    public void keyPressed(KeyEvent keyEvent) {
        viewModel.movePlayer(keyEvent);
        keyEvent.consume();
    }

    public void setPlayerPosition(int row, int col){
        mazeDisplayer.setPlayerPosition(row, col);
        setUpdatePlayerRow(row);
        setUpdatePlayerCol(col);

    }

    public void mouseClicked(MouseEvent mouseEvent) {
        mazeDisplayer.requestFocus();
    }
    @Override
    public void update(Observable o, Object arg) {
        String change = (String) arg;
        switch (change){
            case "maze generated" -> mazeGenerated();
            case "player moved" -> playerMoved();
            case "maze solved" -> mazeSolved();
            case "maze saved" -> mazeSaved();
            case "maze solved by user" -> isSolved();
            default -> System.out.println("Not implemented change: " + change);
        }
    }
    private void mazeSolved() {
        mazeDisplayer.setSolution(viewModel.getSolution());}
    private void playerMoved() {setPlayerPosition(viewModel.getPlayerRow(), viewModel.getPlayerCol());}
    private void mazeGenerated() {
//        playMusic("startSong"); //TODO release
        mazeDisplayer.drawMaze(viewModel.getMaze());}
    private void mazeSaved(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("Maze saved successfully");
        alert.show();
    }
    private void isSolved(){
//        playMusic("endSong");//TODO release
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("Maze resolved successfully");
        alert.show();
        viewModel.setPlayerLocation(0, 0);
    }
    public void about(ActionEvent actionEvent) {
        try {
            Stage stage = new Stage();
            stage.setTitle("About");
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("About.fxml")));
            Scene scene = new Scene(root, 610, 350);
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
        } catch (Exception e) {
            System.out.println("Error About.fxml not found");
        }
    }
    public void help(ActionEvent actionEvent) {
        try {
            Stage stage = new Stage();
            stage.setTitle("Information");
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Help.fxml")));
            Scene scene = new Scene(root, 800, 420);
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
        } catch (Exception e) {
            System.out.println("Error Help.fxml not found");
        }
    }
    public void playMusic(String songName){
        stopMusic();
        audio = new AudioClip(new File(String.format("ATP-Project-PartC/resources/%s.mp3", songName)).toURI().toString());
        runMusic();
    }
    private void runMusic(){
        int playTimes = 1;
        audio.setVolume(0.1f);
        audio.setCycleCount(playTimes);
        audio.play();
    }
    private void stopMusic(){
        if (audio != null) {
            audio.stop();
        }
        audio = null;
    }
    public void updateProperties(ActionEvent actionEvent) {
        try {
            Stage stage = new Stage();
            stage.setTitle("Option");
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Option.fxml")));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL); //Lock the window until it closes
            stage.show();
        } catch (Exception e) {
            System.out.println("Error Option.fxml not found");
        }
    }
}