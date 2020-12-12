import java.util.Calendar;

public class DateUtils
{
	public static String getMonthName(int nbr)
	{
		if (nbr == 0)
			return ("Января");
		if (nbr == 1)
			return ("Февраля");
		if (nbr == 2)
			return ("Марта");
		if (nbr == 3)
			return ("Апреля");
		if (nbr == 4)
			return ("Мая");
		if (nbr == 5)
			return ("Июня");
		if (nbr == 6)
			return ("Июля");
		if (nbr == 7)
			return ("Августа");
		if (nbr == 8)
			return ("Сентября");
		if (nbr == 9)
			return ("Октября");
		if (nbr == 10)
			return ("Ноября");
		return ("Декабря");
	}

	public static String getFormatDate(Calendar calendar)
	{
		return (String.format("%d %s в %d:%d",
				calendar.get(Calendar.DAY_OF_MONTH),
				getMonthName(calendar.get(Calendar.MONTH)),
				calendar.get(Calendar.HOUR_OF_DAY),
				calendar.get(Calendar.MINUTE)
		));
	}
}
