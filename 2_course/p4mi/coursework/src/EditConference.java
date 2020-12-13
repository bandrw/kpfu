import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class EditConference
{
	private int conferenceId;

	@FXML
	private TextField name;
	@FXML
	private DatePicker date;
	@FXML
	private TextField duration;
	@FXML
	private TextArea description;
	@FXML
	private TextField link;
	@FXML
	private Button closeButton;
	@FXML
	private VBox inputVBox;
	@FXML
	private Button editButton;
	@FXML
	private TextField hours;
	@FXML
	private TextField minutes;

	public void initData(Conference conference)
	{
		this.conferenceId = conference.id;
		this.name.setText(conference.name);
		this.date.setValue(LocalDate.ofInstant(conference.date.toInstant(), conference.date.getTimeZone().toZoneId()));
		this.duration.setText(conference.duration);
		this.description.setText(conference.description);
		this.link.setText(conference.link);
		this.hours.setText(String.valueOf(conference.date.get(Calendar.HOUR_OF_DAY)));
		this.minutes.setText(String.valueOf(conference.date.get(Calendar.MINUTE)));
	}

	@FXML
	private void editHandle()
	{
		ArrayList<String> errors = new ArrayList<>();
		int hours = -1;
		int minutes = -1;

		if (this.name.getText().isEmpty())
			errors.add("Enter conference name");
		if (this.date.getValue() == null)
			errors.add("Enter conference date");
		if (this.hours.getText().isEmpty() || this.minutes.getText().isEmpty())
			errors.add("Enter conference time");
		else
		{
			hours = Integer.parseInt(this.hours.getText());
			minutes = Integer.parseInt(this.minutes.getText());
			if (hours < 0 || hours > 23 || minutes < 0 || minutes > 59)
				errors.add("Enter correct time");
		}
		if (this.duration.getText().isEmpty())
			errors.add("Enter conference duration");
		if (this.description.getText().isEmpty())
			errors.add("Enter conference description");
		if (this.link.getText().isEmpty())
			errors.add("Enter conference link");
		if (this.inputVBox.getChildren().size() == 6)
			this.inputVBox.getChildren().remove(5);
		if (errors.isEmpty())
		{
			Conference conference = new Conference();
			conference.id = this.conferenceId;
			conference.name = this.name.getText();
			LocalDate localDate = this.date.getValue();
			conference.date = new GregorianCalendar(
					localDate.getYear(),
					localDate.getMonthValue() - 1,
					localDate.getDayOfMonth(),
					hours + 3, // UTC+3
					minutes);
			conference.duration = this.duration.getText();
			conference.professorId = LoginController.user.id;
			conference.description = this.description.getText();
			conference.link = this.link.getText();
			Main.database.editConference(conference);
			((Stage)this.editButton.getScene().getWindow()).close();
		}
		else
		{
			Label errorLabel = new Label(errors.get(0));
			errorLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: #e44444;");
			this.inputVBox.getChildren().add(errorLabel);
		}
	}

	@FXML
	private void close()
	{
		((Stage) this.closeButton.getScene().getWindow()).close();
	}
}
