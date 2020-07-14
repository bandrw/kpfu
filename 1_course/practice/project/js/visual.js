let g_transition = 0.3;

function show_library() // выводит имеющиеся книги из g_library в #library_content
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

function disable_buttons()
{
	let i;

	document.getElementById("lib_request_btn").onclick = null;
	document.getElementById("close_res_btn").onclick = null;
	setTimeout(function() {
		document.getElementById("lib_request_btn").onclick = show_result;
		document.getElementById("close_res_btn").onclick = close_result;
	}, g_transition * 1000);
}

function close_result() // закрывает result
{
	let result;

	disable_buttons();
	result = document.getElementById("result");
	if (result == null)
	{
		console.log("Error in close_result");
		return;
	}
	document.getElementById("main_form").style.marginLeft = null;
	result.style.transform = "scale(0.8)";
	result.style.marginLeft = null;
	setTimeout(function() {
		result.remove();
	}, g_transition * 1000);
}

function create_result() // создаем блок result
{
	let elem;
	let result;

	result = document.createElement("div");
	result.style.transition = g_transition + "s ease";
	result.id = "result";
	elem = document.createElement("span");
	elem.id = "close_res_btn";
	elem.onclick = close_result;
	result.appendChild(elem);
	elem = document.getElementById("main_form"); // анимация появления result
	elem.style.marginLeft = "-" + (elem.offsetWidth / 2 + 20) + "px";
	result.style.transform = "scale(0.8)";
	document.getElementById("container").appendChild(result);
	result.style.marginLeft = (document.getElementById("main_form").offsetWidth / 2 + 20) + "px";
	result.style.transform = "scale(1)";
}

function show_books() // создаем span и ul и анимируем переходы
{
	let books;
	let elem;
	let ul;
	let li;
	let i;

	books = get_books();
	elem = document.createElement("span");
	ul = document.createElement("ul");
	elem.style.transition = g_transition + "s ease";
	ul.style.transition = g_transition + "s ease";
	elem.className = "result_span";
	ul.className = "books_list";
	if (books.length > 0)
		elem.innerHTML = "Доступные книги";
	else
		elem.innerHTML = "Книги не найдены";
	books_height = 0;
	i = 0;
	while (i < books.length)
	{
		li = document.createElement("li");
		li.innerHTML = books[i].author + " - " + books[i].name;
		ul.appendChild(li);
		i++;
	}
	if (document.getElementsByClassName("result_span").length > 0)
	{
		elem.style.marginLeft = document.getElementById("result").offsetWidth + "px";
		ul.style.marginLeft = document.getElementById("result").offsetWidth + "px";
		document.getElementsByClassName("books_list")[0].style.marginLeft =
			"-" + document.getElementById("result").offsetWidth + "px";
		document.getElementsByClassName("result_span")[0].style.marginLeft =
			"-" + document.getElementById("result").offsetWidth + "px";
		setTimeout(function() {
			document.getElementsByClassName("books_list")[0].remove();
			document.getElementsByClassName("result_span")[0].remove();
		}, g_transition * 1000);
	}
	document.getElementById("result").appendChild(elem);
	document.getElementById("result").appendChild(ul);
	if (document.getElementsByClassName("result_span").length > 1)
		setTimeout(function() {
			elem.style.marginLeft = null;
			ul.style.marginLeft = null;
		}, 0);
	document.getElementById("result").style.height = elem.offsetHeight + 62 + ul.offsetHeight + 30 + "px";
}

function show_result() // вывод блока result
{
	if (document.getElementById("result") == null)
		create_result();
	disable_buttons();
	show_books();
}

document.getElementById("main_form").style.transition = g_transition + "s ease";
show_library();
document.getElementById("lib_request_btn").onclick = show_result;
