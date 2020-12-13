import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
	@FXML
	private VBox conferenceList;
	@FXML
	private Label nameLabel;
	@FXML
	private HBox header;
	@FXML
	private ImageView home_icon;
	@FXML
	private ImageView history_icon;

	private static ArrayList<Conference> conferences;
	private Label status;
	private enum Scenes {
		Main,
		History
	}
	private Scenes currentScene;

	public void initialize()
	{
		mainSceneInit();
		if (LoginController.user.isProfessor)
		{
			ImageView plus = new ImageView();
			plus.setImage(new Image("img/plus.png"));
			plus.setFitWidth(25.0);
			plus.setFitHeight(25.0);
			plus.setCursor(Cursor.HAND);
			plus.setOnMouseClicked((e) -> this.addConference());
			this.header.getChildren().add(plus);
		}
	}

	@FXML
	private void mainSceneInit()
	{
		this.currentScene = Scenes.Main;
		this.home_icon.setImage(new Image("img/home_active.png"));
		this.history_icon.setImage(new Image("img/history.png"));
		this.nameLabel.setText(LoginController.user.name);
		this.conferenceList.getChildren().clear();
		conferences = Main.database.getConferences();
		for (Conference conference : conferences)
		{
			if (conference.date.after(DateUtils.getCurrentTime()))
				this.conferenceList.getChildren().add(makeConferenceView(conference));
		}
		if (this.conferenceList.getChildren().isEmpty())
			this.conferenceList.getChildren().add(new Label("Empty"));
	}

	@FXML
	private void historySceneInit()
	{
		this.currentScene = Scenes.History;
		this.home_icon.setImage(new Image("img/home.png"));
		this.history_icon.setImage(new Image("img/history_active.png"));
		this.nameLabel.setText(LoginController.user.name);
		conferences = Main.database.getConferences();
		this.conferenceList.getChildren().clear();
		int i = conferences.size() - 1;
		while (i >= 0)
		{
			if (conferences.get(i).date.before(DateUtils.getCurrentTime()))
				this.conferenceList.getChildren().add(makeConferenceView(conferences.get(i)));
			i--;
		}
		if (this.conferenceList.getChildren().isEmpty())
			this.conferenceList.getChildren().add(new Label("Empty"));
	}

	private AnchorPane makeConferenceView(Conference conference)
	{
		AnchorPane	mainView = new AnchorPane();
		AnchorPane	dateArea = makeDateArea(conference);
		AnchorPane	contentArea = makeContentArea(conference);
		AnchorPane	deleteArea = new AnchorPane();

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
				controller.initData(conference, LoginController.user, this.status);
				stage.show();
			}
			catch (Exception ex)
			{
				System.err.println("[setOnMouseClicked]");
				ex.printStackTrace();
				System.exit(1);
			}
		});

		if (this.currentScene == Scenes.Main && conference.professorId == LoginController.user.id)
		{
			AnchorPane.setRightAnchor(deleteArea, 0.0);
			AnchorPane.setTopAnchor(deleteArea, 0.0);
			AnchorPane.setBottomAnchor(deleteArea, 0.0);
			deleteArea.setPrefSize(80.0, 80.0);
			ImageView deleteImage = new ImageView(new Image("img/delete.png"));
			VBox tmp = new VBox();
			tmp.setAlignment(Pos.CENTER);
			tmp.getChildren().add(deleteImage);
			deleteImage.setFitHeight(40.0);
			deleteImage.setFitWidth(40.0);
			deleteImage.setCursor(Cursor.DISAPPEAR);
			deleteImage.setOnMouseClicked((e) -> deleteConference(conference));
			AnchorPane.setBottomAnchor(tmp, 0.0);
			AnchorPane.setTopAnchor(tmp, 0.0);
			AnchorPane.setLeftAnchor(tmp, 0.0);
			AnchorPane.setRightAnchor(tmp, 0.0);
			deleteArea.getChildren().add(tmp);
		}

		mainView.getChildren().add(dateArea);
		mainView.getChildren().add(contentArea);
		if (this.currentScene == Scenes.Main && conference.professorId == LoginController.user.id)
			mainView.getChildren().add(deleteArea);
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
		Label		name = new Label(conference.name);
		HBox		info = new HBox();
		Label		time = new Label(String.format("В %02d:%02d",
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

		this.status = new Label();
		this.status.setPrefWidth(80.0);
		this.status.setAlignment(Pos.CENTER);
		this.status.setFont(Font.font("System", 11));
		AnchorPane.setTopAnchor(this.status, 5.0);
		AnchorPane.setRightAnchor(this.status, 0.0);
		this.status.setTextFill(Color.web("#ee7b42"));
		for (int id : conference.participants)
		{
			if (id == LoginController.user.id)
			{
				this.status.setText("REGISTERED");
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
		contentArea.getChildren().add(this.status);
		return (contentArea);
	}

	private void addConference()
	{
		try
		{
			FXMLLoader loader = new FXMLLoader(getClass().getResource("views/addConference.fxml"));

			Stage stage = new Stage();
			stage.setTitle("New conference");
			stage.setScene(new Scene(loader.load(), 600, 500));
			stage.getScene().getStylesheets().add("css/style.css");
			stage.setMinHeight(500.0);
			stage.setMinWidth(550.0);
			stage.setMaxHeight(700.0);
			stage.setMaxWidth(700.0);
			stage.show();
		}
		catch (Exception e)
		{
			System.err.println("[addConference]");
			e.printStackTrace();
			System.exit(1);
		}
	}

	private void deleteConference(Conference conference)
	{
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		DialogPane dialogPane = alert.getDialogPane();
		dialogPane.getStylesheets().add(getClass().getResource("css/alert.css").toExternalForm());
		dialogPane.getStyleClass().add("myDialog");
		dialogPane.lookupButton(ButtonType.OK).setStyle("-fx-background-color: #e9873a");
		dialogPane.lookupButton(ButtonType.OK).setCursor(Cursor.HAND);
		dialogPane.lookupButton(ButtonType.CANCEL).setCursor(Cursor.HAND);
		dialogPane.setPrefWidth(500.0);
		dialogPane.setPrefHeight(200.0);
		alert.setTitle("Delete confirmation");
		alert.setHeaderText("Are you sure you want to delete conference " + conference.name + "?");
		if (alert.showAndWait().get() == ButtonType.OK)
		{
			Main.database.deleteConference(conference.id);
			this.mainSceneInit();
		}
	}
}
