package views;

import controllers.MenuBarController;
import controllers.MenuSceneController;
import controllers.ShapeSceneController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuBar;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;


public class MainApp extends Application {

	public static Stage primaryStage;
	private AnchorPane vennPane;
	private BorderPane rootLayout;
	private MenuBar menuBar;
	private FXMLLoader loader;
	private BorderPane menuPane;
	private MenuSceneController menuSceneCont;
	private ShapeSceneController shapeSceneCont;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws IOException {
		MainApp.primaryStage = primaryStage;

		loadRootLayout();
		loadMenubar();
		loadMenuScene();

		Scene scene = new Scene(this.rootLayout);

		MainApp.primaryStage.setScene(scene);
		MainApp.primaryStage.sizeToScene();

		MainApp.primaryStage.setMinWidth(primaryStage.getWidth());
		MainApp.primaryStage.setMinHeight(primaryStage.getHeight());

		MainApp.primaryStage.show();

		// Maximizes the stage immediately on Launch
		MainApp.primaryStage.setMaximized(true);

		// Close window properly using consume
		MainApp.primaryStage.setOnCloseRequest(e -> {
			if (shapeSceneCont != null) {
				shapeSceneCont.saveVenn(shapeSceneCont.getTextFields());
			}
			e.consume();
			MenuBarController.closeProgram(e);
		});
	}

	/**
	 * A Method to parse the RootLayout.fxml file and turn it into java code
	 * 
	 * @throws IOException
	 */
	private void loadRootLayout() throws IOException {
		this.loader = new FXMLLoader();
		this.loader.setLocation(getClass().getResource("./fxml/RootLayout.fxml"));
		this.rootLayout = loader.load();
	}

	/**
	 * A Method to parse the menuBar.fxml file and turn it into java code
	 * 
	 * @throws IOException
	 */
	private void loadMenubar() throws IOException {
		FXMLLoader loader1 = new FXMLLoader();
		loader1.setLocation(getClass().getResource("./fxml/menuBar.fxml"));
		this.menuBar = (MenuBar) loader1.load();
		this.rootLayout.setTop(this.menuBar);
	}

	/**
	 * A Method to parse the shapeScene.fxml file and turn it into java code
	 * 
	 * @throws IOException
	 */
	private void loadShapeScene() throws IOException {
		this.loader = new FXMLLoader();
		this.loader.setLocation(getClass().getResource("./fxml/shapeScene.fxml"));
		// this.vennPane = (StackPane) loader.load();

		// this.rootLayout.setCenter(this.vennPane);
		this.vennPane = loader.load();

		// Zoom!
		Parent zoomPane = new ZoomPane(new Group(this.vennPane)).getParent();
		VBox layout = new VBox();
		layout.getChildren().setAll(zoomPane);
		VBox.setVgrow(zoomPane, Priority.ALWAYS);

		rootLayout.setCenter(layout); // make the center of the Menubar Scene to the rootLayout
		shapeSceneCont = loader.getController();
		shapeSceneCont.setMainApp(this);

	}

	/**
	 * A Method that switches the scene on the mainStage
	 * 
	 * @param sceneNew A string of the scene to change to
	 * @throws IOException
	 */
	public void switchScene(String sceneNew) throws IOException {
		if (sceneNew.equals("menuScene")) {
			loadMenuScene();
		} else if (sceneNew.equals("shapeScene")) {
			loadShapeScene();
		} else if (sceneNew.equals("load")) {
			loadShapeScene();
			shapeSceneCont.loadVenn();
			if (shapeSceneCont.getTextFields().isEmpty()) {
				loadMenuScene();
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Warning Dialog");
				alert.setHeaderText("Empty TextField");
				alert.setContentText("Error Loading: Nothing to Load, Please Create a New Venn Diagram First");
				alert.showAndWait();
			}
		}
	}

	/**
	 * A Method to parse the menuScene.fxml file and turn it into java code
	 * 
	 * @throws IOException
	 */
	private void loadMenuScene() throws IOException {
		this.loader = new FXMLLoader();
		this.loader.setLocation(getClass().getResource("./fxml/menuScene.fxml"));
		this.menuPane = (BorderPane) loader.load();
		this.menuPane.getStylesheets().add(getClass().getResource("./css/menuScene.css").toString());
		rootLayout.setCenter(this.menuPane);
		menuSceneCont = (MenuSceneController) loader.getController();
		menuSceneCont.setMainApp(this);
	}
}