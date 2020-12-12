import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;

public class Database
{
	private static final String url = "jdbc:mysql://localhost:3306/conferences?serverTimezone=UTC";
	private static final String user = "root";
	private static final String password = "";

	private static Connection connection;
	private static Statement statement;
	private static ResultSet resultSet;

	public static ResultSet getUsers()
	{
		try
		{
			connection = DriverManager.getConnection(url, user, password);
			statement = connection.createStatement();
			resultSet = statement.executeQuery("SELECT * FROM users");
		}
		catch (Exception e)
		{
			System.err.println("[getUsers]");
			e.printStackTrace(System.err);
		}
		return (resultSet);
	}

	public static ArrayList<Conference> getConferences()
	{
		ArrayList<Conference> conferences = new ArrayList<>();
		try
		{
			connection = DriverManager.getConnection(url, user, password);
			statement = connection.createStatement();
			resultSet = statement.executeQuery("SELECT * FROM conference");
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
				if (!participantsStr.isEmpty())
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

	public static void updateParticipants(int id, ArrayList<Integer> participants)
	{
		PreparedStatement preparedStatement;
		StringBuilder str = new StringBuilder();

		try
		{
			connection = DriverManager.getConnection(url, user, password);
			preparedStatement = connection.prepareStatement("UPDATE conference SET participants = ? WHERE id = ?");
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

	public static String getUserName(int id)
	{
		ResultSet res = Database.getUsers();
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
		return ("");
	}
}
