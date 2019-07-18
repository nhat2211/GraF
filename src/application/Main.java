package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class Main extends Application {
	private Stage primaryStage;
	@Override
	public void start(Stage primaryStage) {
		 this.primaryStage = primaryStage;
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/ui/HomePage.fxml"));
			//Parent root = FXMLLoader.load(getClass().getResource("/ui/HomePage.fxml"));
			MainController mainController = new MainController();
			mainController.setMainApp(this);
			loader.setController(mainController);
			Parent layout = loader.load();
			Scene scene = new Scene(layout);
			scene.getStylesheets().add("/application/application.css");

			primaryStage.setTitle("GraF");
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
	
	public Stage getPrimaryStage() {
        return primaryStage;
    }
}