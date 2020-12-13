import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;

public class Database
{
	private Connection connection;
	private Statement statement;

	Database(String url, String user, String password)
	{
		try
		{
			this.connection = DriverManager.getConnection(url, user, password);
			this.statement = connection.createStatement();
		}
		catch (Exception e)
		{
			System.err.println("[Database]");
			e.printStackTrace(System.err);
		}
	}

	public ResultSet getUsers()
	{
		try
		{
			return (this.statement.executeQuery("SELECT * FROM users"));
		}
		catch (Exception e)
		{
			System.err.println("[getUsers]");
			e.printStackTrace(System.err);
		}
		return (null);
	}

	public ArrayList<Conference> getConferences()
	{
		ArrayList<Conference> conferences = new ArrayList<>();
		ResultSet resultSet;

		try
		{
			resultSet = this.statement.executeQuery("SELECT * FROM conference ORDER BY date");
			while (resultSet.next())
			{
				Conference tmpConference = new Conference();
				tmpConference.id = resultSet.getInt("id");
				tmpConference.name = resultSet.getString("name");
				Calendar calendar = Calendar.getInstance();
				Timestamp timeStamp = resultSet.getTimestamp("date", calendar);
				calendar.setTimeInMillis(timeStamp.getTime());
				tmpConference.date = calendar;
				tmpConference.duration = resultSet.getString("duration");
				tmpConference.professorId = resultSet.getInt("professor_id");
				tmpConference.description = resultSet.getString("description");
				tmpConference.link = resultSet.getString("link");
				tmpConference.participants = new ArrayList<>();
				String participantsStr = resultSet.getString("participants");
				if (participantsStr != null && !participantsStr.isEmpty())
				{
					String[] arr = participantsStr.split(":");
					for (String p : arr)
						tmpConference.participants.add(Integer.parseInt(p));
				}
				conferences.add(tmpConference);
			}
		}
		catch (Exception e)
		{
			System.err.println("[getConferences]");
			e.printStackTrace(System.err);
		}
		return (conferences);
	}

	public void updateParticipants(int id, ArrayList<Integer> participants)
	{
		PreparedStatement preparedStatement;
		StringBuilder str = new StringBuilder();

		try
		{
			preparedStatement = this.connection.prepareStatement("UPDATE conference SET participants = ? WHERE id = ?");
			for (int nbr : participants)
			{
				str.append(nbr);
				str.append(':');
			}
			if (str.length() != 0)
				str.deleteCharAt(str.length() - 1);
			preparedStatement.setString(1, str.toString());
			preparedStatement.setInt(2, id);
			preparedStatement.executeUpdate();
		}
		catch (Exception e)
		{
			System.err.println("[updateParticipants]");
			e.printStackTrace(System.err);
		}
	}

	public void addConference(Conference conference)
	{
		PreparedStatement preparedStatement;

		if (conference.date.before(Calendar.getInstance()))
			return ;
		try
		{
			preparedStatement = this.connection.prepareStatement(
				"INSERT INTO conference (name, professor_id, date, duration, description, link) " +
				"VALUES (?, ?, ?, ?, ?, ?)");
			preparedStatement.setString(1, conference.name);
			preparedStatement.setInt(2, conference.professorId);
			preparedStatement.setDate(3, new Date(conference.date.getTimeInMillis()));
			//handle hours and minutes
			preparedStatement.setString(4, conference.duration);
			preparedStatement.setString(5, conference.description);
			preparedStatement.setString(6, conference.link);
			preparedStatement.executeUpdate();
		}
		catch (Exception e)
		{
			System.err.println("[updateParticipants]");
			e.printStackTrace(System.err);
		}
	}

	public String getUserName(int id)
	{
		ResultSet res = this.getUsers();

		try
		{
			while (res.next())
			{
				if (res.getInt("id") == id)
					return (res.getString("name"));
			}
		}
		catch (Exception e)
		{
			System.err.println("[getProfessorName]");
			e.printStackTrace();
		}
		return (null);
	}
}
