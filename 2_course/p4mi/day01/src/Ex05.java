import java.util.Scanner;

public class Ex05
{
	private static int f(int x)
	{
		if (x % 2 == 0)
			return (x / 2);
		return (0);
	}

	public static void main(String[] args)
	{
		Scanner sc = new Scanner(System.in);

		System.out.print("Put number: ");
		System.out.println(f(sc.nextInt()));
	}
}
