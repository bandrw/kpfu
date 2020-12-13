import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class AddConference
{
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
	private Button addButton;
	@FXML
	private TextField hours;
	@FXML
	private TextField minutes;

	@FXML
	private void addHandle()
	{
		ArrayList<String> errors = new ArrayList<>();

		if (this.name.getText().isEmpty())
			errors.add("Enter conference name");
		if (this.date == null)
			errors.add("Enter conference date");
		if (this.duration.getText().isEmpty())
			errors.add("Enter conference duration");
		if (this.description.getText().isEmpty())
			errors.add("Enter conference description");
		if (this.link.getText().isEmpty())
			errors.add("Enter conference link");
		if (this.inputVBox.getChildren().size() == 6)
			this.inputVBox.getChildren().remove(5);
		int hours = Integer.parseInt(this.hours.getText());
		int minutes = Integer.parseInt(this.minutes.getText());
		if (hours < 0 || hours > 23 || minutes < 0 || minutes > 59)
			errors.add("Enter correct time");
		if (errors.isEmpty())
		{
			Conference conference = new Conference();
			conference.name = this.name.getText();
			LocalDate localDate = this.date.getValue();
			conference.date = new GregorianCalendar(
					localDate.getYear(),
					localDate.getMonthValue() - 1,
					localDate.getDayOfMonth(),
					hours,
					minutes);
			conference.duration = this.duration.getText();
			conference.professorId = LoginController.user.id;
			conference.description = this.description.getText();
			conference.link = this.link.getText();
			Main.database.addConference(conference);
			((Stage)this.addButton.getScene().getWindow()).close();
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
		((Stage)this.closeButton.getScene().getWindow()).close();
	}
}
