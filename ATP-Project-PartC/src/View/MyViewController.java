package View;

import Model.MyModel;
import ViewModel.MyViewModel;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Observable;
import java.util.ResourceBundle;

public class MyViewController implements IView{
    public MyViewModel viewModel;
    @FXML
    private Button solve;

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
        fc.setInitialDirectory(new File("./resources"));
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
        fc.setInitialDirectory(new File("./resources"));
        File chosen = fc.showSaveDialog(null);
        try{
            chosen.createNewFile();
            viewModel.saveMaze(chosen);
        }
        catch (IOException ignored){
            System.out.println("Cannot save maze");
        }
    }
    public void updateProperties(ActionEvent actionEvent) {
        // TODO
    }
    public void exitProject(ActionEvent actionEvent) {
        ObservableList<Window> window = Stage.getWindows();
        window.get(0).hide();
    }
    public void help(ActionEvent actionEvent) {
        // TODO
    }
    public void about(ActionEvent actionEvent) {
        // TODO
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
            case "exit project" -> exit();
            default -> System.out.println("Not implemented change: " + change);
        }
    }
    private void mazeSolved() {mazeDisplayer.setSolution(viewModel.getSolution());}
    private void playerMoved() {setPlayerPosition(viewModel.getPlayerRow(), viewModel.getPlayerCol());}
    private void mazeGenerated() {mazeDisplayer.drawMaze(viewModel.getMaze());}
    private void mazeSaved(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("Maze saved successfully");
        alert.show();
    }
}