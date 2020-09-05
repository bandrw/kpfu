import java.util.Scanner;

public class Ex04
{
	private static int min(int a, int b)
	{
		if (a < b)
			return (a);
		else
			return (b);
	}

	public static void main(String[] args)
	{
		int x;
		int y;
		int z;
		Scanner sc = new Scanner(System.in);

		System.out.print("x = ");
		x = sc.nextInt();
		System.out.print("y = ");
		y = sc.nextInt();
		z = min(3 * x, 2 * y) + min(x - y, x + y);
		System.out.printf("min(3 * %d, 2 * %d) + min(%d - %d, %d + %d) = %d\n",
			x, y, x, y, x, y, z);
	}
}
