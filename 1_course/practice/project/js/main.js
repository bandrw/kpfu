function get_input() // возвращает значения из инпутов
{
	let data;

	data = {
		author: document.getElementById("author_input").value,
		name: document.getElementById("name_input").value,
		country: document.getElementById("country_input").value,
		year: document.getElementById("year_input").value,
		count: document.getElementById("count_input").value
	};
	return (data);
}

function str_next(str)
{
	let s;
	let i;

	s = [];
	i = 1;
	while (i < str.length)
	{
		s.push(str[i]);
		i++;
	}
	return (s);
}

function ft_match(s1, s2) // регулярные выражения
{
	if (s1[0] == null && s2[0] == null)
		return (true);
	if (s1[0] == null && s2[0] == "*")
		return (ft_match(s1, str_next(s2)));
	if (s1[0] != null && s1[0] == s2[0])
		return (ft_match (str_next(s1), str_next(s2)));
	if (s1[0] != null && s2[0] == "*")
		return (ft_match(s1, str_next(s2)) || ft_match(str_next(s1), s2));
	return (false);
}

function match(str1, str2)
{
	if (str1 == "" || ft_match(str2.toLowerCase(), ("*" + str1.toLowerCase() + "*")))
		return (true);
	else
		return (false);
}

function get_books() // возвращает массив подобранных книг
{
	let data;
	let res;
	let list;

	if (!localStorage.getItem("library"))
	{
		console.error("Can't get library in get_books");
		return (0);
	}
	data = get_input();
	list = JSON.parse(localStorage.getItem("library"));
	res = [];
	while (list)
	{
		if (match(data.author, list.data.author) && match(data.name, list.data.name) &&
				match(data.country, list.data.country) &&
				(data.year == "" || data.year == list.data.year) &&
				((data.count <= list.data.count && data.count > 0) || data.count == ""))
			res.push(list.data);
		list = list.next;
	}
	return (res);
}
