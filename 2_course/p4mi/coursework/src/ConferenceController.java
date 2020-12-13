import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class ConferenceController
{
	private Conference conference;
	private boolean isSubscribed = false;

	private Label regLabel;

	@FXML
	private Label mainName;
	@FXML
	private Label date;
	@FXML
	private Label professor;
	@FXML
	private Label description;
	@FXML
	private Hyperlink link;
	@FXML
	private Button closeButton;
	@FXML
	private Button subscribeButton;
	@FXML
	private HBox conferenceViewButtons;
	@FXML
	private VBox information;

	@FXML
	private void closeConference()
	{
		((Stage)this.closeButton.getScene().getWindow()).close();
	}

	@FXML
	private void subscribeHandle()
	{
		if (isSubscribed)
		{
			this.conference.deleteParticipant(LoginController.user);
			this.subscribeButton.setStyle("-fx-background-color: #ee7b42");
			this.subscribeButton.setText("Subscribe");
			this.isSubscribed = false;
			this.regLabel.setText("");
		}
		else
		{
			this.conference.addParticipant(LoginController.user);
			this.subscribeButton.setStyle("-fx-background-color: #607b86");
			this.subscribeButton.setText("Unsubscribe");
			this.isSubscribed = true;
			this.regLabel.setText("REGISTERED");
		}
	}

	public void initData(Conference conference, Label regLabel)
	{
		this.regLabel = regLabel;
		this.conference = conference;
		if (conference.professorId == LoginController.user.id)
			ownerView();
		else
			guestView();
	}

	private void ownerView()
	{
		Button edit = new Button();

		this.conferenceViewButtons.getChildren().remove(this.subscribeButton);
		if (this.conference.date.before(DateUtils.getCurrentTime()))
			edit.setDisable(true);
		edit.setText("Edit");
		edit.setCursor(Cursor.HAND);
		edit.setStyle("-fx-background-color: #ee7b42; -fx-text-fill: white");
		edit.setFont(Font.font(15));
		edit.setOnMouseClicked((e) -> editConference());
		this.conferenceViewButtons.getChildren().add(edit);
		mainName.setText(this.conference.name);
		date.setText(DateUtils.getFormatDate(conference.date));
		professor.setText("Преподаватель - " + Main.database.getUserName(conference.professorId));
		description.setText(conference.description);
		link.setText("cсылка");
		this.information.getChildren().add(makeParticipantsList());
		link.setOnAction((e) -> Main.hostServices.showDocument(conference.link));
	}

	private void guestView()
	{
		for (int participant : conference.participants)
		{
			if (participant == LoginController.user.id)
			{
				this.isSubscribed = true;
				this.subscribeButton.setStyle("-fx-background-color: #607b86");
				this.subscribeButton.setText("Unsubscribe");
				break;
			}
		}
		mainName.setText(this.conference.name);
		date.setText(DateUtils.getFormatDate(conference.date));
		professor.setText("Преподаватель - " + Main.database.getUserName(conference.professorId));
		description.setText(conference.description);
		link.setText("cсылка");
		link.setOnAction((e) -> Main.hostServices.showDocument(conference.link));
		if (this.conference.date.before(DateUtils.getCurrentTime()))
			this.subscribeButton.setDisable(true);
	}

	private VBox makeParticipantsList()
	{
		VBox		vBox = new VBox();
		ScrollPane	scrollArea = new ScrollPane();
		VBox		contentArea = new VBox();

		vBox.setSpacing(10.0);
		vBox.setMinHeight(150.0);
		scrollArea.setStyle("-fx-border-width: 2px; -fx-border-color: #3d3d3d");
		scrollArea.setPrefHeight(150.0);
		scrollArea.setPadding(new Insets(5, 5, 5, 5));
		Label label = new Label("Список участников:");
		label.setStyle("-fx-text-fill: white");
		label.setFont(Font.font(15.0));
		vBox.getChildren().add(label);
		contentArea.setSpacing(3.0);
		int n = 1;
		for (int participant : this.conference.participants)
		{
			Label l = new Label(String.format("%2d) %s", n, Main.database.getUserName(participant)));
			l.setFont(Font.font(15.0));
			contentArea.getChildren().add(l);
			n++;
		}
		scrollArea.setContent(contentArea);
		vBox.getChildren().add(scrollArea);
		return (vBox);
	}

	private void editConference()
	{
		try
		{
			FXMLLoader loader = new FXMLLoader(getClass().getResource("views/editConference.fxml"));

			Stage stage = new Stage();
			stage.setTitle("Edit conference");
			stage.setScene(new Scene(loader.load(), 600, 500));
			stage.getScene().getStylesheets().add("css/style.css");
			EditConference controller = loader.getController();
			controller.initData(conference);
			stage.setX(this.closeButton.getScene().getWindow().getX() + (this.closeButton.getScene().getWindow().getWidth() - 600) / 2.0);
			stage.setY(this.closeButton.getScene().getWindow().getY() + (this.closeButton.getScene().getWindow().getHeight() - 500) / 2.0);
			stage.setMinHeight(500.0);
			stage.setMinWidth(550.0);
			stage.setMaxHeight(700.0);
			stage.setMaxWidth(700.0);
			stage.show();
		}
		catch (Exception e)
		{
			System.err.println("[editConference]");
			e.printStackTrace();
			System.exit(1);
		}
	}
}
