package View;

import ViewModel.MyViewModel;
import javafx.fxml.Initializable;
import java.util.Observer;

public interface IView extends Initializable, Observer {
    void setViewModel(MyViewModel viewModel);
    String getUpdatePlayerRow();
    String getUpdatePlayerCol();
    void setUpdatePlayerRow(int updatePlayerRow);
    void setUpdatePlayerCol(int updatePlayerCol);
    void setPlayerPosition(int row, int col);
}
