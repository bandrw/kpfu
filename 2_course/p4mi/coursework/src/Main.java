import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application
{
	@Override
	public void start(Stage primaryStage) throws Exception
	{
		Parent root = FXMLLoader.load(getClass().getResource("views/login.fxml"));
		primaryStage.setMinHeight(250.0);
		primaryStage.setMinWidth(350.0);
		primaryStage.setTitle("Conference");
		primaryStage.setScene(new Scene(root, 600, 500));
		primaryStage.show();
	}

	public static void main(String[] args)
	{
		launch(args);
	}
}
