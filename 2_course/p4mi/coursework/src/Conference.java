import java.util.ArrayList;
import java.util.Calendar;

public class Conference
{
	public int					id;
	public String				name;
	public Calendar				date;
	public String				duration;
	public int					professorId;
	public String				description;
	public String				link;
	public ArrayList<Integer>	participants = new ArrayList<>();

	public void addParticipant(User user)
	{
		this.participants.add(user.id);
		Main.database.updateParticipants(this.id, this.participants);
	}

	public void deleteParticipant(User user)
	{
		this.participants.remove((Integer) user.id);
		Main.database.updateParticipants(this.id, this.participants);
	}
}
