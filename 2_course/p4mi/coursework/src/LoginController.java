import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController
{
	public static User user;

	@FXML
	private Label result;
	@FXML
	private TextField login;
	@FXML
	private TextField password;
	@FXML
	private Button loginButton;

	@FXML
	private void handleButtonAction()
	{
		user = new User();
		if (user.authorize(this.login.getText(), this.password.getText()))
		{
			this.result.setText("");
			try
			{
				FXMLLoader loader = new FXMLLoader(getClass().getResource("views/main.fxml"));

				Stage stage = new Stage();
				stage.setTitle("Conference");
				stage.setScene(new Scene(loader.load(), 850, 650));
				stage.getScene().getStylesheets().add("css/style.css");
				stage.setMinWidth(650.0);
				stage.setMinHeight(300.0);
				stage.setX(this.loginButton.getScene().getWindow().getX() + (this.loginButton.getScene().getWindow().getWidth() - 850) / 2.0);
				stage.setY(this.loginButton.getScene().getWindow().getY() + (this.loginButton.getScene().getWindow().getHeight() - 650) / 2.0);
				stage.show();
				((Stage)this.loginButton.getScene().getWindow()).close();
			}
			catch (Exception e)
			{
				System.err.println("[handleButtonAction]");
				e.printStackTrace();
				System.exit(1);
			}
		}
		else
		{
			this.result.setText("Error");
			this.result.setStyle("-fx-text-fill: #e44444;");
		}
	}
}
