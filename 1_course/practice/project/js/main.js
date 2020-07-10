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

function ft_match(s1, s2)
{
	if (s1[0] == null && s2[0] == null)
		return (1);
	if (s1[0] == null && s2[0] == "*")
		return (ft_match(s1, str_next(s2)));
	if (s1[0] != null && s1[0] == s2[0])
		return (ft_match (str_next(s1), str_next(s2)));
	if (s1[0] != null && s2[0] == "*")
		return (ft_match(s1, str_next(s2)) || ft_match (str_next(s1), s2));
	return (0);
}

function match(str1, str2)
{
	if (str1 == "" || ft_match(str2.toLowerCase(), ("*" + str1.toLowerCase() + "*")))
		return (true);
	else
		return (false);
}

function get_books() // возвращает массив книг который выводим в result -> ul
{
	let data;
	let res;
	let i;

	data = get_input();
	res = [];
	i = 0;
	while (i < g_library.length)
	{
		if (match(data.author, g_library[i].author) && match(data.name, g_library[i].name) &&
			match(data.country, g_library[i].country) && (data.year == "" || data.year == g_library[i].year) &&
			data.count <= g_library[i].count)
			res.push(g_library[i]);
		i++;
	}
	return (res);
}
