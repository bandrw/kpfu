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
		}
		else
		{
			this.conference.addParticipant(this.user);
			this.subscribeButton.setStyle("-fx-background-color: #add8e6");
			this.subscribeButton.setText("Unsubscribe");
			this.isSubscribed = true;
		}
	}

	public void initData(Conference conference, User user)
	{
		this.conference = conference;
		this.user = user;
		mainName.setText(this.conference.name);
		date.setText(conference.date);
		professor.setText("Преподаватель - " + conference.professor);
		description.setText(conference.description);
		link.setText(conference.link);
	}
}
