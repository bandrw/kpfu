import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Random;

public class Main
{
	private static void addNoise(BufferedImage img)
	{
		int tmp;
		Color color = new Color();
		Random random = new Random();

		for (int i = 0; i < img.getWidth(); i++)
		{
			for (int j = 0; j < img.getHeight(); j++)
			{
				color.setRGB(img.getRGB(i, j));
				tmp = random.nextInt(64);
				if (tmp + Math.max(Math.max(color.red, color.green), color.blue) > 256)
					tmp = 256 - Math.max(Math.max(color.red, color.green), color.blue);
				color.red += tmp;
				color.green += tmp;
				color.blue += tmp;
				img.setRGB(i, j, color.getRGB());
			}
		}
	}

	private static void filterNoise(BufferedImage img)
	{
		int step = 4;
		Color color = new Color();
		Color[] colors = new Color[step * step];

		for (int i = 0; i < colors.length; i++)
			colors[i] = new Color();
		for (int i = 0; i < img.getWidth() - step; i += step)
		{
			for (int j = 0; j < img.getHeight() - step; j += step)
			{
				for (int k = 0; k < step; k++)
					for (int l = 0; l < step; l++)
						colors[k * step + l].setRGB(img.getRGB(k + i, l + j));
				for (int k = 0; k < step * step; k++)
				{
					color.red += colors[k].red;
					color.green += colors[k].green;
					color.blue += colors[k].blue;
				}
				color.red /= step * step;
				color.green /= step * step;
				color.blue /= step * step;
				for (int k = 0; k < step; k++)
					for (int l = 0; l < step; l++)
						img.setRGB(k + i, l + j, color.getRGB());
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

		if (args.length < 2)
		{
			System.out.println("Usage: `java Main src.jpg out.jpg`");
			System.out.println("Flags:");
			System.out.println("\t--add-noise");
			System.out.println("\t--filter-noise");
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
			if (args.length == 3)
			{
				if (args[2].equals("--add-noise"))
					addNoise(dst);
				else if (args[2].equals("--filter-noise"))
					filterNoise(dst);
			}
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
