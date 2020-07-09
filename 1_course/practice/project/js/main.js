function close_result() // закрывает #result
{
	document.getElementById("main_form").style.marginLeft = null;
	document.getElementById("result").style.marginLeft = null;
	document.getElementById("result").style.transform = "scale(0.5) rotate(30deg)";
	setTimeout(function(){
		document.getElementById("result").remove();
	}, 500);
}

function create_result() // создаем блок #result
{
	let btn;
	let elem;

	document.getElementById("main_form").style.marginLeft = "-" +
		(document.getElementById("main_form").offsetWidth / 2 + 20) + "px";
	elem = document.createElement("div");
	elem.id = "result";
	document.getElementById("container").appendChild(elem);
	document.getElementById("result").style.marginLeft =
		(document.getElementById("main_form").offsetWidth / 2 + 20) + "px";
	elem = document.createElement("span");
	elem.id = "result_span";
	document.getElementById("result").appendChild(elem);
	elem = document.createElement("ul");
	elem.id = "books_list";
	document.getElementById("result").appendChild(elem);
	btn = document.createElement("span");
	btn.id = "close_res_btn";
	document.getElementById("result").appendChild(btn);
	btn.onclick = close_result;
}

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

function show_library() // выводит имеющиеся книги в #g_library
{
	let i;

	i = 0;
	while (i < g_library.length)
	{
		div = document.createElement("div");
		div.className = "library_book";
		span = document.createElement("span");
		span.className = "lib_book_author";
		span.innerHTML = g_library[i].author;
		div.appendChild(span);
		span = document.createElement("span");
		span.className = "lib_book_name";
		span.innerHTML = g_library[i].name;
		div.appendChild(span);
		span = document.createElement("span");
		span.className = "lib_book_country";
		span.innerHTML = g_library[i].country;
		div.appendChild(span);
		span = document.createElement("span");
		span.className = "lib_book_year";
		span.innerHTML = g_library[i].year;
		div.appendChild(span);
		span = document.createElement("span");
		span.className = "lib_book_count";
		span.innerHTML = g_library[i].count;
		div.appendChild(span);
		document.getElementById("library_content").appendChild(div);
		i++;
	}
}

function get_books() // возвращает массив книг который выводим в #result
{
	let data;
	let test;
	let i;
	let book_count;

	test = [];
	i = 0;
	book_count = Math.floor(Math.random() * 4);
	while (i < book_count)
	{
		test[i] = g_library[Math.floor(Math.random() * 4)];
		i++;
	}
	data = get_input();
	return (test);
}

function show_result() // вывод блока #result
{
	let books;
	let elem;
	let i;

	books = get_books();
	if (document.getElementById("result") == null)
		create_result();
	else
		while (document.getElementById("books_list").firstChild)
			document.getElementById("books_list").firstChild.remove();
	if (books.length > 0)
	{
		document.getElementById("result_span").innerHTML = "Доступные книги:";
		i = 0;
		while (i < books.length)
		{
			elem = document.createElement("li");
			elem.innerHTML = books[i].author + " - " + books[i].name;
			document.getElementById("books_list").appendChild(elem);
			i++;
		}
	}
	else
		document.getElementById("result_span").innerHTML = "Книги не найдены";
}

show_library();
document.getElementById("lib_request_btn").onclick = show_result;
