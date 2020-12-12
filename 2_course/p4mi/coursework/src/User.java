import java.sql.ResultSet;

public class User
{
	public int				id;
	public String			name;
	public boolean			isProfessor;
	private final boolean	isAuthorized;
	private Database		database;

	User(String login, String password, Database database)
	{
		this.database = database;
		this.isAuthorized = authorize(login, password);
	}

	private boolean authorize(String login, String password)
	{
		ResultSet resultSet = this.database.getUsers();
		try
		{
			while (resultSet.next())
			{
				if (resultSet.getString("login").equals(login) && resultSet.getString("password").equals(password))
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

	public boolean isAuthorized()
	{
		return (this.isAuthorized);
	}
}