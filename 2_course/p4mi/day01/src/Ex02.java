import java.util.Scanner;

public class Ex02
{
	public static void main(String[] args)
	{
		int a;
		int b;
		int i;
		Scanner sc = new Scanner(System.in);

		System.out.print("A = ");
		a = sc.nextInt();
		System.out.print("B = ");
		b = sc.nextInt();
		i = b;
		while (i >= a)
		{
			System.out.println(i * i * i);
			i--;
		}
	}
}
