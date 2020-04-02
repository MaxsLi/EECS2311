package views;

import java.io.File;
import java.io.IOException;
import controllers.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuBar;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class MainApp extends Application {

	public static final String APP_TITLE = "VennCreate";

	public static Stage primaryStage;
	private AnchorPane vennPane;
	private MenuBar menuBar;
	private FXMLLoader loader;
	private AnchorPane menuPane;
	private MenuSceneController menuSceneCont;
	private MenuBarController menuBarCont;
	private ShapeSceneController shapeSceneCont;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws IOException {
		MainApp.primaryStage = primaryStage;

		this.loadMenuScene();
		this.loadMenubar();
		
		Parent root = menuPane;
		Scene scene = new Scene(root);
		
	
		MainApp.primaryStage.setScene(scene);
		//MainApp.primaryStage.sizeToScene();
		MainApp.primaryStage.setTitle(APP_TITLE);
		MainApp.primaryStage.getIcons().add(new Image("/images/logo.png"));

		MainApp.primaryStage.setMaxWidth(1250);
		MainApp.primaryStage.setMaxHeight(917);

		MainApp.primaryStage.show();

		// Maximizes the stage immediately on Launch
		//MainApp.primaryStage.setMaximized(true);

		// Close window properly using consume
		MainApp.primaryStage.setOnCloseRequest(e -> {
			if (shapeSceneCont != null) {
				try {
				shapeSceneCont.saveVenn(shapeSceneCont.getTextFields());
				}
				catch(NullPointerException NPE) {
					System.out.println("Thank You for Using Venn Create! (Exception)");
					//NPE.printStackTrace();
				}
			}
			e.consume();
			MenuBarController.closeProgram(e);
		});
	}


	/**
	 * A Method to parse the menuBar.fxml file and turn it into java code
	 * 
	 * @throws IOException
	 */
	public void loadMenubar() throws IOException {
		FXMLLoader loader1 = new FXMLLoader();
		loader1.setLocation(getClass().getResource("/fxml/menuBar.fxml"));
		this.menuBar = loader1.load();
		menuBarCont = loader1.getController();
		menuBarCont.setMainApp(this);
		//menuBarCont.addKeyShortcuts();
	}

	/**
	 * A Method to parse the shapeScene.fxml file and turn it into java code
	 * 
	 * @throws IOException
	 */
	public void loadShapeScene() throws IOException {
		this.loader = new FXMLLoader();
		this.loader.setLocation(getClass().getResource("/fxml/shapeScene.fxml"));

		this.vennPane = loader.load();


		shapeSceneCont = loader.getController();
		shapeSceneCont.setMainApp(this);
		MainApp.primaryStage.setScene(new Scene(this.vennPane));
	}

	/**
	 * A Method that switches the scene on the mainStage
	 * 
	 * @param sceneNew A string of the scene to change to
	 * @throws IOException
	 */
	public void switchScene(String sceneNew, File file) throws IOException {
		if (sceneNew.equals("menuScene")) {
			loadMenuScene();
		} else if (sceneNew.equals("shapeScene")) {
			loadShapeScene();
		} else if (sceneNew.equals("load")) {
			loadShapeScene();
			shapeSceneCont.loadVenn(file);
			
			 File currentDir = file.getParentFile();
			 
			 if( ! currentDir.exists()) {
				 currentDir = new File(System.getProperty("user.home"));
			 }
			
			if (currentDir.list().length == 0) {
				loadMenuScene();
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Warning Dialog");
				alert.setHeaderText("Empty TextField");
				alert.setContentText("Error Loading: Nothing to Load, Please Create a New Venn Diagram First");
				alert.showAndWait();
			}
		}
	}
	
	public ShapeSceneController getShapeSceneController() {
		return this.shapeSceneCont;
	}

	public MenuSceneController getMenuSceneCont() {
		return this.menuSceneCont;
	}

	public MenuBarController getMenuBarCont() {
		return menuBarCont;
	}

	/**
	 * A Method to parse the menuScene.fxml file and turn it into java code
	 * 
	 * @throws IOException
	 */
	public void loadMenuScene() throws IOException {
		this.loader = new FXMLLoader();
		this.loader.setLocation(getClass().getResource("/fxml/menuScene.fxml"));
		this.menuPane = loader.load();
		menuSceneCont = loader.getController();
		menuSceneCont.setMainApp(this);
	}
	
}