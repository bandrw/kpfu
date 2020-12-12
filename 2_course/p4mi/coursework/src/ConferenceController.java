import javafx.application.HostServices;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class ConferenceController
{
	private Conference conference;
	private User user;
	private boolean isSubscribed = false;
	private HostServices hostServices;
	private Database database;

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
	private void closeConference()
	{
		((Stage)this.closeButton.getScene().getWindow()).close();
	}

	@FXML
	private void subscribeHandle()
	{
		if (isSubscribed)
		{
			this.conference.deleteParticipant(this.user);
			this.subscribeButton.setStyle("-fx-background-color: #ee7b42");
			this.subscribeButton.setText("Subscribe");
			this.isSubscribed = false;
			this.regLabel.setText("");
		}
		else
		{
			this.conference.addParticipant(this.user);
			this.subscribeButton.setStyle("-fx-background-color: #607b86");
			this.subscribeButton.setText("Unsubscribe");
			this.isSubscribed = true;
			this.regLabel.setText("REGISTERED");
		}
	}

	public void initData(Conference conference, User user, Label regLabel, HostServices hostServices, Database database)
	{
		this.database = database;
		this.hostServices = hostServices;
		this.regLabel = regLabel;
		for (int participant : conference.participants)
		{
			if (participant == user.id)
			{
				this.isSubscribed = true;
				this.subscribeButton.setStyle("-fx-background-color: #607b86");
				this.subscribeButton.setText("Unsubscribe");
				break;
			}
		}
		this.conference = conference;
		this.user = user;
		mainName.setText(this.conference.name);
		date.setText(DateUtils.getFormatDate(conference.date));
		professor.setText("Преподаватель - " + this.database.getUserName(conference.professorId));
		description.setText(conference.description);
		link.setText("cсылка");
		link.setOnAction((e) -> {
			hostServices.showDocument(conference.link);
		});
	}
}
