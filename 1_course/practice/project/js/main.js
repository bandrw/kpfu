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

function match(str1, str2)
{
	if (str1 == "" || str1 == str2)
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
			match(data.country, g_library[i].country) && match(data.year, g_library[i].year) &&
			data.count <= g_library[i].count)
			res.push(g_library[i]);
		i++;
	}
	return (res);
}
