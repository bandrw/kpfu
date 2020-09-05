import java.util.Scanner;

public class Ex01
{
	private static void check_a(int x, int y)
	{
		System.out.print("a) ");
		if (x * x + y * y <= 81 && x >= 0)
		{
			if (x * x + y * y == 81 || x == 0)
				System.out.println("На границе");
			else
				System.out.println("Да");
		}
		else
			System.out.println("Нет");
	}

	private static void check_b(int x, int y)
	{
		System.out.print("b) ");
		if (x * x + y * y <= 100 &&
			x * x + y * y >= 25 &&
			y >= 0)
		{
			if (x * x + y * y == 100 ||
				x * x + y * y == 25 ||
				y == 0)
				System.out.println("На границе");
			else
				System.out.println("Да");
		}
		else
			System.out.println("Нет");
	}

	public static void main(String[] args)
	{
		int x;
		int y;
		Scanner sc = new Scanner(System.in);

		System.out.print("x = ");
		x = sc.nextInt();
		System.out.print("y = ");
		y = sc.nextInt();
		check_a(x, y);
		check_b(x, y);
	}
}
