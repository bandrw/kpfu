import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class MainController
{
	private User user;

	@FXML
	private VBox conferenceList;

	private Conference[] conferences = {
			new Conference("Иностранный язык", "Юзмухаметова Л.Н.", "20 ноября 15:40", 90),
			new Conference("Проектирование человеко-машинного интерфейса", "Балафендиева И.С.", "20 ноября 17:50", 90),
			new Conference("Спецификация программных систем", "Матренина О.М.", "24 ноября 11:50", 90),
	};

	public void initData(User user)
	{
		this.user = user;
		conferences[0].description = "Изучаем английский";
		conferences[0].link = "https://teams.microsoft.com";
		conferences[1].description = "Делаем курсовые";
		conferences[1].link = "https://teams.microsoft.com";
		conferences[2].description = "Слушаем лекции";
		conferences[2].link = "https://teams.microsoft.com";
		for (Conference conference : conferences)
		{
			AnchorPane conferenceView = makeConferenceView(conference);
			this.conferenceList.getChildren().add(conferenceView);
		}
	}

	private AnchorPane makeConferenceView(Conference conference)
	{
		AnchorPane	mainView = new AnchorPane();
		AnchorPane	dateArea = makeDateArea(conference);
		VBox		contentArea = makeContentArea(conference);

		mainView.setPrefSize(200.0, 80.0);
		mainView.setMaxSize(700.0, 80.0);
		mainView.setStyle("-fx-border-color: #ee7b42;");
		VBox.setMargin(mainView, new Insets(0, 60, 0, 60));

		contentArea.setOnMouseClicked((e) -> {
			try
			{
				FXMLLoader loader = new FXMLLoader(getClass().getResource("views/conference.fxml"));

				Stage stage = new Stage();
				stage.setTitle(conference.name);
				stage.setScene(new Scene(loader.load(), 550, 400));
				ConferenceController controller = loader.getController();
				controller.initData(conference, this.user);
				stage.show();
			}
			catch (Exception ex)
			{
				System.err.println("[setOnMouseClicked] " + ex);
			}
		});

		mainView.getChildren().add(dateArea);
		mainView.getChildren().add(contentArea);
		return (mainView);
	}

	private AnchorPane makeDateArea(Conference conference)
	{
		AnchorPane	dateArea = new AnchorPane();
		VBox		vBox = new VBox();
		Label		labelDate = new Label("20");
		Label		labelMonth = new Label("Ноября");

		AnchorPane.setTopAnchor(dateArea, 0.0);
		AnchorPane.setLeftAnchor(dateArea, 0.0);
		AnchorPane.setBottomAnchor(dateArea, 0.0);
		dateArea.setPrefSize(80.0, 80.0);
		dateArea.setMaxSize(80.0, 80.0);
		dateArea.setStyle("-fx-background-color: #ee7b42;");

		vBox.setAlignment(Pos.CENTER);
		AnchorPane.setTopAnchor(vBox, 0.0);
		AnchorPane.setLeftAnchor(vBox, 0.0);
		AnchorPane.setBottomAnchor(vBox, 0.0);
		AnchorPane.setRightAnchor(vBox, 0.0);

		labelDate.setFont(Font.font("System", 30));
		labelDate.setTextFill(Color.web("#ffffff"));

		labelMonth.setTextFill(Color.web("#ffffff"));

		vBox.getChildren().add(labelDate);
		vBox.getChildren().add(labelMonth);
		dateArea.getChildren().add(vBox);
		return (dateArea);
	}

	private VBox makeContentArea(Conference conference)
	{
		VBox	contentArea = new VBox();
		Label	name = new Label(conference.name);
		HBox	info = new HBox();
		Label	time = new Label("В 17:50");
		Label	duration = new Label("1.5 часа");

		AnchorPane.setLeftAnchor(contentArea, 80.0);
		AnchorPane.setTopAnchor(contentArea, 0.0);
		AnchorPane.setRightAnchor(contentArea, 0.0);
		AnchorPane.setBottomAnchor(contentArea, 0.0);
		contentArea.setAlignment(Pos.CENTER_LEFT);
		contentArea.setPadding(new Insets(0, 0, 0, 20));
		contentArea.setSpacing(5.0);

		name.setFont(Font.font("System", 16));
		name.setTextFill(Color.web("#ffffff"));
		time.setTextFill(Color.web("#ffffff"));
		duration.setTextFill(Color.web("#ffffff"));

		info.setSpacing(50.0);

		info.getChildren().add(time);
		info.getChildren().add(duration);
		contentArea.getChildren().add(name);
		contentArea.getChildren().add(info);
		contentArea.setCursor(Cursor.HAND);
		return (contentArea);
	}
}
