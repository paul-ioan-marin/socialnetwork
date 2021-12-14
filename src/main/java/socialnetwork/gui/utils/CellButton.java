package socialnetwork.gui.utils;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

import java.util.function.Function;

public class CellButton<S> extends TableCell<S, Button> {
    private final Button button;

    public CellButton(String label, Function<S,Void> function) {
        this.getStyleClass().add("action-button-table-cell");
        this.button = new Button(label);
        this.button.setOnAction((ActionEvent e) -> {
            function.apply(getCurrentItem());
        } );
        this.button.setMaxWidth(Double.MAX_VALUE);
    }

    private S getCurrentItem() {
        return getTableView().getItems().get(getIndex());
    }

    public static <S> Callback<TableColumn<S, Button>, TableCell<S, Button>> forTableColumn(String label, Function< S, Void> function) {
        return param -> new CellButton<>(label, function);
    }

    @Override
    public void updateItem(Button item, boolean empty) {
        super.updateItem(item, empty);

        if (empty) {
            setGraphic(null);
        } else {
            setGraphic(button);
        }
    }
}
