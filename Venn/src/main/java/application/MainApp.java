package application;


import java.io.IOException;
import controllers.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuBar;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainApp extends Application {
	
	public static Stage primaryStage;
	private AnchorPane vennPane;
	private BorderPane rootLayout;
	private MenuBar menuBar;
	private FXMLLoader loader;
	private BorderPane menuPane;
	private MenuSceneController menuSceneCont;
	private ShapeSceneController shapeSceneCont;
	private MenuSceneController menuBarCont;
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

    //Maximizes the stage immediately on Launch
		MainApp.primaryStage.setMaximized(true);
		
		//Close window properly using consume
		MainApp.primaryStage.setOnCloseRequest(e -> {
			if (shapeSceneCont!=null) {
			shapeSceneCont.saveVenn(shapeSceneCont.getTextFields());
			}
			e.consume();
			MenuBarController.closeProgram(e);
		});
	
	}
	
	private void loadRootLayout() throws IOException {
		this.loader = new FXMLLoader();
		this.loader.setLocation(getClass().getResource("RootLayout.fxml")); 		
		this.rootLayout = (BorderPane) loader.load();
	}
	
	private void loadMenubar() throws IOException  {
		FXMLLoader loader1 = new FXMLLoader();
		loader1.setLocation(getClass().getResource("menuBar.fxml"));
		this.menuBar = (MenuBar) loader1.load();
		this.rootLayout.setTop(this.menuBar);
		menuBarCont=(MenuSceneController) loader1.getController();
		menuBarCont.setMainApp(this);
	}
	
	private void loadShapeScene() throws IOException {
		MainApp.primaryStage.setTitle("Untitled Vennn Diagram");
		this.loader = new FXMLLoader();
		this.loader.setLocation(getClass().getResource("shapeScene.fxml"));
		//this.vennPane = (StackPane) loader.load();
		
		//this.rootLayout.setCenter(this.vennPane);
		this.vennPane = (AnchorPane) loader.load();
	
		rootLayout.setCenter(this.vennPane); //make the center of the Menubar Scene to the rootLayout
		shapeSceneCont=(ShapeSceneController) loader.getController();
		shapeSceneCont.setMainApp(this);
		
		
	}
	
	public void save() {
			shapeSceneCont.saveVenn(shapeSceneCont.getTextFields());

		
	}
	
	public void switchScene(String sceneNew) throws IOException {
		if (sceneNew.equals("menuScene")) {
			loadMenuScene();
		}	
		else if (sceneNew.equals("shapeScene")) {
			loadShapeScene();
		}
		else if (sceneNew.equals("load")) {
			loadShapeScene();
			shapeSceneCont.loadVenn();
			if(shapeSceneCont.getTextFields().isEmpty()){
				loadMenuScene();
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Warning Dialog");
				alert.setHeaderText("Empty TextField");
				alert.setContentText("Error Loading: Nothing to Load, Please Create a New Venn Diagram First");
				alert.showAndWait();
			}
		}
	}
	
	private void loadMenuScene() throws IOException {
		this.loader = new FXMLLoader();
		this.loader.setLocation(getClass().getResource("menuScene.fxml"));
		this.menuPane = (BorderPane) loader.load();
		this.menuPane.getStylesheets().add(getClass().getResource("menuScene.css").toString());
		rootLayout.setCenter(this.menuPane);
		menuSceneCont=(MenuSceneController) loader.getController();
		menuSceneCont.setMainApp(this);
	}
	
    public static void main(String[] args) {
		launch(args);
	}
    
}