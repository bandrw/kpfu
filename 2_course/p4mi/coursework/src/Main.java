import javafx.application.Application;
import javafx.application.HostServices;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Main extends Application
{
	public static HostServices hostServices;
	public static Database database;
	public static ArrayList<User> users;

	@Override
	public void start(Stage primaryStage) throws Exception
	{
		hostServices = getHostServices();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("views/login.fxml"));
		database = new Database("jdbc:mysql://35.228.242.143:3306/conferences?serverTimezone=UTC", "p4mi", "P4mi_12345");
		users = database.getUsersList(database.getUsers());

		primaryStage.setTitle("Conference");
		primaryStage.setScene(new Scene(loader.load(), 600, 500));
		primaryStage.setMinWidth(500.0);
		primaryStage.setMinHeight(500.0);
		primaryStage.show();
	}

	public static void main(String[] args)
	{
		launch(args);
	}
}
