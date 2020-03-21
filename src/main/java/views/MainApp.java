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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class MainApp extends Application {

	public static final String APP_TITLE = "VennCreate";

	public static Stage primaryStage;
	private AnchorPane vennPane;
	private BorderPane rootLayout;
	private MenuBar menuBar;
	private FXMLLoader loader;
	private BorderPane menuPane;
	private MenuSceneController menuSceneCont;
	private MenuBarController menuBarCont;
	private ShapeSceneController shapeSceneCont;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws IOException {
		MainApp.primaryStage = primaryStage;

		this.loadRootLayout();
		this.loadMenuScene();
		this.loadMenubar();

		Scene scene = new Scene(this.rootLayout);
		
		MainApp.primaryStage.setScene(scene);
		MainApp.primaryStage.sizeToScene();
		MainApp.primaryStage.setTitle(APP_TITLE);
		MainApp.primaryStage.getIcons().add(new Image("/images/logo.png"));

		MainApp.primaryStage.setMinWidth(primaryStage.getWidth());
		MainApp.primaryStage.setMinHeight(primaryStage.getHeight());

		MainApp.primaryStage.show();

		// Maximizes the stage immediately on Launch
		MainApp.primaryStage.setMaximized(true);
		
		
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
	 * A Method to parse the rootLayout.fxml file and turn it into java code
	 * 
	 * @throws IOException
	 */
	public void loadRootLayout() throws IOException {
		this.loader = new FXMLLoader();
		this.loader.setLocation(getClass().getResource("/fxml/rootLayout.fxml"));
		this.rootLayout = loader.load();
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
		this.rootLayout.setTop(this.menuBar);
		menuBarCont = loader1.getController();
		menuBarCont.setMainApp(this);
	}

	/**
	 * A Method to parse the shapeScene.fxml file and turn it into java code
	 * 
	 * @throws IOException
	 */
	public void loadShapeScene() throws IOException {
		this.loader = new FXMLLoader();
		this.loader.setLocation(getClass().getResource("/fxml/shapeScene.fxml"));
		// this.vennPane = (StackPane) loader.load();

		// this.rootLayout.setCenter(this.vennPane);

		this.vennPane = loader.load();

		rootLayout.setCenter(this.addZoomPane(this.vennPane)); // make the center of the Menubar Scene to the rootLayout
		shapeSceneCont = loader.getController();
		shapeSceneCont.setMainApp(this);
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

	/**
	 * A Method to parse the menuScene.fxml file and turn it into java code
	 * 
	 * @throws IOException
	 */
	public void loadMenuScene() throws IOException {
		this.loader = new FXMLLoader();
		this.loader.setLocation(getClass().getResource("/fxml/menuScene.fxml"));
		this.menuPane = loader.load();
		rootLayout.setCenter(this.addZoomPane(this.menuPane));
		menuSceneCont = loader.getController();
		menuSceneCont.setMainApp(this);
	}

	private VBox addZoomPane(Node node) {
		Parent zoomPane = new ZoomPane(new Group(node)).getParent();
		VBox layout = new VBox();
		layout.getChildren().setAll(zoomPane);
		VBox.setVgrow(zoomPane, Priority.ALWAYS);
		return layout;
	}
}