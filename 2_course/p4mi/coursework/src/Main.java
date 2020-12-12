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

		primaryStage.setMinHeight(250.0);
		primaryStage.setMinWidth(350.0);
		primaryStage.setTitle("Conference");
		primaryStage.setScene(new Scene(loader.load(), 600, 500));
		primaryStage.setMinWidth(650.0);
		primaryStage.setMinHeight(300.0);
		LoginController controller = loader.getController();
		controller.initData(getHostServices());
		primaryStage.show();
	}

	public static void main(String[] args)
	{
		launch(args);
	}
}
