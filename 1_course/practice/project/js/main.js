let g_transition = 0.5;

function lastChild(arr)
{
	if (arr)
		return (arr[arr.length - 1]);
	else
		return (null);
}

function close_result() // закрывает result
{
	let result_arr;

	result_arr = document.getElementsByClassName("result");
	document.getElementById("main_form").style.marginLeft = null;
	while (result_arr.length > 1)
		result_arr[1].remove();
	if (result_arr[0])
	{
		result_arr[0].style.marginLeft = null;
		result_arr[0].style.transform = "scale(0.5) rotate(30deg)";
		setTimeout(function() {
			if (result_arr[0])
				result_arr[0].remove();
		}, g_transition * 1000);
	}
}

function result_animate(result) // анимация появления и замены result
{
	let prev_result;

	if (document.getElementsByClassName("result").length == 0)
	{
		// сдвигаем result и main_form
		document.getElementById("main_form").style.marginLeft = "-" +
			(document.getElementById("main_form").offsetWidth / 2 + 20) + "px";
		document.getElementById("container").appendChild(result);
		result.style.marginLeft = (document.getElementById("main_form").offsetWidth / 2 + 20) + "px";
	}
	else
	{
		// сдвигаем result вверх - вниз
		result.style.marginLeft = (document.getElementById("main_form").offsetWidth / 2 + 20) + "px";
		result.style.transition = g_transition / 2 + "s ease-out";
		document.getElementById("container").appendChild(result);
		setTimeout(function() {
			result.style.zIndex = "20";
			result.style.marginTop = "-" + (result.offsetHeight / 2 + 10) + "px";
			result.style.transform = "rotateX(30deg)";
		}, 0);
		prev_result = document.getElementsByClassName("result")[document.getElementsByClassName("result").length - 2];
		prev_result.style.transition = g_transition / 2 + "s ease-out";
		prev_result.style.marginTop = (prev_result.offsetHeight / 2 + 10) + "px";
		prev_result.style.transform = "rotateX(30deg)";
		setTimeout(function() {
			prev_result.style.zIndex = "20";
			result.style.zIndex = "50";
		}, g_transition / 2 * 1000);
		setTimeout(function() {
			result.style.marginTop = null;
			result.style.transition = g_transition + "s ease";
			result.style.transform = null;
			prev_result.style.marginTop = null;
			prev_result.style.opacity = "0"
		}, g_transition / 2 * 1000);
	}
}

function create_result() // создаем блок result
{
	let elem;
	let result;

	result = document.createElement("div");
	result.style.transition = g_transition + "s ease";
	result.className = "result";
	elem = document.createElement("span");
	elem.className = "result_span";
	result.appendChild(elem);
	elem = document.createElement("ul");
	elem.className = "books_list";
	result.appendChild(elem);
	elem = document.createElement("span");
	elem.className = "close_res_btn";
	result.appendChild(elem);
	elem.onclick = close_result;
	result_animate(result);
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

function get_books() // возвращает массив книг который выводим в result -> ul
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

function show_result() // вывод блока result
{
	let books;
	let elem;
	let i;

	// выключаем кнопку после нажатия и включаем после анимации
	document.getElementById("lib_request_btn").setAttribute("disabled", "disabled");
	setTimeout(function() {
		document.getElementById("lib_request_btn").removeAttribute("disabled");
	}, g_transition * 1000);
	//books - массив подобранных книг
	books = get_books();
	create_result();
	if (document.getElementsByClassName("result").length > 1)
		setTimeout(function() {
			if (document.getElementsByClassName("result")[0])
				document.getElementsByClassName("result")[0].remove();
		}, g_transition * 1000);
	if (books.length > 0)
	{
		lastChild(document.getElementsByClassName("result_span")).innerHTML = "Доступные книги:";
		i = 0;
		while (i < books.length)
		{
			elem = document.createElement("li");
			elem.innerHTML = books[i].author + " - " + books[i].name;
			lastChild(document.getElementsByClassName("books_list")).appendChild(elem);
			i++;
		}
	}
	else
		lastChild(document.getElementsByClassName("result_span")).innerHTML = "Книги не найдены";
}

document.getElementById("main_form").style.transition = g_transition + "s ease";
show_library();
document.getElementById("lib_request_btn").onclick = show_result;
