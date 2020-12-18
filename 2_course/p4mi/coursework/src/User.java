import java.sql.ResultSet;

public class User
{
	public int		id;
	public String	name;
	public boolean	isProfessor;

	public boolean authorize(String login, String password)
	{
		ResultSet resultSet = Main.database.getUsers();
		try
		{
			while (resultSet.next())
			{
				if (resultSet.getString("login").equals(login) &&
					resultSet.getString("password").equals(password))
				{
					this.id = resultSet.getInt("id");
					this.name = resultSet.getString("name");
					this.isProfessor = resultSet.getBoolean("is_professor");
					return (true);
				}
			}
		}
		catch (Exception e)
		{
			System.err.println("[authorize]");
			e.printStackTrace(System.err);
		}
		return (false);
	}
}