function close_result()
{
	document.getElementById("result").setAttribute("style", "transition: 0.25s; opacity: 0");
	setTimeout(function(){
		document.getElementById("result").remove();
	}, 250);
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

function show_library(library)
{
	let i;

	i = 0;
	while (i < library.length)
	{
		div = document.createElement("div");
		div.className = "library_book";
		span = document.createElement("span");
		span.className = "lib_book_author";
		span.innerHTML = library[i].author;
		div.appendChild(span);
		span = document.createElement("span");
		span.className = "lib_book_name";
		span.innerHTML = library[i].name;
		div.appendChild(span);
		span = document.createElement("span");
		span.className = "lib_book_country";
		span.innerHTML = library[i].country;
		div.appendChild(span);
		span = document.createElement("span");
		span.className = "lib_book_year";
		span.innerHTML = library[i].year;
		div.appendChild(span);
		span = document.createElement("span");
		span.className = "lib_book_count";
		span.innerHTML = library[i].count;
		div.appendChild(span);
		document.getElementById("library_content").appendChild(div);
		i++;
	}
}

function get_library()
{
	let library;
	let div;
	let span;
	let i;

	library = [];
	library[0] = {
		author: "А.С. Пушкин",
		name: "Дубровский",
		country: "Russia",
		year: 2000,
		count: 10
	};
	library[1] = {
		author: "Л.Н. Толстой",
		name: "Война и мир",
		country: "Russia",
		year: 2000,
		count: 10
	};
	library[2] = {
		author: "Л.Н. Толстой",
		name: "Война и мир",
		country: "Russia",
		year: 2000,
		count: 10
	};
	library[3] = {
		author: "Л.Н. Толстой",
		name: "Война и мир",
		country: "Russia",
		year: 2000,
		count: 10
	};
	library[4] = {
		author: "Л.Н. Толстой",
		name: "Война и мир",
		country: "Russia",
		year: 2000,
		count: 10
	};
	return (library);
}

function books_cmp(data, lib)
{
	let books;

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

function get_books()
{
	let lib;
	let data;

	data = input_handle();
	console.log(data);
	lib = get_library();
	return (books_cmp(data, lib));
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
		show_exit();
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
}

show_library(get_library());
document.getElementById("lib_request_btn").onclick = show_result;
