import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Random;

public class Main
{
	private static void imgPixelPut(BufferedImage img, int x, int y, int color)
	{
		if (x >= 0 && x < img.getWidth() && y >= 0 && y < img.getHeight())
			img.setRGB(x, y, color);
	}

	private static void putCircle(BufferedImage img, Circle circle)
	{
		int error;
		int x = 0;
		int y = circle.size / 2;
		int delta = 1 - circle.size;

		while (y >= 0)
		{
			imgPixelPut(img, circle.x + x, circle.y + y, circle.color);
			imgPixelPut(img, circle.x + x, circle.y - y, circle.color);
			imgPixelPut(img, circle.x - x, circle.y + y, circle.color);
			imgPixelPut(img, circle.x - x, circle.y - y, circle.color);
			error = 2 * (delta + y) - 1;
			if ((delta < 0) && (error <= 0))
			{
				x++;
				delta += 2 * x + 1;
				continue ;
			}
			if ((delta > 0) && (error > 0))
			{
				y--;
				delta -= 2 * y + 1;
				continue ;
			}
			x++;
			y--;
			delta += 2 * (x - y);
		}
	}

	public static void main(String[] args)
	{
		Random random = new Random();
		File out;
		BufferedImage dst;
		int width = 2560;
		int height = 1600;

		if (args.length != 1)
		{
			System.out.println("Usage: `java Main [out.jpg]`");
			System.exit(1);
		}
		try
		{
			dst = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			for (int i = 0; i < dst.getWidth(); i++)
				for (int j = 0; j < dst.getHeight(); j++)
					dst.setRGB(i, j, Color.WHITE.getRGB());
			for (int i = 0; i < 100; i++)
			{
				putCircle(dst, new Circle(random.nextInt(width), random.nextInt(height),
											random.nextInt(width + height),
											-random.nextInt(-Color.BLACK.getRGB())));
			}
			out = new File(args[0]);
			if (!out.exists() && !out.createNewFile() || !out.canWrite())
				throw new Exception("Can't create file");
			ImageIO.write(dst, "jpg", out);
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}
}
