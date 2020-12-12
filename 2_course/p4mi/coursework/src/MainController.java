import javafx.application.HostServices;
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

import java.util.ArrayList;
import java.util.Calendar;

public class MainController
{
	private User user;
	private HostServices hostServices;
	private Database database;

	@FXML
	private VBox conferenceList;
	@FXML
	private Label nameLabel;

	private ArrayList<Conference> conferences;

	public void initData(User user, HostServices hostServices, Database database)
	{
		this.database = database;
		this.hostServices = hostServices;
		this.user = user;
		this.nameLabel.setText(user.name);
		this.conferences = database.getConferences(this.database);
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
		AnchorPane	contentArea = makeContentArea(conference);

		mainView.setPrefSize(200.0, 80.0);
		mainView.setMaxSize(700.0, 80.0);
		mainView.setMinSize(200.0, 80.0);
		mainView.setStyle("-fx-border-color: #ee7b42;");
		VBox.setMargin(mainView, new Insets(0, 60, 0, 60));

		contentArea.setOnMouseClicked((e) -> {
			try
			{
				FXMLLoader loader = new FXMLLoader(getClass().getResource("views/conference.fxml"));

				Stage stage = new Stage();
				stage.setTitle(conference.name);
				stage.setScene(new Scene(loader.load(), 550, 400));
				stage.getScene().getStylesheets().add("css/style.css");
				stage.setMinHeight(400.0);
				stage.setMinWidth(500.0);
				stage.setMaxHeight(450.0);
				stage.setMaxWidth(650.0);
				ConferenceController controller = loader.getController();
				controller.initData(conference, this.user, (Label) contentArea.getChildren().get(1), hostServices, this.database);
				stage.show();
			}
			catch (Exception ex)
			{
				System.err.println("[setOnMouseClicked]");
				ex.printStackTrace();
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
		Label		labelDate = new Label(String.format("%d", conference.date.get(Calendar.DAY_OF_MONTH)));
		Label		labelMonth = new Label(DateUtils.getMonthName(conference.date.get(Calendar.MONTH)));

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

	private AnchorPane makeContentArea(Conference conference)
	{
		AnchorPane	contentArea = new AnchorPane();
		VBox		content = new VBox();
		Label		registration = new Label();
		Label		name = new Label(conference.name);
		HBox		info = new HBox();
		Label		time = new Label(String.format("В %d:%d",
				conference.date.get(Calendar.HOUR_OF_DAY),
				conference.date.get(Calendar.MINUTE))
		);
		Label		duration = new Label(conference.duration);

		AnchorPane.setLeftAnchor(contentArea, 80.0);
		AnchorPane.setTopAnchor(contentArea, 0.0);
		AnchorPane.setRightAnchor(contentArea, 0.0);
		AnchorPane.setBottomAnchor(contentArea, 0.0);
		AnchorPane.setLeftAnchor(content, 0.0);
		AnchorPane.setTopAnchor(content, 0.0);
		AnchorPane.setRightAnchor(content, 0.0);
		AnchorPane.setBottomAnchor(content, 0.0);
		content.setAlignment(Pos.CENTER_LEFT);
		content.setPadding(new Insets(0, 0, 0, 20));
		content.setSpacing(5.0);

		registration.setFont(Font.font("System", 11));
		AnchorPane.setTopAnchor(registration, 5.0);
		AnchorPane.setRightAnchor(registration, 7.0);
		registration.setTextFill(Color.web("#ee7b42"));
		for (int id : conference.participants)
		{
			if (id == this.user.id)
			{
				registration.setText("REGISTERED");
				break;
			}
		}

		name.setFont(Font.font("System", 16));
		name.setTextFill(Color.web("#ffffff"));
		time.setTextFill(Color.web("#ffffff"));
		duration.setTextFill(Color.web("#ffffff"));

		info.setSpacing(65.0);

		info.getChildren().add(time);
		info.getChildren().add(duration);
		content.getChildren().add(name);
		content.getChildren().add(info);
		contentArea.setCursor(Cursor.HAND);
		contentArea.getChildren().add(content);
		contentArea.getChildren().add(registration);
		return (contentArea);
	}
}
