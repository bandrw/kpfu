let g_transition = 0.3;

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

function show_book_info()
{
	let list;

	list = g_library.next;
	if (list)
	{
		while (list.data.id != this.value)
			list = list.next;
		if (list.data)
			alert("Название: " + list.data.name + "\nАвтор: " + list.data.author + "\nСтрана: " +
				list.data.country + "\nГод: " + list.data.year + "\nКоличество: " + list.data.count);
	}
}

function ul_handle(ul, elem)
{
	let books;
	let li;
	let i;
	let div;
	let info;

	books = get_books();
	if (books.length > 0)
		elem.innerHTML = "Доступные книги";
	else
		elem.innerHTML = "Книги не найдены";
	i = 0;
	while (i < books.length)
	{
		li = document.createElement("li");
		li.className = "books_list_li";
		div = document.createElement("div");
		div.innerHTML = books[i].author + " - " + books[i].name;
		li.appendChild(div);
		info = document.createElement("span");
		info.className = "li_info";
		info.value = books[i].id;
		info.onclick = show_book_info;
		div.appendChild(info);
		ul.appendChild(li);
		i++;
	}
}

function show_books() // создаем span и ul и анимируем переходы
{
	let elem;
	let ul;

	elem = document.createElement("span");
	elem.style.transition = g_transition + "s ease";
	elem.className = "result_span";
	ul = document.createElement("ul");
	ul.style.transition = g_transition + "s ease";
	ul.className = "books_list";
	ul_handle(ul, elem);
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

function scroll_to_library()
{
	let start;
	let timer;
	let timePassed;
	let currentScroll;
	let goal;
	let src;

	prev = window.pageYOffset || document.documentElement.scrollTop;
	goal = document.getElementById("library_href").offsetTop;
	if (goal < prev)
	{
		scroll(0, goal);
		return;
	}
	start = Date.now();
	scr = 5;
	timer = setInterval(function() {
		timePassed = Date.now() - start;
		currentScroll = window.pageYOffset || document.documentElement.scrollTop;
		if (currentScroll == goal || prev > currentScroll)
			clearInterval(timer);
		if (timePassed > 100)
			scr *= 1.01;
		if (goal - currentScroll < scr)
			scr = goal - currentScroll;
		if (goal - currentScroll < 100)
			scr *= 0.85;
		if (scr < 1)
			scr = 1;
		scroll(0, currentScroll + scr);
		prev = currentScroll;
	}, 1);
}

document.getElementById("main_form").style.transition = g_transition + "s ease";
document.getElementById("lib_request_btn").onclick = show_result;
document.getElementById("library_href").onclick = scroll_to_library;
