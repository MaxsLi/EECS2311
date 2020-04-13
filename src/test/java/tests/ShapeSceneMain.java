package tests;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ShapeSceneMain extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws IOException {
		Parent mainNode = FXMLLoader.load(getClass().getResource("/fxml/shapeScene.fxml"));
		primaryStage.setScene(new Scene(mainNode));
		primaryStage.show();
		primaryStage.toFront();
	}
}
