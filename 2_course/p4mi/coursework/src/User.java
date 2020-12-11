import java.sql.ResultSet;

public class User
{
	public int				id;
	public String			name;
	private final boolean	isAuthorized;

	User(String login, String password)
	{
		this.isAuthorized = authorize(login, password);
	}

	private boolean authorize(String login, String password)
	{
		ResultSet resultSet = Database.getUsers();
		try
		{
			while (resultSet.next())
			{
				if (resultSet.getString("login").equals(login) && resultSet.getString("password").equals(password))
				{
					this.id = resultSet.getInt("id");
					this.name = resultSet.getString("name");
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