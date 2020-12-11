public class Conference
{
	public String	name;
	public String	date;
	public int		duration;
	public String	professor;
	public String	description;
	public String	link;

	Conference(String name, String professor, String date, int duration)
	{
		this.name = name;
		this.professor = professor;
		this.date = date;
		this.duration = duration;
	}

	public void addParticipant(User user)
	{
		System.out.println("Added " + user.name + " to " + this.name);
	}

	public void deleteParticipant(User user)
	{
		System.out.println("Deleted " + user.name + " from " + this.name);
	}
}
