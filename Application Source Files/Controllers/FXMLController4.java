import javafx.event.ActionEvent;
import Classes.Actors;
import Classes.Directors;
import Classes.LabelValues;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;

import java.io.IOException;
public class FXMLController4 {
    @javafx.fxml.FXML
    private AnchorPane rootPane4;
    @javafx.fxml.FXML
    private TextArea textArea;

    @javafx.fxml.FXML
    public void initialize() {
        // Initialize the text area
        textArea.setFont(Font.font("Verdana", 20));
        textArea.setText(LabelValues.SearchResult);
    }
    public void ToPane3(ActionEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("Pane 3.fxml"));
        rootPane4.getChildren().setAll(pane);
    }

    public void searchBtn(ActionEvent event) {
        LabelValues.SearchResult = LabelValues.getSearchResult();
        // Update text area
        textArea.setText(LabelValues.SearchResult);
    }
}
