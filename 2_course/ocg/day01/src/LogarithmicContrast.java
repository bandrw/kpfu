import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class LogarithmicContrast
{
	private static void getMinMax(Color min, Color max, BufferedImage img)
	{
		int color;

		for (int i = 0; i < img.getWidth(); i++)
		{
			for (int j = 0; j < img.getHeight(); j++)
			{
				color = img.getRGB(i, j);
				Color rgb = new Color(-color);
				if (rgb.red > max.red)
					max.red = rgb.red;
				if (rgb.green > max.green)
					max.green = rgb.green;
				if (rgb.blue > max.blue)
					max.blue = rgb.blue;
				if (rgb.red < min.red)
					min.red = rgb.red;
				if (rgb.green < min.green)
					min.green = rgb.green;
				if (rgb.blue < min.blue)
					min.blue = rgb.blue;
			}
		}
	}

	private static void logarithmicContrast(BufferedImage img, int c)
	{
		Color color;
		Color min = new Color(255, 255, 255);
		Color max = new Color(0, 0, 0);

		getMinMax(min, max, img);
		for (int i = 0; i < img.getWidth(); i++)
		{
			for (int j = 0; j < img.getHeight(); j++)
			{
				color = new Color(-img.getRGB(i, j));
				color.red = (int)(c * Math.log(1 + (double)color.red));
				color.green = (int)(c * Math.log(1 + (double)color.green));
				color.blue = (int)(c * Math.log(1 + (double)color.blue));
				img.setRGB(i, j, -color.getRGB());
			}
		}
	}

	public static void main(String[] args)
	{
		File out;
		BufferedImage src;
		BufferedImage dst;
		int width;
		int height;

		if (args.length != 3)
		{
			System.out.println("Usage: `java Main <src.jpg> <out.jpg> <const c>`");
			System.exit(1);
		}
		try
		{
			src = ImageIO.read(new File(args[0]));
			width = src.getWidth();
			height = src.getHeight();
			dst = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			for (int i = 0; i < width; i++)
				for (int j = 0; j < height; j++)
					dst.setRGB(i, j, src.getRGB(i, j));
			logarithmicContrast(dst, Integer.parseInt(args[2]));
			out = new File(args[1]);
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
