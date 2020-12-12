import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application
{
	@Override
	public void start(Stage primaryStage) throws Exception
	{
		FXMLLoader loader = new FXMLLoader(getClass().getResource("views/login.fxml"));
		Database database = new Database("jdbc:mysql://localhost:3306/conferences?serverTimezone=UTC", "root", "");

		primaryStage.setMinWidth(350.0);
		primaryStage.setTitle("Conference");
		primaryStage.setScene(new Scene(loader.load(), 600, 500));
		primaryStage.setMinWidth(650.0);
		primaryStage.setMinHeight(300.0);
		LoginController controller = loader.getController();
		controller.initData(getHostServices(), database);
		primaryStage.show();
	}

	public static void main(String[] args)
	{
		launch(args);
	}
}
