import Classes.*;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


public class MyApplication extends Application{

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Pane 1.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1200, 800);
        stage.setTitle("ASU Movies Ontology");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        Actors actors = new Actors();
        Directors directors = new Directors();
        Genres genres = new Genres();
        LabelValues labelValues = new LabelValues();
        launch();
    }
}
