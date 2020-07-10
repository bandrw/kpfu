const g_transition = 0.5;

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

function disable_buttons()
{
	let i;

	document.getElementById("lib_request_btn").setAttribute("disabled", "disabled");
	i = 0;
	while (i < document.getElementsByClassName("close_res_btn").length)
	{
		document.getElementsByClassName("close_res_btn")[i].onclick = null;
		i++;
	}
	setTimeout(function() {
		document.getElementById("lib_request_btn").removeAttribute("disabled");
		i = 0;
		while (i < document.getElementsByClassName("close_res_btn").length)
		{
			document.getElementsByClassName("close_res_btn")[i].onclick = close_result;
			i++;
		}
	}, g_transition * 1000);
}

function close_result() // закрывает result
{
	let result_arr;

	disable_buttons();
	result_arr = document.getElementsByClassName("result");
	if (result_arr.length < 1)
	{
		console.log("Error in close_result");
		return;
	}
	document.getElementById("main_form").style.marginLeft = null;
	while (result_arr.length > 1)
		result_arr[1].remove();
	result_arr[0].style.marginLeft = null;
	result_arr[0].style.transform = "scale(0.5) rotate(30deg)";
	setTimeout(function() {
		result_arr[0].remove();
	}, g_transition * 1000);
}

function result_animate() // анимация появления и замены result
{
	let results;

	results = document.getElementsByClassName("result"); // results[0] - старый результат, results[1] - новый
	if (results.length != 2)
	{
		console.log("Error in result_animate");
		return;
	}
	results[0].style.transition = g_transition / 2 + "s ease-out"; // первая половина анимации
	results[1].style.transition = g_transition / 2 + "s ease-out";
	results[0].style.transform = "rotateX(30deg) translateZ(0)";
	results[1].style.transform = "rotateX(30deg) translateZ(-100px)";
	results[0].style.marginTop = (results[0].offsetHeight / 2) + "px";
	results[1].style.marginTop = "-" + (results[1].offsetHeight / 2 * 2.5) + "px";
	results[1].style.zIndex = "49";
	setTimeout(function() { // вторая половина анимации
		results[0].style.zIndex = "49";
		results[1].style.zIndex = "50";
		results[0].style.marginTop = null;
		results[1].style.marginTop = null;
		results[1].style.transition = g_transition + "s ease";
		results[0].style.opacity = "0";
		results[0].style.transform = "translateZ(-100px)"
		results[1].style.transform = null;
	}, g_transition / 2 * 1000);
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
	if (document.getElementsByClassName("result").length > 0)
	{
		// есть предыдущий результат, анимируем result по оси y
		result.style.marginLeft = (document.getElementById("main_form").offsetWidth / 2 + 20) + "px";
		document.getElementById("container").appendChild(result);
		result_animate();
	}
	else
	{
		// это первый результат, анимируем main_form и result по оси x
		document.getElementById("main_form").style.marginLeft = "-" +
			(document.getElementById("main_form").offsetWidth / 2 + 20) + "px";
		document.getElementById("container").appendChild(result);
		result.style.marginLeft = (document.getElementById("main_form").offsetWidth / 2 + 20) + "px";
	}
	return (result);
}

function show_result() // вывод блока result
{
	let books;
	let elem;
	let result;
	let i;

	books = get_books(); //books - массив подобранных книг
	result = create_result(); // создаем result
	disable_buttons();
	setTimeout(function() { // конец анимации
		if (document.getElementsByClassName("result").length > 1)
			document.getElementsByClassName("result")[0].remove();
	}, g_transition * 1000);
	if (books.length > 0) // заполняем result
	{
		result.childNodes[0].innerHTML = "Доступные книги:";
		i = 0;
		while (i < books.length)
		{
			elem = document.createElement("li");
			elem.innerHTML = books[i].author + " - " + books[i].name;
			result.childNodes[1].appendChild(elem);
			i++;
		}
	}
	else
		result.childNodes[0].innerHTML = "Книги не найдены";
}

document.getElementById("main_form").style.transition = g_transition + "s ease";
show_library();
document.getElementById("lib_request_btn").onclick = show_result;
