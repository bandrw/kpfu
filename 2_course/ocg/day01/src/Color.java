public class Color
{
	int red;
	int green;
	int blue;

	public Color(int rgb)
	{
		this.red = getRed(rgb);
		this.green = getGreen(rgb);
		this.blue = getBlue(rgb);
	}

	public Color(int red, int green, int blue)
	{
		this.red = red;
		this.green = green;
		this.blue = blue;
	}

	public static int getRed(int rgb)
	{
		return ((rgb >> 16) & 0xFF);
	}

	public static int getGreen(int rgb)
	{
		return ((rgb >> 8) & 0xFF);
	}

	public static int getBlue(int rgb)
	{
		return (rgb & 0xFF);
	}

	public int getRGB()
	{
		return ((red << 16) | (green << 8) | blue);
	}
}
