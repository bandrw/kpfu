function close_result()
{
	document.getElementById("result").remove();
}

function show_exit()
{
	let btn;

	btn = document.createElement("span");
	btn.id = "close_res_btn";
	document.getElementById("result").appendChild(btn);
	btn.onclick = close_result;
}

function input_handle()
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

function get_books()
{
	let books;
	let data;

	data = input_handle();
	console.log(data);
	books = [];
	books[0] = {
		author: "А.С. Пушкин",
		name: "Дубровский",
		country: "Russia",
		year: 2000,
		count: 10
	};
	books[1] = {
		author: "Л.Н. Толстой",
		name: "Война и мир",
		country: "Russia",
		year: 2000,
		count: 10
	};
	return (books);
}

function show_result()
{
	let books;
	let elem;
	let i;

	books = get_books();
	if (document.getElementById("result") == null)
	{
		elem = document.createElement("div");
		elem.id = "result";
		document.getElementById("container").appendChild(elem);
		elem = document.createElement("span");
		elem.id = "result_span";
		document.getElementById("result").appendChild(elem);
		elem = document.createElement("ul");
		elem.id = "books_list";
		document.getElementById("result").appendChild(elem);
	}
	else
		while (document.getElementById("books_list").firstChild)
			document.getElementById("books_list").removeChild(document.getElementById("books_list").firstChild);
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
	show_exit();
}

document.getElementById("lib_request_btn").onclick = show_result;
