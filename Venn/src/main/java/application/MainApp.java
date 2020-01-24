package application;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class MainApp extends Application {
	
	private Stage primaryStage;
	private StackPane vennPane;
	private BorderPane rootLayout;
	private MenuBar menuBar;
	private FXMLLoader loader;

	@Override
	public void start(Stage primaryStage) throws IOException {
		
		this.primaryStage = primaryStage; //Setting the Primary Stage
		this.primaryStage.setTitle("Venn Diagram Maker App");
		
		loadRootLayout();
		loadMenubar();
		loadShapeScene();
		
		
		Scene scene = new Scene(this.rootLayout);
		this.primaryStage.setScene(scene);
		this.primaryStage.show();
	

		
	
	}
	
	private void loadRootLayout() throws IOException {
		this.loader = new FXMLLoader();
		this.loader.setLocation(getClass().getResource("rootLayout.fxml")); 		
		this.rootLayout = (BorderPane) loader.load();
	}
	
	private void loadMenubar() throws IOException  {
		this.loader = new FXMLLoader();
		this.loader.setLocation(getClass().getResource("menuBar.fxml"));
		this.menuBar = (MenuBar) loader.load();
		this.rootLayout.setTop(this.menuBar);
	}
	
	private void loadShapeScene() throws IOException {
		this.loader = new FXMLLoader();
		this.loader.setLocation(getClass().getResource("shapeScene.fxml"));
		this.vennPane = (StackPane) loader.load();
		this.rootLayout.setCenter(this.vennPane);
	}
	
    
    public static void main(String[] args) {
		launch(args);
	}
    
   
}
