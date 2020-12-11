import java.io.BufferedReader;
import java.io.FileReader;

public class User
{
	private final boolean isAuthorized;
	public String name;

	User(String login, String password)
	{
		this.isAuthorized = authorize(login, password);
	}

	private boolean authorize(String login, String password)
	{
		String line;
		String[] arr;
		BufferedReader reader;

		try
		{
			reader = new BufferedReader(new FileReader("src/users.txt"));
			while ((line = reader.readLine()) != null)
			{
				arr = line.split(",");
				if (arr.length == 2 && arr[0].equals(login) && arr[1].equals(password))
				{
					this.name = "Андрей Балашов";
					return (true);
				}
			}
		}
		catch (Exception e)
		{
			System.err.println(e);
		}
		return (false);
	}

	public boolean isAuthorized()
	{
		return (this.isAuthorized);
	}
}